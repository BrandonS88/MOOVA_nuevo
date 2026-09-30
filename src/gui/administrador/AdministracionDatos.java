//------------------ A D M I N I S T R A C I O N   D A T O S ------------------
package gui.administrador;

import gui.componentes.*;
import gui.estilo.*;
import java.awt.*;
import javax.swing.*;
import logica.Reloj;

public class AdministracionDatos extends VentanaAdministrador {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Panel principal
    private JPanel Pcontenido;

    //-------------- Título
    private JLabel Ltitulo;

    //-------------- Botones principales
    private Boton Bcombos;
    private Boton Bpersonalizacion;
    private Boton Bproducto;

    //-------------- Botones izquierda
    private Boton Bcategoria;
    private Boton Bsubcategoria;
    private Boton Bdescuento;

    //-------------- Botones derecha
    private Boton Bingrediente;
    private Boton Breceta;
    private Boton Busuario;

    //-------------- Carrusel
    private CarruselImagenes carrusel;


    //------------------------ C O N S T R U C T O R ----------------
    public AdministracionDatos() {

        super("Administración de Datos");

        // Esta pantalla es el menú de administración de datos, así que la
        // flecha debe devolver al panel principal del administrador.
        setAccionRegreso(
                () -> abrir(PantallaDashboard.class, PantallaDashboard::new)
        );

        inicializarAdministracion();

        setVisible(true);
    }


    //------------------------ I N I C I A L I Z A R ----------------
    private void inicializarAdministracion() {

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

        //---------------------- COMPONENTES
        inicializarTitulo();

        inicializarBotonesPrincipales();

        inicializarBotonesLaterales();

        inicializarCarrusel();

        //---------------------- AGREGAR
        Pcentral.add(
                Pcontenido,
                BorderLayout.CENTER
        );
    }


    //------------------------ T Í T U L O ----------------
    private void inicializarTitulo() {

        Ltitulo = new JLabel(
                "Administración de Datos"
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

        gbc.gridwidth = 3;

        gbc.weightx = 1.0;
        gbc.weighty = 0.10;

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


    //------------------------ B O T O N E S   P R I N C I P A L E S ----------------
    private void inicializarBotonesPrincipales() {

        //---------------------- COMBOS
        Bcombos = new Boton(
                "Combos"
        );

        Bcombos.amarillo();

        //---------------------- PERSONALIZACIÓN
        Bpersonalizacion = new Boton(
                "Personalización"
        );

        Bpersonalizacion.amarillo();

        //---------------------- PRODUCTO
        Bproducto = new Boton(
                "Producto"
        );

        Bproducto.amarillo();

        //---------------------- COMBOS
        agregarBoton(
                Bcombos,
                0,
                1,
                1
        );

        //---------------------- PERSONALIZACIÓN
        agregarBoton(
                Bpersonalizacion,
                1,
                1,
                1
        );

        //---------------------- PRODUCTO
        agregarBoton(
                Bproducto,
                2,
                1,
                1
        );

        //---------------------- ACCIONES
        Bcombos.addActionListener(e -> {
            abrir(AdministracionDatosCombo.class, AdministracionDatosCombo::new);
        });

        Bpersonalizacion.addActionListener(e -> {
            abrir(AdministracionDatosPersonalizacion.class, AdministracionDatosPersonalizacion::new);
        });

        Bproducto.addActionListener(e -> {
            abrir(ProductoCRUD.class, ProductoCRUD::new);
        });
    }


    //------------------------ B O T O N E S   L A T E R A L E S ----------------
    private void inicializarBotonesLaterales() {

        //---------------------- IZQUIERDA
        Bcategoria = new Boton(
                "Categoría"
        );

        Bcategoria.verde();

        Bsubcategoria = new Boton(
                "Subcategoría"
        );

        Bsubcategoria.verde();

        Bdescuento = new Boton(
                "Descuento"
        );

        Bdescuento.verde();

        //---------------------- DERECHA
        Bingrediente = new Boton(
                "Ingrediente"
        );

        Bingrediente.verde();

        Breceta = new Boton(
                "Receta"
        );

        Breceta.verde();

        Busuario = new Boton(
                "Usuario"
        );

        Busuario.verde();

        //---------------------- IZQUIERDA
        agregarBoton(
                Bcategoria,
                0,
                2,
                1
        );

        agregarBoton(
                Bsubcategoria,
                0,
                3,
                1
        );

        agregarBoton(
                Bdescuento,
                0,
                4,
                1
        );

        //---------------------- DERECHA
        agregarBoton(
                Bingrediente,
                2,
                2,
                1
        );

        agregarBoton(
                Breceta,
                2,
                3,
                1
        );

        agregarBoton(
                Busuario,
                2,
                4,
                1
        );

        //---------------------- ACCIONES
        Bcategoria.addActionListener(e -> {
            abrir(CategoriaCRUD.class, CategoriaCRUD::new);
        });

        Bsubcategoria.addActionListener(e -> {
            abrir(SubcategoriaCRUD.class, SubcategoriaCRUD::new);
        });

        Bdescuento.addActionListener(e -> {
            abrir(DescuentoCRUD.class, DescuentoCRUD::new);
        });

        Bingrediente.addActionListener(e -> {
            abrir(IngredienteCRUD.class, IngredienteCRUD::new);
        });

        Breceta.addActionListener(e -> {
            abrir(RecetaCRUD.class, RecetaCRUD::new);
        });

        Busuario.addActionListener(e -> {
            abrir(UsuarioCRUD.class, UsuarioCRUD::new);
        });
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

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 2;

        gbc.gridheight = 3;

        gbc.weightx = 1.0;
        gbc.weighty = 0.90;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.BOTH;

        gbc.insets = new Insets(
                10,
                15,
                15,
                15
        );

        Pcontenido.add(
                carrusel,
                gbc
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


    //------------------------ A G R E G A R   B O T Ó N ----------------
    private void agregarBoton(
            Boton boton,
            int columna,
            int fila,
            int ancho
    ) {

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = columna;
        gbc.gridy = fila;

        gbc.gridwidth = ancho;

        gbc.weightx = 1.0;
        gbc.weighty = 0.20;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                8,
                10,
                8,
                10
        );

        Pcontenido.add(
                boton,
                gbc
        );
    }
}
