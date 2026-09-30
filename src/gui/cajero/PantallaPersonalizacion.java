//---------------------------------- P A N T A L L A   P E R S O N A L I Z A C I O N -----------------------
package gui.cajero;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import dao.GrupoPersonalizacionDAO;
import dao.OpcionPersonalizacionDAO;

import gui.componentes.*;
import gui.estilo.*;

import dao.ProductoDAO;

import modelo.*;

public class PantallaPersonalizacion extends VentanaCompra {

//------------------------ A T R I B U T O S ----------------
    private Producto producto;
    private Pedido pedido;

    private List<SeccionPersonalizacion> secciones;

    private GrupoPersonalizacionDAO grupoDAO;
    private OpcionPersonalizacionDAO opcionDAO;

//-------------- Panel izquierdo
    private ImagenEscalada Iproducto;
    private JTextArea Tnombre;
    private JLabel Lprecio;
    private Boton Bconfirmar;
    private JTextArea Tdescripcion;

//-------------- Panel derecho
    private JPanel Psecciones;
    private JScrollPane scroll;

    // Si no es null, significa que se está editando
    // un producto que ya estaba dentro del pedido.
    private ItemPedido itemEditando;

//------------------------ C O N S T R U C T O R ----------------
    //---------------- Constructor para un producto nuevo
    public PantallaPersonalizacion(
            Producto producto,
            Pedido pedido
    ) {

        this(
                producto,
                pedido,
                null
        );
    }

    //---------------- Constructor principal
    public PantallaPersonalizacion(
            Producto producto,
            Pedido pedido,
            ItemPedido itemEditando
    ) {

        super("Personaliza tu producto");

        Producto productoCompleto
                = new ProductoDAO().buscarPorId(
                        producto.getId_Producto()
                );

        if (productoCompleto != null) {

            producto = productoCompleto;
        }

        this.producto = producto;
        this.pedido = pedido;
        this.itemEditando = itemEditando;

        this.secciones = new ArrayList<>();

        this.grupoDAO
                = new GrupoPersonalizacionDAO();

        this.opcionDAO
                = new OpcionPersonalizacionDAO();

        inicializarContenido();

        configurarRegreso();

        mostrarVentana();
    }

//---------------------- C O N T E N I D O ----------------
    private void inicializarContenido() {

        configurarTitulo(
                "¿Cómo lo quieres?"
        );

        inicializarPanelIzquierdo();
        inicializarPanelDerecho();
    }

//---------------------- P A N E L   I Z Q U I E R D O ----------------
    private void inicializarPanelIzquierdo() {

        Pizquierdo.setLayout(
                new GridBagLayout()
        );

        //---------------------- I M A G E N
        Iproducto = new ImagenEscalada(
                producto.getImagen(),
                280,
                280
        );

        Iproducto.setAlineacion(
                SwingConstants.CENTER,
                SwingConstants.CENTER
        );

        agregarIzquierdo(
                Iproducto,
                0,
                0.55,
                new Insets(10, 20, 5, 20),
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH
        );

        //---------------------- N O M B R E
        Tnombre = new JTextArea(
                producto.getNombre()
        );

        Tnombre.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.SUBTITULO
                )
        );

        Tnombre.setForeground(
                Colores.CORINTO
        );

        Tnombre.setOpaque(false);
        Tnombre.setEditable(false);
        Tnombre.setFocusable(false);
        Tnombre.setLineWrap(true);
        Tnombre.setWrapStyleWord(true);
        Tnombre.setBorder(null);
        Tnombre.setRows(2);

        agregarIzquierdo(
                Tnombre,
                1,
                0.07,
                new Insets(5, 20, 2, 20),
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL
        );

        //---------------------- P R E C I O
        Lprecio = new JLabel(
                textoPrecioTotal(),
                SwingConstants.CENTER
        );

        Lprecio.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.SUBTITULO
                )
        );

        Lprecio.setForeground(
                Colores.ROJO
        );

        agregarIzquierdo(
                Lprecio,
                2,
                0.06,
                new Insets(2, 20, 2, 20),
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL
        );

        //---------------------- D E S C R I P C I Ó N
        Tdescripcion = new JTextArea(
                producto.getDescripcion()
        );

        Tdescripcion.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Tdescripcion.setForeground(
                Colores.CORINTO
        );

        Tdescripcion.setOpaque(false);
        Tdescripcion.setEditable(false);
        Tdescripcion.setFocusable(false);
        Tdescripcion.setLineWrap(true);
        Tdescripcion.setWrapStyleWord(true);
        Tdescripcion.setBorder(null);

        agregarIzquierdo(
                Tdescripcion,
                3,
                0.16,
                new Insets(4, 20, 2, 20),
                GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL
        );

        //---------------------- B O T Ó N
        Bconfirmar = new Boton(
                "Guardar"
        );

        Bconfirmar.rojo();

        Bconfirmar.addActionListener(
                e -> guardarPersonalizacion()
        );

        agregarIzquierdo(
                Bconfirmar,
                4,
                0.06,
                new Insets(5, 20, 15, 20),
                GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL
        );
    }

//---------------------- A G R E G A R   A L   I Z Q U I E R D O ----------------
    private void agregarIzquierdo(
            Component componente,
            int fila,
            double peso,
            Insets margen,
            int anchor,
            int fill
    ) {

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = fila;

        gbc.weightx = 1.0;
        gbc.weighty = peso;

        gbc.anchor = anchor;
        gbc.fill = fill;

        gbc.insets = margen;

        Pizquierdo.add(
                componente,
                gbc
        );
    }

//---------------------- P A N E L   D E R E C H O ----------------
    private void inicializarPanelDerecho() {

        Psecciones = new JPanel();

        Psecciones.setOpaque(false);

        Psecciones.setLayout(
                new BoxLayout(
                        Psecciones,
                        BoxLayout.Y_AXIS
                )
        );

        cargarSeccionesDesdeBD();

        //---------------------- S C R O L L
        scroll = new JScrollPane(
                Psecciones
        );

        scroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        scroll.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        //---------------------- C O N T E N E D O R
        JPanel Pcontenedor
                = new JPanel(
                        new BorderLayout()
                );

        Pcontenedor.setOpaque(false);

        Pcontenedor.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        Pcontenedor.add(
                scroll,
                BorderLayout.CENTER
        );

        Pderecho.add(
                Pcontenedor,
                BorderLayout.CENTER
        );
    }

//---------------------- C A R G A R   S E C C I O N E S   D E S D E   B D ----------------
    private void cargarSeccionesDesdeBD() {

        List<Grupo_Personalizacion> grupos
                = grupoDAO.listarPorProducto(
                        producto.getId_Producto()
                );

        //---------------------- S I N   P E R S O N A L I Z A C I O N
        if (grupos.isEmpty()) {

            JLabel Lmensaje
                    = new JLabel(
                            "<html><div style='text-align:center;'>"
                            + "Este producto no tiene opciones de personalización."
                            + "</div></html>",
                            SwingConstants.CENTER
                    );

            Lmensaje.setFont(
                    Fuentes.LFL.deriveFont(
                            Fuentes.PEQUENO
                    )
            );

            Lmensaje.setForeground(
                    Colores.CORINTO
            );

            Psecciones.add(
                    Lmensaje
            );

            return;
        }

        //---------------------- C R E A R   S E C C I O N E S
        for (Grupo_Personalizacion grupo
                : grupos) {

            TarjetaOpcion.Tipo tipo
                    = determinarTipo(
                            grupo
                    );

            //---------------------- S E C C I Ó N
            SeccionPersonalizacion seccion
                    = new SeccionPersonalizacion(
                            grupo.getNombre(),
                            tipo,
                            grupo.isObligatorio(),
                            grupo.getCantidad_minima(),
                            grupo.getCantidad_maxima(),
                            grupo
                    );

            //---------------------- O P C I O N E S
            List<Opcion_Personalizacion> opciones
                    = opcionDAO.listarPorGrupo(
                            grupo.getId_GrupoPersonalizacion()
                    );

            for (Opcion_Personalizacion opcion
                    : opciones) {

                Ingrediente ingrediente
                        = opcion.getIngrediente();

                if (ingrediente == null) {
                    continue;
                }

                //---------------------- D E F A U L T
                int cantidadBase
                        = opcion.isEs_predeterminada()
                        ? opcion.getCantidadBaseReceta()
                        : 0;

                //---------------------- T A R J E T A
                TarjetaOpcion tarjeta
                        = new TarjetaOpcion(
                                ingrediente,
                                tipo,
                                cantidadBase,
                                opcion.getPrecio_extra()
                        );

                tarjeta.setOpcionPersonalizacion(
                        opcion
                );

                seccion.agregarTarjeta(
                        tarjeta
                );
            }

            //---------------------- R E S T A U R A R   E D I C I Ó N
            if (itemEditando != null) {

                restaurarSeccion(
                        seccion,
                        tipo
                );
            }

            agregarSeccion(
                    seccion
            );
        }

        actualizarPrecioTotal();
    }

//---------------------- D E T E R M I N A R   T I P O ----------------
    private TarjetaOpcion.Tipo determinarTipo(
            Grupo_Personalizacion grupo
    ) {

        /*
         * Si el grupo permite solamente una opción,
         * se comporta como selección única.
         *
         * Si permite varias unidades, se comporta
         * como una opción ajustable.
         */
        if (grupo.getCantidad_maxima() <= 1) {

            return TarjetaOpcion.Tipo.SELECCION_UNICA;
        }

        return TarjetaOpcion.Tipo.AJUSTABLE;
    }

//---------------------- R E S T A U R A R   S E C C I Ó N ----------------
    private void restaurarSeccion(
            SeccionPersonalizacion seccion,
            TarjetaOpcion.Tipo tipo
    ) {

        if (itemEditando == null) {
            return;
        }

        List<SeleccionPersonalizacion> selecciones
                = itemEditando.getSeleccionesPersonalizacion();

        if (selecciones == null
                || selecciones.isEmpty()) {

            return;
        }

        //---------------------- S E L E C C I Ó N   U N I C A
        if (tipo
                == TarjetaOpcion.Tipo.SELECCION_UNICA) {

            for (TarjetaOpcion tarjeta
                    : seccion.getTarjetas()) {

                Opcion_Personalizacion opcion
                        = tarjeta.getOpcionPersonalizacion();

                if (opcion == null) {
                    continue;
                }

                for (SeleccionPersonalizacion seleccion
                        : selecciones) {

                    if (seleccion.getOpcion() == null) {
                        continue;
                    }

                    if (seleccion.getTipo()
                            != SeleccionPersonalizacion.Tipo.SELECCION) {

                        continue;
                    }

                    if (seleccion.getOpcion()
                            .getId_OpcionPersonalizacion()
                            == opcion.getId_OpcionPersonalizacion()) {

                        //---------------- D E S M A R C A R   T O D A S
                        for (TarjetaOpcion otra
                                : seccion.getTarjetas()) {

                            otra.deseleccionar();
                        }

                        //---------------- M A R C A R   G U A R D A D A
                        tarjeta.marcarSeleccionada();

                        break;
                    }
                }
            }

            return;
        }

        //---------------------- A J U S T A B L E
        for (TarjetaOpcion tarjeta
                : seccion.getTarjetas()) {

            Opcion_Personalizacion opcion
                    = tarjeta.getOpcionPersonalizacion();

            if (opcion == null) {
                continue;
            }

            int cantidadActual
                    = tarjeta.getCantidadActual();

            for (SeleccionPersonalizacion seleccion
                    : selecciones) {

                if (seleccion.getOpcion() == null) {
                    continue;
                }

                if (seleccion.getOpcion()
                        .getId_OpcionPersonalizacion()
                        != opcion.getId_OpcionPersonalizacion()) {

                    continue;
                }

                //---------------- A G R E G A R
                if (seleccion.getTipo()
                        == SeleccionPersonalizacion.Tipo.AGREGAR) {

                    cantidadActual
                            += seleccion.getCantidad();
                }

                //---------------- Q U I T A R
                if (seleccion.getTipo()
                        == SeleccionPersonalizacion.Tipo.QUITAR) {

                    cantidadActual
                            -= seleccion.getCantidad();
                }
            }

            //---------------------- E V I T A R   N E G A T I V O
            if (cantidadActual < 0) {

                cantidadActual = 0;
            }

            //---------------------- A P L I C A R
            tarjeta.setCantidadActual(
                    cantidadActual
            );
        }
    }

//---------------------- A G R E G A R   S E C C I Ó N ----------------
    private void agregarSeccion(
            SeccionPersonalizacion seccion
    ) {

        seccion.setSeccionListener(
                this::actualizarPrecioTotal
        );

        secciones.add(
                seccion
        );

        Psecciones.add(
                seccion
        );

        Psecciones.add(
                Box.createVerticalStrut(
                        20
                )
        );
    }

//---------------------- A C T U A L I Z A R   P R E C I O ----------------
    private void actualizarPrecioTotal() {

        if (Lprecio == null) {
            return;
        }

        Lprecio.setText(
                textoPrecioTotal()
        );
    }

//---------------------- T E X T O   P R E C I O   T O T A L ----------------
    private String textoPrecioTotal() {

        double total
                = producto.getPrecio();

        for (SeccionPersonalizacion seccion
                : secciones) {

            for (TarjetaOpcion tarjeta
                    : seccion.getTarjetas()) {

                total
                        += tarjeta.getExtraPrecio();
            }
        }

        return "Q"
                + String.format(
                        "%.2f",
                        total
                );
    }

//---------------------- G U A R D A R   P E R S O N A L I Z A C I Ó N ----------------
    private void guardarPersonalizacion() {

        //---------------------- V A L I D A R
        for (SeccionPersonalizacion seccion
                : secciones) {

            if (!seccion.esValida()) {

                Dialogo.aviso(
                        this,
                        "Personalización incompleta",
                        "Debes completar correctamente la sección: "
                        + seccion.getTitulo()
                );

                return;
            }
        }

        //---------------------- O B T E N E R   I T E M
        ItemPedido item;

        if (itemEditando != null) {

            //---------------------- E D I T A R   E X I S T E N T E
            item = itemEditando;

            item.limpiarPersonalizacionesDeProducto(
                    producto.getId_Producto()
            );

        } else {

            //---------------------- C R E A R   N U E V O
            item = new ItemPedido(
                    producto,
                    null,
                    new ArrayList<>()
            );
        }

        //---------------------- G U A R D A R   S E L E C C I O N E S
        for (SeccionPersonalizacion seccion
                : secciones) {

            /*
             * La propia sección ya conoce
             * qué Grupo_Personalizacion representa.
             */
            Grupo_Personalizacion grupo
                    = seccion.getGrupo();

            for (TarjetaOpcion tarjeta
                    : seccion.getTarjetas()) {

                Opcion_Personalizacion opcion
                        = tarjeta.getOpcionPersonalizacion();

                if (opcion == null) {
                    continue;
                }

                //---------------------- S E L E C C I Ó N   U N I C A
                if (tarjeta.getTipo()
                        == TarjetaOpcion.Tipo.SELECCION_UNICA) {

                    if (!tarjeta.isSeleccionada()) {
                        continue;
                    }

                    SeleccionPersonalizacion seleccion
                            = new SeleccionPersonalizacion(
                                    grupo,
                                    opcion,
                                    1,
                                    tarjeta.getPrecioExtraUnitario(),
                                    SeleccionPersonalizacion.Tipo.SELECCION
                            );

                    item.agregarPersonalizacion(
                            seleccion
                    );

                    continue;
                }

                //---------------------- A G R E G A R
                int cantidadAgregada
                        = tarjeta.getCantidadAgregada();

                if (cantidadAgregada > 0) {

                    SeleccionPersonalizacion seleccion
                            = new SeleccionPersonalizacion(
                                    grupo,
                                    opcion,
                                    cantidadAgregada,
                                    tarjeta.getPrecioExtraUnitario(),
                                    SeleccionPersonalizacion.Tipo.AGREGAR
                            );

                    item.agregarPersonalizacion(
                            seleccion
                    );
                }

                //---------------------- Q U I T A R
                int cantidadQuitada
                        = tarjeta.getCantidadQuitada();

                if (cantidadQuitada > 0) {

                    SeleccionPersonalizacion seleccion
                            = new SeleccionPersonalizacion(
                                    grupo,
                                    opcion,
                                    cantidadQuitada,
                                    0.00,
                                    SeleccionPersonalizacion.Tipo.QUITAR
                            );

                    item.agregarPersonalizacion(
                            seleccion
                    );
                }
            }
        }

        //---------------------- A G R E G A R   S O L O   S I   E S   N U E V O
        if (itemEditando == null) {

            pedido.agregarItem(
                    item
            );
        }

        //---------------------- V O L V E R
        new ResumenCombo(
                pedido,
                "Tu pedido"
        );

        dispose();
    }

//------------------------ R E G R E S A R ----------------
    private void configurarRegreso() {

        Bregresar.addActionListener(
                e -> {

                    new ResumenCombo(
                            pedido,
                            "Tu pedido"
                    );

                    dispose();
                }
        );
    }
}
