//---------------------- V E N T A N A   D A T O S ----------------------
package gui.ventanitas;

import java.awt.*;
import javax.swing.*;

import gui.componentes.*;
import gui.estilo.*;

public class VentanaDatos extends JDialog {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Fondo
    protected PanelFondo Pfondo;

    //-------------- Capa transparente sobre toda la imagen
    protected PanelColor Pcapa;

    //-------------- Panel transparente del contenido
    protected PanelColor Pcontenido;

    //-------------- Botón regresar
    protected BotonRegresar Bregresar;

    //-------------------- C O N S T R U C T O R -----------------
    public VentanaDatos(
            Window padre,
            String titulo
    ) {

        super(
                padre,
                titulo,
                ModalityType.APPLICATION_MODAL
        );

        //----------------- J D I A L O G
        setDefaultCloseOperation(
                JDialog.DISPOSE_ON_CLOSE
        );

        setSize(
                650,
                550
        );

        setLocationRelativeTo(padre);

        setResizable(false);

        //----------------- I N I C I A R
        inicializarComponentes();
    }

    //----------- I N I C I A L I Z A R   C O M P O N E N T E S ----------------
    private void inicializarComponentes() {

        inicializarFondo();
        inicializarCapa();
        inicializarContenido();
    }

    //----------------------- F O N D O
    private void inicializarFondo() {

        Pfondo = new PanelFondo(
                "/utiles/imagenes/fondos/fondo_A.png"
        );

        Pfondo.setLayout(
                new GridBagLayout()
        );

        setContentPane(Pfondo);
    }

    //----------------------- C A P A   S O B R E   I M A G E N
    private void inicializarCapa() {

        Pcapa = new PanelColor();

        Pcapa.setColorFondo(
                Colores.DURAZNO
        );

        Pcapa.setOpacidad(190);

        Pcapa.setRadio(0);

        Pcapa.setLayout(
                new GridBagLayout()
        );

        //---------------- A G R E G A R   C A P A
        GridBagConstraints gbcCapa
                = new GridBagConstraints();

        gbcCapa.gridx = 0;
        gbcCapa.gridy = 0;

        gbcCapa.weightx = 1.0;
        gbcCapa.weighty = 1.0;

        gbcCapa.fill
                = GridBagConstraints.BOTH;

        Pfondo.add(
                Pcapa,
                gbcCapa
        );
    }

    //----------------------- C O N T E N I D O
    private void inicializarContenido() {

        Pcontenido = new PanelColor();

        //---------------- F O N D O   T R A N S P A R E N T E
        Pcontenido.setColorFondo(
                Colores.AMARILLO
        );

        Pcontenido.setOpacidad(190);

        Pcontenido.setRadio(25);

        Pcontenido.setLayout(
                new GridBagLayout()
        );

        //---------------- B O T O N   R E G R E S A R
        Bregresar = new BotonRegresar();

        Bregresar.addActionListener(
                e -> retroceder()
        );

        GridBagConstraints gbcRegresar
                = new GridBagConstraints();

        gbcRegresar.gridx = 0;
        gbcRegresar.gridy = 0;

        gbcRegresar.gridwidth = 2;

        gbcRegresar.weightx = 1.0;

        gbcRegresar.anchor
                = GridBagConstraints.NORTHWEST;

        gbcRegresar.insets
                = new Insets(
                        15, 15, 0, 0
                );

        Pcontenido.add(
                Bregresar,
                gbcRegresar
        );

        //---------------- A G R E G A R   C O N T E N I D O
        GridBagConstraints gbcContenido
                = new GridBagConstraints();

        gbcContenido.gridx = 0;
        gbcContenido.gridy = 0;

        gbcContenido.weightx = 0.75;
        gbcContenido.weighty = 1.0;

        gbcContenido.fill
                = GridBagConstraints.BOTH;

        gbcContenido.insets
                = new Insets(
                        20, 90, 20, 90
                );

        Pcapa.add(
                Pcontenido,
                gbcContenido
        );
    }

    //---------------------- R E T R O C E D E R
    private void retroceder() {
        dispose();
    }
}
