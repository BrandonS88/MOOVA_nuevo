//------------------ P A N T A L L A   H I S T O R I A L ------------------
package gui.administrador;

import dao.AdminDAO;
import gui.componentes.*;
import gui.estilo.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PantallaHistorial extends VentanaAdministrador {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Panel principal
    private JPanel Pcontenido;

    //-------------- Título
    private JLabel Ltitulo;

    //-------------- Calendarios
    private Calendario Cdesde;
    private Calendario Chasta;

    //-------------- Combobox
    private CampoComboBox<String> Cestado;

    //-------------- Campos de texto
    private CampoTexto Tnit;
    private CampoTexto Tcajero;
    private CampoTexto TidVenta;

    //-------------- Botones
    private Boton Bbuscar;
    private Boton Blimpiar;
    private Boton Bdetalle;

    // Botón para cancelar una venta que quedó registrada por error.
    // Solo aplica a ventas "Sin Pagar": una venta ya Pagada o Cancelada
    // no se toca, porque afectaría el stock y los reportes ya cerrados.
    private Boton Bcancelar;

    // Botón para exportar el resultado a un archivo CSV, que Excel abre
    // directamente. Se exporta lo que se está viendo, incluidos los
    // filtros aplicados.
    private Boton Bexportar;

    //-------------- Paginación
    private Boton Banterior;
    private Boton Bsiguiente;
    private JLabel LinfoPagina;

    // Cantidad de ventas que se muestran por página.
    private static final int FILAS_POR_PAGINA = 10;

    // Número de página actual (empieza en 1).
    private int paginaActual = 1;

    // Total de páginas calculadas según el total de ventas encontradas.
    private int totalPaginas = 1;

    // Total de ventas que cumplen los filtros, sin importar la página.
    private int totalVentas = 0;

    // Indica si la carga en curso debe avisar cuando no encuentra
    // resultados. Solo se activa cuando el usuario pulsó "Buscar".
    private boolean avisarSinResultados = false;

    // Guarda el SQL y los parámetros del filtro vigente para poder
    // reconsultar al cambiar de página y al exportar.
    private String ultimoSql = "";
    private java.util.List<Object> ultimosParametros = new java.util.ArrayList<>();

    //-------------- Tabla
    private Tabla Tventas;

    //-------------- Modelo de la tabla
    // Se guarda aparte para poder llenarla y vaciarla desde buscarVentas()
    // sin tener que volver a crearla (y perder el ancho de columnas).
    private DefaultTableModel modeloVentas;

    //-------------- Acceso a datos
    private AdminDAO adminDAO;

    //------------------------ C O N S T R U C T O R ----------------
    public PantallaHistorial() {

        super("Historial de Ventas");

        // El historial se abre desde el panel del administrador, así que la
        // flecha vuelve a ese panel.
        setAccionRegreso(
                () -> abrir(PantallaDashboard.class, PantallaDashboard::new)
        );

        inicializarHistorial();

        setVisible(true);
    }

    //------------------------ I N I C I A L I Z A R ----------------
    private void inicializarHistorial() {

        //---------------------- PANEL CENTRAL
        Pcentral.setLayout(
                new BorderLayout()
        );

        //---------------------- CONTENIDO
        Pcontenido = new JPanel(
                new GridBagLayout()
        );

        Pcontenido.setOpaque(false);

        Pcontenido.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        20,
                        20,
                        20
                )
        );

        //---------------------- ACCESO A DATOS
        // Se usa AdminDAO porque esta pantalla no es un CRUD: hace
        // consultas con varios filtros opcionales que se arman según lo
        // que el usuario escribió, cosa que el DAO genérico no contempla.
        adminDAO = new AdminDAO();

        //---------------------- COMPONENTES
        inicializarTitulo();

        inicializarFiltrosFecha();

        inicializarEstado();

        inicializarFiltrosVenta();

        inicializarBotones();

        inicializarTabla();

        // Al abrir la pantalla se muestran las ventas del mes en curso, para
        // que el usuario no vea una tabla vacía sin saber por qué.
        // Los filtros por defecto se llenan en inicializarFiltrosFecha().
        cargarVentas();

        //---------------------- SCROLL
        JScrollPane scroll
                = new JScrollPane(
                        Pcontenido
                );

        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scroll.setOpaque(false);

        scroll.getViewport().setOpaque(false);

        scroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        Pcentral.add(
                scroll,
                BorderLayout.CENTER
        );
    }

    //------------------------ T Í T U L O ----------------
    private void inicializarTitulo() {

        Ltitulo = new JLabel(
                "Historial de Ventas"
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

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridwidth = 8;

        gbc.weightx = 1.0;
        gbc.weighty = 0.05;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                5,
                5,
                15,
                5
        );

        Pcontenido.add(
                Ltitulo,
                gbc
        );
    }

    //------------------------ F I L T R O S   F E C H A ----------------
    private void inicializarFiltrosFecha() {

        //---------------------- DESDE
        Cdesde = new Calendario();

        Cdesde.setTamaño(
                180,
                45
        );

        // Por defecto se busca desde el primer día del mes actual, que es el
        // rango que normalmente interesa al administrador.
        Cdesde.setFecha(
                LocalDate.now().withDayOfMonth(1)
        );

        agregarCampo(
                "Fecha",
                Cdesde,
                0,
                1,
                2
        );

        //---------------------- HASTA
        Chasta = new Calendario();

        Chasta.setTamaño(
                180,
                45
        );

        // Y hasta el día de hoy, para no mostrar ventas futuras.
        Chasta.setFecha(
                LocalDate.now()
        );

        agregarCampo(
                "a",
                Chasta,
                2,
                1,
                2
        );
    }

    //------------------------ E S T A D O ----------------
    private void inicializarEstado() {

        Cestado = new CampoComboBox<>();

        Cestado.setPreferredSize(
                new Dimension(
                        180,
                        45
                )
        );

        //---------------------- E N U M
        // La primera opción es "Todos" para que por defecto no se filtre
        // por estado. Los otros tres valores son los que permite el
        // ENUM de la columna "estado" de la tabla Venta.
        Cestado.addItem(
                "Todos"
        );

        Cestado.addItem(
                "Sin Pagar"
        );

        Cestado.addItem(
                "Cancelado"
        );

        Cestado.addItem(
                "Pagado"
        );

        agregarCampo(
                "Estado",
                Cestado,
                4,
                1,
                2
        );
    }

    //------------------------ F I L T R O S   V E N T A ----------------
    private void inicializarFiltrosVenta() {

        //---------------------- NIT
        Tnit = new CampoTexto();

        Tnit.setPreferredSize(
                new Dimension(
                        180,
                        45
                )
        );

        agregarCampo(
                "NIT",
                Tnit,
                0,
                2,
                2
        );

        //---------------------- CAJERO
        Tcajero = new CampoTexto();

        Tcajero.setPreferredSize(
                new Dimension(
                        180,
                        45
                )
        );

        agregarCampo(
                "Cajero",
                Tcajero,
                2,
                2,
                2
        );

        //---------------------- ID VENTA
        TidVenta = new CampoTexto();

        TidVenta.setPreferredSize(
                new Dimension(
                        180,
                        45
                )
        );

        agregarCampo(
                "ID_venta",
                TidVenta,
                4,
                2,
                2
        );
    }

    //------------------------ B O T O N E S ----------------
    private void inicializarBotones() {

        //---------------------- BUSCAR
        Bbuscar = new Boton(
                "Buscar"
        );

        Bbuscar.amarillo();

        Bbuscar.setPreferredSize(
                new Dimension(
                        140,
                        45
                )
        );

        //---------------------- LIMPIAR
        Blimpiar = new Boton(
                "Limpiar"
        );

        Blimpiar.amarillo();

        Blimpiar.setPreferredSize(
                new Dimension(
                        140,
                        45
                )
        );

        //---------------------- DETALLE
        Bdetalle = new Boton(
                "Ver detalle venta"
        );

        Bdetalle.verde();

        Bdetalle.setPreferredSize(
                new Dimension(
                        190,
                        45
                )
        );

        //---------------------- DESHABILITADO
        Bdetalle.setEnabled(
                false
        );

        //---------------------- C A N C E L A R
        // Permite anular una venta que se registró por error.
        Bcancelar = new Boton(
                "Cancelar venta"
        );

        Bcancelar.rojo();

        Bcancelar.setPreferredSize(
                new Dimension(
                        170,
                        45
                )
        );

        // Se habilita solo cuando la venta seleccionada está "Sin Pagar".
        Bcancelar.setEnabled(
                false
        );

        //---------------------- E X P O R T A R
        Bexportar = new Boton(
                "Exportar"
        );

        Bexportar.amarillo();

        Bexportar.setPreferredSize(
                new Dimension(
                        140,
                        45
                )
        );

        //---------------------- P A G I N A C I Ó N
        Banterior = new Boton(
                "Anterior"
        );

        Banterior.crema();

        Banterior.setPreferredSize(
                new Dimension(
                        120,
                        35
                )
        );

        Banterior.setEnabled(
                false
        );

        Bsiguiente = new Boton(
                "Siguiente"
        );

        Bsiguiente.crema();

        Bsiguiente.setPreferredSize(
                new Dimension(
                        120,
                        35
                )
        );

        Bsiguiente.setEnabled(
                false
        );

        LinfoPagina = new JLabel(
                "Página 1 de 1"
        );

        LinfoPagina.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.PEQUENO
                )
        );

        LinfoPagina.setForeground(
                Colores.CORINTO
        );

        LinfoPagina.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        //---------------------- BUSCAR
        agregarBoton(
                Bbuscar,
                6,
                1
        );

        //---------------------- LIMPIAR
        agregarBoton(
                Blimpiar,
                7,
                1
        );

        //---------------------- DETALLE
        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = 6;
        gbc.gridy = 2;

        gbc.gridwidth = 2;

        gbc.weightx = 1.0;
        gbc.weighty = 0.15;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                5,
                10,
                5,
                10
        );

        Pcontenido.add(
                Bdetalle,
                gbc
        );

        //---------------------- A C C I O N E S
        // Los tres botones se conectan aquí. Antes los botones existían en
        // pantalla pero no tenían ninguna acción programada, así que
        // pulsarlos no hacía nada.
        Bbuscar.addActionListener(
                e -> buscarVentas()
        );

        Blimpiar.addActionListener(
                e -> limpiarFiltros()
        );

        Bdetalle.addActionListener(
                e -> verDetalle()
        );

        //---------------------- C A N C E L A R
        gbc.gridx = 8;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        Pcontenido.add(
                Bcancelar,
                gbc
        );

        //---------------------- E X P O R T A R
        gbc.gridx = 10;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        Pcontenido.add(
                Bexportar,
                gbc
        );

        Bcancelar.addActionListener(
                e -> cancelarVenta()
        );

        Bexportar.addActionListener(
                e -> exportar()
        );

        // Los botones de página cambian de página usando el mismo filtro,
        // así que solo necesitan redibujar la tabla.
        Banterior.addActionListener(
                e -> cambiarPagina(
                        paginaActual - 1
                )
        );

        Bsiguiente.addActionListener(
                e -> cambiarPagina(
                        paginaActual + 1
                )
        );
    }

    //------------------------ T A B L A ----------------
    private void inicializarTabla() {

        //---------------------- MODELO
        // La fecha se muestra formateada (dd/MM/yyyy HH:mm) y no como
        // Timestamp crudo, que se vería como un bloque de números.
        // El nombre del cajero se resuelve con un JOIN a la tabla Usuario,
        // porque en la tabla Venta solo está el id del usuario.
        modeloVentas
                = new DefaultTableModel(
                        new Object[]{
                            "id_Venta",
                            "Fecha",
                            "Total",
                            "NIT",
                            "Método de pago",
                            "Estado",
                            "Cajero"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int fila,
                            int columna
                    ) {

                        return false;
                    }
                };

        //---------------------- TABLA
        Tventas = new Tabla();

        Tventas.setModel(
                modeloVentas
        );

        // Se selecciona la fila completa, no una celda suelta.
        Tventas.setCellSelectionEnabled(
                false
        );

        Tventas.setRowSelectionAllowed(
                true
        );

        Tventas.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        //---------------------- SELECCIÓN
        // Al seleccionar una venta se habilita el botón de detalle.
        // Se usa el listener de selección de la tabla en vez de un
        // MouseListener para que también funcione con las flechas del
        // teclado, no solo con el ratón.
        Tventas.getSelectionModel().addListSelectionListener(
                e -> {

                    if (!e.getValueIsAdjusting()) {

                        Bdetalle.setEnabled(
                                Tventas.getSelectedRow() >= 0
                        );

                        // El botón de cancelar solo tiene sentido si la venta
                        // elegida todavía no fue cobrada. Si ya está Pagada
                        // o Cancelada, se deja deshabilitado para no
                        // modificar un cobro ya cerrado.
                        Bcancelar.setEnabled(
                                ventaSinPagar()
                        );
                    }
                }
        );

        // Doble clic sobre la fila abre directamente el detalle, que es lo
        // que se espera de un historial.
        Tventas.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        if (e.getClickCount() == 2) {

                            verDetalle();
                        }
                    }
                }
        );

        //---------------------- SCROLL TABLA
        JScrollPane scrollTabla
                = new JScrollPane(
                        Tventas
                );

        scrollTabla.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollTabla.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        scrollTabla.setOpaque(false);

        scrollTabla.getViewport().setOpaque(false);

        scrollTabla.setBorder(
                BorderFactory.createEmptyBorder()
        );

        //---------------------- POSICIÓN
        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 3;

        gbc.gridwidth = 8;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.BOTH;

        gbc.insets = new Insets(
                15,
                5,
                5,
                5
        );

        Pcontenido.add(
                scrollTabla,
                gbc
        );

        //---------------------- P A G I N A C I Ó N
        // Se coloca debajo de la tabla con los botones Anterior / Siguiente
        // y un texto que indica en qué página se está y cuántas hay.
        JPanel Ppaginacion = new JPanel(
                new GridBagLayout()
        );

        Ppaginacion.setOpaque(false);

        GridBagConstraints gbcP = new GridBagConstraints();

        gbcP.gridy = 0;
        gbcP.weighty = 0.0;
        gbcP.anchor = GridBagConstraints.CENTER;
        gbcP.insets = new Insets(
                5,
                10,
                10,
                10
        );

        // "Anterior" a la izquierda
        gbcP.gridx = 0;

        Ppaginacion.add(
                Banterior,
                gbcP
        );

        // Texto informativo en el centro
        gbcP.gridx = 1;
        gbcP.weightx = 1.0;
        gbcP.fill = GridBagConstraints.HORIZONTAL;

        Ppaginacion.add(
                LinfoPagina,
                gbcP
        );

        // "Siguiente" a la derecha
        gbcP.gridx = 2;
        gbcP.weightx = 0.0;
        gbcP.fill = GridBagConstraints.NONE;

        Ppaginacion.add(
                Bsiguiente,
                gbcP
        );

        GridBagConstraints gbcPag = new GridBagConstraints();

        gbcPag.gridx = 0;
        gbcPag.gridy = 4;
        gbcPag.gridwidth = 12;
        gbcPag.weightx = 1.0;
        gbcPag.weighty = 0.05;
        gbcPag.anchor = GridBagConstraints.CENTER;
        gbcPag.fill = GridBagConstraints.HORIZONTAL;
        gbcPag.insets = new Insets(
                5,
                5,
                5,
                5
        );

        Pcontenido.add(
                Ppaginacion,
                gbcPag
        );
    }

    //------------------------------------------------------------------------
    //                       A C C I O N E S
    //------------------------------------------------------------------------

    /**
     * Arma la consulta con los filtros que escribió el usuario y llena la
     * tabla con el resultado.
     *
     * Cada filtro es opcional: solo se agrega a la consulta si tiene un
     * valor. Por eso la cláusula WHERE se construye por partes y al final
     * se le pega el ORDER BY, en vez de escribir un WHERE fijo. Si no
     * hubiera ningún filtro se usa "WHERE 1=1" para poder aunar todos los
     * WHERE con AND sin casos especiales.
     *
     * Todos los valores viajan como parámetros (? PreparedStatement), nunca
     * concatenados en el texto, para evitar inyección SQL.
     */
    private void buscarVentas() {
        buscarVentas(true);
    }

    /**
     * Carga la tabla aplicando los filtros de la pantalla.
     *
     * @param avisarSiVacio si es true y no hay resultados, muestra un
     *                      diálogo. Se pasa false en las cargas
     *                      automáticas (al abrir la pantalla, al limpiar
     *                      filtros o al cambiar de página) porque un
     *                      diálogo modal en esos casos taparía la pantalla
     *                      sin que el usuario haya pedido nada.
     */
    private void buscarVentas(boolean avisarSiVacio) {

        avisarSinResultados = avisarSiVacio;

        //---------------------- V A L I D A R   F E C H A S
        // La fecha "desde" no puede ser posterior a la fecha "hasta",
        // porque ese rango no devolvería nada y el usuario creería que
        // no hay ventas cuando en realidad el filtro está mal.
        if (Cdesde.getFecha() != null
                && Chasta.getFecha() != null
                && Cdesde.getFecha().isAfter(Chasta.getFecha())) {

            mensaje(
                    "La fecha inicial no puede ser posterior a la fecha final.",
                    "Rango de fechas inválido"
            );

            return;
        }

        //---------------------- V A L I D A R   I D   V E N T A
        String idTexto = TidVenta.getText().trim();

        if (!idTexto.isEmpty()) {

            try {
                Integer.parseInt(idTexto);
            } catch (NumberFormatException e) {

                mensaje(
                        "El ID de venta debe ser un número.",
                        "Dato inválido"
                );

                return;
            }
        }

        //---------------------- C O N S T R U I R   C O N S U L T A
        // Se separa el "FROM ... JOIN" del "WHERE" para poder reutilizar las
        // mismas condiciones en dos consultas: una que cuenta el total y
        // otra que trae los datos de la página actual. Así no hay que
        // reescribir los filtros dos veces ni arriesgarse a que se
        // desincronicen.
        String from = " FROM Venta v "
                + "JOIN Usuario u ON u.id_Usuario = v.id_Usuario ";

        // Empieza en "1=1" para poder unir todos los filtros con AND sin
        // tener que revisar si es el primero.
        StringBuilder where = new StringBuilder(
                " WHERE 1=1"
        );

        // Lista donde se guardan los valores de los filtros aplicados.
        java.util.List<Object> parametros = new java.util.ArrayList<>();

        //---------------------- F I L T R O   F E C H A   D E S D E
        if (Cdesde.getFecha() != null) {

            where.append(" AND v.fecha_realizacion >= ?");

            parametros.add(
                    Timestamp.valueOf(
                            Cdesde.getFecha().atStartOfDay()
                    )
            );
        }

        //---------------------- F I L T R O   F E C H A   H A S T A
        if (Chasta.getFecha() != null) {

            where.append(" AND v.fecha_realizacion < ?");

            // Se suma un día porque la fecha se guarda con hora. Si se
            // pusiera "menor o igual" al primer instante del día, se
            // perderían todas las ventas hechas después de medianoche.
            parametros.add(
                    Timestamp.valueOf(
                            Chasta.getFecha()
                                    .plusDays(1)
                                    .atStartOfDay()
                    )
            );
        }

        //---------------------- F I L T R O   E S T A D O
        String estado = String.valueOf(
                Cestado.getSelectedItem()
        );

        // "Todos" es la opción neutra: no filtra por estado.
        if (!"Todos".equals(estado)) {

            where.append(" AND v.estado = ?");

            parametros.add(estado);
        }

        //---------------------- F I L T R O   N I T
        if (!Tnit.getText().trim().isEmpty()) {

            where.append(" AND v.nit LIKE ?");

            // Se envuelve entre % para buscar coincidencias parciales:
            // si el usuario escribe "123" encuentra el NIT "2015123".
            parametros.add(
                    "%" + Tnit.getText().trim() + "%"
            );
        }

        //---------------------- F I L T R O   C A J E R O
        if (!Tcajero.getText().trim().isEmpty()) {

            where.append(" AND u.nombre_usuario LIKE ?");

            parametros.add(
                    "%" + Tcajero.getText().trim() + "%"
            );
        }

        //---------------------- F I L T R O   I D   V E N T A
        if (!idTexto.isEmpty()) {

            where.append(" AND v.id_Venta = ?");

            parametros.add(
                    Integer.parseInt(idTexto)
            );
        }

        // Las ventas más recientes primero: en un historial lo que se
        // consulta casi siempre es lo último que ocurrió.
        String orden = " ORDER BY v.fecha_realizacion DESC, v.id_Venta DESC";

        // Se guardan el SQL y los parámetros del filtro para poder
        // reconsultar al cambiar de página y también al exportar.
        ultimoSql = "SELECT v.id_Venta, v.fecha_realizacion, v.total, "
                + "v.nit, v.metodo_pago, v.estado, u.nombre_usuario "
                + from + where + orden;

        ultimosParametros = new java.util.ArrayList<>(parametros);

        //---------------------- C O N T A R   R E S U L T A D O S
        // Antes de traer los datos se cuenta cuántos hay en total, porque
        // un SELECT con LIMIT no informa cuántas filas hay en realidad.
        // Se reutiliza el mismo WHERE, así que el conteo siempre coincide
        // con lo que la tabla está mostrando.
        Object[] conteo = adminDAO.consultarUno(
                "SELECT COUNT(*)" + from + where,
                parametros.toArray()
        );

        totalVentas = conteo == null ? 0 : (int) datoNumero(conteo[0]);

        //---------------------- C A L C U L A R   P Á G I N A S
        totalPaginas = Math.max(
                1,
                (int) Math.ceil(
                        (double) totalVentas / FILAS_POR_PAGINA
                )
        );

        // Si al filtrar se quedó en una página que ya no existe (por
        // ejemplo, tras limpiar filtros), se vuelve a la primera.
        if (paginaActual > totalPaginas) {

            paginaActual = 1;
        }

        //---------------------- C O N S U L T A   C O N   P Á G I N A
        // LIMIT indica cuántas filas traer y OFFSET cuántas saltarse.
        // Ojo: los parámetros van en el orden en que aparecen los "?" del
        // SQL, y los del LIMIT se agregan al final.
        java.util.List<Object[]> filas = adminDAO.consultar(
                ultimoSql + " LIMIT ? OFFSET ?",
                parametrosPaginados(
                        parametros,
                        FILAS_POR_PAGINA,
                        (paginaActual - 1) * FILAS_POR_PAGINA
                )
        );

        //---------------------- L L E N A R   T A B L A
        modeloVentas.setRowCount(0);

        // Cuando no hay nada que mostrar se agrega una fila de texto
        // explicativo. Un JTable completamente vacío no dice si falló la
        // consulta, si el filtro estaba muy cerrado o si de verdad no hay
        // ventas registradas, y esa diferencia es importante.
        if (filas.isEmpty()) {

            modeloVentas.addRow(
                    new Object[]{
                        "-",
                        "No hay ventas que coincidan con los filtros",
                        "",
                        "",
                        "",
                        "",
                        ""
                    }
            );

            // Se ajusta el ancho para que el texto quepa en la columna de
            // fecha, que es la más ancha.
            if (Tventas.getColumnModel().getColumn(1).getWidth() < 380) {

                Tventas.getColumnModel().getColumn(1)
                        .setPreferredWidth(380);
            }
        }

        for (Object[] f : filas) {

            modeloVentas.addRow(
                    new Object[]{
                        f[0],
                        formatearFecha(f[1]),
                        String.format("%.2f", datoNumero(f[2])),
                        f[3],
                        f[4],
                        f[5],
                        f[6]
                    }
            );
        }

        // Al recargar la tabla la selección anterior se pierde, así que
        // los botones que dependen de una venta seleccionada se apagan.
        Bdetalle.setEnabled(false);
        Bcancelar.setEnabled(false);

        actualizarInfoPaginacion();

        // El aviso de "sin resultados" solo tiene sentido cuando el usuario
        // apretó Buscar: si aparece al abrir la pantalla o al cambiar de
        // página, el diálogo modal sería una molestia y taparía la pantalla
        // cada vez. Por eso se consulta la bandera avisarSinResultados.
        if (filas.isEmpty() && totalVentas == 0 && avisarSinResultados) {

            mensaje(
                    "No se encontraron ventas con los filtros indicados.",
                    "Sin resultados"
            );
        }
    }

    /**
     * Devuelve una copia de la lista de parámetros con dos valores extra
     * al final. Se usa para los parámetros del LIMIT y del OFFSET, que van
     * después de los del WHERE.
     */
    private Object[] parametrosPaginados(
            java.util.List<Object> lista,
            int limite,
            int desplazamiento
    ) {
        java.util.List<Object> copia
                = new java.util.ArrayList<>(lista);

        copia.add(limite);
        copia.add(desplazamiento);

        return copia.toArray();
    }

    /**
     * Actualiza el texto "Página X de Y" y habilita o deshabilita los
     * botones de página según la posición actual.
     */
    private void actualizarInfoPaginacion() {

        LinfoPagina.setText(
                "Página " + paginaActual + " de " + totalPaginas
                        + "   (" + totalVentas + " ventas)"
        );

        // No se puede ir antes de la primera página.
        Banterior.setEnabled(
                paginaActual > 1
        );

        // No se puede ir después de la última.
        Bsiguiente.setEnabled(
                paginaActual < totalPaginas
        );
    }

    /**
     * Cambia de página y vuelve a consultar usando el filtro que ya estaba
     * aplicado. Si el número de página no está dentro del rango, no hace
     * nada para no consultar con valores inválidos.
     */
    private void cambiarPagina(
            int pagina
    ) {

        if (pagina < 1 || pagina > totalPaginas) {

            return;
        }

        paginaActual = pagina;

        buscarVentas();
    }

    /**
     * Devuelve el id de la venta seleccionada en la tabla, o 0 si no hay
     * ninguna seleccionada. Ese id es el que se pasa a la pantalla de
     * detalle para que sepa qué venta mostrar.
     */
    private int ventaSeleccionada() {

        int fila = Tventas.getSelectedRow();

        if (fila < 0) {
            return 0;
        }

        Object valor = modeloVentas.getValueAt(fila, 0);

        if (valor instanceof Number) {
            return ((Number) valor).intValue();
        }

        try {
            return Integer.parseInt(valor.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Abre la pantalla de detalle de la venta seleccionada.
     * Si no hay ninguna venta seleccionada se avisa al usuario en lugar
     * de abrir una pantalla vacía.
     */
    private void verDetalle() {

        int id = ventaSeleccionada();

        if (id <= 0) {

            mensaje(
                    "Seleccione una venta de la tabla para ver su detalle.",
                    "Ninguna venta seleccionada"
            );

            return;
        }

        new DetalleVenta(id);
    }

    /**
     * Borra todos los filtros y vuelve a mostrar el mes en curso.
     * Es la forma de deshacer una búsqueda demasiado específica.
     */
    private void limpiarFiltros() {

        Cdesde.setFecha(
                LocalDate.now().withDayOfMonth(1)
        );

        Chasta.setFecha(
                LocalDate.now()
        );

        Cestado.setSelectedIndex(0);

        Tnit.setText("");
        Tcajero.setText("");
        TidVenta.setText("");

        // Se vuelve a la primera página: si se estaba en la 5 y al limpiar
        // solo quedan 2 páginas, quedarse en la 5 mostraría una tabla vacía.
        paginaActual = 1;

        cargarVentas();
    }

    /**
     * Vuelve a llenar la tabla usando los filtros que ya hay en pantalla.
     *
     * Se usa en las cargas automáticas (al abrir la pantalla, al limpiar
     * filtros y al cambiar de página). No avisa si no hay resultados,
     * porque en esos casos la tabla vacía ya es la respuesta y un diálogo
     * solo taparía la pantalla.
     */
    private void cargarVentas() {
        buscarVentas(false);
    }

    //---------------------- A L   M O S T R A R ----------------
    /**
     * Vuelve a consultar las ventas cada vez que se muestra la pantalla.
     *
     * La ventana se reutiliza en vez de construirse de nuevo, que es lo que
     * elimina el parpadeo al navegar. El precio es que la tabla se
     * quedaría con los datos de la última visita, así que al volver hay
     * que releerlos de la base de datos para que se vean las ventas nuevas.
     *
     * Se llama a cargarVentas() y no a buscarVentas() porque cargarVentas()
     * no abre el aviso de "no se encontraron resultados": si el usuario
     * llega a la pantalla y no hay ventas todavía, no merece un cuadro de
     * diálogo interrumpiendo.
     */
    @Override
    protected void alMostrar() {

        cargarVentas();
    }

    //---------------------- F O R M A T E A R   F E C H A
    /**
     * Convierte la fecha de la base de datos (un Timestamp) en un texto
     * legible. Si viniera nulo se muestra "-", que es más claro que
     * dejar la celda en blanco.
     */
    private String formatearFecha(Object valor) {

        if (valor == null) {
            return "-";
        }

        if (valor instanceof Timestamp) {

            return ((Timestamp) valor)
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
     * Convierte el total a número. MySQL devuelve los DECIMAL como
     * BigDecimal, y si se intentara aplicar formato directamente a otro
     * tipo fallaría, por eso se centraliza la conversión.
     */
    private double datoNumero(Object valor) {

        if (valor instanceof Number) {
            return ((Number) valor).doubleValue();
        }

        try {
            return Double.parseDouble(String.valueOf(valor));
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Indica si la venta seleccionada en la tabla sigue en estado
     * "Sin Pagar". Solo en ese caso se puede cancelar.
     */
    private boolean ventaSinPagar() {

        int fila = Tventas.getSelectedRow();

        if (fila < 0) {
            return false;
        }

        Object estado = modeloVentas.getValueAt(fila, 5);

        return estado != null
                && "Sin Pagar".equalsIgnoreCase(String.valueOf(estado));
    }

    //------------------------------------------------------------------------
    //                    C A N C E L A R   V E N T A
    //------------------------------------------------------------------------
    /**
     * Cancela la venta seleccionada, cambiando su estado a "Cancelado".
     *
     * Solo se permite si la venta está "Sin Pagar". Una venta ya cobrada
     * no se anula desde aquí, porque eso ya no es una cancelación sino una
     * devolución, y eso requiere devolver el inventario, que es otro
     * proceso. También se pide confirmación, porque la operación queda
     * registrada en la base de datos.
     */
    private void cancelarVenta() {

        int id = ventaSeleccionada();

        if (id <= 0) {

            mensaje(
                    "Seleccione una venta de la tabla.",
                    "Ninguna venta seleccionada"
            );

            return;
        }

        if (!ventaSinPagar()) {

            mensaje(
                    "Solo se pueden cancelar ventas que aún no han sido pagadas.\n"
                            + "Una venta pagada o cancelada no se modifica.",
                    "Venta no cancelable"
            );

            return;
        }

        // Se usa Dialogo para que los botones salgan como "Sí" y "No",
        // en lugar de "Yes" y "No" cuando la computadora está en inglés.
        // El aviso va con ícono de advertencia porque cancelar una venta
        // no se puede deshacer.
        int respuesta = Dialogo.confirmar(
                this,
                "Cancelar venta",
                "¿Desea cancelar la venta N° " + id + "?\n\n"
                        + "La venta quedará marcada como 'Cancelado'.\n"
                        + "Esta acción no se puede deshacer.",
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta != Dialogo.SI) {
            return;
        }

        // Se actualiza solo el estado de esa venta. La consulta va con
        // parámetro para no concatenar el id en el texto del SQL.
        boolean ok = adminDAO.ejecutar(
                "UPDATE Venta SET estado = ? WHERE id_Venta = ?",
                "Cancelado",
                id
        );

        if (!ok) {

            mensaje(
                    "No se pudo cancelar la venta. Revise la conexión.",
                    "Operación fallida"
            );

            return;
        }

        // Se recarga la página actual para que el cambio se vea.
        paginaActual = 1;

        buscarVentas();

        Dialogo.informacion(
                this,
                "Venta cancelada",
                "La venta N° " + id + " fue cancelada."
        );
    }

    //------------------------------------------------------------------------
    //                        E X P O R T A R
    //------------------------------------------------------------------------
    /**
     * Exporta las ventas que cumplen los filtros a un archivo CSV, que
     * Microsoft Excel y LibreOffice Calc abren directamente.
     *
     * Se exportan TODAS las ventas del filtro, no solo las de la página
     * visible: si el archivo tuviera solo 10 filas el reporte sería
     * inútil. Por eso se consulta otra vez sin el LIMIT.
     *
     * El formato CSV usa punto y coma como separador porque en muchos
     * países el Excel está configurado con separador ";" (si se usara la
     * coma, abriría todo en una sola columna).
     */
    private void exportar() {

        if (totalVentas == 0) {

            mensaje(
                    "No hay ventas para exportar con los filtros actuales.",
                    "Nada que exportar"
            );

            return;
        }

        // Se pide dónde guardar el archivo.
        JFileChooser selector = new JFileChooser();

        selector.setDialogTitle(
                "Guardar reporte de ventas"
        );

        selector.setSelectedFile(
                new java.io.File(
                        "reporte_ventas_"
                                + LocalDate.now() + ".csv"
                )
        );

        if (selector.showSaveDialog(this)
                != JFileChooser.APPROVE_OPTION) {

            return;
        }

        java.io.File archivo = selector.getSelectedFile();

        // Se consulta sin paginación para llevar todos los datos.
        java.util.List<Object[]> filas = adminDAO.consultar(
                ultimoSql,
                ultimosParametros.toArray()
        );

        // Se escribe el archivo como texto plano con acentos correctos.
        try (java.io.PrintWriter escritor = new java.io.PrintWriter(
                archivo,
                "UTF-8"
        )) {

            //---- E N C A B E Z A D O
            escritor.println("REPORTE DE VENTAS - MOOVA");

            escritor.println("Generado: "
                    + java.time.LocalDateTime.now()
                    .format(
                            java.time.format.DateTimeFormatter
                                    .ofPattern("dd/MM/yyyy HH:mm")
                    ));

            escritor.println("Filtros aplicados:");

            escritor.println("  Fecha desde: "
                    + (Cdesde.getFecha() == null ? "sin filtro"
                    : Cdesde.getFecha().format(
                            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

            escritor.println("  Fecha hasta: "
                    + (Chasta.getFecha() == null ? "sin filtro"
                    : Chasta.getFecha().format(
                            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

            escritor.println("  Estado: " + Cestado.getSelectedItem());

            escritor.println("  NIT: "
                    + (Tnit.getText().trim().isEmpty() ? "todos" : Tnit.getText().trim()));

            escritor.println("  Cajero: "
                    + (Tcajero.getText().trim().isEmpty() ? "todos" : Tcajero.getText().trim()));

            escritor.println("  Total de ventas: " + totalVentas);

            escritor.println();

            //---- E N C A B E Z A D O S   D E   L A   T A B L A
            escritor.println(
                    "ID Venta;Fecha;Total;NIT;Metodo de pago;Estado;Cajero"
            );

            //---- F I L A S
            for (Object[] f : filas) {

                escritor.println(
                        f[0] + ";"
                                + formatearFecha(f[1]) + ";"
                                + String.format("%.2f", datoNumero(f[2])) + ";"
                                + escapar(f[3]) + ";"
                                + escapar(f[4]) + ";"
                                + escapar(f[5]) + ";"
                                + escapar(f[6])
                );
            }

        } catch (java.io.IOException e) {

            mensaje(
                    "No se pudo escribir el archivo:\n" + e.getMessage(),
                    "Error al exportar"
            );

            return;
        }

        Dialogo.informacion(
                this,
                "Exportación exitosa",
                "Se exportaron " + filas.size() + " ventas a:\n"
                        + archivo.getAbsolutePath()
        );
    }

    /**
     * Envuelve entre comillas un valor de la base de datos si contiene
     * punto y coma, para que no se desarme la columna del CSV.
     * Ejemplo: un NIT con ";" dentro rompería la fila.
     */
    private String escapar(Object valor) {

        String texto = valor == null ? "" : String.valueOf(valor);

        if (texto.contains(";") || texto.contains("\"")) {

            return "\"" + texto.replace("\"", "\"\"") + "\"";
        }

        return texto;
    }

    /**
     * Muestra un mensaje de error o aviso con el estilo del proyecto.
     */
    private void mensaje(String texto, String titulo) {

        Dialogo.aviso(this, titulo, texto);
    }

    //------------------------ A G R E G A R   C A M P O ----------------
    private void agregarCampo(
            String texto,
            JComponent componente,
            int columna,
            int fila,
            int ancho
    ) {

        JPanel panel
                = new JPanel(
                        new GridBagLayout()
                );

        panel.setOpaque(false);

        //---------------------- LABEL
        JLabel label
                = new JLabel(
                        texto
                );

        label.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.SUBTITULO
                )
        );

        label.setForeground(
                Colores.CORINTO
        );

        label.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        GridBagConstraints gbcLabel
                = new GridBagConstraints();

        gbcLabel.gridx = 0;
        gbcLabel.gridy = 0;

        gbcLabel.weightx = 0.35;
        gbcLabel.weighty = 1.0;

        gbcLabel.anchor =
                GridBagConstraints.CENTER;

        gbcLabel.fill =
                GridBagConstraints.HORIZONTAL;

        gbcLabel.insets = new Insets(
                5,
                5,
                5,
                10
        );

        panel.add(
                label,
                gbcLabel
        );

        //---------------------- COMPONENTE
        GridBagConstraints gbcComponente
                = new GridBagConstraints();

        gbcComponente.gridx = 1;
        gbcComponente.gridy = 0;

        gbcComponente.weightx = 0.65;
        gbcComponente.weighty = 1.0;

        gbcComponente.anchor =
                GridBagConstraints.CENTER;

        gbcComponente.fill =
                GridBagConstraints.HORIZONTAL;

        gbcComponente.insets = new Insets(
                5,
                5,
                5,
                5
        );

        panel.add(
                componente,
                gbcComponente
        );

        //---------------------- POSICIÓN
        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = columna;
        gbc.gridy = fila;

        gbc.gridwidth = ancho;

        gbc.weightx = 1.0;
        gbc.weighty = 0.15;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                5,
                5,
                5,
                5
        );

        Pcontenido.add(
                panel,
                gbc
        );
    }

    //------------------------ A G R E G A R   B O T Ó N ----------------
    private void agregarBoton(
            Boton boton,
            int columna,
            int fila
    ) {

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = columna;
        gbc.gridy = fila;

        gbc.weightx = 1.0;
        gbc.weighty = 0.15;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                5,
                10,
                5,
                10
        );

        Pcontenido.add(
                boton,
                gbc
        );
    }
}
