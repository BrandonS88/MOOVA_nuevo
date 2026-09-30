//---------------------------------- B O T O N   R E G R E S A R -----------------------
package gui.componentes;

import java.awt.*;
import javax.swing.*;
import gui.estilo.*;

public class BotonRegresar extends JButton {

    //------------------------ A T R I B U T O S ----------------

    // Colores
    private Color colorCirculo;
    private Color colorTriangulo;
    private Color colorCirculoHover;
    private Color colorTrianguloHover;

    // Tamaño
    private int diametro;
    private int tamanoTriangulo;

    public BotonRegresar() {

        //----------- Configuración
        setPreferredSize(
                new Dimension(
                        60,
                        60
                )
        );

        setMinimumSize(
                new Dimension(
                        60,
                        60
                )
        );

        setMaximumSize(
                new Dimension(
                        60,
                        60
                )
        );

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        //----------- Colores
        colorCirculo = Colores.ROJO;
        colorTriangulo = Colores.AMARILLO;

        colorCirculoHover = Colores.AMARILLO_FUERTE;
        colorTrianguloHover = Colores.ROJO;

        //----------- Tamaños
        diametro = 50;
        tamanoTriangulo = 18;

        //----------- Hover
        addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {
                        repaint();
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {
                        repaint();
                    }
                }
        );
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        //----------- Suavizado
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        //----------- Determinar estado
        boolean hover = getModel().isRollover();

        Color fondo = hover
                ? colorCirculoHover
                : colorCirculo;

        Color triangulo = hover
                ? colorTrianguloHover
                : colorTriangulo;

        //----------- Posición del círculo
        int x = (getWidth() - diametro) / 2;
        int y = (getHeight() - diametro) / 2;

        //----------- Dibujar círculo
        g2.setColor(fondo);

        g2.fillOval(
                x,
                y,
                diametro,
                diametro
        );

        //----------- Dibujar triángulo
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;

        int mitad = tamanoTriangulo / 2;

        Polygon trianguloIzquierda = new Polygon();

        trianguloIzquierda.addPoint(
                centroX - mitad,
                centroY
        );

        trianguloIzquierda.addPoint(
                centroX + mitad,
                centroY - mitad
        );

        trianguloIzquierda.addPoint(
                centroX + mitad,
                centroY + mitad
        );

        g2.setColor(triangulo);

        g2.fillPolygon(
                trianguloIzquierda
        );

        g2.dispose();
    }
}