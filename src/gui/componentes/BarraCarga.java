//------------------------ B A R R A   C A R G A  --------------------
package gui.componentes;

import gui.estilo.*;
import javax.swing.*;
import java.awt.*;

public class BarraCarga extends JComponent {

    //------------------ ATRIBUTOS -----------------
    //-------------- COLORES
    // Fondo de la barra
    private Color colorFondo = Colores.VERDE_OSCURO;

    // Color del progreso
    private Color colorProgreso = Colores.AMARILLO_FUERTE;

    // Color del borde
    private Color colorBorde = Colores.VERDE_CLARO;

    //-------------- BORDE
    private int grosorBorde = 2;
    private int radio = 15;

    //-------------- PROGRESO
    // Valor actual de la barra
    private int valor = 0;

    // Valor máximo
    private int valorMaximo = 100;

    //-------------- ALTURA
    private int altura = 20;

    //------------------ CONSTRUCTOR -----------------
    public BarraCarga() {

        // Transparencia
        setOpaque(false);

        // Tamaño
        setPreferredSize(
                new Dimension(300, altura)
        );
    }

    //                 C O L O R E S
    //--------------- Fondo ----------------
    public void setColorFondo(Color color) {

        this.colorFondo = color;
        repaint();
    }

    //--------------- Progreso ----------------
    public void setColorProgreso(Color color) {

        this.colorProgreso = color;
        repaint();
    }

    //--------------- Borde ----------------
    public void setColorBorde(Color color) {

        this.colorBorde = color;
        repaint();
    }

    //                  B O R D E
    public void setGrosorBorde(int grosor) {

        this.grosorBorde = grosor;
        repaint();
    }

    public void setRadio(int radio) {

        this.radio = radio;
        repaint();
    }

    //                P R O G R E S O
    public void setValor(int valor) {

        if (valor < 0) {
            valor = 0;
        }

        if (valor > valorMaximo) {
            valor = valorMaximo;
        }

        this.valor = valor;

        repaint();
    }

    public int getValor() {

        return valor;
    }

    public void setValorMaximo(int valorMaximo) {

        if (valorMaximo <= 0) {
            return;
        }

        this.valorMaximo = valorMaximo;

        // Evita que el valor actual supere el nuevo máximo
        if (valor > valorMaximo) {
            valor = valorMaximo;
        }

        repaint();
    }

    public int getValorMaximo() {

        return valorMaximo;
    }

    public void aumentarValor(int cantidad) {

        setValor(valor + cantidad);
    }

    //                  T A M A Ñ O
    public void setAncho(int ancho) {

        Dimension tamaño = getPreferredSize();

        setPreferredSize(
                new Dimension(
                        ancho,
                        tamaño.height
                )
        );

        revalidate();
        repaint();
    }

    public void setAltura(int altura) {

        this.altura = altura;

        Dimension tamaño = getPreferredSize();

        setPreferredSize(
                new Dimension(
                        tamaño.width,
                        altura
                )
        );

        revalidate();
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

        //                 F O N D O
        g2.setColor(colorFondo);

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                radio,
                radio
        );

        //              P R O G R E S O
        // Calcula cuánto ancho debe ocupar el progreso
        int anchoProgreso = (int) ((double) valor / valorMaximo
                * getWidth());

        // Evita dibujar si el progreso es 0
        if (anchoProgreso > 0) {

            g2.setColor(colorProgreso);

            g2.fillRoundRect(
                    0,
                    0,
                    anchoProgreso,
                    getHeight(),
                    radio,
                    radio
            );
        }

        //                  B O R D E
        g2.setColor(colorBorde);

        g2.setStroke(
                new BasicStroke(grosorBorde)
        );

        g2.drawRoundRect(
                grosorBorde / 2,
                grosorBorde / 2,
                getWidth() - grosorBorde,
                getHeight() - grosorBorde,
                radio,
                radio
        );

        g2.dispose();
    }

}
