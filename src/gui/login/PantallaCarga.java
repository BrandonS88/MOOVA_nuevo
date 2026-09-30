//--------------------------------- P A N T A L L A  D E  C A R G A ----------------------------------
package gui.login;

import java.awt.*;
import javax.swing.*;
import gui.componentes.*;
import gui.estilo.*;

public class PantallaCarga extends JFrame {

    //------------------------  A T R I B U T O S ---------------
    //-------------- Panel principal
    private PanelFondo Pprincipal;
    //-------------- Paneles decorativos
    private JPanel PlateralIzquierdo, PlateralDerecho;
    //-------------- Panel central
    private JPanel Pcentro;
    //-------------- Componentes
    private JLabel Ilogo, Lcargando;
    //-------------- Panel de capas
    private JLayeredPane Pcapas;
    //-------------- Panel decorativo superior
    private JPanel Pdecoraciones;
    //-------------- Imágenes decorativas
    private ImagenEscalada IdecoracionIzquierda, IdecoracionDerecha;
    //-------------- Barra de carga
    private BarraCarga barraCarga;

    //-------------------- C O N S T R U C T O R -----------------
    public PantallaCarga() {

        //----------------- JFRAME
        setTitle("Cargando...");

        // Tamaño de la ventana
        setSize(500, 300);

        // Centrar en la pantalla
        setLocationRelativeTo(null);

        // No permitir redimensionar
        setResizable(false);

        // Quitar bordes y controles de Windows
        setUndecorated(true);

        // Cerrar el programa si se cierra esta ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarComponentes();

        // Mostrar ventana
        setVisible(true);

        //Incia la carga
        iniciarCarga();
    }

    //----------- I N I C I A L I Z A R  C O M P O N E N T E S ----------------
    public void inicializarComponentes() {

        inicializarPrincipal();
        inicializarPanelesLaterales();
        inicializarCentro();
        inicializarDecoraciones();
    }

    //----------------------- PANEL PRINCIPAL
    public void inicializarPrincipal() {

        // Panel con fondo
        Pprincipal = new PanelFondo(
                "/utiles/imagenes/fondos/fondo_A.png"
        );

        Pprincipal.setLayout(new GridBagLayout());

        //------------------ PANEL DE CAPAS
        Pcapas = new JLayeredPane();

        Pcapas.setLayout(null);

        // Tamaño de la capa
        Pcapas.setBounds(
                0,
                0,
                getWidth(),
                getHeight()
        );

        // Tamaño del panel principal
        Pprincipal.setBounds(
                0,
                0,
                getWidth(),
                getHeight()
        );

        // Agregar el panel principal como fondo
        Pcapas.add(
                Pprincipal,
                JLayeredPane.DEFAULT_LAYER
        );

        // Agregar al JFrame
        this.add(Pcapas);
    }

    //----------------------- PANELES LATERALES
    public void inicializarPanelesLaterales() {
        PlateralIzquierdo = new JPanel();
        PlateralDerecho = new JPanel();

        // Colores
        PlateralIzquierdo.setBackground(Colores.CORINTO);
        PlateralDerecho.setBackground(Colores.NARANJA);

        //----------------- PANEL IZQUIERDO
        GridBagConstraints gbcIzquierdo = new GridBagConstraints();

        gbcIzquierdo.gridx = 0;
        gbcIzquierdo.gridy = 0;

        // Proporción horizontal
        gbcIzquierdo.weightx = 0.20;
        gbcIzquierdo.weighty = 1.0;

        // Ocupa todo el espacio asignado
        gbcIzquierdo.fill = GridBagConstraints.BOTH;

        Pprincipal.add(PlateralIzquierdo, gbcIzquierdo);

        //----------------- PANEL DERECHO
        GridBagConstraints gbcDerecho = new GridBagConstraints();

        gbcDerecho.gridx = 2;
        gbcDerecho.gridy = 0;

        // Proporción horizontal
        gbcDerecho.weightx = 0.20;
        gbcDerecho.weighty = 1.0;

        // Ocupa todo el espacio asignado
        gbcDerecho.fill = GridBagConstraints.BOTH;

        Pprincipal.add(PlateralDerecho, gbcDerecho);
    }

    //----------------------- PANEL CENTRAL
    public void inicializarCentro() {

        Pcentro = new JPanel();

        // Organizador
        Pcentro.setLayout(new GridBagLayout());

        //Lo hacemos transparente
        Pcentro.setOpaque(false);

        //                     LOGO
        Ilogo = new ImagenEscalada(
                "/utiles/imagenes/logotipo/logo-texto.png",
                270,
                120
        );

        GridBagConstraints gbcLogo = new GridBagConstraints();

        gbcLogo.gridx = 0;
        gbcLogo.gridy = 0;

        gbcLogo.weightx = 1.0;
        gbcLogo.weighty = 0.55;

        gbcLogo.fill = GridBagConstraints.BOTH;

        Pcentro.add(Ilogo, gbcLogo);

        //                  TEXTO
        Lcargando = new JLabel("Cargando...");

        Lcargando.setFont(
                Fuentes.VC.deriveFont(Fuentes.SUBTITULO)
        );

        Lcargando.setForeground(Colores.CORINTO);

        Lcargando.setHorizontalAlignment(SwingConstants.CENTER);
        Lcargando.setVerticalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbcTexto = new GridBagConstraints();

        gbcTexto.gridx = 0;
        gbcTexto.gridy = 1;

        gbcTexto.weightx = 1.0;
        gbcTexto.weighty = 0.10;

        gbcTexto.fill = GridBagConstraints.BOTH;

        Pcentro.add(Lcargando, gbcTexto);

        //------------------ BARRA DE CARGA
        barraCarga = new BarraCarga();
        barraCarga.setAncho(350);
        barraCarga.setAltura(20);
        barraCarga.setValor(0);

        GridBagConstraints gbcBarra = new GridBagConstraints();

        gbcBarra.gridx = 0;
        gbcBarra.gridy = 2;

        gbcBarra.weightx = 1.0;
        gbcBarra.weighty = 0.35;

        gbcBarra.fill = GridBagConstraints.HORIZONTAL;

        gbcBarra.insets = new Insets(
                0, 30, 0, 30
        );

        Pcentro.add(barraCarga, gbcBarra);
        //              Pcentro EN Pprincipal
        GridBagConstraints gbcCentro = new GridBagConstraints();

        gbcCentro.gridx = 1;
        gbcCentro.gridy = 0;

        gbcCentro.weightx = 0.40;
        gbcCentro.weighty = 1.0;

        gbcCentro.fill = GridBagConstraints.BOTH;

        Pprincipal.add(Pcentro, gbcCentro);
    }

    public void iniciarCarga() {

        Timer timerCarga = new Timer(50, e -> {

            barraCarga.aumentarValor(1);
            
            //cuando llege a 100
            if (barraCarga.getValor() >= barraCarga.getValorMaximo()) {

                ((Timer) e.getSource()).stop();

                dispose();

                new PantallaLogin();
            }
        });

        timerCarga.start();
    }

    //----------------------- P A N E L   D E C O R A C I O N E S
    public void inicializarDecoraciones() {

        // Panel transparente
        Pdecoraciones = new JPanel();

        Pdecoraciones.setOpaque(false);

        // Posiciones absolutas
        Pdecoraciones.setLayout(null);

        //------------------ TAMAÑO DE LA CAPA
        Pdecoraciones.setBounds(
                0,
                0,
                getWidth(),
                getHeight()
        );

        //------------------ I M A G E N   I Z Q U I E R D A
        IdecoracionIzquierda = new ImagenEscalada(
                "/utiles/imagenes/elementosGraficos/Mancha-verde-arriba-izquierda.png",
                180,
                120
        );

        IdecoracionIzquierda.setAlineacion(
                SwingConstants.LEFT,
                SwingConstants.TOP
        );

        IdecoracionIzquierda.setBounds(
                0,
                0,
                180,
                120
        );

        Pdecoraciones.add(IdecoracionIzquierda);

        //------------------ I M A G E N   D E R E C H A
        IdecoracionDerecha = new ImagenEscalada(
                "/utiles/imagenes/elementosGraficos/Mancha-roja-abajo-derecha.png",
                180,
                120
        );

        IdecoracionDerecha.setAlineacion(
                SwingConstants.RIGHT,
                SwingConstants.BOTTOM
        );

        IdecoracionDerecha.setBounds(
                Pdecoraciones.getWidth() - 180,
                Pdecoraciones.getHeight() - 120,
                180,
                120
        );

        Pdecoraciones.add(IdecoracionDerecha);

        //------------------ AGREGAR A LA CAPA SUPERIOR
        Pcapas.add(
                Pdecoraciones,
                JLayeredPane.PALETTE_LAYER
        );
    }

}
