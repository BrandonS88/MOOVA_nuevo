//---------------------------------- S E C C I O N   P E R S O N A L I Z A C I O N -----------------------
package gui.cajero;

import gui.componentes.*;
import gui.estilo.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import modelo.*;

public class SeccionPersonalizacion extends JPanel {

    //------------------------ A T R I B U T O S ----------------
    private String titulo;
    private TarjetaOpcion.Tipo tipo;
    private List<TarjetaOpcion> tarjetas;

    // Reglas obtenidas de Grupo_Personalizacion
    private boolean obligatorio;
    private int cantidadMinima;
    private int cantidadMaxima;

    private JPanel Pcontenido;

    // Grupo al que pertenece esta sección
    private Grupo_Personalizacion grupo;

    //-------------- Listener
    private SeccionListener listener;

    //------------------------ L I S T E N E R ----------------
    public interface SeccionListener {

        void seccionModificada();
    }

    public void setSeccionListener(
            SeccionListener listener
    ) {

        this.listener = listener;
    }

    //------------------------ C O N S T R U C T O R ----------------
    //---------------- Constructor compatible con el anterior
    public SeccionPersonalizacion(
            String titulo,
            TarjetaOpcion.Tipo tipo,
            Grupo_Personalizacion grupo
    ) {

        this(
                titulo,
                tipo,
                grupo != null && grupo.isObligatorio(),
                grupo != null
                        ? grupo.getCantidad_minima()
                        : 0,
                grupo != null
                        ? grupo.getCantidad_maxima()
                        : Integer.MAX_VALUE,
                grupo
        );
    }

    //---------------- Constructor con reglas
    public SeccionPersonalizacion(
            String titulo,
            TarjetaOpcion.Tipo tipo,
            boolean obligatorio,
            int cantidadMinima,
            int cantidadMaxima
    ) {

        this(
                titulo,
                tipo,
                obligatorio,
                cantidadMinima,
                cantidadMaxima,
                null
        );
    }

    //---------------- Constructor completo
    public SeccionPersonalizacion(
            String titulo,
            TarjetaOpcion.Tipo tipo,
            boolean obligatorio,
            int cantidadMinima,
            int cantidadMaxima,
            Grupo_Personalizacion grupo
    ) {

        this.titulo = titulo;
        this.tipo = tipo;
        this.obligatorio = obligatorio;
        this.cantidadMinima = cantidadMinima;
        this.cantidadMaxima = cantidadMaxima;
        this.grupo = grupo;

        this.tarjetas = new ArrayList<>();

        setOpaque(false);

        setLayout(
                new BorderLayout()
        );

        inicializarTitulo();
        inicializarContenido();
    }

    //------------------------ T I T U L O ----------------
    private void inicializarTitulo() {

        JLabel Ltitulo
                = new JLabel(
                        titulo
                );

        Ltitulo.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Ltitulo.setForeground(
                Colores.CORINTO
        );

        Ltitulo.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        0,
                        8,
                        0
                )
        );

        add(
                Ltitulo,
                BorderLayout.NORTH
        );
    }

    //------------------------ C O N T E N I D O ----------------
    private void inicializarContenido() {

        Pcontenido
                = new JPanel(
                        new WrapLayout(
                                FlowLayout.LEFT,
                                12,
                                8
                        )
                );

        Pcontenido.setOpaque(false);

        add(
                Pcontenido,
                BorderLayout.CENTER
        );
    }

    //------------------------ A G R E G A R   T A R J E T A ----------------
    public void agregarTarjeta(
            TarjetaOpcion tarjeta
    ) {

        tarjeta.setTarjetaOpcionListener(
                t -> manejarCambio(t)
        );

        tarjetas.add(
                tarjeta
        );

        Pcontenido.add(
                tarjeta
        );
    }

    //------------------------ M A N E J A R   C A M B I O ----------------
    private void manejarCambio(
            TarjetaOpcion tarjeta
    ) {

        //---------------------- S E L E C C I O N   U N I C A
        if (tipo
                == TarjetaOpcion.Tipo.SELECCION_UNICA) {

            //---------------------- D E S E L E C C I O N A R   O T R A S
            if (tarjeta.isSeleccionada()) {

                for (TarjetaOpcion t
                        : tarjetas) {

                    if (t != tarjeta) {

                        t.deseleccionar();
                    }
                }

            } else {

                //---------------------- R E G L A   M I N I M A
                // Si el grupo requiere al menos una opción
                // y el usuario intentó dejarlo vacío,
                // volvemos a seleccionar la tarjeta.
                if (obtenerCantidadSeleccionada() == 0
                        && obtenerMinimoReal() > 0) {

                    tarjeta.marcarSeleccionada();
                }
            }
        }

        //---------------------- A V I S A R   A L   P A D R E
        if (listener != null) {

            listener.seccionModificada();
        }
    }

    //---------------------- O B T E N E R   M I N I M O   R E A L ----------------
    private int obtenerMinimoReal() {

        if (obligatorio) {

            return Math.max(
                    1,
                    cantidadMinima
            );
        }

        return cantidadMinima;
    }

    //---------------------- O B T E N E R   C A N T I D A D   S E L E C C I O N A D A ----------------
    public int obtenerCantidadSeleccionada() {

        int cantidad = 0;

        for (TarjetaOpcion tarjeta
                : tarjetas) {

            if (tipo
                    == TarjetaOpcion.Tipo.SELECCION_UNICA) {

                if (tarjeta.isSeleccionada()) {

                    cantidad++;
                }

            } else {

                cantidad
                        += tarjeta.getCantidadActual();
            }
        }

        return cantidad;
    }

    //---------------------- V A L I D A R   R E G L A S ----------------
    public boolean esValida() {

        int cantidad
                = obtenerCantidadSeleccionada();

        int minimo
                = obtenerMinimoReal();

        return cantidad >= minimo
                && cantidad <= cantidadMaxima;
    }

    //---------------------- G E T T E R S ----------------
    public List<TarjetaOpcion> getTarjetas() {

        return tarjetas;
    }

    public TarjetaOpcion.Tipo getTipo() {

        return tipo;
    }

    public String getTitulo() {

        return titulo;
    }

    public boolean isObligatorio() {

        return obligatorio;
    }

    public int getCantidadMinima() {

        return cantidadMinima;
    }

    public int getCantidadMaxima() {

        return cantidadMaxima;
    }

    public Grupo_Personalizacion getGrupo() {

        return grupo;
    }

//---------------------- W R A P   L A Y O U T ----------------
// Permite que las tarjetas ocupen el ancho disponible
// y pasen a la siguiente fila cuando ya no caben.
    private static class WrapLayout extends FlowLayout {

        public WrapLayout(
                int alineacion,
                int hgap,
                int vgap
        ) {

            super(
                    alineacion,
                    hgap,
                    vgap
            );
        }

        @Override
        public Dimension preferredLayoutSize(
                Container target
        ) {

            return calcularTamano(
                    target,
                    true
            );
        }

        @Override
        public Dimension minimumLayoutSize(
                Container target
        ) {

            Dimension minimo
                    = calcularTamano(
                            target,
                            false
                    );

            minimo.width
                    -= getHgap()
                    + 1;

            return minimo;
        }

        private Dimension calcularTamano(
                Container target,
                boolean preferido
        ) {

            synchronized (target.getTreeLock()) {

                Insets insets
                        = target.getInsets();

                int anchoDisponible
                        = target.getWidth();

                /*
             * Cuando Swing todavía no le ha asignado
             * un ancho al panel, usamos el ancho de
             * su contenedor padre.
                 */
                if (anchoDisponible <= 0
                        && target.getParent() != null) {

                    anchoDisponible
                            = target.getParent().getWidth();
                }

                anchoDisponible
                        -= insets.left
                        + insets.right;

                if (anchoDisponible <= 0) {

                    anchoDisponible = 500;
                }

                int anchoFila = 0;
                int altoFila = 0;

                int anchoTotal = 0;
                int altoTotal = 0;

                Component[] componentes
                        = target.getComponents();

                for (Component componente
                        : componentes) {

                    if (!componente.isVisible()) {

                        continue;
                    }

                    Dimension tamano
                            = preferido
                                    ? componente.getPreferredSize()
                                    : componente.getMinimumSize();

                    int anchoComponente
                            = tamano.width;

                    int altoComponente
                            = tamano.height;

                    //---------------------- S I G U I E N T E   T A R J E T A
                    if (anchoFila == 0
                            || anchoFila
                            + getHgap()
                            + anchoComponente
                            <= anchoDisponible) {

                        if (anchoFila > 0) {

                            anchoFila
                                    += getHgap();
                        }

                        anchoFila
                                += anchoComponente;

                        altoFila
                                = Math.max(
                                        altoFila,
                                        altoComponente
                                );

                    } else {

                        //---------------------- N U E V A   F I L A
                        anchoTotal
                                = Math.max(
                                        anchoTotal,
                                        anchoFila
                                );

                        altoTotal
                                += altoFila
                                + getVgap();

                        anchoFila
                                = anchoComponente;

                        altoFila
                                = altoComponente;
                    }
                }

                anchoTotal
                        = Math.max(
                                anchoTotal,
                                anchoFila
                        );

                altoTotal
                        += altoFila;

                return new Dimension(
                        anchoTotal
                        + insets.left
                        + insets.right,
                        altoTotal
                        + insets.top
                        + insets.bottom
                );
            }
        }
    }
}
