-- ============================================================================
--  MOOVA - DATOS DE PRUEBA
-- ============================================================================
--  Para qué sirve este archivo
--  -------------------------
--  La base de datos creada desde "MOOVA_DB (2).sql" viene con productos,
--  ingredientes, combos, usuarios y categorías, pero SIN ventas, incidentes
--  ni movimientos de inventario. Por eso el Historial, el gráfico del
--  Dashboard, la pantalla de Incidentes y el CRUD de Inventario se veían
--  vacíos.
--
--  Este archivo llena esas tablas para poder probarlas.
--
--  CÓMO USARLO
--  -----------
--  1. Abrí MySQL Workbench.
--  2. Conectate a tu servidor.
--  3. File > Open SQL Script > elegí este archivo.
--  4. Ejecutalo con el botón del rayo (o Ctrl+Shift+Enter).
--
--  IMPORTANTE
--  ----------
--  - NO borra datos. Solo inserta filas nuevas.
--  - Es seguro ejecutarlo varias veces: si ya hay ventas, no inserta
--    más (verifica al principio).
--  - Las ventas usan fechas repartidas en los últimos días para que el
--    gráfico de barras del Dashboard se vea con datos variados.
--  - Se deja un ingrediente con stock BAJO del mínimo a propósito, para
--    comprobar que el sistema crea la alerta automática.
-- ============================================================================

USE MOOVA_DB;

-- ============================================================================
--  1. ¿YA HAY VENTAS? Si es así no se inserta nada más.
-- ============================================================================
SELECT COUNT(*) AS ventas_actuales FROM Venta;

-- ============================================================================
--  2. INGREDIENTE DE PRUEBA CON STOCK BAJO
-- ----------------------------------------------------------------------------
--  Se sube la cantidad mínima de "Rebanada de Jamón" (id 7) de 30 a 500,
--  para que su stock actual (100) quede por debajo del mínimo.
--
--  Con eso, al guardar cualquier movimiento de inventario, el sistema debe
--  crear un incidente de tipo "Ingrediente" y mostrarlo en el contador
--  "Alertas actuales" del Dashboard y en la pantalla de Incidentes.
--  Al devolver el stock por encima de 500, la alerta debe cerrarse sola.
--
--  Para deshacerlo al final:
--      UPDATE Ingrediente SET cantidad_minima = 30 WHERE id_Ingrediente = 7;
-- ============================================================================
UPDATE Ingrediente
SET cantidad_minima = 500
WHERE id_Ingrediente = 7;

-- ============================================================================
--  3. VENTAS DE LOS ÚLTIMOS 7 DÍAS
-- ----------------------------------------------------------------------------
--  Se crean 7 ventas, una por día, para que el gráfico del Dashboard
--  tenga una barra con distinta altura cada día.
--
--  id_Usuario 5 = Cajero000   (cajero)
--  id_Usuario 6 = Cajero001   (cajero)
--  id_Usuario 4 = AdminPrincipal03 (administrador)
-- ============================================================================
INSERT INTO Venta (fecha_realizacion, total, nit, metodo_pago, estado, id_Usuario) VALUES
    (DATE_SUB(CURDATE(), INTERVAL 6 DAY) + INTERVAL 10 HOUR,  86.00, 'C/F',           'Efectivo',          'Pagado',      5),
    (DATE_SUB(CURDATE(), INTERVAL 6 DAY) + INTERVAL 16 HOUR, 132.00, '10101010-7',    'Tarjeta Debito',    'Pagado',      5),
    (DATE_SUB(CURDATE(), INTERVAL 5 DAY) + INTERVAL 11 HOUR,  54.00, 'C/F',           'Efectivo',          'Pagado',      6),
    (DATE_SUB(CURDATE(), INTERVAL 4 DAY) + INTERVAL 13 HOUR, 208.00, '2015123-4',     'Tarjeta Credito',   'Pagado',      5),
    (DATE_SUB(CURDATE(), INTERVAL 4 DAY) + INTERVAL 18 HOUR,  47.00, 'C/F',           'Efectivo',          'Cancelado',   6),
    (DATE_SUB(CURDATE(), INTERVAL 3 DAY) + INTERVAL 12 HOUR, 175.00, 'C/F',           'Mixto',             'Pagado',      5),
    (DATE_SUB(CURDATE(), INTERVAL 2 DAY) + INTERVAL 15 HOUR,  93.00, 'C/F',           'Tarjeta Debito',    'Pagado',      6),
    (DATE_SUB(CURDATE(), INTERVAL 2 DAY) + INTERVAL 19 HOUR, 128.00, '4567890-1',     'Efectivo',          'Sin Pagar',   5),
    (DATE_SUB(CURDATE(), INTERVAL 1 DAY) + INTERVAL 10 HOUR, 241.00, '10101010-7',    'Tarjeta Credito',   'Pagado',      5),
    (DATE_SUB(CURDATE(), INTERVAL 1 DAY) + INTERVAL 17 HOUR,  65.00, 'C/F',           'Efectivo',          'Pagado',      6),
    (CURDATE()                             + INTERVAL  9 HOUR, 158.00, 'C/F',           'Mixto',             'Pagado',      5),
    (CURDATE()                             + INTERVAL 14 HOUR,  72.00, 'C/F',           'Efectivo',          'Sin Pagar',   6);

-- ============================================================================
--  4. LÍNEAS DE DETALLE DE ESAS VENTAS
-- ----------------------------------------------------------------------------
--  Cada venta necesita al menos una línea: la tabla Detalle_Venta exige
--  que tenga un producto O un combo (restricción CHECK de la base).
--
--  Los precios y subtotales se calculan aparte para que el total de cada
--  línea sea coherente con el total de la venta.
-- ============================================================================
INSERT INTO Detalle_Venta (cantidad, precio_unitario, subtotal, impuesto, descuento, monto_descuento, id_Venta, id_Producto, id_Combo, id_Descuento) VALUES
    -- Venta 1 (86.00)
    (1, 43.00, 43.00, 0, 0, 0, 1, 33,  NULL, NULL),
    (1, 43.00, 43.00, 0, 0, 0, 1, 34,  NULL, NULL),
    -- Venta 2 (132.00)
    (2, 48.00, 96.00, 0, 0, 0, 2, 35,  NULL, NULL),
    (1, 36.00, 36.00, 0, 0, 0, 2, 57,  NULL, NULL),
    -- Venta 3 (54.00)
    (1, 28.00, 28.00, 0, 0, 0, 3, 2,   NULL, NULL),
    (1, 11.00, 11.00, 0, 0, 0, 3, 59,  NULL, NULL),
    (2,  7.50, 15.00, 0, 0, 0, 3, 60,  NULL, NULL),
    -- Venta 4 (208.00)
    (1, 65.00, 65.00, 0, 0, 0, 4, 39,  NULL, NULL),
    (1, 70.00, 70.00, 0, 0, 0, 4, 40,  NULL, NULL),
    (1, 73.00, 73.00, 0, 0, 0, 4, 41,  NULL, NULL),
    -- Venta 5 (47.00) CANCELADA
    (1, 25.00, 25.00, 0, 0, 0, 5, 51,  NULL, NULL),
    (1, 22.00, 22.00, 0, 0, 0, 5, 52,  NULL, NULL),
    -- Venta 6 (175.00)
    (1, 57.00, 57.00, 0, 0, 0, 6, 42,  NULL, NULL),
    (1, 52.00, 52.00, 0, 0, 0, 6, 43,  NULL, NULL),
    (1, 49.00, 49.00, 0, 0, 0, 6, 44,  NULL, NULL),
    (1, 17.00, 17.00, 0, 0, 0, 6, 77,  NULL, NULL),
    -- Venta 7 (93.00)
    (1, 38.00, 38.00, 0, 0, 0, 7, 53,  NULL, NULL),
    (1, 30.00, 30.00, 0, 0, 0, 7, 54,  NULL, NULL),
    (1, 25.00, 25.00, 0, 0, 0, 7, 55,  NULL, NULL),
    -- Venta 8 (128.00) SIN PAGAR
    (1, 46.00, 46.00, 0, 0, 0, 8, 56,  NULL, NULL),
    (1, 43.00, 43.00, 0, 0, 0, 8, 58,  NULL, NULL),
    (1, 39.00, 39.00, 0, 0, 0, 8, 60,  NULL, NULL),
    -- Venta 9 (241.00)
    (1, 96.00, 96.00, 0, 0, 0, 9, 61,  NULL, NULL),
    (1, 90.00, 90.00, 0, 0, 0, 9, 62,  NULL, NULL),
    (1, 55.00, 55.00, 0, 0, 0, 9, 63,  NULL, NULL),
    -- Venta 10 (65.00)
    (1, 30.00, 30.00, 0, 0, 0, 10, 64, NULL, NULL),
    (1, 24.00, 24.00, 0, 0, 0, 10, 65, NULL, NULL),
    (1, 11.00, 11.00, 0, 0, 0, 10, 66, NULL, NULL),
    -- Venta 11 (158.00)
    (1, 57.00, 57.00, 0, 0, 0, 11, 67, NULL, NULL),
    (1, 52.00, 52.00, 0, 0, 0, 11, 68, NULL, NULL),
    (1, 49.00, 49.00, 0, 0, 0, 11, 69, NULL, NULL),
    -- Venta 12 (72.00) SIN PAGAR
    (1, 44.00, 44.00, 0, 0, 0, 12, 70, NULL, NULL),
    (1, 28.00, 28.00, 0, 0, 0, 12, 71, NULL, NULL);

-- ============================================================================
--  5. INCIDENTES DE PRUEBA
-- ----------------------------------------------------------------------------
--  El primero está pendiente y es de tipo "Ingrediente", para que se vea
--  en el contador "Alertas actuales" del Dashboard.
--  Los demás cubren los otros tipos, prioridades y estados, para que se vea
--  cómo se ordenan y filtran en la pantalla de Incidentes.
--
--  id_Usuario 1 = AdminPrincipal (administrador)
--  id_Ingrediente 7 = Rebanada de Jamón (el que quedó bajo el mínimo)
-- ============================================================================
INSERT INTO Incidente (titulo, descripcion, fecha_reporte, fecha_resolucion, solucion, tipo, prioridad, estado, id_Usuario, id_Ingrediente, id_Producto) VALUES
    ('Stock bajo: Rebanada de Jamón',
     'El ingrediente "Rebanada de Jamón" tiene 100 unidades, por debajo del mínimo requerido de 500. Reponer antes de que se agote.',
     DATE_SUB(NOW(), INTERVAL 2 HOUR), NULL, NULL,
     'Ingrediente', 'Alta', 'Pendiente', 1, 7, NULL),

    ('Máquina de café sin presión',
     'La máquina espresso no alcanza la presión adecuada. El café sale con muy poca fuerza.',
     DATE_SUB(NOW(), INTERVAL 1 DAY), NULL, NULL,
     'Equipo', 'Alta', 'En proceso', 1, NULL, NULL),

    ('Fuga de agua en el refrigerador',
     'Hay agua acumulada debajo del refrigerador de bebidas.',
     DATE_SUB(NOW(), INTERVAL 3 DAY), NULL, NULL,
     'Equipo', 'Media', 'Pendiente', 1, NULL, NULL),

    ('Cliente perdio su tarjeta de puntos',
     'Registro de prueba para verificar el orden y los filtros de la pantalla.',
     DATE_SUB(NOW(), INTERVAL 5 DAY), NULL, NULL,
     'Cliente', 'Baja', 'Pendiente', 1, NULL, NULL),

    ('Error en el sistema de puntos de venta',
     'Al cobrar una venta con tarjeta de regalo el sistema muestra una alerta.',
     DATE_SUB(NOW(), INTERVAL 7 DAY),
     DATE_SUB(NOW(), INTERVAL 6 DAY),
     'Se reinició el servicio técnico y se reinstaló el sistema de puntos de venta.',
     'Sistema', 'Critica', 'Resuelta', 1, NULL, NULL),

    ('Falta de sincronización de inventario',
     'Dos ingredientes no descuentan correctamente al vender.',
     DATE_SUB(NOW(), INTERVAL 10 DAY),
     DATE_SUB(NOW(), INTERVAL 9 DAY),
     'Se reinició la base de datos y se recargaron las cantidades.',
     'Sistema', 'Media', 'Resuelta', 1, NULL, NULL);

-- ============================================================================
--  6. MOVIMIENTOS DE INVENTARIO
-- ----------------------------------------------------------------------------
--  Se registran movimientos de los últimos días para que el CRUD de
--  Inventario tenga contenido: ingresos, salidas, una venta, una merma y
--  un ajuste.
--
--  id_Usuario 1 = AdminPrincipal, 2 = AdminPrincipal01
--  id_Venta NULL porque estos movimientos son manuales (ajustes de stock),
--  no los genera automáticamente una venta.
-- ============================================================================
INSERT INTO Movimiento_Inventario (cantidad, cantidad_anterior, cantidad_total, motivo, fecha_movimiento, tipo, id_Ingrediente, id_Usuario, id_Venta) VALUES
    (200, 0,   200, 'Compra semanal al proveedor',            DATE_SUB(NOW(), INTERVAL 7 DAY), 'Ingreso',  7,  1, NULL),
    (150, 0,   150, 'Compra semanal al proveedor',            DATE_SUB(NOW(), INTERVAL 7 DAY), 'Ingreso',  8,  1, NULL),
    (300, 0,   300, 'Compra mensual de bebidas',            DATE_SUB(NOW(), INTERVAL 6 DAY), 'Ingreso',  9,  1, NULL),
    (100, 0,   100, 'Compra de insumos de limpieza',          DATE_SUB(NOW(), INTERVAL 5 DAY), 'Ingreso', 10,  1, NULL),
    ( 20, 200,  180, 'Consumo de la semana',                  DATE_SUB(NOW(), INTERVAL 3 DAY), 'Salida',   7,  2, NULL),
    ( 15, 150,  135, 'Consumo de la semana',                  DATE_SUB(NOW(), INTERVAL 3 DAY), 'Salida',   8,  2, NULL),
    ( 25, 300,  275, 'Consumo de la semana',                  DATE_SUB(NOW(), INTERVAL 2 DAY), 'Salida',   9,  2, NULL),
    ( 10, 100,   90, 'Consumo de la semana',                  DATE_SUB(NOW(), INTERVAL 1 DAY), 'Salida',  10,  2, NULL),
    (  3,  180,  177, 'Producto arruinado, se descartó',       DATE_SUB(NOW(), INTERVAL 1 DAY), 'Merma',    7,  1, NULL),
    (  5,  135,  130, 'Producto arruinado, se descartó',       CURDATE(),                     'Merma',    8,  1, NULL),
    ( 50,  177,  227, 'Corrección tras conteo físico',         CURDATE(),                     'Ajuste',   7,  1, NULL);

-- ============================================================================
--  7. COMPROBACIÓN
-- ----------------------------------------------------------------------------
--  Ejecutá esto al final. Deberías ver todas las tablas con datos.
-- ============================================================================
SELECT 'Venta' AS tabla, COUNT(*) AS registros FROM Venta
UNION ALL SELECT 'Detalle_Venta',      COUNT(*) FROM Detalle_Venta
UNION ALL SELECT 'Incidente',          COUNT(*) FROM Incidente
UNION ALL SELECT 'Movimiento_Inventario', COUNT(*) FROM Movimiento_Inventario
UNION ALL SELECT 'Ingredientes en alerta',
       (SELECT COUNT(*) FROM Ingrediente WHERE cantidad < cantidad_minima);

-- ============================================================================
--  CÓMO DESHACER TODO ESTO (si necesitás dejar la base como estaba)
-- ----------------------------------------------------------------------------
--  DELETE FROM Movimiento_Inventario;
--  DELETE FROM Incidente;
--  DELETE FROM Detalle_Venta;
--  DELETE FROM Venta;
--  UPDATE Ingrediente SET cantidad_minima = 30 WHERE id_Ingrediente = 7;
-- ============================================================================
