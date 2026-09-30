package gui.componentes;

import java.awt.*;
import javax.swing.*;

public class PanelFondo extends JPanel {

    //------------------------ A T R I B U T O S ----------------
    
    private Image imagen;


    //---------------------- C O N S T R U C T O R -------------

    public PanelFondo(String ruta) {

        imagen = new ImageIcon(
                getClass().getResource(ruta)
        ).getImage();

        setOpaque(false);
    }


    //-------------------- P A I N T C O M P O N E N T ---------

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (imagen != null) {

            Graphics2D g2 = (Graphics2D) g.create();

            int anchoPanel = getWidth();
            int altoPanel = getHeight();

            int anchoImagen = imagen.getWidth(this);
            int altoImagen = imagen.getHeight(this);

            // Escala necesaria para llenar TODO el panel
            double escala = Math.max(
                    (double) anchoPanel / anchoImagen,
                    (double) altoPanel / altoImagen
            );

            int nuevoAncho = (int) (anchoImagen * escala);
            int nuevoAlto = (int) (altoImagen * escala);

            // Centrar la imagen
            int x = (anchoPanel - nuevoAncho) / 2;
            int y = (altoPanel - nuevoAlto) / 2;

            // Suavizado de la imagen
            g2.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );

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
}