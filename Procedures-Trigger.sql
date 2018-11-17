#PROCEDURES

delimiter !

CREATE PROCEDURE transferir(IN codCaja INT(5),IN monto DECIMAL(16,2), IN numTarjeta BIGINT,IN cajaB INT(8))
                            
  BEGIN   

     #Declaración de variables.
     DECLARE cajaA INT(8);
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
     
		IF EXISTS(SELECT * FROM caja WHERE cod_caja=codCaja) THEN
			IF EXISTS (SELECT * FROM tarjeta WHERE nro_tarjeta=numTarjeta) THEN
				IF EXISTS (SELECT * FROM caja_ahorro WHERE nro_ca=cajaB) THEN
				
          
					SELECT nro_ca INTO cajaA FROM tarjeta WHERE nro_tarjeta=numTarjeta;
					SELECT saldo INTO saldo_cajaA FROM caja_ahorro WHERE nro_ca=cajaA FOR UPDATE;
					SELECT saldo INTO saldo_cajaB FROM caja_ahorro WHERE nro_ca=cajaB FOR UPDATE;
	
					#Si el saldo actual de la cajaA es suficiente para realizar la transferencia, 
					#entonces actualizo el saldo de ambas cuentas 
			
					IF monto>0 THEN
		
						IF cajaA <> cajaB THEN
        
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
								SELECT nro_cliente INTO numCliente FROM tarjeta WHERE nro_tarjeta=numTarjeta;
			 
								#transferencia
								INSERT INTO transferencia(nro_trans,nro_cliente,origen,destino) VALUES (LAST_INSERT_ID(),numCliente,cajaA,cajaB); 

								#Transaccion para deposito
								INSERT INTO transaccion(fecha,hora,monto) VALUES (curdate(),curtime(),monto);
								set lastIDTransaccion=LAST_INSERT_ID();
                            
								INSERT INTO transaccion_por_caja(nro_trans,cod_caja) VALUES (lastIDTransaccion,codCaja);
								set lastIDTransaccionCaja=LAST_INSERT_ID();
                            
                            
								#DEPOSITO
								INSERT INTO deposito VALUES (lastIDTransaccionCaja,cajaB);
	
								SELECT 'La transferencia ha sido exitosa' AS resultado; 
							ELSE  
								SELECT 'ERROR: Saldo insuficiente para realizar la transferencia' AS resultado;
							END IF;
						ELSE
							SELECT 'ERROR: El numero de cuenta destino coincide con la cuenta de origen' AS resultado;
						END IF;
					ELSE
						SELECT 'ERROR: El monto a transferir debe ser mayora cero' AS resultado;
					END IF;
				ELSE
					SELECT 'ERROR: Caja de ahorro destino inexistente' AS resultado;
				END IF;    
			ELSE  
				SELECT 'ERROR: Numero de tarjeta inexistene' AS resultado;  
			END IF;  	
		ELSE
			SELECT 'ERROR: Caja inexistente' as Resultado;
		END IF;
        
		COMMIT;   #Comete la transacción  
 END; !


CREATE PROCEDURE extraer(codCaja INT (5),numTarjeta BIGINT,monto INT(12))

	BEGIN

		#Declaración de variables.
		DECLARE saldo_caja DECIMAL(16,2);
		DECLARE nuevoSaldo DECIMAL(16,2);
		DECLARE num_cliente INT(5);
        DECLARE numCA INT(8);
	
		#Declaro variables locales para recuperar los errores.
		DECLARE codigo_SQL  CHAR(5) DEFAULT '00000';	 
		DECLARE codigo_MYSQL INT DEFAULT 0;
		DECLARE mensaje_error TEXT;

		DECLARE EXIT HANDLER FOR SQLEXCEPTION 	 	
     
			BEGIN  	  

				GET DIAGNOSTICS CONDITION 1  codigo_MYSQL= MYSQL_ERRNO,codigo_SQL= RETURNED_SQLSTATE,mensaje_error= MESSAGE_TEXT;
				SELECT 'SQLEXCEPTION!, extraccion abortada' AS resultado, codigo_MySQL, codigo_SQL,  mensaje_error;		
				ROLLBACK;

			END;		      
	
		START TRANSACTION;	#Comienza la transacción 		 
		
		IF EXISTS(SELECT * FROM caja WHERE cod_caja=codCaja) THEN
			
			IF EXISTS(SELECT * FROM tarjeta WHERE nro_tarjeta=numTarjeta) THEN
			 
				SELECT nro_ca INTO numCa FROM tarjeta WHERE nro_tarjeta=numTarjeta;
				SELECT saldo INTO saldo_caja FROM caja_ahorro WHERE nro_ca=numCA FOR UPDATE;
				SELECT nro_cliente INTO num_cliente FROM tarjeta WHERE nro_tarjeta=numTarjeta;

				IF monto>0 THEN
 
					IF saldo_caja >= monto THEN 

						SET nuevoSaldo= saldo_caja - monto;

						#actualizacion del saldo de la caja de ahorro
						UPDATE caja_ahorro SET saldo = nuevoSaldo  WHERE nro_ca=numCA;	
			
						#transaccion
						INSERT INTO transaccion(fecha,hora,monto) VALUES (curdate(),curtime(),monto);

						#transaccion_por_caja
						INSERT INTO transaccion_por_caja(nro_trans,cod_caja) VALUES (LAST_INSERT_ID(),codCaja);

						#extraccion
						INSERT INTO extraccion(nro_trans,nro_cliente,nro_ca) VALUES (LAST_INSERT_ID(),num_cliente,numCA);

						SELECT 'La extracción ha sido exitosa' AS resultado;
	
					ELSE	
						SELECT 'ERROR: El monto a extraer es superior al saldo de la cuenta' AS resultado;
					END IF;	
                ELSE
					SELECT 'ERROR: El monto a extraer debe ser superior a cero' AS resultado;
				END IF;
			ELSE
				SELECT 'ERROR: Numero de tarjeta inexistente' AS resultado; 
			END IF;
		ELSE
			SELECT 'ERROR: Caja inexistente' AS resultado;
		END IF;	


		COMMIT; #Comete la transacción 

	END; !

CREATE TRIGGER cuotasDePrestamo AFTER INSERT ON prestamo FOR EACH ROW
	
	BEGIN
		#Declaración de variables
		DECLARE k INT;

	    SET k = 1;

	    WHILE k <= NEW.cant_meses DO
			INSERT INTO pago(nro_prestamo,nro_pago,fecha_venc,fecha_pago) VALUES(NEW.nro_prestamo, k, (SELECT date_add(NEW.fecha, INTERVAL k MONTH)),NULL);
	        SET k = k+1;
	    END WHILE;

END; !
	
    
 delimiter ; #reestablece ';' como delimitador de sentencias

GRANT EXECUTE ON banco.* TO 'atm'@'%';	
GRANT EXECUTE ON banco.* TO 'atm'@'%';	
