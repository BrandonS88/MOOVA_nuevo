//------------------ D E T A L L E   V E N T A ------------------
package gui.administrador;

import dao.AdminDAO;
import gui.componentes.*;
import gui.estilo.*;
import java.awt.*;
import javax.swing.*;
import logica.Reloj;

public class DetalleVenta extends VentanaAdministrador {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Título
    private JLabel Ltitulo;

    //-------------- Factura
    private PanelFactura panelFactura;
    private JScrollPane scrollFactura;

    //-------------- Carrusel
    private CarruselImagenes carrusel;

    //-------------- Id de la venta a mostrar
    // 0 significa que no se recibió ninguna venta (constructor vacío).
    private int idVenta = 0;

    //-------------- Acceso a datos
    private AdminDAO adminDAO;

    //------------------------ C O N S T R U C T O R ----------------

    /**
     * Se conservan este constructor sin argumentos para no romper el
     * código que lo invocaba, pero muestra un aviso porque sin un id de
     * venta no hay nada real que mostrar.
     */
    public DetalleVenta() {

        super("Detalle de la Venta");

        // Sin id no hay venta que ver, pero la pantalla igual necesita un
        // destino de regreso coherente con el módulo de historial.
        configurarRegreso();

        inicializarDetalle();

        setVisible(true);
    }

    /**
     * Constructor principal: recibe el id de la venta que se quiere ver.
     *
     * Antes la pantalla no recibía ningún id y siempre mostraba datos de
     * ejemplo, así que cualquier venta se veía igual. Con este
     * constructor la pantalla se llena con la venta que el administrador
     * seleccionó en el historial.
     *
     * @param idVenta id de la venta a mostrar.
     */
    public DetalleVenta(int idVenta) {

        super("Detalle de la Venta");

        this.idVenta = idVenta;

        // El detalle de una venta siempre viene del historial, así que la
        // flecha vuelve al historial y no al panel del administrador.
        configurarRegreso();

        inicializarDetalle();

        cargarVenta();

        setVisible(true);
    }

    //------------------------------------------------------------------------
    //                              R E G R E S A R
    //------------------------------------------------------------------------
    /**
     * Define el destino de la flecha de regresar.
     *
     * No se vuelve directo al panel del administrador, porque entonces se
     * perdería el contexto: el administrador_filters está viendo una venta
     * puntual y esperaría volver al listado para seguir con otras. Por eso
     * el destino es el historial de ventas.
     *
     * Se separa en un método aparte porque los dos constructores de esta
     * clase necesitan el mismo comportamiento y no conviene duplicarlo.
     */
    private void configurarRegreso() {

        setAccionRegreso(
                () -> abrir(PantallaHistorial.class, PantallaHistorial::new)
        );
    }

    //------------------------ I N I C I A L I Z A R ----------------
    private void inicializarDetalle() {

        //---------------------- PANEL CENTRAL
        Pcentral.setLayout(
                new BorderLayout()
        );

        //---------------------- TÍTULO
        inicializarTitulo();

        //---------------------- CONTENIDO
        inicializarContenido();
    }

    //------------------------ T Í T U L O ----------------
    private void inicializarTitulo() {

        Ltitulo = new JLabel(
                "Detalle de la Venta"
        );

        Ltitulo.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.TITULO
                )
        );

        Ltitulo.setForeground(
                Colores.CORINTO
        );

        Ltitulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        JPanel Ptitulo
                = new JPanel(
                        new GridBagLayout()
                );

        Ptitulo.setOpaque(false);

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        Ptitulo.add(
                Ltitulo,
                gbc
        );

        Pcentral.add(
                Ptitulo,
                BorderLayout.NORTH
        );
    }

    //------------------------ C O N T E N I D O ----------------
    private void inicializarContenido() {

        JPanel Pcontenido
                = new JPanel(
                        new GridBagLayout()
                );

        Pcontenido.setOpaque(false);

        //---------------------- FACTURA
        inicializarFactura();

        //---------------------- CARRUSEL
        inicializarCarrusel();

        //---------------------- COLUMNA FACTURA
        GridBagConstraints gbcFactura
                = new GridBagConstraints();

        gbcFactura.gridx = 0;
        gbcFactura.gridy = 0;

        gbcFactura.weightx = 0.65;
        gbcFactura.weighty = 1.0;

        gbcFactura.anchor =
                GridBagConstraints.CENTER;

        gbcFactura.fill =
                GridBagConstraints.VERTICAL;

        gbcFactura.insets = new Insets(
                10,
                20,
                20,
                10
        );

        Pcontenido.add(
                scrollFactura,
                gbcFactura
        );

        //---------------------- COLUMNA CARRUSEL
        GridBagConstraints gbcCarrusel
                = new GridBagConstraints();

        gbcCarrusel.gridx = 1;
        gbcCarrusel.gridy = 0;

        gbcCarrusel.weightx = 0.35;
        gbcCarrusel.weighty = 1.0;

        gbcCarrusel.anchor =
                GridBagConstraints.CENTER;

        gbcCarrusel.fill =
                GridBagConstraints.BOTH;

        gbcCarrusel.insets = new Insets(
                10,
                10,
                20,
                20
        );

        Pcontenido.add(
                carrusel,
                gbcCarrusel
        );

        //---------------------- AGREGAR
        Pcentral.add(
                Pcontenido,
                BorderLayout.CENTER
        );
    }

    //------------------------ P A N E L   F A C T U R A ----------------
    private void inicializarFactura() {

        panelFactura
                = new PanelFactura();

        //---------------------- CONTENIDO INICIAL
        // La factura real se carga después, en cargarVenta(), porque ahí ya
        // se sabe el id de la venta. Si no se recibió id, se avisa.
        if (idVenta <= 0) {

            panelFactura.mostrarAvisoSinVenta();

        } else {

            panelFactura.mostrarFacturaEjemplo();
        }

        //---------------------- SCROLL
        scrollFactura
                = new JScrollPane(
                        panelFactura
                );

        scrollFactura.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollFactura.setOpaque(false);

        scrollFactura.getViewport().setOpaque(false);

        scrollFactura.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollFactura.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
    }

    //------------------------ C A R R U S E L ----------------
    private void inicializarCarrusel() {

        String[] rutasCarrusel
                = obtenerImagenesCarrusel();

        carrusel
                = new CarruselImagenes(
                        rutasCarrusel,
                        4000
                );
    }

    //---------------------- I M A G E N E S   C A R R U S E L ----------------
    private String[] obtenerImagenesCarrusel() {

        if (Reloj.esManana()) {

            return new String[]{
                "/utiles/imagenes/fotografias/Spreen_PantallaPrincipal.png",
                "/utiles/imagenes/fotografias/Auron_PantallaPrincipal.png",
                "/utiles/imagenes/fotografias/Mike-Timba_PantallaPrincipal.png"
            };

        } else {

            return new String[]{
                "/utiles/imagenes/fotografias/Ran_PantallaPrincipal.png",
                "/utiles/imagenes/fotografias/Artux_PantallaPrincipal.png",
                "/utiles/imagenes/fotografias/Willy-Rubius_PantallaPrincipal.png"
            };
        }
    }

    //------------------------------------------------------------------------
    //                       C A R G A R   V E N T A
    //------------------------------------------------------------------------

    /**
     * Busca la venta en la base de datos y la muestra en la factura.
     *
     * Se hacen dos consultas: una para la cabecera de la venta (fecha, NIT,
     * método de pago, estado, cajero y total) y otra para sus líneas de
     * detalle. Se piden por separado porque son datos de tablas distintas
     * (Venta, Detalle_Venta, Producto y Combo).
     */
    private void cargarVenta() {

        adminDAO = new AdminDAO();

        //---------------------- C A B E C E R A
        // Se usa LEFT JOIN con Producto y Combo porque una línea de venta
        // puede ser de un producto o de un combo, nunca de los dos. Con
        // INNER JOIN se perderían las líneas que no tuvieran coincidencia.
        Object[] venta = adminDAO.consultarUno(
                "SELECT v.id_Venta, v.fecha_realizacion, v.total, v.nit, "
                        + "v.metodo_pago, v.estado, u.nombre_usuario "
                        + "FROM Venta v "
                        + "JOIN Usuario u ON u.id_Usuario = v.id_Usuario "
                        + "WHERE v.id_Venta = ?",
                idVenta
        );

        if (venta == null) {

            panelFactura.mostrarAvisoSinVenta();

            Dialogo.aviso(
                    this,
                    "Venta no encontrada",
                    "No se encontró la venta N° " + idVenta + "."
            );

            return;
        }

        //---------------------- D E T A L L E
        java.util.List<Object[]> detalles = adminDAO.consultar(
                "SELECT COALESCE(p.nombre, c.nombre), d.cantidad, "
                        + "d.precio_unitario, d.subtotal, d.impuesto, "
                        + "d.monto_descuento "
                        + "FROM Detalle_Venta d "
                        + "LEFT JOIN Producto p ON p.id_Producto = d.id_Producto "
                        + "LEFT JOIN Combo c ON c.id_Combo = d.id_Combo "
                        + "WHERE d.id_Venta = ? "
                        + "ORDER BY d.id_DetalleVenta",
                idVenta
        );

        //---------------------- M O S T R A R
        panelFactura.mostrarFacturaVenta(
                idVenta,
                formatearFecha(venta[1]),
                venta[3] == null ? "C/F" : String.valueOf(venta[3]),
                venta[4] == null ? "-" : String.valueOf(venta[4]),
                venta[5] == null ? "-" : String.valueOf(venta[5]),
                venta[6] == null ? "" : String.valueOf(venta[6]),
                detalles,
                numero(venta[2])
        );

        // El título ahora menciona qué venta se está viendo, para que
        // el administrador no confunda dos ventanas de detalle abiertas.
        Ltitulo.setText("Detalle de la Venta N° " + idVenta);
    }

    /**
     * Formatea la fecha de la venta. MySQL la devuelve como Timestamp.
     */
    private String formatearFecha(Object valor) {

        if (valor == null) {
            return "-";
        }

        if (valor instanceof java.sql.Timestamp) {

            return ((java.sql.Timestamp) valor)
                    .toLocalDateTime()
                    .format(
                            java.time.format.DateTimeFormatter
                                    .ofPattern("dd/MM/yyyy HH:mm")
                    );
        }

        if (valor instanceof java.time.LocalDateTime) {

            return ((java.time.LocalDateTime) valor)
                    .format(
                            java.time.format.DateTimeFormatter
                                    .ofPattern("dd/MM/yyyy HH:mm")
                    );
        }

        return String.valueOf(valor);
    }

    /**
     * Convierte un valor numérico de la base de datos a double.
     * MySQL devuelve los DECIMAL como BigDecimal, que no es un int ni un
     * double, por eso se centraliza la conversión.
     */
    private double numero(Object valor) {

        if (valor instanceof Number) {
            return ((Number) valor).doubleValue();
        }

        try {
            return Double.parseDouble(String.valueOf(valor));
        } catch (Exception e) {
            return 0.0;
        }
    }

    //---------------------- G E T T E R
    public PanelFactura getPanelFactura() {

        return panelFactura;
    }
}
