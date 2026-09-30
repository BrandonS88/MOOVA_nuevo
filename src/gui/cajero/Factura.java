//------------------- F A C T U R A-----------------
package gui.cajero;

import java.awt.*;
import javax.swing.*;
import gui.componentes.*;
import logica.Reloj;
import modelo.*;

public class Factura extends VentanaCompra {

    //------------------------ A T R I B U T O S ----------------
    private JScrollPane scroll;
    private PanelFactura Pfactura;

    private Boton Bfinalizar;
    private CarruselImagenes carrusel;

    //-------------- Datos de la compra
    private double totalAPagar;
    private Pedido pedido;
    private double descuento;

//------------------------ C O N S T R U C T O R ----------------
    public Factura(
            double totalAPagar,
            Pedido pedido,
            double descuento
    ) {

        super("Factura");

        this.totalAPagar = totalAPagar;
        this.pedido = pedido;
        this.descuento = descuento;

        configurarTitulo(
                "Factura"
        );

        inicializarContenido();
        configurarRegreso();

        mostrarVentana();
    }

    //------------------------ C O N T E N I D O ----------------
    private void inicializarContenido() {

        inicializarPanelFactura();
        inicializarBotonFinalizar();
        inicializarCarrusel();
    }

    //------------------------ C A R R U S E L ----------------
    private void inicializarCarrusel() {

        String[] rutasCarrusel = obtenerImagenesCarrusel();

        carrusel = new CarruselImagenes(
                rutasCarrusel,
                4000
        );

        Pizquierdo.setLayout(
                new BorderLayout()
        );

        Pizquierdo.add(
                carrusel,
                BorderLayout.CENTER
        );
    }

    //---------------------- I M A G E N E S   C A R R U S E L ----------------
    private String[] obtenerImagenesCarrusel() {

        if (Reloj.esManana()) {

            return new String[]{
                "/utiles/imagenes/fotografias/Spreen_PantallaPrincipal.png",
                "/utiles/imagenes/fotografias/Auron_PantallaPrincipal.png",
                "/utiles/imagenes/fotografias/Mike-Timba_PantallaPrincipal.png"
            };

        } else {

            return new String[]{
                "/utiles/imagenes/fotografias/Ran_PantallaPrincipal.png",
                "/utiles/imagenes/fotografias/Artux_PantallaPrincipal.png",
                "/utiles/imagenes/fotografias/Willy-Rubius_PantallaPrincipal.png"
            };
        }
    }

    //------------------------ P A N E L   F A C T U R A ----------------
    private void inicializarPanelFactura() {

        Pfactura = new PanelFactura();

        Pfactura.mostrarFacturaEjemplo();

        scroll
                = new JScrollPane(Pfactura);

        scroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scroll.setOpaque(false);

        scroll.getViewport().setOpaque(false);

        //---------------------- C O N T E N E D O R   ( centra sin estirar )
        JPanel Pcontenedor
                = new JPanel(new GridBagLayout());

        Pcontenedor.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;

        Pcontenedor.add(
                scroll,
                gbc
        );

        Pderecho.add(
                Pcontenedor,
                BorderLayout.CENTER
        );
    }

    //------------------------ B O T Ó N   F I N A L I Z A R ----------------
    private void inicializarBotonFinalizar() {

        Bfinalizar
                = new Boton("Finalizar");

        Bfinalizar.verde();

        JPanel Pboton
                = new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                0,
                                15
                        )
                );

        Pboton.setOpaque(false);

        Pboton.add(Bfinalizar);

        Pderecho.add(
                Pboton,
                BorderLayout.SOUTH
        );

        //---------------------- A C C I Ó N   F I N A L I Z A R
        Bfinalizar.addActionListener(
                e -> {
                    new PantallaPrincipal();
                    dispose();
                }
        );
    }

//------------------------ R E G R E S A R ----------------
    private void configurarRegreso() {

        Bregresar.addActionListener(
                e -> {

                    new PantallaMetodoPago(
                            totalAPagar,
                            pedido,
                            descuento
                    );

                    dispose();
                }
        );
    }

}
