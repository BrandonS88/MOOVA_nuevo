//------------------ P A N T A L L A   I N C I D E N T E S ------------------
package gui.administrador;

import dao.AdminDAO;
import java.sql.Timestamp;
import gui.componentes.*;
import gui.estilo.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PantallaIncidentes extends VentanaAdministrador {

    private final AdminDAO adminDAO = new AdminDAO();

    //------------------------ A T R I B U T O S ----------------
    //-------------- Panel principal
    private JPanel Pcontenido;

    //-------------- Título
    private JLabel Ltitulo;

    //-------------- Campos de texto
    private CampoTexto FidIncidente;
    private CampoTexto Ftitulo;

    //-------------- Calendarios
    private Calendario Creporte;
    private Calendario Cresolucion;

    //-------------- Áreas de texto
    private CampoArea Adescripcion;
    private CampoArea Asolucion;

    //-------------- Combobox
    private CampoComboBox<String> Ctipo;
    private CampoComboBox<String> Cprioridad;
    private CampoComboBox<String> Cestado;

    //-------------- Botones
    private Boton Bbuscar;
    private Boton Bcrear;
    private Boton Beditar;

    //-------------- Tabla
    private Tabla Tincidentes;

    //------------------------ C O N S T R U C T O R ----------------
    public PantallaIncidentes() {

        super("Incidentes");

        // La pantalla de incidentes se abre desde el panel del
        // administrador, así que la flecha vuelve a ese panel.
        setAccionRegreso(
                () -> abrir(PantallaDashboard.class, PantallaDashboard::new)
        );

        inicializarIncidentes();

        setVisible(true);
    }

    //------------------------ I N I C I A L I Z A R ----------------
    private void inicializarIncidentes() {

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

        //---------------------- CREAR
        inicializarTitulo();

        inicializarCamposTexto();

        inicializarCalendarios();

        inicializarAreas();

        inicializarCombobox();

        inicializarBotones();

        inicializarTabla();
        activarCRUD();

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
                "Incidentes"
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

        gbc.gridwidth = 6;

        gbc.weightx = 1.0;
        gbc.weighty = 0.05;

        gbc.anchor
                = GridBagConstraints.CENTER;

        gbc.fill
                = GridBagConstraints.HORIZONTAL;

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

    //------------------------ C A M P O S   T E X T O ----------------
    private void inicializarCamposTexto() {

        //---------------------- ID
        FidIncidente = new CampoTexto();

        FidIncidente.setPreferredSize(
                new Dimension(
                        250,
                        45
                )
        );

        //---------------------- TÍTULO
        Ftitulo = new CampoTexto();

        Ftitulo.setPreferredSize(
                new Dimension(
                        250,
                        45
                )
        );

        //---------------------- AGREGAR
        agregarCampo(
                "Id_incidente",
                FidIncidente,
                0,
                1
        );

        agregarCampo(
                "Título",
                Ftitulo,
                3,
                1
        );
    }

    //------------------------ C A L E N D A R I O S ----------------
    private void inicializarCalendarios() {

        //---------------------- FECHA REPORTE
        Creporte = new Calendario();

        Creporte.setTamaño(
                250,
                45
        );

        //---------------------- FECHA RESOLUCIÓN
        Cresolucion = new Calendario();

        Cresolucion.setTamaño(
                250,
                45
        );

        //---------------------- AGREGAR
        agregarCampo(
                "Fecha Reporte",
                Creporte,
                0,
                2
        );

        agregarCampo(
                "Fecha resolución",
                Cresolucion,
                3,
                2
        );
    }

    //------------------------ A R E A S ----------------
    private void inicializarAreas() {

        //---------------------- DESCRIPCIÓN
        Adescripcion = new CampoArea();

        Adescripcion.setTamaño(
                300,
                130
        );

        //---------------------- SOLUCIÓN
        Asolucion = new CampoArea();

        Asolucion.setTamaño(
                300,
                130
        );

        //---------------------- AGREGAR
        agregarCampo(
                "Descripción",
                Adescripcion,
                0,
                3
        );

        agregarCampo(
                "Solución",
                Asolucion,
                3,
                3
        );
    }

    //------------------------ C O M B O B O X ----------------
    private void inicializarCombobox() {

        //---------------------- TIPO
        Ctipo = new CampoComboBox<>();

        Ctipo.setPreferredSize(
                new Dimension(
                        180,
                        45
                )
        );

        Ctipo.addItem("Producto");
        Ctipo.addItem("Ingrediente");
        Ctipo.addItem("Equipo");
        Ctipo.addItem("Cliente");
        Ctipo.addItem("Sistema");
        Ctipo.addItem("Otro");

        //---------------------- PRIORIDAD
        Cprioridad = new CampoComboBox<>();

        Cprioridad.setPreferredSize(
                new Dimension(
                        180,
                        45
                )
        );

        Cprioridad.addItem("Baja");
        Cprioridad.addItem("Media");
        Cprioridad.addItem("Alta");
        Cprioridad.addItem("Critica");

        //---------------------- ESTADO
        Cestado = new CampoComboBox<>();

        Cestado.setPreferredSize(
                new Dimension(
                        180,
                        45
                )
        );

        Cestado.addItem("Pendiente");
        Cestado.addItem("En proceso");
        Cestado.addItem("Resuelta");
        Cestado.addItem("Cancelada");

        //---------------------- AGREGAR
        agregarCampo(
                "Tipo",
                Ctipo,
                0,
                4,
                2
        );

        agregarCampo(
                "Prioridad",
                Cprioridad,
                2,
                4,
                2
        );

        agregarCampo(
                "Estado",
                Cestado,
                4,
                4,
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

        //---------------------- CREAR
        Bcrear = new Boton(
                "Crear"
        );

        Bcrear.verde();

        //---------------------- EDITAR
        Beditar = new Boton(
                "Editar"
        );

        Bcrear.amarillo();

        //---------------------- BUSCAR
        agregarBoton(
                Bbuscar,
                0,
                5
        );

        //---------------------- CREAR
        agregarBoton(
                Bcrear,
                2,
                5
        );

        //---------------------- EDITAR
        agregarBoton(
                Beditar,
                4,
                5
        );
    }

    private void inicializarTabla() {

        //---------------------- MODELO
        DefaultTableModel modelo
                = new DefaultTableModel(
                        new Object[]{
                            "id_Incidente",
                            "titulo",
                            "tipo",
                            "prioridad",
                            "estado",
                            "fecha_reporte",
                            "Reportado por",
                            "Ingrediente"
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
        Tincidentes = new Tabla();

        Tincidentes.setModel(
                modelo
        );

        //---------------------- SCROLL TABLA
        JScrollPane scrollTabla
                = new JScrollPane(
                        Tincidentes
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

        scrollTabla.setPreferredSize(
                new Dimension(
                        0,
                        350
                )
        );

        //---------------------- POSICIÓN
        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 6;

        gbc.gridwidth = 6;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.anchor
                = GridBagConstraints.CENTER;

        gbc.fill
                = GridBagConstraints.BOTH;

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
    }

    //------------------------ A G R E G A R   C A M P O ----------------
    private void agregarCampo(
            String texto,
            JComponent componente,
            int columna,
            int fila
    ) {

        agregarCampo(
                texto,
                componente,
                columna,
                fila,
                3
        );
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

        gbcLabel.anchor
                = GridBagConstraints.CENTER;

        gbcLabel.fill
                = GridBagConstraints.HORIZONTAL;

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

        gbcComponente.anchor
                = GridBagConstraints.CENTER;

        gbcComponente.fill
                = GridBagConstraints.HORIZONTAL;

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

        gbc.anchor
                = GridBagConstraints.CENTER;

        gbc.fill
                = GridBagConstraints.HORIZONTAL;

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

        gbc.gridwidth = 2;

        gbc.weightx = 1.0;
        gbc.weighty = 0.10;

        gbc.anchor
                = GridBagConstraints.CENTER;

        gbc.fill
                = GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                8,
                15,
                8,
                15
        );

        Pcontenido.add(
                boton,
                gbc
        );
    }


    //------------------------ C R U D   I N C I D E N T E S ----------------
    private void activarCRUD() {
        FidIncidente.setEditable(false);
        Bbuscar.addActionListener(e -> buscarIncidente());
        Bcrear.addActionListener(e -> crearIncidente());
        Beditar.addActionListener(e -> editarIncidente());
        Tincidentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Tincidentes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && Tincidentes.getSelectedRow() >= 0) {
                Object id = Tincidentes.getValueAt(Tincidentes.getSelectedRow(), 0);
                cargarIncidente(((Number) id).intValue());
            }
        });
        recargarIncidentes();
        siguienteIdIncidente();
    }

    //---------------------- A L   M O S T R A R ----------------
    /**
     * Vuelve a consultar los incidentes y a calcular el ID del siguiente
     * cada vez que se muestra la pantalla.
     *
     * La ventana se reutiliza en vez de construirse de nuevo, que es lo que
     * elimina el parpadeo al navegar. El precio es que la tabla se quedaría
     * con los datos de la última visita, así que al volver hay que releerlos
     * de la base de datos: si otro usuario reportó un incidente mientras
     * tanto, tiene que aparecer sin tener que cerrar y abrir la ventana.
     */
    @Override
    protected void alMostrar() {

        recargarIncidentes();
        siguienteIdIncidente();
    }

    private void recargarIncidentes() {
        DefaultTableModel m = (DefaultTableModel) Tincidentes.getModel();
        m.setRowCount(0);

        java.util.List<Object[]> filas = adminDAO.consultar(
                "SELECT i.id_Incidente,i.titulo,i.tipo,i.prioridad,i.estado,"
                        + "i.fecha_reporte,COALESCE(u.nombre_usuario,'-'),"
                        + "COALESCE(ing.nombre,'-') "
                        + "FROM Incidente i "
                        + "LEFT JOIN Usuario u ON u.id_Usuario = i.id_Usuario "
                        + "LEFT JOIN Ingrediente ing ON ing.id_Ingrediente = i.id_Ingrediente "
                        + "ORDER BY i.id_Incidente");

        for (Object[] f : filas) {
            m.addRow(f);
        }

        // Cuando no hay incidentes se muestra una fila explicativa. Una
        // tabla completamente vacía no permite distinguir entre "no hay
        // incidentes" y "falló la consulta", y esa diferencia importa:
        // lo primero es normal, lo segundo es un error.
        if (filas.isEmpty()) {
            m.addRow(new Object[]{
                    "-",
                    "No hay incidentes registrados",
                    "-", "-", "-", "-", "-", "-"
            });
        }
    }

    private void siguienteIdIncidente() {
        FidIncidente.setText(String.valueOf(adminDAO.siguienteId("Incidente", "id_Incidente")));
    }

    /**
     * Busca un incidente por su ID y lo muestra en el formulario.
     *
     * El ID se toma del campo de texto de la pantalla, no de una ventana
     * emergente, para que el usuario use el mismo campo que ve.
     */
    private void buscarIncidente() {

        String idTexto = FidIncidente.getText().trim();

        if (idTexto.isEmpty()) {
            Dialogo.aviso(this, "Aviso", "Ingrese el ID del incidente.");
            return;
        }

        try {
            if (!cargarIncidente(Integer.parseInt(idTexto)))
                Dialogo.aviso(this, "Aviso", "No se encontró el incidente con ese ID.");
        } catch (Exception e) {
            Dialogo.aviso(this, "Aviso", "El ID debe ser un número.");
        }
    }

    private boolean cargarIncidente(int id) {
        Object[] f = adminDAO.consultarUno(
                "SELECT id_Incidente,titulo,descripcion,fecha_reporte,fecha_resolucion,solucion,tipo,prioridad,estado FROM Incidente WHERE id_Incidente=?", id);
        if (f == null) return false;
        FidIncidente.setText(String.valueOf(f[0]));
        Ftitulo.setText(f[1] == null ? "" : String.valueOf(f[1]));
        Adescripcion.setText(f[2] == null ? "" : String.valueOf(f[2]));
        setFecha(Creporte, f[3]);
        setFecha(Cresolucion, f[4]);
        Asolucion.setText(f[5] == null ? "" : String.valueOf(f[5]));
        Ctipo.setSelectedItem(String.valueOf(f[6]));
        Cprioridad.setSelectedItem(String.valueOf(f[7]));
        Cestado.setSelectedItem(String.valueOf(f[8]));
        return true;
    }

    private void crearIncidente() {
        if (Ftitulo.getText().trim().isEmpty() || Adescripcion.getText().trim().isEmpty()) {
            Dialogo.aviso(this, "Aviso", "Complete título y descripción.");
            return;
        }
        // Se busca un administrador activo para asignarle el reporte.
        // La columna "estado" de la tabla Usuario es de tipo BOOLEAN, por eso
        // se compara contra TRUE. Compararla contra el texto 'Activa' haría
        // que MySQL lo convirtiera a 0 y la búsqueda nunca encontrara a nadie.
        Object[] u = adminDAO.consultarUno(
                "SELECT id_Usuario FROM Usuario WHERE rol='Administrador' AND estado=TRUE ORDER BY id_Usuario LIMIT 1");
        if (u == null) {
            Dialogo.aviso(this, "Aviso", "No hay administrador activo.");
            return;
        }
        boolean ok = adminDAO.ejecutar(
                "INSERT INTO Incidente(titulo,descripcion,fecha_reporte,fecha_resolucion,solucion,tipo,prioridad,estado,id_Usuario,id_Ingrediente,id_Producto) VALUES(?,?,?,?,?,?,?,?,?,NULL,NULL)",
                Ftitulo.getText().trim(), Adescripcion.getText().trim(),
                Timestamp.valueOf(Creporte.getFecha().atStartOfDay()),
                "Resuelta".equals(Cestado.getSelectedItem()) ? Timestamp.valueOf(Cresolucion.getFecha().atStartOfDay()) : null,
                Asolucion.getText().trim(), Ctipo.getSelectedItem(), Cprioridad.getSelectedItem(),
                Cestado.getSelectedItem(), ((Number) u[0]).intValue());
        finalizarOperacion(ok, "Incidente creado.");
    }

    private void editarIncidente() {
        int id;
        try { id = Integer.parseInt(FidIncidente.getText().trim()); }
        catch (Exception e) { Dialogo.aviso(this, "Aviso", "Seleccione un incidente."); return; }
        boolean ok = adminDAO.ejecutar(
                "UPDATE Incidente SET titulo=?,descripcion=?,fecha_resolucion=?,solucion=?,tipo=?,prioridad=?,estado=? WHERE id_Incidente=?",
                Ftitulo.getText().trim(), Adescripcion.getText().trim(),
                "Resuelta".equals(Cestado.getSelectedItem()) ? Timestamp.valueOf(Cresolucion.getFecha().atStartOfDay()) : null,
                Asolucion.getText().trim(), Ctipo.getSelectedItem(), Cprioridad.getSelectedItem(), Cestado.getSelectedItem(), id);
        finalizarOperacion(ok, "Incidente actualizado.");
    }

    private void finalizarOperacion(boolean ok, String mensaje) {
        Dialogo.aviso(this, "Aviso", ok ? mensaje : "No se pudo realizar la operación.");
        if (!ok) return;
        Ftitulo.setText("");
        Adescripcion.setText("");
        Asolucion.setText("");
        recargarIncidentes();
        siguienteIdIncidente();
    }

    private void setFecha(Calendario calendario, Object fecha) {
        if (fecha instanceof Timestamp)
            calendario.setFecha(((Timestamp) fecha).toLocalDateTime().toLocalDate());
        else if (fecha instanceof java.sql.Date)
            calendario.setFecha(((java.sql.Date) fecha).toLocalDate());
    }
}
