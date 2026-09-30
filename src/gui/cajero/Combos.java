//---------------------------------- C O M B O S -----------------------
package gui.cajero;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import modelo.*;
import logica.LogicaCombo;

public class Combos extends VentanaCajero {

    //------------------------ A T R I B U T O S ----------------
    private Producto producto;

    private PanelContenidoCombos contenido;
    private JScrollPane scroll;

    private String categoriaOrigen;
    private Runnable accionRegreso;

    private Pedido pedido;

//------------------------ C O N S T R U C T O R ----------------
    public Combos(
            Producto producto,
            Pedido pedido,
            String categoriaOrigen,
            Runnable accionRegreso
    ) {

        super("Combos");

        this.producto = producto;

        this.pedido = pedido;

        this.categoriaOrigen
                = categoriaOrigen;

        this.accionRegreso
                = accionRegreso;

        inicializarContenido();

        configurarRegreso();

        setVisible(true);
    }

    //------------------------ C O N T E N I D O ----------------
    private void inicializarContenido() {

        contenido
                = new PanelContenidoCombos(producto);

        contenido.setComboListener(
                combo -> comboSeleccionado(combo)
        );

        contenido.setProductoListener(
                productoSeleccionado
                -> productoIndividualSeleccionado(
                        productoSeleccionado
                )
        );

        scroll
                = new JScrollPane(contenido);

        scroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scroll.setOpaque(false);

        scroll.getViewport().setOpaque(false);

        Pcentral.setLayout(
                new BorderLayout()
        );

        Pcentral.add(
                scroll,
                BorderLayout.CENTER
        );
    }

    //------------------------ P R O D U C T O   I N D I V I D U A L ----------------
    private void productoIndividualSeleccionado(
            Producto producto
    ) {

        ItemPedido item
                = new ItemPedido(
                        producto,
                        null,
                        new ArrayList<>()
                );

        pedido.agregarItem(
                item
        );

        new ResumenCombo(
                pedido,
                producto.getNombre()
                + " Individual"
        );

        dispose();
    }

    //------------------------ C O M B O   S E L E C C I O N A D O ----------------
    private void comboSeleccionado(Combo combo) {

        LogicaCombo logicaCombo
                = new LogicaCombo(
                        combo,
                        producto
                );

        continuarFlujo(
                logicaCombo
        );
    }

    //------------------------ A B R I R   R E S U M E N ----------------
    private void abrirResumen(
            LogicaCombo logicaCombo
    ) {

        ItemPedido item
                = logicaCombo.crearItemPedido();

        pedido.agregarItem(
                item
        );

        new ResumenCombo(
                pedido,
                logicaCombo.getCombo().getNombre()
        );

        dispose();
    }

//------------------------ C O N T I N U A R   F L U J O ----------------
    private void continuarFlujo(
            LogicaCombo logicaCombo
    ) {

        Grupo_Combo grupoPendiente
                = logicaCombo.obtenerSiguienteGrupoPendiente();

        if (grupoPendiente == null) {

            abrirResumen(
                    logicaCombo
            );

        } else {

            abrirSeleccionEspacio(
                    logicaCombo,
                    grupoPendiente
            );
        }
    }

//------------------------ A B R I R   S E L E C C I O N   D E   E S P A C I O
    private void abrirSeleccionEspacio(
            LogicaCombo logicaCombo,
            Grupo_Combo grupo
    ) {

        new SeleccionEspacioCombo(
                logicaCombo,
                grupo,
                pedido,
                producto,
                categoriaOrigen,
                accionRegreso
        );

        dispose();
    }

    //------------------------ C A T E G O R I A ----------------
    @Override
    protected void categoriaSeleccionada(
            String categoria
    ) {

        new Menu(
                categoria,
                pedido,
                accionRegreso
        );

        dispose();
    }

    //------------------------ R E G R E S A R ----------------
    private void configurarRegreso() {

        getEncabezado()
                .getBregresar()
                .addActionListener(
                        e -> {

                            new Menu(
                                    categoriaOrigen,
                                    pedido,
                                    accionRegreso
                            );

                            dispose();
                        }
                );
    }
}
