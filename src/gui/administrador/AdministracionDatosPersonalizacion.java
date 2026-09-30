//------------------ A D M I N I S T R A C I O N   D A T O S   P E R S O N A L I Z A C I O N ------------------
package gui.administrador;

import gui.componentes.Boton;
import gui.componentes.CarruselImagenes;
import gui.estilo.*;
import java.awt.*;
import javax.swing.*;
import logica.Reloj;

public class AdministracionDatosPersonalizacion extends VentanaAdministrador {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Panel principal
    private JPanel Pcontenido;

    //-------------- Título
    private JLabel Ltitulo;

    //-------------- Carrusel
    private CarruselImagenes carrusel;

    //-------------- Botones
    private Boton BgrupoPersonalizacion;
    private Boton BopcionPersonalizacion;


    //------------------------ C O N S T R U C T O R ----------------
    public AdministracionDatosPersonalizacion() {

        super("Administración de Personalización");

        // Es un submenú de administración de datos, por eso la flecha
        // vuelve al menú general y no al panel principal.
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
                "Administración de Personalización"
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

        gbc.gridheight = 2;

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

        //---------------------- GRUPO PERSONALIZACIÓN
        BgrupoPersonalizacion = new Boton(
                "Grupo Personalización"
        );

        BgrupoPersonalizacion.amarillo();

        //---------------------- OPCIÓN PERSONALIZACIÓN
        BopcionPersonalizacion = new Boton(
                "Opción Personalización"
        );

        BopcionPersonalizacion.verde();

        //---------------------- AGREGAR
        agregarBoton(
                BgrupoPersonalizacion,
                1
        );

        agregarBoton(
                BopcionPersonalizacion,
                2
        );

        //---------------------- ACCIONES
        BgrupoPersonalizacion.addActionListener(e -> {
            abrir(GrupoPersonalizacionCRUD.class, GrupoPersonalizacionCRUD::new);
        });

        BopcionPersonalizacion.addActionListener(e -> {
            abrir(OpcionPersonalizacionCRUD.class, OpcionPersonalizacionCRUD::new);
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
        gbc.weighty = 0.45;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                15,
                20,
                15,
                10
        );

        Pcontenido.add(
                boton,
                gbc
        );
    }
}
