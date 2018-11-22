USE banco;

DELETE FROM debito;
DELETE FROM deposito;
DELETE FROM transferencia;
DELETE FROM extraccion;
DELETE FROM transaccion_por_caja;
DELETE FROM transaccion;
DELETE FROM atm;
DELETE FROM ventanilla;
DELETE FROM caja;
DELETE FROM tarjeta;
DELETE FROM cliente_CA;
DELETE FROM caja_ahorro;
DELETE FROM tasa_prestamo;
DELETE FROM pago;
DELETE FROM prestamo;
DELETE FROM plazo_cliente;
DELETE FROM tasa_plazo_fijo;
DELETE FROM plazo_fijo;
DELETE FROM cliente;
DELETE FROM empleado;
DELETE FROM sucursal;
DELETE FROM ciudad;


#................................................................................................
# 3 ciudades

INSERT INTO ciudad(cod_postal,Nombre) VALUES (8000, 'Bahia Blanca'); 
INSERT INTO ciudad(cod_postal,Nombre) VALUES (1000, 'Buenos Aires'); 
INSERT INTO ciudad(cod_postal,Nombre) VALUES (2000, 'Mar del Plata'); 

#................................................................................................
# 3 Sucursales de Bahia Blanca

 INSERT INTO sucursal(nro_suc, nombre, direccion, telefono, horario, cod_postal)
 VALUES (1,'Suc1-BB', 'Dir Suc1-BB', '0291-4540001', 'lu. a vi. de 10 a 15 hs.', 8000); 
INSERT INTO sucursal(nro_suc, nombre, direccion, telefono, horario, cod_postal) 
VALUES (2,'Suc2-BB', 'Dir Suc2-BB', '0291-4540002', 'lu. a vi. de 10 a 15 hs.', 8000); 
INSERT INTO sucursal(nro_suc, nombre, direccion, telefono, horario, cod_postal) 
VALUES (3,'Suc3-BB', 'Dir Suc3-BB', '0291-4540003', 'lu. a vi. de 10 a 15 hs.', 8000); 

# 2 Sucursales de Buenos Aires
INSERT INTO sucursal(nro_suc, nombre, direccion, telefono, horario, cod_postal) 
VALUES (4,'Suc4-BA', 'Dir Suc4-BA', '011-4540004', 'lu. a vi. de 10 a 15 hs.', 1000); 
INSERT INTO sucursal(nro_suc, nombre, direccion, telefono, horario, cod_postal) 
VALUES (5,'Suc5-BA', 'Dir Suc5-BA', '011-4540005', 'lu. a vi. de 10 a 15 hs.', 1000); 

# 2 Sucursales de Mardel Plata
INSERT INTO sucursal(nro_suc, nombre, direccion, telefono, horario, cod_postal) 
VALUES (6,'Suc6-MDP', 'Dir Suc6-MDP', '0223-4540006', 'lu. a vi. de 10 a 15 hs.', 2000); 
INSERT INTO sucursal(nro_suc, nombre, direccion, telefono, horario, cod_postal) 
VALUES (7,'Suc7-MDP', 'Dir Suc7-MDP', '0223-4540007', 'lu. a vi. de 10 a 15 hs.', 2000); 

#................................................................................................
# Empleados: del 1 al 7, uno por sucursal

INSERT INTO empleado(legajo, nombre, apellido, tipo_doc, nro_doc, direccion, telefono, cargo, password, nro_suc) 
VALUES (1, 'Nombre_Emp1', 'Apellido_Emp1', 'DNI', 11, 'Dir_Emp1', '0291-4540011', 'Gerente', md5('emp1'), 1);
INSERT INTO empleado(legajo, nombre, apellido, tipo_doc, nro_doc, direccion, telefono, cargo, password, nro_suc) 
VALUES (2, 'Nombre_Emp2', 'Apellido_Emp2', 'DNI', 22, 'Dir_Emp2', '0291-4540012', 'Cajero', md5('emp2'), 2);
INSERT INTO empleado(legajo, nombre, apellido, tipo_doc, nro_doc, direccion, telefono, cargo, password, nro_suc) 
VALUES (3, 'Nombre_Emp3', 'Apellido_Emp3', 'DNI', 33, 'Dir_Emp3', '0291-4540013', 'Oficial de prestamos', md5('emp3'), 3);
INSERT INTO empleado(legajo, nombre, apellido, tipo_doc, nro_doc, direccion, telefono, cargo, password, nro_suc) 
VALUES (4, 'Nombre_Emp4', 'Apellido_Emp4', 'DNI', 44, 'Dir_Emp4', '011-4540014', 'Gerente', md5('emp4'), 4);
INSERT INTO empleado(legajo, nombre, apellido, tipo_doc, nro_doc, direccion, telefono, cargo, password, nro_suc) 
VALUES (5, 'Nombre_Emp5', 'Apellido_Emp5', 'DNI', 55, 'Dir_Emp5', '011-4540015', 'Cajero', md5('emp5'), 5);
INSERT INTO empleado(legajo, nombre, apellido, tipo_doc, nro_doc, direccion, telefono, cargo, password, nro_suc) 
VALUES (6, 'Nombre_Emp6', 'Apellido_Emp6', 'DNI', 66, 'Dir_Emp6', '0223-4540016', 'Oficial de prestamos',md5('emp6'), 6);
INSERT INTO empleado(legajo, nombre, apellido, tipo_doc, nro_doc, direccion, telefono, cargo, password, nro_suc) 
VALUES (7, 'Nombre_Emp7', 'Apellido_Emp7', 'DNI', 77, 'Dir_Emp7', '0223-4540017', 'Oficial de prestamos',md5('emp7'), 7);

#................................................................................................
# Clientes: del 1 al 7 (nro_doc = nro_cliente)
 
INSERT INTO cliente(nro_cliente, nombre, apellido, tipo_doc, nro_doc, direccion, telefono, fecha_nac) 
VALUES (1, 'Nombre_Cli1', 'Apellido_Cli1', 'DNI', 1, 'Dir_Cli1', '0291-4540021', '1980-05-01');
INSERT INTO cliente(nro_cliente, nombre, apellido, tipo_doc, nro_doc, direccion, telefono, fecha_nac) 
VALUES (2, 'Nombre_Cli2', 'Apellido_Cli2', 'DNI', 2, 'Dir_Cli2', '0291-4540022', '1980-05-02');
INSERT INTO cliente(nro_cliente, nombre, apellido, tipo_doc, nro_doc, direccion, telefono, fecha_nac) 
VALUES (3, 'Nombre_Cli3', 'Apellido_Cli3', 'DNI', 3, 'Dir_Cli3', '0291-4540023', '1980-05-03');
INSERT INTO cliente(nro_cliente, nombre, apellido, tipo_doc, nro_doc, direccion, telefono, fecha_nac) 
VALUES (4, 'Nombre_Cli4', 'Apellido_Cli4', 'DNI', 4, 'Dir_Cli4', '0291-4540024', '1980-05-04');
INSERT INTO cliente(nro_cliente, nombre, apellido, tipo_doc, nro_doc, direccion, telefono, fecha_nac) 
VALUES (5, 'Nombre_Cli5', 'Apellido_Cli5', 'DNI', 5, 'Dir_Cli5', '0291-4540025', '1980-05-05');
INSERT INTO cliente(nro_cliente, nombre, apellido, tipo_doc, nro_doc, direccion, telefono, fecha_nac) 
VALUES (6, 'Nombre_Cli6', 'Apellido_Cli6', 'DNI', 6, 'Dir_Cli6', '0291-4540026', '1980-05-06');
INSERT INTO cliente(nro_cliente, nombre, apellido, tipo_doc, nro_doc, direccion, telefono, fecha_nac) 
VALUES (7, 'Nombre_Cli7', 'Apellido_Cli7', 'DNI', 7,'Dir_Cli7', '0291-4540027', '1980-05-07');


#................................................................................................
# Prestamos 
 
# prestamos del cliente 1 (y empleado 1)
INSERT INTO prestamo(nro_prestamo, fecha, cant_meses, monto, tasa_interes, interes, valor_cuota, legajo, nro_cliente) 
VALUES (1, '2017-6-6', 6, 2000, 17, 0, 0, 1, 1);  # esta pago
INSERT INTO prestamo(nro_prestamo, fecha, cant_meses, monto, tasa_interes, interes, valor_cuota,legajo, nro_cliente) 
VALUES (2, '2018-4-4', 6, 9000, 20, 0, 0, 1, 1);  # moroso

# prestamos del cliente 2 (y empleado 2)
INSERT INTO prestamo(nro_prestamo, fecha, cant_meses, monto, tasa_interes, interes, valor_cuota,legajo, nro_cliente)
VALUES (3, '2017-4-4', 6, 5000, 20, 0, 0, 2, 2);   # esta pago
INSERT INTO prestamo(nro_prestamo, fecha, cant_meses, monto, tasa_interes, interes, valor_cuota,legajo, nro_cliente)
VALUES (4, '2018-4-5', 6, 20000, 24, 0, 0, 2, 2); # moroso

# prestamos del cliente 3 (y empleado 3)
INSERT INTO prestamo(nro_prestamo, fecha, cant_meses, monto, tasa_interes,interes, valor_cuota, legajo, nro_cliente)
VALUES (5, '2018-1-7', 6, 18000, 24, 0, 0, 3, 3); # moroso

# prestamos del cliente 4 (y empleado 4)
INSERT INTO prestamo(nro_prestamo, fecha, cant_meses, monto, tasa_interes, interes, valor_cuota, legajo, nro_cliente)
VALUES (6, '2016-9-9', 6, 20000, 24, 0, 0, 4, 4); # esta pagado
INSERT INTO prestamo(nro_prestamo, fecha, cant_meses, monto, tasa_interes, interes, valor_cuota, legajo, nro_cliente)
VALUES (7, '2018-9-20', 6, 1000, 17, 0, 0, 4, 4); # cuotas impagas, pero al dia


UPDATE prestamo set interes=(monto * tasa_interes * cant_meses)/1200; 

UPDATE prestamo set valor_cuota=(monto+interes)/cant_meses;


#................................................................................................
# Pagos

# del prestamo 1 (esta pago)
UPDATE pago SET fecha_venc='2017-7-6', fecha_pago='2017-7-4'  WHERE nro_prestamo=1 AND nro_pago=1; 
UPDATE pago SET fecha_venc='2017-8-6', fecha_pago='2017-8-4'  WHERE nro_prestamo=1 AND nro_pago=2;
UPDATE pago SET fecha_venc='2017-9-6', fecha_pago='2017-9-4'  WHERE nro_prestamo=1 AND nro_pago=3;
UPDATE pago SET fecha_venc='2017-10-6', fecha_pago='2017-10-4' WHERE nro_prestamo=1 AND nro_pago=4;
UPDATE pago SET fecha_venc='2017-11-6', fecha_pago='2017-11-4' WHERE nro_prestamo=1 AND nro_pago=5;
UPDATE pago SET fecha_venc='2017-12-6', fecha_pago='2017-12-4' WHERE nro_prestamo=1 AND nro_pago=6; 

# del prestamo 2 (moroso: tiene la cuota 4, 5 y 6 cuotas vencidas e impagas)
UPDATE pago SET fecha_venc='2018-5-4', fecha_pago='2018-5-4' WHERE nro_prestamo=2 AND nro_pago=1;   
UPDATE pago SET fecha_venc='2018-6-4', fecha_pago='2018-6-4' WHERE nro_prestamo=2 AND nro_pago=2;   
UPDATE pago SET fecha_venc='2018-7-4', fecha_pago='2018-7-4' WHERE nro_prestamo=2 AND nro_pago=3;   
UPDATE pago SET fecha_venc='2018-8-4'  WHERE nro_prestamo=2 AND nro_pago=4;   
UPDATE pago SET fecha_venc='2018-9-4'  WHERE nro_prestamo=2 AND nro_pago=5;   
UPDATE pago SET fecha_venc='2018-10-4' WHERE nro_prestamo=2 AND nro_pago=6;   

# del prestamo 3 (esta pago)
UPDATE pago SET fecha_venc='2017-5-4', fecha_pago='2017-5-4' WHERE nro_prestamo=3 AND nro_pago=1;   
UPDATE pago SET fecha_venc='2017-6-4', fecha_pago='2017-6-4' WHERE nro_prestamo=3 AND nro_pago=2;   
UPDATE pago SET fecha_venc='2017-7-4', fecha_pago='2017-7-4' WHERE nro_prestamo=3 AND nro_pago=3;   
UPDATE pago SET fecha_venc='2017-8-4', fecha_pago='2017-8-6' WHERE nro_prestamo=3 AND nro_pago=4;    
UPDATE pago SET fecha_venc='2017-9-4', fecha_pago='2017-9-6' WHERE nro_prestamo=3 AND nro_pago=5;    
UPDATE pago SET fecha_venc='2017-10-4', fecha_pago='2017-10-6' WHERE nro_prestamo=3 AND nro_pago=6;    

# del prestamo 4 (moroso: tiene la cuota 5 y 6 vencidas e impagas)
UPDATE pago SET fecha_venc='2018-5-5', fecha_pago='2018-5-3' WHERE nro_prestamo=4 AND nro_pago=1;    
UPDATE pago SET fecha_venc='2018-6-5', fecha_pago='2018-6-3' WHERE nro_prestamo=4 AND nro_pago=2;    
UPDATE pago SET fecha_venc='2018-7-5', fecha_pago='2018-7-3' WHERE nro_prestamo=4 AND nro_pago=3;     
UPDATE pago SET fecha_venc='2018-8-5', fecha_pago='2018-8-3' WHERE nro_prestamo=4 AND nro_pago=4;     
UPDATE pago SET fecha_venc='2018-9-5' WHERE nro_prestamo=4 AND nro_pago=5;     
UPDATE pago SET fecha_venc='2018-10-5' WHERE nro_prestamo=4 AND nro_pago=6;     

# del prestamo 5 (moroso: tiene las cuotas 2 a 6 vencidas e impagas)
UPDATE pago SET fecha_venc='2018-2-7', fecha_pago='2018-2-7' WHERE nro_prestamo=5 AND nro_pago=1;     
UPDATE pago SET fecha_venc='2018-3-7' WHERE nro_prestamo=5 AND nro_pago=2;     
UPDATE pago SET fecha_venc='2018-4-7' WHERE nro_prestamo=5 AND nro_pago=3;     
UPDATE pago SET fecha_venc='2018-5-7' WHERE nro_prestamo=5 AND nro_pago=4;     
UPDATE pago SET fecha_venc='2018-6-7' WHERE nro_prestamo=5 AND nro_pago=5;     
UPDATE pago SET fecha_venc='2018-7-7' WHERE nro_prestamo=5 AND nro_pago=6;     

# del prestamo 6 (esta pago)
UPDATE pago SET fecha_venc='2016-10-9', fecha_pago='2016-10-5' WHERE nro_prestamo=6 AND nro_pago=1;     
UPDATE pago SET fecha_venc='2016-11-9', fecha_pago='2016-11-5' WHERE nro_prestamo=6 AND nro_pago=2;     
UPDATE pago SET fecha_venc='2016-12-9', fecha_pago='2016-12-5' WHERE nro_prestamo=6 AND nro_pago=3;      
UPDATE pago SET fecha_venc='2017-1-9', fecha_pago='2017-1-5'  WHERE nro_prestamo=6 AND nro_pago=4;      
UPDATE pago SET fecha_venc='2017-2-9', fecha_pago='2017-2-5'  WHERE nro_prestamo=6 AND nro_pago=5;      
UPDATE pago SET fecha_venc='2017-3-9', fecha_pago='2017-3-5'  WHERE nro_prestamo=6 AND nro_pago=6;      

# del prestamo 7 (tiene cuotas impagas, pero esta al dia)
UPDATE pago SET fecha_venc='2018-10-20', fecha_pago='2018-10-15' WHERE nro_prestamo=7 AND nro_pago=1;      
UPDATE pago SET fecha_venc='2018-11-20', fecha_pago='2018-11-15' WHERE nro_prestamo=7 AND nro_pago=2;      
UPDATE pago SET fecha_venc='2018-12-20', fecha_pago='2018-11-15' WHERE nro_prestamo=7 AND nro_pago=3;      
UPDATE pago SET fecha_venc='2019-1-20' WHERE nro_prestamo=7 AND nro_pago=4;      
UPDATE pago SET fecha_venc='2019-2-20' WHERE nro_prestamo=7 AND nro_pago=5;      
UPDATE pago SET fecha_venc='2019-3-20' WHERE nro_prestamo=7 AND nro_pago=6;      

#...............................................................................
# Tasas prestamos
 
INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (6,0,2999,17);  
INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (6,3000,9999,20);  
INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (6,10000,20000,24);  

INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (12,0,2999,18.5);  
INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (12,3000,9999,21.5);  
INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (12,10000,20000,25.5);  

# periodo nuevo de 18 meses, no estaba en el enunciado del proyecto 1
INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (18,0,2999,19);  
INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (18,3000,9999,22);  
INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (18,10000,20000,26);  

INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (24,0,2999,20);  
INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (24,3000,9999,23);  
INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (24,10000,20000,27);  

INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (60,0,2999,25);  
INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (60,3000,9999,28);  
INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (60,10000,20000,32);  

INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (120,0,2999,30);  
INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (120,3000,9999,33);  
INSERT INTO tasa_prestamo(periodo, monto_inf, monto_sup, tasa) VALUES (120,10000,20000,37);  

#...............................................................................
# Cajas de ahorro
 
INSERT INTO caja_ahorro(nro_ca,CBU, saldo) VALUES (1,11,1000);  
INSERT INTO caja_ahorro(nro_ca,CBU, saldo) VALUES (2,12,2000);  
INSERT INTO caja_ahorro(nro_ca,CBU, saldo) VALUES (3,13,3000);  
INSERT INTO caja_ahorro(nro_ca,CBU, saldo) VALUES (4,14,4000); 


#...............................................................................
# Cliente_CA
# Caja 1 con cliente 1 y 7
# Caja 2 con cliente 2 
# Caja 3 con cliente 3 
# Caja 4 con cliente 4,5,6 y 7
 
INSERT INTO Cliente_CA(nro_cliente, nro_ca) VALUES (1,1);  
INSERT INTO Cliente_CA(nro_cliente, nro_ca) VALUES (2,2);  
INSERT INTO Cliente_CA(nro_cliente, nro_ca) VALUES (3,3);  
INSERT INTO Cliente_CA(nro_cliente, nro_ca) VALUES (4,4);  
INSERT INTO Cliente_CA(nro_cliente, nro_ca) VALUES (5,4);  
INSERT INTO Cliente_CA(nro_cliente, nro_ca) VALUES (6,4);  
INSERT INTO Cliente_CA(nro_cliente, nro_ca) VALUES (7,4);  
INSERT INTO Cliente_CA(nro_cliente, nro_ca) VALUES (7,1);  
 
#...............................................................................
# Tarjetas 
# El pin de la tarjeta i es: iiii 
# La tarjeta 1 esta asociada al par caja-cliente (1,1)
# La tarjeta 2 esta asociada al par caja-cliente (2,2)
# La tarjeta 3 esta asociada al par caja-cliente (3,3)
# La tarjeta 4 esta asociada al par caja-cliente (4,4)
# La tarjeta 5 esta asociada al par caja-cliente (5,4)
# La tarjeta 6 esta asociada al par caja-cliente (6,4)
# La tarjeta 7 esta asociada al par caja-cliente (7,4)
# La tarjeta 8 esta asociada al par caja-cliente (7,1)
 
INSERT INTO tarjeta(nro_tarjeta, PIN, CVT, fecha_venc, nro_cliente, nro_ca)
VALUES(1,md5('1111'),md5('111'),'2030-4-16',1,1);
INSERT INTO tarjeta(nro_tarjeta, PIN, CVT, fecha_venc, nro_cliente, nro_ca)
VALUES(2,md5('2222'),md5('222'),'2030-4-16',2,2);
INSERT INTO tarjeta(nro_tarjeta, PIN, CVT, fecha_venc, nro_cliente, nro_ca)
VALUES(3,md5('3333'),md5('333'),'2030-4-16',3,3);
INSERT INTO tarjeta(nro_tarjeta, PIN, CVT, fecha_venc, nro_cliente, nro_ca)
VALUES(4,md5('4444'),md5('444'),'2030-4-16',4,4);
INSERT INTO tarjeta(nro_tarjeta, PIN, CVT, fecha_venc, nro_cliente, nro_ca)
VALUES(5,md5('5555'),md5('555'),'2030-4-16',5,4);
INSERT INTO tarjeta(nro_tarjeta, PIN, CVT, fecha_venc, nro_cliente, nro_ca)
 VALUES(6,md5('6666'),md5('666'),'2030-4-16',6,4);
INSERT INTO tarjeta(nro_tarjeta, PIN, CVT, fecha_venc, nro_cliente, nro_ca)
 VALUES(7,md5('7777'),md5('777'),'2030-4-16',7,4);
INSERT INTO tarjeta(nro_tarjeta, PIN, CVT, fecha_venc, nro_cliente, nro_ca)
 VALUES(8,md5('8888'),md5('888'),'2030-4-16',7,1);


#...............................................................................
# cajas

# 7 Ventanillas, una por sucursal
INSERT INTO caja VALUES(1);
INSERT INTO caja VALUES(2);
INSERT INTO caja VALUES(3);
INSERT INTO caja VALUES(4);
INSERT INTO caja VALUES(5);
INSERT INTO caja VALUES(6);
INSERT INTO caja VALUES(7);

# 4 ATM
INSERT INTO caja VALUES(10);
INSERT INTO caja VALUES(11);
INSERT INTO caja VALUES(12);
INSERT INTO caja VALUES(13);
INSERT INTO caja VALUES(100);

#...............................................................................
# 7 Ventanillas, una por sucursal
 
INSERT INTO ventanilla(cod_caja, nro_suc) VALUES(1,1);
INSERT INTO ventanilla(cod_caja, nro_suc) VALUES(2,2);
INSERT INTO ventanilla(cod_caja, nro_suc) VALUES(3,3);
INSERT INTO ventanilla(cod_caja, nro_suc) VALUES(4,4);
INSERT INTO ventanilla(cod_caja, nro_suc) VALUES(5,5);
INSERT INTO ventanilla(cod_caja, nro_suc) VALUES(6,6);
INSERT INTO ventanilla(cod_caja, nro_suc) VALUES(7,7);

#...............................................................................
# 4 atm
 
# 3 Cajeros en Bahia
INSERT INTO ATM(cod_caja, cod_postal, direccion) VALUES(10,8000,'Dir ATM 10-BB');
INSERT INTO ATM(cod_caja, cod_postal, direccion) VALUES(11,8000,'Dir ATM 11-BB');
INSERT INTO ATM(cod_caja, cod_postal, direccion) VALUES(100,8000,'Dir ATM 100-BB');

# 1 Cajero en Bs. As.
INSERT INTO ATM(cod_caja, cod_postal, direccion) VALUES(12,1000,'Dir ATM 12-BA');
# 1 Cajero en Mar Del Plata
INSERT INTO ATM(cod_caja, cod_postal, direccion) VALUES(13,2000,'Dir ATM 13-MDP');



#...............................................................................
# transacciones

# transacciones de la caja de ahorro 1: 
# números del 1001 al 1016, el número coincide con la fecha: 1001 = 10-01 (mmdd)
# los montos son de la forma:
#  XX.50 para los débitos
#  100X para los depósitos
#  10X para las extracciones
#  50X para las transferencias
   
  
INSERT INTO transaccion(nro_trans, fecha, hora, monto)
VALUES(1001,'2018-10-1','13:30:00',40.50); 
INSERT INTO debito(nro_trans, descripcion, nro_cliente, nro_ca)
VALUES(1001,'Pago Servicios',1,1); 

INSERT INTO transaccion(nro_trans, fecha, hora, monto)
VALUES(1002,'2018-10-2','13:30:00',1001); 
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(1002,2); 
INSERT INTO deposito(nro_trans, nro_ca) VALUES(1002,1);

INSERT INTO transaccion(nro_trans, fecha, hora, monto)
VALUES(1003,'2018-10-3','13:30:00',101); 
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(1003,10); 
INSERT INTO extraccion(nro_trans, nro_cliente, nro_ca) VALUES(1003,7,1);

INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(1004,'2018-10-4','13:30:00',501); 
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(1004,11); 
INSERT INTO transferencia(nro_trans, nro_cliente, origen, destino) VALUES(1004,1,1,2);

INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(1005,'2018-10-5','13:30:00',50.50); 
INSERT INTO debito(nro_trans, descripcion, nro_cliente, nro_ca) 
VALUES(1005,'Pago servicios',7,1); 

INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(1006,'2018-10-6','13:30:00',1002); 
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(1006,3); 
INSERT INTO deposito(nro_trans, nro_ca) VALUES(1006,1);

INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(1007,'2018-10-7','13:30:00',102); 
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(1007,12); 
INSERT INTO extraccion(nro_trans, nro_cliente, nro_ca) VALUES(1007,1,1);

INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(1008,'2018-10-8','13:30:00',502); 
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(1008,13); 
INSERT INTO transferencia(nro_trans, nro_cliente, origen, destino) VALUES(1008,7,1,3);

INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(1009,'2018-10-9','13:30:00',60.50); 
INSERT INTO debito(nro_trans, descripcion, nro_cliente, nro_ca) 
VALUES(1009,'Pago servicios',1,1); 

INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(1010,'2018-10-10','13:30:00',1003);
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(1010,2);  
INSERT INTO deposito(nro_trans, nro_ca) VALUES(1010,1);

INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(1011,'2018-10-11','13:30:00',103); 
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(1011,10);  
INSERT INTO extraccion(nro_trans, nro_cliente, nro_ca) VALUES(1011,1,1);

INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(1012,'2018-10-12','13:30:00',503); 
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(1012,11);  
INSERT INTO transferencia(nro_trans, nro_cliente, origen, destino) VALUES(1012,7,1,4);

INSERT INTO transaccion(nro_trans, fecha, hora, monto)
VALUES(1013,'2018-10-13','13:30:00',70.50); 
INSERT INTO debito(nro_trans, descripcion, nro_cliente, nro_ca) 
VALUES(1013,'Pago servicios',1,1); 

INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(1014,'2018-10-14','13:30:00',1004); 
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(1014,2);  
INSERT INTO deposito(nro_trans, nro_ca) VALUES(1014,1);

INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(1015,'2018-10-15','13:30:00',104); 
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(1015,12);  
INSERT INTO extraccion(nro_trans, nro_cliente, nro_ca) VALUES(1015,1,1);

INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(1016,'2018-10-16','13:30:00',504); 
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(1016,13);  
INSERT INTO transferencia(nro_trans, nro_cliente, origen, destino) VALUES(1016,7,1,4);

    
# de la caja de ahorro 2 (un deposito por 2000)
INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(2,'2018-9-1','13:30:00',2000);
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(2,1);   
INSERT INTO deposito(nro_trans, nro_ca) VALUES(2,2);

# de la caja de ahorro 3 (un deposito por 3000)
INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(3,'2018-9-1','13:30:00',3000); 
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(3,1);  
INSERT INTO deposito(nro_trans, nro_ca) VALUES(3,3);

# de la caja de ahorro 4 (un deposito por 4000)
INSERT INTO transaccion(nro_trans, fecha, hora, monto) 
VALUES(4,'2018-9-1','13:30:00',4000); 
INSERT INTO transaccion_por_caja(nro_trans, cod_caja) VALUES(4,1);  
INSERT INTO deposito(nro_trans, nro_ca) VALUES(4,4);

