//---------------------- R E S U M E N   C O M B O ----------------------
package gui.cajero;

import java.awt.*;
import javax.swing.*;
import gui.componentes.*;
import modelo.*;

public class ResumenCombo extends VentanaCompra {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Mascota
    private ImagenEscalada Ivaca;

    //-------------- Eslogan
    private ImagenEscalada Ieslogan;

    //-------------- Botón
    private Boton Bagregar;
    private Boton Bpagar;

    //-------------- Resumen
    private PanelResumen Presumen;

    //-------------- Scroll
    private JScrollPane scroll;

    //-------------- Productos
    private Pedido pedido;

    //-------------- Título
    private String titulo;

//------------------------ C O N S T R U C T O R ----------------
    public ResumenCombo(
            Pedido pedido,
            String titulo
    ) {

        super("Resumen del combo");

        this.pedido = pedido;
        this.titulo = titulo;

        inicializarContenido();
        configurarRegreso();

        mostrarVentana();
    }

    //---------------------- C O N T E N I D O ----------------
    private void inicializarContenido() {

        configurarTitulo(
                "Tu pedido"
        );

        inicializarPanelIzquierdo();
        inicializarPanelDerecho();
    }

    //---------------------- P A N E L   I Z Q U I E R D O ----------------
    private void inicializarPanelIzquierdo() {

        Pizquierdo.setLayout(
                new GridBagLayout()
        );

        //---------------------- M A S C O T A
        Ivaca = new ImagenEscalada(
                "/utiles/imagenes/elementosGraficos/Moovita-mascota.png",
                300,
                300
        );

        Ivaca.setAlineacion(
                SwingConstants.CENTER,
                SwingConstants.CENTER
        );

        GridBagConstraints gbcVaca
                = new GridBagConstraints();

        gbcVaca.gridx = 0;
        gbcVaca.gridy = 0;

        gbcVaca.weightx = 1.0;
        gbcVaca.weighty = 0.55;

        gbcVaca.anchor = GridBagConstraints.CENTER;
        gbcVaca.fill = GridBagConstraints.NONE;

        Pizquierdo.add(
                Ivaca,
                gbcVaca
        );

        //---------------------- B O T Ó N   A G R E G A R
        Bagregar = new Boton(
                "Agregar más"
        );

        Bagregar.rojo();

        Bagregar.addActionListener(
                e -> agregarMas()
        );

        GridBagConstraints gbcAgregar
                = new GridBagConstraints();

        gbcAgregar.gridx = 0;
        gbcAgregar.gridy = 1;

        gbcAgregar.weightx = 1.0;
        gbcAgregar.weighty = 0.12;

        gbcAgregar.anchor = GridBagConstraints.NORTH;
        gbcAgregar.fill = GridBagConstraints.NONE;

        gbcAgregar.insets = new Insets(
                10, 0, 5, 0
        );

        Pizquierdo.add(
                Bagregar,
                gbcAgregar
        );

        //---------------------- B O T Ó N   P A G A R
        Bpagar = new Boton(
                "Pagar"
        );

        Bpagar.addActionListener(
                e -> pagar()
        );

        GridBagConstraints gbcPagar
                = new GridBagConstraints();

        gbcPagar.gridx = 0;
        gbcPagar.gridy = 2;

        gbcPagar.weightx = 1.0;
        gbcPagar.weighty = 0.12;

        gbcPagar.anchor = GridBagConstraints.NORTH;
        gbcPagar.fill = GridBagConstraints.NONE;

        gbcPagar.insets = new Insets(
                5, 0, 10, 0
        );

        Pizquierdo.add(
                Bpagar,
                gbcPagar
        );

        //---------------------- E S L O G A N
        Ieslogan = new ImagenEscalada(
                "/utiles/imagenes/logotipo/eslogan-verde.png",
                400,
                200
        );

        Ieslogan.setAlineacion(
                SwingConstants.CENTER,
                SwingConstants.CENTER
        );

        GridBagConstraints gbcEslogan
                = new GridBagConstraints();

        gbcEslogan.gridx = 0;
        gbcEslogan.gridy = 3;

        gbcEslogan.weightx = 1.0;
        gbcEslogan.weighty = 0.21;

        gbcEslogan.anchor = GridBagConstraints.NORTH;
        gbcEslogan.fill = GridBagConstraints.NONE;

        gbcEslogan.insets = new Insets(
                5, 0, 20, 0
        );

        Pizquierdo.add(
                Ieslogan,
                gbcEslogan
        );
    }

//---------------------- P A N E L   D E R E C H O ----------------
    private void inicializarPanelDerecho() {

        //---------------------- P A N E L   R E S U M E N
        Presumen = new PanelResumen();

        //---------------------- T Í T U L O
        Presumen.setTitulo(
                "Tu pedido"
        );

        //---------------------- L I S T E N E R
        Presumen.setResumenListener(
                (item, producto) -> {

                    Producto productoCompleto
                    = new dao.ProductoDAO().buscarPorId(
                            producto.getId_Producto()
                    );

                    if (productoCompleto == null) {

                        productoCompleto = producto;
                    }

                    new PantallaPersonalizacion(
                            productoCompleto,
                            pedido,
                            item
                    );

                    dispose();
                }
        );

        //---------------------- P E D I D O
        Presumen.agregarPedido(
                pedido
        );

        //---------------------- S C R O L L
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

        //---------------------- C O N T E N E D O R
        JPanel PcontenedorScroll = new JPanel(
                new BorderLayout()
        );

        PcontenedorScroll.setOpaque(false);

        PcontenedorScroll.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        30,
                        30,
                        30
                )
        );

        PcontenedorScroll.add(
                scroll,
                BorderLayout.CENTER
        );

        //---------------------- A G R E G A R
        Pderecho.add(
                PcontenedorScroll,
                BorderLayout.CENTER
        );
    }

//---------------------- A G R E G A R   M Á S
    private void agregarMas() {

        new Menu(
                "Hamburguesas",
                pedido,
                () -> new ResumenCombo(
                        pedido,
                        titulo
                )
        );

        dispose();
    }

    //---------------------- P A G A R ----------------
    /**
     * Pregunta si el cajero ya terminó el pedido y, si contesta que sí,
     * abre la pantalla de pago.
     *
     * El mensaje se redacta con la pregunta en positivo ("¿Deseas pasar
     * al pago?") porque así se entiende de una vistazo qué botón hay que
     * pulsar. Antes la pregunta estaba en negativo ("¿Ya no deseas
     * agregar algo más?") y por eso el botón "Sí" terminaba el pedido
     * mientras el "No" no hacía nada, que es lo contrario de lo que
     * espera cualquiera que lo lea.
     */
    private void pagar() {

        int respuesta = Dialogo.confirmar(
                this,
                "Confirmar pedido",
                "¿Deseas pasar al pago de tu pedido?"
        );

        // Las dos respuestas llevan al pago. Si todavía quiere agregar
        // algo, tiene el botón "Agregar más" a la izquierda, así que
        // quedarse aquí sin hacer nada no aportaba nada.
        if (respuesta == Dialogo.CERRADO) {

            // Cerró la ventana con la X: no se hace nada.
            return;
        }

        new PantallaPago(
                pedido,
                0.0
        );

        dispose();
    }

    //------------------------ R E G R E S A R ----------------
    private void configurarRegreso() {

        Bregresar.addActionListener(
                e -> {

                    int respuesta = Dialogo.confirmar(
                            this,
                            "Cancelar pedido",
                            "¿Deseas regresar al menú?\nSe cancelará el pedido actual."
                    );

                    if (respuesta == Dialogo.SI) {

                        //Cancelar el pedido actual
                        new Menu();

                        dispose();
                    }
                }
        );
    }

}
