//--------------  S E L E C C I Ó N   E S P A C I O    C O M B O -----------------
package gui.cajero;

import dao.OpcionComboDAO;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import modelo.*;
import logica.LogicaCombo;

public class SeleccionEspacioCombo extends VentanaCajero {

    //------------------------ A T R I B U T O S ----------------
    private LogicaCombo logicaCombo;
    private Grupo_Combo grupo;
    private Pedido pedido;

    private Producto producto;
    private String categoriaOrigen;
    private Runnable accionRegreso;

    //-------------- Contenido
    private PanelContenidoMenu contenido;
    private JScrollPane scroll;

//------------------------ C O N S T R U C T O R ----------------
    public SeleccionEspacioCombo(
            LogicaCombo logicaCombo,
            Grupo_Combo grupo,
            Pedido pedido,
            Producto producto,
            String categoriaOrigen,
            Runnable accionRegreso
    ) {

        super(
                "Selecciona: "
                + grupo.getNombre()
        );

        this.logicaCombo = logicaCombo;
        this.grupo = grupo;

        this.pedido = pedido;

        this.producto = producto;

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

        //---------------------- O P C I O N E S
        List<SubcategoriaMenu> subcategorias
                = obtenerSubcategoriasCombo();

        //---------------------- M E N U
        contenido
                = new PanelContenidoMenu(
                        grupo.getNombre(),
                        subcategorias
                );

        contenido.setProductoListener(
                productoSeleccionado
                -> productoSeleccionado(
                        productoSeleccionado
                )
        );

        //---------------------- S C R O L L
        scroll = new JScrollPane(
                contenido
        );

        scroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scroll.setOpaque(false);

        scroll.getViewport().setOpaque(false);

        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        //---------------------- P A N E L   C E N T R A L
        Pcentral.setLayout(
                new BorderLayout()
        );

        Pcentral.add(
                scroll,
                BorderLayout.CENTER
        );
    }

    //------------------------ S U B C A T E G O R Í A S   C O M B O ----------------
    private List<SubcategoriaMenu> obtenerSubcategoriasCombo() {

        OpcionComboDAO opcionComboDAO
                = new OpcionComboDAO();

        List<Subcategoria> subcategorias
                = opcionComboDAO
                        .listarSubcategoriasPorGrupo(
                                grupo.getId_GrupoCombo()
                        );

        List<SubcategoriaMenu> resultado
                = new ArrayList<>();

        for (Subcategoria subcategoria
                : subcategorias) {

            List<Producto> productos
                    = opcionComboDAO
                            .listarProductosPorGrupoYSubcategoria(
                                    grupo.getId_GrupoCombo(),
                                    subcategoria.getId_Subcategoria()
                            );

            if (!productos.isEmpty()) {

                resultado.add(
                        new SubcategoriaMenu(
                                subcategoria.getNombre(),
                                productos
                        )
                );
            }
        }

        return resultado;
    }

    //------------------------ P R O D U C T O   S E L E C C I O N A D O ----------------
    private void productoSeleccionado(
            Producto producto
    ) {

        boolean asignado
                = logicaCombo.asignarProducto(
                        grupo,
                        producto
                );

        if (asignado) {

            continuarFlujo();
        }
    }

//------------------------ C O N T I N U A R   F L U J O ----------------
    private void continuarFlujo() {

        Grupo_Combo siguienteGrupo
                = logicaCombo
                        .obtenerSiguienteGrupoPendiente();

        if (siguienteGrupo == null) {

            finalizarCombo();

        } else {

            new SeleccionEspacioCombo(
                    logicaCombo,
                    siguienteGrupo,
                    pedido,
                    producto,
                    categoriaOrigen,
                    accionRegreso
            );
        }

        dispose();
    }

//------------------------ F I N A L I Z A R   C O M B O ----------------
    private void finalizarCombo() {

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

    //------------------------ C A T E G O R I A ----------------
    @Override
    protected void categoriaSeleccionada(
            String categoria
    ) {

        new Menu(
                categoria
        );

        dispose();
    }

//------------------------ R E G R E S A R ----------------
    private void configurarRegreso() {

        getEncabezado()
                .getBregresar()
                .addActionListener(
                        e -> {

                            if (producto == null) {

                                new Menu(
                                        categoriaOrigen,
                                        pedido,
                                        accionRegreso
                                );

                            } else {

                                new Combos(
                                        producto,
                                        pedido,
                                        categoriaOrigen,
                                        accionRegreso
                                );
                            }

                            dispose();
                        }
                );
    }

}
