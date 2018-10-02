#Archivo batch (batalllas.sql) para la creación de la 
#Base de datos del práctico de SQL

#Lo que esta después del "#" es un comentario

# Creo de la Base de Datos
CREATE DATABASE batallas;

# selecciono la base de datos sobre la cual voy a hacer modificaciones
USE batallas;

#-------------------------------------------------------------------------
# Creación Tablas para las entidades

CREATE TABLE barcos (
 nombre_barco VARCHAR(45) NOT NULL, 
 capitan VARCHAR(45) NOT NULL, 
 id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
 
 CONSTRAINT pk_barcos 
 PRIMARY KEY (nombre_barco),
 
 KEY (id)
) ENGINE=InnoDB;


CREATE TABLE clases (
 clase VARCHAR(45) NOT NULL,
 tipo VARCHAR(20) NOT NULL,
 pais VARCHAR(45) NOT NULL,
 nro_caniones SMALLINT unsigned NOT NULL,
 calibre SMALLINT unsigned NOT NULL,
 desplazamiento SMALLINT unsigned NOT NULL,
 
 CONSTRAINT pk_clases
 PRIMARY KEY (clase)
) ENGINE=InnoDB;

CREATE TABLE batallas (
 nombre_batalla VARCHAR(25) NOT NULL DEFAULT '',
 fecha DATE NOT NULL,
 
 CONSTRAINT pk_batallas
 PRIMARY KEY (nombre_batalla)
) ENGINE=InnoDB;

#-------------------------------------------------------------------------
# Creación Tablas para las relaciones

CREATE TABLE barco_clase (
 nombre_barco VARCHAR(45) NOT NULL,
 clase VARCHAR(45) NOT NULL,
 lanzado SMALLINT unsigned NOT NULL,
 
 CONSTRAINT pk_barco_clase
 PRIMARY KEY (nombre_barco),

 CONSTRAINT FK_barco_clase_barco 
 FOREIGN KEY (nombre_barco) REFERENCES barcos (nombre_barco),
 
 CONSTRAINT FK_barco_clase_clase 
 FOREIGN KEY (clase) REFERENCES clases (clase)
) ENGINE=InnoDB;


CREATE TABLE resultados (
 nombre_barco VARCHAR(45) NOT NULL,
 nombre_batalla VARCHAR(45) NOT NULL,
 resultado VARCHAR(45) NOT NULL, 
 
 CONSTRAINT pk_resultados
 PRIMARY KEY (nombre_barco,nombre_batalla),
 
 CONSTRAINT FK_resultados_barco 
 FOREIGN KEY (nombre_barco) REFERENCES barcos (nombre_barco),
 CONSTRAINT FK_resultados_batallas 
 FOREIGN KEY (nombre_batalla) REFERENCES batallas (nombre_batalla) 
) ENGINE=InnoDB;

#-------------------------------------------------------------------------
# Carga de datos de Prueba
 
INSERT INTO clases VALUES ("Bismarck", "acorazado", "Germany", 8 ,15 ,42000);
INSERT INTO clases VALUES ("Iowa", "acorazado", "USA", 9, 16, 46000);
INSERT INTO clases VALUES ("Kongo", "crucero", "Japan", 8, 14, 32000);
INSERT INTO clases VALUES ("North Carolina", "acorazado", "USA", 9 ,16, 37000);
INSERT INTO clases VALUES ("Renown", "crucero", "Gt.Britain" ,6, 15, 32000);
INSERT INTO clases VALUES ("Revenge", "acorazado", "Gt.Britain" ,8, 15, 29000);
INSERT INTO clases VALUES ("Tennessee", "acorazado", "USA", 12 ,14 ,32000);
INSERT INTO clases VALUES ("Yamato", "acorazado", "Japan", 9 ,18 ,65000);
INSERT INTO clases VALUES ("Suffolk", "crucero", "Gt.Britain", 8 , 8 , 10000);
INSERT INTO clases VALUES ("Colorado", "acorazado", "USA", 9 , 16, 33000);

INSERT INTO batallas VALUES("Sola Air Station", "1940/04/17");
INSERT INTO batallas VALUES("North Atlantic", "1941/05/24");
INSERT INTO batallas VALUES("Guadalcanal", str_to_date('15/11/1942', '%d/%m/%Y')); 
#INSERT INTO batallas VALUES("Guadalcanal", "1942/11/15");
INSERT INTO batallas VALUES("North Cape", "1943/12/26");
INSERT INTO batallas VALUES("Surigao Strait", "1944/10/25");
INSERT INTO batallas VALUES("Murmansk","1942/05/01");

INSERT INTO barcos(nombre_barco, capitan) VALUES("California","SWIRYD" );
INSERT INTO barcos(nombre_barco, capitan) VALUES("Haruna","STEIJMAN");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Hiei","SIMONE");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Iowa","SILVETTI");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Kirishima", "SEVERINI");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Kongo","KUNISCH");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Missouri","IHITZ");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Musashi","PIETRASANTA");
INSERT INTO barcos(nombre_barco, capitan) VALUES("New Jersey","KWIATKOWSKI");
INSERT INTO barcos(nombre_barco, capitan) VALUES("North Carolina","LANG");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Ramillies", "LEMA");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Renown","PETERSEN");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Repulse", "PODVERSICH CHIAVENNA");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Resolution","ITURRE");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Revenge","LEVIN SACCONE");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Royal Oak","LIRIO");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Royal Sovereign", "SCHROH OLIVERA");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Tennessee","LEVI");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Washington","SERI MEDEI");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Wisconsin", "LASSALLETTE");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Yamato", "LONGONI");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Bismarck", "Ernst Lindeman");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Duke of York", "LANGE");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Fuso", "SEPULVEDA");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Hood", "Lancelot Holland");
INSERT INTO barcos(nombre_barco, capitan) VALUES("HMS King George V", "John Tovey");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Suffolk", "Lancelot Holland");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Prince of Wales", "SMITH");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Rodney", "George Campell Ross");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Scharnhorst", "LATINI");
INSERT INTO barcos(nombre_barco, capitan) VALUES("South Dakota", "PIRO LEW" );
INSERT INTO barcos(nombre_barco, capitan) VALUES("West Virginia", "Thomas J. Senn");
INSERT INTO barcos(nombre_barco, capitan) VALUES("Yamashiro", "ILARRESCAPE");


INSERT INTO barco_clase VALUES("California","Tennessee",1921);
INSERT INTO barco_clase VALUES("Haruna","Kongo",1915);
INSERT INTO barco_clase VALUES("Hiei","Kongo",1914);
INSERT INTO barco_clase VALUES("Iowa","Iowa",1943);
INSERT INTO barco_clase VALUES("Kirishima","Kongo",1915);
INSERT INTO barco_clase VALUES("Kongo","Kongo",1913);
INSERT INTO barco_clase VALUES("Missouri","Iowa",1944);
INSERT INTO barco_clase VALUES("Musashi","Yamato",1942);
INSERT INTO barco_clase VALUES("New Jersey","Iowa",1943);
INSERT INTO barco_clase VALUES("North Carolina","North Carolina",1941);
INSERT INTO barco_clase VALUES("Ramillies","Revenge",1917);
INSERT INTO barco_clase VALUES("Renown","Renown",1916);
INSERT INTO barco_clase VALUES("Repulse","Renown",1916);
INSERT INTO barco_clase VALUES("Resolution","Revenge",1916);
INSERT INTO barco_clase VALUES("Revenge","Revenge",1916);
INSERT INTO barco_clase VALUES("Royal Oak", "Revenge",1916);
INSERT INTO barco_clase VALUES("Royal Sovereign","Revenge",1916);
INSERT INTO barco_clase VALUES("Suffolk","Suffolk",1924);
INSERT INTO barco_clase VALUES("Tennessee","Tennessee",1920);
INSERT INTO barco_clase VALUES("West Virginia","Colorado",1920);
INSERT INTO barco_clase VALUES("Washington","North Carolina",1941);
INSERT INTO barco_clase VALUES("Wisconsin","Iowa",1944);
INSERT INTO barco_clase VALUES("Yamato","Yamato",1941);

INSERT INTO resultados VALUES ("Bismarck","North Atlantic","hundido");
INSERT INTO resultados VALUES ("California","Surigao Strait","ok");
INSERT INTO resultados VALUES ("Duke of York","North Cape","ok");
INSERT INTO resultados VALUES ("Fuso","Surigao Strait","hundido");
INSERT INTO resultados VALUES ("Hood","North Atlantic","hundido");
INSERT INTO resultados VALUES ("Suffolk","Sola Air Station","averiado");
INSERT INTO resultados VALUES ("Suffolk","North Atlantic","ok");
INSERT INTO resultados VALUES ("HMS King George V","Murmansk","averiado");
INSERT INTO resultados VALUES ("HMS King George V","North Atlantic","ok");
INSERT INTO resultados VALUES ("Kirishima","Guadalcanal","hundido");
INSERT INTO resultados VALUES ("Prince of Wales","North Atlantic","averiado");
INSERT INTO resultados VALUES ("Rodney","North Atlantic","ok");
INSERT INTO resultados VALUES ("Scharnhorst","North Cape","hundido");
INSERT INTO resultados VALUES ("South Dakota","Guadalcanal","averiado");
INSERT INTO resultados VALUES ("Tennessee","Surigao Strait","ok");
INSERT INTO resultados VALUES ("Washington","Guadalcanal","ok");
INSERT INTO resultados VALUES ("West Virginia","Surigao Strait","ok");
INSERT INTO resultados VALUES ("Yamashiro","Surigao Strait","hundido");

#-------------------------------------------------------------------------
# Creación de vistas 
# (acorazados = Datos de todos los barcos que son "acorazados"

   CREATE VIEW acorazados as 
   SELECT b.nombre_barco, b.capitan, 
          c.clase, c.pais, c.nro_caniones, c.calibre, c. desplazamiento,
		  b_c.lanzado
   FROM (barcos as b JOIN  barco_clase as b_c ON b.nombre_barco = b_c.nombre_barco) 
        JOIN clases as c   ON c.clase = b_c.clase
   WHERE c.tipo="acorazado";


#-------------------------------------------------------------------------
# Creación de usuarios y otorgamiento de privilegios

# creo el usuario y le otorgo privilegios 
# utilizando solo la sentencia GRANT

    GRANT ALL PRIVILEGES ON batallas.* TO admin_batallas@localhost 
    IDENTIFIED BY 'pwadmin' WITH GRANT OPTION;

# El usuario 'admin_batallas' tiene acceso total a todas las tablas de 
# la B.D. batallas, puede conectarse solo desde la desde la computadora 
# donde se encuentra el servidor de MySQL (localhost), el password de su 
# cuenta es 'pwadmin' y puede otorgar privilegios 


# primero creo un usuario con CREATE USER

    CREATE USER barco@localhost IDENTIFIED BY 'pwbarco'; 

# el usuario 'barco' con password 'pwbarco' puede conectarse solo desde localhost


# Luego le otorgo privilegios con GRANT

    GRANT SELECT ON batallas.acorazados TO barco@localhost;

# el usuario 'barco' solo puede acceder a la tabla (vista) acorazados
# con permiso para selecionar  



