//---------------------------------- P A N E L   M E N U -----------------------
package gui.cajero;

import gui.componentes.Boton;
import gui.componentes.ImagenEscalada;
import java.awt.*;
import javax.swing.*;
import gui.estilo.*;
import logica.*;

public class PanelMenu extends JPanel {

    //------------------------  A T R I B U T O S ----------------
    //-------------- Logo
    private ImagenEscalada Ilogo;

    //-------------- Botones
    private Boton Bhamburguesas;
    private Boton Bbebidas;
    private Boton Bpostres;
    private Boton Bantojos;
    private Boton BcajitaFeliz;

    //-------------- Total
    private JLabel Ltotal;
    private JLabel Lnum;

    //-------------- Temporizador
    private Timer temporizador;
    private boolean mananaActual;

    //-------------- Listener
    private CategoriaListener categoriaListener;

    //----------------------- L I S T E N E R
    public interface CategoriaListener {

        void categoriaSeleccionada(
                String categoria
        );
    }

    //-------------------- C O N S T R U C T O R -----------------
    public PanelMenu() {

        //---------------------- PANEL
        setBackground(
                Colores.AMARILLO
        );

        setLayout(
                new GridBagLayout()
        );

        setPreferredSize(
                new Dimension(
                        300,
                        0
                )
        );

        //---------------------- INICIALIZAR
        inicializarComponentes();
    }

    //---------------------- I N I C I A L I Z A R
    private void inicializarComponentes() {

        inicializarLogo();
        inicializarBotones();
        inicializarTotal();
    }

    private void inicializarLogo() {

        Ilogo = new ImagenEscalada(
                "/utiles/imagenes/logotipo/logo-texto-eslogan.png",
                300,
                300
        );

        Ilogo.setAlineacion(
                SwingConstants.CENTER,
                SwingConstants.CENTER
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 0.20;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        add(
                Ilogo,
                gbc
        );
    }

    private void inicializarBotones() {

        Bhamburguesas = new Boton(
                "Hamburguesas"
        );

        Bbebidas = new Boton(
                "Bebidas"
        );

        Bpostres = new Boton(
                "Postres"
        );

        Bantojos = new Boton(
                "Antojos"
        );

        BcajitaFeliz = new Boton(
                "Cajita Feliz"
        );

        Bhamburguesas.verde();
        Bbebidas.verde();
        Bpostres.verde();
        Bantojos.verde();
        BcajitaFeliz.verde();

        agregarBoton(
                Bhamburguesas,
                1
        );

        agregarBoton(
                Bbebidas,
                2
        );

        agregarBoton(
                Bpostres,
                3
        );

        agregarBoton(
                Bantojos,
                4
        );

        agregarBoton(
                BcajitaFeliz,
                5
        );

        //---------------------- LISTENER
        agregarAccionCategoria(
                Bhamburguesas,
                "Hamburguesas"
        );

        agregarAccionCategoria(
                Bbebidas,
                "Bebidas"
        );

        agregarAccionCategoria(
                Bpostres,
                "Postres"
        );

        agregarAccionCategoria(
                Bantojos,
                "Antojos"
        );

        agregarAccionCategoria(
                BcajitaFeliz,
                "Cajita Feliz"
        );
    }

    private void agregarBoton(
            Boton boton,
            int fila
    ) {

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = fila;

        gbc.weightx = 1.0;
        gbc.weighty = 0.12;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                5,
                15,
                5,
                15
        );

        add(
                boton,
                gbc
        );
    }

    private void inicializarTotal() {

        Ltotal = new JLabel(
                "Total: Q."
        );

        Lnum = new JLabel(
                "0.00"
        );

        Ltotal.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lnum.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Ltotal.setForeground(
                Colores.CORINTO
        );

        Lnum.setForeground(
                Colores.CORINTO
        );

        //---------------------- PANEL TOTAL
        JPanel Ptotal = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        5,
                        0
                )
        );

        Ptotal.setOpaque(false);

        Ptotal.add(Ltotal);
        Ptotal.add(Lnum);

        //---------------------- POSICIÓN
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 6;

        gbc.weightx = 1.0;
        gbc.weighty = 0.20;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        add(
                Ptotal,
                gbc
        );
    }

    public void setCategoriaListener(
            CategoriaListener listener
    ) {

        this.categoriaListener = listener;
    }

    private void agregarAccionCategoria(
            Boton boton,
            String categoria
    ) {

        boton.addActionListener(e -> {

            if (categoriaListener != null) {

                categoriaListener.categoriaSeleccionada(
                        categoria
                );
            }
        });
    }

}
