//---------------------------------- M E N U -----------------------
package gui.cajero;

import java.awt.*;
import javax.swing.*;
import logica.*;
import modelo.Producto;
import java.util.ArrayList;
import java.util.List;
import modelo.*;

public class Menu extends VentanaCajero {

    //------------------------  A T R I B U T O S ----------------
    //-------------- Contenido
    private PanelContenidoMenu contenido;
    private JScrollPane scroll;

    //-------------- Categoría
    private String categoriaActual;

    private Runnable accionRegreso;

    //-------------- Periodo
    private String periodoActual;

    //-------------- Temporizador
    private Timer temporizadorMenu;

    //-------------- Pedido
    private Pedido pedido;

//-------------------- C O N S T R U C T O R -----------------
    public Menu() {

        this(
                "Hamburguesas",
                new Pedido(),
                null
        );
    }

    public Menu(
            String categoria
    ) {

        this(
                categoria,
                new Pedido(),
                null
        );
    }

    public Menu(
            String categoria,
            Pedido pedido
    ) {

        this(
                categoria,
                pedido,
                null
        );
    }

    public Menu(
            String categoria,
            Pedido pedido,
            Runnable accionRegreso
    ) {

        super("Menú");

        this.pedido = pedido;

        this.accionRegreso
                = accionRegreso;

        inicializarContenido(
                categoria
        );

        configurarRegreso();

        setVisible(true);
    }

    //---------------------- C O N T E N I D O
    private void inicializarContenido(String categoria) {

        //---------------------- CATEGORÍA INICIAL
        categoriaActual = categoria;

        //---------------------- PERIODO ACTUAL
        periodoActual = obtenerPeriodoActual();

        //---------------------- CONTENIDO
        contenido = new PanelContenidoMenu(
                categoriaActual
        );

        configurarContenido();

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

        //---------------------- PANEL CENTRAL
        Pcentral.setLayout(
                new BorderLayout()
        );

        Pcentral.add(
                scroll,
                BorderLayout.CENTER
        );

        //---------------------- TEMPORIZADOR
        iniciarTemporizadorMenu();
    }

    //---------------------- C O N F I G U R A R   C O N T E N I D O
    private void configurarContenido() {

        contenido.setProductoListener(
                producto -> abrirCombos(
                        producto
                )
        );

        contenido.setComboListener(
                combo -> abrirCajita(
                        combo
                )
        );
    }

    //---------------------- O B T E N E R   P E R I O D O
    private String obtenerPeriodoActual() {

        if (Reloj.esManana()) {

            return "Manana";

        } else if (Reloj.esTarde()) {

            return "Tarde";

        } else {

            return "Noche";
        }
    }

    //---------------------- I N I C I A R   T E M P O R I Z A D O R
    private void iniciarTemporizadorMenu() {

        temporizadorMenu = new Timer(
                60000,
                e -> actualizarMenu()
        );

        temporizadorMenu.start();
    }

    //---------------------- A C T U A L I Z A R   M E N U
    private void actualizarMenu() {

        String nuevoPeriodo
                = obtenerPeriodoActual();

        if (!nuevoPeriodo.equals(periodoActual)) {

            periodoActual = nuevoPeriodo;

            cambiarCategoria(
                    categoriaActual
            );
        }
    }

    //---------------------- C A M B I A R   C A T E G O R Í A
    public void cambiarCategoria(
            String categoria
    ) {

        //---------------------- GUARDAR CATEGORÍA
        categoriaActual = categoria;

        //---------------------- CREAR CONTENIDO
        contenido = new PanelContenidoMenu(
                categoria
        );

        configurarContenido();

        //---------------------- CAMBIAR CONTENIDO
        scroll.setViewportView(
                contenido
        );

        //---------------------- ACTUALIZAR
        scroll.revalidate();
        scroll.repaint();
    }

//---------------------- A B R I R   C A J I T A
    private void abrirCajita(
            Combo combo
    ) {

        LogicaCombo logicaCombo
                = new LogicaCombo(
                        combo
                );

        Grupo_Combo grupoPendiente
                = logicaCombo
                        .obtenerSiguienteGrupoPendiente();

        if (grupoPendiente == null) {

            return;
        }

        new SeleccionEspacioCombo(
                logicaCombo,
                grupoPendiente,
                pedido,
                null,
                categoriaActual,
                accionRegreso
        );

        dispose();
    }

//---------------------- A B R I R   C O M B O S
    private void abrirCombos(
            Producto producto
    ) {

        new Combos(
                producto,
                pedido,
                categoriaActual,
                accionRegreso
        );

        dispose();
    }

    //---------------------- C A T E G O R Í A   S E L E C C I O N A D A
    @Override
    protected void categoriaSeleccionada(
            String categoria
    ) {

        cambiarCategoria(
                categoria
        );
    }

    //---------------------- C E R R A R   V E N T A N A
    @Override
    public void dispose() {

        if (temporizadorMenu != null) {

            temporizadorMenu.stop();
        }

        super.dispose();
    }

    //------------------------ R E G R E S A R ----------------
    private void configurarRegreso() {

        getEncabezado()
                .getBregresar()
                .addActionListener(
                        e -> {

                            if (accionRegreso != null) {

                                accionRegreso.run();

                            } else {

                                new PantallaPrincipal();
                            }

                            dispose();
                        }
                );
    }
}
