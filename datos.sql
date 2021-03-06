#DATOS DE CIUDADES
INSERT INTO ciudad VALUES (8000, 'Bahia Blanca');
INSERT INTO ciudad VALUES (1000, 'Buenos Aires');
INSERT INTO ciudad VALUES (8500, 'Viedam');

#DATOS DE SUCURSALES

#Bahía Blanca
INSERT INTO sucursal VALUES (3,'sucBB1','Alem 1150','1234321', 'Lun-Vie 8-16hs',8000);

INSERT INTO sucursal VALUES (4,'sucBB2','Brown 50','1334255', 'Lun-Vie 7-17hs',8000);

#Buenos Aires
INSERT INTO sucursal VALUES (2,'sucBA1','Corrientes 77','15523232', 'Lun-Vie 7-20hs',1000);

INSERT INTO sucursal VALUES (1,'sucBA2','9 de julio 3070','08900213', 'Lun-Vie 7-20hs',1000);

#Viedma
INSERT INTO sucursal VALUES (5,'sucV1','Manzanos 210','29923234', 'Lun-Vie 8-16hs',8500);

INSERT INTO sucursal VALUES (6,'sucV1','Mitre 123','29923234', 'Lun-Vie 8-16hs',8500);

#DATOS EMPLEADOS -> 12 EN TOTAL, 2 POR SUCURSAL

#De Bahía Blanca
INSERT INTO empleado VALUES (2015,'Esteban','Quito','DNI', 33155788,'Rosales 25', '155676777','Gerente', md5('embb1'),3);
 
INSERT INTO empleado VALUES (787,'Guillermo','Puertas','DNI', 29134698,'Uruguay 990', '154323223','Cajero', md5('embb2'),3);

INSERT INTO empleado VALUES (3200,'Ayrton','Cenna','DNI',37938284,'Pilcaniyen 586', '1558273719','Cajero', md5('embb3'),4);
 
INSERT INTO empleado VALUES (4715,'Pablo','Gomez','DNI', 3888283,'Socrates 3527', '156554534','Gerente', md5('embb4'),4);

#De Viedma
INSERT INTO empleado VALUES (999,'Mario','Salazar','DNI',35999658,'Paraguay 325', '154223233','Gerente', md5('emv1'),5);
 
INSERT INTO empleado VALUES (700,'Raul','Gonzalez','DNI', 32137695,'Estomba 1256', '155366443','Cajero', md5('emv2'),5);

INSERT INTO empleado VALUES (888,'Ayrton','Cenna','DNI', 31955284,'Chiclana 2148', '154677900','Cajero', md5('emv3'),6);
 
INSERT INTO empleado VALUES (437,'Florencia','Martinez','DNI', 27778345,'Salta 354', '156554534','Gerente', md5('emv4'),6);

#De Buenos Aires

INSERT INTO empleado VALUES (1020,'Marcela','Gallardi','DNI', 38828353,'Peru 2733', '154223233','Gerente', md5('emba1'),1);
 
INSERT INTO empleado VALUES (5,'Claudio','Sanchez','DNI', 17283845,'Chancai 1754', '155366443','Cajero', md5('emba2'),1);

INSERT INTO empleado VALUES (3,'Romina','Perez','DNI', 19002345,'Newton 1001', '155576783','Cajero', md5('emba3'),2);
 
INSERT INTO empleado VALUES (2,'Sonia','Tembolini','DNI', 21384734,'Darwin 1010', '154445566','Gerente', md5('emba4'),2);

#DATOS DE CLIENTE
INSERT INTO cliente VALUES (20,'Mauro','Moura','DNI',32002312, 'Albatros 24','154432322','1977-11-30');

INSERT INTO cliente VALUES (99,'Maria','Dellai','DNI',31443532, 'Albatros 24','154432324','1989-07-09');

INSERT INTO cliente VALUES (77,'Muriel','Huara','DNI',41443532, 'Chiclana 27','154432328','1999-03-09');

INSERT INTO cliente VALUES (88,'Marta','Sentorami','DNI',29443532, 'Chacabuco 99','154432327','1977-06-09');

INSERT INTO cliente VALUES (15,'Mirta','Scarfo','DNI',16443532, 'Panama 2222','154432335','1985-05-15');

INSERT INTO cliente VALUES (39,'Mario','Heredia','DNI',33443532, 'Sarmiento 3','154455322','1972-03-09');

#DATOS DE PLAZOS FIJOS
INSERT INTO plazo_fijo VALUES (232323,3000.04,5.00,'2018-05-30','2019-05-30',05.30,1);
INSERT INTO plazo_fijo VALUES (242424,3200.20,3.30,'2018-10-25','2019-10-25',05.30,2);
INSERT INTO plazo_fijo VALUES (252525,1900.04,4.30,'2018-07-02','2019-03-02',07.00,3);
INSERT INTO plazo_fijo VALUES (303030,3300.04,3.20,'2018-10-08','2019-07-12',07.60,4);
INSERT INTO plazo_fijo VALUES (444444,3400.04,5.50,'2018-06-27','2019-08-23',03.30,5);
INSERT INTO plazo_fijo VALUES (123123,2900.04,5.00,'2018-04-15','2019-02-28',02.30,6); 

#DATOS DE TASA_PLAZO_FIJOS
INSERT INTO tasa_plazo_fijo VALUES (543,1000.25,10000.10,11.33);
INSERT INTO tasa_plazo_fijo VALUES (517,2000.25,12000.76,13.15);
INSERT INTO tasa_plazo_fijo VALUES (120,3000.25,15000.10,15.18);
INSERT INTO tasa_plazo_fijo VALUES (420,4000.25,17000.10,14.88);
INSERT INTO tasa_plazo_fijo VALUES (333,5000.25,20000.10,12.40);
INSERT INTO tasa_plazo_fijo VALUES (220,6000.25,25000.10,19.45);

#DATOS PLAZO_CLIENTE
INSERT INTO plazo_cliente VALUES (232323,39);
INSERT INTO plazo_cliente VALUES (242424,20);
INSERT INTO plazo_cliente VALUES (252525,99);
INSERT INTO plazo_cliente VALUES (303030,77);
INSERT INTO plazo_cliente VALUES (444444,15);
INSERT INTO plazo_cliente VALUES (123123,88);

#DATO PRESTAMO
INSERT INTO prestamo VALUES (999,'2018-09-15',12,250000,10.00,05.00,2187.5,1020,39);

INSERT INTO prestamo VALUES (1020,'2018-07-30',12,300000,10.00,05.00,2625,2015,77);

INSERT INTO prestamo VALUES (2500,'2018-09-15',12,250000,10.00,05.00,2187.5,999,99);

INSERT INTO prestamo VALUES (2120,'2018-05-01',12,100000,10.00,05.00,8750,5,88);

INSERT INTO prestamo VALUES (1750,'2018-02-28',12,40000,10.00,05.00,3500,700,15);

INSERT INTO prestamo VALUES (1995,'2018-03-19',12,500000,10.00,05.00,4375,787,20);

#DATOS PAGO

INSERT INTO pago VALUES(999,1555, '2018-10-15','2018-10-14');
INSERT INTO pago VALUES(1020,1777, '2018-09-30','2018-09-25');
INSERT INTO pago VALUES(999,1232, '2018-12-15','2018-12-14');
INSERT INTO pago VALUES(2120,1923, '2018-07-1','2018-06-29');
INSERT INTO pago VALUES(1750,2143, '2018-05-28','2018-05-26');
INSERT INTO pago VALUES(1995,2245, '2018-08-19','2018-08-14');

#DATOS TASA_PRESTAMO

INSERT INTO tasa_prestamo VALUES(666,10000,100000,7.00);
INSERT INTO tasa_prestamo VALUES(666,20000,120000,5.90);
INSERT INTO tasa_prestamo VALUES(666,30000,150000,6.55);
INSERT INTO tasa_prestamo VALUES(666,50000,170000,7.00);

#DATOS CAJA_AHORRO

INSERT INTO caja_ahorro VALUES(1234567,2345,20000);
INSERT INTO caja_ahorro VALUES(1234568,2346,100);
INSERT INTO caja_ahorro VALUES(1234569,2347,2134);
INSERT INTO caja_ahorro VALUES(1234570,2348,6000);
INSERT INTO caja_ahorro VALUES(1234571,2349,950);
INSERT INTO caja_ahorro VALUES(1234572,2350,5230);

#DATOS CLIENTE_CA

INSERT INTO cliente_ca VALUES(39,1234568);
INSERT INTO cliente_ca VALUES(77,1234569);
INSERT INTO cliente_ca VALUES(88,1234567);
INSERT INTO cliente_ca VALUES(99,1234570);
INSERT INTO cliente_ca VALUES(15,1234571);
INSERT INTO cliente_ca VALUES(20,1234572);

#DATOS TARJETA

INSERT INTO tarjeta VALUES (223344,md5('4444'),5151,'2025-10-31',88,1234567);

INSERT INTO tarjeta VALUES (445566,md5('4445'),5152,'2025-07-31',77,1234569);

INSERT INTO tarjeta VALUES (778899,md5('4446'),5153,'2025-12-31',99,1234570);

INSERT INTO tarjeta VALUES (432345,md5('4447'),5154,'2025-11-30',20,1234572);

INSERT INTO tarjeta VALUES (112255,md5('4448'),5155,'2025-10-31',15,1234571);

INSERT INTO tarjeta VALUES (658999,md5('4449'),5156,'2025-05-31',39,1234568);

#DATOS CAJA
INSERT INTO caja VALUES(20);
INSERT INTO caja VALUES(21);
INSERT INTO caja VALUES(22);
INSERT INTO caja VALUES(23);
INSERT INTO caja VALUES(24);
INSERT INTO caja VALUES(25);

#DATOS VENTANILLA

INSERT INTO ventanilla VALUES(20,1);
INSERT INTO ventanilla VALUES(21,2);
INSERT INTO ventanilla VALUES(22,3);
INSERT INTO ventanilla VALUES(23,4);
INSERT INTO ventanilla VALUES(24,5);
INSERT INTO ventanilla VALUES(25,6);

#DATOS ATM

INSERT INTO atm VALUES(20,1000,'Corrientes 77');
INSERT INTO atm VALUES(21,1000,'9 de julio 3070');
INSERT INTO atm VALUES(22,8000,'Alem 1150');
INSERT INTO atm VALUES(23,8000,'Brown 50');
INSERT INTO atm VALUES(24,8500,'Manzanos 210');
INSERT INTO atm VALUES(25,8500,'Mitre 123');

#DATOS TRANSACCION
INSERT INTO transaccion VALUES(9005,'2018-06-11','14:00:00',1000);

INSERT INTO transaccion VALUES(9634,'2018-07-30','12:37:50',750);

INSERT INTO transaccion VALUES(10005,'2018-12-03','16:13:21',20000);

INSERT INTO transaccion VALUES(1105,'2018-08-10','15:36:44',1250);

INSERT INTO transaccion VALUES(1505,'2018-09-12','09:30:12',1400);

INSERT INTO transaccion VALUES(1605,'2018-10-20','18:00:23',900);

#DATOS DEBITO

INSERT INTO debito VALUES (9005,NULL,77,1234569);
INSERT INTO debito VALUES (10005,NULL,88,1234567);

#DATOS TRANSACCION_POR_CAJA

INSERT INTO transaccion_por_caja VALUES (9634,21);
INSERT INTO transaccion_por_caja VALUES (1105,23);
INSERT INTO transaccion_por_caja VALUES (1505,24);
INSERT INTO transaccion_por_caja VALUES (1605,25);

#DATOS DEPOSITO
INSERT INTO deposito VALUES (9634,1234572);
INSERT INTO deposito VALUES (1105,1234569);

#DATOS EXTRACCION
INSERT INTO extraccion VALUES (1505,77,1234569);

#DATOS TRANSFERENCIA
INSERT INTO transferencia VALUES (1605,99,1234570,1234568);

