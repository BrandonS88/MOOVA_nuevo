DROP DATABASE IF EXISTS MOOVA_DB;
CREATE DATABASE IF NOT EXISTS MOOVA_DB;
USE MOOVA_DB;

CREATE TABLE Categoria (
    id_Categoria INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    estado BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT PK_Categoria
        PRIMARY KEY (id_Categoria)
);

CREATE TABLE Subcategoria (
    id_Subcategoria INT AUTO_INCREMENT,
    id_Categoria INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    estado BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT PK_Subcategoria
        PRIMARY KEY (id_Subcategoria),

    CONSTRAINT FK_Subcategoria_Categoria
        FOREIGN KEY (id_Categoria)
        REFERENCES Categoria(id_Categoria),

    CONSTRAINT UQ_Subcategoria
        UNIQUE (id_Categoria, nombre)
);

CREATE TABLE Usuario (
    id_Usuario INT AUTO_INCREMENT,
    nombre_usuario VARCHAR(25) NOT NULL,
    nombre VARCHAR(25) NOT NULL,
    apellido VARCHAR(25) NOT NULL,
    contrasena VARCHAR(20) NOT NULL,
	correo_electronico VARCHAR(50) NOT NULL,
    
    rol ENUM(
        'Cajero',
        'Administrador'
    ) NOT NULL,

    estado BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT PK_Usuario
        PRIMARY KEY (id_Usuario),

    CONSTRAINT UQ_Usuario_Correo
        UNIQUE (correo_electronico),

    CONSTRAINT UQ_Usuario_Nombre
        UNIQUE (nombre_usuario)
);

CREATE TABLE Ingrediente (
    id_Ingrediente INT AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL DEFAULT 100,
    cantidad_minima INT NOT NULL DEFAULT 30,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    descripcion TEXT,
    precio DECIMAL(10,2),
    imagen VARCHAR(400),
    estado BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT PK_Ingrediente
        PRIMARY KEY (id_Ingrediente)
);

CREATE TABLE Producto (
    id_Producto INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    imagen VARCHAR(400),
    estado BOOLEAN NOT NULL DEFAULT TRUE,
	
    id_Subcategoria INT NOT NULL,

    CONSTRAINT PK_Producto
        PRIMARY KEY (id_Producto),
        
	CONSTRAINT FK_Producto_Subcategoria 
		FOREIGN KEY (id_Subcategoria)
		REFERENCES Subcategoria(id_Subcategoria)
);

CREATE TABLE Receta (
    id_Receta INT AUTO_INCREMENT,
    cantidad INT NOT NULL,
    
    id_Producto INT NOT NULL,
    id_Ingrediente INT NOT NULL,

    CONSTRAINT PK_Receta
        PRIMARY KEY (id_Receta),

    CONSTRAINT FK_Receta_Producto
        FOREIGN KEY (id_Producto)
        REFERENCES Producto(id_Producto),

    CONSTRAINT FK_Receta_Ingrediente
        FOREIGN KEY (id_Ingrediente)
        REFERENCES Ingrediente(id_Ingrediente),

    CONSTRAINT UQ_Receta_Producto_Ingrediente
        UNIQUE (id_Producto, id_Ingrediente)
);
CREATE TABLE Combo (
    id_Combo INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    tipo ENUM('Complementario', 'Independiente') NOT NULL,
    precio DECIMAL(10,2) NOT NULL DEFAULT 0.00,        -- Independiente: precio completo
    precio_extra DECIMAL(10,2) NOT NULL DEFAULT 0.00,  -- Complementario: se suma al producto
    imagen VARCHAR(400),
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    id_Subcategoria INT NULL,                          -- Independiente: dónde aparece

    CONSTRAINT PK_Combo PRIMARY KEY (id_Combo),
    CONSTRAINT FK_Combo_Subcategoria
        FOREIGN KEY (id_Subcategoria) REFERENCES Subcategoria(id_Subcategoria),
    CONSTRAINT CK_Combo_Tipo CHECK (
        (tipo = 'Independiente'  AND id_Subcategoria IS NOT NULL AND precio > 0 AND precio_extra = 0)
     OR (tipo = 'Complementario' AND id_Subcategoria IS NULL AND precio = 0)
    )
);

CREATE TABLE Producto_Combo (
    id_ProductoCombo INT AUTO_INCREMENT,
    id_Producto INT NOT NULL,
    id_Combo INT NOT NULL,

    CONSTRAINT PK_ProductoCombo
        PRIMARY KEY (id_ProductoCombo),

    CONSTRAINT FK_ProductoCombo_Producto
        FOREIGN KEY (id_Producto)
        REFERENCES Producto(id_Producto),

    CONSTRAINT FK_ProductoCombo_Combo
        FOREIGN KEY (id_Combo)
        REFERENCES Combo(id_Combo),

    CONSTRAINT UQ_ProductoCombo
        UNIQUE (id_Producto, id_Combo)
);

-- Representa un grupo de selección dentro de un combo.
--
-- Ejemplos:
--     Bebida
--     Acompañamiento
--     Postre
--
-- obligatorio:
--     TRUE  = el cliente debe seleccionar
--     FALSE = el cliente puede omitirlo
--
-- cantidad_minima y cantidad_maxima permiten definir
-- cuántas opciones se pueden seleccionar.

CREATE TABLE Grupo_Combo (
    id_GrupoCombo INT AUTO_INCREMENT,
    id_Combo INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    -- agregado
    obligatorio BOOLEAN NOT NULL DEFAULT TRUE,
    cantidad_minima INT NOT NULL DEFAULT 1,
    cantidad_maxima INT NOT NULL DEFAULT 1,

    CONSTRAINT PK_GrupoCombo
        PRIMARY KEY (id_GrupoCombo),

    CONSTRAINT FK_GrupoCombo_Combo
        FOREIGN KEY (id_Combo)
        REFERENCES Combo(id_Combo)
);

-- Productos que pueden ser seleccionados dentro de un grupo.
--
-- Ejemplo:
--
-- Grupo: Bebida
--
-- Coca-Cola       +Q0.00
-- Sprite          +Q0.00
-- Fanta           +Q0.00
-- Jugo natural    +Q3.00
-- Batido          +Q6.00

CREATE TABLE Opcion_Combo (
    id_OpcionCombo INT AUTO_INCREMENT,
	precio_extra DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    
    id_GrupoCombo INT NOT NULL,
    id_Producto INT NOT NULL,

    CONSTRAINT PK_OpcionCombo
        PRIMARY KEY (id_OpcionCombo),

    CONSTRAINT FK_OpcionCombo_Grupo
        FOREIGN KEY (id_GrupoCombo)
        REFERENCES Grupo_Combo(id_GrupoCombo),

    CONSTRAINT FK_OpcionCombo_Producto
        FOREIGN KEY (id_Producto)
        REFERENCES Producto(id_Producto),

    CONSTRAINT UQ_OpcionCombo
        UNIQUE (id_GrupoCombo, id_Producto)
);


CREATE TABLE Descuento (
    id_Descuento INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    valor DECIMAL(10,2) NOT NULL,
	cantidad_minima INT NOT NULL DEFAULT 1,
    fecha_inicio DATETIME NOT NULL,
    fecha_fin DATETIME NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    tipo ENUM(
        'Porcentaje',
        'Monto'
    ) NOT NULL,
    
    CONSTRAINT PK_Descuento
        PRIMARY KEY (id_Descuento)
);

-- CREATE TABLE Descuento_Producto (
    -- id_DescuentoProducto INT AUTO_INCREMENT,
    -- id_Descuento INT NOT NULL,
    -- id_Producto INT NOT NULL,

    -- CONSTRAINT PK_DescuentoProducto
        -- PRIMARY KEY (id_DescuentoProducto),

    -- CONSTRAINT FK_DescuentoProducto_Descuento
        -- FOREIGN KEY (id_Descuento)
        -- REFERENCES Descuento(id_Descuento),

    -- CONSTRAINT FK_DescuentoProducto_Producto
        -- FOREIGN KEY (id_Producto)
        -- REFERENCES Producto(id_Producto),
        
	-- CONSTRAINT UQ_DescuentoProducto
        -- UNIQUE (id_Descuento, id_Producto)
-- );

CREATE TABLE Venta (
    id_Venta INT AUTO_INCREMENT,
    fecha_realizacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    nit VARCHAR(15) DEFAULT 'C/F',

    metodo_pago ENUM(
        'Efectivo',
        'Tarjeta Debito',
        'Tarjeta Credito',
        'Mixto',
        'Tarjeta de regalo'
    ) NOT NULL,

    estado ENUM(
        'Sin Pagar',
        'Cancelado',
        'Pagado'
    ) NOT NULL DEFAULT 'Sin Pagar',

    id_Usuario INT NOT NULL,

    CONSTRAINT PK_Venta
        PRIMARY KEY (id_Venta),

    CONSTRAINT FK_Venta_Usuario
        FOREIGN KEY (id_Usuario)
        REFERENCES Usuario(id_Usuario)
);

CREATE TABLE Detalle_Venta (
    id_DetalleVenta INT AUTO_INCREMENT,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    impuesto DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    descuento DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    monto_descuento DECIMAL(10,2) NOT NULL DEFAULT 0.00,

    id_Venta INT NOT NULL,
    id_Producto INT NULL,
    id_Combo INT NULL,
    id_Descuento INT NULL,

    CONSTRAINT PK_DetalleVenta
        PRIMARY KEY (id_DetalleVenta),

    CONSTRAINT FK_DetalleVenta_Venta
        FOREIGN KEY (id_Venta)
        REFERENCES Venta(id_Venta),

    CONSTRAINT CK_DetalleVenta_ProductoOCombo 
		CHECK (id_Producto IS NOT NULL OR id_Combo IS NOT NULL),

    CONSTRAINT FK_DetalleVenta_Combo
        FOREIGN KEY (id_Combo)
        REFERENCES Combo(id_Combo),
        
	CONSTRAINT FK_DetalleVenta_Descuento
		FOREIGN KEY (id_Descuento)
		REFERENCES Descuento(id_Descuento)
);

CREATE TABLE Detalle_Venta_OpcionCombo (
    id_DetalleVentaOpcion INT AUTO_INCREMENT,
    id_DetalleVenta INT NOT NULL,
    id_OpcionCombo INT NOT NULL,

    precio_extra DECIMAL(10,2) NOT NULL DEFAULT 0.00,

    PRIMARY KEY (id_DetalleVentaOpcion),

    FOREIGN KEY (id_DetalleVenta)
        REFERENCES Detalle_Venta(id_DetalleVenta),

    FOREIGN KEY (id_OpcionCombo)
        REFERENCES Opcion_Combo(id_OpcionCombo)
);

-- Permite agregar o quitar ingredientes de un producto
-- durante una venta.
--
-- Ejemplo:
--
-- Quitar cebolla
-- Agregar queso +Q5.00
-- Agregar tocino +Q8.00

CREATE TABLE Personalizacion (
    id_Personalizacion INT AUTO_INCREMENT,
    cantidad INT NOT NULL DEFAULT 0,
    precio DECIMAL(10,2) NOT NULL DEFAULT 0.00,

    tipo ENUM(
        'Agregar',
        'Quitar'
    ) NOT NULL,
    
    id_DetalleVenta INT NOT NULL,
    id_Ingrediente INT NOT NULL,

    CONSTRAINT PK_Personalizacion
        PRIMARY KEY (id_Personalizacion),

    CONSTRAINT FK_Personalizacion_DetalleVenta
        FOREIGN KEY (id_DetalleVenta)
        REFERENCES Detalle_Venta(id_DetalleVenta),

    CONSTRAINT FK_Personalizacion_Ingrediente
        FOREIGN KEY (id_Ingrediente)
        REFERENCES Ingrediente(id_Ingrediente)
);

CREATE TABLE Movimiento_Inventario (
    id_Movimiento INT AUTO_INCREMENT,
	cantidad INT NOT NULL,
    cantidad_anterior INT NOT NULL,
    cantidad_total INT NOT NULL,
    motivo VARCHAR(255),
    fecha_movimiento DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    tipo ENUM(
        'Ingreso',
        'Salida',
        'Venta',
        'Merma',
        'Ajuste',
        'Devolucion'
    ) NOT NULL,

    id_Ingrediente INT NOT NULL,
    id_Usuario INT NOT NULL,
    id_Venta INT NULL,

    CONSTRAINT PK_MovimientoInventario
        PRIMARY KEY (id_Movimiento),

    CONSTRAINT FK_MovimientoInventario_Ingrediente
        FOREIGN KEY (id_Ingrediente)
        REFERENCES Ingrediente(id_Ingrediente),

    CONSTRAINT FK_MovimientoInventario_Usuario
        FOREIGN KEY (id_Usuario)
        REFERENCES Usuario(id_Usuario),
        
	CONSTRAINT FK_MovimientoInventario_Venta
		FOREIGN KEY (id_Venta)
		REFERENCES Venta(id_Venta)
);

CREATE TABLE Incidente (
    id_Incidente INT AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT NOT NULL,
	fecha_reporte DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_resolucion DATETIME NULL,
    solucion TEXT,

    tipo ENUM(
        'Producto',
        'Ingrediente',
        'Equipo',
        'Cliente',
        'Sistema',
        'Otro'
    ) NOT NULL,

    prioridad ENUM(
        'Baja',
        'Media',
        'Alta',
        'Critica'
    ) NOT NULL DEFAULT 'Media',

    estado ENUM(
        'Pendiente',
        'En proceso',
        'Resuelta',
        'Cancelada'
    ) NOT NULL DEFAULT 'Pendiente',
    
    id_Usuario INT NOT NULL,
    id_Ingrediente INT NULL,
    id_Producto INT NULL,

    CONSTRAINT PK_Incidente
        PRIMARY KEY (id_Incidente),

    CONSTRAINT FK_Incidente_Usuario
        FOREIGN KEY (id_Usuario)
        REFERENCES Usuario(id_Usuario),
	
    CONSTRAINT FK_Incidente_Ingrediente
        FOREIGN KEY (id_Ingrediente)
        REFERENCES Ingrediente(id_Ingrediente),
	
    CONSTRAINT FK_Incidente_Producto
        FOREIGN KEY (id_Producto)
        REFERENCES Producto(id_Producto)
	
);


-- Representa un espacio de elección dentro de un producto.
--
-- Ejemplos:
--     "Tipo de líquido" (del café)
--     "Topping" (del helado)
--
-- Igual que Grupo_Combo: obligatorio, cantidad_minima y
-- cantidad_maxima controlan si el cliente DEBE elegir algo,
-- y cuántas opciones puede tomar.

CREATE TABLE Grupo_Personalizacion (
    id_GrupoPersonalizacion INT AUTO_INCREMENT,
    id_Producto INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,

    obligatorio BOOLEAN NOT NULL DEFAULT TRUE,
    cantidad_minima INT NOT NULL DEFAULT 1,
    cantidad_maxima INT NOT NULL DEFAULT 1,

    CONSTRAINT PK_GrupoPersonalizacion
        PRIMARY KEY (id_GrupoPersonalizacion),

    CONSTRAINT FK_GrupoPersonalizacion_Producto
        FOREIGN KEY (id_Producto)
        REFERENCES Producto(id_Producto)
);

-- Las alternativas disponibles dentro de un grupo.
--
-- Ejemplo (grupo "Tipo de líquido" del café):
--
-- Agua              es_predeterminada = TRUE,  precio_extra = 0.00
-- Leche entera      es_predeterminada = FALSE, precio_extra = 3.00
-- Leche descremada  es_predeterminada = FALSE, precio_extra = 3.00


CREATE TABLE Opcion_Personalizacion (
    id_OpcionPersonalizacion INT AUTO_INCREMENT,
    id_GrupoPersonalizacion INT NOT NULL,
    id_Ingrediente INT NOT NULL,

    precio_extra DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    es_predeterminada BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT PK_OpcionPersonalizacion
        PRIMARY KEY (id_OpcionPersonalizacion),

    CONSTRAINT FK_OpcionPersonalizacion_Grupo
        FOREIGN KEY (id_GrupoPersonalizacion)
        REFERENCES Grupo_Personalizacion(id_GrupoPersonalizacion),

    CONSTRAINT FK_OpcionPersonalizacion_Ingrediente
        FOREIGN KEY (id_Ingrediente)
        REFERENCES Ingrediente(id_Ingrediente),

    CONSTRAINT UQ_OpcionPersonalizacion
        UNIQUE (id_GrupoPersonalizacion, id_Ingrediente)
);

CREATE TABLE Detalle_Venta_OpcionPersonalizacion (
    id_DetalleVentaOpcionPersonalizacion INT AUTO_INCREMENT,
    id_DetalleVenta INT NOT NULL,
    id_OpcionPersonalizacion INT NOT NULL,
    precio_extra DECIMAL(10,2) NOT NULL DEFAULT 0.00,

    CONSTRAINT PK_DetalleVentaOpcionPersonalizacion
        PRIMARY KEY (id_DetalleVentaOpcionPersonalizacion),

    CONSTRAINT FK_DetalleVentaOpcionPersonalizacion_DetalleVenta
        FOREIGN KEY (id_DetalleVenta)
        REFERENCES Detalle_Venta(id_DetalleVenta),

    CONSTRAINT FK_DetalleVentaOpcionPersonalizacion_Opcion
        FOREIGN KEY (id_OpcionPersonalizacion)
        REFERENCES Opcion_Personalizacion(id_OpcionPersonalizacion)
);

-- *********************** INGRESO DE DATOS ***********************
-- ///////// TABLA: CATEGORIA /////////
INSERT INTO Categoria(nombre) VALUES
	-- ID: 1
    ('Desayuno'),
    -- ID: 2
    ('Almuerzo'),
    -- ID: 3
    ('Postres'),
    -- ID: 4
    ('Antojos'),
    -- ID: 5
    ('Bebidas Calientes'),
    -- ID: 6
    ('Bebidas Frías'),
    -- ID: 7
    ('Gaseosas'),
    -- ID: 8
    ('Jugo Natural'),
    -- ID: 9
    ('Cajita Infantil');

-- ///////// TABLA: SUBCATEGORIA /////////
INSERT INTO Subcategoria (id_Categoria,nombre) VALUES

	-- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     DESAYUNO     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
	(1, 'MooFfin'),
    (1, 'Moovan'),
    (1, 'MooGrilled'),
    (1, 'Desayunos Completos'),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     ALMUERZO     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    (2,'Hamboorguesa'),
    (2, 'Big Moo'),
    (2, 'Cuarto de Libra'),
    (2, 'Moo Insano'),
    (2, 'MoovaBurguesa'),
    (2, 'Big Moova'),
    (2, 'Moova Crispy'),
    (2, 'GourMoova'),
    (2, 'Pico Guacamol GourMoova'),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     POSTRE     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    (3, 'Pie'),
    (3, 'Pasteles'),
    (3, 'MooFlurry'),
    (3, 'Helados de cono'),
    (3, 'Moovandae'),
    (3, 'Yogurt'),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     ANTOJOS     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    (4, 'Derretido'),
    (4, 'Papas'),
    (4, 'MooPatatas'),
    (4, 'Hashbrown'),
    (4, 'Mooggets'),
    (4, 'Tostados'),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     BEBIDAS CALIENTES     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    (5, 'Chocolate'),
    (5, 'Cafe'),
    (5, 'Moovaccino'),
    (5, 'Te'),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     BEBIDAS FRÍAS     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    (6, 'MooFizz'),
    (6, 'Smoothie'),
    (6, 'Frappé'),
    (6, 'Moova Iced'),
    (6, 'Moova Chai Frappe'),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     GASEOSAS     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    (7, 'Coca-Cola'),
    (7, 'Varios'), 
    (7, 'Te Lipton'),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     JUGO NATURAL     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    (8, 'Flores'),
    (8, 'Frutas'),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     CAJITA INFANTIL     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    (9, 'Cajita Infantil Desayuno'),
    (9, 'Cajita Infantil Almuerzo');

-- ///////// TABLA: USUARIO /////////
INSERT INTO Usuario(nombre_usuario, nombre, apellido, contrasena, rol, correo_electronico) VALUES
    -- ADMINISTRADOR
    ('AdminPrincipal','Leslie','Velásquez','A12345','Administrador','Leslie@moova.com'),
    ('AdminPrincipal01','Sara','Tipáz','A123456','Administrador','Sara@moova.com'),
    ('AdminPrincipal02','Noemy','M.Hernandez','A1234567','Administrador','Noemy@moova.com'),
    ('AdminPrincipal03','Brandon','Subuyuj','A12345678','Administrador','Subu@moova.com'),
    -- CAJEROS
    ('Cajero000','Leslie','Velásquez','C1234','Cajero','LeslieC@moova.com'),
    ('Cajero001','Sara','Tipáz','C12345','Cajero','SaraC@moova.com'),
    ('Cajero002','Noemy','M. Hernandez','C123456','Cajero','NoemyC@moova.com'),
    ('Cajero003','Brandon','Subuyuj','C1234567','Cajero','SubuC@moova.com');
 
-- ///////// TABLA: PRODUCTO /////////
INSERT INTO Producto(id_Subcategoria, nombre, descripcion, precio, imagen) VALUES
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     DESAYUNO (1)    /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
   
   -- /*/*/*/*/*/*/* MOOFIN /*/*/*/*/*/*/*
    -- TOCINO Y QUESO
    (1,'MooFfin de Tocino y Queso MooMelt', '1 pan muffin, 1 cucharada de mantequilla, 1 porción de tocino, 1 torta de salchicha, 1 huevo, 1 queso chedddar amarillo, sal, aceite vegetal.', 28.00,'/utiles/imagenes/menu/comida/desayuno/mooFin/MoofinBaconCheddarMooMelt.png'),
    -- SALCHICHA
    (1,'MooFfin de Salchicha', '1 pan muffin, 1 torta de salchicha, 1 queso cheddar amarillo, sal, aceite vegetal.', 27.00,'/utiles/imagenes/menu/comida/desayuno/mooFin//MoofinSalchicha.png'),
    (1,'MooFfin de Salchicha y Huevo', '1 pan muffin, 1 torta de salchicha, 1 queso cheddar amarillo, 1 cucharada de mantequilla, 1 huevo, sal, aceite vegetal.',28.00, '/utiles/imagenes/menu/comida/desayuno/mooFin/MoofinSalchichaHuevo.png'),
    (1,'MooFfin de Doble Salchicha y Huevo','1 pan muffin, 2 tortas de salchicha, 1 queso cheddar amarillo, 1 cucharada de mantequilla, 1 huevo, sal, aceite vegetal.',29.00, '/utiles/imagenes/menu/comida/desayuno/mooFin/MoofinDobleSalchichaHuevo.png'),
    (1,'MooFfin de Salchicha y Doble Huevo','1 pan muffin, 1 torta de salchicha, 1 queso cheddar amarillo, 1 cucharada de mantequilla, 2 huevos, sal, aceite vegetal.',30.00,'/utiles/imagenes/menu/comida/desayuno/mooFin/MoofinSalchichaDobleHuevo.png'),
    -- TOCINO
    (1,'MooFfin de Tocino y Huevo', '1 pan muffin, 1 porción de tocino, 1 huevo, 1 cucharada de mantequilla, 1 queso cheddar amarillo, sal, aceite vegetal.',28.00,'/utiles/imagenes/menu/comida/desayuno/mooFin/MoofinTocinoHuevo.png'),
	(1,'MooFfin de Doble Tocino y Huevo', '1 pan puffin, 2 porciones de tocino, 1 huevo, 1 queso cheddar amarillo, sal, aceite vegetal.', 29.00,'/utiles/imagenes/menu/comida/desayuno/mooFin/MoofinDobleTocinoHuevo.png'),
    (1,'MooFfin de Tocino y Doble Huevo','1 pan muffin, 1 porción de tocino, 2 huevos, 1 queso cheddar amarillo, sal, aceite vegetal.',30.00,'/utiles/imagenes/menu/comida/desayuno/mooFin/MoofinTocinoDobleHuevo.png'),
    -- JAMON
    (1,'MooFfin de Jamón y Huevo', '1 pan muffin, 1 rebanada de jamón, 1 huevo, 1 pedazo de mantequilla, 1 queso cheddar amarillo, sal, aceite vegetal.', 28.00, '/utiles/imagenes/menu/comida/desayuno/mooFin/MoofinHuevoJamon.png'),
    (1,'MooFfin de Doble Jamón y Huevo', '1 pan muffin, 1 reabanada de jamón, 1 huevo, 1 cucharada de mantequilla, 1 queso cheddar amarillo, sal, aceite vegetal.', 29.00, '/utiles/imagenes/menu/comida/desayuno/mooFin/MoofinHuevoDobleJamón.png'),
    (1,'MooFfin de Jamón y Doble Huevo','1 pan muffin, 1 rebanada de jamón, 1 huevo, 1 cucharada de mantequilla, 1 queso cheddar amarillo, sal, aceite vegetal.',30.00,'/utiles/imagenes/menu/comida/desayuno/mooFin/MoofinDobleHuevoJamón.png'),
	-- FRIJOL
	(1, 'MooFfin de Frijol y Huevo', '1 pan muffin, 1 porcion de frijol, 1 huevo, sal, aceite vegetal.', 25.00, '/utiles/imagenes/menu/comida/desayuno/mooFin/MoofinHuevoFrijol.png'),
    -- QUESO
    (1, 'MooFfin de Queso y Huevo','1 pan muffin, 1 queso cheddar amarillo, 1 huevo, sal, aceite vegetal.', 24.00, '/utiles/imagenes/menu/comida/desayuno/mooFin/MoofinHuevoQueso.png'),

	-- /*/*/*/*/*/*/* MOOVAN /*/*/*/*/*/*/*
    -- SALCHICHA
    (2,'Súper Moovan con Salchicha', '1 pan muffin, 1 pedazo de mantequilla, 1 porcion de frijol, 1 queso cheddar amarillo, 1 torta de salchicha, 1 huevo, 2 plátanos frito, sal, aceite Vegetal.',28.00, '/utiles/imagenes/menu/comida/desayuno/superMoovan/SuperMoovanSalchicha.png'),
    (2, 'Moovan con Salchicha', '1 pan muffin, 1 pedazo de mantequilla, 1 porcion de frijol, 1 queso cheddar amarillo, 1 torta de salchicha, 1 huevo, sal, aceite vegetal.', 30.00, '/utiles/imagenes/menu/comida/desayuno/superMoovan/MoovanSalchicha.png'),
    -- JAMÓN
    (2,'Súper Moovan con Jamón','1 pan muffin, 1 pedazo de mantequilla, 1 porcion de frijol, 1 queso cheddar amarillo, 1 rebana de jamón, 1 huevo, 2 plátanos fritos, sal, aceite vegetal.',32.00,'/utiles/imagenes/menu/comida/desayuno/superMoovan/SuperMoovanJamon.png'),
    (2, 'Moovan con Jamón','1 pan muffin, 1 cuchara de mantequilla, 1 porcion de frijol, 1 queso cheddar amarillo, 1 rebana de jamón, 1 huevo, sal, aceite vegetal.',32.00,'/utiles/imagenes/menu/comida/desayuno/superMoovan/MoovanSalchicha.png'),
    
    -- /*/*/*/*/*/*/* MOOGRILLED /*/*/*/*/*/*/*
    -- TOCINO
    (3, 'MooGrilled de Tocino y Huevo', '1 pan esponjoso, 1 cucharada de miel de maple, 1 porción de tocino, 1 huevo, 1 queso cheddar amarillo, sal, aceite vegetal.', 35.00, '/utiles/imagenes/menu/comida/desayuno/mooGrilled/MooGrilledTocinoHuevo.png'),
    -- SALCHICHA
    (3, 'MooGrilled de Salchicha y Huevo', '1 pan esponjoso, 1 cucharada de miel de maple, 1 torta de salchicha, 1 huevo, 1 queso cheddar amarillo, sal, aceite vegetal.', 39.00, '/utiles/imagenes/menu/comida/desayuno/mooGrilled/MooGrilledSalchichaHuevo.png'),
    (3, 'MooGrilled de Salchicha', '1 pan esponjoso, 1 torta de salchicha, 1 cucharada de miel de maple, sal, aceite vegetal.', 30.00, '/utiles/imagenes/menu/comida/desayuno/mooGrilled/MooGrilledSalchicha.png'),
 
 -- KJSADSAKDSDKJASJDSHJDHKASHDASHDJKSDAJDAHDKAJSDAHSD
    -- /*/*/*/*/*/*/* DESAYUNOS COMPLETOS /*/*/*/*/*/*/*
    -- DELUXE
    (4,'Desayuno MooDeluxe','2 Huevos, 1 torta de Salchicha, 3 Panqueques, 1 cucharada de Miel de Maple, 1 pedazo de Mantequilla, 1 Pan Muffin, 1 Hash brown, Sal, Aceite Vegetal.', 46.00, '/utiles/imagenes/menu/comida/desayuno/comboDesayunos/DesayunoMooDeluxe.png'),
    -- CLASICO
    (4,'Desayuno MooClásico', '2 Huevos, 1 torta de Salchicha, 1 Pan Muffin, 1 Hash brown, Sal, Aceite Vegetal.', 38.00, '/utiles/imagenes/menu/comida/desayuno/comboDesayunos/DesayunoMooClasico.png'),
    -- TRADICIONAL
    (4,'Desayuno MooTradicional', '2 Huevos, 1 torta de Salchicha, 1 porcion de Frijol, 1 rebanada de Queso Fresco, 2 Plátanos, 2 Tortillas, 1 vasito de Crema, 1 Chirmol, Sal, Aceite Vegetal.',45.00 ,'/utiles/imagenes/menu/comida/desayuno/comboDesayunos/DesayunoMooTradicional.png'),
    -- MOOVACAKE
    (4, 'MoovaCake', '1 taza de Harina de Trigo, 1 taza de leche, 1 cucharada de canela en polvo, 1 huevo, 1 pedazo de mantequilla, 1 cucharada de Miel de maple', 36.00, '/utiles/imagenes/menu/comida/desayuno/comboDesayunos/MooCake.png'),
    -- MOOVITO
    (4, 'Moovito Individual', '1 Tortilla de harina, 1 torta de salchicha, 1 Huevo, 1 Queso cheddar amarillo, 1 Chile Pimiento, Sal, Aceite Vegetal.', 30.00, '/utiles/imagenes/menu/comida/desayuno/comboDesayunos/Moovito.png'),
    (4, 'Moovito Doble', '2 Tortillas de harina, 2 tortas de salchicha, 2 Huevos, 2 Quesos cheddar amarillo, 1 Chile Pimiento, Sal, Aceite Vegetal.', 39.00, '/utiles/imagenes/menu/comida/desayuno/comboDesayunos/MoovitoDoble.png'),
	
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     ALMUERZO (2)     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    -- /*/*/*/*/*/*/* HAMBOORGUESA /*/*/*/*/*/*/*
    (5, 'Hamboorguesa', '1 Pan con Ajonjolí, 1 torta de Carne de res, 2 rodajas de pepinillos, 3 rodajas de cebolla, 1 bolsita de ketchup, 1 bolsita de mostaza, Sal, Aceite Vegetal.', 43.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/hamooburguesa/Hamooburguesa.png'),
	(5, 'Hamboorguesa Jr','1 Pan con Ajonjolí, 1 torta de Carne de res, 3 rodajas de tomate, 2 lechugas, 2 rodajas de pepinillos, Sal, Aceite Vegetal.', 39.00, '/utiles/imagenes/menu/comida/almuerzo/hamburguesas/hamooburguesa/HamooburguesaJr.png'),
    (5, 'Hamboorguesa de Tocino con Chedddar','1 Pan con Ajonjolí, 1/4 torta de Carne de res, 3 tocinos, 2 rebanadas de queso cheddar amarillo, 1 porción de Salsa de queso, 3 rodajas de cebollas caramelizadas, Sal, Aceite Vegetal.',50.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/hamooburguesa/HamooburguesaTocinoCheddar.png'),
 
    -- /*/*/*/*/*/*/* BIG MOO /*/*/*/*/*/*/*
    (6, 'Big Moo','2 tortas de carne de res, 1 porción de Salsa Big Mac, 2 lechugas, 2 rodajas de pepinillos, 3 rodajas de cebolla, 2 queso cheddar amarillo, 1 pan con ajonjolí, Sal, Aceite Vegetal.',46.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/bigMoo/BigMoo.png'),
    (6, 'Súper Big Moo','4 tortas de carne de res, 1 porción de Salsa Big Mac, 2 lechugas, 2 rodajas de pepinillos, 3 rodajas de cebolla, 2 queso cheddar amarillo, 1 1/2 pan con ajonjolí, Sal, Aceite Vegetal.',52.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/bigMoo/SuperBigMoo.png'),
 
    -- /*/*/*/*/*/*/* CUARTO DE MOO LIBRA /*/*/*/*/*/*/*
    (7, 'Cuarto de Moo Libra','1 torta de 1/4 de libra de carne de res, 3 rodajas de cebolla, 1 bolsita de ketchup, 1 porcion de mostaza, 2 rodajas de pepinillos, 2 queso cheddar amarillo, 1 pan con ajonjolí, Sal, Aceite Vegetal.', 51.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/cuartoMooLibra/CuartoMooLibra.png'),
	(7, 'Cuarto de Moo Libra con Queso','1 torta de 1/4 de libra de carne de res, 3 rodajas de cebolla, 1 bolsita de ketchup, 1 porcion de mostaza, 2 rodajas de pepinillos, 3 queso cheddar amarillo, 1 pan con ajonjolí, Sal, Aceite Vegetal.', 54.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/cuartoMooLibra/CuartoMooLibraQueso.png'),
	(7, 'Cuarto de Moo Libra con Tocino y Queso','1 torta de 1/4 de libra de carne de res, 2 tocinos, 3 rodajas de cebolla, 1 bolsita de ketchup, 1 porcion de mostaza, 2 rodajas de pepinillos, 2 queso cheddar amarillo, 1 pan con ajonjolí, Sal, Aceite Vegetal.', 55.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/cuartoMooLibra/CuartoMooLibraTocinoQueso.png'),
	(7, 'Cuarto de Moo Libra con Doble Tocino y Queso','1 torta de 1/4 de libra de carne de res, 4 tocinos, 3 rodajas de cebolla, 1 bolsita de ketchup, 1 porcion de mostaza, 2 rodajas de pepinillos, 2 queso cheddar amarillo, 1 pan con ajonjolí, Sal, Aceite Vegetal.', 60.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/cuartoMooLibra/CuartoMooLibraDobleTocinoQueso.png'),
    (7, 'Cuarto de Moo Libra con Doble Carne','2 tortas de 1/4 de libra de carne de res, 4 tocinos, 3 rodajas de cebolla, 1 bolsita de ketchup, 1 porcion de mostaza, 2 rodajas de pepinillos, 2 queso cheddar amarillo, 1 pan con ajonjolí, Sal, Aceite Vegetal.', 65.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/cuartoMooLibra/CuartoMooLibraDobleCarne.png'),
    (7, 'Cuarto de Moo Libra Deluxe con Queso','1 torta de 1/4 de libra de carne de res, 4 tocinos, 3 rodajas de cebolla, 1 bolsita de ketchup, 1 porcion de mostaza, 2 rodajas de pepinillos, 4 queso cheddar amarillo, 1 pan con ajonjolí, Sal, Aceite Vegetal.', 57.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/cuartoMooLibra/CuartoMooLibraDeluxeQueso.png'),
    (7, 'Cuarto de Moo Libra Deluxe con Doble Carne','2 tortas de 1/4 de libra de carne de res, 4 tocinos, 3 rodajas de cebolla, 1 bolsita de ketchup, 1 porcion de mostaza, 2 rodajas de pepinillos, 2 queso cheddar amarillo, 1 pan con ajonjolí, Sal, Aceite Vegetal.', 63.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/cuartoMooLibra/CuartoMooLibraDeluxeDobleCarne.png'),
 
    -- /*/*/*/*/*/*/* MOO INSANO /*/*/*/*/*/*/*
    (8,'Moo Insano de Carne','1 torta de 1/4 de libra de carne de res, 3 rodajas de cebolla, 1 porción de mayonesa, 3 rodajas de tomate, 2 lechugas, 1 bolsita de ketchup, 1 porción de mostaza, 2 rodajas de pepinillos, 2 queso cheddar amarillo, 1 pan con ajonjolí, Sal, Aceite Vegetal.',65.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/mooInsano/MooinsanoTortaCarne.png'),
    (8,'Moo Insano de Doble Carne','2 tortas de 1/4 de libra de carne de res, 3 rodajas de cebolla, 1 porción de mayonesa, 3 rodajas de tomate, 2 lechugas, 1 bolsita de ketchup, 1 porción de mostaza, 2 rodajas de pepinillos, 2 queso cheddar amarillo, 1 pan con ajonjolí, Sal, Aceite Vegetal.',70.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/mooInsano/MooinsanoDobleTortaCarne.png'),
 
    -- /*/*/*/*/*/*/* MOOVA BURGUESA /*/*/*/*/*/*/*
    (9,'MoovaBurguesa','1 pan de hamburguesa, 2 quesos cheddar amarillo, 1 torta de carne de res, 2 rodajas de pepinillos, 3 rodajas de cebolla, 1 bolsito de ketchup, 1 porción de mostaza, Sal, Aceite Vegetal.', 48.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/moovaBurguesa/MoovaBurguesa.png'),
    (9,'MoovaBurguesa Doble','1 pan de hamburguesa, 2 quesos cheddar amarillo, 2 tortas de carne de res, 2 rodajas de pepinillos, 3 rodajas de cebolla, 1 bolsito de ketchup, 1 porción de mostaza, Sal, Aceite Vegetal.',52.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/moovaBurguesa/MoovaBurguesaDoble.png'),
    (9,'MoovaBurguesa Triple','1 pan de hamburguesa, 2 quesos cheddar amarillo, 3 tortas de carne de res, 2 rodajas de pepinillos, 3 rodajas de cebolla, 1 bolsito de ketchup, 1 porción de mostaza, Sal, Aceite Vegetal.',57.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/moovaBurguesa/MoovaBurguesaTriple.png'),
 
	-- /*/*/*/*/*/*/* BIG MOOVA /*/*/*/*/*/*/*
    (10, 'Big Moova','1 Pan con ajonjolí, 2 rodajas pepinillos, 3 rodajas de cebolla, 3 rodajas de tomate, 2 lechugas, 3 queso cheddar blanco, 1 Salsa tasty, 1 torta de carne de res, Sal, Aceite Vegetal.',48.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/bigMoova/BigMoova.png'),
    (10, 'Big Moova Doble Carne','1 Pan con ajonjolí, 2 rodajas pepinillos, 3 rodajas de cebolla, 3 rodajas de tomate, 2 lechugas, 3 queso cheddar blanco, 1 Salsa tasty, 2 tortas de carne de res, Sal, Aceite Vegetal.', 54.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/bigMoova/BigMoovaDoble.png'),
    (10, 'Big Moova de Tocino','1 Pan con ajonjolí, 2 rodajas pepinillos, 3 rodajas de cebolla, 3 rodajas de tomate, 2 lechugas, 3 queso cheddar blanco, 1 Salsa tasty, 2 tocinos, Sal, Aceite Vegetal.',58.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/bigMoova/BigMoovaTocino.png'),
    (10, 'Big Moova Triple Carne','1 Pan con ajonjolí, 2 rodajas pepinillos, 3 rodajas de cebolla, 3 rodajas de tomate, 2 lechugas, 3 queso cheddar blanco, 1 Salsa tasty, 3 tortas de carne de res, Sal, Aceite Vegetal.',58.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/bigMoova/BigMoovaTripleCarne.png'),
 
    -- /*/*/*/*/*/*/* MOOVA CRISPY /*/*/*/*/*/*/*
    (11, 'Moova Crispy Tocino y Queso', '1 filete de pollo crispy, 2 tocinos, 1 salsa de queso, 2 queso cheddar amarillo, 3 cebollas caramelizadas, 1 pan brioche, Sal, Aceite Vegetal.', 42.00, '/utiles/imagenes/menu/comida/almuerzo/hamburguesas/moovaCrispy/MoovaCrispyTocinoQueso.png'),
    (11, 'Moova Crispy Tocino con Ranch', '1 filete de pollo crispy, 1 salsa Ranch, 3 rodajas de tomate, 2 lechugas, 1 pan brioche, Sal, Aceite Vegetal.', 45.00, '/utiles/imagenes/menu/comida/almuerzo/hamburguesas/moovaCrispy/MoovaCrispyTocinoRanch.png'),
    (11, 'Moova Crispy Deluxe', '1 filete de pollo crispy, 1 porción de Mayonesa, 3 rodajas de tomate, 2 lechugas, 1 pan brioche, Sal, Aceite Vegetal.', 49.00, '/utiles/imagenes/menu/comida/almuerzo/hamburguesas/moovaCrispy/MoovaCrispyDeluxe.png'),
 
    -- /*/*/*/*/*/*/* GOUR MOOVA /*/*/*/*/*/*/*
    (12, 'Gourmoova Crispy' ,'1 filete de pollo crispy, 1 pan artesanal, 3 rodajas de tomates, 2 lechugas, 3 rodajas de cebolla, 2 queso cheddar blanco, 1 mayonesa, Sal, Aceite Vegetal.',48.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/gourMoova/GourMoovaCrispy.png'),
    (12, 'Gourmoova Grilled','1 filete de pollo a la plancha, 1 pan artesanal, 3 rodajas de tomates, 2 lechugas, 3 rodajas de cebolla, 2 queso cheddar blanco, 1 mayonesa, Sal, Aceite Vegetal.',50.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/gourMoova/GourMoovaGrilled.png'),
    (12, 'Gourmoova de Res','1 torta de carne de res, 1 pan artesanal, 3 rodajas de tomates, 2 lechugas, 3 rodajas de cebolla, 2 queso cheddar blanco, 1 porción de mayonesa, Sal, Aceite Vegetal.',53.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/gourMoova/GourMoovaCarne.png'),

	-- /*/*/*/*/*/*/* PICO GUACAMOL GOUR MOOVA /*/*/*/*/*/*/*
    (13,'Pico Guacamol GourMoova Crispy', '1 filete de pollo crispy, 1 pan artesanal, 2 quesos cheddar blanco, 1 lechuga , 1 porción de pico de gallo, 1 porción de aderezo ranch, 1 porción de guacamol, Sal, Aceite Vegetal.' ,52.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/picoGuacamolGourMoova/PicoGuacamolGourmoovaCrispy.png'),
    (13,'Pico Guacamol GourMoova Grilled', '1 filete de pollo a la plancha, 1 pan artesanal, 2 quesos cheddar blanco, 1 lechuga , 1 porción de pico de gallo, 1 porción de aderezo ranch, 1 porción de guacamol, Sal, Aceite Vegetal.' ,52.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/picoGuacamolGourMoova/PicoGuacamolGourmoovaGrilled.png'),
    (13,'Pico Guacamol GourMoova de Res', '1 torta de carne de res, 1 pan artesanal, 2 quesos cheddar blanco, 1 lechuga , 1 porción de pico de gallo, 1 porción de aderezo ranch, 1 porción de guacamol, Sal, Aceite Vegetal.' ,54.00,'/utiles/imagenes/menu/comida/almuerzo/hamburguesas/picoGuacamolGourMoova/PicoGuacamolGourmoovaCarne.png'),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     POSTRE (3)     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    -- /*/*/*/*/*/*/* PASTELES /*/*/*/*/*/*/*
    -- //// PIE
    (14, 'Pie de Manzana','1 lámina de masa de hojaldre, 2 manzanas Verdes, 2 cucharadas de mantequilla, 4 cucharadas de azúcar moreno, 1 cucharada de canela en polvo, 1 pizca de nuez moscada, 1 cucharada de jugo de limón, 1 cucharada de fécula de maíz, Agua, Aceite para freír.',12.00,'/utiles/imagenes/menu/comida/postres/pies/PieManzana.png'),
    (14, 'Pie de Queso','1 lámina de masa de hojaldre, 14 cucharadas de queso crema, 3 cucharadas de azúcar glass, 1 cucharada de esencia de vainilla, 1 cucharada de jugo de limón, Aceite para freír.',12.00,'/utiles/imagenes/menu/comida/postres/pies/PieQueso.png'),
    
    -- //// PASTELES
    (15,'Pastel de Chocolate', 'Masa: 32 cucharadas de harina de trigo, 32 cucharadas de azúcar morena, 12 cucharadas de cacao en polvo, 1 cucharada de bicarbonato de sodio, 1 cucharada de sal, 2 huevos, 16 cucharadas de leche, 8 cucharadas de aceite vegetal, 2 cucharadas de esencia de vainilla, 16 cucharadas de agua. Cobertura: 20 cucharadas de chocolate semiamargo, 20 cucharadas de crema para batir, 2 cucharadas de mantequilla',18.00,'/utiles/imagenes/menu/comida/postres/pasteles/PastelChocolate.png'),
	
    -- //// CHEESECAKE
    (15,'Cheesecake de Fresa', 'Mermelada: 8 cucharadas taza de fresas picadas, 3 cucharadas de azúcar morena, 1 cucharada de jugo de limón, 1 cucharada de fécula de maíz, Agua. Crema: 20 cucharadas de queso crema, 4 cucharadas de azúcar glass, 1 cucharada de esencia de vainilla. Masa: 2 láminas de masa de hojaldre, 1 huevo, Aceite.',18.00,'/utiles/imagenes/menu/comida/postres/pasteles/CheesecakeFresa.png'),
    (15,'Cheesecake de Mora', 'Mermelada: 8 cucharadas de moras picadas, 3 cucharadas de azúcar morena, 1 cucharada de jugo de limón, 1 cucharada de fécula de maíz, Agua. Crema: 20 cucharadas de queso crema, 4 cucharadas de azúcar glass, 1 cucharada de esencia de vainilla. Masa: 2 láminas de masa de hojaldre, 1 huevo, Aceite.',18.00,'/utiles/imagenes/menu/comida/postres/pasteles/CheesecakeMora.png'),

	-- /*/*/*/*/*/*/* HELADOS /*/*/*/*/*/*/*
    -- //// MOOFLURRY
    -- Oreo
    (16, 'MooFlurry de Oreo','1 helado de vainilla, 5 galletas oreo trituradas',15.00,'/utiles/imagenes/menu/comida/postres/helados/MooFlurryOreo.png'),
    -- M&Ms
    (16, 'MooFlurry de M&Ms','1 helado de vainilla, 1 Bolsa de M&Ms',15.00,'/utiles/imagenes/menu/comida/postres/helados/MooFlurryMyMs.png'),
    
    -- //// HELADOS EN CONO
    (17, 'Helado de Cono de Vainilla','1 helado de Vainilla, cono waffle',8.00,'/utiles/imagenes/menu/comida/postres/helados/HeladoConoVainilla.png'),
    (17, 'Helado de Cono de Chocolate','1 helado de chocolate, cono waffle',8.00,'/utiles/imagenes/menu/comida/postres/helados/HeladoConoChocolate.png'),
    (17, 'Helado de Cono Mixto','1 helado de vainilla, 1 helado de chocolate, cono waffle',8.00,'/utiles/imagenes/menu/comida/postres/helados/HeladoConoMixto.png'),
    
    -- //// MOOVANDAE
    (18, 'Moovadae','1 helado de vainilla en vaso',11.00,'/utiles/imagenes/menu/comida/postres/helados/MoovandaeGeneral.png'),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     ANTOJO (4)     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    -- /*/*/*/*/*/*/* DERRETIDO CLASICO /*/*/*/*/*/*/*
    (20, 'Derretido Moo Clásico','1 Pan de Hamburguesa, 1 queso cheddar amarillo, Sal, Aceite Vegetal.', 10.00,'/utiles/imagenes/menu/comida/antojo/DerretidoMooClasico.png'),

	-- /*/*/*/*/*/*/* PAPAS /*/*/*/*/*/*/*
	-- //// HASHBROWN
    (21, 'Papas Medianas','1 porción de papas, aceite vegetal, sal', 12.00,'/utiles/imagenes/menu/comida/antojo/papas/PapasMedianas.png'),
    (21, 'Papas Grandes','2 porciones de papas, aceite vegetal, sal', 16.00,'/utiles/imagenes/menu/comida/antojo/papas/PapasGrandes.png'),
    (22, 'MooPatatas','1 porción de papas, aceite vegetal, paprika, pimienta negra molida, fécula de maíz, ajo en polvo, cebolla en polvo.', 18.00,'/utiles/imagenes/menu/comida/antojo/papas/MooPatatas.png'),
    (23, 'Hashbrown','1 porción de papas, aceite vegetal', 5.00,'/utiles/imagenes/menu/comida/antojo/papas/Hasbrown.png'),
    
    -- //// MOOGGETS
    (24, 'Mooggets 4 Piezas','4 piezas de pollo redondas pequeñas, Sal, Aceite Vegetal.',15.00,'/utiles/imagenes/menu/comida/antojo/mooggets/Mooggets4Pz.png'),
    (24, 'Mooggets 6 Piezas','6 piezas de pollo redondas pequeñas, Sal, Aceite Vegetal.',20.00,'/utiles/imagenes/menu/comida/antojo/mooggets/Mooggets6Pz.png'),
    
    -- //// TOSTADOS
    (25, 'Tostado de Queso con Frijol','1 pan artesanal, 2 quesos cheddar blanco, 1 porción de frijol, 1 porción de mayonesa, Sal, Aceite Vegetal.',16.00, '/utiles/imagenes/menu/comida/antojo/tostados/TostadoQuesoFrijol.png'),
    (25, 'Tostado de Queso con Jamón','1 pan artesanal, 2 quesos cheddar blanco, 1 rebanada de jamón, 1 porción de mayonesa, Sal, Aceite Vegetal.',17.00, '/utiles/imagenes/menu/comida/antojo/tostados/TostadoQuesoJamon.png'),
    (25, 'Tostado de Queso con Tomate','1 pan artesanal, 2 quesos cheddar blanco, 1 porción de tomate, 1 porción de mayonesa, Sal, Aceite Vegetal.',17.00, '/utiles/imagenes/menu/comida/antojo/tostados/TostadoQuesoTomate.png'),
	
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     BEBIDAS CALIENTES (5)     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    -- /*/*/*/*/*/*/* CHOCOLATE /*/*/*/*/*/*/*
    (26, 'Chocolate','Cocoa en polvo, agua, azúcar, leche',14.00,'/utiles/imagenes/menu/comida/bebidas/calientes/Chocolate.png'),
    
    -- /*/*/*/*/*/*/* CAFE MOO /*/*/*/*/*/*/*
    (27, 'Café MOO','Granos de café, agua',10.00,'/utiles/imagenes/menu/comida/bebidas/calientes/cafe/Cafe.png'),
    (27, 'Café Moo con Leche','Granos de café, agua, leche',13.00,'/utiles/imagenes/menu/comida/bebidas/calientes/cafe/MooLeche.png'),
    
    -- /*/*/*/*/*/*/* MOOVACCINO /*/*/*/*/*/*/*
    (28, 'Moovaccino','2 cucharadas de Leche al vapor, cafe y agua',17.00,'/utiles/imagenes/menu/comida/bebidas/calientes/cafe/Moovaccino.png'),
    
    -- /*/*/*/*/*/*/* TE /*/*/*/*/*/*/*
    (29,'Té Moo','1 Infusión de té negro, azúcar, agua.',12.00,'/utiles/imagenes/menu/comida/bebidas/calientes/te/Te.png'),
    (29,'Té Moo de Manzanilla','1 Infusión de té de manzanilla, 2 hojas de menta y 2 flores de caléndula, agua.',14.00,'/utiles/imagenes/menu/comida/bebidas/calientes/te/Manzanilla.png'),
    (29,'Té Chai de Vainilla','1 Infusión de té negro, 1 cucharada de canela en polvo, 1 cardamomo, 1 clavo de olor, 1 cucharada de esencia vainilla, 2 azúcar, Agua o leche.',18.00,'/utiles/imagenes/menu/comida/bebidas/calientes/te/ChaiVainilla.png'),
    (29,'Té Moo de Bora Bora','1 Infusión de rosa de Jamaica, papaya, manzana, pasas, fresas y agua.',14.00,'/utiles/imagenes/menu/comida/bebidas/calientes/te/BoraBora.png'),
    (29,'Té Moo de Menta','1 Infusión de té de limón, 2 hojas de menta, 2 hojas de hierbabuena, 2 rodajas de jengibre y agua.',15.00,'/utiles/imagenes/menu/comida/bebidas/calientes/te/Menta.png'),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     BEBIDAS FRÍAS (6)     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    
    -- /*/*/*/*/*/*/* MOO FIZZ /*/*/*/*/*/*/*
    (30,'MooFizz Rosado','1 lata de Sprite, Saborizante de Pitaya, Azúcar y hielo', 22.00, '/utiles/imagenes/menu/comida/bebidas/frias/mooFiz/Rosado.png'),
    (30,'MooFizz Verde','1 lata de Sprite, Saborizante de Manzana verde, Azúcar y hielo', 22.00, '/utiles/imagenes/menu/comida/bebidas/frias/mooFiz/Verde.png'),
    (30,'MooFizz Azul','1 lata de Sprite, Saborizante de Frambuesa azul, Azúcar y hielo', 22.00, '/utiles/imagenes/menu/comida/bebidas/frias/mooFiz/Azul.png'),
    (30,'MooFizz Amarillo ','1 lata de Sprite, Saborizante de Naranja, Azúcar y hielo', 22.00, '/utiles/imagenes/menu/comida/bebidas/frias/mooFiz/Amarillo.png'),
    
    -- /*/*/*/*/*/*/* SMOOTHIE /*/*/*/*/*/*/*
    (31, 'Smoothie de Frutos Rojos','4 fresas, 4 frambuesas, 4 moras, Azúcar hielo y agua o leche.',25.00,'/utiles/imagenes/menu/comida/bebidas/frias/smoothie/FrutosRojos.png'),
    (31, 'Smoothie de Mango','1 mango mediano, Azúcar hielo y agua o leche',25.00,'/utiles/imagenes/menu/comida/bebidas/frias/smoothie/Mango.png'),
    
    -- /*/*/*/*/*/*/* FRAPPÉ /*/*/*/*/*/*/*
    (32, 'Frappé de Oreo','1 Expreso frío de café, 16 cucharadas de leche, 3 chocolates, 2 galletas Oreo, hielo y 2 cucharadas de azúcar.',27.00,'/utiles/imagenes/menu/comida/bebidas/frias/frappe/Oreo.png'),
    (32, 'Frappé de Vainilla','1 Expreso frío de café, 16 cucharadas de leche, 1 cucharada de saborizante de vainilla, hielo y 2 cucharadas de azúcar',27.00,'/utiles/imagenes/menu/comida/bebidas/frias/frappe/Vainilla.png'),
    (32, 'Frappé de Chocolate','1 Expreso frío de café, 16 cucharadas de leche, 1 cucharada de saborizante de chocolate, hielo y 2 cucharadas de azúcar',27.00,'/utiles/imagenes/menu/comida/bebidas/frias/frappe/Chocolate.png'),
    (32, 'Frappé de Caramelo','1 Expreso frío de café, 16 cucharadas de leche, 1 cucharada de saborizante de caramelo, hielo y 2 cucharadas de azúcar',27.00,'/utiles/imagenes/menu/comida/bebidas/frias/frappe/Caramelo.png'),
    
    -- /*/*/*/*/*/*/* MOOVA ICED /*/*/*/*/*/*/*
    (33,'Moova Iced','3 cucharadas de café, 3 cucharadas de leche, hielo, 2 cucharadas de azúcar,',24.00,'/utiles/imagenes/menu/comida/bebidas/frias/moovaIced/MoovaIced.png'),
    (33,'Moova Iced de Vainilla','3 cucharadas de café, 3 cucharadas de leche, hielo, 2 cucharadas de azúcar, saborizante de Vainilla,',24.00,'/utiles/imagenes/menu/comida/bebidas/frias/moovaIced/Vainilla.png'),
    (33,'Moova Iced de Chocolate','3 cucharadas de café, 3 cucharadas de leche, hielo, 2 cucharadas de azúcar, saborizante de Chocolate',24.00,'/utiles/imagenes/menu/comida/bebidas/frias/moovaIced/Chocolate.png'),
    (33,'Moova Iced de Caramelo','3 cucharadas de café, 3 cucharadas de leche, hielo, 2 cucharadas de azúcar, saborizante de Caramelo',24.00,'/utiles/imagenes/menu/comida/bebidas/frias/moovaIced/Caramelo.png'),
    
    -- /*/*/*/*/*/*/* MOOOVA CHAI FRAPPÉ /*/*/*/*/*/*/*
    (34,'Moova Chai Frappé de Té Verde', '1 infusión de té verde, 1 cucharada de canela en polvo, 1 cardamomo, 2 cucharadas de azúcar, hielo y leche o agua',27.00,'/utiles/imagenes/menu/comida/bebidas/frias/moovaChaiFrappe/TeVerde.png'),
    (34,'Moova Chai Frappé de Vainilla', '1 infusión de té Vainilla, 1 cucharada de canela en polvo, 1 cardamomo, 2 cucharadas de azúcar, hielo y leche o agua',27.00,'/utiles/imagenes/menu/comida/bebidas/frias/moovaChaiFrappe/Vainilla.png'),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     GASEOSAS (7)     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    
    -- /*/*/*/*/*/*/* COCA-COLA /*/*/*/*/*/*/*
    (35,  'Coca Cola','Hielos',11.00, '/utiles/imagenes/menu/comida/bebidas/gaseosas/CocaCola.png'),
    (35,  'Coca Cola sin Azúcar','Hielos',11.00, '/utiles/imagenes/menu/comida/bebidas/gaseosas/CocaColaSinAzucar.png'),
    -- /*/*/*/*/*/*/* VARIOS /*/*/*/*/*/*/*
    (36,  'Fanta','Hielos',11.00, '/utiles/imagenes/menu/comida/bebidas/gaseosas/Fanta.png'),
    (36,  'Sprite','Hielos',11.00, '/utiles/imagenes/menu/comida/bebidas/gaseosas/Sprite.png'),
    -- /*/*/*/*/*/*/* TÉ LIPTON /*/*/*/*/*/*/*
    (37,  'Te Lipton de Limón','Hielos',12.00, '/utiles/imagenes/menu/comida/bebidas/gaseosas/TeLiptonLimon.png'),
    (37,  'Te Lipton de Frambuesa','Hielos',12.00, '/utiles/imagenes/menu/comida/bebidas/gaseosas/TeLiptonFrambuesa.png'),
    (37,  'Te Lipton de Durazno','Hielos',12.00, '/utiles/imagenes/menu/comida/bebidas/gaseosas/TeLiptonDurazno.png'),
    
	-- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     JUGO NATURAL (8)     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
   
   -- /*/*/*/*/*/*/* FLOR /*/*/*/*/*/*/*
    (38, 'Jugo de Jamaica', 'Esencia de Jamaica, Agua, 2 cucharadas de azúcar, Hielos', 8.00, '/utiles/imagenes/menu/comida/bebidas/jugoNatural/Jamaica.png'),
    -- /*/*/*/*/*/*/* FRUTA /*/*/*/*/*/*/*
    (39, 'Jugo de Manzana', '2 Manzanas, Agua, 2 cucharadas de azúcar, Hielos', 8.00, '/utiles/imagenes/menu/comida/bebidas/jugoNatural/Manzana.png'),
    (39, 'Jugo de Mora', '2 Mora, Agua, 2 cucharadas de azúcar, Hielos', 8.00, '/utiles/imagenes/menu/comida/bebidas/jugoNatural/Naranja.png'),
    
    -- //// YOGURT
    (19, 'Yogurt Líquido de Fresa','',5.00,'/utiles/imagenes/menu/ingredientes/lacteos/YogurtFresa.png');
-- INGRESO DE DATOS
-- /////////  TABLA: INGREDIENTE ///////// 
INSERT INTO Ingrediente(nombre, precio, imagen) VALUES

	-- /*/*/*/*/*/*/* ADEREZOS /*/*/*/*/*/*/*
	('Ranch',5.00,'/utiles/imagenes/menu/ingredientes/aderezos/Ranch.png'),
    ('Tasty',5.00,'/utiles/imagenes/menu/ingredientes/aderezos/Tasty.png'),
    ('Vinagreta',5.00,'/utiles/imagenes/menu/ingredientes/aderezos/Vinagreta.png'),
    
    -- /*/*/*/*/*/*/* CAFE /*/*/*/*/*/*/* 
    ('Expreso de Café',12.00,'/utiles/imagenes/menu/ingredientes/cafe/ExpresoCafe.png'),
    ('Granos de Café',7.00,'/utiles/imagenes/menu/ingredientes/cafe/GranoCafe.png'),
    
    -- /*/*/*/*/*/*/* CARNES /*/*/*/*/*/*/* 
    ('Torta de Carne de Res',7.00,'/utiles/imagenes/menu/ingredientes/carnes/CarneRes.png'),
    ('Rebanada de Jamón',5.00,'/utiles/imagenes/menu/ingredientes/carnes/Jamon.png'),
    ('Porción de Tocino',5.00,'/utiles/imagenes/menu/ingredientes/carnes/Tocino.png'),
    ('Torta de Salchicha',5.00,'/utiles/imagenes/menu/ingredientes/carnes/TortaSalchicha.png'),
    
    -- /*/*/*/*/*/*/*  CONDIMENTOS /*/*/*/*/*/*/* 
    ('Cucharada de Ajo en Polvo',2.00,'/utiles/imagenes/menu/ingredientes/condimentos/AjoPolvo.png'),
    ('Cucharada de Bicarbonato de Sodio',2.00,'/utiles/imagenes/menu/ingredientes/condimentos/Bicarbonato.png'),
    ('Cucharada de Cacao en Polvo',3.00,'/utiles/imagenes/menu/ingredientes/condimentos/CacaoPolvo.png'),
    ('Cucharada de Canela en Polvo',2.00,'/utiles/imagenes/menu/ingredientes/condimentos/CanelaPolvo.png'),
    ('Cucharada de Cebolla en Polvo',3.00,'/utiles/imagenes/menu/ingredientes/condimentos/CebollaPolvo.png'),
    ('Cucharada de Paprika en Polvo',3.00, '/utiles/imagenes/menu/ingredientes/condimentos/Paprika.png'),
    ('Cucharada de Pimienta Negra Molida',2.00,'/utiles/imagenes/menu/ingredientes/condimentos/PimientaNegra.png'),
    ('Cucharada de Sal',2.00,'/utiles/imagenes/menu/ingredientes/condimentos/Sal.png'),
    
    -- /*/*/*/*/*/*/* ESENCIA Y SABORIZANTES /*/*/*/*/*/*/* 
    -- //// ESENCIA
    ('Cucharada de esencia de Vainilla',3.00,'/utiles/imagenes/menu/ingredientes/esenciaSaborizante/EsenciaVainilla.png'),
    
    -- //// SABORIZANTE
    ('Cucharada de saborizante de Caramelo',3.00,'/utiles/imagenes/menu/ingredientes/esenciaSaborizante/SaborizanteCaramelo.png'),
    ('Cucharada de saborizante de Chocolate',3.00,'/utiles/imagenes/menu/ingredientes/esenciaSaborizante/SaborizanteChocolate.png'),
    ('Cucharada de saborizante de Frambuesa Azul',3.00,'/utiles/imagenes/menu/ingredientes/esenciaSaborizante/SaborizanteFrambuesaAzul.png'),
    ('Cucharada de saborizante de Manzana Verde',3.00,'/utiles/imagenes/menu/ingredientes/esenciaSaborizante/SaborizanteManzanaVerde.png'),
    ('Cucharada de saborizante de Naranja',3.00,'/utiles/imagenes/menu/ingredientes/esenciaSaborizante/SaborizanteNaranja.png'),
    ('Cucharada de saborizante de Pitaya',3.00,'/utiles/imagenes/menu/ingredientes/esenciaSaborizante/SaborizantePitaya.png'),
    ('Cucharada de saborizante de Vainilla',3.00,'/utiles/imagenes/menu/ingredientes/esenciaSaborizante/SaborizanteVainilla.png'),

	-- /*/*/*/*/*/*/* FRUTAS /*/*/*/*/*/*/*
    ('Arándanos',3.00,'/utiles/imagenes/menu/ingredientes/frutas/Arandanos.png'),
    ('Frambuesa',3.00,'/utiles/imagenes/menu/ingredientes/frutas/Frambuesa.png'),
    ('Fresas',4.00,'/utiles/imagenes/menu/ingredientes/frutas/Fresas.png'),
    ('Jengibre',3.00,'/utiles/imagenes/menu/ingredientes/frutas/Jengibre.png'),
    ('Mango',4.00,'/utiles/imagenes/menu/ingredientes/frutas/Mango.png'),
    ('Manzana',3.00,'/utiles/imagenes/menu/ingredientes/frutas/Manzana.png'),
    ('Manzana Verde', 4.00, '/utiles/imagenes/menu/ingredientes/frutas/ManzanaVerde.png'),
    ('Melocotón',3.00,'/utiles/imagenes/menu/ingredientes/frutas/Melocoton.png'),
    ('Moras',4.00,'/utiles/imagenes/menu/ingredientes/frutas/Moras.png'),
    ('Papaya',3.00,'/utiles/imagenes/menu/ingredientes/frutas/Papaya.png'),
    ('Pasas',4.00,'/utiles/imagenes/menu/ingredientes/frutas/Pasas.png'),
    ('Rosa Jamaica',3.00,'/utiles/imagenes/menu/ingredientes/frutas/RosaJamaica.png'),
    
    -- /*/*/*/*/*/*/* INFUSIÓN /*/*/*/*/*/*/*
    ('Infusión de Té de Verde',4.00,'/utiles/imagenes/menu/ingredientes/infusion/InfusionTeVerde.png'),
    ('Infusión de Té de Limón',4.00,'/utiles/imagenes/menu/ingredientes/infusion/InfusionTeLimon.png'),
    ('Infusión de Té de Manzanilla',4.00,'/utiles/imagenes/menu/ingredientes/infusion/InfusionTeManzanilla.png'),
    ('Infusión de Té de Negro',4.00,'/utiles/imagenes/menu/ingredientes/infusion/InfusionTeNegro.png'),
    
    -- /*/*/*/*/*/*/* LÁCTEOS /*/*/*/*/*/*/*
    -- //// QUESO CHEDDAR 
    ('Queso Cheddar Amarillo',3.00,'/utiles/imagenes/menu/ingredientes/lacteos/CheddarAmarillo.png'),
    ('Queso Cheddar Blanco',4.00,'/utiles/imagenes/menu/ingredientes/lacteos/CheddarBlanco.png'),
    
    -- //// CREMA
    ('Porción de Crema',4.00,'/utiles/imagenes/menu/ingredientes/lacteos/Crema.png'),
    
    -- //// QUESO EMMENTAL
    ('Queso Emmental',5.00,'/utiles/imagenes/menu/ingredientes/lacteos/Emmental.png'),
    
    -- //// QUESO FRESCO
    ('Queso Fresco',3.00,'/utiles/imagenes/menu/ingredientes/lacteos/Fresco.png'),
    
    -- //// HELADOS
    ('Porción de Helado de Chocolate',4.00,'/utiles/imagenes/menu/ingredientes/lacteos/HeladoChocolate.png'),
    ('Porción de Helado de Vainilla',4.00,'/utiles/imagenes/menu/ingredientes/lacteos/HeladoVainilla.png'),
    
    -- //// LECHE
    ('Leche Descremada Light',5.00,'/utiles/imagenes/menu/ingredientes/lacteos/LecheDescremadaLight.png'),
    ('Leche Deslactosada',5.00,'/utiles/imagenes/menu/ingredientes/lacteos/LecheDeslactosada.png'),
    ('Leche Entera',5.00,'/utiles/imagenes/menu/ingredientes/lacteos/LecheEntera.png'),
    ('Leche Evaporada',5.00,'/utiles/imagenes/menu/ingredientes/lacteos/LecheEvaporada.png'),
    ('Leche Semi Deslactosada',5.00,'/utiles/imagenes/menu/ingredientes/lacteos/LecheSemiDeslactosada.png'),
    
    -- //// MANTEQUILLA
    ('Cucharada de Mantequilla',3.00,'/utiles/imagenes/menu/ingredientes/lacteos/Mantequilla.png'),
    
    -- //// QUESO CREMA
    ('Cucharada de Queso Crema',3.00,'/utiles/imagenes/menu/ingredientes/lacteos/QuesoCrema.png'),
    
    -- //// YOGURT
    ('Yogurt Líquido de Fresa',5.00,'/utiles/imagenes/menu/ingredientes/lacteos/YogurtFresa.png'),
    
    -- /*/*/*/*/*/*/* OTROS /*/*/*/*/*/*/*
    -- //// AGUA
    ('Agua',3.00,'/utiles/imagenes/menu/ingredientes/otros/Agua.png'),
    
    -- //// ACEITE
    ('Aceite Oliva',3.00,'/utiles/imagenes/menu/ingredientes/otros/AceiteOliva.png'),
    ('Aceite Vegetal',3.00,'/utiles/imagenes/menu/ingredientes/otros/AceiteVegetal.png'),
    
    -- //// HIELO
    ('Hielo',3.00,'/utiles/imagenes/menu/ingredientes/otros/Hielo.png'),
    
    -- /*/*/*/*/*/*/* HARINAS /*/*/*/*/*/*/* 
    -- //// PANES
    ('Pan de Hamburguesa con Ajonjolí',5.00,'/utiles/imagenes/menu/ingredientes/harinas/Ajonjoli.png'),
    ('Pan de Hamburguesa Artesanal',5.00,'/utiles/imagenes/menu/ingredientes/harinas/Artesanal.png'),
    ('Bollo',3.00,'/utiles/imagenes/menu/ingredientes/harinas/Bollo.png'),
    ('Pan Brioche',5.00,'/utiles/imagenes/menu/ingredientes/harinas/Brioche.png'),
    ('Pan de Hamburguesa',4.00,'/utiles/imagenes/menu/ingredientes/harinas/Hamburguesa.png'),
    ('Pan Muffin',5.00,'/utiles/imagenes/menu/ingredientes/harinas/Muffin.png'),
    
    -- //// CONO
    ('Cono',5.00,'/utiles/imagenes/menu/ingredientes/harinas/Cono.png'),
    
    -- //// HARINAS 
    ('Cucharada de Harina de Trigo',2.00, '/utiles/imagenes/menu/ingredientes/harinas/HarinaTrigo.png'),
    ('Cucharada de Maizena',2.00, '/utiles/imagenes/menu/ingredientes/harinas/Maizena.png'),
    
    -- //// MASA
    ('Lámina de masa de Hojaldre',2.00, '/utiles/imagenes/menu/ingredientes/harinas/MasaHojaldre.png'),
    
    -- /*/*/*/*/*/*/* PLANTAS /*/*/*/*/*/*/* 
    ('Cardamomo',2.00,'/utiles/imagenes/menu/ingredientes/plantas/Cardamomo.png'),
    ('Clavo de Olor',2.00,'/utiles/imagenes/menu/ingredientes/plantas/ClavoOlor.png'),
    ('Flores de Cálendula',2.00,'/utiles/imagenes/menu/ingredientes/plantas/FloresCalendula.png'),
    ('Hojas de Hierba Buena',2.00,'/utiles/imagenes/menu/ingredientes/plantas/HojaHierbaBuena.png'),
    ('Hoja de Menta',2.00,'/utiles/imagenes/menu/ingredientes/plantas/HojaMenta.png'),
    ('Nuez Moscada',3.00,'/utiles/imagenes/menu/ingredientes/plantas/NuezMoscada.png'),
    
    -- /*/*/*/*/*/*/* REPOSTERÍA /*/*/*/*/*/*/* 
    -- //// AZÚCAR
    ('Cucharada de Azúcar Blanca',3.00,'/utiles/imagenes/menu/ingredientes/reposteria/AzucarBlanca.png'),
    ('Cucharada de Azucar Glass',3.00,'/utiles/imagenes/menu/ingredientes/reposteria/AzucarGlass.png'),
    ('Cucharada de Azúcar Morena',3.00,'/utiles/imagenes/menu/ingredientes/reposteria/AzucarMorena.png'),
    ('Cucharada de Chocolate Semi Amargo',4.00,'/utiles/imagenes/menu/ingredientes/reposteria/ChocolateSemiAmargo.png'),
    ('Cucharada de Crema para Batir',4.00,'/utiles/imagenes/menu/ingredientes/reposteria/CremaBatir.png'),
    
    -- /*/*/*/*/*/*/* SALSAS /*/*/*/*/*/*/* 
    ('Porción de Barbacoa',6.00,'/utiles/imagenes/menu/ingredientes/salsas/Barbacoa.png'),
    ('Porción de BigMac',6.00,'/utiles/imagenes/menu/ingredientes/salsas/BigMac.png'),
    ('Porción de Chirmol',6.00,'/utiles/imagenes/menu/ingredientes/salsas/Chirmol.png'),
    ('Porción de Kétchup',4.00,'/utiles/imagenes/menu/ingredientes/salsas/Ketchup.png'),
    ('Porción de Mayonesa',4.00,'/utiles/imagenes/menu/ingredientes/salsas/Mayonesa.png'),
    ('Porción de Mostaza',4.00,'/utiles/imagenes/menu/ingredientes/salsas/Mostaza.png'),
    ('Porción de Queso',6.00,'/utiles/imagenes/menu/ingredientes/salsas/Queso.png'),
    ('Porción de Ranch',6.00,'/utiles/imagenes/menu/ingredientes/salsas/Ranch.png'),
    ('Porción de Tasty',6.00,'/utiles/imagenes/menu/ingredientes/salsas/Tasty.png'),
    
    -- /*/*/*/*/*/*/* TOPPING /*/*/*/*/*/*/* 
    ('Cucharada de Miel de Maple',3.00,'/utiles/imagenes/menu/ingredientes/topping/MielMaple.png'),
    ('Galleta Oreo',3.00,'/utiles/imagenes/menu/ingredientes/topping/Oreo.png'),
    ('Caramelo',3.00,'/utiles/imagenes/menu/ingredientes/topping/Caramelo.png'),
    ('Chocolate',3.00,'/utiles/imagenes/menu/ingredientes/topping/Chocolate.png'),
    ('Fresa',3.00,'/utiles/imagenes/menu/ingredientes/topping/Fresa.png'),
    ('MyMs',3.00,'/utiles/imagenes/menu/ingredientes/topping/MyMs.png'),
    
    -- /*/*/*/*/*/*/* TORTILLAS /*/*/*/*/*/*/* 
    ('Porción de tortilla',4.00,'/utiles/imagenes/menu/ingredientes/tortilla/Tortilla.png'),
    ('Tortilla de Harina',5.00,'/utiles/imagenes/menu/ingredientes/tortilla/TortillaHarina.png'),
    
    -- /*/*/*/*/*/*/* VERDURAS /*/*/*/*/*/*/*
    ('Aguacate',3.00,'/utiles/imagenes/menu/ingredientes/verduras/Aguacate.png'),
    ('Cebolla',3.00,'/utiles/imagenes/menu/ingredientes/verduras/Cebolla.png'),
    ('Cebolla Caramelizada',4.00,'/utiles/imagenes/menu/ingredientes/verduras/CebollaCaramelizada.png'),
    ('Cebolla Frita',4.00,'/utiles/imagenes/menu/ingredientes/verduras/CebollaFrita.png'),
    ('Chile Pimiento',3.00,'/utiles/imagenes/menu/ingredientes/verduras/ChilePimiento.png'),
    ('Porción de Frijol',5.00,'/utiles/imagenes/menu/ingredientes/verduras/Frijol.png'),
    ('Porción de Jugo de Limón', 4.00, '/utiles/imagenes/menu/ingredientes/verduras/JugoLimon.png'),
    ('Lechuga',3.00, '/utiles/imagenes/menu/ingredientes/verduras/Lechuga.png'),
    ('Papa',3.00,'/utiles/imagenes/menu/ingredientes/verduras/Papa.png'),
    ('Pepinillos',3.00,'/utiles/imagenes/menu/ingredientes/verduras/Pepinillos.png'),
    ('Pepino',3.00,'/utiles/imagenes/menu/ingredientes/verduras/Pepino.png'),
    ('Porción de plátanos fritos',4.00,'/utiles/imagenes/menu/ingredientes/verduras/PlatanoFrito.png'),
    ('Tomate',3.00,'/utiles/imagenes/menu/ingredientes/verduras/Tomate.png'),
    ('Zanahoria',3.00,'/utiles/imagenes/menu/ingredientes/verduras/Zanahoria.png'),
    
    -- //// HUEVO
    ('Huevo',4.00,'/utiles/imagenes/menu/ingredientes/carnes/Huevo.png'),
	-- //// PANQUEQUES
    ('Porción Panqueques',7.00, '/utiles/imagenes/menu/ingredientes/harinas/Panqueque.png'),
    
    -- 1/4 DE LIBRA DE CARNE DE RES
    ('1/4 de Libra de Carne de Res',7.00,'/utiles/imagenes/menu/ingredientes/carnes/CuartoLibra.png'),
    
    -- /*/*/*/*/*/*/* POLLO /*/*/*/*/*/*/*
    ('Filete de Pollo Crispy',6.00,'/utiles/imagenes/menu/ingredientes/pollo/FileteCrispy.png'),
    ('Filete de Pollo a la Plancha',6.00,'/utiles/imagenes/menu/ingredientes/pollo/FiletePlancha.png');

-- ///////// TABLA: RECETA /////////
INSERT INTO Receta(id_Producto, id_Ingrediente, cantidad) VALUES
	
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     DESAYUNO     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    
    -- ============================ MOOFFIN ============================
    
    -- /*/*/*/*/* MOOFFIN TOCINO Y QUESO MOOMELT /*/*/*/*/*
    -- Pan Muffin
    (1,66,1),
    -- Mantequilla
    (1,54,1),
    -- Tocino
    (1,8,1),
    -- Salchicha
    (1,9,1),
    -- Queso Cheddar Amarillo
    (1,42,1),
    -- Sal
    (1,17,1),
    -- Aceite Vegetal
    (1,59,1),
    
    -- /*/*/*/*/* MOOFFIN DE SALCHICHA /*/*/*/*/*
    -- Pan Muffin
    (2,66,1),
    -- Salchicha
    (2,9,1),
    -- Queso Cheddar Amarillo
    (2,42,1),
    -- Sal
    (2,17,1),
    -- Aceite Vegetal
    (2,59,1),
    
    -- /*/*/*/*/* MOOFFIN SALCHICHA Y HUEVO /*/*/*/*/*
    -- Pan Muffin
    (3,66,1),
    -- Salchicha
    (3,9,1),
    -- Queso Cheddar Amarillo
    (3,42,1),
    -- Mantequilla
    (3,54,1),
    -- Huevo
    (3,113,1),
    -- Sal
    (3,17,1),
    -- Aceite Vegetal
    (3,59,1),
    
    -- /*/*/*/*/* MOOFFIN DE DOBLE SALCHICHA Y HUEVO /*/*/*/*/* 
    -- Pan Muffin
    (4,66,1),
    -- Salchicha
    (4,9,2),
    -- Queso Cheddar Amarillo
    (4,42,1),
    -- Mantequilla
    (4,54,1),
    -- Huevo
    (4,113,1),
    -- Sal
    (4,17,1),
    -- Aceite Vegetal
    (4,59,1),
    
    -- /*/*/*/*/* MOOFFIN DE SALCHICHA Y DOBLE HUEVO /*/*/*/*/* 
    -- Pan Muffin
    (5,66,1),
    -- Salchicha
    (5,9,1),
    -- Queso Cheddar Amarillo
    (5,42,1),
    -- Mantequilla
    (5,54,1),
    -- Huevo
    (5,113,2),
    -- Sal
    (5,17,1),
    -- Aceite Vegetal
    (5,59,1),
    
    -- /*/*/*/*/* MOOFFIN DE TOCINO Y HUEVO/*/*/*/*/* 
    -- Pan Muffin
    (6,66,1),
    -- Tocino
    (6,8,1),
    -- Queso Cheddar Amarillo
    (6,42,1),
    -- Mantequilla
    (6,54,1),
    -- Huevo
    (6,113,1),
    -- Sal
    (6,17,1),
    -- Aceite Vegetal
    (6,59,1),
    
    -- /*/*/*/*/* MOOFFIN DE DOBLE TOCINO Y HUEVO /*/*/*/*/* 
    -- Pan Muffin
    (7,66,1),
    -- Tocino
    (7,8,2),
    -- Queso Cheddar Amarillo
    (7,42,1),
    -- Mantequilla
    (7,54,1),
    -- Huevo
    (7,113,1),
    -- Sal
    (7,17,1),
    -- Aceite Vegetal
    (7,59,1),
    
    -- /*/*/*/*/* MOOFFIN DE TOCINO Y DOBLE HUEVO /*/*/*/*/* 
    -- Pan Muffin
    (8,66,1),
    -- Tocino
    (8,8,1),
    -- Queso Cheddar Amarillo
    (8,42,1),
    -- Mantequilla
    (8,54,1),
    -- Huevo
    (8,113,2),
    -- Sal
    (8,17,1),
    -- Aceite Vegetal
    (8,59,1),
    
    -- /*/*/*/*/* MOOFFIN DE JAMÓN Y HUEVO /*/*/*/*/* 
    -- Pan Muffin
    (9,66,1),
    -- Jamón
    (9,7,1),
    -- Queso Cheddar Amarillo
    (9,42,1),
    -- Mantequilla
    (9,54,1),
    -- Huevo
    (9,113,1),
    -- Sal
    (9,17,1),
    -- Aceite Vegetal
    (9,59,1),
    
	-- /*/*/*/*/* MOOFFIN DE DOBLE JAMÓN Y HUEVO /*/*/*/*/* 
    -- Pan Muffin
    (10,66,1),
    -- Jamón
    (10,7,2),
    -- Queso Cheddar Amarillo
    (10,42,1),
    -- Mantequilla
    (10,54,1),
    -- Huevo
    (10,113,1),
    
	-- /*/*/*/*/* MOOFFIN DE JAMÓN Y DOBLE HUEVO /*/*/*/*/* 
    -- Pan Muffin
    (11,66,1),
    -- Jamón
    (11,7,1),
    -- Queso Cheddar Amarillo
    (11,42,1),
    -- Mantequilla
    (11,54,1),
    -- Huevo
    (11,113,2),
    -- Sal
    (11,17,1),
    -- Aceite Vegetal
    (11,59,1),
    
	-- /*/*/*/*/* MOOFFIN DE FRIJOL Y HUEVO /*/*/*/*/* 
    -- Pan Muffin
    (12,66,1),
    -- Frijol
    (12,104,1),
    -- Huevo
    (12,113,1),
    
    -- /*/*/*/*/* MOOFFIN DE QUESO Y HUEVO /*/*/*/*/*
    -- Pan Muffin
    (13,66,1),
    -- Queso Cheddar Amarillo
    (13,42,1),
    -- Huevo
    (13,113,1),
    -- Sal
    (13,17,1),
    -- Aceite Vegetal
    (13,59,1),
    
	-- ============================ MOOVAN ============================
    -- /*/*/*/*/* SÚPER MOOVAN CON SALCHICHA /*/*/*/*/* 
    -- Pan Muffin
    (14,66,1),
    -- Frijol
    (14,104,1),
    -- Huevo
    (14,113,1),
    -- Queso Cheddar Amarillo
    (14,42,1),
    -- Mantequilla
	(14,54,1),
    -- Salchicha
    (14,9,1),
    -- Plátano Frito
    (14,110,1), 
    -- Sal
    (14,17,1),
    -- Aceite Vegetal
    (14,59,1),
    
    -- /*/*/*/*/* MOOVAN CON SALCHICHA /*/*/*/*/* 
	-- Pan Muffin
    (15,66,1),
    -- Frijol
    (15,104,1),
    -- Huevo
    (15,113,1),
    -- Queso Cheddar Amarillo
    (15,42,1),
    -- Mantequilla
	(15,54,1),
    -- Salchicha
    (15,9,1),
    -- Sal
    (15,17,1),
    -- Aceite Vegetal
    (15,59,1),
    
    -- /*/*/*/*/* SÚPER MOOVAN CON JAMÓN /*/*/*/*/* 
	-- Pan Muffin
    (16,66,1),
    -- Frijol
    (16,104,1),
    -- Huevo
    (16,113,1),
    -- Queso Cheddar Amarillo
    (16,42,1),
    -- Mantequilla
	(16,54,1),
    -- Jamón
    (16,7,1),
	-- Plátano Frito
    (16,110,1), 
    -- Sal
    (16,17,1),
    -- Aceite Vegetal
    (16,59,1),
    
	-- /*/*/*/*/* MOOVAN CON JAMÓN /*/*/*/*/* 
	-- Pan Muffin
    (17,66,1),
    -- Frijol
    (17,104,1),
    -- Huevo
    (17,113,1),
    -- Queso Cheddar Amarillo
    (17,42,1),
    -- Mantequilla
	(17,54,1),
    -- Jamón
    (17,7,1),
    -- Sal
    (17,17,1),
    -- Aceite Vegetal
    (17,59,1),
    
    -- ============================ MOOGRILLED ============================
    -- /*/*/*/*/* MOOGRILLED DE TOCINO Y HUEVO /*/*/*/*/*
	-- Pan Esponjoso
	(18,62,1),
	-- Huevo
    (18,113,1),
    -- Queso Cheddar Amarillo
    (18,42,1),
    -- Tocino
    (18,8,1),
    -- Miel de Mapple
    (18,91,1),
    -- Sal
    (18,17,1),
    -- Aceite Vegetal
    (18,59,1),
    
    -- /*/*/*/*/* MOOGRILLED DE SALCHICHA Y HUEVO /*/*/*/*/*
	-- Pan Esponjoso
	(19,62,1),
	-- Huevo
    (19,113,1),
    -- Queso Cheddar Amarillo
    (19,42,1),
    -- Salchicha
    (19,9,1),
    -- Miel de Mapple
    (19,91,1),
    -- Sal
    (19,17,1),
    -- Aceite Vegetal
    (19,59,1),
    
    -- /*/*/*/*/* MOOGRILLED DE SALCHICHA /*/*/*/*/*
	-- Pan Esponjoso
	(20,62,1),
    -- Salchicha
    (20,9,1),
    -- Miel de Mapple
    (20,91,1),
    -- Sal
    (20,17,1),
    -- Aceite Vegetal
    (20,59,1),
    
    
    -- ============================ DESAYUNOS COMPLETOS ============================
    -- /*/*/*/*/* DESAYUNO MOODELUXE /*/*/*/*/*
    -- Huevos
    (21,113,2),
    -- Salchicha
    (21,9,1),
    -- Panqueques
    (21,114,3),
    -- Miel de Mapple
    (21,91,1),
    -- Mantequilla
    (21,54,1),
    -- Pan Muffin
    (21,66,1),
    -- // Hashbrown
    -- Papa
    (21,107,1),
    -- Sal
    (21,17,1),
    -- Aceite Vegetal
    (21,59,1),
    
    
    -- /*/*/*/*/* DESAYUNO MOOCLÁSICO /*/*/*/*/*
    -- Huevos
    (22,113,2),
    -- Salchicha
    (22,9,1),
    -- Pan Muffin
    (22,66,1),
    -- // Hashbrown
    -- Papa
    (22,107,1),
    -- Sal
    (22,17,1),
    -- Aceite Vegetal
    (22,59,1), 
    
    -- /*/*/*/*/* DESAYUNO MOOTRADICIONAL /*/*/*/*/*
    -- Huevos
    (23,113,2),
    -- Salchicha
    (23,9,1),
    -- Frijol
    (23,104,1),
    -- Queso Fresco
    (23,46,1),
    -- Plátano frito
    (23,110,2),
    -- Tortillas
    (23,97,2),
    -- Crema
    (23,44,1),
    -- Chirmol
    (23,84,1),
    -- Sal
    (23,17,1),
    -- Aceite Vegetal
    (23,59,1),
    
    -- /*/*/*/*/* MOOVACAKE /*/*/*/*/*
    -- Harina de Trigo
    (24,68,2),
    -- Leche
    (24,51,2),
    -- Canela en Polvo
    (24,13,1),
    -- Huevo
    (24,113,1),
    -- Mantequilla
    (24,54,1),
    -- Miel de Mapple
    (24,91,1),
    -- Sal
    (24,17,1),
    -- Aceite Vegetal
    (24,59,1),
    
    -- ============================ MOOVITO ============================

	-- /*/*/*/*/* MOOVITO INDIVIDUAL /*/*/*/*/*
	-- Tortilla de Harina
    (25,98,1),
    -- Salchicha
    (25,9,1),
    -- Huevo
    (25,113,1),
    -- Queso Cheddar Amarillo
    (25,42,1),
    -- Chile Pimiento
    (25,103,1),
    -- Sal
    (25,17,1),
    -- Aceite Vegetal
    (25,59,1),
    
    -- /*/*/*/*/* MOOVITO DOBLE /*/*/*/*/*
	-- Tortilla de Harina
    (26,98,2),
    -- Salchicha
    (26,9,2),
    -- Huevo
    (26,113,2),
    -- Queso Cheddar Amarillo
    (26,42,2),
    -- Chile Pimiento
    (26,103,2),
    -- Sal
    (26,17,1),
    -- Aceite Vegetal
    (26,59,1),
    
     -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     ALMUERZO     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    
    -- ============================ HAMBOORGUESA ============================
    
    -- /*/*/*/*/* HAMBOORGUESA /*/*/*/*/*
    -- Pan con Ajonjolí
    (27,61,1),
    -- Carne de Res
    (27,6,1),
    -- Pepinillos
    (27,108,2),
    -- Cebolla
    (27,100,1),
    -- Kétchup
    (27,85,1),
    -- Mostaza
    (27,87,1),
    -- Sal
    (27,17,1),
    -- Aceite Vegetal
    (27,59,1),
    
    -- /*/*/*/*/* HAMBOORGUESA Jr /*/*/*/*/*
    -- Pan con Ajonjolí
    (28,61,1),
    -- Carne de Res
    (28,6,1),
    -- Pepinillos
    (28,108,2),
    -- Tomate
    (28,111,3),
    -- Lechuga
    (28,106,2),
    -- Sal
    (28,17,1),
    -- Aceite Vegetal
    (28,59,1),
    
    -- /*/*/*/*/* HAMBOORGUESA DE TOCINO CON CHEDDAR /*/*/*/*/*
    -- Pan con Ajonjolí
    (29,61,1),
    -- Carne de Res
    (29,6,2),
    -- Tocino
    (29,8,3),
    -- Pepinillos
    (29,108,2),
    -- Queso Cheddar
    (29,42,2),
    -- Salsa de Queso
    (29,88,1),
    -- Cebolla Caramelizada
    (29,101,3),
    -- Sal
    (29,17,1),
    -- Aceite Vegetal
    (29,59,1),
    
    -- ============================ BIG MOO ============================
    -- /*/*/*/*/* BIG MOO /*/*/*/*/*
    -- Pan con Ajonjolí
    (30,61,1),
    -- Carne de Res
    (30,6,2),
    -- Pepinillos
    (30,108,2),
    -- Queso Cheddar
    (30,42,2),
    -- Salsa Big Mac
    (30,83,1),
    -- Cebolla 
    (30,100,3),
    -- Lechuga
    (30,106,2),
    -- Sal
    (30,17,1),
    -- Aceite Vegetal
    (30,59,1),
    
    -- /*/*/*/*/* SÚPER BIG MOO /*/*/*/*/*
    -- Pan con Ajonjolí
    (31,61,2),
    -- Carne de Res
    (31,6,2),
    -- Pepinillos
    (31,108,2),
    -- Queso Cheddar
    (31,42,2),
    -- Salsa Big Mac
    (31,83,4),
    -- Cebolla 
    (31,100,3),
    -- Lechuga
    (31,106,2),
    -- Sal
    (31,17,1),
    -- Aceite Vegetal
    (31,59,1),
    
    -- ============================  CUARTO DE MOO LIBRA ============================ 
    -- /*/*/*/*/* CUARTO DE MOO LIBRA /*/*/*/*/* 
    -- Pan con Ajonjolí
    (32,61,1),
    -- 1/4 Libra de Carne de Res
    (32,115,1),
    -- Pepinillos
    (32,108,2),
    -- Queso Cheddar
    (32,42,2),
    -- Kétchup
    (32,85,1),
    -- Mostaza 
    (32,87,1),
    -- Cebolla
    (32,100,3),
    -- Sal
    (32,17,1),
    -- Aceite Vegetal
    (32,59,1),
    
    -- /*/*/*/*/* CUARTO DE MOO LIBRA CON QUESO /*/*/*/*/* 
    -- Pan con Ajonjolí
    (33,61,1),
    -- 1/4 de Libra de Carne de Res
    (33,115,1),
    -- Pepinillos
    (33,108,2),
    -- Queso Cheddar
    (33,42,3),
    -- Kétchup
    (33,85,1),
    -- Mostaza 
    (33,87,1),
    -- Cebolla
    (33,100,3),
    -- Sal
    (33,17,1),
    -- Aceite Vegetal
    (33,59,1),
    
    -- /*/*/*/*/* CUARTO DE MOO LIBRA CON TOCINO Y QUESO /*/*/*/*/* 
    -- Pan con Ajonjolí
    (34,61,1),
    -- 1/4 de libra de Carne de Res
    (34,115,1),
    -- Pepinillos
    (34,108,2),
    -- Queso Cheddar
    (34,42,2),
    -- Kétchup
    (34,85,1),
    -- Mostaza 
    (34,87,1),
    -- Cebolla
    (34,100,3),
    -- Tocino
    (34,8,2),
    -- Sal
    (34,17,1),
    -- Aceite Vegetal
    (34,59,1),
    
    -- /*/*/*/*/* CUARTO DE MOO LIBRA CON DOBLE TOCINO Y QUESO /*/*/*/*/* 
    -- Pan con Ajonjolí
    (35,61,1),
    -- 1/4 de Libra de Carne de Res
    (35,115,1),
    -- Pepinillos
    (35,108,2),
    -- Queso Cheddar
    (35,42,3),
    -- Kétchup
    (35,85,1),
    -- Mostaza 
    (35,87,3),
    -- Cebolla
    (35,100,3),
    -- Tocino
    (35,8,4),
    -- Sal
    (35,17,1),
    -- Aceite Vegetal
    (35,59,1),
    
    -- /*/*/*/*/* CUARTO DE MOO LIBRA CON DOBLE CARNE /*/*/*/*/* 
    -- Pan con Ajonjolí
    (36,61,1),
    -- 1/4 de Libra de Carne de Res
    (36,115,2),
    -- Pepinillos
    (36,108,2),
    -- Queso Cheddar
    (36,42,3),
    -- Kétchup
    (36,85,1),
    -- Mostaza 
    (36,87,3),
    -- Cebolla
    (36,100,3),
    -- Tocino
    (36,8,4),
    -- Sal
    (36,17,1),
    -- Aceite Vegetal
    (36,59,1),
    
    -- /*/*/*/*/* CUARTO DE MOO LIBRA DELUXE CON DOBLE QUESO /*/*/*/*/* 
    -- Pan con Ajonjolí
    (37,61,1),
    -- 1/4 de Libra de Carne de Res
    (37,115,1),
    -- Pepinillos
    (37,108,2),
    -- Queso Cheddar
    (37,42,4),
    -- Kétchup
    (37,85,1),
    -- Mostaza 
    (37,87,3),
    -- Cebolla
    (37,100,3),
    -- Tocino
    (37,8,4),
    -- Sal
    (37,17,1),
    -- Aceite Vegetal
    (37,59,1),
    
    -- /*/*/*/*/* CUARTO DE MOO LIBRA DELUXE CON DOBLE CARNE /*/*/*/*/* 
    -- Pan con Ajonjolí
    (38,61,1),
    -- 1/4 de Libra de Carne de Res
    (38,115,2),
    -- Pepinillos
    (38,108,2),
    -- Queso Cheddar
    (38,42,2),
    -- Kétchup
    (38,85,1),
    -- Mostaza 
    (38,87,3),
    -- Cebolla
    (38,100,3),
    -- Tocino
    (38,8,4),
    -- Sal
    (38,17,1),
    -- Aceite Vegetal
    (38,59,1),
    
    -- ============================  MOO INSANO ============================  
    -- /*/*/*/*/* MOO INSANO DE CARNE /*/*/*/*/*
    -- Pan con Ajonjolí
    (39,61,1),
    -- 1/4 de Libra de Carne de Res
    (39,115,1),
    -- Pepinillos
    (39,108,2),
    -- Queso Cheddar
    (39,42,2),
    -- Kétchup
    (39,85,1),
    -- Mayonesa
    (39,86,1),
    -- Mostaza 
    (39,87,3),
    -- Cebolla
    (39,100,3),
    -- Tomate
    (39,111,3),
    -- Lechuga
    (39,106,2),
    -- Sal
    (39,17,1),
    -- Aceite Vegetal
    (39,59,1),
    
    -- /*/*/*/*/* MOO INSANO DE DOBLE CARNE /*/*/*/*/*
    -- Pan con Ajonjolí
    (40,61,1),
    -- 1/4 de Libra de Carne de Res
    (40,115,2),
    -- Pepinillos
    (40,108,2),
    -- Queso Cheddar
    (40,42,2),
    -- Kétchup
    (40,85,1),
    -- Mayonesa
    (40,86,1),
    -- Mostaza 
    (40,87,3),
    -- Cebolla
    (40,100,3),
    -- Tomate
    (40,111,3),
    -- Lechuga
    (40,106,2),
    -- Sal
    (40,17,1),
    -- Aceite Vegetal
    (40,59,1),
    
    -- ============================  MOOVABURGUESA ============================ 
    -- /*/*/*/*/* MOOVABURGUESA /*/*/*/*/*
    -- Pan de Hamburguesa
    (41,65,1),
    -- Carne de Res
    (41,6,1),
    -- Pepinillos
    (41,108,2),
    -- Queso Cheddar
    (41,42,2),
    -- Cebolla 
    (41,100,3),
    -- Kétchup
    (41,85,1),
    -- Mostaza
    (41,87,1),
    -- Sal
    (41,17,1),
    -- Aceite Vegetal
    (41,59,1),
    
    -- /*/*/*/*/* MOOVABURGUESA DOBLE/*/*/*/*/*
    -- Pan de Hamburguesa
    (42,65,1),
    -- Carne de Res
    (42,6,2),
    -- Pepinillos
    (42,108,2),
    -- Queso Cheddar
    (42,42,2),
    -- Cebolla 
    (42,100,3),
    -- Kétchup
    (42,85,1),
    -- Mostaza
    (42,87,1),
    -- Sal
    (42,17,1),
    -- Aceite Vegetal
    (42,59,1),
    
    -- /*/*/*/*/* MOOVABURGUESA TRIPLE /*/*/*/*/*
    -- Pan de Hamburguesa
    (43,65,1),
    -- Carne de Res
    (43,6,3),
    -- Pepinillos
    (43,108,2),
    -- Queso Cheddar
    (43,42,2),
    -- Cebolla 
    (43,100,3),
    -- Kétchup
    (43,85,1),
    -- Mostaza
    (43,87,1),
    -- Sal
    (43,17,1),
    -- Aceite Vegetal
    (43,59,1),
    
    -- ============================ BIG MOOVA ============================  
    -- /*/*/*/*/* BIG MOOVA /*/*/*/*/*
    -- Pan con Ajonjolí
    (44,61,1),
    -- Carne de Res
    (44,6,1),
    -- Pepinillos
    (44,108,2),
    -- Queso Cheddar Blanco
    (44,43,3),
    -- Cebolla 
    (44,100,3),
    -- Tomate
    (44,111,3),
    -- Lechuga
    (44,106,2),
    -- Salsa Tasty
    (44,90,1),
    -- Sal
    (44,17,1),
    -- Aceite Vegetal
    (44,59,1),
    
    -- /*/*/*/*/* BIG MOOVA DOBLE CARNE /*/*/*/*/*
    -- Pan con Ajonjolí
    (45,61,1),
    -- Carne de Res
    (45,6,2),
    -- Pepinillos
    (45,108,2),
    -- Queso Cheddar Blanco
    (45,43,3),
    -- Cebolla 
    (45,100,3),
    -- Tomate
    (45,111,3),
    -- Lechuga
    (45,106,2),
    -- Salsa Tasty
    (45,90,1),
    -- Sal
    (45,17,1),
    -- Aceite Vegetal
    (45,59,1),
    
    -- /*/*/*/*/* BIG MOOVA DE TOCINO /*/*/*/*/*
    -- Pan con Ajonjolí
    (46,61,1),
    -- Tocino
    (46,8,2),
    -- Pepinillos
    (46,108,2),
    -- Queso Cheddar Blanco
    (46,43,3),
    -- Cebolla 
    (46,100,3),
    -- Tomate
    (46,111,3),
    -- Lechuga
    (46,106,2),
    -- Salsa Tasty
    (46,90,1),
    -- Sal
    (46,17,1),
    -- Aceite Vegetal
    (46,59,1),
    
    -- /*/*/*/*/* BIG MOOVA TRIPLE CARNE /*/*/*/*/*
    -- Pan con Ajonjolí
    (47,61,1),
    -- Carne de Res
    (47,6,3),
    -- Pepinillos
    (47,108,2),
    -- Queso Cheddar Blanco
    (47,43,3),
    -- Cebolla 
    (47,100,3),
    -- Tomate
    (47,111,3),
    -- Lechuga
    (47,106,2),
    -- Salsa Tasty
    (47,90,1),
    -- Sal
    (47,17,1),
    -- Aceite Vegetal
    (47,59,1),
    
    -- ============================ MOOVA CRISPY ============================
    -- /*/*/*/*/* MOOVA CRISPY TOCINO Y QUESO /*/*/*/*/*
    -- Pan brioche
    (48,64,1),
    -- Filete de Pollo
    (48,116,1),
    -- Salsa de Queso
    (48,88,1),
    -- Tocino
    (48,8,2),
    -- Cebolla Caramelizada
    (48,101,3),
    -- Sal
    (48,17,1),
    -- Aceite Vegetal
    (48,59,1),
    
    -- /*/*/*/*/* MOOVA CRISPY TOCINO CON RANCH /*/*/*/*/*
    -- Pan brioche
    (49,64,1),
    -- Filete de Pollo
    (49,116,1),
    -- Salsa Ranch
    (49,89,1),
    -- Tomate
    (49,111,3),
    -- Lechuga 
    (49,106,2),
    -- Sal
    (49,17,1),
    -- Aceite Vegetal
    (49,59,1),
    
    -- /*/*/*/*/* MOOVA CRISPY DELUXE /*/*/*/*/*
    -- Pan brioche
    (50,64,1),
    -- Filete de Pollo
    (50,116,1),
    -- Mayonesa
    (50,86,1),
    -- Tomate
    (50,111,3),
    -- Lechuga 
    (50,106,2),
    -- Sal
    (50,17,1),
    -- Aceite Vegetal
    (50,59,1),
    
    -- ============================  GOURMOOVA ============================ 
    -- /*/*/*/*/* GOURMOOVA CRISPY /*/*/*/*/* 
    -- Filete de Pollo Crispy
    (51,116,1),
    -- Pan Artesanal
    (51,62,1),
    -- Tomate
    (51,111,3),
    -- Lechuga
    (51,106,2),
    -- Cebolla
    (51,100,3),
    -- Queso Cheddar Blanco
    (51,43,2),
    -- Mayonesa
    (51,86,1),
    -- Sal
    (51,17,1),
    -- Aceite Vegetal
    (51,59,1),
    
    -- /*/*/*/*/* GOURMOOVA GRILLED /*/*/*/*/* 
    -- Filete de Pollo a la Plancha
    (52,117,1),
    -- Pan Artesanal
    (52,62,1),
    -- Tomate
    (52,111,3),
    -- Lechuga
    (52,106,2),
    -- Cebolla
    (52,100,3),
    -- Queso Cheddar Blanco
    (52,43,2),
    -- Mayonesa
    (52,86,1),
    -- Sal
    (52,17,1),
    -- Aceite Vegetal
    (52,59,1),
    
    -- /*/*/*/*/* GOURMOOVA DE RES /*/*/*/*/* 
    -- Carne de Res
    (53,6,1),
    -- Pan Artesanal
    (53,62,1),
    -- Tomate
    (53,111,3),
    -- Lechuga
    (53,106,2),
    -- Cebolla
    (53,100,3),
    -- Queso Cheddar Blanco
    (53,43,2),
    -- Mayonesa
    (53,86,1),
    -- Sal
    (53,17,1),
    -- Aceite Vegetal
    (53,59,1),
    
    -- -- ============================  PICO GUACAMOL GOURMOOVA ============================ 
    -- /*/*/*/*/* PICO GUACAMOL GOURMOOVA CRISPY /*/*/*/*/*  
    -- Filete de Pollo Crispy
    (54,116,1),
    -- Pan Artesanal
    (54,62,1),
    -- Queso Cheddar Blanco
    (54,43,2),
    -- Lechuga
    (54,106,1),
    -- //// Porción de Pico de Gallo
    -- Tomate
    (54,111,2),
    -- Cebolla
    (54,100,2),
    -- Jugo de Limón
    (54,105,1),
    -- Aderezo Ranch
    (54,89,1),
    -- Guacamol 
    (54,99,1),
    -- Sal
    (54,17,1),
    -- Aceite Vegetal
    (54,59,1),
    
    
    -- /*/*/*/*/* PICO GUACAMOL GOURMOOVA GRILLED /*/*/*/*/*  
    -- Filete de Pollo a la Plancha
    (55,117,1),
    -- Pan Artesanal
    (55,62,1),
    -- Queso Cheddar Blanco
    (55,43,2),
    -- Lechuga
    (55,106,1),
    -- //// Porción de Pico de Gallo
    -- Tomate
    (55,111,2),
    -- Cebolla
    (55,100,2),
    -- Jugo de Limón
    (55,105,1),
    -- Aderezo Ranch
    (55,89,1),
    -- Guacamol 
    (55,99,1),
    -- Sal
    (55,17,1),
    -- Aceite Vegetal
    (55,59,1),
    
    -- /*/*/*/*/* PICO GUACAMOL GOURMOOVA DE RES /*/*/*/*/*
    -- Carne de Res
    (56,6,1),
    -- Pan Artesanal
    (56,62,1),
    -- Queso Cheddar Blanco
    (56,43,2),
    -- Lechuga
    (56,106,1),
    -- //// Porción de Pico de Gallo
    -- Tomate
    (56,111,2),
    -- Cebolla
    (56,100,2),
    -- Jugo de Limón
    (56,105,1),
    -- Aderezo Ranch
    (56,89,1),
    -- Guacamol 
    (56,99,1),
    -- Sal
    (56,17,1),
    -- Aceite Vegetal
    (56,59,1),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     POSTRES     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    -- ============================ PIE ============================
    -- /*/*/*/*/* PIE DE MANZANA /*/*/*/*/* 
    -- Lámina de Hojaldre
    (57,70,1),
    -- Manzana Verde
    (57,32,2),
    -- Mantequilla
    (57,54,2),
    -- Azúcar Morena
    (57,79,4),
    -- Canela en Polvo
    (57,13,1),
    -- Nuez Moscada
    (57,76,1),
    -- Jugo de Limón
    (57,105,1),
    -- Maizena
    (57,69,1),
    -- Agua
    (57,57,1),
    -- Aceite
    (57,59,1),
    
    -- /*/*/*/*/* PIE DE QUESO /*/*/*/*/* 
    -- Lámina de Hojaldre
    (58,70,1),
    -- Queso Crema
    (58,55,14),
    -- Azúcar Glass
    (58,78,2),
    -- Esencia de Vainilla
    (58,18,4),
    -- Jugo de Limón
    (58,105,1),
    -- Aceite
    (58,59,1),
    
    -- ============================ PASTEL ============================
    -- /*/*/*/*/* PASTEL DE CHOCOLATE /*/*/*/*/*
    -- Harina de Trigo
    (59,68,32),
    -- Azúcar Morena
    (59,79,32),
    -- Cacao en Polvo
    (59,12,12),
    -- Bicarbonato de Sodio
    (59,11,1),
    -- Sal
    (59,17,1),
    -- Huevo
    (59,113,2),
    -- Leche
    (59,51,16),
    -- Aceite
    (59,59,8),
    -- Esencia de Vainilla
    (59,18,2),
    -- Agua
    (59,57,16),
    
    -- ============================ CHEESECAKE ============================
    -- /*/*/*/*/* CHEESECAKE DE FRESA /*/*/*/*/*
    -- //// MERMELADA
    -- Fresas picadas
    (60,28,8),
    -- Azúcar Morena
    (60,79,3),
    -- Jugo de Limón 
    (60,105,1),
    -- Maizena
    (60,69,1),
    -- Agua
    (60,57,1),
    -- //// CREMA
    -- Queso Crema
    (60,55,14),
    -- Azúcar Glass
    (60,78,4),
    -- Esencia de Vainilla
    (60,18,1),
    -- //// MASA
    -- Lámina de Hojaldre
    (60,70,2),
    -- Huevo
    (60,113,1),
    -- Aceite
    (60,59,1),
    
    -- /*/*/*/*/* CHEESECAKE DE MORA /*/*/*/*/*
    -- //// MERMELADA
    -- Moras picadas
    (61,34,8),
    -- Azúcar Morena
    (61,79,3),
    -- Jugo de Limón 
    (61,105,1),
    -- Maizena
    (61,69,1),
    -- Agua
    (61,57,1),
    -- //// CREMA
    -- Queso Crema
    (61,55,14),
    -- Azúcar Glass
    (61,78,4),
    -- Esencia de Vainilla
    (61,18,1),
    -- //// MASA
    -- Lámina de Hojaldre
    (61,70,2),
    -- Huevo
    (61,113,1),
    -- Aceite
    (61,59,1),
    
    -- ============================ MOOFLURRY ============================
    -- /*/*/*/*/* MOOFLURRY OREO /*/*/*/*/*
    -- Helado de Vanilla 
    (62,48,1),
    -- Oreo
    (62,92,5),
    
    -- /*/*/*/*/* MOOFLURRY OREO /*/*/*/*/*
    -- Helado de Vanilla 
    (63,48,1),
    -- M&Ms
    (63,96,1),
    
    -- ============================ HELADO DE CONO ============================
    -- /*/*/*/*/* HELADO DE CONO DE VAINILLA /*/*/*/*/*
    -- Helado de Vainilla
    (64,48,1),
    -- Cono Waffle
    (64,67,1),
    
    -- /*/*/*/*/* HELADO DE CONO DE CHOCOLATE /*/*/*/*/*
    -- Helado de Chocolate
    (65,47,1),
    -- Cono Waffle
    (65,67,1),
    
    -- /*/*/*/*/* HELADO DE CONO MIXTO /*/*/*/*/*
    -- Helado de Vainilla
    (66,48,1),
    -- Helado de Chocolate
	(66,47,1),
    -- Cono Waffle
    (66,67,1),
    
    -- ============================ MOOVANDAE ============================
    -- /*/*/*/*/* MOOVANDAE /*/*/*/*/*
    -- Helado de Vainilla
    (67,48,1),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     ANTOJO     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    
    -- ============================ DERRETIDO ============================
    -- /*/*/*/*/* DETERRIDO MOO CLÁSICO /*/*/*/*/*
    -- Pan de Hamburguesa
    (68,65,1),
    -- Queso Cheddar Amarillo
    (68,42,1),
    -- Sal
    (68,17,1),
    -- Aceite Vegetal
    (68,59,1),
    
    -- ============================ PAPAS ============================
    -- /*/*/*/*/* PAPAS MEDIANAS /*/*/*/*/*
    -- Papas
    (69,107,1),
    -- Sal
    (69,17,1),
    -- Aceite Vegetal
    (69,59,1),
    
    -- /*/*/*/*/* PAPAS GRANDES /*/*/*/*/*
    -- Papas
    (70,107,2),
    -- Sal
    (70,17,1),
    -- Aceite Vegetal
    (70,59,1),
    
    -- /*/*/*/*/* MOOPATATAS /*/*/*/*/*
    -- Papas
    (71,107,1),
    -- Sal
    (71,17,1),
    -- Aceite Vegetal
    (71,59,1),
    -- Paprika
    (71,15,1),
    -- Pimienta Negra Molida
    (71,16,1),
    -- Maizena
    (71,69,1),
    -- Ajo en Polvo 
    (71,10,1),
    -- Cebolla en Polvo
    (71,14,1),
    
    -- /*/*/*/*/* HASHBROWN /*/*/*/*/*
    -- Papas
    (72,107,1),
    -- Sal
    (72,17,1),
    -- Aceite Vegetal
    (72,59,1),
    
    -- ============================ MOOGGETS ============================
    -- /*/*/*/*/* MOOGGETS 4 PIEZAS /*/*/*/*/*
    -- Filete de Pollo Crispy
    (73,116,1),
    -- Sal
    (73,17,1),
    -- Aceite Vegetal
    (73,59,1),
    
    -- /*/*/*/*/* MOOGGETS 6 PIEZAS /*/*/*/*/*
    -- Filete de Pollo Crispy
    (74,116,1),
    -- Sal
    (74,17,1),
    -- Aceite Vegetal
    (74,59,1),
    
    -- ============================ TOSTADO ============================
    -- /*/*/*/*/* TOSTADO DE QUESO CON FRIJOL /*/*/*/*/* 
    -- Pan Artesanal 
    (75,62,1),
    -- Queso Cheddar Blanco
    (75,43,1),
    -- Frijol
    (75,104,1),
    -- Mayonesa
    (75,86,1),
    -- Sal
    (75,17,1),
    -- Aceite Vegetal
    (75,59,1),
    
    -- /*/*/*/*/* TOSTADO DE QUESO CON JAMÓN /*/*/*/*/* 
    -- Pan Artesanal 
    (76,62,1),
    -- Queso Cheddar Blanco
    (76,43,1),
    -- Jamón
    (76,7,1),
    -- Mayonesa
    (76,86,1),
    -- Sal
    (76,17,1),
    -- Aceite Vegetal
    (76,59,1),
    
    -- /*/*/*/*/* TOSTADO DE QUESO CON TOMATE /*/*/*/*/* 
    -- Pan Artesanal 
    (77,62,1),
    -- Queso Cheddar Blanco
    (77,43,1),
    -- Tomate
    (77,111,1),
    -- Mayonesa
    (77,86,1),
    -- Sal
    (77,17,1),
    -- Aceite Vegetal
    (77,59,1),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     BEBIDAS CALIENTES     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    
    -- ============================ CHOCOLATE ============================
    -- /*/*/*/*/* CHOCOLATE /*/*/*/*/* 
    -- Cacao 
    (78,12,1),
    -- Agua
    (78,57,1),
    -- Azúcar
    (78,77,2),
    -- Leche
    (78,51,1),
    
    -- ============================ CAFÉ ============================
    -- /*/*/*/*/* CAFÉ MOO /*/*/*/*/* 
    -- Granos de Café
    (79,5,1),
    -- Agua
    (79,57,1),
    
    -- /*/*/*/*/* CAFÉ MOO CON LECHE /*/*/*/*/* 
    -- Granos de Café
    (80,5,1),
    -- Agua
    (80,57,1),
    -- Leche
    (80,51,1),
    
    -- ============================ MOOVACCINO ============================
    -- /*/*/*/*/* MOOVACCINO /*/*/*/*/* 
    -- Leche al Vapor
    (81,52,2),
    -- Granos de café
    (81,5,1),
    -- Agua
    (81,57,1),
    
    -- ============================ TÉ MOO ============================
    -- /*/*/*/*/* TÉ MOO /*/*/*/*/* 
    -- Infusión té negro
    (82,41,1),
    -- Azúcar
    (82,77,1),
    -- Agua
    (82,57,1),
    
    -- /*/*/*/*/* TÉ MOO DE MANZANILLA /*/*/*/*/* 
    -- Infusión té de Manzanilla
    (83,40,1),
    -- Hojas de Menta
    (83,75,1),
    -- Flores de Caléndula
    (83,73,2),
    -- Agua
    (83,57,1),
    
    -- /*/*/*/*/* TÉ CHAI DE VAINILLA /*/*/*/*/* 
    -- Infusión té Negro
    (84,41,1),
    -- Canela en Polvo
    (84,13,1),
    -- Cardamomo
    (84,71,2),
    -- Clavo de Olor
    (84,72,1),
    -- Esencia de Vainilla
    (84,18,1),
    -- Azúcar Blanca
    (84,77,2),
    -- Agua
    (84,57,1),
    
    -- /*/*/*/*/* TÉ MOO DE BORA BORA /*/*/*/*/* 
	-- Infusión de rosa de Jamaica
    (85,37,1),
    -- Papaya
    (85,35,1),
    -- Manzana 
    (85,31,1),
    -- Pasas
    (85,36,1),
    -- Fresas
    (85,28,1),
    -- Agua
    (85,57,1),
    
    -- /*/*/*/*/* TÉ MOO DE MENTA /*/*/*/*/* 
    -- Infusión de té de limón
    (86,39,1),
    -- Hoja de Menta
    (86,75,2),
    -- Hojas de Hierbabuena
    (86,74,2),
    -- Jengibre
    (86,29,2),
    -- Agua 
    (86,57,1),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     BEBIDAS FRÍAS     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    
    -- ============================ MOOFIZZ ============================ 
    -- /*/*/*/*/* MOOFIZZ ROSADO /*/*/*/*/* 
    -- //// Sprite
    -- Agua
    (87,57,1),
    -- Jugo de Limón
    (87,105,1),
    -- Sal
    (87,17,1),
    -- Bicarbonato
    (87,11,1),
    -- Saborizante de Pitaya
    (87,24,1),
    -- Azúcar
    (87,77,1),
    -- Hielo
    (87,60,5),
    
    -- /*/*/*/*/* MOOFIZZ VERDE /*/*/*/*/* 
    -- //// Sprite
    -- Agua
    (88,57,1),
    -- Jugo de Limón
    (88,105,1),
    -- Sal
    (88,17,1),
    -- Bicarbonato
    (88,11,1),
    -- Saborizante de Manzana Verde
    (88,22,1),
    -- Azúcar
    (88,77,1),
    -- Hielo
    (88,60,5), 
    
    -- /*/*/*/*/* MOOFIZZ AZUL /*/*/*/*/* 
    -- //// Sprite
    -- Agua
    (89,57,1),
    -- Jugo de Limón
    (89,105,1),
    -- Sal
    (89,17,1),
    -- Bicarbonato
    (89,11,1),
    -- Saborizante de Frambuesa Azul
    (89,21,1),
    -- Azúcar
    (89,77,1),
    -- Hielo
    (89,60,5), 
    
    -- /*/*/*/*/* MOOFIZZ AMARILLO /*/*/*/*/* 
    -- //// Sprite
    -- Agua
    (90,57,1),
    -- Jugo de Limón
    (90,105,1),
    -- Sal
    (90,17,1),
    -- Bicarbonato
    (90,11,1),
    -- Saborizante de Naranja
    (90,23,1),
    -- Azúcar
    (90,77,1),
    -- Hielo
    (90,60,5),
    
    -- ============================ SMOOTHIE ============================ 
    -- /*/*/*/*/* SMOOTHIE DE FRUTOS ROJOS /*/*/*/*/* 
    -- Fresas
    (91,28,4),
    -- Frambuesas
    (91,27,4),
    -- Moras
    (91,34,4),
    -- Azúcar
    (91,77,1),
    -- Hielo
    (91,60,5),
    -- Agua
    (91,57,1),
    
    -- /*/*/*/*/* SMOOTHIE DE MANGOS /*/*/*/*/* 
    -- Mango
    (92,30,4),
    -- Azúcar
    (92,77,1),
    -- Hielo
    (92,60,5),
    -- Agua
    (92,57,1),
    
    -- ============================ FRAPPÉ ============================ 
    -- /*/*/*/*/* FRAPPÉ DE OREO /*/*/*/*/* 
    -- Expreso de café
    (93,4,1),
    -- Azúcar
    (93,77,1),
    -- Hielo
    (93,60,5),
    -- Agua
    (93,57,1),
    -- Cacao de Chocolate
    (93,12,1),
    -- Leche
    (93,51,16),
    -- Galletas Oreo
    (93,92,1),
    
    -- /*/*/*/*/* FRAPPÉ DE VAINILLA /*/*/*/*/* 
    -- Expreso de café
    (94,4,1),
    -- Azúcar
    (94,77,1),
    -- Hielo
    (94,60,5),
    -- Agua
    (94,57,1),
    -- Esencia de Vainilla
    (94, 18,1),
    -- Leche
    (94,51,16),
    
     -- /*/*/*/*/* FRAPPÉ DE CHOCOLATE /*/*/*/*/* 
    -- Expreso de café
    (95,4,1),
    -- Azúcar
    (95,77,1),
    -- Hielo
    (95,60,5),
    -- Agua
    (95,57,1),
    -- Saborizante de Chocolate
    (95,20,1),
    -- Leche
    (95,51,16),
    
    -- /*/*/*/*/* FRAPPÉ DE CARAMELO /*/*/*/*/* 
    -- Expreso de café
    (96,4,1),
    -- Azúcar
    (96,77,1),
    -- Hielo
    (96,60,5),
    -- Agua
    (96,57,1),
    -- Saborizante de Caramelo
    (96,19,1),
    -- Leche
    (96,51,16),
    
    -- ============================ MOOVA ICED ============================ 
    -- /*/*/*/*/* MOOVA ICED /*/*/*/*/*
    -- Café
    (97,5,3),
    -- Leche
    (97,51,3),
    -- Hielo
    (97,60,5),
    -- Azúcar 
    (97,77,2),
    
    -- /*/*/*/*/* MOOVA ICED DE VAINILLA /*/*/*/*/*
    -- Café
    (98,5,3),
    -- Leche
    (98,51,3),
    -- Hielo
    (98,60,5),
    -- Azúcar 
    (98,77,2),
    -- Saborizante de Vainilla
    (98,25,1),
    
    -- /*/*/*/*/* MOOVA ICED DE CHOCALATE /*/*/*/*/*
    -- Café
    (99,5,3),
    -- Leche
    (99,51,3),
    -- Hielo
    (99,60,5),
    -- Azúcar 
    (99,77,2),
    -- Saborizante de Chocolate
    (99,20,1),
    
    -- /*/*/*/*/* MOOVA ICED DE CARAMELO /*/*/*/*/*
    -- Café
    (100,5,3),
    -- Leche
    (100,51,3),
    -- Hielo
    (100,60,5),
    -- Azúcar 
    (100,77,2),
    -- Saborizante de Caramelo
    (100,19,1),
    
    -- ============================ MOOVA CHAI FRAPPÉ ============================ 
    -- /*/*/*/*/* MOOVA CHAI FRAPPÉ DE TÉ VERDE /*/*/*/*/*
    -- Infusión de té Verde
    (101,38,1),
    -- Canela en Polvo
    (101,13,1),
    -- Cardadamo
    (101,71,1),
    -- Azúcar
    (101,77,2),
    -- Hielo 
    (101,60,5),
    -- Agua
    (101,57,1),
    
    -- /*/*/*/*/* MOOVA CHAI FRAPPÉ DE VAINILLA /*/*/*/*/*
    -- Esencia de Vainilla
    (102,18,1),
    -- Canela en Polvo
    (102,13,1),
    -- Cardadamo
    (102,71,1),
    -- Azúcar
    (102,77,2),
    -- Hielo 
    (102,60,5),
    -- Agua
    (102,57,1),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     GASEOSAS     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    
    -- /*/*/*/*/* COCA-COLA /*/*/*/*/* 
    -- Hielo
    (103,60,5),
    
    -- /*/*/*/*/* COCA-COLA SIN AZÚCAR/*/*/*/*/* 
    -- Hielo
    (104,60,5),
    
    -- /*/*/*/*/* FANTA /*/*/*/*/* 
    -- Hielo
    (105,60,5),
    
    -- /*/*/*/*/* SPRITE /*/*/*/*/* 
    -- Hielo
    (106,60,5),
    
    -- /*/*/*/*/* TÉ LIPTON DE LIMÓN /*/*/*/*/* 
    -- Hielo
    (107,60,5),
    
    -- /*/*/*/*/* TÉ LIPTON DE FRAMBUESA /*/*/*/*/* 
    -- Hielo
    (108,60,5),
    
    -- /*/*/*/*/* TÉ LIPTON DE DURAZNO /*/*/*/*/* 
    -- Hielo
    (109,60,5),
    
    -- /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-     JUGOS NATURALES     /-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-
    
    -- /*/*/*/*/* JUGO DE JAMAICA /*/*/*/*/*
    -- Esencia de Jamaica
    (110,37,1),
    -- Agua
    (110,57,1),
    -- Azúcar 
    (110,77,2),
    -- Hielo
    (110,60,5),
    
    -- /*/*/*/*/* JUGO DE MANZANA /*/*/*/*/*
    -- Manzana
    (111,31,1),
    -- Agua
    (111,57,1),
    -- Azúcar 
    (111,77,2),
    -- Hielo
    (111,60,5),
    
    -- /*/*/*/*/* JUGO DE MORA /*/*/*/*/*
    -- Mora
    (112,34,1),
    -- Agua
    (112,57,1),
    -- Azúcar 
    (112,77,2),
    -- Hielo
    (112,60,5),
    
    -- /*/*/*/*/* YOGURT DE FRESA /*/*/*/*/*
    (113,56,1);
    
-- ///////// TABLA: COMBO /////////
INSERT INTO Combo (nombre, descripcion,tipo,precio, precio_extra, imagen, id_Subcategoria) VALUES
	-- ============================ CAJITA INFANTIL - DESAYUNO ============================
	-- INDEPENDIENTES: Cajita Desayuno (subcategoría 40)
	-- ID: 1
    ('Cajita Infantil MooFfin de Queso y Huevo', '1 MooFfin de Queso y Huevo + 1 jugo natural + 1 yogurt de fresa', 'Independiente', 30.00, 0.00, '/utiles/imagenes/menu/comida/combosGenerales/cajitaDesayuno/MooFfinHuevoQueso.png', 40),
	-- ID: 2
    ('Cajita Infantil MooFfin de Frijol y Huevo', '1 MooFfin de Frijol y Huevo + 1 jugo natural + 1 yogurt de fresa', 'Independiente', 28.00, 0.00, '/utiles/imagenes/menu/comida/combosGenerales/cajitaDesayuno/MooFfinHuevoFrijol.png', 40),
	-- ID: 3
    ('Cajita Infantil MooFfin de Salchicha', '1 MooFfin de Salchicha + 1 jugo natural + 1 yogurt de fresa', 'Independiente', 27.00, 0.00, '/utiles/imagenes/menu/comida/combosGenerales/cajitaDesayuno/MooFfinSalchicha.png', 40),
	-- ID:4
    ('Cajita Infantil con Derretido (Desayuno)', '1 Derretido + 1 jugo natural + 1 yogurt de fresa', 'Independiente', 27.00, 0.00, '/utiles/imagenes/menu/comida/combosGenerales/cajitaDesayuno/Derretido.png', 40),
	-- ============================ CAJITA INFANTIL - ALMUERZO ============================
	-- ID: 5
    ('Cajita Infantil Mooggets', '4 nuggets de pollo + 1 bebida + 1 papa pequeña + 1 acompañamiento', 'Independiente', 32.00, 0.00, '/utiles/imagenes/menu/comida/combosGenerales/cajitaAlmuerzo/Mooggets.png', 41),
	-- ID: 6
    ('Cajita Infantil Hamboorguesa', '1 Hamburguesa + 1 bebida + 1 papa pequeña + 1 acompañamiento', 'Independiente', 32.00, 0.00, '/utiles/imagenes/menu/comida/combosGenerales/cajitaAlmuerzo/MooHamburguesa.png', 41),
	-- ID: 7
    ('Cajita Infantil Hamboorguesa Jr', '1 Hamburguesa Jr + 1 bebida + 1 papa pequeña + 1 acompañamiento', 'Independiente', 31.00, 0.00, '/utiles/imagenes/menu/comida/combosGenerales/cajitaAlmuerzo/MooHamburguesaJr.png', 41),
	-- ID: 8
    ('Cajita Infantil Moovaburguesa', '1 Quesoburguesa + 1 bebida + 1 papa pequeña + 1 acompañamiento', 'Independiente', 34.00, 0.00, '/utiles/imagenes/menu/comida/combosGenerales/cajitaAlmuerzo/Moovaburguesa.png', 41),
	-- ID: 9
    ('Cajita Infantil Pollo Moova Crispy', '1 Hamburguesa de Pollo Crispy + 1 bebida + 1 papa pequeña + 1 acompañamiento', 'Independiente', 34.00, 0.00, '/utiles/imagenes/menu/comida/combosGenerales/cajitaAlmuerzo/PolloMoovaCrispy.png', 41),
	-- ID: 10
    ('Cajita Infantil con Derretido (Almuerzo)', '1 Derretido + 1 bebida + 1 papa pequeña + 1 acompañamiento', 'Independiente', 30.00, 0.00, '/utiles/imagenes/menu/comida/combosGenerales/cajitaAlmuerzo/MooDerretido.png', 41),
	-- ============================ ALMUERZO ============================
    -- ID: 11
    ('Combo #1', '+ Bebida', 'Complementario', 0.00, 7.00, '/utiles/imagenes/menu/comida/combosGenerales/combosAlmuerzo/Combo1.png', NULL),
	-- ID: 12
    ('Combo #2', '+ Bebida + Papas', 'Complementario', 0.00, 13.00, '/utiles/imagenes/menu/comida/combosGenerales/combosAlmuerzo/Combo2.png', NULL),
	-- ID: 13
    ('Combo #3', '+ Bebida + Papas + Postres', 'Complementario', 0.00, 18.00, '/utiles/imagenes/menu/comida/combosGenerales/combosAlmuerzo/Combo3.png', NULL),
	-- ============================ ANTOJO ============================
	-- COMPLEMENTARIOS ESPECÍFICOS: Salados
    -- ID: 14
    ('Combo Salado #1', 'Gaseosa + Derretido', 'Complementario', 0.00, 7.00, '/utiles/imagenes/menu/comida/combosGenerales/combosAntojos/GaseosaDerretido.png', NULL),
	-- ID: 15
    ('Combo Salado #2', 'Café + Derretido', 'Complementario', 0.00, 6.00, '/utiles/imagenes/menu/comida/combosGenerales/combosAntojos/CafeDerretido.png', NULL),
    -- ID: 16
    ('Combo Salado #3', 'MooFizz + Derretido', 'Complementario', 0.00, 8.00, '/utiles/imagenes/menu/comida/combosGenerales/combosAntojos/MooFizzDerretido.png', NULL),
	-- ID: 17
    ('Combo Salado #4', 'Café + Papas Medianas', 'Complementario', 0.00, 7.00, '/utiles/imagenes/menu/comida/combosGenerales/combosAntojos/CafePapas.png', NULL),
	-- ID: 18
    ('Combo Salado #5', 'Gaseosa + Papas Medianas', 'Complementario', 0.00, 7.00, '/utiles/imagenes/menu/comida/combosGenerales/combosAntojos/GaseosaPapas.png', NULL),
	-- ID: 19
    ('Combo Salado #6', 'MooFizz + Papas Medianas', 'Complementario', 0.00, 8.00, '/utiles/imagenes/menu/comida/combosGenerales/combosAntojos/MooFizzPapas.png', NULL),
	-- ============================  POSTRES ============================
	-- COMPLEMENTARIOS ESPECÍFICOS: Dulces
	-- ID: 20
    ('Combo Dulce #1', 'Café + Pastel de Chocolate', 'Complementario', 0.00, 6.00, '/utiles/imagenes/menu/comida/combosGenerales/combosPostres/CafePChocolate.png', NULL),
	-- ID: 21
    ('Combo Dulce #2', 'Gaseosa + Pastel de Chocolate', 'Complementario', 0.00, 7.00, '/utiles/imagenes/menu/comida/combosGenerales/combosPostres/GaseosaPChocolate.png', NULL),
	-- ID: 22
    ('Combo Dulce #3', 'MooFizz + Pastel de Chocolate', 'Complementario', 0.00, 8.00, '/utiles/imagenes/menu/comida/combosGenerales/combosPostres/MooFizzPChocolate.png', NULL);

-- ///////// TABLA: PRODUCTO_COMBO /////////
INSERT INTO Producto_Combo(id_Producto, id_Combo) VALUES
	-- ============================ CAJITA INFANTIL - DESAYUNO ============================
	-- /*/*/*/*/* MooFfin de Queso y Huevo /*/*/*/*/* 
	-- GENERALES: Combo #1, #2, #3 (ids 11, 12, 13) para los 30 productos de Almuerzo (27-56)
	-- Qué producto se le ofrece a cada combo. LAS CAJITAS INFANTILES NO LLEVAN COMBO.
    -- /*/*/*/*/* HAMBURGUESA /*/*/*/*/*
    (27,11),(27,12),(27,13),
	
    -- /*/*/*/*/* HAMBOORGUESA JR /*/*/*/*/* 
    (28,11),(28,12),(28,13),
    
    -- /*/*/*/*/* HAMBOORGUESA DE TOCINO CON CHEDDAR /*/*/*/*/* 
	(29,11),(29,12),(29,13),
    
    -- /*/*/*/*/* BIG MOO /*/*/*/*/* 
	(30,11),(30,12),(30,13),
    
    -- /*/*/*/*/* SÚPER BIG MOO /*/*/*/*/* 
	(31,11),(31,12),(31,13),
	
    -- /*/*/*/*/*  CUARTO DE MOO LIBRA /*/*/*/*/* 
    (32,11),(32,12),(32,13),
    
    -- /*/*/*/*/*  CUARTO DE MOO LIBRA CON QUESO /*/*/*/*/* 
	(33,11),(33,12),(33,13),
    
    -- /*/*/*/*/* CUARTO DE MOO LIBRA CON TOCINO Y QUESO /*/*/*/*/* 
	(34,11),(34,12),(34,13),
    
    -- /*/*/*/*/* CUARTO DE MOO LIBRA CON DOBLE TOCINO Y QUESO /*/*/*/*/* 
	(35,11),(35,12),(35,13),
    
    -- /*/*/*/*/* CUARTO DE MOO LIBRA CON DOBLE CARNE /*/*/*/*/* 
	(36,11),(36,12),(36,13),
    
    -- /*/*/*/*/* CUARTO DE MOO LIBRA DELUXE CON QUESO /*/*/*/*/* 
	(37,11),(37,12),(37,13),
    
    -- /*/*/*/*/*  CUARTO DE MOO LIBRA DELUXE CON CARNE /*/*/*/*/* 
	(38,11),(38,12),(38,13),
    
    -- /*/*/*/*/* MOO INSANO DE CARNE /*/*/*/*/* 
	(39,11),(39,12),(39,13),
    
    -- /*/*/*/*/* MOO INSANO DE DOBLE CARNE /*/*/*/*/* 
	(40,11),(40,12),(40,13),
    
    -- /*/*/*/*/* MOOVABURGUESA /*/*/*/*/* 
	(41,11),(41,12),(41,13),
    
    -- /*/*/*/*/*  MOOVABURGUESA DOBLE /*/*/*/*/* 
	(42,11),(42,12),(42,13),
    
    -- /*/*/*/*/* MOOVABURGUESA TRIPLE /*/*/*/*/* 
	(43,11),(43,12),(43,13),
    
    -- /*/*/*/*/* BIG MOOVA /*/*/*/*/* 
	(44,11),(44,12),(44,13),
    
    -- /*/*/*/*/* BIG MOOVA DOBLE CARNE /*/*/*/*/* 
	(45,11),(45,12),(45,13),
    
    -- /*/*/*/*/* BIG MOOVA TRIPLE CARNE /*/*/*/*/* 
	(46,11),(46,12),(46,13),
    
    -- /*/*/*/*/* MOOVA CRISPY TOCINO Y QUESO /*/*/*/*/* 
	(47,11),(47,12),(47,13),
    
    -- /*/*/*/*/* MOOVA CRISPY TOCINO CON RANCH /*/*/*/*/* 
	(48,11),(48,12),(48,13),
    
    -- /*/*/*/*/* MOOVA CRISPY TOCINO CON RANCH /*/*/*/*/* 
	(49,11),(49,12),(49,13),
    
    -- /*/*/*/*/* MOOVA CRISPY DELUXE /*/*/*/*/* 
	(50,11),(50,12),(50,13),
    
    -- /*/*/*/*/* GOURMOOVA CRISPY /*/*/*/*/*
	(51,11),(51,12),(51,13),
    
    -- /*/*/*/*/* GOURMOOVA GRILLED /*/*/*/*/*
	(52,11),(52,12),(52,13),
    
    -- /*/*/*/*/* GOURMOOVA DE RES /*/*/*/*/*
	(53,11),(53,12),(53,13),
    
    -- /*/*/*/*/* PICO GUACAMOL GOURMOOVA CRISPY /*/*/*/*/*
	(54,11),(54,12),(54,13),
    
    -- /*/*/*/*/* PICO GUACAMOL GOURMOOVA GRILLED /*/*/*/*/*
	(55,11),(55,12),(55,13),
    
    -- /*/*/*/*/* PICO GUACAMOL GOURMOOVA DE RES /*/*/*/*/*
	(56,11),(56,12),(56,13),
    
    
	-- ESPECÍFICOS
    -- Derretido Moo Clásico -> Salado #1, #2, #3
	(68,14),(68,15),(68,16),   
	-- Papas Medianas        -> Salado #4, #5, #6
    (69,17),(69,18),(69,19),   
	-- Pastel de Chocolate   -> Dulce #1, #2, #3
    (59,20),(59,21),(59,22);   
	
-- ///////// TABLA: GRUPO COMBO /////////
	INSERT INTO Grupo_Combo (id_Combo, nombre, obligatorio, cantidad_minima, cantidad_maxima) VALUES
	-- Cajitas desayuno (principal fijo, jugo seleccionable, yogurt fijo)
    
    -- ============================ CAJITA INFANTIL - DESAYUNO ============================
	-- /*/*/*/*/* MooFfin de Queso y Huevo /*/*/*/*/* 
	(1, 'Principal - Cajita MooFfin Queso', TRUE,1,1), 
    (1, 'Jugo - Cajita MooFfin Queso', TRUE,1,1), 
    (1, 'Acompañamiento - Cajita MooFfin Queso', TRUE,1,1),
    
    -- /*/*/*/*/* MooFfin de Frijol y Huevo /*/*/*/*/* 
	(2, 'Principal - Cajita MooFfin Frijol y Huevo', TRUE,1,1), 
    (2, 'Jugo - Cajita MooFfin Frijol y Huevo', TRUE,1,1), 
    (2, 'Acompañamiento - Cajita MooFfin Frijol Huevo', TRUE,1,1),
    
    -- /*/*/*/*/* MooFfin de Salchicha /*/*/*/*/* 
	(3, 'Principal - Cajita MooFfin Salchicha', TRUE,1,1), 
    (3, 'Jugo - Cajita MooFfin Salchicha', TRUE,1,1), 
    (3, 'Acompañamiento - Cajita MooFfin Salchicha', TRUE,1,1),
    
    -- /*/*/*/*/* Derretido (Desayuno) /*/*/*/*/* 
	(4, 'Principal - Cajita Derretido Desayuno', TRUE,1,1), 
    (4, 'Jugo - Cajita Derretido Desayuno', TRUE,1,1), 
    (4, 'Acompañamiento - Cajita Derretido Desayuno', TRUE,1,1),
    
     -- ============================ CAJITA INFANTIL - ALMUERZO ============================
	-- Cajitas almuerzo (principal fijo, bebida seleccionable, papas fijas, yogurt fijo)
	-- /*/*/*/*/* Mooggets /*/*/*/*/* 
    (5, 'Principal - Cajita Mooggets', TRUE,1,1), 
    (5, 'Bebida - Cajita Mooggets', TRUE,1,1), 
    (5, 'Papas - Cajita Mooggets', TRUE,1,1), 
    (5, 'Acompañamiento - Cajita Mooggets', TRUE,1,1),
    
    -- /*/*/*/*/* Hamboorguesa /*/*/*/*/* 
	(6, 'Principal - Cajita Hamboorguesa', TRUE,1,1), 
    (6, 'Bebida - Cajita Hamboorguesa', TRUE,1,1), 
    (6, 'Papas - Cajita Hamboorguesa', TRUE,1,1), 
    (6, 'Acompañamiento - Cajita Hamboorguesa', TRUE,1,1),
    
    -- /*/*/*/*/* Hamboorguesa Jr /*/*/*/*/* 
	(7, 'Principal - Cajita Hamboorguesa Jr', TRUE,1,1), 
    (7, 'Bebida - Cajita Hamboorguesa Jr', TRUE,1,1), 
    (7, 'Papas - Cajita Hamboorguesa Jr', TRUE,1,1), 
    (7, 'Acompañamiento - Cajita Hamboorguesa Jr', TRUE,1,1),
    
    -- /*/*/*/*/* MoovaBurguesa /*/*/*/*/*  
	(8, 'Principal - Cajita MoovaBurguesa', TRUE,1,1), 
    (8, 'Bebida - Cajita MoovaBurguesa', TRUE,1,1), 
    (8, 'Papas - Cajita MoovaBurguesa', TRUE,1,1), 
    (8, 'Acompañamiento - Cajita MoovaBurguesa', TRUE,1,1),
    
    -- /*/*/*/*/* Pollo Moova Crispy /*/*/*/*/* 
	(9, 'Principal - Cajita Pollo Moova Crispy', TRUE,1,1), 
    (9, 'Bebida - Cajita Pollo Moova Crispy', TRUE,1,1), 
    (9, 'Papas - Cajita Pollo Moova Crispy', TRUE,1,1), 
    (9, 'Acompañamiento - Cajita Pollo Moova Crispy', TRUE,1,1),
    
    -- /*/*/*/*/* Derretido (Almuerzo) /*/*/*/*/* 
	(10, 'Principal - Cajita Derretido Almuerzo', TRUE,1,1), 
    (10, 'Bebida - Cajita Derretido Almuerzo', TRUE,1,1), 
    (10, 'Papas - Cajita Derretido Almuerzo', TRUE,1,1), 
    (10, 'Acompañamiento - Cajita Derretido Almuerzo', TRUE,1,1),
    
	-- Complementarios generales
    -- -- /*/*/*/*/* Combo #1 /*/*/*/*/* 
	(11, 'Bebida - Combo #1', TRUE,1,1),
    
    -- /*/*/*/*/* Combo #2 /*/*/*/*/* 
	(12, 'Bebida - Combo #2', TRUE,1,1), 
    (12, 'Papas - Combo #2', TRUE,1,1),
    
    -- /*/*/*/*/* Combo #3 /*/*/*/*/* 
	(13, 'Bebida - Combo #3', TRUE,1,1), 
    (13, 'Papas - Combo #3', TRUE,1,1), 
    (13, 'Postres - Combo #3', TRUE,1,1),
    
    
    -- ============================ ANTOJO ============================
	-- Complementarios específicos (Salado y Dulce)
    -- /*/*/*/*/* Combo Salado #1 /*/*/*/*/* 
	(14, 'Gaseosa - Combo Salado #1', TRUE,1,1),
    
    -- /*/*/*/*/* Combo Salado #2 /*/*/*/*/*
	(15, 'Café - Combo Salado #2', TRUE,1,1),
    
    -- /*/*/*/*/* Combo Salado #3 /*/*/*/*/*
	(16, 'MooFizz - Combo Salado #3', TRUE,1,1),
    
    -- /*/*/*/*/* Combo Salado #4 /*/*/*/*/*
	(17, 'Café - Combo Salado #4', TRUE,1,1),
    
    -- /*/*/*/*/* Combo Salado #5 /*/*/*/*/*
	(18, 'Gaseosa - Combo Salado #5', TRUE,1,1),
    
    -- /*/*/*/*/* Combo Salado #6 /*/*/*/*/*
	(19, 'MooFizz - Combo Salado #6', TRUE,1,1),
    
    -- ============================ DULCE ============================
    -- /*/*/*/*/* Combo Dulce #1 /*/*/*/*/*
	(20, 'Café - Combo Dulce #1', TRUE,1,1),
    -- /*/*/*/*/* Combo Dulce #2 /*/*/*/*/*
	(21, 'Gaseosa - Combo Dulce #2', TRUE,1,1),
    -- /*/*/*/*/* Combo Dulce #3 /*/*/*/*/*
	(22, 'MooFizz - Combo Dulce #3', TRUE,1,1);

-- ///////// TABLA: OPCION COMBO /////////
INSERT INTO Opcion_Combo (precio_extra, id_GrupoCombo, id_Producto) VALUES
	-- ============================ CAJITA INFANTIL - DESAYUNO ============================
	
    -- DEBE DE CONTENER GRUPO_COMBO: Principal fijo, jugo seleccionable, yogurt fijo
    -- /*/*/*/*/* MooFfin de Queso y Huevo /*/*/*/*/* 
    (0.00, 1, 13),    -- Principal: MooFfin de Queso y Huevo
	(0.00, 2, 111),   -- Jugo: Manzana
	(0.00, 2, 112),   -- Jugo: Naranja
	(0.00, 3, 113),   -- Acompañamiento: Yogurt de Fresa
    
    -- /*/*/*/*/* MooFfin de Frijol y Huevo /*/*/*/*/* 
    (0.00, 4, 12),    -- Principal: MooFfin de Frijol y Huevo
	(0.00, 5, 111),   -- Jugo: Manzana
	(0.00, 5, 112),   -- Jugo: Naranja
	(0.00, 6, 113),   -- Acompañamiento: Yogurt de Fresa
    
    -- /*/*/*/*/* MooFfin de Salchicha /*/*/*/*/* 
    (0.00, 7, 2),    -- Principal: MooFfin de Salchicha
	(0.00, 8, 111),   -- Jugo: Manzana
	(0.00, 8, 112),   -- Jugo: Naranja
	(0.00, 9, 113),   -- Acompañamiento: Yogurt de Fresa
    
    -- /*/*/*/*/* Derretido (Desayuno) /*/*/*/*/* 
    (0.00, 10, 68),    -- Principal: Derretido
	(0.00, 11, 111),   -- Jugo: Manzana
	(0.00, 11, 112),   -- Jugo: Naranja
	(0.00, 12, 113),   -- Acompañamiento: Yogurt de Fresa
    
    
    -- ============================ CAJITA INFANTIL - ALMUERZO ============================
	
    -- DEBE DE CONTENER GRUPO_COMBO: Principal fijo, jugo seleccionable, yogurt fijo
    -- /*/*/*/*/* Mooggets /*/*/*/*/* 
    (0.00, 13, 73),  -- Principal: Mooggets (fijo)
	(0.00, 14, 103), -- Bebida: Coca-Cola
    (0.00, 14, 104), -- Bebida: Coca-Cola Sin Azúcar
    (0.00, 14, 105), -- Bebida: Fanta
    (0.00, 14, 106), -- Bebida: Sprite
	(0.00, 14, 107), -- Bebida: Té Lipton de Limón
    (0.00, 14, 108), -- Bebida: Té Lipton de Frambuesa
    (0.00, 14, 109), -- Bebida: Té Lipton de Durazno
	(0.00, 15, 69),  -- Papas: Papas Medianas (fijo)
	(0.00, 16, 113), -- Acompañamiento: Yogurt (fijo)
    
    -- /*/*/*/*/* Hamboorguesa /*/*/*/*/* 
    (0.00, 17, 27),  -- Principal: Hamboorguesa (fijo)
	(0.00, 18, 103), -- Bebida: Coca-Cola
    (0.00, 18, 104), -- Bebida: Coca-Cola Sin Azúcar
    (0.00, 18, 105), -- Bebida: Fanta
    (0.00, 18, 106), -- Bebida: Sprite
	(0.00, 18, 107), -- Bebida: Té Lipton de Limón
    (0.00, 18, 108), -- Bebida: Té Lipton de Frambuesa
    (0.00, 18, 109), -- Bebida: Té Lipton de Durazno
	(0.00, 19, 69),  -- Papas: Papas Medianas (fijo)
	(0.00, 20, 113), -- Acompañamiento: Yogurt (fijo)
    
    -- /*/*/*/*/* Hamboorguesa Jr /*/*/*/*/* 
    (0.00, 21, 28),  -- Principal: Hamboorguesa Jr (fijo)
	(0.00, 22, 103), -- Bebida: Coca-Cola
    (0.00, 22, 104), -- Bebida: Coca-Cola Sin Azúcar
    (0.00, 22, 105), -- Bebida: Fanta
    (0.00, 22, 106), -- Bebida: Sprite
	(0.00, 22, 107), -- Bebida: Té Lipton de Limón
    (0.00, 22, 108), -- Bebida: Té Lipton de Frambuesa
    (0.00, 22, 109), -- Bebida: Té Lipton de Durazno
	(0.00, 23, 69),  -- Papas: Papas Medianas (fijo)
	(0.00, 24, 113), -- Acompañamiento: Yogurt (fijo)
    
    -- /*/*/*/*/* MoovaBurguesa /*/*/*/*/* 
    (0.00, 25, 41),  -- Principal: MoovaBurguesa (fijo)
	(0.00, 26, 103), -- Bebida: Coca-Cola
    (0.00, 26, 104), -- Bebida: Coca-Cola Sin Azúcar
    (0.00, 26, 105), -- Bebida: Fanta
    (0.00, 26, 106), -- Bebida: Sprite
	(0.00, 26, 107), -- Bebida: Té Lipton de Limón
    (0.00, 26, 108), -- Bebida: Té Lipton de Frambuesa
    (0.00, 26, 109), -- Bebida: Té Lipton de Durazno
	(0.00, 27, 69),  -- Papas: Papas Medianas (fijo)
	(0.00, 28, 113), -- Acompañamiento: Yogurt (fijo)
    
    -- /*/*/*/*/* Pollo Moova Crispy /*/*/*/*/* 
    (0.00, 29, 50),  -- Principal: Pollo Moova Crispy (fijo)
	(0.00, 30, 103), -- Bebida: Coca-Cola
    (0.00, 30, 104), -- Bebida: Coca-Cola Sin Azúcar
    (0.00, 30, 105), -- Bebida: Fanta
    (0.00, 30, 106), -- Bebida: Sprite
	(0.00, 30, 107), -- Bebida: Té Lipton de Limón
    (0.00, 30, 108), -- Bebida: Té Lipton de Frambuesa
    (0.00, 30, 109), -- Bebida: Té Lipton de Durazno
	(0.00, 31, 69),  -- Papas: Papas Medianas (fijo)
	(0.00, 32, 113), -- Acompañamiento: Yogurt (fijo)
    
    -- /*/*/*/*/* Derretido (Almuerzo) /*/*/*/*/* 
    (0.00, 33, 68),  -- Principal: Derretido (fijo)
	(0.00, 34, 103), -- Bebida: Coca-Cola
    (0.00, 34, 104), -- Bebida: Coca-Cola Sin Azúcar
    (0.00, 34, 105), -- Bebida: Fanta
    (0.00, 34, 106), -- Bebida: Sprite
	(0.00, 34, 107), -- Bebida: Té Lipton de Limón
    (0.00, 34, 108), -- Bebida: Té Lipton de Frambuesa
    (0.00, 34, 109), -- Bebida: Té Lipton de Durazno
	(0.00, 35, 69),  -- Papas: Papas Medianas (fijo)
	(0.00, 36, 113), -- Acompañamiento: Yogurt (fijo)
    
    -- /*/*/*/*/* COMBO #1 /*/*/*/*/* 
    (0.00, 37, 103), -- Bebida: Coca-Cola
    (0.00, 37, 104), -- Bebida: Coca-Cola Sin Azúcar
    (0.00, 37, 105), -- Bebida: Fanta
    (0.00, 37, 106), -- Bebida: Sprite
	(0.00, 37, 107), -- Bebida: Té Lipton de Limón
    (0.00, 37, 108), -- Bebida: Té Lipton de Frambuesa
    (0.00, 37, 109), -- Bebida: Té Lipton de Durazno
    
    -- /*/*/*/*/* COMBO #2 /*/*/*/*/* 
    (0.00, 38, 103), -- Bebida: Coca-Cola
    (0.00, 38, 104), -- Bebida: Coca-Cola Sin Azúcar
    (0.00, 38, 105), -- Bebida: Fanta
    (0.00, 38, 106), -- Bebida: Sprite
	(0.00, 38, 107), -- Bebida: Té Lipton de Limón
    (0.00, 38, 108), -- Bebida: Té Lipton de Frambuesa
    (0.00, 38, 109), -- Bebida: Té Lipton de Durazno
	(0.00, 39, 69),  -- Papas: Papas Medianas (Predeterminado)
	(4.00, 39, 70),  -- Papas: Papas Grandes +Q.4.00
	(6.00, 39, 71),  -- Papas: MooPatatas +Q.6.00
    
    -- /*/*/*/*/* COMBO #3 /*/*/*/*/* 
    (0.00, 40, 103), -- Bebida: Coca-Cola
    (0.00, 40, 104), -- Bebida: Coca-Cola Sin Azúcar
    (0.00, 40, 105), -- Bebida: Fanta
    (0.00, 40, 106), -- Bebida: Sprite
	(0.00, 40, 107), -- Bebida: Té Lipton de Limón
    (0.00, 40, 108), -- Bebida: Té Lipton de Frambuesa
    (0.00, 40, 109), -- Bebida: Té Lipton de Durazno
	(0.00, 41, 69),  -- Papas: Papas Medianas (Predeterminado)
	(4.00, 41, 70),  -- Papas: Papas Grandes +Q.4.00
	(6.00, 41, 71),  -- Papas: MooPatatas +Q.6.00
    (0.00, 42, 57),  -- Postre: Pie de Manzana
    (0.00, 42, 58),  -- Postre: Pie de Queso
    
    -- ============================ COMBO SALADO ============================
	
    -- DEBE DE CONTENER GRUPO_COMBO: Bebida Fija (Caliente y Fría)
    -- /*/*/*/*/* COMBO SALADO #1 /*/*/*/*/*
    (0.00, 43, 103), -- Bebida: Coca-Cola
    (0.00, 43, 104), -- Bebida: Coca-Cola Sin Azúcar
    (0.00, 43, 105), -- Bebida: Fanta
    (0.00, 43, 106), -- Bebida: Sprite
	(0.00, 43, 107), -- Bebida: Té Lipton de Limón
    (0.00, 43, 108), -- Bebida: Té Lipton de Frambuesa
    (0.00, 43, 109), -- Bebida: Té Lipton de Durazno
    
    -- /*/*/*/*/* COMBO SALADO #2 /*/*/*/*/*
    (0.00, 44, 79),  -- Bebida: Café
    
    -- /*/*/*/*/* COMBO SALADO #3 /*/*/*/*/*
    (0.00, 45, 87),  -- Bebida: MooFizz Rosado
    (0.00, 45, 88),  -- Bebida: MooFizz Verde
    (0.00, 45, 89),  -- Bebida: MooFizz Azul
    (0.00, 45, 90),  -- Bebida: MooFizz Amarillo
    
    -- /*/*/*/*/* COMBO SALADO #4 /*/*/*/*/*
    (0.00, 46, 79),  -- Bebida: Café
    
    -- /*/*/*/*/* COMBO SALADO #5 /*/*/*/*/*
    (0.00, 47, 103), -- Bebida: Coca-Cola
    (0.00, 47, 104), -- Bebida: Coca-Cola Sin Azúcar
    (0.00, 47, 105), -- Bebida: Fanta
    (0.00, 47, 106), -- Bebida: Sprite
	(0.00, 47, 107), -- Bebida: Té Lipton de Limón
    (0.00, 47, 108), -- Bebida: Té Lipton de Frambuesa
    (0.00, 47, 109), -- Bebida: Té Lipton de Durazno
    
    -- /*/*/*/*/* COMBO SALADO #6 /*/*/*/*/*
    (0.00, 48, 87),  -- Bebida: MooFizz Rosado
    (0.00, 48, 88),  -- Bebida: MooFizz Verde
    (0.00, 48, 89),  -- Bebida: MooFizz Azul
    (0.00, 48, 90),  -- Bebida: MooFizz Amarillo
    
    -- ============================ COMBO DULCE ============================
	
    -- DEBE DE CONTENER GRUPO_COMBO: Bebida Fija (Caliente y Fría)
    -- /*/*/*/*/* COMBO DULCE #1 /*/*/*/*/*
    (0.00, 49, 79),  -- Bebida: Café
    
    -- /*/*/*/*/* COMBO DULCE  #2 /*/*/*/*/*
    (0.00, 50, 103), -- Bebida: Coca-Cola
    (0.00, 50, 104), -- Bebida: Coca-Cola Sin Azúcar
    (0.00, 50, 105), -- Bebida: Fanta
    (0.00, 50, 106), -- Bebida: Sprite
	(0.00, 50, 107), -- Bebida: Té Lipton de Limón
    (0.00, 50, 108), -- Bebida: Té Lipton de Frambuesa
    (0.00, 50, 109), -- Bebida: Té Lipton de Durazno
    
    -- /*/*/*/*/* COMBO DULCE #3 /*/*/*/*/*
    (0.00, 51, 87),  -- Bebida: MooFizz Rosado
    (0.00, 51, 88),  -- Bebida: MooFizz Verde
    (0.00, 51, 89),  -- Bebida: MooFizz Azul
    (0.00, 51, 90);  -- Bebida: MooFizz Amarillo
    
    
-- ///////// TABLA: GRUPO PERSONALIZACION /////////
INSERT INTO Grupo_Personalizacion (id_Producto, nombre, obligatorio, cantidad_minima, cantidad_maxima) VALUES
	-- /*/*/*/*/* MooFlurry de Oreo /*/*/*/*/* 
	(62,'Topping - MooFlurry Oreo',FALSE,0,1),
	
	-- /*/*/*/*/* MooFlurry de M&Ms /*/*/*/*/* 
	(63,'Topping - MooFlurry M&Ms',FALSE,0,1),
        
	-- /*/*/*/*/* Moovandae /*/*/*/*/* 
	(67,'Topping - Moovandae',TRUE,1,1),
        
	-- /*/*/*/*/* Té Chai de Vainilla /*/*/*/*/* 
	(84,'Tipo de Leche - Té Chai de Vainilla',TRUE,1,1),
	(84,'Azúcar',FALSE,0,1),
        
	-- /*/*/*/*/* MooFizz Rosado /*/*/*/*/* 
	(87,'Azúcar - Rosado',FALSE,0,1),
	(87,'Hielo',FALSE,0,1),
        
	-- /*/*/*/*/* MooFizz Verde /*/*/*/*/* 
	(88,'Azúcar - Verde',FALSE,0,1),
	(88,'Hielo',FALSE,0,1),
        
	-- /*/*/*/*/* MooFizz Azul /*/*/*/*/* 
	(89,'Azúcar - Azul',FALSE,0,1),
	(89,'Hielo',FALSE,0,1),
        
	-- /*/*/*/*/* MooFizz Amarillo /*/*/*/*/* 
	(90,'Azúcar - Amarillo',FALSE,0,1),
	(90,'Hielo',FALSE,0,1),
        
	-- /*/*/*/*/* Smoothie de Frutos Rojos /*/*/*/*/* 
	(91,'Tipo de Líquido - Smoothie de Frutos Rojos',TRUE,1,1),
	(91,'Azúcar',FALSE,0,1),
	(91,'Hielo',FALSE,0,1),
        
	-- /*/*/*/*/* Smoothie de Mango /*/*/*/*/* 
	(92,'Tipo de Líquido - Smoothie de Mango',TRUE,1,1),
	(92,'Azúcar',FALSE,0,1),
	(92,'Hielo',FALSE,0,1),
        
	-- /*/*/*/*/* Frappé de Oreo /*/*/*/*/* 
	(93,'Tipo de Leche - Frappé de Oreo',TRUE,1,1),
	(93,'Azúcar',FALSE,0,1),
	(93,'Hielo',FALSE,0,1),
        
	-- /*/*/*/*/* Frappé de Vainilla /*/*/*/*/* 
	(94,'Tipo de Leche - Frappé de Vainilla',TRUE,1,1),
	(94,'Azúcar',FALSE,0,1),
	(94,'Hielo',FALSE,0,1),
        
	-- /*/*/*/*/* Frappé de Chocolate /*/*/*/*/* 
	(95,'Tipo de Leche - Frappé de Chocolate',TRUE,1,1),
	(95,'Azúcar',FALSE,0,1),
	(95,'Hielo',FALSE,0,1),
        
	-- /*/*/*/*/* Frappé de Caramelo /*/*/*/*/* 
	(96,'Tipo de Leche - Frappé de Caramelo',TRUE,1,1),
	(96,'Azúcar',FALSE,0,1),
	(96,'Hielo',FALSE,0,1),
        
	-- /*/*/*/*/* Moova Iced /*/*/*/*/* 
	(97,'Tipo de Leche - Moova Iced',TRUE,1,1),
	(97,'Azúcar',FALSE,0,1),
	(97,'Hielo',FALSE,0,1),
	
	-- /*/*/*/*/* Moova Iced de Vainilla /*/*/*/*/* 
	(98,'Tipo de Leche - Moova Iced de Vainilla',TRUE,1,1),
	(98,'Azúcar',FALSE,0,1),
	(98,'Hielo',FALSE,0,1),
        
	-- /*/*/*/*/* Moova Iced de Chocolate /*/*/*/*/* 
	(99,'Tipo de Leche - Moova Iced de Chocolate',TRUE,1,1),
	(99,'Azúcar',FALSE,0,1),
	(99,'Hielo',FALSE,0,1),
        
	-- /*/*/*/*/* Moova Iced de Caramelo /*/*/*/*/* 
	(100,'Tipo de Leche - Moova Iced de Caramelo',TRUE,1,1),
	(100,'Azúcar',FALSE,0,1),
	(100,'Hielo',FALSE,0,1),
        
	-- /*/*/*/*/* Moova Chai Frappé de Té Verde /*/*/*/*/*  
	(101,'Tipo de Líquido - Moova Chai Frappé de Té Verde',TRUE,1,1),
	(101,'Azúcar',FALSE,0,1),
	(101,'Hielo',FALSE,0,1),
	
	-- /*/*/*/*/* Moova Chai Frappé de Vainilla /*/*/*/*/*  
	(102,'Tipo de Líquido - Moova Chai Frappé de Vainilla',TRUE,1,1),
	(102,'Azúcar',FALSE,0,1),
	(102,'Hielo',FALSE,0,1),
	
	-- /*/*/*/*/* Coca-Cola /*/*/*/*/* 
	(103,'Hielo - Coca-Cola',FALSE,0,1),
        
	-- /*/*/*/*/* Coca-Cola sin Azúcar /*/*/*/*/* 
	(104,'Hielo - Coca-Cola sin Azúcar',FALSE,0,1),
	
	-- /*/*/*/*/* Fanta /*/*/*/*/* 
	(105,'Hielo - Fanta',FALSE,0,1),
        
	-- /*/*/*/*/* Sprite /*/*/*/*/* 
	(106,'Hielo - Sprite',FALSE,0,1),
        
	-- /*/*/*/*/* Té Lipton de Limón /*/*/*/*/* 
	(107,'Hielo - Té Lipton de Limón',FALSE,0,1),
        
	-- /*/*/*/*/* Té Lipton de Frambuesa /*/*/*/*/* 
	(108,'Hielo - Té Lipton de Frambuesa',FALSE,0,1),
        
	-- /*/*/*/*/* Té Lipton de Durazno /*/*/*/*/* 
	(109,'Hielo - Té Lipton de Durazno',FALSE,0,1);
        
-- ///////// TABLA: OPCION PERSONALIZACION /////////
INSERT INTO Opcion_Personalizacion(id_GrupoPersonalizacion,id_Ingrediente, precio_extra, es_predeterminada) VALUES
	-- /*/*/*/*/* MooFlurry de Oreo /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Topping
	-- Caramelo
	(1,93,3.00,FALSE),
	-- Chocolate
	(1,94,3.00,FALSE),
	-- Fresa
	(1,95,3.00, FALSE),
	
	-- /*/*/*/*/* MooFlurry de M&Ms /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Topping
	-- Caramelo
	(2,93,3.00,FALSE),
	-- Chocolate
	(2,94,3.00,FALSE),
	-- Fresa
	(2,95,3.00, FALSE),

	-- /*/*/*/*/* Moovandae /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Topping
	-- Caramelo
	(3,93,3.00,FALSE),
	-- Chocolate
	(3,94,3.00,FALSE),
	-- Fresa
	(3,95,3.00, FALSE),

	-- /*/*/*/*/* Té Chai de Vainilla /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Tipo de Leche
	-- Agua
	(4,57,0.00,TRUE),
	-- Leche Entera
	(4,51,4.00,FALSE),
	-- Leche Descremada Light 
	(4,49,4.00,FALSE),
	-- Leche Deslactosada
	(4,50,3.00,FALSE),
	-- Leche Semi Deslactosada
	(4,53,3.00,FALSE),
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(5,77,0.00,TRUE),
        
	-- /*/*/*/*/* MooFizz Rosado /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(6,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(7,60,0.00,TRUE),
        
	-- /*/*/*/*/* MooFizz Verde /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(8,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(9,60,0.00,TRUE),
        
	-- /*/*/*/*/* MooFizz Azul /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(10,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(11,60,0.00,TRUE),
        
	-- /*/*/*/*/* MooFizz Amarillo /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(12,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(13,60,0.00,TRUE),
        
	-- /*/*/*/*/* Smoothie de Frutos Rojos /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Tipo de Líquido
	-- Agua
	(14,57,0.00,TRUE),
	-- Leche Entera
	(14,51,4.00,FALSE),
	-- Leche Descremada Light 
	(14,49,4.00,FALSE),
	-- Leche Deslactosada
	(14,50,3.00,FALSE),
	-- Leche Semi Deslactosada
	(14,53,3.00,FALSE),
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(15,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(16,60,0.00,TRUE),
        
	-- /*/*/*/*/* Smoothie de Mango /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Tipo de Líquido
	-- Agua
	(17,57,0.00,TRUE),
	-- Leche Entera
	(17,51,4.00,FALSE),
	-- Leche Descremada Light 
	(17,49,4.00,FALSE),
	-- Leche Deslactosada
	(17,50,3.00,FALSE),
	-- Leche Semi Deslactosada
	(17,53,3.00,FALSE),
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(18,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(19,60,0.00,TRUE),
        
	-- /*/*/*/*/* Frappé de Oreo /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Tipo de Leche
	-- Leche Entera
	(20,51,4.00,TRUE),
	-- Leche Descremada Light 
	(20,49,4.00,FALSE),
	-- Leche Deslactosada
	(20,50,3.00,FALSE),
	-- Leche Semi Deslactosada
	(20,53,3.00,FALSE),
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(21,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(22,60,0.00,TRUE),
        
	-- /*/*/*/*/* Frappé de Vainilla /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Tipo de Leche
	-- Leche Entera
	(23,51,4.00,TRUE),
	-- Leche Descremada Light 
	(23,49,4.00,FALSE),
	-- Leche Deslactosada
	(23,50,3.00,FALSE),
	-- Leche Semi Deslactosada
	(23,53,3.00,FALSE),
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(24,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(25,60,0.00,TRUE),
        
	-- /*/*/*/*/* Frappé de Chocolate /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Tipo de Leche
	-- Leche Entera
	(26,51,4.00,TRUE),
	-- Leche Descremada Light 
	(26,49,4.00,FALSE),
	-- Leche Deslactosada
	(26,50,3.00,FALSE),
	-- Leche Semi Deslactosada
	(26,53,3.00,FALSE),
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(27,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(28,60,0.00,TRUE),
	
	-- /*/*/*/*/* Frappé de  Caramelo /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Tipo de Leche
	-- Leche Entera
	(29,51,4.00,TRUE),
	-- Leche Descremada Light 
	(29,49,4.00,FALSE),
	-- Leche Deslactosada
	(29,50,3.00,FALSE),
	-- Leche Semi Deslactosada
	(29,53,3.00,FALSE),
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(30,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(31,60,0.00,TRUE),
	
	-- /*/*/*/*/* Moova Iced /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Tipo de Leche
	-- Leche Entera
	(32,51,4.00,TRUE),
	-- Leche Descremada Light 
	(32,49,4.00,FALSE),
	-- Leche Deslactosada
	(32,50,3.00,FALSE),
	-- Leche Semi Deslactosada
	(32,53,3.00,FALSE),
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(33,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(34,60,0.00,TRUE),
	
	-- /*/*/*/*/* Moova Iced de Vainilla /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Tipo de Leche
	-- Leche Entera
	(35,51,4.00,TRUE),
	-- Leche Descremada Light 
	(35,49,4.00,FALSE),
	-- Leche Deslactosada
	(35,50,3.00,FALSE),
	-- Leche Semi Deslactosada
	(35,53,3.00,FALSE),
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(36,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(37,60,0.00,TRUE),
        
	-- /*/*/*/*/* Moova Iced de Chocolate /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Tipo de Leche
	-- Leche Entera
	(38,51,4.00,TRUE),
	-- Leche Descremada Light 
	(38,49,4.00,FALSE),
	-- Leche Deslactosada
	(38,50,3.00,FALSE),
	-- Leche Semi Deslactosada
	(38,53,3.00,FALSE),
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(39,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(40,60,0.00,TRUE),
	
	-- /*/*/*/*/* Moova Iced de Caramelo /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Tipo de Leche
	-- Leche Entera
	(41,51,4.00,TRUE),
	-- Leche Descremada Light 
	(41,49,4.00,FALSE),
	-- Leche Deslactosada
	(41,50,3.00,FALSE),
	-- Leche Semi Deslactosada
	(41,53,3.00,FALSE),
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(42,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(43,60,0.00,TRUE),
        
	-- /*/*/*/*/* Moova Chai Frappé de Té Verde /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Tipo de Líquido
	-- Agua
	(44,57,0.00,TRUE),
	-- Leche Entera
	(44,51,4.00,FALSE),
	-- Leche Descremada Light 
	(44,49,4.00,FALSE),
	-- Leche Deslactosada
	(44,50,3.00,FALSE),
	-- Leche Semi Deslactosada
	(44,53,3.00,FALSE),
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(45,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(46,60,0.00,TRUE),
        
	-- /*/*/*/*/* Moova Chai Frappé de Vainilla /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Tipo de Líquido
	-- Agua
	(47,57,0.00,TRUE),
	-- Leche Entera
	(47,51,4.00,FALSE),
	-- Leche Descremada Light 
	(47,49,4.00,FALSE),
	-- Leche Deslactosada
	(47,50,3.00,FALSE),
	-- Leche Semi Deslactosada
	(47,53,3.00,FALSE),
	-- //// GRUPO_PERSONALIZACION: Azúcar
	(48,77,0.00,TRUE),
	-- //// GRUPO_PERSONALIZACION: Hielo
	(49,60,0.00,TRUE),
        
	-- /*/*/*/*/* Coca-Cola /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Hielo
	(50,60,0.00,TRUE),
        
	-- /*/*/*/*/* Coca-Cola sin Azúcar /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Hielo
	(51,60,0.00,TRUE),
        
	-- /*/*/*/*/* Fanta /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Hielo
	(52,60,0.00,TRUE),
        
	-- /*/*/*/*/* Sprite /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Hielo
	(53,60,0.00,TRUE),
        
	-- /*/*/*/*/* Té Lipton de Limón /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Hielo
	(54,60,0.00,TRUE),
        
	-- /*/*/*/*/* Té Lipton de Frambuesa /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Hielo
	(55,60,0.00,TRUE),

	-- /*/*/*/*/* Té Lipton de Durazno /*/*/*/*/*
	-- //// GRUPO_PERSONALIZACION: Hielo
	(56,60,0.00,TRUE);
        
INSERT INTO Descuento (nombre, descripcion, valor, cantidad_minima, fecha_inicio, tipo) VALUES
	('Descuento Q.50.00',  'Descuento por compras desde Q.50.00',  5.00,  50,'2026-09-21 00:00:00', 'Porcentaje'),
	('Descuento Q.100.00', 'Descuento por compras desde Q.100.00', 10.00, 100,'2026-09-21 00:00:00', 'Porcentaje'),
	('Descuento Q.150.00', 'Descuento por compras desde Q.150.00', 15.00, 150,'2026-09-21 00:00:00', 'Porcentaje'),
	('Descuento Q.200.00', 'Descuento por compras desde Q.200.00', 20.00, 200,'2026-09-21 00:00:00', 'Porcentaje'),
	('Descuento Q.250.00', 'Descuento por compras desde Q.250.00', 25.00, 250,'2026-09-21 00:00:00', 'Porcentaje');
    

--  ------------------ PERSONALIZACIÓN DE HAMBURGUESAS BASADA EN LA RECETA

-- Crear una sección de personalización para cada hamburguesa que todavía no tenga una.

INSERT INTO Grupo_Personalizacion
(
    id_Producto,
    nombre,
    obligatorio,
    cantidad_minima,
    cantidad_maxima
)
SELECT
    p.id_Producto,
    'Ingredientes',
    FALSE,
    0,
    20
FROM Producto p
WHERE p.id_Producto BETWEEN 27 AND 56
AND EXISTS
(
    SELECT 1
    FROM Receta r
    WHERE r.id_Producto = p.id_Producto
)
AND NOT EXISTS
(
    SELECT 1
    FROM Grupo_Personalizacion gp
    WHERE gp.id_Producto = p.id_Producto
    AND gp.nombre = 'Ingredientes'
);


-- ------------------ AGREGAR INGREDIENTES DE LA RECETA COMO OPCIONES

INSERT INTO Opcion_Personalizacion
(
    id_GrupoPersonalizacion,
    id_Ingrediente,
    precio_extra,
    es_predeterminada
)
SELECT
    gp.id_GrupoPersonalizacion,
    r.id_Ingrediente,
    COALESCE(i.precio, 0.00),
    TRUE
FROM Grupo_Personalizacion gp

INNER JOIN Receta r
    ON r.id_Producto = gp.id_Producto

INNER JOIN Ingrediente i
    ON i.id_Ingrediente = r.id_Ingrediente

WHERE gp.nombre = 'Ingredientes'
AND gp.id_Producto BETWEEN 27 AND 56

AND NOT EXISTS
(
    SELECT 1
    FROM Opcion_Personalizacion op
    WHERE op.id_GrupoPersonalizacion = gp.id_GrupoPersonalizacion
    AND op.id_Ingrediente = r.id_Ingrediente
);
	
    SELECT * FROM Combo;
	SELECT * FROM Producto;
    SELECT * FROM Producto_Combo;
    SELECT * FROM Grupo_Combo;
    SELECT * FROM Opcion_Combo;
    SELECT * FROM Categoria;
	SELECT * FROM subcategoria;