//---------------------- P A N T A L L A   P A G O ----------------------
package gui.cajero;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import gui.componentes.*;
import gui.estilo.*;
import modelo.*;

public class PantallaPago extends VentanaCompra {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Datos de la compra
    private Pedido pedido;
    private double descuento;

    //-------------- Porcentaje de impuesto (IVA)
    private static final double PORCENTAJE_IMPUESTO = 0.12;

    //-------------- Selector de factura
    private Boton Bnit;
    private Boton Bcf;
    private CampoTexto Cnit;

    //-------------- Totales
    private JLabel LsubtotalValor;
    private JLabel LimpuestoValor;
    private JLabel LtotalValor;

    //-------------- Botón pagar
    private Boton Bpagar;

    //-------------- Resumen (panel derecho)
    private PanelResumen Presumen;
    private JScrollPane scroll;

    //------------------------ C O N S T R U C T O R ----------------
    public PantallaPago(
            Pedido pedido,
            double descuento
    ) {

        super("Pago");

        this.pedido = pedido;
        this.descuento = descuento;

        inicializarContenido();
        configurarRegreso();

        mostrarVentana();
    }

    //---------------------- C O N T E N I D O ----------------
    private void inicializarContenido() {

        configurarTitulo(
                "Pago"
        );

        inicializarPanelIzquierdo();
        inicializarPanelDerecho();
    }

    //---------------------- P A N E L   I Z Q U I E R D O ----------------
    private void inicializarPanelIzquierdo() {

        Pizquierdo.setLayout(
                new GridBagLayout()
        );

        inicializarSelectorFactura();
        inicializarTotales();
        inicializarBotonPagar();
    }

    //---------------------- S E L E C T O R   D E   F A C T U R A ----------------
    private void inicializarSelectorFactura() {

        //---------------------- B O T O N E S
        JPanel Pbotones = new JPanel(
                new GridLayout(1, 2, 10, 0)
        );

        Pbotones.setOpaque(false);

        Bnit = new Boton("NIT");
        Bcf = new Boton("CF");

        Bnit.addActionListener(
                e -> seleccionarNit()
        );

        Bcf.addActionListener(
                e -> seleccionarConsumidorFinal()
        );

        Pbotones.add(Bnit);
        Pbotones.add(Bcf);

        GridBagConstraints gbcBotones = new GridBagConstraints();

        gbcBotones.gridx = 0;
        gbcBotones.gridy = 0;

        gbcBotones.weightx = 1.0;
        gbcBotones.weighty = 0.10;

        gbcBotones.anchor = GridBagConstraints.NORTH;
        gbcBotones.fill = GridBagConstraints.HORIZONTAL;

        gbcBotones.insets = new Insets(
                20, 20, 10, 20
        );

        Pizquierdo.add(
                Pbotones,
                gbcBotones
        );

        //---------------------- C A M P O   D E   T E X T O
        Cnit = new CampoTexto();

        // Por defecto inicia como Consumidor Final
        seleccionarConsumidorFinal();

        GridBagConstraints gbcCampo = new GridBagConstraints();

        gbcCampo.gridx = 0;
        gbcCampo.gridy = 1;

        gbcCampo.weightx = 1.0;
        gbcCampo.weighty = 0.10;

        gbcCampo.anchor = GridBagConstraints.NORTH;
        gbcCampo.fill = GridBagConstraints.HORIZONTAL;

        gbcCampo.insets = new Insets(
                0, 20, 10, 20
        );

        Pizquierdo.add(
                Cnit,
                gbcCampo
        );
    }

    //---------------------- S E L E C C I O N A R   N I T ----------------
    private void seleccionarNit() {

        Cnit.setText("");
        Cnit.setEditable(true);
        Cnit.setPlaceholder("1234567-A");

        resaltarBoton(Bnit, Bcf);
    }

//---------------------- S E L E C C I O N A R   C O N S U M I D O R   F I N A L ----------------
    private void seleccionarConsumidorFinal() {

        Cnit.setPlaceholder(null);
        Cnit.setText("Consumidor Final");
        Cnit.setEditable(false);

        resaltarBoton(Bcf, Bnit);
    }

//---------------------- R E S A L T A R   B O T Ó N   S E L E C C I O N A D O ----------------
    private void resaltarBoton(
            Boton seleccionado,
            Boton noSeleccionado
    ) {

        seleccionado.rojo();
        noSeleccionado.amarillo();
    }

    //---------------------- T O T A L E S ----------------
    private void inicializarTotales() {

        double subtotal = calcularSubtotal();
        double baseImponible = subtotal - descuento;
        double impuesto = baseImponible * PORCENTAJE_IMPUESTO;
        double total = baseImponible + impuesto;

        //---------------------- F O N D O   B L A N C O
        PanelColor Ptotales = new PanelColor();

        Ptotales.setColorFondo(
                Colores.BLANCO
        );

        Ptotales.setOpacidad(255);

        Ptotales.setRadio(20);

        Ptotales.setLayout(
                new BorderLayout()
        );

        //---------------------- C O N T E N I D O
        JPanel PcontenidoTotales = new JPanel(
                new GridBagLayout()
        );

        PcontenidoTotales.setOpaque(false);

        PcontenidoTotales.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 20, 15, 20
                )
        );

        LsubtotalValor = new JLabel();
        LimpuestoValor = new JLabel();
        LtotalValor = new JLabel();

        agregarFilaTotal(
                PcontenidoTotales, 0, "Subtotal", LsubtotalValor, Fuentes.NORMAL
        );

        agregarFilaTotal(
                PcontenidoTotales, 1, "Impuesto (12%)", LimpuestoValor, Fuentes.NORMAL
        );

        agregarFilaTotal(
                PcontenidoTotales, 2, "Total", LtotalValor, Fuentes.SUBTITULO
        );

        actualizarTotales(
                subtotal, impuesto, total
        );

        Ptotales.add(
                PcontenidoTotales,
                BorderLayout.CENTER
        );

        //---------------------- U B I C A C I Ó N   E N   P I Z Q U I E R D O
        GridBagConstraints gbcTotales = new GridBagConstraints();

        gbcTotales.gridx = 0;
        gbcTotales.gridy = 2;

        gbcTotales.weightx = 1.0;
        gbcTotales.weighty = 0.60;

        gbcTotales.anchor = GridBagConstraints.SOUTH;
        gbcTotales.fill = GridBagConstraints.HORIZONTAL;

        gbcTotales.insets = new Insets(
                0, 20, 10, 20
        );

        Pizquierdo.add(
                Ptotales,
                gbcTotales
        );
    }

    //---------------------- F I L A   D E   T O T A L ----------------
    private void agregarFilaTotal(
            JPanel panel,
            int fila,
            String etiqueta,
            JLabel valor,
            float tamanoFuente
    ) {

        JLabel Letiqueta = new JLabel(etiqueta);

        Letiqueta.setFont(
                Fuentes.LFL.deriveFont(tamanoFuente)
        );

        Letiqueta.setForeground(
                Colores.NEGRO
        );

        valor.setFont(
                Fuentes.LFL.deriveFont(tamanoFuente)
        );

        valor.setForeground(
                Colores.CORINTO
        );

        GridBagConstraints gbcEtiqueta = new GridBagConstraints();

        gbcEtiqueta.gridx = 0;
        gbcEtiqueta.gridy = fila;

        gbcEtiqueta.weightx = 1.0;

        gbcEtiqueta.anchor = GridBagConstraints.WEST;

        gbcEtiqueta.insets = new Insets(
                5, 0, 5, 10
        );

        panel.add(
                Letiqueta,
                gbcEtiqueta
        );

        GridBagConstraints gbcValor = new GridBagConstraints();

        gbcValor.gridx = 1;
        gbcValor.gridy = fila;

        gbcValor.anchor = GridBagConstraints.EAST;

        gbcValor.insets = new Insets(
                5, 0, 5, 0
        );

        panel.add(
                valor,
                gbcValor
        );
    }

    //---------------------- A C T U A L I Z A R   T O T A L E S ----------------
    private void actualizarTotales(
            double subtotal,
            double impuesto,
            double total
    ) {

        LsubtotalValor.setText(
                "Q." + String.format("%.2f", subtotal)
        );

        LimpuestoValor.setText(
                "Q." + String.format("%.2f", impuesto)
        );

        LtotalValor.setText(
                "Q." + String.format("%.2f", total)
        );
    }

    //---------------------- C A L C U L A R   S U B T O T A L ----------------
    private double calcularSubtotal() {

        double subtotal = 0.0;

        for (ItemPedido item
                : pedido.getItems()) {

            subtotal += item.obtenerPrecio();
        }

        return subtotal;
    }

    //---------------------- B O T Ó N   P A G A R ----------------
    private void inicializarBotonPagar() {

        Bpagar = new Boton("Pagar");

        Bpagar.rojo();

        Bpagar.addActionListener(
                e -> pagar()
        );

        GridBagConstraints gbcPagar = new GridBagConstraints();

        gbcPagar.gridx = 0;
        gbcPagar.gridy = 3;

        gbcPagar.weightx = 1.0;
        gbcPagar.weighty = 0.20;

        gbcPagar.anchor = GridBagConstraints.SOUTH;
        gbcPagar.fill = GridBagConstraints.HORIZONTAL;

        gbcPagar.insets = new Insets(
                10, 20, 20, 20
        );

        Pizquierdo.add(
                Bpagar,
                gbcPagar
        );
    }

    //---------------------- P A N E L   D E R E C H O ----------------
    private void inicializarPanelDerecho() {

        Presumen = new PanelResumen();
        Presumen.setMostrarBotonEditar(false);
        Presumen.setTitulo(
                "Resumen de tu pago"
        );

        for (Producto producto
                : pedido.obtenerProductos()) {

            Presumen.agregarProducto(
                    producto
            );
        }

        if (descuento > 0) {

            Presumen.agregarDetalle(
                    "Descuento: -Q." + String.format("%.2f", descuento)
            );
        }

        scroll = new JScrollPane(
                Presumen
        );

        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        JPanel PcontenedorScroll = new JPanel(
                new BorderLayout()
        );

        PcontenedorScroll.setOpaque(false);

        PcontenedorScroll.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 30, 30, 30
                )
        );

        PcontenedorScroll.add(
                scroll,
                BorderLayout.CENTER
        );

        Pderecho.add(
                PcontenedorScroll,
                BorderLayout.CENTER
        );
    }

//---------------------- P A G A R ----------------
    private void pagar() {

        double subtotal
                = calcularSubtotal();

        double baseImponible
                = subtotal - descuento;

        double impuesto
                = baseImponible
                * PORCENTAJE_IMPUESTO;

        double total
                = baseImponible
                + impuesto;

        PantallaMetodoPago ventanaMetodoPago
                = new PantallaMetodoPago(
                        total,
                        pedido,
                        descuento
                );

        this.dispose();
    }

//------------------------ R E G R E S A R ----------------
    private void configurarRegreso() {

        Bregresar.addActionListener(
                e -> {

                    new ResumenCombo(
                            pedido,
                            "Tu pedido"
                    );

                    dispose();
                }
        );
    }

}
