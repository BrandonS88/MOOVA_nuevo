//------------------------ I M A G E N   E S C A L A D A ---------------------------
package gui.componentes;

import java.awt.*;
import javax.swing.*;

public class ImagenEscalada extends JLabel {

    //------------------ ATRIBUTOS -----------------
    // Imagen original
    private Image imagen;

    // Tamaño máximo
    private int anchoMaximo;
    private int altoMaximo;

    //Alineación
    private int alineacionHorizontal;
    private int alineacionVertical;

    //------------------ CONSTRUCTOR -----------------
    public ImagenEscalada(
            String ruta,
            int anchoMaximo,
            int altoMaximo
    ) {

        java.net.URL recurso
                = getClass().getResource(ruta);

        if (recurso == null) {

            throw new IllegalArgumentException(
                    "No se encontró la imagen: " + ruta
            );
        }

        imagen = new ImageIcon(
                recurso
        ).getImage();

        // Guardar tamaño máximo
        this.anchoMaximo = anchoMaximo;
        this.altoMaximo = altoMaximo;

        // Por defecto: centrada
        alineacionHorizontal = SwingConstants.CENTER;
        alineacionVertical = SwingConstants.CENTER;

        // El JLabel no necesita pintar su fondo
        setOpaque(false);

        // Tamaño preferido del componente
        setPreferredSize(
                new Dimension(anchoMaximo, altoMaximo)
        );
    }

    //------------------ PINTAR IMAGEN -----------------
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // Tamaño original
        int anchoOriginal = imagen.getWidth(this);
        int altoOriginal = imagen.getHeight(this);

        // Mantener proporción
        double escala = Math.min(
                (double) anchoMaximo / anchoOriginal,
                (double) altoMaximo / altoOriginal
        );

        // Nuevo tamaño
        int nuevoAncho = (int) (anchoOriginal * escala);
        int nuevoAlto = (int) (altoOriginal * escala);

        // Suavizado
        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );

        //------------------ POSICIÓN HORIZONTAL
        int x;

        if (alineacionHorizontal == SwingConstants.LEFT) {

            x = 0;

        } else if (alineacionHorizontal == SwingConstants.RIGHT) {

            x = getWidth() - nuevoAncho;

        } else {

            x = (getWidth() - nuevoAncho) / 2;
        }

        //------------------ POSICIÓN VERTICAL
        int y;

        if (alineacionVertical == SwingConstants.TOP) {

            y = 0;

        } else if (alineacionVertical == SwingConstants.BOTTOM) {

            y = getHeight() - nuevoAlto;

        } else {

            y = (getHeight() - nuevoAlto) / 2;
        }

        // Dibujar
        g2.drawImage(
                imagen,
                x,
                y,
                nuevoAncho,
                nuevoAlto,
                this
        );

        g2.dispose();
    }

    public void setAlineacion(
            int horizontal,
            int vertical
    ) {
        this.alineacionHorizontal = horizontal;
        this.alineacionVertical = vertical;

        repaint();
    }

    public void setImagen(String ruta) {

        java.net.URL recurso
                = getClass().getResource(ruta);

        if (recurso == null) {
            throw new IllegalArgumentException(
                    "No se encontró la imagen: " + ruta
            );
        }

        imagen = new ImageIcon(
                recurso
        ).getImage();

        repaint();
    }
}
