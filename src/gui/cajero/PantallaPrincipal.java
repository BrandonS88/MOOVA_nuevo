//---------------------------------- P A N T A L L A  P R I N C I P A L -----------------------
package gui.cajero;

import java.awt.*;
import javax.swing.*;
import gui.componentes.*;
import gui.estilo.*;
import logica.Reloj;

public class PantallaPrincipal extends JFrame {

    //------------------------  A T R I B U T O S ----------------
    //---------------- Paneles
    private BarraInferior barraInferior;
    private PanelFondo Pprincipal;
    private BarraSuperior barraSuperior;
    private JPanel Pizquierdo;
    private PanelColor Pcentral;
    //-------------- Componentes
    private ImagenEscalada Ieslogan, Ivaca;
    //-------------- Imágenes decorativas
    private ImagenEscalada IdecoracionIzquierda, IdecoracionDerecha;
    //-------------- Carrusel
    private CarruselImagenes carrusel;

//-------------- Periodo actual
    private String periodoActual;

//-------------- Temporizador del periodo
    private Timer temporizadorPeriodo;

    //-------------------- C O N S T R U C T O R -----------------
    public PantallaPrincipal() {
        setTitle("Menú Principal");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        // Quitar bordes y controles de Windows
        setUndecorated(true);
        
        inicializarComponentes();
        barraSuperior.setConfirmarMenu(false);

        setVisible(true);
    }

    public void inicializarComponentes() {

        periodoActual = obtenerPeriodoActual();

        inicializarPrincipal();
        inicializarArriba();
        inicializarCentro();
        inicializarAbajo();

        iniciarTemporizadorPeriodo();
    }

    //------------------------ P A N E L  P R I N C I P A L --------------------------
    public void inicializarPrincipal() {
        Pprincipal = new PanelFondo("/utiles/imagenes/fondos/fondo_A.png");
        //organizador
        Pprincipal.setLayout(new GridBagLayout());

        this.add(Pprincipal);
    }

    //---------------------- P A N E L  S U P E R I O R ----------------------
    public void inicializarArriba() {
        barraSuperior = new BarraSuperior();

        //---------------------- R E G L A S   D E L   P A N E L
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        // Ocupa todo el ancho
        gbc.gridwidth = 2;

        gbc.weightx = 1.0;

        // Altura de la barra
        gbc.weighty = 0.05;

        gbc.fill = GridBagConstraints.BOTH;

        Pprincipal.add(barraSuperior, gbc);
    }

    public void inicializarCentro() {
        //---------------------- P A N E L   C E N T R A L
        Pcentral = new PanelColor();

        Pcentral.setColorFondo(Colores.DURAZNO);
        Pcentral.setOpacidad(200);

        Pcentral.setLayout(new OverlayLayout(Pcentral));

        //---------------------- P A N E L   C O N T E N I D O
        JPanel Pcontenido = new JPanel();
        Pcontenido.setOpaque(false);
        Pcontenido.setLayout(new GridBagLayout());

        //---------------------- P A N E L   I Z Q U I E R D O
        Pizquierdo = new JPanel();
        Pizquierdo.setOpaque(false);
        Pizquierdo.setLayout(new GridBagLayout());

        //---------------------- E S L O G A N
        Ieslogan = new ImagenEscalada(
                "/utiles/imagenes/logotipo/eslogan-verde.png",
                350,
                150
        );

        Ieslogan.setAlineacion(
                SwingConstants.CENTER,
                SwingConstants.CENTER
        );

        GridBagConstraints gbcEslogan = new GridBagConstraints();

        gbcEslogan.gridx = 0;
        gbcEslogan.gridy = 0;

        gbcEslogan.weightx = 1.0;
        gbcEslogan.weighty = 0.35;

        gbcEslogan.anchor = GridBagConstraints.CENTER;
        gbcEslogan.fill = GridBagConstraints.NONE;

        Pizquierdo.add(Ieslogan, gbcEslogan);

        //---------------------- M A S C O T A
        Ivaca = new ImagenEscalada(
                "/utiles/imagenes/elementosGraficos/Moovita-mascota.png",
                350,
                350
        );

        Ivaca.setAlineacion(
                SwingConstants.CENTER,
                SwingConstants.BOTTOM
        );

        GridBagConstraints gbcVaca = new GridBagConstraints();

        gbcVaca.gridx = 0;
        gbcVaca.gridy = 1;

        gbcVaca.weightx = 1.0;
        gbcVaca.weighty = 0.65;

        gbcVaca.anchor = GridBagConstraints.SOUTH;
        gbcVaca.fill = GridBagConstraints.NONE;

        Pizquierdo.add(Ivaca, gbcVaca);

        //---------------------- P I Z Q U I E R D O   E N   C O N T E N I D O
        GridBagConstraints gbcIzquierdo = new GridBagConstraints();

        gbcIzquierdo.gridx = 0;
        gbcIzquierdo.gridy = 0;

        gbcIzquierdo.weightx = 0.40;
        gbcIzquierdo.weighty = 1.0;

        gbcIzquierdo.fill = GridBagConstraints.BOTH;

        Pcontenido.add(Pizquierdo, gbcIzquierdo);
        //---------------------- C A R R U S E L    D E   I M Á G E N E S -------------------

        String[] rutasCarrusel = obtenerImagenesCarrusel();

        carrusel = new CarruselImagenes(
                rutasCarrusel,
                4000
        );

        carrusel.setPreferredSize(new Dimension(650, 800));

        GridBagConstraints gbcCarrusel = new GridBagConstraints();

        gbcCarrusel.gridx = 1;
        gbcCarrusel.gridy = 0;

        gbcCarrusel.weightx = 0.60;
        gbcCarrusel.weighty = 1.0;

        gbcCarrusel.anchor = GridBagConstraints.CENTER;
        gbcCarrusel.fill = GridBagConstraints.NONE;

        Pcontenido.add(carrusel, gbcCarrusel);

        //---------------------- P A N E L   M A N C H A S
        JPanel Pmanchas = new JPanel(new BorderLayout());

        Pmanchas.setOpaque(false);

        //---------------------- M A N C H A   V E R D E
        IdecoracionIzquierda = new ImagenEscalada(
                "/utiles/imagenes/elementosGraficos/Mancha-verde-arriba-izquierda.png",
                350,
                230
        );

        IdecoracionIzquierda.setAlineacion(
                SwingConstants.LEFT,
                SwingConstants.TOP
        );

        Pmanchas.add(
                IdecoracionIzquierda,
                BorderLayout.NORTH
        );

        //---------------------- M A N C H A   R O J A
        IdecoracionDerecha = new ImagenEscalada(
                "/utiles/imagenes/elementosGraficos/Mancha-roja-abajo-derecha.png",
                350,
                230
        );

        IdecoracionDerecha.setAlineacion(
                SwingConstants.RIGHT,
                SwingConstants.BOTTOM
        );

        Pmanchas.add(
                IdecoracionDerecha,
                BorderLayout.SOUTH
        );

        //---------------------- C A P A S
        Pcentral.add(Pcontenido);
        Pcentral.add(Pmanchas);

        //---------------------- R E G L A S   D E L   P A N E L   C E N T R A L
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.gridwidth = 2;

        gbc.weightx = 1.0;
        gbc.weighty = 0.85;

        gbc.fill = GridBagConstraints.BOTH;

        Pprincipal.add(Pcentral, gbc);
    }

    //---------------------- P A N E L E S  I N F E R I O R E S ----------------------
    public void inicializarAbajo() {

        barraInferior = new BarraInferior(
                Colores.CORINTO,
                Colores.NARANJA
        );

        //---------------------- REGLAS
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 2;

        // Ocupa las dos columnas
        gbc.gridwidth = 2;

        gbc.weightx = 1.0;

        // 10% de altura total
        gbc.weighty = 0.10;

        gbc.fill = GridBagConstraints.BOTH;

        Pprincipal.add(barraInferior, gbc);
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

    //---------------------- O B T E N E R   P E R I O D O ----------------
    private String obtenerPeriodoActual() {

        if (Reloj.esManana()) {

            return "Manana";

        } else {

            return "TardeNoche";
        }
    }

    //---------------------- T E M P O R I Z A D O R   P E R I O D O ----------------
    private void iniciarTemporizadorPeriodo() {

        temporizadorPeriodo
                = new Timer(
                        60000,
                        e -> actualizarPeriodo()
                );

        temporizadorPeriodo.start();
    }

    //---------------------- A C T U A L I Z A R   P E R I O D O ----------------
    private void actualizarPeriodo() {

        String nuevoPeriodo
                = obtenerPeriodoActual();

        if (!nuevoPeriodo.equals(periodoActual)) {

            periodoActual = nuevoPeriodo;

            carrusel.setImagenes(
                    obtenerImagenesCarrusel()
            );
        }
    }

    @Override
    public void dispose() {

        if (temporizadorPeriodo != null) {

            temporizadorPeriodo.stop();
        }

        super.dispose();
    }
}
