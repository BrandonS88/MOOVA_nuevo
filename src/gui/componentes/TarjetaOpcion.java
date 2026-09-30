//---------------------------------- T A R J E T A    O P C I O N -----------------------
package gui.componentes;

import gui.estilo.*;
import java.awt.*;
import javax.swing.*;
import modelo.*;

public class TarjetaOpcion extends PanelColor {

    //------------------------ TIPO ----------------
    // Define cómo se comporta el control de abajo
    public enum Tipo {
        AJUSTABLE,          // +/- con cantidad base
        SELECCION_UNICA     // se elige una opción
    }

    //------------------------ ATRIBUTOS ----------------
    private Ingrediente ingrediente;
    private Tipo tipo;

    private double precioExtra;

    private int cantidadBase;   // cantidad que trae originalmente
    private int cantidadActual; // cantidad que el cliente terminó eligiendo

    private boolean seleccionada; // solo se usa en SELECCION_UNICA
    private Opcion_Personalizacion opcionPersonalizacion;

    private ImagenEscalada Iimagen;
    private JLabel Lnombre;
    private JLabel Lprecio;
    private JLabel Lcantidad;
    private JPanel Pcontrol;

    private TarjetaOpcionListener listener;

    //------------------------ LISTENER ----------------
    public interface TarjetaOpcionListener {

        void opcionModificada(TarjetaOpcion tarjeta);
    }

    public void setTarjetaOpcionListener(
            TarjetaOpcionListener listener
    ) {

        this.listener = listener;
    }

    //------------------------ CONSTRUCTOR ----------------
    public TarjetaOpcion(
            Ingrediente ingrediente,
            Tipo tipo,
            int cantidadBase
    ) {

        this(
                ingrediente,
                tipo,
                cantidadBase,
                0.00
        );
    }

    //---------------------- C O N S T R U C T O R   C O M P L E T O ----------------
    public TarjetaOpcion(
            Ingrediente ingrediente,
            Tipo tipo,
            int cantidadBase,
            double precioExtra
    ) {

        this.ingrediente = ingrediente;
        this.tipo = tipo;
        this.cantidadBase = cantidadBase;
        this.cantidadActual = cantidadBase;
        this.precioExtra = precioExtra;

        // Si la opción única tiene cantidad base 1,
        // significa que viene seleccionada por defecto.
        this.seleccionada
                = tipo == Tipo.SELECCION_UNICA
                && cantidadBase > 0;

        setColorFondo(Colores.BLANCO);
        setRadio(16);

        setPreferredSize(
                new Dimension(
                        240,
                        300
                )
        );

        setLayout(
                new BoxLayout(
                        this,
                        BoxLayout.Y_AXIS
                )
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        16,
                        12,
                        16,
                        12
                )
        );

        inicializarImagen();
        inicializarInformacion();
        inicializarControl();

        // Mostrar visualmente la opción predeterminada.
        if (seleccionada) {

            setColorBorde(
                    Colores.ROJO
            );

            setGrosorBorde(2);
        }
    }

    //---------------------- O P C I O N   P E R S O N A L I Z A C I O N ----------------
    public void setOpcionPersonalizacion(
            Opcion_Personalizacion opcion
    ) {

        this.opcionPersonalizacion = opcion;
    }

    //---------------------- O P C I O N   P E R S O N A L I Z A C I O N ----------------
    public Opcion_Personalizacion
            getOpcionPersonalizacion() {

        return opcionPersonalizacion;
    }

    //------------------------ I M A G E N ----------------
    private void inicializarImagen() {

        Iimagen = new ImagenEscalada(
                ingrediente.getImagen(),
                130,
                130
        );

        Iimagen.setAlineacion(
                SwingConstants.CENTER,
                SwingConstants.CENTER
        );

        Iimagen.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        Iimagen.setMaximumSize(
                new Dimension(
                        130,
                        130
                )
        );

        add(Iimagen);

        add(
                Box.createVerticalStrut(
                        10
                )
        );
    }

    //------------------------ I N F O R M A C I O N ----------------
    private void inicializarInformacion() {

        Lnombre = new JLabel(
                ingrediente.getNombre(),
                SwingConstants.CENTER
        );

        Lprecio = new JLabel(
                textoPrecio(),
                SwingConstants.CENTER
        );

        Lnombre.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lprecio.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lnombre.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        Lnombre.getPreferredSize().height
                )
        );

        Lprecio.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        Lprecio.getPreferredSize().height
                )
        );

        Lnombre.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        Lprecio.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        add(Lnombre);
        add(Lprecio);

        add(
                Box.createVerticalStrut(
                        10
                )
        );
    }

    //---------------------- T E X T O   P R E C I O ----------------
    private String textoPrecio() {

        if (tipo == Tipo.SELECCION_UNICA) {

            if (precioExtra <= 0) {

                return "incluido";
            }

            return "+Q"
                    + String.format(
                            "%.2f",
                            precioExtra
                    );
        }

        //---------------------- A J U S T A B L E

        if (cantidadActual > cantidadBase) {

            int extra =
                    cantidadActual - cantidadBase;

            return "+Q"
                    + String.format(
                            "%.2f",
                            precioExtra * extra
                    );
        }

        return cantidadActual == 0
                ? "quitado"
                : "incluido";
    }

    //---------------------- E X T R A   P R E C I O ----------------
    // Lo que esta tarjeta suma al total.
    public double getExtraPrecio() {

        if (tipo == Tipo.SELECCION_UNICA) {

            return seleccionada
                    ? precioExtra
                    : 0.00;
        }

        //---------------------- A J U S T A B L E

        if (cantidadActual > cantidadBase) {

            return (
                    cantidadActual - cantidadBase
            ) * precioExtra;
        }

        return 0.00;
    }

    //---------------------- I N I C I A L I Z A R   C O N T R O L ----------------
    private void inicializarControl() {

        Pcontrol = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        12,
                        0
                )
        );

        Pcontrol.setOpaque(false);

        Pcontrol.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        if (tipo == Tipo.AJUSTABLE) {

            construirStepper();

        } else {

            construirSeleccionable();
        }

        add(Pcontrol);
    }

    //---------------------- S T E P P E R ----------------
    private void construirStepper() {

        Boton Bmenos =
                crearBotonStepper("-");

        Bmenos.rojo();

        //---------------------- C A N T I D A D

        Lcantidad = new JLabel(
                String.valueOf(
                        cantidadActual
                )
        );

        Lcantidad.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Boton Bmas =
                crearBotonStepper("+");

        Bmas.verde();

        //---------------------- M E N O S

        Bmenos.addActionListener(e -> {

            if (cantidadActual > 0) {

                cantidadActual--;

                actualizarControl();

                notificarCambio();
            }
        });

        //---------------------- M A S

        Bmas.addActionListener(e -> {

            cantidadActual++;

            actualizarControl();

            notificarCambio();
        });

        Pcontrol.add(Bmenos);
        Pcontrol.add(Lcantidad);
        Pcontrol.add(Bmas);
    }

    //---------------------- A C T U A L I Z A R   C O N T R O L ----------------
    private void actualizarControl() {

        if (Lcantidad != null) {

            Lcantidad.setText(
                    String.valueOf(
                            cantidadActual
                    )
            );
        }

        if (Lprecio != null) {

            Lprecio.setText(
                    textoPrecio()
            );
        }
    }

    //---------------------- B O T O N   S T E P P E R ----------------
    private Boton crearBotonStepper(
            String texto
    ) {

        Boton boton =
                new Boton(
                        texto
                );

        boton.setPreferredSize(
                new Dimension(
                        50,
                        50
                )
        );

        boton.setRadio(25);

        boton.setColorFondo(
                Colores.AMARILLO_FUERTE
        );

        boton.setColorBorde(
                Colores.NARANJA
        );

        boton.setColorTexto(
                Colores.NEGRO
        );

        boton.setColorFondoHover(
                Colores.NARANJA
        );

        boton.setColorBordeHover(
                Colores.CORINTO
        );

        boton.setColorTextoHover(
                Colores.BLANCO
        );

        return boton;
    }

//---------------------- S E L E C C I O N A B L E ----------------

    private void construirSeleccionable() {

        addMouseListener(
                new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(
                    java.awt.event.MouseEvent e
            ) {

                alternarSeleccion();

                notificarCambio();
            }
        }
        );
    }

//---------------------- A L T E R N A R   S E L E C C I O N ----------------

    private void alternarSeleccion() {

        if (seleccionada) {

            deseleccionar();

        } else {

            marcarSeleccionada();
        }
    }

    //---------------------- M A R C A R   S E L E C C I O N ----------------

    public void marcarSeleccionada() {

        seleccionada = true;

        setColorBorde(
                Colores.ROJO
        );

        setGrosorBorde(2);

        if (Lprecio != null) {

            Lprecio.setText(
                    textoPrecio()
            );
        }
    }

    //---------------------- D E S E L E C C I O N A R ----------------

    public void deseleccionar() {

        seleccionada = false;

        setColorBorde(null);
        setGrosorBorde(0);

        if (Lprecio != null) {

            Lprecio.setText(
                    textoPrecio()
            );
        }
    }

//---------------------- C A N T I D A D   A G R E G A D A ----------------

    public int getCantidadAgregada() {

        if (tipo != Tipo.AJUSTABLE) {

            return seleccionada
                    ? 1
                    : 0;
        }

        return Math.max(
                0,
                cantidadActual - cantidadBase
        );
    }

//---------------------- C A N T I D A D   Q U I T A D A ----------------

    public int getCantidadQuitada() {

        if (tipo != Tipo.AJUSTABLE) {

            return 0;
        }

        return Math.max(
                0,
                cantidadBase - cantidadActual
        );
    }

//---------------------- P R E C I O   E X T R A   U N I T A R I O ----------------

    public double getPrecioExtraUnitario() {

        return precioExtra;
    }

//---------------------- T I E N E   M O D I F I C A C I O N ----------------

    public boolean tieneModificacion() {

        if (tipo == Tipo.SELECCION_UNICA) {

            return seleccionada;
        }

        return cantidadActual != cantidadBase;
    }

//---------------------- A C T U A L I Z A R   P R E C I O   E X T R A ----------------

    public void setPrecioExtra(
            double precioExtra
    ) {

        this.precioExtra = precioExtra;

        if (Lprecio != null) {

            Lprecio.setText(
                    textoPrecio()
            );
        }
    }

//---------------------- G E T   P R E C I O   E X T R A ----------------

    public double getPrecioExtra() {

        return precioExtra;
    }

//---------------------- N O T I F I C A R ----------------

    private void notificarCambio() {

        if (listener != null) {

            listener.opcionModificada(
                    this
            );
        }
    }

//---------------------- S E T   C A N T I D A D   A C T U A L ----------------

    public void setCantidadActual(
            int cantidadActual
    ) {

        this.cantidadActual =
                Math.max(
                        0,
                        cantidadActual
                );

        actualizarControl();

        notificarCambio();
    }

//------------------------ G E T T E R S ----------------

    public Ingrediente getIngrediente() {

        return ingrediente;
    }

    public Tipo getTipo() {

        return tipo;
    }

    public int getCantidadBase() {

        return cantidadBase;
    }

    public int getCantidadActual() {

        return cantidadActual;
    }

    public boolean isSeleccionada() {

        return seleccionada;
    }
}
