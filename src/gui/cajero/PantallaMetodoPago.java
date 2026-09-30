//---------------------- P A N T A L L A   M E T O D O   P A G O ----------------------
package gui.cajero;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;
import gui.componentes.*;
import gui.estilo.*;
import gui.ventanitas.VentanaDatosRegalo;
import gui.ventanitas.VentanaDatosTarjeta;
import java.util.List;
import modelo.*;

public class PantallaMetodoPago extends VentanaCompra {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Datos del pago
    private double totalAPagar;
    private Map<String, Double> metodosAplicados;

    private static final int MAXIMO_METODOS = 3;

    //-------------- Selector de método
    private Boton Befectivo;
    private Boton Bcredito;
    private Boton Bdebito;
    private Boton Bregalo;
    private String metodoSeleccionado;

    //-------------- Monto
    private CampoTexto Cmonto;
    private Boton Bagregar;

    //-------------- Totales
    private JLabel LtotalValor;
    private JLabel LpagadoValor;
    private JLabel LvueltoValor;

    //-------------- Continuar
    private Boton Bcontinuar;

    //-------------- Métodos aplicados (panel derecho)
    private PanelMetodosPago Pmetodos;
    private JScrollPane scroll;

    //-------------- Datos de la compra
    private Pedido pedido;
    private double descuento;

//------------------------ C O N S T R U C T O R ----------------
    public PantallaMetodoPago(
            double totalAPagar,
            Pedido pedido,
            double descuento
    ) {

        super("Método de pago");

        this.totalAPagar = totalAPagar;
        this.pedido = pedido;
        this.descuento = descuento;

        this.metodosAplicados
                = new LinkedHashMap<>();

        inicializarContenido();
        configurarRegreso();

        mostrarVentana();
    }

    //---------------------- C O N T E N I D O ----------------
    private void inicializarContenido() {

        configurarTitulo("Método de pago");

        inicializarPanelIzquierdo();
        inicializarPanelDerecho();
    }

    //---------------------- P A N E L   I Z Q U I E R D O ----------------
    private void inicializarPanelIzquierdo() {

        Pizquierdo.setLayout(new GridBagLayout());

        inicializarSelectorMetodo();
        inicializarBotonContinuar();
        inicializarTotales();
        actualizarEstadoBotones();
    }

    //---------------------- S E L E C T O R   D E   M É T O D O ----------------
    private void inicializarSelectorMetodo() {

        JPanel Pbotones = new JPanel(new GridLayout(2, 2, 10, 10));
        Pbotones.setOpaque(false);

        Befectivo = new Boton("Efectivo");
        Bcredito = new Boton("Crédito");
        Bdebito = new Boton("Débito");
        Bregalo = new Boton("Regalo");

        Befectivo.addActionListener(e -> seleccionarMetodo("Efectivo", Befectivo));
        Bcredito.addActionListener(e -> seleccionarMetodo("Tarjeta de crédito", Bcredito));
        Bdebito.addActionListener(e -> seleccionarMetodo("Tarjeta de débito", Bdebito));
        Bregalo.addActionListener(e -> seleccionarMetodo("Tarjeta de regalo", Bregalo));

        Pbotones.add(Befectivo);
        Pbotones.add(Bcredito);
        Pbotones.add(Bdebito);
        Pbotones.add(Bregalo);

        GridBagConstraints gbcBotones = new GridBagConstraints();

        gbcBotones.gridx = 0;
        gbcBotones.gridy = 0;

        gbcBotones.weightx = 1.0;
        gbcBotones.weighty = 0.20;

        gbcBotones.anchor = GridBagConstraints.NORTH;
        gbcBotones.fill = GridBagConstraints.HORIZONTAL;

        gbcBotones.insets = new Insets(20, 20, 10, 20);

        Pizquierdo.add(Pbotones, gbcBotones);

        //---------------------- C A M P O   M O N T O
        Cmonto = new CampoTexto();
        Cmonto.setPlaceholder("Monto");

        GridBagConstraints gbcCampo = new GridBagConstraints();

        gbcCampo.gridx = 0;
        gbcCampo.gridy = 1;

        gbcCampo.weightx = 1.0;
        gbcCampo.weighty = 0.08;

        gbcCampo.anchor = GridBagConstraints.NORTH;
        gbcCampo.fill = GridBagConstraints.HORIZONTAL;

        gbcCampo.insets = new Insets(0, 20, 10, 20);

        Pizquierdo.add(Cmonto, gbcCampo);

        //---------------------- B O T Ó N   A G R E G A R
        Bagregar = new Boton("Agregar");
        Bagregar.verde();

        Bagregar.addActionListener(e -> agregarMetodo());

        GridBagConstraints gbcAgregar = new GridBagConstraints();

        gbcAgregar.gridx = 0;
        gbcAgregar.gridy = 2;

        gbcAgregar.weightx = 1.0;
        gbcAgregar.weighty = 0.08;

        gbcAgregar.anchor = GridBagConstraints.NORTH;
        gbcAgregar.fill = GridBagConstraints.HORIZONTAL;

        gbcAgregar.insets = new Insets(0, 20, 10, 20);

        Pizquierdo.add(Bagregar, gbcAgregar);
    }

    //---------------------- S E L E C C I O N A R   M É T O D O ----------------
    private void seleccionarMetodo(String metodo, Boton boton) {

        metodoSeleccionado = metodo;

        Befectivo.amarillo();
        Bcredito.amarillo();
        Bdebito.amarillo();
        Bregalo.amarillo();

        boton.rojo();
    }

    //---------------------- A G R E G A R   M É T O D O ----------------
    private void agregarMetodo() {

        if (metodoSeleccionado == null) {
            return;
        }

        double monto;

        try {

            monto = Double.parseDouble(
                    Cmonto.getText().trim()
            );

        } catch (NumberFormatException e) {

            return;
        }

        if (monto <= 0) {
            return;
        }

        //---------------- D A T O S   D E   T A R J E T A
        if (metodoSeleccionado.equals("Tarjeta de crédito")
                || metodoSeleccionado.equals("Tarjeta de débito")) {

            VentanaDatosTarjeta ventana
                    = new VentanaDatosTarjeta(this);

            // Si el usuario regresó, no se agrega el método
            if (!ventana.fueAceptado()) {

                return;
            }
        }

        //---------------- C Ó D I G O   D E   R E G A L O
        if (metodoSeleccionado.equals("Tarjeta de regalo")) {

            VentanaDatosRegalo ventana
                    = new VentanaDatosRegalo(this);

            // Si el usuario regresó, no se agrega el método
            if (!ventana.fueAceptado()) {

                return;
            }
        }

        //---------------- G U A R D A R   M É T O D O
        metodosAplicados.put(
                metodoSeleccionado,
                monto
        );

        metodoSeleccionado = null;

        Befectivo.amarillo();
        Bcredito.amarillo();
        Bdebito.amarillo();
        Bregalo.amarillo();

        Cmonto.setText("");

        actualizarListaMetodos();
        actualizarTotales();
        actualizarEstadoBotones();
    }

    //---------------------- E L I M I N A R   M É T O D O ----------------
    private void eliminarMetodo(String metodo) {

        metodosAplicados.remove(metodo);

        actualizarListaMetodos();
        actualizarTotales();
        actualizarEstadoBotones();
    }

    //---------------------- A C T U A L I Z A R   L I S T A ----------------
    private void actualizarListaMetodos() {

        Pmetodos.limpiar();

        for (Map.Entry<String, Double> entrada : metodosAplicados.entrySet()) {

            Pmetodos.agregarMetodo(
                    entrada.getKey(),
                    entrada.getValue()
            );
        }
    }

    //---------------------- E S T A D O   D E   B O T O N E S ----------------
    private void actualizarEstadoBotones() {

        boolean limiteAlcanzado
                = metodosAplicados.size() >= MAXIMO_METODOS;

        Befectivo.setEnabled(
                !metodosAplicados.containsKey("Efectivo") && !limiteAlcanzado
        );

        Bcredito.setEnabled(
                !metodosAplicados.containsKey("Tarjeta de crédito") && !limiteAlcanzado
        );

        Bdebito.setEnabled(
                !metodosAplicados.containsKey("Tarjeta de débito") && !limiteAlcanzado
        );

        Bregalo.setEnabled(
                !metodosAplicados.containsKey("Tarjeta de regalo") && !limiteAlcanzado
        );
    }

    //---------------------- T O T A L E S ----------------
    private void inicializarTotales() {

        PanelColor Ptotales = new PanelColor();

        Ptotales.setColorFondo(Colores.BLANCO);
        Ptotales.setOpacidad(255);
        Ptotales.setRadio(20);
        Ptotales.setLayout(new BorderLayout());

        JPanel PcontenidoTotales = new JPanel(new GridBagLayout());
        PcontenidoTotales.setOpaque(false);

        PcontenidoTotales.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        LtotalValor = new JLabel();
        LpagadoValor = new JLabel();
        LvueltoValor = new JLabel();

        agregarFilaTotal(PcontenidoTotales, 0, "Total a pagar", LtotalValor, Fuentes.NORMAL);
        agregarFilaTotal(PcontenidoTotales, 1, "Pagado", LpagadoValor, Fuentes.NORMAL);
        agregarFilaTotal(PcontenidoTotales, 2, "Vuelto", LvueltoValor, Fuentes.SUBTITULO);

        Ptotales.add(PcontenidoTotales, BorderLayout.CENTER);

        GridBagConstraints gbcTotales = new GridBagConstraints();

        gbcTotales.gridx = 0;
        gbcTotales.gridy = 3;

        gbcTotales.weightx = 1.0;
        gbcTotales.weighty = 0.44;

        gbcTotales.anchor = GridBagConstraints.SOUTH;
        gbcTotales.fill = GridBagConstraints.HORIZONTAL;

        gbcTotales.insets = new Insets(0, 20, 10, 20);

        Pizquierdo.add(Ptotales, gbcTotales);

        actualizarTotales();
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

        Letiqueta.setFont(Fuentes.LFL.deriveFont(tamanoFuente));
        Letiqueta.setForeground(Colores.NEGRO);

        valor.setFont(Fuentes.LFL.deriveFont(tamanoFuente));
        valor.setForeground(Colores.CORINTO);

        GridBagConstraints gbcEtiqueta = new GridBagConstraints();

        gbcEtiqueta.gridx = 0;
        gbcEtiqueta.gridy = fila;
        gbcEtiqueta.weightx = 1.0;

        gbcEtiqueta.anchor = GridBagConstraints.WEST;
        gbcEtiqueta.insets = new Insets(5, 0, 5, 10);

        panel.add(Letiqueta, gbcEtiqueta);

        GridBagConstraints gbcValor = new GridBagConstraints();

        gbcValor.gridx = 1;
        gbcValor.gridy = fila;

        gbcValor.anchor = GridBagConstraints.EAST;
        gbcValor.insets = new Insets(5, 0, 5, 0);

        panel.add(valor, gbcValor);
    }

    //---------------------- A C T U A L I Z A R   T O T A L E S ----------------
    private void actualizarTotales() {

        double pagado = 0.0;

        for (double monto : metodosAplicados.values()) {
            pagado += monto;
        }

        double vuelto = pagado - totalAPagar;

        if (vuelto < 0) {
            vuelto = 0.0;
        }

        LtotalValor.setText("Q." + String.format("%.2f", totalAPagar));
        LpagadoValor.setText("Q." + String.format("%.2f", pagado));
        LvueltoValor.setText("Q." + String.format("%.2f", vuelto));

        Bcontinuar.setEnabled(pagado >= totalAPagar);
    }

    //---------------------- B O T Ó N   C O N T I N U A R ----------------
    private void inicializarBotonContinuar() {

        Bcontinuar = new Boton("Continuar");

        Bcontinuar.rojo();
        Bcontinuar.setEnabled(false);

        Bcontinuar.addActionListener(e -> continuar());

        GridBagConstraints gbcContinuar = new GridBagConstraints();

        gbcContinuar.gridx = 0;
        gbcContinuar.gridy = 4;

        gbcContinuar.weightx = 1.0;
        gbcContinuar.weighty = 0.20;

        gbcContinuar.anchor = GridBagConstraints.SOUTH;
        gbcContinuar.fill = GridBagConstraints.HORIZONTAL;

        gbcContinuar.insets = new Insets(10, 20, 20, 20);

        Pizquierdo.add(Bcontinuar, gbcContinuar);
    }

    //---------------------- C O N T I N U A R ----------------
    private void continuar() {

        Factura factura = new Factura(
                totalAPagar,
                pedido,
                descuento
        );

        this.dispose();
    }

    //---------------------- P A N E L   D E R E C H O ----------------
    private void inicializarPanelDerecho() {

        Pmetodos = new PanelMetodosPago();
        Pmetodos.setTitulo("Métodos aplicados");

        Pmetodos.setMetodosPagoListener(metodo -> eliminarMetodo(metodo));

        scroll = new JScrollPane(Pmetodos);

        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        JPanel PcontenedorScroll = new JPanel(new BorderLayout());

        PcontenedorScroll.setOpaque(false);

        PcontenedorScroll.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        PcontenedorScroll.add(scroll, BorderLayout.CENTER);

        Pderecho.add(PcontenedorScroll, BorderLayout.CENTER);
    }

    //------------------------ R E G R E S A R ----------------
    private void configurarRegreso() {

        Bregresar.addActionListener(
                e -> {

                    new PantallaPago(
                            pedido,
                            descuento
                    );

                    dispose();
                }
        );
    }
}
