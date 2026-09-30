//---------------------------------- P R O D U C T O   M E N U -----------------------
package gui.cajero;

import gui.componentes.ImagenEscalada;
import gui.componentes.PanelColor;
import java.awt.*;
import javax.swing.*;
import gui.estilo.*;
import modelo.Producto;

public class ProductoMenu extends PanelColor {

    //------------------------  A T R I B U T O S ----------------
    //-------------- Producto
    private Producto producto;

    //-------------- Imagen
    private ImagenEscalada Iproducto;

    //-------------- Información
    private JLabel Lnombre;
    private JLabel Lprecio;

    //-------------- Listener
    private ProductoListener productoListener;

    //-------------------- C O N S T R U C T O R -----------------
    public ProductoMenu(
            Producto producto
    ) {

        this.producto = producto;

        //---------------------- PANEL
        setColorFondo(
                Colores.BLANCO
        );

        setRadio(
                20
        );

        setPreferredSize(
                new Dimension(
                        750,
                        130
                )
        );

        setLayout(
                new BorderLayout()
        );

        //---------------------- INICIALIZAR
        inicializarComponentes();
    }

    //---------------------- L I S T E N E R
    public interface ProductoListener {

        void productoSeleccionado(
                Producto producto
        );
    }

    //---------------------- S E T T E R   L I S T E N E R
    public void setProductoListener(
            ProductoListener listener
    ) {

        this.productoListener = listener;
    }

    //---------------------- I N I C I A L I Z A R
    private void inicializarComponentes() {

        inicializarImagen();

        inicializarInformacion();

        inicializarEvento();
    }

    //---------------------- I M A G E N
    private void inicializarImagen() {

        Iproducto = new ImagenEscalada(
                producto.getImagen(),
                120,
                100
        );

        Iproducto.setAlineacion(
                SwingConstants.CENTER,
                SwingConstants.CENTER
        );

        JPanel Pimagen = new JPanel(
                new GridBagLayout()
        );

        Pimagen.setOpaque(false);

        Pimagen.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        15,
                        0,
                        5
                )
        );

        Pimagen.add(
                Iproducto
        );

        add(
                Pimagen,
                BorderLayout.WEST
        );
    }

    //---------------------- I N F O R M A C I Ó N
    private void inicializarInformacion() {

        Lnombre = new JLabel(
                producto.getNombre()
        );

        Lprecio = new JLabel(
                String.format(
                        "Q%.2f",
                        producto.getPrecio()
                )
        );

        //---------------------- FUENTES
        Lnombre.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lprecio.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        //---------------------- COLORES
        Lnombre.setForeground(
                Colores.ROJO
        );

        Lprecio.setForeground(
                Colores.ROJO
        );

        //---------------------- PANEL INFORMACIÓN
        JPanel Pinformacion = new JPanel();

        Pinformacion.setOpaque(false);

        Pinformacion.setLayout(
                new BoxLayout(
                        Pinformacion,
                        BoxLayout.Y_AXIS
                )
        );

        Pinformacion.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        10,
                        15,
                        15
                )
        );

        Pinformacion.add(
                Lnombre
        );

        Pinformacion.add(
                Lprecio
        );

        add(
                Pinformacion,
                BorderLayout.CENTER
        );
    }

    //---------------------- E V E N T O
    private void inicializarEvento() {

        addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e
                    ) {

                        if (productoListener != null) {

                            productoListener.productoSeleccionado(
                                    producto
                            );
                        }
                    }
                }
        );
    }
}