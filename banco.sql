# CREACION DE LA BASE DE DATOS

	CREATE DATABASE banco;
	
	USE banco;
	
	CREATE TABLE ciudad(
		
		cod_postal INT(4) UNSIGNED NOT NULL,
		nombre VARCHAR(20) NOT NULL,
	
		CONSTRAINT pk_ciudad
		PRIMARY KEY (cod_postal)
	
	) ENGINE=InnoDB;

	CREATE TABLE sucursal(
	
		nro_suc INT(3) UNSIGNED AUTO_INCREMENT NOT NULL,
		nombre VARCHAR(20) NOT NULL,
		direccion VARCHAR(20) NOT NULL,
		telefono VARCHAR(20) NOT NULL,
		horario VARCHAR(50) NOT NULL,
		cod_postal INT(4) UNSIGNED NOT NULL,
		
		CONSTRAINT pk_sucursal
		PRIMARY KEY (nro_suc),

		CONSTRAINT fk_sucursal_cod_postal
		FOREIGN KEY (cod_postal) REFERENCES ciudad (cod_postal)
		
	) ENGINE=InnoDB;
	
	CREATE TABLE empleado(
	
		legajo INT(4) UNSIGNED AUTO_INCREMENT NOT NULL,
		nombre VARCHAR(20) NOT NULL,
		apellido VARCHAR(20) NOT NULL,
		tipo_doc VARCHAR(20) NOT NULL,
		nro_doc INT(8) UNSIGNED NOT NULL,
		direccion VARCHAR(20) NOT NULL,
		telefono VARCHAR(20) NOT NULL,
		cargo VARCHAR(20) NOT NULL,
		password CHAR(32) NOT NULL,
		nro_suc INT(3) UNSIGNED NOT NULL,
		
		
		CONSTRAINT pk_empleado
		PRIMARY KEY (legajo),

		CONSTRAINT fk_empleado_nro_suc
		FOREIGN KEY (nro_suc) REFERENCES sucursal (nro_suc)
		
	) ENGINE=InnoDB;

	CREATE TABLE cliente(
	
		nro_cliente INT(5) UNSIGNED AUTO_INCREMENT NOT NULL,
		nombre VARCHAR(20) NOT NULL,
		apellido VARCHAR(20) NOT NULL,
		tipo_doc VARCHAR(20) NOT NULL,
		nro_doc INT(8) UNSIGNED NOT NULL,
		direccion VARCHAR(20) NOT NULL,
		telefono VARCHAR(20) NOT NULL,
		fecha_nac DATE NOT NULL,
	
		CONSTRAINT pk_cliente
		PRIMARY KEY (nro_cliente)
	
	) ENGINE=InnoDB;

	CREATE TABLE plazo_fijo(

		nro_plazo INT(8) UNSIGNED AUTO_INCREMENT NOT NULL,
		capital DECIMAL(16,2) UNSIGNED NOT NULL,
		tasa_interes DECIMAL(4,2) UNSIGNED NOT NULL,
		fecha_inicio DATE NOT NULL,
		fecha_fin DATE NOT NULL,
		interes DECIMAL(16,2) UNSIGNED NOT NULL,
		nro_suc INT(3) UNSIGNED NOT NULL,

		CONSTRAINT pk_plazo_fijo
		PRIMARY KEY (nro_plazo),

		CONSTRAINT fk_plazo_fijo_nro_suc
		FOREIGN KEY (nro_suc) REFERENCES sucursal (nro_suc)
		
	) ENGINE=InnoDB;

	CREATE TABLE tasa_plazo_fijo(
		
		periodo INT(3) UNSIGNED NOT NULL,
		monto_inf DECIMAL(16,2) UNSIGNED NOT NULL,
		monto_sup DECIMAL(16,2) UNSIGNED NOT NULL,
		tasa DECIMAL(4,2) UNSIGNED NOT NULL,
		
		CONSTRAINT pk_tasa
		PRIMARY KEY (periodo,monto_inf,monto_sup)
 
	) ENGINE=InnoDB;
	
	CREATE TABLE plazo_cliente(

		nro_plazo INT(8) UNSIGNED NOT NULL,
		nro_cliente INT(5) UNSIGNED NOT NULL,
		
		CONSTRAINT pk_plazo_cliente
		PRIMARY KEY (nro_plazo,nro_cliente),
		
		CONSTRAINT fk_plazo_cliente_nro_plazo 
		FOREIGN KEY (nro_plazo) REFERENCES plazo_fijo (nro_plazo),
		
		CONSTRAINT fk_plazo_cliente_nro_cliente 
		FOREIGN KEY (nro_cliente) REFERENCES cliente (nro_cliente)
	
	) ENGINE=InnoDB;

	CREATE TABLE prestamo(
    
		nro_prestamo INT(8) UNSIGNED  AUTO_INCREMENT NOT NULL,
		fecha DATE NOT NULL,
		cant_meses INT(2) UNSIGNED NOT NULL,
		monto DECIMAL(10,2) UNSIGNED NOT NULL,
		tasa_interes DECIMAL(4,2) UNSIGNED NOT NULL,
		interes DECIMAL(9,2) UNSIGNED NOT NULL,
		valor_cuota DECIMAL(9,2) UNSIGNED NOT NULL,
		legajo INT(4) UNSIGNED NOT NULL,
		nro_cliente INT(5) UNSIGNED NOT NULL,
		
		CONSTRAINT pk_prestamo
		PRIMARY KEY (nro_prestamo),

		CONSTRAINT fk_prestamo_legajo
		FOREIGN KEY (legajo) REFERENCES empleado (legajo),

		CONSTRAINT fk_prestamo_nro_cliente
		FOREIGN KEY (nro_cliente) REFERENCES cliente (nro_cliente)
	
	) ENGINE=InnoDB;
	
	CREATE TABLE pago(
	
		nro_prestamo INT(8) UNSIGNED AUTO_INCREMENT NOT NULL,
		nro_pago INT(2) UNSIGNED NOT NULL,
		fecha_venc DATE NOT NULL,
		fecha_pago DATE,
		
		CONSTRAINT pk_pago
		PRIMARY KEY (nro_prestamo,nro_pago),
		
		CONSTRAINT fk_pago_nro_prestamo 
		FOREIGN KEY (nro_prestamo) REFERENCES prestamo (nro_prestamo)
	
	) ENGINE=InnoDB;
	
	CREATE TABLE tasa_prestamo(
	
		periodo INT(3) UNSIGNED NOT NULL,
		monto_inf DECIMAL(10,2) UNSIGNED NOT NULL,
		monto_sup DECIMAL(10,2) UNSIGNED NOT NULL,
		tasa DECIMAL(4,2) UNSIGNED NOT NULL,
		
		CONSTRAINT pk_tasa_prestamo
		PRIMARY KEY (periodo,monto_inf,monto_sup)
		
		
	) ENGINE=InnoDB;
	
	CREATE TABLE caja_ahorro(
	
		nro_ca INT(8) UNSIGNED AUTO_INCREMENT NOT NULL,
		cbu BIGINT UNSIGNED NOT NULL,
		saldo DECIMAL(16,2) UNSIGNED NOT NULL,
		
		CONSTRAINT pk_caja_ahorro
		PRIMARY KEY (nro_ca)

	) ENGINE=InnoDB;
	
	CREATE TABLE cliente_ca(
	
		nro_cliente INT(5) UNSIGNED NOT NULL,
		nro_ca INT(8) UNSIGNED NOT NULL,
		
		CONSTRAINT pk_clienta_ca
		PRIMARY KEY (nro_cliente,nro_ca),
		
		CONSTRAINT fk_cliente_ca_nro_cliente 
		FOREIGN KEY (nro_cliente) REFERENCES cliente (nro_cliente),
		
		CONSTRAINT fk_cliente_ca_nro_ca
		FOREIGN KEY (nro_ca) REFERENCES caja_ahorro (nro_ca)
	
	) ENGINE=InnoDB;
	
	CREATE TABLE tarjeta(
	
		nro_tarjeta BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
		pin VARCHAR(32) NOT NULL,
		cvt VARCHAR(32) NOT NULL,
		fecha_venc DATE NOT NULL,
		nro_cliente INT(5) UNSIGNED NOT NULL,
		nro_ca INT(18) UNSIGNED NOT NULL,
		
		CONSTRAINT pk_tarjeta
		PRIMARY KEY (nro_tarjeta),

		CONSTRAINT fk_tarjeta_nro_ca_nro_cliente
		FOREIGN KEY (nro_cliente,nro_ca) REFERENCES cliente_ca (nro_cliente,nro_ca)

		
	) ENGINE=InnoDB;
	
	CREATE TABLE caja(
	
		cod_caja INT(5) UNSIGNED AUTO_INCREMENT NOT NULL,
		
		CONSTRAINT pk_caja
		PRIMARY KEY (cod_caja)
		
	) ENGINE=InnoDB;
	
	CREATE TABLE ventanilla(
	
		cod_caja INT(5) UNSIGNED NOT NULL,
		nro_suc INT(3) UNSIGNED NOT NULL,

		CONSTRAINT pk_ventanilla
		PRIMARY KEY (cod_caja),

		CONSTRAINT fk_ventanilla_cod_caja
		FOREIGN KEY (cod_caja) REFERENCES caja (cod_caja),

		CONSTRAINT fk_ventanilla_nro_suc
		FOREIGN KEY (nro_suc) REFERENCES sucursal (nro_suc)

		
	) ENGINE=InnoDB;
	
	CREATE TABLE atm(
	
		cod_caja INT(5) UNSIGNED NOT NULL,
		cod_postal INT(4) UNSIGNED NOT NULL,
		direccion VARCHAR(20) NOT NULL,
	
		CONSTRAINT pk_atm
		PRIMARY KEY (cod_caja),

		CONSTRAINT fk_amt_cod_caja
		FOREIGN KEY (cod_caja) REFERENCES caja (cod_caja),

		CONSTRAINT fk_atm_cod_postal
		FOREIGN KEY (cod_postal) REFERENCES ciudad (cod_postal)


	) ENGINE=InnoDB;
	
	CREATE TABLE transaccion(
	
		nro_trans INT(10) UNSIGNED AUTO_INCREMENT NOT NULL,
		fecha DATE NOT NULL,
		hora TIME NOT NULL,
		monto DECIMAL(16,2) UNSIGNED NOT NULL,
		
		CONSTRAINT pk_transaccion
		PRIMARY KEY (nro_trans)
	
	) ENGINE=InnoDB;
	
	CREATE TABLE debito(
	
		nro_trans INT(10) UNSIGNED NOT NULL,
		descripcion TEXT,
		nro_cliente INT(5) UNSIGNED NOT NULL,
		nro_ca INT(8) UNSIGNED NOT NULL,

		CONSTRAINT pk_debito
		PRIMARY KEY (nro_trans),

		CONSTRAINT fk_debito_nro_trans
		FOREIGN KEY (nro_trans) REFERENCES transaccion (nro_trans),

		CONSTRAINT fk_debito_nro_ca_nro_cliente
		FOREIGN KEY (nro_cliente,nro_ca) REFERENCES cliente_ca (nro_cliente,nro_ca)

	) ENGINE=InnoDB;
	
	CREATE TABLE transaccion_por_caja(
	
		nro_trans INT(10) UNSIGNED NOT NULL,
		cod_caja INT(5) UNSIGNED NOT NULL,

		CONSTRAINT pk_transaccion_caja
		PRIMARY KEY (nro_trans),

		CONSTRAINT fk_transaccion_nro_trans
		FOREIGN KEY (nro_trans) REFERENCES transaccion (nro_trans),

		CONSTRAINT fk_transaccion_cod_caja
		FOREIGN KEY (cod_caja) REFERENCES caja (cod_caja)
	
	
	) ENGINE=InnoDB;
	
	CREATE TABLE deposito(
	
		nro_trans INT(10) UNSIGNED NOT NULL,
		nro_ca INT(8) UNSIGNED NOT NULL,

		CONSTRAINT pk_deposito
		PRIMARY KEY (nro_trans),

		CONSTRAINT fk_deposito_nro_trans
		FOREIGN KEY (nro_trans) REFERENCES transaccion_por_caja (nro_trans),

		CONSTRAINT fk_deposito_nro_ca
		FOREIGN KEY (nro_ca) REFERENCES caja_ahorro (nro_ca)	
	 
	) ENGINE=InnoDB;
	
	CREATE TABLE extraccion(
	
		nro_trans INT(10) UNSIGNED NOT NULL,
		nro_cliente INT(5) UNSIGNED NOT NULL,
		nro_ca INT(8) UNSIGNED NOT NULL,

		CONSTRAINT pk_extraccion
		PRIMARY KEY (nro_trans),

		CONSTRAINT fk_extraccion_nro_trans
		FOREIGN KEY (nro_trans) REFERENCES transaccion_por_caja (nro_trans),

		CONSTRAINT fk_extraccion_nro_ca_nro_cliente
		FOREIGN KEY (nro_cliente,nro_ca) REFERENCES cliente_ca (nro_cliente,nro_ca)
	
	) ENGINE=InnoDB;
	
	CREATE TABLE transferencia(
	
		nro_trans INT(10) UNSIGNED NOT NULL,
		nro_cliente INT(5) UNSIGNED NOT NULL,
		origen INT(8) UNSIGNED NOT NULL,
		destino INT(8) UNSIGNED NOT NULL,
		
		CONSTRAINT pk_transferencia
		PRIMARY KEY (nro_trans),

		CONSTRAINT fk_transferencia_nro_trans
		FOREIGN KEY (nro_trans) REFERENCES transaccion_por_caja (nro_trans),

		CONSTRAINT fk_transferencia_nro_cliente_origen
		FOREIGN KEY (nro_cliente,origen) REFERENCES cliente_ca (nro_cliente,nro_ca),

		CONSTRAINT fk_transferencia_destino
		FOREIGN KEY (destino) REFERENCES caja_ahorro (nro_ca)
		
	) ENGINE=InnoDB;

# CREACION DE VISTAS	

	#CREAR VISTAS PARA USUARIO atm	
	CREATE VIEW banco.datos_deposito AS
	SELECT nro_ca, saldo, nro_trans, fecha, hora, monto, cod_caja, 'Deposito' AS tipo 
	FROM ((deposito NATURAL JOIN transaccion) NATURAL JOIN transaccion_por_caja) NATURAL JOIN caja_ahorro;
    
    CREATE VIEW banco.datos_extraccion AS
    SELECT nro_ca, saldo, nro_trans, fecha, hora, monto, cod_caja, nro_cliente, tipo_doc, nro_doc, nombre, apellido, 'Extraccion' AS tipo
    FROM (((extraccion NATURAL JOIN transaccion) NATURAL JOIN cliente) NATURAL JOIN transaccion_por_caja) NATURAL JOIN caja_ahorro;
    
    CREATE VIEW banco.datos_transferencia AS
    SELECT nro_ca, saldo, nro_trans, fecha, hora, monto, cod_caja, destino, nro_cliente, tipo_doc, nro_doc, nombre, apellido, 'Transferencia' AS tipo
	FROM (((transferencia NATURAL JOIN transaccion) NATURAL JOIN cliente) NATURAL JOIN transaccion_por_caja) JOIN caja_ahorro ON transferencia.origen=caja_ahorro.nro_ca;
	
	CREATE VIEW banco.datos_debito AS
	SELECT nro_ca, saldo, nro_trans, fecha, hora, monto, nro_cliente, tipo_doc, nro_doc, nombre, apellido, 'Debito' AS tipo
	FROM ((debito NATURAL JOIN transaccion) NATURAL JOIN cliente) NATURAL JOIN caja_ahorro;
    
    CREATE VIEW banco.trans_cajas_ahorro AS
	SELECT nro_ca, saldo, nro_trans, fecha, hora, monto, tipo, cod_caja, '-' AS destino, '-' AS nro_cliente, '-' AS tipo_doc, '-' AS nro_doc, '-' AS nombre, '-' AS apellido from banco.datos_deposito
	UNION
	SELECT nro_ca, saldo, nro_trans, fecha, hora, monto, tipo, cod_caja, '-' AS destino, nro_cliente, tipo_doc, nro_doc, nombre, apellido from banco.datos_extraccion
	UNION
	SELECT nro_ca, saldo, nro_trans, fecha, hora, monto, tipo, cod_caja, destino, nro_cliente, tipo_doc, nro_doc, nombre, apellido from banco.datos_transferencia
	UNION
	SELECT nro_ca, saldo, nro_trans, fecha, hora, monto, tipo, '-' AS cod_caja, '-' AS destino, nro_cliente, tipo_doc, nro_doc, nombre, apellido from banco.datos_debito;


# CREACION DE USUARIOS

# admin:

	CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
	GRANT ALL PRIVILEGES ON banco.* TO 'admin'@'localhost' WITH GRANT OPTION;

# empleado:

	CREATE USER empleado@'%' IDENTIFIED BY 'empleado'; 
	
	GRANT SELECT ON banco.empleado TO empleado@'%';
	GRANT SELECT ON banco.sucursal TO empleado@'%';
	GRANT SELECT ON banco.tasa_plazo_fijo TO empleado@'%';
	GRANT SELECT ON banco.tasa_prestamo TO empleado@'%';
	GRANT SELECT ON banco.prestamo TO empleado@'%';
	GRANT SELECT ON banco.plazo_cliente TO empleado@'%';
	GRANT SELECT ON banco.caja_ahorro TO empleado@'%';
	GRANT SELECT ON banco.tarjeta TO empleado@'%';
	GRANT SELECT ON banco.cliente_ca TO empleado@'%';
	GRANT SELECT ON banco.cliente TO empleado@'%';
	GRANT SELECT ON banco.pago TO empleado@'%';

	GRANT INSERT ON banco.prestamo TO empleado@'%';
	GRANT INSERT ON banco.plazo_fijo TO empleado@'%';	
	GRANT INSERT ON banco.plazo_cliente TO empleado@'%';
	GRANT INSERT ON banco.caja_ahorro TO empleado@'%';
	GRANT INSERT ON banco.tarjeta TO empleado@'%';
	GRANT INSERT ON banco.cliente_ca TO empleado@'%';
	GRANT INSERT ON banco.cliente TO empleado@'%';
	GRANT INSERT ON banco.pago TO empleado@'%';

	GRANT UPDATE ON banco.cliente_ca TO empleado@'%';
	GRANT UPDATE ON banco.cliente TO empleado@'%';
	GRANT UPDATE ON banco.pago TO empleado@'%';

# atm:

	CREATE USER atm@'%' IDENTIFIED BY 'atm';
    
	GRANT SELECT ON banco.trans_cajas_ahorro TO atm@'%';
    GRANT SELECT ON banco.tarjeta TO atm@'%';
    GRANT UPDATE ON banco.tarjeta TO atm@'%';


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


