

CREATE TABLE Modulo(
	Name varchar(50) PRIMARY KEY,
	Alfa  varchar(20)  NULL,
	Beta  varchar(20)  NULL,
	Gamma varchar(20)  NULL,
	Kappa varchar(20) NULL,
	Rs varchar(20) NULL);

CREATE TABLE Medida (
	id int PRIMARY KEY,
	Campania varchar(50) NOT NULL,
	Fecha varchar(20) NOT NULL,
	Hora varchar(20) NOT NULL,
	Isc varchar(20) NULL,
	Voc varchar(20) NULL,
	Pmax varchar(20) NULL,
	IPmax varchar(20) NULL,
	VPmax varchar(20) NULL,
	FF varchar(20) NULL,	
	ModuloNombre varchar(50) NOT NULL,
	MedidaOrig int NULL,
	FOREIGN KEY(Campania, ModuloNombre) REFERENCES Campanya(Nombre, ModuloNombre),
	FOREIGN KEY(ModuloNombre) REFERENCES Modulo(Name),
	FOREIGN KEY(MedidaOrig) REFERENCES Medida(id));

CREATE TABLE Campanya(
	Nombre varchar(50),
	ModuloNombre varchar(50),
	FechaInit varchar(20) NULL,
	FechaFin varchar(20) NULL,
	PRIMARY KEY(Nombre, ModuloNombre),
	FOREIGN KEY(ModuloNombre) REFERENCES Modulo(Name));
	
CREATE TABLE Punto(
	id int PRIMARY KEY,
	idMedida varchar(50) NOT NULL,
	tension varchar(50) NOT NULL,
	corriente varchar(50) NOT NULL,
	potencia varchar(50) NOT NULL,
	FOREIGN KEY(idMedida) REFERENCES Medida(id));
	
CREATE TABLE Canal(
	id int PRIMARY KEY,
	idM int NOT NULL,
	Campania varchar(50) NOT NULL,
	ModuloNombre  varchar(50) NOT NULL,
	nombre varchar(50) NOT NULL,
	valorM real NULL,
	valorI real NULL,
	valorF real NULL,
	unidad varchar(20) NULL,
	FOREIGN KEY (idM) REFERENCES Medida(id),
	FOREIGN KEY(ModuloNombre) REFERENCES Modulo(Name));
	
CREATE TABLE PARAMETROSC(
	id int PRIMARY KEY,
	temperaturaObjetivo varchar(20) NOT NULL,
	irradianciaObjetivo varchar(20) NOT NULL,
	metodo varchar(100) NOT NULL,
	FOREIGN KEY(id) REFERENCES Medida(id)); 