//------------------------ I M A G E N   R E C O R T A D A ---------------------------
package gui.componentes;

import java.awt.*;
import javax.swing.*;

public class ImagenRecortada extends JPanel {

    //------------------ A T R I B U T O S -----------------
    // Imagen original
    private Image imagen;

    //------------------ C O N S T R U C T O R -----------------
    public ImagenRecortada(
            String ruta
    ) {

        // Cargar imagen
        imagen = new ImageIcon(
                getClass().getResource(ruta)
        ).getImage();

        // El panel no pinta su propio fondo
        setOpaque(false);
    }

    //------------------ P I N T A R   I M A G E N -----------------
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        //------------------ TAMAÑO ORIGINAL
        int anchoOriginal = imagen.getWidth(this);
        int altoOriginal = imagen.getHeight(this);

        //------------------ ESCALA
        // Math.max hace que la imagen cubra
        // completamente el panel.
        double escala = Math.max(
                (double) getWidth() / anchoOriginal,
                (double) getHeight() / altoOriginal
        );

        //------------------ NUEVO TAMAÑO
        int nuevoAncho = (int) (anchoOriginal * escala);
        int nuevoAlto = (int) (altoOriginal * escala);

        //------------------ CENTRAR IMAGEN
        int x = (getWidth() - nuevoAncho) / 2;
        int y = (getHeight() - nuevoAlto) / 2;

        //------------------ SUAVIZADO
        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );

        //------------------ DIBUJAR
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
}