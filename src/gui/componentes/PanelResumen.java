//---------------------- P A N E L   R E S U M E N  ----------------------
package gui.componentes;

import java.awt.*;
import javax.swing.*;
import gui.estilo.*;
import modelo.*;

public class PanelResumen extends PanelColor {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Panel de contenido
    private JPanel Pcontenido;

    //-------------- Fila actual
    private int fila;

    //-------------- Mostrar botón editar
    private boolean mostrarBotonEditar = true;

    //------------------------ C O N S T R U C T O R ----------------
    public PanelResumen() {

        fila = 0;

        //---------------------- E S T I L O
        setColorFondo(
                Colores.BLANCO
        );

        setOpacidad(255);

        setRadio(30);

        setColorBorde(
                Colores.CORINTO
        );

        setGrosorBorde(2);

        //---------------------- D I S T R I B U C I Ó N
        setLayout(
                new BorderLayout()
        );

        inicializarContenido();
    }

    //---------------------- M O S T R A R   B O T Ó N   E D I T A R ----------------
    public void setMostrarBotonEditar(
            boolean mostrar
    ) {

        this.mostrarBotonEditar = mostrar;
    }

    //---------------------- C O N T E N I D O ----------------
    private void inicializarContenido() {

        Pcontenido = new JPanel(
                new GridBagLayout()
        );

        Pcontenido.setOpaque(false);

        Pcontenido.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        add(
                Pcontenido,
                BorderLayout.NORTH
        );
    }

    //---------------------- T Í T U L O ----------------
    public void setTitulo(
            String titulo
    ) {

        JLabel Ltitulo = new JLabel(
                titulo
        );

        Ltitulo.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.SUBTITULO
                )
        );

        Ltitulo.setForeground(
                Colores.NEGRO
        );

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = fila;

        gbc.gridwidth = 3;

        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                0,
                0,
                20,
                0
        );

        Pcontenido.add(
                Ltitulo,
                gbc
        );

        fila++;

        revalidate();
        repaint();
    }

//---------------------- P E D I D O ----------------
    public void agregarPedido(
            Pedido pedido
    ) {

        for (ItemPedido item
                : pedido.getItems()) {

            agregarItemPedido(
                    item
            );
        }

        revalidate();
        repaint();
    }

    //---------------------- I T E M   P E D I D O ----------------
    private void agregarItemPedido(
            ItemPedido item
    ) {

        //---------------------- C O M B O
        if (item.getCombo() != null) {

            agregarTituloCombo(
                    item
            );

            //---------------------- P R O D U C T O   B A S E
            if (item.getProductoBase() != null) {

                agregarProductoDetalle(
                        item,
                        item.getProductoBase()
                );
            }

            //---------------------- S E L E C C I O N E S   C O M B O
            for (SeleccionCombo seleccion
                    : item.getSelecciones()) {

                if (seleccion.getProducto() != null) {

                    agregarProductoDetalle(
                            item,
                            seleccion.getProducto()
                    );
                }
            }

            return;
        }

        //---------------------- P R O D U C T O   I N D I V I D U A L
        if (item.getProductoBase() != null) {

            agregarProductoPersonalizado(
                    item
            );
        }
    }

//---------------------- T Í T U L O   C O M B O ----------------
    private void agregarTituloCombo(
            ItemPedido item
    ) {

        JLabel Ltitulo = new JLabel(
                item.getCombo().getNombre()
                + "    Q."
                + String.format(
                        "%.2f",
                        item.obtenerPrecio()
                )
        );

        Ltitulo.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.SUBTITULO
                )
        );

        Ltitulo.setForeground(
                Colores.NEGRO
        );

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = fila;

        gbc.gridwidth = 3;

        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        gbc.anchor
                = GridBagConstraints.WEST;

        gbc.fill
                = GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                15,
                0,
                5,
                0
        );

        Pcontenido.add(
                Ltitulo,
                gbc
        );

        fila++;
    }

    //---------------------- P R O D U C T O   D E   C O M B O ----------------
    private void agregarProductoDetalle(
            ItemPedido item,
            Producto producto
    ) {

        //---------------------- N O M B R E
        JLabel Lproducto = new JLabel(
                "    - "
                + producto.getNombre()
        );

        Lproducto.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lproducto.setForeground(
                Colores.CORINTO
        );

        //---------------------- P R E C I O
        JLabel Lprecio = new JLabel(
                "Q."
                + String.format(
                        "%.2f",
                        producto.getPrecio()
                )
        );

        Lprecio.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lprecio.setForeground(
                Colores.CORINTO
        );

        //---------------------- N O M B R E
        GridBagConstraints gbcProducto
                = new GridBagConstraints();

        gbcProducto.gridx = 0;
        gbcProducto.gridy = fila;

        gbcProducto.weightx = 1.0;
        gbcProducto.weighty = 0.0;

        gbcProducto.anchor
                = GridBagConstraints.WEST;

        gbcProducto.fill
                = GridBagConstraints.HORIZONTAL;

        gbcProducto.insets
                = new Insets(
                        2,
                        0,
                        2,
                        0
                );

        Pcontenido.add(
                Lproducto,
                gbcProducto
        );

        //---------------------- P R E C I O
        GridBagConstraints gbcPrecio
                = new GridBagConstraints();

        gbcPrecio.gridx = 1;
        gbcPrecio.gridy = fila;

        gbcPrecio.weightx = 0.0;
        gbcPrecio.weighty = 0.0;

        gbcPrecio.anchor
                = GridBagConstraints.EAST;

        gbcPrecio.insets
                = new Insets(
                        2,
                        15,
                        2,
                        15
                );

        Pcontenido.add(
                Lprecio,
                gbcPrecio
        );

        //---------------------- L Á P I Z
        agregarBotonEditar(
                item,
                producto,
                fila
        );

        //---------------------- S I G U I E N T E   F I L A
        fila++;

        //---------------------- P E R S O N A L I Z A C I O N E S
        agregarPersonalizaciones(
                item,
                producto
        );
    }

//---------------------- P R O D U C T O   P E R S O N A L I Z A D O ----------------
    private void agregarProductoPersonalizado(
            ItemPedido item
    ) {

        Producto producto
                = item.getProductoBase();

        //---------------------- N O M B R E
        JLabel Lproducto = new JLabel(
                "- " + producto.getNombre()
        );

        Lproducto.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lproducto.setForeground(
                Colores.CORINTO
        );

        //---------------------- P R E C I O
        JLabel Lprecio = new JLabel(
                "Q."
                + String.format(
                        "%.2f",
                        item.obtenerPrecio()
                )
        );

        Lprecio.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lprecio.setForeground(
                Colores.CORINTO
        );

        //---------------------- N O M B R E
        GridBagConstraints gbcProducto
                = new GridBagConstraints();

        gbcProducto.gridx = 0;
        gbcProducto.gridy = fila;

        gbcProducto.weightx = 1.0;
        gbcProducto.weighty = 0.0;

        gbcProducto.anchor
                = GridBagConstraints.WEST;

        gbcProducto.fill
                = GridBagConstraints.HORIZONTAL;

        gbcProducto.insets
                = new Insets(
                        5,
                        0,
                        5,
                        0
                );

        Pcontenido.add(
                Lproducto,
                gbcProducto
        );

        //---------------------- P R E C I O
        GridBagConstraints gbcPrecio
                = new GridBagConstraints();

        gbcPrecio.gridx = 1;
        gbcPrecio.gridy = fila;

        gbcPrecio.weightx = 0.0;
        gbcPrecio.weighty = 0.0;

        gbcPrecio.anchor
                = GridBagConstraints.EAST;

        gbcPrecio.insets
                = new Insets(
                        5,
                        15,
                        5,
                        15
                );

        Pcontenido.add(
                Lprecio,
                gbcPrecio
        );

        //---------------------- L Á P I Z
        agregarBotonEditar(
                item,
                producto,
                fila
        );

        //---------------------- S I G U I E N T E   F I L A
        fila++;

        //---------------------- P E R S O N A L I Z A C I O N E S
        agregarPersonalizaciones(
                item,
                producto
        );
    }

//---------------------- E D I T A R ----------------
    private void agregarBotonEditar(
            ItemPedido item,
            Producto producto,
            int fila
    ) {

        if (!mostrarBotonEditar) {
            return;
        }

        BotonEditar Beditar
                = new BotonEditar();

        Beditar.addActionListener(
                e -> {

                    if (resumenListener != null) {

                        resumenListener.editarProducto(
                                item,
                                producto
                        );
                    }
                }
        );

        GridBagConstraints gbcEditar
                = new GridBagConstraints();

        gbcEditar.gridx = 2;
        gbcEditar.gridy = fila;

        gbcEditar.weightx = 0.0;
        gbcEditar.weighty = 0.0;

        gbcEditar.anchor
                = GridBagConstraints.CENTER;

        gbcEditar.insets
                = new Insets(
                        2,
                        10,
                        2,
                        0
                );

        Pcontenido.add(
                Beditar,
                gbcEditar
        );
    }

//---------------------- P E R S O N A L I Z A C I O N E S ----------------
    private void agregarPersonalizaciones(
            ItemPedido item,
            Producto producto
    ) {

        if (item.getSeleccionesPersonalizacion() == null
                || item.getSeleccionesPersonalizacion().isEmpty()) {

            return;
        }

        for (SeleccionPersonalizacion seleccion
                : item.getSeleccionesPersonalizacion()) {

            //---------------------- G R U P O
            Grupo_Personalizacion grupo
                    = seleccion.getGrupo();

            if (grupo == null) {
                continue;
            }

            //---------------------- V E R I F I C A R   P R O D U C T O
            if (grupo.getId_Producto()
                    != producto.getId_Producto()) {

                continue;
            }

            //---------------------- O P C I O N
            if (seleccion.getOpcion() == null) {
                continue;
            }

            Ingrediente ingrediente
                    = seleccion.getOpcion()
                            .getIngrediente();

            if (ingrediente == null) {
                continue;
            }

            //---------------------- C A N T I D A D
            int cantidad
                    = seleccion.getCantidad();

            //---------------------- P R E F I J O
            String prefijo;

            if (seleccion.getTipo()
                    == SeleccionPersonalizacion.Tipo.QUITAR) {

                prefijo = "    - ";

            } else {

                prefijo = "    + ";
            }

            //---------------------- T E X T O
            String texto
                    = prefijo
                    + ingrediente.getNombre();

            if (cantidad > 1) {

                texto
                        += " x" + cantidad;
            }

            //---------------------- P R E C I O
            double precio
                    = seleccion.getPrecioTotal();

            if (seleccion.getTipo()
                    != SeleccionPersonalizacion.Tipo.QUITAR
                    && precio > 0) {

                texto
                        += "    +Q"
                        + String.format(
                                "%.2f",
                                precio
                        );
            }

            //---------------------- L A B E L
            JLabel Lpersonalizacion
                    = new JLabel(
                            texto
                    );

            Lpersonalizacion.setFont(
                    Fuentes.LFL.deriveFont(
                            Fuentes.PEQUENO
                    )
            );

            Lpersonalizacion.setForeground(
                    Colores.ROJO
            );

            //---------------------- C O N S T R A I N T S
            GridBagConstraints gbc
                    = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = fila;

            gbc.gridwidth = 3;

            gbc.weightx = 1.0;
            gbc.weighty = 0.0;

            gbc.anchor
                    = GridBagConstraints.WEST;

            gbc.fill
                    = GridBagConstraints.HORIZONTAL;

            gbc.insets
                    = new Insets(
                            1,
                            30,
                            1,
                            0
                    );

            Pcontenido.add(
                    Lpersonalizacion,
                    gbc
            );

            fila++;
        }
    }

//---------------------- P R O D U C T O ----------------
    public void agregarProducto(
            Producto producto
    ) {

        //---------------------- N O M B R E
        JLabel Lproducto = new JLabel(
                "- " + producto.getNombre()
        );

        Lproducto.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lproducto.setForeground(
                Colores.CORINTO
        );

        //---------------------- P R E C I O
        JLabel Lprecio = new JLabel(
                "Q."
                + String.format(
                        "%.2f",
                        producto.getPrecio()
                )
        );

        Lprecio.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lprecio.setForeground(
                Colores.CORINTO
        );

        //---------------------- N O M B R E
        GridBagConstraints gbcProducto
                = new GridBagConstraints();

        gbcProducto.gridx = 0;
        gbcProducto.gridy = fila;

        gbcProducto.weightx = 1.0;
        gbcProducto.weighty = 0.0;

        gbcProducto.anchor
                = GridBagConstraints.WEST;

        gbcProducto.fill
                = GridBagConstraints.HORIZONTAL;

        gbcProducto.insets
                = new Insets(
                        5,
                        0,
                        5,
                        0
                );

        Pcontenido.add(
                Lproducto,
                gbcProducto
        );

        //---------------------- P R E C I O
        GridBagConstraints gbcPrecio
                = new GridBagConstraints();

        gbcPrecio.gridx = 1;
        gbcPrecio.gridy = fila;

        gbcPrecio.weightx = 0.0;
        gbcPrecio.weighty = 0.0;

        gbcPrecio.anchor
                = GridBagConstraints.EAST;

        gbcPrecio.insets
                = new Insets(
                        5,
                        15,
                        5,
                        15
                );

        Pcontenido.add(
                Lprecio,
                gbcPrecio
        );

        //---------------------- S I N   E D I T A R

        /*
         * Este método recibe solamente Producto.
         *
         * Se utiliza cuando se muestra un producto
         * que no pertenece a un ItemPedido editable,
         * por lo que no se agrega botón editar.
         */
        //---------------------- S I G U I E N T E   F I L A
        fila++;

        revalidate();
        repaint();
    }

//---------------------- D E T A L L E ----------------
    public void agregarDetalle(
            String nombre
    ) {

        JLabel Ldetalle
                = new JLabel(
                        "    - " + nombre
                );

        Ldetalle.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Ldetalle.setForeground(
                Colores.ROJO
        );

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = fila;

        gbc.gridwidth = 3;

        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        gbc.anchor
                = GridBagConstraints.WEST;

        gbc.fill
                = GridBagConstraints.HORIZONTAL;

        gbc.insets
                = new Insets(
                        0,
                        0,
                        3,
                        0
                );

        Pcontenido.add(
                Ldetalle,
                gbc
        );

        fila++;

        revalidate();
        repaint();
    }

//---------------------- L I S T E N E R ----------------
    public interface ResumenListener {

        void editarProducto(
                ItemPedido item,
                Producto producto
        );
    }

    private ResumenListener resumenListener;

    public void setResumenListener(
            ResumenListener listener
    ) {

        this.resumenListener = listener;
    }

}
