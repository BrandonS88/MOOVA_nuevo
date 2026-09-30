//---------------------------------- C O M B O   M E N U -----------------------
package gui.cajero;

import gui.componentes.*;
import gui.estilo.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import modelo.Combo;
import modelo.Producto;

public class ComboMenu extends PanelColor {

    //------------------------ A T R I B U T O S ----------------
    private Combo combo;
    private Producto producto;

    //-------------- Componentes
    private ImagenEscalada Iimagen;
    private JLabel Lnombre;
    private JLabel Lprecio;
    private JLabel Ltipo;
    private PanelCombo Pcontenido;

    private JPanel Pimagen;
    private JPanel Pinformacion;

    //-------------- Listener
    private ComboListener comboListener;
    private ProductoListener productoListener;

    //-------------- Boolean
    private boolean esIndividual;

    //------------------------ C O N S T R U C T O R E S ----------------
    //-------------- Combo complementario
    public ComboMenu(
            Combo combo,
            Producto producto
    ) {

        this.combo = combo;
        this.producto = producto;
        this.esIndividual = false;

        inicializarComponentes();
    }

//-------------- Combo independiente, como Cajita
    public ComboMenu(
            Combo combo
    ) {

        this.combo = combo;
        this.producto = null;
        this.esIndividual = false;

        inicializarComponentes();
    }

//-------------- Producto individual
    public ComboMenu(
            Producto producto
    ) {

        this.combo = null;
        this.producto = producto;
        this.esIndividual = true;

        inicializarComponentes();
    }

    //------------------------ L I S T E N E R ----------------
    public interface ComboListener {

        void comboSeleccionado(
                Combo combo
        );
    }

    public void setComboListener(
            ComboListener listener
    ) {

        this.comboListener = listener;
    }

    public interface ProductoListener {

        void productoSeleccionado(
                Producto producto
        );
    }

    public void setProductoListener(
            ProductoListener listener
    ) {

        this.productoListener = listener;
    }

//------------------------ C O M P O N E N T E S ----------------
    private void inicializarComponentes() {

        //---------------------- T A M A Ñ O   F I J O
        setPreferredSize(
                new Dimension(
                        600,
                        350
                )
        );

        setMinimumSize(
                new Dimension(
                        600,
                        350
                )
        );

        setMaximumSize(
                new Dimension(
                        600,
                        350
                )
        );

        //---------------------- L A Y O U T
        setLayout(
                new BoxLayout(
                        this,
                        BoxLayout.Y_AXIS
                )
        );

        inicializarImagen();
        inicializarInformacion();
        inicializarContenido();
        inicializarEvento();
    }

//------------------------ I M A G E N ----------------
    private void inicializarImagen() {

        String imagen;

        if (esIndividual) {

            imagen = producto.getImagen();

        } else {

            imagen = combo.getImagen();
        }

        Iimagen = new ImagenEscalada(
                imagen,
                150,
                150
        );

        Iimagen.setAlineacion(
                SwingConstants.CENTER,
                SwingConstants.CENTER
        );

        Pimagen
                = new JPanel(
                        new GridBagLayout()
                );

        Pimagen.setOpaque(false);

        Pimagen.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        Pimagen.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        5,
                        10
                )
        );

        Pimagen.add(
                Iimagen
        );

        add(
                Pimagen
        );
    }

//------------------------ I N F O R M A C I O N ----------------
    private void inicializarInformacion() {

        String nombre;
        String tipo;
        double precio;

        if (esIndividual) {

            nombre = producto.getNombre();
            tipo = "Individual";
            precio = producto.getPrecio();

        } else {

            nombre = combo.getNombre();
            tipo = combo.getTipo();
            precio = obtenerPrecio();
        }

        Lnombre
                = new JLabel(
                        nombre,
                        SwingConstants.CENTER
                );

        Ltipo
                = new JLabel(
                        tipo,
                        SwingConstants.CENTER
                );

        Lprecio
                = new JLabel(
                        String.format(
                                "Q%.2f",
                                precio
                        ),
                        SwingConstants.CENTER
                );

        Lnombre.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Ltipo.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lprecio.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lnombre.setForeground(
                Colores.ROJO
        );

        Ltipo.setForeground(
                Colores.CORINTO
        );

        Lprecio.setForeground(
                Colores.ROJO
        );

        Lnombre.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        Ltipo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        Lprecio.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        Pinformacion
                = new JPanel();

        Pinformacion.setOpaque(false);

        Pinformacion.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        Pinformacion.setLayout(
                new BoxLayout(
                        Pinformacion,
                        BoxLayout.Y_AXIS
                )
        );

        Pinformacion.add(
                Lnombre
        );

        Pinformacion.add(
                Box.createVerticalStrut(
                        5
                )
        );

        Pinformacion.add(
                Ltipo
        );

        Pinformacion.add(
                Box.createVerticalStrut(
                        5
                )
        );

        Pinformacion.add(
                Lprecio
        );

        Pinformacion.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        10,
                        5,
                        10
                )
        );

        add(
                Pinformacion
        );
    }

//------------------------ P R E C I O ----------------
    private double obtenerPrecio() {

        if ("Complementario".equals(
                combo.getTipo()
        )) {

            return producto.getPrecio()
                    + combo.getPrecio_extra();

        } else {

            return combo.getPrecio();
        }
    }

    //------------------------ C O N T E N I D O ----------------
    private void inicializarContenido() {

        String contenido;

        if (esIndividual) {

            contenido = "Producto individual";

        } else {

            contenido = combo.getDescripcion();
        }

        Pcontenido
                = new PanelCombo(
                        contenido
                );

        Pcontenido.setPreferredSize(
                new Dimension(
                        700,
                        50
                )
        );

        Pcontenido.setMinimumSize(
                new Dimension(
                        700,
                        50
                )
        );

        Pcontenido.setMaximumSize(
                new Dimension(
                        700,
                        50
                )
        );

        Pcontenido.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        add(
                Pcontenido
        );
    }

    //------------------------ E V E N T O ----------------
    private void inicializarEvento() {

        MouseAdapter eventoClick
                = new MouseAdapter() {

            @Override
            public void mouseClicked(
                    MouseEvent e
            ) {

                if (esIndividual) {

                    if (productoListener != null) {

                        productoListener.productoSeleccionado(
                                producto
                        );
                    }

                } else {

                    if (comboListener != null) {

                        comboListener.comboSeleccionado(
                                combo
                        );
                    }
                }
            }
        };

        addMouseListener(
                eventoClick
        );

        Pimagen.addMouseListener(
                eventoClick
        );

        Iimagen.addMouseListener(
                eventoClick
        );

        Pinformacion.addMouseListener(
                eventoClick
        );

        Lnombre.addMouseListener(
                eventoClick
        );

        Ltipo.addMouseListener(
                eventoClick
        );

        Lprecio.addMouseListener(
                eventoClick
        );

        Pcontenido.setMouseListener(
                eventoClick
        );
    }
}
