//---------------------------------- P A N E L   C O N T E N I D O   C O M B O S -----------------------
package gui.cajero;

import dao.ComboDAO;
import gui.componentes.PanelColor;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import modelo.*;

public class PanelContenidoCombos extends JPanel {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Panel de contenido
    private JPanel Pcontenido;
    private Producto producto;

    //-------------- Listener
    private ComboListener comboListener;
    private ProductoListener productoListener;

    //------------------------ C O N S T R U C T O R ----------------
    public PanelContenidoCombos(
            Producto producto
    ) {

        this.producto = producto;

        setOpaque(false);

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

        setLayout(
                new BorderLayout()
        );

        inicializarContenido();
    }

    //------------------------ C O N T E N I D O ----------------
    private void inicializarContenido() {

        Pcontenido = new JPanel();

        Pcontenido.setOpaque(false);

        Pcontenido.setLayout(
                new BoxLayout(
                        Pcontenido,
                        BoxLayout.Y_AXIS
                )
        );

        agregarProducto();
        agregarCombos();

        add(
                Pcontenido,
                BorderLayout.NORTH
        );
    }

    //------------------------ P R O D U C T O ----------------
    private void agregarProducto() {

        JPanel Ptitulo
                = new JPanel(
                        new BorderLayout()
                );

        Ptitulo.setOpaque(false);

        JLabel Lproducto
                = new JLabel(
                        producto.getNombre(),
                        SwingConstants.CENTER
                );

        Lproducto.setFont(
                gui.estilo.Fuentes.VC.deriveFont(
                        gui.estilo.Fuentes.SUBTITULO
                )
        );

        Lproducto.setForeground(
                gui.estilo.Colores.CORINTO
        );

        Ptitulo.add(
                Lproducto,
                BorderLayout.CENTER
        );

        Pcontenido.add(
                Ptitulo
        );

        Pcontenido.add(
                Box.createVerticalStrut(
                        20
                )
        );
    }

    //------------------------ C O M B O S ----------------
    private void agregarCombos() {

        JPanel Pcombos
                = new JPanel(
                        new GridBagLayout()
                );

        Pcombos.setOpaque(false);

        agregarCombosProductos(
                Pcombos
        );

        Pcontenido.add(
                Pcombos
        );
    }

    //------------------------ A G R E G A R   C O M B O S ----------------
    private void agregarCombosProductos(
            JPanel panel
    ) {

        int columna = 0;
        int fila = 0;

        //---------------------- I N D I V I D U A L
        ComboMenu productoIndividual
                = new ComboMenu(
                        producto
                );

        productoIndividual.setProductoListener(
                productoSeleccionado -> {

                    if (productoListener != null) {

                        productoListener.productoSeleccionado(
                                productoSeleccionado
                        );
                    }
                }
        );

        agregarCombo(
                panel,
                productoIndividual,
                columna,
                fila
        );

        columna++;

        //---------------------- C O M B O S
        List<Combo> combos
                = obtenerCombos();

        for (Combo combo : combos) {

            ComboMenu comboMenu
                    = new ComboMenu(
                            combo,
                            producto
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

            agregarCombo(
                    panel,
                    comboMenu,
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

    //------------------------ A G R E G A R   U N   C O M B O ----------------
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

    //------------------------ O B T E N E R   C O M B O S ----------------
    private List<Combo> obtenerCombos() {

        ComboDAO comboDAO
                = new ComboDAO();

        return comboDAO.listarPorProducto(
                producto.getId_Producto()
        );
    }
}
