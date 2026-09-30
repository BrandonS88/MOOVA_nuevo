//------------------ A D M I N I S T R A C I O N   D A T O S   C O M B O ------------------
package gui.administrador;

import gui.componentes.Boton;
import gui.componentes.CarruselImagenes;
import gui.estilo.*;
import java.awt.*;
import javax.swing.*;
import logica.Reloj;

public class AdministracionDatosCombo extends VentanaAdministrador {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Panel principal
    private JPanel Pcontenido;

    //-------------- Título
    private JLabel Ltitulo;

    //-------------- Carrusel
    private CarruselImagenes carrusel;

    //-------------- Botones
    private Boton Bcombos;
    private Boton BgrupoCombos;
    private Boton BopcionCombos;
    private Boton BproductoCombos;


    //------------------------ C O N S T R U C T O R ----------------
    public AdministracionDatosCombo() {

        super("Administración de Combos");

        // Esta pantalla es un submenú de administración de datos, así que
        // la flecha vuelve al menú general de administración.
        setAccionRegreso(
                () -> abrir(AdministracionDatos.class, AdministracionDatos::new)
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
                        25,
                        25,
                        25
                )
        );

        //---------------------- COMPONENTES
        inicializarTitulo();

        inicializarCarrusel();

        inicializarBotones();

        //---------------------- AGREGAR
        Pcentral.add(
                Pcontenido,
                BorderLayout.CENTER
        );
    }


    //------------------------ T Í T U L O ----------------
    private void inicializarTitulo() {

        Ltitulo = new JLabel(
                "Administración de Combos"
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

        gbc.gridwidth = 2;

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

        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.gridheight = 4;

        gbc.weightx = 0.65;
        gbc.weighty = 0.90;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.BOTH;

        gbc.insets = new Insets(
                10,
                10,
                10,
                20
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


    //------------------------ B O T O N E S ----------------
    private void inicializarBotones() {

        //---------------------- COMBOS
        Bcombos = new Boton(
                "Combos"
        );

        Bcombos.amarillo();

        //---------------------- GRUPO COMBOS
        BgrupoCombos = new Boton(
                "Grupo Combos"
        );

        BgrupoCombos.verde();

        //---------------------- OPCIÓN COMBOS
        BopcionCombos = new Boton(
                "Opción Combos"
        );

        BopcionCombos.verde();

        //---------------------- PRODUCTO COMBOS
        BproductoCombos = new Boton(
                "Producto Combos"
        );

        BproductoCombos.verde();

        //---------------------- AGREGAR
        agregarBoton(
                Bcombos,
                1
        );

        agregarBoton(
                BgrupoCombos,
                2
        );

        agregarBoton(
                BopcionCombos,
                3
        );

        agregarBoton(
                BproductoCombos,
                4
        );

        //---------------------- ACCIONES
        Bcombos.addActionListener(e -> {
            abrir(ComboCRUD.class, ComboCRUD::new);
        });

        BgrupoCombos.addActionListener(e -> {
            abrir(GrupoComboCRUD.class, GrupoComboCRUD::new);
        });

        BopcionCombos.addActionListener(e -> {
            abrir(OpcionComboCRUD.class, OpcionComboCRUD::new);
        });

        BproductoCombos.addActionListener(e -> {
            abrir(ProductoComboCRUD.class, ProductoComboCRUD::new);
        });
    }


    //------------------------ A G R E G A R   B O T Ó N ----------------
    private void agregarBoton(
            Boton boton,
            int fila
    ) {

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = fila;

        gbc.weightx = 0.35;
        gbc.weighty = 0.225;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                10,
                20,
                10,
                10
        );

        Pcontenido.add(
                boton,
                gbc
        );
    }
}
