//---------------------- V E N T A N A   C R U D ----------------------
package gui.administrador;

import dao.AdminDAO;
import gui.componentes.*;
import gui.estilo.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

public class VentanaCRUD extends VentanaAdministrador {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Panel principal
    protected JPanel Pcontenido;

    //-------------- Panel de imagen
    private JPanel Pimagen;

    //-------------- Panel derecho
    protected JPanel Pderecha;

    //-------------- Título
    protected JLabel Ltitulo;

    //-------------- Componentes de ID
    protected JLabel Lid;
    protected CampoTexto Tid;

    //-------------- Componentes de imagen
    protected JLabel Limagen;
    protected CargarImagen cargarImagen;

    //-------------- Tabla
    protected Tabla tabla;
    protected DefaultTableModel modeloTabla;

    //-------------- Botones
    protected Boton Bbuscar;
    protected Boton Bcrear;
    protected Boton Bactualizar;
    protected Boton Bdesactivar;

    // Texto actual del botón de estado. Se guarda aparte para poder
    // alternar entre "Desactivar" y "Activar" según el registro que haya
    // seleccionado el usuario en la tabla.
    private String textoBotonEstado = "Desactivar";

    // Indica si el campo del ID debe permanecer bloqueado.
    private boolean idBloqueado = false;

    //-------------- Configuración
    private boolean mostrarDesactivar = true;
    private boolean mostrarBotones = true;

    //-------------- Filas
    protected int filaSiguiente = 1;
    protected int filaDerecha = 0;

    //------------------------ C O N S T R U C T O R ----------------
    public VentanaCRUD(
            String titulo
    ) {

        super(
                titulo
        );

        // Se define a qué menú pertenece este CRUD para que la flecha de
        // regresar suba al menú correcto. Como los CRUDs no guardan de
        // dónde se entraron, se deduce por el tipo de pantalla. Se hace
        // aquí, en el constructor, porque en este punto "this" ya es el
        // objeto del CRUD concreto y se puede preguntar con instanceof.
        configurarRegreso();

        //---------------------- PANEL CONTENIDO
        Pcontenido = new JPanel(
                new GridBagLayout()
        );

        Pcontenido.setOpaque(false);

        //---------------------- TÍTULO
        Ltitulo = new JLabel(
                titulo
        );

        Ltitulo.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.SUBTITULO
                )
        );

        Ltitulo.setForeground(
                Colores.CORINTO
        );

        //---------------------- RESTRICCIONES TÍTULO
        GridBagConstraints gbcTitulo = new GridBagConstraints();

        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;

        gbcTitulo.gridwidth = 12;

        gbcTitulo.weightx = 0.0;
        gbcTitulo.weighty = 0.0;

        gbcTitulo.anchor = GridBagConstraints.CENTER;
        gbcTitulo.fill = GridBagConstraints.NONE;

        gbcTitulo.insets = new Insets(
                10,
                5,
                10,
                5
        );

        //---------------------- AGREGAR TÍTULO
        Pcontenido.add(
                Ltitulo,
                gbcTitulo
        );

        //---------------------- SCROLL
        JScrollPane scroll = new JScrollPane(
                Pcontenido
        );

        scroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scroll.setOpaque(false);

        scroll.getViewport().setOpaque(false);

        //---------------------- SCROLL VERTICAL
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        //---------------------- SIN SCROLL HORIZONTAL
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scroll.getVerticalScrollBar().setUnitIncrement(
                20
        );

        //---------------------- AGREGAR AL PANEL CENTRAL
        getPcentral().setLayout(
                new BorderLayout()
        );

        getPcentral().add(
                scroll,
                BorderLayout.CENTER
        );
    }

    //------------------------------------------------------------------------
    //                              I D
    //------------------------------------------------------------------------
    //---------------------- S E T T E A R   T E X T O   I D ----------------
    protected void setTextoId(
            String texto
    ) {
        // El campo del ID no se edita a mano: su valor lo calcula el
        // sistema con prepararId() cuando se va a crear un registro, o
        // se copia del registro elegido en la tabla. Si el usuario pudiera
        // escribirlo podría crear o sobrescribir registros ajenos.
        // Por eso se bloquea aquí, al crear el campo, y no solo después.
        idBloqueado = true;

        //---------------------- PANEL ID
        JPanel Pid = new JPanel(
                new GridBagLayout()
        );

        Pid.setOpaque(false);

        //---------------------- ETIQUETA
        Lid = new JLabel(
                texto + ":"
        );

        Lid.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.GRANDE
                )
        );

        Lid.setForeground(
                Colores.CORINTO
        );

        //---------------------- CAMPO
        Tid = new CampoTexto();

        Tid.setPreferredSize(
                new Dimension(
                        350,
                        45
                )
        );

        //---------------------- RESTRICCIONES
        GridBagConstraints gbc = new GridBagConstraints();

        //---------------------- ETIQUETA
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;

        gbc.insets = new Insets(
                5,
                5,
                5,
                10
        );

        Pid.add(
                Lid,
                gbc
        );

        //---------------------- CAMPO
        gbc.gridx = 1;

        gbc.insets = new Insets(
                5,
                0,
                5,
                0
        );

        Pid.add(
                Tid,
                gbc
        );

        //---------------------- AGREGAR PANEL ID
        GridBagConstraints gbcId = new GridBagConstraints();

        gbcId.gridx = 0;
        gbcId.gridy = filaSiguiente;

        gbcId.gridwidth = 1;

        gbcId.weightx = 0.0;
        gbcId.weighty = 0.0;

        gbcId.anchor = GridBagConstraints.WEST;
        gbcId.fill = GridBagConstraints.NONE;

        gbcId.insets = new Insets(
                5,
                5,
                5,
                5
        );

        Pcontenido.add(
                Pid,
                gbcId
        );

        //---------------------- SIGUIENTE FILA
        filaSiguiente++;
    }

    //------------------------------------------------------------------------
    //                            I M A G E N
    //------------------------------------------------------------------------
    //---------------------- M O S T R A R   I M A G E N ----------------
    protected void mostrarImagen(
            boolean mostrar
    ) {

        if (!mostrar) {
            return;
        }

        //---------------------- PANEL DE IMAGEN
        Pimagen = new JPanel(
                new GridBagLayout()
        );

        Pimagen.setOpaque(false);

        //---------------------- ETIQUETA
        Limagen = new JLabel(
                "Imagen:"
        );

        Limagen.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.GRANDE
                )
        );

        Limagen.setForeground(
                Colores.CORINTO
        );

        //---------------------- CARGADOR DE IMAGEN
        cargarImagen = new CargarImagen();

        cargarImagen.setTamaño(
                300,
                300
        );

        //---------------------- PANEL DERECHO
        Pderecha = new JPanel(
                new GridBagLayout()
        );

        Pderecha.setOpaque(false);

        //---------------------- ETIQUETA IMAGEN
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 0.0;
        gbc.weighty = 1.0;

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;

        gbc.insets = new Insets(
                5,
                5,
                5,
                15
        );

        Pimagen.add(
                Limagen,
                gbc
        );

        //---------------------- CARGADOR
        gbc.gridx = 1;

        gbc.insets = new Insets(
                5,
                0,
                5,
                25
        );

        Pimagen.add(
                cargarImagen,
                gbc
        );

        //---------------------- PANEL DERECHO
        gbc.gridx = 2;

        gbc.weightx = 1.0;

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                5,
                5,
                5,
                5
        );

        Pimagen.add(
                Pderecha,
                gbc
        );

        //---------------------- AGREGAR PANEL DE IMAGEN
        GridBagConstraints gbcImagen = new GridBagConstraints();

        gbcImagen.gridx = 0;
        gbcImagen.gridy = filaSiguiente;

        gbcImagen.gridwidth = 12;

        gbcImagen.weightx = 1.0;
        gbcImagen.weighty = 0.0;

        gbcImagen.anchor = GridBagConstraints.WEST;
        gbcImagen.fill = GridBagConstraints.HORIZONTAL;

        gbcImagen.insets = new Insets(
                5,
                5,
                10,
                5
        );

        Pcontenido.add(
                Pimagen,
                gbcImagen
        );

        //---------------------- SIGUIENTE FILA
        filaSiguiente++;
    }

    //------------------------------------------------------------------------
    //                              R E G R E S A R
    //------------------------------------------------------------------------
    /**
     * Decide a qué pantalla debe volver la flecha de regresar.
     *
     * Los CRUDs no guardan el lugar desde el que se abrieron, así que se
     * deduce según el tipo de CRUD:
     *
     *   - Los 4 CRUDs de combos (Combo, GrupoCombo, OpcionCombo y
     *     ProductoCombo) vienen del submenú de combos.
     *   - Los 2 CRUDs de personalización vienen del submenú de
     *     personalización.
     *   - Todos los demás vienen del menú general de administración.
     *
     * MovimientoInventarioCRUD es un caso aparte: no se abre desde el menú
     * de administración sino desde el CRUD de ingredientes (al pulsar
     * "Actualizar Stock"), por eso vuelve allí y no al menú.
     */
    private void configurarRegreso() {

        if (this instanceof MovimientoInventarioCRUD) {

            // Se abre desde IngredienteCRUD.
            setAccionRegreso(
                    () -> abrir(IngredienteCRUD.class, IngredienteCRUD::new)
            );

            return;
        }

        if (this instanceof ComboCRUD
                || this instanceof GrupoComboCRUD
                || this instanceof OpcionComboCRUD
                || this instanceof ProductoComboCRUD) {

            setAccionRegreso(
                    () -> abrir(AdministracionDatosCombo.class, AdministracionDatosCombo::new)
            );

            return;
        }

        if (this instanceof GrupoPersonalizacionCRUD
                || this instanceof OpcionPersonalizacionCRUD) {

            setAccionRegreso(
                    () -> abrir(AdministracionDatosPersonalizacion.class, AdministracionDatosPersonalizacion::new)
            );

            return;
        }

        // El resto de CRUDs se abre desde Administración de Datos.
        setAccionRegreso(
                () -> abrir(AdministracionDatos.class, AdministracionDatos::new)
        );
    }

    //------------------------------------------------------------------------
    //                     C A M P O S   N O R M A L E S
    //------------------------------------------------------------------------
    //---------------------- A G R E G A R   C A M P O ----------------
    protected void agregarCampo(
            String texto,
            JComponent componente,
            int x,
            int y,
            int ancho
    ) {

        //---------------------- ETIQUETA
        JLabel etiqueta = new JLabel(
                texto + ":"
        );

        etiqueta.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.GRANDE
                )
        );

        etiqueta.setForeground(
                Colores.CORINTO
        );

        //---------------------- PANEL DEL CAMPO
        JPanel Pcampo = new JPanel(
                new GridBagLayout()
        );

        Pcampo.setOpaque(false);

        //---------------------- RESTRICCIONES INTERNAS
        GridBagConstraints gbcCampo = new GridBagConstraints();

        //---------------------- ETIQUETA
        gbcCampo.gridx = 0;
        gbcCampo.gridy = 0;

        gbcCampo.weightx = 0.0;
        gbcCampo.weighty = 0.0;

        gbcCampo.anchor = GridBagConstraints.WEST;
        gbcCampo.fill = GridBagConstraints.NONE;

        gbcCampo.insets = new Insets(
                5,
                5,
                5,
                10
        );

        Pcampo.add(
                etiqueta,
                gbcCampo
        );

        //---------------------- COMPONENTE
        gbcCampo.gridx = 1;

        gbcCampo.weightx = 1.0;

        gbcCampo.fill = GridBagConstraints.HORIZONTAL;

        gbcCampo.insets = new Insets(
                5,
                0,
                5,
                5
        );

        Pcampo.add(
                componente,
                gbcCampo
        );

        //---------------------- RESTRICCIONES PRINCIPALES
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = x;
        gbc.gridy = y;

        gbc.gridwidth = ancho;

        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                5,
                5,
                5,
                5
        );

        //---------------------- AGREGAR
        Pcontenido.add(
                Pcampo,
                gbc
        );

        //---------------------- ACTUALIZAR FILA
        filaSiguiente = Math.max(
                filaSiguiente,
                y + 1
        );
    }

    //------------------------------------------------------------------------
    //                 C A M P O S   A   L A   D E R E C H A
    //------------------------------------------------------------------------
    //---------------------- A G R E G A R   C A M P O   D E R E C H A ----------------
    protected void agregarCampoDerecha(
            String texto,
            JComponent componente
    ) {

        //---------------------- ETIQUETA
        JLabel etiqueta = new JLabel(
                texto + ":"
        );

        etiqueta.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.GRANDE
                )
        );

        etiqueta.setForeground(
                Colores.CORINTO
        );

        //---------------------- PANEL DEL CAMPO
        JPanel Pcampo = new JPanel(
                new GridBagLayout()
        );

        Pcampo.setOpaque(false);

        //---------------------- RESTRICCIONES
        GridBagConstraints gbc = new GridBagConstraints();

        //---------------------- ETIQUETA
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;

        gbc.insets = new Insets(
                5,
                5,
                5,
                10
        );

        Pcampo.add(
                etiqueta,
                gbc
        );

        //---------------------- COMPONENTE
        gbc.gridx = 1;

        gbc.insets = new Insets(
                5,
                0,
                5,
                5
        );

        Pcampo.add(
                componente,
                gbc
        );

        //---------------------- AGREGAR AL PANEL DERECHO
        GridBagConstraints gbcCampo = new GridBagConstraints();

        gbcCampo.gridx = 0;
        gbcCampo.gridy = filaDerecha;

        gbcCampo.weightx = 1.0;
        gbcCampo.weighty = 0.0;

        gbcCampo.anchor = GridBagConstraints.WEST;
        gbcCampo.fill = GridBagConstraints.HORIZONTAL;

        gbcCampo.insets = new Insets(
                5,
                5,
                5,
                5
        );

        Pderecha.add(
                Pcampo,
                gbcCampo
        );

        //---------------------- SIGUIENTE FILA
        filaDerecha++;
    }

    //------------------------------------------------------------------------
    //           C A M P O S   D E R E C H A   H O R I Z O N T A L
    //------------------------------------------------------------------------
    //---------------------- A G R E G A R   C A M P O   H O R I Z O N T A L ----------------
    protected void agregarCampoDerechaHorizontal(
            String texto,
            JComponent componente,
            int fila,
            int columna
    ) {

        //---------------------- ETIQUETA
        JLabel etiqueta = new JLabel(
                texto + ":"
        );

        etiqueta.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.GRANDE
                )
        );

        etiqueta.setForeground(
                Colores.CORINTO
        );

        //---------------------- POSICIÓN
        int x = columna * 2;

        //---------------------- RESTRICCIONES
        GridBagConstraints gbc = new GridBagConstraints();

        //---------------------- ETIQUETA
        gbc.gridx = x;
        gbc.gridy = fila;

        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;

        gbc.insets = new Insets(
                5,
                5,
                5,
                10
        );

        Pderecha.add(
                etiqueta,
                gbc
        );

        //---------------------- COMPONENTE
        gbc.gridx = x + 1;

        gbc.insets = new Insets(
                5,
                0,
                5,
                15
        );

        Pderecha.add(
                componente,
                gbc
        );
    }

    //------------------------------------------------------------------------
    //                  B O T O N E S
    //------------------------------------------------------------------------
    //---------------------- M O S T R A R   B O T O N E S ----------------
    protected void mostrarBotones(
            boolean mostrar
    ) {

        mostrarBotones = mostrar;
    }

    //---------------------- M O S T R A R   B O T Ó N   D E S A C T I V A R ----------------
    protected void mostrarBotonDesactivar(
            boolean mostrar
    ) {

        mostrarDesactivar = mostrar;
    }

    //------------------------------------------------------------------------
    //                              T A B L A
    //------------------------------------------------------------------------
    //---------------------- F I N A L I Z A R   C R U D ----------------
    protected void finalizarCRUD(
            String[] columnas
    ) {

        //---------------------- MODELO
        modeloTabla = new DefaultTableModel(
                columnas,
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {

                return false;
            }
        };

        //---------------------- TABLA
        tabla = new Tabla();

        tabla.setModel(
                modeloTabla
        );

        tabla.setCellSelectionEnabled(
                false
        );

        tabla.setRowSelectionAllowed(
                true
        );

        tabla.getSelectionModel().setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        //---------------------- SELECCIÓN
        tabla.getSelectionModel().addListSelectionListener(
                (ListSelectionEvent e) -> {

                    if (!e.getValueIsAdjusting()) {

                        tablaSeleccionada();
                    }
                }
        );

        //---------------------- SCROLL TABLA
        JScrollPane scrollTabla = new JScrollPane(
                tabla
        );

        scrollTabla.setPreferredSize(
                new Dimension(
                        900,
                        250
                )
        );

        //---------------------- SIN BORDE
        scrollTabla.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollTabla.setOpaque(false);

        scrollTabla.getViewport().setOpaque(false);

        //---------------------- SCROLL VERTICAL
        scrollTabla.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        //---------------------- SCROLL HORIZONTAL
        scrollTabla.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        //---------------------- RESTRICCIONES TABLA
        GridBagConstraints gbcTabla = new GridBagConstraints();

        gbcTabla.gridx = 0;
        gbcTabla.gridy = filaSiguiente;

        gbcTabla.gridwidth = 12;

        gbcTabla.weightx = 1.0;
        gbcTabla.weighty = 1.0;

        gbcTabla.anchor = GridBagConstraints.CENTER;
        gbcTabla.fill = GridBagConstraints.BOTH;

        gbcTabla.insets = new Insets(
                15,
                5,
                10,
                5
        );

        //---------------------- AGREGAR TABLA
        Pcontenido.add(
                scrollTabla,
                gbcTabla
        );

        //---------------------- SIGUIENTE FILA
        filaSiguiente++;

        //---------------------- BOTONES
        if (mostrarBotones) {

            //---------------------- PANEL BOTONES
            JPanel Pbotones = new JPanel(
                    new GridBagLayout()
            );

            Pbotones.setOpaque(false);

            //---------------------- BUSCAR
            Bbuscar = new Boton(
                    "Buscar"
            );

            Bbuscar.crema();

            //---------------------- CREAR
            Bcrear = new Boton(
                    "Crear"
            );

            Bcrear.amarillo();

            //---------------------- ACTUALIZAR
            Bactualizar = new Boton(
                    "Actualizar"
            );

            Bactualizar.verde();

            //---------------------- D E S A C T I V A R  /  A C T I V A R
            // Este botón tiene dos comportamientos según el estado del
            // registro seleccionado: si está activo lo desactiva y se
            // pone en rojo; si ya está inactivo lo reactiva y se pone
            // en verde. El texto y el color se actualizan en
            // CrudAdministrador.actualizarBotonEstado().
            Bdesactivar = new Boton(
                    textoBotonEstado
            );

            Bdesactivar.rojo();

            //---------------------- ACCIONES
            Bbuscar.addActionListener(
                    e -> buscar()
            );

            Bcrear.addActionListener(
                    e -> crear()
            );

            Bactualizar.addActionListener(
                    e -> actualizar()
            );

            Bdesactivar.addActionListener(
                    e -> desactivar()
            );

            //---------------------- RESTRICCIONES BOTONES
            GridBagConstraints gbcBoton = new GridBagConstraints();

            gbcBoton.gridy = 0;

            gbcBoton.weightx = 1.0;
            gbcBoton.weighty = 0.0;

            gbcBoton.fill = GridBagConstraints.HORIZONTAL;

            gbcBoton.insets = new Insets(
                    5,
                    5,
                    10,
                    5
            );

            //---------------------- BUSCAR
            gbcBoton.gridx = 0;

            Pbotones.add(
                    Bbuscar,
                    gbcBoton
            );

            //---------------------- CREAR
            gbcBoton.gridx = 1;

            Pbotones.add(
                    Bcrear,
                    gbcBoton
            );

            //---------------------- ACTUALIZAR
            gbcBoton.gridx = 2;

            Pbotones.add(
                    Bactualizar,
                    gbcBoton
            );

            //---------------------- DESACTIVAR
            if (mostrarDesactivar) {

                gbcBoton.gridx = 3;

                Pbotones.add(
                        Bdesactivar,
                        gbcBoton
                );
            }

            //---------------------- RESTRICCIONES PANEL BOTONES
            GridBagConstraints gbcFinal = new GridBagConstraints();

            gbcFinal.gridx = 0;
            gbcFinal.gridy = filaSiguiente;

            gbcFinal.gridwidth = 12;

            gbcFinal.weightx = 1.0;
            gbcFinal.weighty = 0.0;

            gbcFinal.fill = GridBagConstraints.HORIZONTAL;

            gbcFinal.insets = new Insets(
                    5,
                    5,
                    15,
                    5
            );

            //---------------------- AGREGAR BOTONES
            Pcontenido.add(
                    Pbotones,
                    gbcFinal
            );
        }

        //---------------------- ACTUALIZAR
        Pcontenido.revalidate();
        Pcontenido.repaint();

        //---------------------- CONEXIÓN CRUD
        CrudAdministrador.inicializar(this);
    }

    //---------------------- A L   M O S T R A R ----------------
    /**
     * Vuelve a llenar la tabla y los combos cada vez que se muestra la
     * ventana.
     *
     * La ventana se reutiliza en vez de construirse de nuevo (eso es lo
     * que elimina el parpadeo al navegar). Pero si solo se la muestra tal
     * cual, la tabla seguiría mostrando los datos de la última visita:
     * si en el meantime otro usuario creó o modificó un registro, no se
     * vería hasta recargar la ventana a mano.
     *
     * Se vuelve a llamar a CrudAdministrador.inicializar(), que es la misma
     * rutina que arma un CRUD recién creado, así que el comportamiento es
     * idéntico tanto la primera vez como las siguientes.
     */
    @Override
    protected void alMostrar() {

        CrudAdministrador.inicializar(this);
    }

    //------------------------------------------------------------------------
//                         T Í T U L O
//------------------------------------------------------------------------
//---------------------- S E T T E A R   T Í T U L O ----------------
    protected void setTituloCRUD(
            String titulo
    ) {

        Ltitulo.setText(
                titulo
        );

        setTitle(
                titulo
        );

        Ltitulo.revalidate();
        Ltitulo.repaint();
    }

    //------------------------------------------------------------------------
    //                            G E T T E R S
    //------------------------------------------------------------------------
    public CampoTexto getCampoId() {

        return Tid;
    }

    public Tabla getTabla() {

        return tabla;
    }

    public CargarImagen getCargarImagen() {

        return cargarImagen;
    }

    //------------------------------------------------------------------------
    //                            A C C I O N E S
    //------------------------------------------------------------------------
    protected void buscar() {
        CrudAdministrador.buscar(this);
    }

    protected void crear() {
        CrudAdministrador.crear(this);
    }

    protected void actualizar() {
        CrudAdministrador.actualizar(this);
    }

    protected void desactivar() {
        CrudAdministrador.desactivar(this);
    }

    protected void tablaSeleccionada() {
        CrudAdministrador.seleccionada(this);
    }

    //------------------------------------------------------------------------
    //                      A Y U D A S   D E   C R U D
    //------------------------------------------------------------------------
    protected final AdminDAO adminDAO = new AdminDAO();

    protected void cargarTabla(String sql, Object... parametros) {

        modeloTabla.setRowCount(0);

        java.util.List<Object[]> filas = adminDAO.consultar(sql, parametros);

        for (Object[] fila : filas) {
            modeloTabla.addRow(fila);
        }

        // Si no hay filas se inserta una con un texto explicativo, para
        // que una tabla vacía no se confunda con una consulta que falló.
        if (filas.isEmpty() && modeloTabla.getColumnCount() > 0) {

            Object[] vacia = new Object[modeloTabla.getColumnCount()];

            java.util.Arrays.fill(vacia, "");

            vacia[0] = "-";
            vacia[1] = "No hay registros";

            modeloTabla.addRow(vacia);
        }
    }

    protected int idSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return 0;
        Object valor = tabla.getValueAt(fila, 0);
        return valor instanceof Number ? ((Number) valor).intValue()
                : Integer.parseInt(valor.toString());
    }

    protected void prepararId(String tablaBD, String columnaId) {
        if (Tid != null) {
            Tid.setText(String.valueOf(adminDAO.siguienteId(tablaBD, columnaId)));
            // Se bloquea el campo solo si el CRUD lo pidió. Esto mantiene
            // el comportamiento anterior para los CRUDs que sí necesitan
            // escribir el ID a mano.
            if (idBloqueado) {
                Tid.setEditable(false);
                Tid.setToolTipText(
                        "El ID se asigna automáticamente. No se puede modificar."
                );
            }
        }
    }

    //------------------------------------------------------------------------
    //              B O T Ó N   D E S A C T I V A R  /  A C T I V A R
    //------------------------------------------------------------------------
    /**
     * Cambia el texto y el color del botón de estado según si el registro
     * seleccionado está activo o inactivo.
     *
     * Lógica:
     *   - Registro activo   -> el botón dice "Desactivar" y es rojo,
     *                           porque la acción será desactivar.
     *   - Registro inactivo -> el botón dice "Activar" y es verde,
     *                           porque la acción será activar.
     *
     * @param activo true si el registro seleccionado está activo.
     */
    protected void actualizarBotonEstado(boolean activo) {

        if (Bdesactivar == null) {
            return;
        }

        if (activo) {
            textoBotonEstado = "Desactivar";
            Bdesactivar.setText("Desactivar");
            Bdesactivar.rojo();
        } else {
            textoBotonEstado = "Activar";
            Bdesactivar.setText("Activar");
            Bdesactivar.verde();
        }

        Bdesactivar.repaint();
    }

    /**
     * Reduce la tabla a una sola fila: la del registro con el ID indicado,
     * y deja esa fila seleccionada.
     *
     * Se usa al buscar un registro puntual, para que el resultado se vea en
     * la tabla y no solo en los campos del formulario. Si el ID no está en
     * la tabla no se hace nada, porque puede haberse buscado un registro
     * que no aparece en el listado (por ejemplo, uno desactivado).
     *
     * @param id id del registro a resaltar.
     */
    protected void encontrarEnTabla(int id) {

        if (modeloTabla == null || tabla == null) {
            return;
        }

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {

            Object valor = modeloTabla.getValueAt(i, 0);

            int filaId = valor instanceof Number
                    ? ((Number) valor).intValue()
                    : parseEntero(valor);

            if (filaId == id) {

                // Se borran las demás filas para que solo quede el
                // resultado de la búsqueda.
                for (int j = modeloTabla.getRowCount() - 1; j >= 0; j--) {

                    if (j != i) {
                        modeloTabla.removeRow(j);
                    }
                }

                int nuevaFila = modeloTabla.getRowCount() - 1;

                if (nuevaFila >= 0) {

                    tabla.setRowSelectionInterval(nuevaFila, nuevaFila);
                    tabla.scrollRectToVisible(
                            tabla.getCellRect(
                                    nuevaFila,
                                    0,
                                    true
                            )
                    );
                }

                modeloTabla.fireTableDataChanged();

                return;
            }
        }
    }

    /**
     * Convierte a número un valor de la primera columna de la tabla.
     * Si no es numérico devuelve -1, que no coincidirá con ningún ID.
     */
    private int parseEntero(Object valor) {

        try {
            return Integer.parseInt(String.valueOf(valor));
        } catch (Exception e) {
            return -1;
        }
    }

    protected void limpiarCampos() {
        limpiarComponente(Pcontenido);
    }

    private void limpiarComponente(Component c) {
        if (c == Tid) return;
        if (c instanceof JTextField) ((JTextField) c).setText("");
        else if (c instanceof JTextArea) ((JTextArea) c).setText("");
        else if (c instanceof JComboBox && ((JComboBox<?>) c).getItemCount() > 0)
            ((JComboBox<?>) c).setSelectedIndex(0);
        if (c instanceof Container)
            for (Component h : ((Container) c).getComponents()) limpiarComponente(h);
    }

    /**
     * Muestra un mensaje de la ventana CRUD.
     *
     * Todas las ventanas de administración pasan por aquí para avisar
     * algo, así que este único método es el que decide cómo se ven esos
     * cuadros. Antes usaba JOptionPane.showMessageDialog, que pone el
     * botón "OK" en el idioma del sistema operativo; ahora usa Dialogo,
     * que siempre muestra "Aceptar".
     *
     * @param texto mensaje a mostrar.
     */
    protected void mensaje(String texto) {
        Dialogo.aviso(this, "Aviso", texto);
    }

    protected boolean enteroValido(String texto) {
        try { Integer.parseInt(texto.trim()); return true; }
        catch (Exception e) { return false; }
    }

    protected boolean decimalValido(String texto) {
        try { Double.parseDouble(texto.trim()); return true; }
        catch (Exception e) { return false; }
    }
}
