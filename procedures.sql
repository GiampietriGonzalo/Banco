#PROCEDURES
use banco;

delimiter !

CREATE PROCEDURE transferir(IN codCaja INT(5),IN monto DECIMAL(16,2), IN cajaA INT,IN cajaB INT)
                            
  BEGIN   
     #Declaro una variable local saldo_actual
	 DECLARE saldo_cajaA DECIMAL(16,2);
	 DECLARE saldo_cajaB DECIMAL(16,2);
	 DECLARE nuevoSaldoA DECIMAL(16,2);
	 DECLARE nuevoSaldoB DECIMAL(16,2);  
	 DECLARE lastIDTransaccion INT(10);
     DECLARE lastIDTransaccionCaja INT(5);
	 DECLARE numCliente INT(5);

     #Declaro variables locales para recuperar los errores 
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
		  SELECT saldo_cajaA;
		  SELECT saldo_cajaB;
		  set nuevoSaldoA=saldo_cajaA - monto;
		  set nuevoSaldoB=saldo_cajaB + monto;
			
          #Recupero el saldo de la cajaA en la variable saldo_actual_cajaA.
          #Al utilizar FOR UPDATE se indica que los datos involucrados en la
          #consulta van a ser actualizados luego.
          #De esta forma se obtiene un write_lock sobre estos datos, que se      
          #mantiene hasta que la trans. comete. Esto garantiza que nadie pueda
          #leer ni escribir el saldo de la cajaA hasta que la trans. comete.      	    
      

		  #Si el saldo actual de la cajaA es suficiente para realizar 
           #la transferencia, entonces actualizo el saldo de ambas cuentas 
	      IF saldo_cajaA >= monto THEN 	  
	       
	         UPDATE caja_ahorro SET saldo = nuevoSaldoA  WHERE nro_ca=cajaA;
	         UPDATE caja_ahorro SET saldo = nuevoSaldoB  WHERE nro_ca=cajaB;

 		   #HACER TRANSACCION-TRANSFERENCIA Y TRANSACCION-DEPOSITO
		   #TRANSFERENCIA
			 INSERT INTO transaccion(fecha,hora,monto) VALUES (curdate(),curtime(),monto);
             set lastIDTransaccion=LAST_INSERT_ID();
             INSERT INTO transaccion_por_caja(nro_trans,cod_caja) VALUES (lastIDTransaccion,codCaja);
             set lastIDTransaccionCaja=LAST_INSERT_ID();
			 SELECT nro_cliente INTO numCliente FROM cliente_ca WHERE nro_ca=cajaA;
			 #INSERT INTO transferencia(nro_trans,nro_cliente,origen,destino) VALUES (LAST_INSERT_ID(),numCliente,codCaja,cajaB); 

		   #DEPOSITO
			 INSERT INTO deposito VALUES (lastIDTransaccionCaja,cajaB);
	
             SELECT 'La transferencia se realizo con exito' AS resultado; 
			 SELECT * FROM transaccion WHERE nro_trans=lastIDTransaccion;
			 SELECT * FROM deposito WHERE nro_trans=lastIDTransaccionCaja;
			 SELECT * FROM transferencia_por_caja WHERE nro_trans=lastIDTransaccionCaja;
             SELECT nro_ca,monto FROM caja_ahorro WHERE nro_ca=cajaA;
             SELECT nro_ca,monto FROM caja_ahorro WHERE nro_ca=cajaB; 
	    
		  ELSE  
             SELECT 'Saldo insuficiente para realizar la transferencia' 
		        AS resultado;
	      END IF;  

	   ELSE  
            SELECT 'ERROR: Cuenta inexistente' 
		        AS resultado;  
	   END IF;  	 		
		
	 COMMIT;   #Comete la transacción  
 END; !

    
 delimiter ; #reestablece ';' como delimitador de sentencias
	
GRANT EXECUTE ON banco.* TO 'admin'@'localhost';
GRANT EXECUTE ON banco.* TO 'atm'@'%';	
