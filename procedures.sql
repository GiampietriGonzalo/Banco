#PROCEDURES

delimiter !

CREATE PROCEDURE transferir(IN codCaja INT(5),IN monto DECIMAL(16,2), IN cajaA INT(8),IN cajaB INT(8))
                            
  BEGIN   

     #Declaración de variables.
	 DECLARE saldo_cajaA DECIMAL(16,2);
	 DECLARE saldo_cajaB DECIMAL(16,2);
	 DECLARE nuevoSaldoA DECIMAL(16,2);
	 DECLARE nuevoSaldoB DECIMAL(16,2);  
	 DECLARE lastIDTransaccion INT(10);
     DECLARE lastIDTransaccionCaja INT(5);
	 DECLARE numCliente INT(5);

     #Declaro variables locales para recuperar los errores.
	 DECLARE codigo_SQL  CHAR(5) DEFAULT '00000';	 
	 DECLARE codigo_MYSQL INT DEFAULT 0;
	 DECLARE mensaje_error TEXT;
	 
     DECLARE EXIT HANDLER FOR SQLEXCEPTION 	 	
     
	  BEGIN  	  

	    GET DIAGNOSTICS CONDITION 1  codigo_MYSQL= MYSQL_ERRNO,codigo_SQL= RETURNED_SQLSTATE,mensaje_error= MESSAGE_TEXT;
	    SELECT 'SQLEXCEPTION!, transacción abortada' AS resultado, codigo_MySQL, codigo_SQL,  mensaje_error;		
        ROLLBACK;

	  END;		      
         
	 START TRANSACTION;	#Comienza la transacción  
		#Verifico que existan ambas cuentas. 
		IF EXISTS (SELECT * FROM caja_ahorro WHERE nro_ca=cajaA) AND EXISTS (SELECT * FROM caja_ahorro WHERE nro_ca=cajaB) THEN
		  
		  SELECT saldo INTO saldo_cajaA FROM caja_ahorro WHERE nro_ca=cajaA FOR UPDATE;
		  SELECT saldo INTO saldo_cajaB FROM caja_ahorro WHERE nro_ca=cajaB FOR UPDATE;
	
     	  #Si el saldo actual de la cajaA es suficiente para realizar la transferencia, 
          #entonces actualizo el saldo de ambas cuentas 

	      IF saldo_cajaA >= monto THEN 	  
	     
			 SET nuevoSaldoA=saldo_cajaA - monto;
		  	 SET nuevoSaldoB=saldo_cajaB + monto;  

	         UPDATE caja_ahorro SET saldo = nuevoSaldoA  WHERE nro_ca=cajaA;
	         UPDATE caja_ahorro SET saldo = nuevoSaldoB  WHERE nro_ca=cajaB;

		   #TRANSFERENCIA

			 #transaccion
			 INSERT INTO transaccion(fecha,hora,monto) VALUES (curdate(),curtime(),monto);

             set lastIDTransaccion=LAST_INSERT_ID();
             
			 #transaccion_por_caja
			 INSERT INTO transaccion_por_caja(nro_trans,cod_caja) VALUES (lastIDTransaccion,codCaja);
             
			 set lastIDTransaccionCaja=LAST_INSERT_ID();
			 SELECT nro_cliente INTO numCliente FROM cliente_ca WHERE nro_ca=cajaA;
			 
			 #transferencia
			 INSERT INTO transferencia(nro_trans,nro_cliente,origen,destino) VALUES (LAST_INSERT_ID(),numCliente,cajaA,cajaB); 

		   #DEPOSITO
			 INSERT INTO deposito VALUES (lastIDTransaccionCaja,cajaB);
	
             SELECT 'La transferencia ha sido exitosa' AS resultado; 

	    
		  ELSE  
             SELECT 'ERROR: Saldo insuficiente para realizar la transferencia' AS resultado;
	      END IF;  

	   ELSE  
            SELECT 'ERROR: Cuenta inexistente' AS resultado;  
	   END IF;  	 		
		
	 COMMIT;   #Comete la transacción  
 END; !


CREATE PROCEDURE extraer(codCaja INT (5),num_caja INT(8),monto INT(12))

	BEGIN

		#Declaración de variables.
		DECLARE saldo_caja DECIMAL(16,2);
		DECLARE nuevoSaldo DECIMAL(16,2);
		DECLARE num_cliente INT(5);
	
		#Declaro variables locales para recuperar los errores.
		DECLARE codigo_SQL  CHAR(5) DEFAULT '00000';	 
		DECLARE codigo_MYSQL INT DEFAULT 0;
		DECLARE mensaje_error TEXT;

		DECLARE EXIT HANDLER FOR SQLEXCEPTION 	 	
     
			BEGIN  	  

				GET DIAGNOSTICS CONDITION 1  codigo_MYSQL= MYSQL_ERRNO,codigo_SQL= RETURNED_SQLSTATE,mensaje_error= MESSAGE_TEXT;
				SELECT 'SQLEXCEPTION!, extracción abortada' AS resultado, codigo_MySQL, codigo_SQL,  mensaje_error;		
				ROLLBACK;

			END;		      
	
		START TRANSACTION;	#Comienza la transacción 		 

 		 
		#Verifico que el numero de cliente y la tarjeta correspondan a una misma caja de ahorro;
		#CREO QUE NO HACE FALTA VERIFICAR		
		IF EXISTS (SELECT * FROM caja_ahorro WHERE nro_ca=num_caja) THEN
			
			SELECT saldo INTO saldo_caja FROM caja_ahorro WHERE nro_ca=num_caja FOR UPDATE;
			SELECT nro_cliente INTO num_cliente FROM tarjeta WHERE nro_ca=num_caja;

			IF saldo_caja >= monto THEN 
		
				#TESTEO-ANTES
				#SELECT nro_ca,saldo FROM caja_ahorro WHERE nro_ca=num_caja;

				SET nuevoSaldo= saldo_caja - monto;

				#actualizacion del saldo de la caja de ahorro
				UPDATE caja_ahorro SET saldo = nuevoSaldo  WHERE nro_ca=num_caja;	
			
				#transaccion
				INSERT INTO transaccion(fecha,hora,monto) VALUES (curdate(),curtime(),monto);

				#transaccion_por_caja
				INSERT INTO transaccion_por_caja(nro_trans,cod_caja) VALUES (LAST_INSERT_ID(),codCaja);

				#extraccion
				INSERT INTO extraccion(nro_trans,nro_cliente,nro_ca) VALUES (LAST_INSERT_ID(),num_cliente,num_caja);

				#TESTEO-DESPUES
				#SELECT nro_ca,saldo FROM caja_ahorro WHERE nro_ca=num_caja;

				SELECT 'La extracción ha sido exitosa' AS resultado;
	
	   	 	ELSE
				SELECT 'ERROR: El monto a extraer es superior al saldo de la cuenta' AS resultado;		
	    
			END IF;	
	
		ELSE
			SELECT 'ERROR: El número de tarjeta no existe' AS resultado; 
		END IF;


		COMMIT; #Comete la transacción 

	END; !

CREATE TRIGGER cuotasDePrestamo AFTER INSERT ON prestamo FOR EACH ROW
	
	BEGIN
		#Declaración de variables
		DECLARE k INT;

	    SET k = 1;

	    WHILE k <= NEW.cant_meses DO
			INSERT INTO pago VALUES(NEW.nro_prestamo, k, NULL, (SELECT date_add(NEW.fecha, INTERVAL k MONTH)));
	        SET k = k+1;
	    END WHILE;

	END; !
	
    
 delimiter ; #reestablece ';' como delimitador de sentencias


GRANT EXECUTE ON banco.* TO 'atm'@'%';	
