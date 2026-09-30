//---------------------------------- V E N T A N A   C A J E R O -----------------------
package gui.cajero;

import java.awt.*;
import javax.swing.*;
import gui.componentes.*;
import gui.estilo.*;

public class VentanaCajero extends JFrame {

    //------------------------ A T R I B U T O S ----------------
    private JPanel Pprincipal;
    private BarraSuperior barraSuperior;
    private Encabezado encabezado;
    private PanelFondo Pderecho;
    private PanelMenu panelMenu;

    protected PanelColor Pcentral;

    //------------------------ C O N S T R U C T O R ----------------
    public VentanaCajero(String titulo) {

        setTitle(titulo);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(
                EXIT_ON_CLOSE
        );

        setResizable(false);

        setUndecorated(true);

        inicializarComponentes();
    }

    //------------------------ C O M P O N E N T E S ----------------
    public void inicializarComponentes() {

        inicializarPrincipal();

        inicializarArriba();

        inicializarEncabezado();

        inicializarIzquierda();

        inicializarCentral();
    }

    //------------------------ P R I N C I P A L ----------------
    public void inicializarPrincipal() {

        Pprincipal = new JPanel();

        Pprincipal.setLayout(
                new BorderLayout()
        );

        Pderecho
                = new PanelFondo(
                        "/utiles/imagenes/fondos/fondo_A.png"
                );

        Pderecho.setLayout(
                new BorderLayout()
        );

        add(Pprincipal);

        Pprincipal.add(
                Pderecho,
                BorderLayout.CENTER
        );
    }

    //------------------------ B A R R A   S U P E R I O R ----------------
    public void inicializarArriba() {

        barraSuperior
                = new BarraSuperior();

        Pprincipal.add(
                barraSuperior,
                BorderLayout.NORTH
        );
    }

    //------------------------ E N C A B E Z A D O ----------------
    public void inicializarEncabezado() {

        encabezado
                = new Encabezado();

        // El botón de la flecha lo conecta cada pantalla por su cuenta
        // (Menu, Combos, SeleccionEspacioCombo) porque todas regresan a
        // destinos distintos. Aquí no se le asigna ninguna acción para no
        // duplicarla.
        Pderecho.add(
                encabezado,
                BorderLayout.NORTH
        );
    }

    //------------------------ I Z Q U I E R D A ----------------
    public void inicializarIzquierda() {

        panelMenu
                = new PanelMenu();

        panelMenu.setCategoriaListener(
                categoria
                -> categoriaSeleccionada(categoria)
        );

        Pprincipal.add(
                panelMenu,
                BorderLayout.WEST
        );
    }

    //------------------------ C E N T R A L ----------------
    public void inicializarCentral() {

        Pcentral
                = new PanelColor();

        Pcentral.setColorFondo(
                Colores.DURAZNO
        );

        Pcentral.setOpacidad(200);

        Pderecho.add(
                Pcentral,
                BorderLayout.CENTER
        );
    }

    //------------------------ C A T E G O R I A ----------------
    protected void categoriaSeleccionada(
            String categoria
    ) {
    }

    //---------------------- G E T T E R S
    public Encabezado getEncabezado() {

        return encabezado;
    }

    //---------------------- G E T T E R S
    public BarraSuperior getBarraSuperior() {

        return barraSuperior;
    }
}
