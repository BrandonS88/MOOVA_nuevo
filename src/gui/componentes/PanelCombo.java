//---------------------------------- P A N E L   C O M B O -----------------------
package gui.componentes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gui.estilo.*;

public class PanelCombo extends PanelColor {

    //------------------------ A T R I B U T O S ----------------

    private JLabel Lcontenido;


    //-------------------- C O N S T R U C T O R -----------------

    public PanelCombo(
        String contenido
    ) {

        //---------------------- C O L O R
        setColorFondo(
            Colores.AMARILLO
        );

        setRadio(
            15
        );

        //---------------------- T A M A Ñ O   F I J O
        setPreferredSize(
            new Dimension(
                300,
                60
            )
        );

        setMinimumSize(
            new Dimension(
                300,
                60
            )
        );

        setMaximumSize(
            new Dimension(
                300,
                60
            )
        );

        //---------------------- L A Y O U T
        setLayout(
            new GridBagLayout()
        );

        inicializarContenido(
            contenido
        );
    }


    //---------------------- C O N T E N I D O ----------------

    private void inicializarContenido(
        String contenido
    ) {

        Lcontenido =
            new JLabel(
                contenido,
                SwingConstants.CENTER
            );

        Lcontenido.setFont(
            Fuentes.VC.deriveFont(
                Fuentes.PEQUENO
            )
        );

        //---------------------- C O L O R   T E X T O
        Lcontenido.setForeground(
            Colores.VERDE_OSCURO
        );

        add(
            Lcontenido
        );
    }


    //---------------------- E V E N T O ----------------

    public void setMouseListener(
        MouseListener listener
    ) {

        addMouseListener(
            listener
        );

        Lcontenido.addMouseListener(
            listener
        );
    }
}
