//---------------------------------- P A N E L   C O N T E N I D O   M E N U -----------------------
package gui.cajero;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import gui.estilo.*;
import modelo.*;
import logica.LogicaMenu;

public class PanelContenidoMenu extends JPanel {

    //------------------------  A T R I B U T O S ----------------
    //-------------- Panel de contenido
    private JPanel Pcontenido;

    //-------------- Listener
    private ProductoListener productoListener;

    private ComboListener comboListener;

    //------------------------ C O M B O   L I S T E N E R ----------------
    public interface ComboListener {

        void comboSeleccionado(
                Combo combo
        );
    }

//-------------------- C O N S T R U C T O R -----------------
    public PanelContenidoMenu(
            String categoria
    ) {

        setOpaque(false);

        inicializarComponentes(
                categoria
        );
    }

    //-------------------- C O N S T R U C T O R   F I L T R A D O -----------------
    public PanelContenidoMenu(
            String titulo,
            List<SubcategoriaMenu> subcategorias
    ) {

        setOpaque(false);

        inicializarContenido();

        agregarCategoria(
                titulo
        );

        for (SubcategoriaMenu subcategoria
                : subcategorias) {

            agregarSubcategoria(
                    subcategoria
            );
        }
    }

    //---------------------- L I S T E N E R
    public interface ProductoListener {

        void productoSeleccionado(
                Producto producto
        );
    }

    //---------------------- S E T T E R
    public void setProductoListener(
            ProductoListener listener
    ) {

        this.productoListener = listener;
    }

    //------------------------ S E T T E R ----------------
    public void setComboListener(
            ComboListener listener
    ) {

        this.comboListener = listener;
    }

//---------------------- I N I C I A L I Z A R
    private void inicializarComponentes(
            String categoria
    ) {

        inicializarContenido();

        //---------------------- C A J I T A
        if (categoria.equals(
                "Cajita Feliz"
        )) {

            agregarCategoria(
                    categoria
            );

            agregarCajitas();

            return;
        }

        //---------------------- P R O D U C T O S
        List<SubcategoriaMenu> subcategorias
                = LogicaMenu.obtenerSubcategorias(
                        categoria
                );

        agregarCategoria(
                categoria
        );

        for (SubcategoriaMenu subcategoria : subcategorias) {

            agregarSubcategoria(
                    subcategoria
            );
        }
    }

    //---------------------- A G R E G A R   C A J I T A S
    private void agregarCajitas() {

        JPanel Pcombos
                = new JPanel(
                        new GridBagLayout()
                );

        Pcombos.setOpaque(false);

        Pcombos.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        List<Combo> combos
                = LogicaMenu.obtenerCajitas();

        int columna = 0;
        int fila = 0;

        for (Combo combo : combos) {

            ComboMenu comboMenu
                    = new ComboMenu(
                            combo
                    );

            comboMenu.setComboListener(
                    comboSeleccionado -> {

                        if (comboListener != null) {

                            comboListener.comboSeleccionado(
                                    comboSeleccionado
                            );
                        }
                    }
            );

            GridBagConstraints gbc
                    = new GridBagConstraints();

            gbc.gridx = columna;
            gbc.gridy = fila;

            gbc.weightx = 0;
            gbc.weighty = 0;

            gbc.fill = GridBagConstraints.NONE;

            gbc.anchor
                    = GridBagConstraints.CENTER;

            gbc.insets = new Insets(
                    10,
                    10,
                    10,
                    10
            );

            Pcombos.add(
                    comboMenu,
                    gbc
            );

            columna++;

            if (columna == 2) {

                columna = 0;
                fila++;
            }
        }

        Pcontenido.add(
                Pcombos
        );
    }

    //---------------------- A G R E G A R   C O M B O
    private void agregarCombo(
            JPanel panel,
            ComboMenu combo,
            int columna,
            int fila
    ) {

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = columna;
        gbc.gridy = fila;

        gbc.insets = new Insets(
                10,
                10,
                10,
                10
        );

        gbc.anchor
                = GridBagConstraints.CENTER;

        panel.add(
                combo,
                gbc
        );
    }

    //---------------------- C O N T E N I D O
    private void inicializarContenido() {

        Pcontenido = new JPanel();

        Pcontenido.setOpaque(false);

        Pcontenido.setLayout(
                new BoxLayout(
                        Pcontenido,
                        BoxLayout.Y_AXIS
                )
        );

        add(
                Pcontenido
        );
    }

    //---------------------- A G R E G A R   C A T E G O R Í A
    private void agregarCategoria(
            String nombre
    ) {

        JLabel titulo
                = crearTituloCategoria(
                        nombre
                );

        titulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        Pcontenido.add(
                titulo
        );

        Pcontenido.add(
                Box.createVerticalStrut(
                        15
                )
        );
    }

    //---------------------- A G R E G A R   S U B C A T E G O R Í A
    private void agregarSubcategoria(
            SubcategoriaMenu subcategoria
    ) {

        JLabel titulo
                = crearTituloSubcategoria(
                        subcategoria.getNombre()
                );

        titulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        Pcontenido.add(
                titulo
        );

        JPanel PproductosSubcategoria
                = crearPanelProductos(
                        subcategoria.getProductos()
                );

        Pcontenido.add(
                PproductosSubcategoria
        );

        Pcontenido.add(
                Box.createVerticalStrut(
                        20
                )
        );
    }

    //---------------------- P A N E L   P R O D U C T O S
    private JPanel crearPanelProductos(
            List<Producto> productos
    ) {

        JPanel panel
                = new JPanel(
                        new GridBagLayout()
                );

        panel.setOpaque(false);

        panel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        agregarProductos(
                panel,
                productos
        );

        return panel;
    }

    //---------------------- A G R E G A R   P R O D U C T O S
    private void agregarProductos(
            JPanel panel,
            List<Producto> productos
    ) {

        int columna = 0;
        int fila = 0;

        for (Producto producto : productos) {

            ProductoMenu productoMenu
                    = new ProductoMenu(
                            producto
                    );

            productoMenu.setProductoListener(
                    productoSeleccionado -> {

                        if (productoListener != null) {

                            productoListener.productoSeleccionado(
                                    productoSeleccionado
                            );
                        }
                    }
            );

            agregarProducto(
                    panel,
                    productoMenu,
                    columna,
                    fila
            );

            columna++;

            if (columna == 2) {

                columna = 0;
                fila++;
            }
        }
    }

    //---------------------- A G R E G A R   P R O D U C T O
    private void agregarProducto(
            JPanel panel,
            ProductoMenu producto,
            int columna,
            int fila
    ) {

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = columna;
        gbc.gridy = fila;

        gbc.insets = new Insets(
                10,
                10,
                10,
                10
        );

        gbc.anchor
                = GridBagConstraints.CENTER;

        panel.add(
                producto,
                gbc
        );
    }

    //---------------------- C R E A R   T Í T U L O   C A T E G O R Í A
    private JLabel crearTituloCategoria(
            String nombre
    ) {

        JLabel titulo
                = new JLabel(
                        nombre
                );

        titulo.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.SUBTITULO
                )
        );

        titulo.setForeground(
                Colores.CORINTO
        );

        titulo.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        return titulo;
    }

    //---------------------- C R E A R   T Í T U L O   S U B C A T E G O R Í A
    private JLabel crearTituloSubcategoria(
            String nombre
    ) {

        JLabel titulo
                = new JLabel(
                        nombre
                );

        titulo.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.NORMAL
                )
        );

        titulo.setForeground(
                Colores.ROJO
        );

        titulo.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        return titulo;
    }
}
