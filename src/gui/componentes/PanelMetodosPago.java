//---------------------- P A N E L   M E T O D O S   P A G O ----------------------
package gui.componentes;

import java.awt.*;
import javax.swing.*;
import gui.estilo.*;

public class PanelMetodosPago extends PanelColor {

    //------------------------ A T R I B U T O S ----------------
    private JPanel Pcontenido;
    private int fila;

    //------------------------ C O N S T R U C T O R ----------------
    public PanelMetodosPago() {

        fila = 0;

        setColorFondo(Colores.BLANCO);
        setOpacidad(255);
        setRadio(30);
        setColorBorde(Colores.CORINTO);
        setGrosorBorde(2);

        setLayout(new BorderLayout());

        inicializarContenido();
    }

    //---------------------- C O N T E N I D O ----------------
    private void inicializarContenido() {

        Pcontenido = new JPanel(new GridBagLayout());
        Pcontenido.setOpaque(false);

        Pcontenido.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        add(Pcontenido, BorderLayout.NORTH);
    }

    //---------------------- T Í T U L O ----------------
    public void setTitulo(String titulo) {

        JLabel Ltitulo = new JLabel(titulo);

        Ltitulo.setFont(Fuentes.LFL.deriveFont(Fuentes.SUBTITULO));
        Ltitulo.setForeground(Colores.NEGRO);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(0, 0, 20, 0);

        Pcontenido.add(Ltitulo, gbc);

        fila++;
        revalidate();
        repaint();
    }

    //---------------------- M É T O D O ----------------
    public void agregarMetodo(
            String metodo,
            double monto
    ) {

        JLabel Lmetodo = new JLabel("- " + metodo);
        Lmetodo.setFont(Fuentes.LFL.deriveFont(Fuentes.NORMAL));
        Lmetodo.setForeground(Colores.CORINTO);

        JLabel Lmonto = new JLabel(
                "Q." + String.format("%.2f", monto)
        );
        Lmonto.setFont(Fuentes.LFL.deriveFont(Fuentes.NORMAL));
        Lmonto.setForeground(Colores.CORINTO);

        BotonEliminar Beliminar = new BotonEliminar();

        Beliminar.addActionListener(e -> {

            if (metodosPagoListener != null) {

                metodosPagoListener.eliminarMetodo(metodo);
            }
        });

        GridBagConstraints gbcMetodo = new GridBagConstraints();

        gbcMetodo.gridx = 0;
        gbcMetodo.gridy = fila;
        gbcMetodo.weightx = 1.0;

        gbcMetodo.anchor = GridBagConstraints.WEST;
        gbcMetodo.fill = GridBagConstraints.HORIZONTAL;

        gbcMetodo.insets = new Insets(5, 0, 5, 0);

        Pcontenido.add(Lmetodo, gbcMetodo);

        GridBagConstraints gbcMonto = new GridBagConstraints();

        gbcMonto.gridx = 1;
        gbcMonto.gridy = fila;

        gbcMonto.anchor = GridBagConstraints.EAST;
        gbcMonto.insets = new Insets(5, 15, 5, 15);

        Pcontenido.add(Lmonto, gbcMonto);

        GridBagConstraints gbcEliminar = new GridBagConstraints();

        gbcEliminar.gridx = 2;
        gbcEliminar.gridy = fila;

        gbcEliminar.anchor = GridBagConstraints.CENTER;
        gbcEliminar.insets = new Insets(5, 10, 5, 0);

        Pcontenido.add(Beliminar, gbcEliminar);

        fila++;
        revalidate();
        repaint();
    }

    //---------------------- L I M P I A R ----------------
    public void limpiar() {

        Pcontenido.removeAll();
        fila = 0;

        revalidate();
        repaint();
    }

    //---------------------- L I S T E N E R ----------------
    public interface MetodosPagoListener {

        void eliminarMetodo(String metodo);
    }

    private MetodosPagoListener metodosPagoListener;

    public void setMetodosPagoListener(MetodosPagoListener listener) {

        this.metodosPagoListener = listener;
    }
}