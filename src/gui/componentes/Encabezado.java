//---------------------------------- E N C A B E Z A D O -----------------------
package gui.componentes;

import java.awt.*;
import javax.swing.*;
import gui.estilo.*;
import logica.*;

public class Encabezado extends JPanel {

    //------------------------  A T R I B U T O S ----------------
    //-------------- Botón regresar
    private BotonRegresar Bregresar;

    //-------------- Saludo
    private JLabel Lsaludo;

    //-------------- Sol / Luna
    private ImagenEscalada Lsoluna;

    //-------------- Imagen actual
    private String imagenActual;

    //-------------- Hora
    private JLabel Lhora;

    //-------------- Temporizador
    private Timer temporizador;

    //-------------------- C O N S T R U C T O R -----------------
    public Encabezado() {

        //---------------------- PANEL
        setBackground(
                Colores.BLANCO
        );

        setLayout(
                new GridBagLayout()
        );

        //---------------------- INICIALIZAR
        inicializarComponentes();

        //---------------------- RELOJ
        iniciarReloj();
    }

    private void inicializarComponentes() {

        inicializarBoton();
        inicializarSaludo();
        inicializarSol();
        inicializarHora();
    }

    private void inicializarBoton() {

        Bregresar = new BotonRegresar();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 0.10;
        gbc.weighty = 1.0;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        add(
                Bregresar,
                gbc
        );
    }

    private void inicializarSaludo() {

        Lsaludo = new JLabel(
                "¡Buenos días!"
        );

        Lsaludo.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lsaludo.setForeground(
                Colores.CORINTO
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 0;

        gbc.weightx = 0.60;
        gbc.weighty = 1.0;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;

        add(
                Lsaludo,
                gbc
        );
    }

    private void inicializarSol() {

        Lsoluna = new ImagenEscalada(
                "/utiles/imagenes/elementosGraficos/sol.png",
                40,
                40
        );

        Lsoluna.setAlineacion(
                SwingConstants.CENTER,
                SwingConstants.CENTER
        );

        imagenActual
                = "/utiles/imagenes/elementosGraficos/sol.png";

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 2;
        gbc.gridy = 0;

        gbc.weightx = 0.10;
        gbc.weighty = 1.0;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        add(
                Lsoluna,
                gbc
        );
    }

    private void inicializarHora() {

        Lhora = new JLabel();

        Lhora.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lhora.setForeground(
                Colores.CORINTO
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 3;
        gbc.gridy = 0;

        gbc.weightx = 0.20;
        gbc.weighty = 1.0;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;

        add(
                Lhora,
                gbc
        );
    }

    private void iniciarReloj() {

        temporizador = new Timer(
                1000,
                e -> actualizarHora()
        );

        actualizarHora();

        temporizador.start();
    }

    private void actualizarHora() {

        //---------------------- HORA ACTUAL
        java.time.LocalTime horaActual
                = Reloj.obtenerHoraActual();

        //---------------------- SALUDO E IMAGEN
        String saludo;
        String imagen;

        //---------------------- MAÑANA
        if (Reloj.esManana()) {

            saludo = "¡Buenos días!";

            imagen
                    = "/utiles/imagenes/elementosGraficos/sol.png";

            //---------------------- TARDE
        } else if (Reloj.esTarde()) {

            saludo = "¡Buenas tardes!";

            imagen
                    = "/utiles/imagenes/elementosGraficos/sol.png";

            //---------------------- NOCHE
        } else {

            saludo = "¡Buenas noches!";

            imagen
                    = "/utiles/imagenes/elementosGraficos/luna.png";
        }

        //---------------------- ACTUALIZAR SALUDO
        Lsaludo.setText(
                saludo
        );

        //---------------------- ACTUALIZAR IMAGEN
        if (!imagen.equals(imagenActual)) {

            Lsoluna.setImagen(
                    imagen
            );

            imagenActual = imagen;
        }

        //---------------------- FORMATO DE HORA
        String horaTexto = String.format(
                "%02d:%02d",
                horaActual.getHour(),
                horaActual.getMinute()
        );

        //---------------------- ACTUALIZAR HORA
        Lhora.setText(
                horaTexto
        );
    }

    //---------------------- D E T E N E R   R E L O J
    /**
     * Detiene el reloj del encabezado.
     *
     * Se llama al cerrar la ventana que lo contiene. Si el temporizador
     * siguiera vivo seguiría despertando una vez por segundo para poner
     * la hora, aunque nadie la estuviera viendo, y eso consume CPU de
     * forma innecesaria.
     */
    public void detenerReloj() {

        if (temporizador != null) {

            temporizador.stop();
        }
    }

    //---------------------- G E T T E R S
    public BotonRegresar getBregresar() {

        return Bregresar;
    }

}
