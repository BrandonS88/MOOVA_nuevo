//------------ B O R D E  R E D O N D E A D O -----------------
package gui.componentes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.AbstractBorder;

public class BordeRedondeado extends AbstractBorder {

    private Color color;
    private int grosor;
    private int radio;

    public BordeRedondeado(Color color, int grosor, int radio) {
        this.color = color;
        this.grosor = grosor;
        this.radio = radio;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(
                grosor + 2,
                grosor + 2,
                grosor + 2,
                grosor + 2
        );
    }

    @Override
    public void paintBorder(
            Component c,
            Graphics g,
            int x,
            int y,
            int width,
            int height
    ) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(color);

        g2.setStroke(
                new BasicStroke(grosor)
        );

        g2.drawRoundRect(
                x + grosor / 2,
                y + grosor / 2,
                width - grosor,
                height - grosor,
                radio,
                radio
        );

        g2.dispose();
    }
}