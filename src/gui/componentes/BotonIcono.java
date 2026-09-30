//---------------------- B O T O N   I C O N O ----------------------
package gui.componentes;

import java.awt.*;
import javax.swing.*;

public class BotonIcono extends JButton {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Imagen
    private Image imagen;

    //------------------------ C O N S T R U C T O R ----------------
    public BotonIcono(
            String rutaImagen
    ) {

        inicializarBoton(
                rutaImagen
        );
    }

    //---------------------- I N I C I A L I Z A R ----------------
    private void inicializarBoton(
            String rutaImagen
    ) {

        //---------------------- I M A G E N
        java.net.URL recurso
                = getClass().getResource(
                        rutaImagen
                );

        if (recurso == null) {

            throw new IllegalArgumentException(
                    "No se encontró la imagen: " + rutaImagen
            );
        }

        imagen = new ImageIcon(
                recurso
        ).getImage();

        //---------------------- E S T I L O
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);

        setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        //---------------------- T A M A Ñ O
        setPreferredSize(new Dimension(45, 45));
        setMinimumSize(new Dimension(45, 45));
        setMaximumSize(new Dimension(45, 45));
    }

    //---------------------- P I N T A R ----------------
    @Override
    protected void paintComponent(
            Graphics g
    ) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        int anchoOriginal = imagen.getWidth(this);
        int altoOriginal = imagen.getHeight(this);

        int anchoMaximo = 25;
        int altoMaximo = 25;

        double escala = Math.min(
                (double) anchoMaximo / anchoOriginal,
                (double) altoMaximo / altoOriginal
        );

        int nuevoAncho = (int) (anchoOriginal * escala);
        int nuevoAlto = (int) (altoOriginal * escala);

        int x = (getWidth() - nuevoAncho) / 2;
        int y = (getHeight() - nuevoAlto) / 2;

        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );

        g2.drawImage(
                imagen,
                x, y,
                nuevoAncho, nuevoAlto,
                this
        );

        g2.dispose();
    }
}