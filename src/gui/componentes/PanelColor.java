//------------------- P A N E L  C O L O R -------------------
package gui.componentes;

//----------------- IMPORTS
import javax.swing.JPanel;
import java.awt.*;

public class PanelColor extends JPanel {

    //------------------ ATRIBUTOS -----------------

    //-------------- FONDO
    private Color colorFondo = Color.WHITE;

    //-------------- OPACIDAD
    private int opacidad = 255;

    //-------------- BORDE
    private Color colorBorde = null;
    private int grosorBorde = 0;
    private int radio = 0;


    //------------------ CONSTRUCTOR -----------------

    public PanelColor() {

        super();

        // Transparencia
        setOpaque(false);
    }


    //                 F O N D O

    public void setColorFondo(Color color) {

        this.colorFondo = color;
        repaint();
    }

    //                O P A C I D A D

    public void setOpacidad(int opacidad) {

        // Mantiene el valor entre 0 y 255
        this.opacidad = Math.max(
                0,
                Math.min(255, opacidad)
        );

        repaint();
    }

    //                  B O R D E

    public void setColorBorde(Color color) {

        this.colorBorde = color;
        repaint();
    }


    public void setGrosorBorde(int grosor) {

        this.grosorBorde = grosor;
        repaint();
    }


    public void setRadio(int radio) {

        this.radio = radio;
        repaint();
    }

    //                  P I N T A D O

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // Antialiasing
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );


        //----------------- COLOR CON OPACIDAD

        Color color = new Color(
                colorFondo.getRed(),
                colorFondo.getGreen(),
                colorFondo.getBlue(),
                opacidad
        );

        g2.setColor(color);


        //----------------- FONDO

        if (radio > 0) {

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    radio,
                    radio
            );

        } else {

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight()
            );
        }


        //----------------- BORDE

        if (colorBorde != null && grosorBorde > 0) {

            g2.setColor(colorBorde);

            g2.setStroke(
                    new BasicStroke(grosorBorde)
            );

            if (radio > 0) {

                g2.drawRoundRect(
                        grosorBorde / 2,
                        grosorBorde / 2,
                        getWidth() - grosorBorde - 1,
                        getHeight() - grosorBorde - 1,
                        radio,
                        radio
                );

            } else {

                g2.drawRect(
                        grosorBorde / 2,
                        grosorBorde / 2,
                        getWidth() - grosorBorde - 1,
                        getHeight() - grosorBorde - 1
                );
            }
        }


        g2.dispose();
    }
}