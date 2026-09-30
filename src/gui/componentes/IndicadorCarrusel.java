//---------------------------------- C A R R U S E L   I M A G E N E S -----------------------
package gui.componentes;

import javax.swing.*;
import java.awt.*;
import gui.estilo.*;

public class IndicadorCarrusel extends JPanel {
    //------------------------ A T R I B U T O S ----------------

    //-------------------- Color del indicador
    private Color color;

    //-------------------- Tamaño del círculo
    private int tamano;

    //-------------------- C O N S T R U C T O R -----------------
    public IndicadorCarrusel() {

        color = Colores.AMARILLO;
        tamano = 10;

        setOpaque(false);

        actualizarTamano();
    }

    //-------------------- CAMBIAR COLOR
    public void setColor(Color color) {

        this.color = color;

        repaint();
    }

    //-------------------- CAMBIAR TAMAÑO
    public void setTamano(int tamano) {

        if (tamano <= 0) {

            throw new IllegalArgumentException(
                    "El tamaño debe ser mayor que cero."
            );
        }

        this.tamano = tamano;

        actualizarTamano();

        repaint();
    }

    //-------------------- ACTUALIZAR TAMAÑO
    private void actualizarTamano() {

        setPreferredSize(
                new Dimension(
                        tamano,
                        tamano
                )
        );
    }

  //-------------------- P I N T A R
@Override
protected void paintComponent(Graphics g) {

    super.paintComponent(g);

    Graphics2D g2 =
            (Graphics2D) g.create();

    g2.setColor(color);

    g2.fillOval(
            0,
            0,
            tamano,
            tamano
    );

    g2.dispose();
}
}
