//---------------------- C A M P O   C O M B O B O X ----------------------
package gui.componentes;

//------------- IMPORTS
import gui.estilo.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class CampoComboBox<E> extends JComboBox<E> {

    //------------------ A T R I B U T O S -----------------

    //-------------- BORDE
    private Color colorBorde = Colores.AMARILLO_FUERTE;
    private int grosorBorde = 2;
    private int radio = 15;

    //-------------- FONDO
    private Color colorFondo = Colores.AMARILLO;

    //-------------- TEXTO
    private Color colorTexto = Colores.CORINTO;

    //-------------- FLECHA
    private Color colorFlecha = Colores.CORINTO;

    //-------------- ALTURA
    private int altura = 45;


    //------------------ C O N S T R U C T O R -----------------

    public CampoComboBox() {

        super();

        //---------------------- TRANSPARENCIA
        setOpaque(false);

        //---------------------- FUENTE
        setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        //---------------------- TEXTO
        setForeground(
                colorTexto
        );

        //---------------------- FONDO
        setBackground(
                colorFondo
        );

        //---------------------- BORDE
        setBorder(
                new BordeRedondeado(
                        colorBorde,
                        grosorBorde,
                        radio
                )
        );

        //---------------------- TAMAÑO
        setPreferredSize(
                new Dimension(
                        300,
                        altura
                )
        );

        //---------------------- FLECHA
        personalizarUI();

        //---------------------- RENDERER
        personalizarRenderizador();
    }


    //------------------ A L T U R A -----------------

    public void setAltura(
            int altura
    ) {

        this.altura = altura;

        Dimension tamaño
                = getPreferredSize();

        setPreferredSize(
                new Dimension(
                        tamaño.width,
                        altura
                )
        );
    }


    //------------------ C O L O R E S -----------------

    //--------------- Fondo
    public void setColorFondo(
            Color color
    ) {

        this.colorFondo = color;

        setBackground(
                color
        );

        personalizarRenderizador();

        repaint();
    }

    //--------------- Texto
    public void setColorTexto(
            Color color
    ) {

        this.colorTexto = color;

        setForeground(
                color
        );

        personalizarRenderizador();

        repaint();
    }

    //--------------- Borde
    public void setColorBorde(
            Color color
    ) {

        this.colorBorde = color;

        actualizarBorde();
    }

    //--------------- Flecha
    public void setColorFlecha(
            Color color
    ) {

        this.colorFlecha = color;

        personalizarUI();

        repaint();
    }


    //------------------ B O R D E -----------------

    public void setGrosorBorde(
            int grosor
    ) {

        this.grosorBorde = grosor;

        actualizarBorde();
    }

    public void setRadio(
            int radio
    ) {

        this.radio = radio;

        actualizarBorde();
    }

    private void actualizarBorde() {

        setBorder(
                new BordeRedondeado(
                        colorBorde,
                        grosorBorde,
                        radio
                )
        );

        repaint();
    }


    //------------------ P I N T A D O -----------------

    @Override
    protected void paintComponent(
            Graphics g
    ) {

        Graphics2D g2
                = (Graphics2D) g.create();

        //---------------------- ANTIALIASING
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        //---------------------- FONDO
        g2.setColor(
                colorFondo
        );

        g2.fillRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                radio,
                radio
        );

        g2.dispose();

        //---------------------- COMPONENTE
        super.paintComponent(g);
    }


    //------------------ U I  P E R S O N A L I Z A D A -----------------

    private void personalizarUI() {

        setUI(
                new BasicComboBoxUI() {

            @Override
            protected JButton createArrowButton() {

                return new BotonFlecha();
            }
        }
        );
    }


    //------------------ R E N D E R E R -----------------

    private void personalizarRenderizador() {

        setRenderer(
                new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista,
                    Object valor,
                    int indice,
                    boolean seleccionado,
                    boolean tieneFoco
            ) {

                JLabel label
                        = (JLabel) super
                                .getListCellRendererComponent(
                                        lista,
                                        valor,
                                        indice,
                                        seleccionado,
                                        tieneFoco
                                );

                //---------------------- FUENTE
                label.setFont(
                        Fuentes.LFL.deriveFont(
                                Fuentes.NORMAL
                        )
                );

                //---------------------- TEXTO
                label.setForeground(
                        colorTexto
                );

                //---------------------- FONDO
                label.setBackground(
                        colorFondo
                );

                //---------------------- OPACO
                label.setOpaque(true);

                //---------------------- ALINEACIÓN
                label.setHorizontalAlignment(
                        SwingConstants.LEFT
                );

                //---------------------- MARGEN
                label.setBorder(
                        BorderFactory.createEmptyBorder(
                                0,
                                12,
                                0,
                                5
                        )
                );

                return label;
            }
        }
        );
    }


    //------------------ F L E C H A -----------------

    private class BotonFlecha extends JButton {

        public BotonFlecha() {

            setOpaque(false);

            setContentAreaFilled(false);

            setBorderPainted(false);

            setFocusPainted(false);

            setPreferredSize(
                    new Dimension(
                            45,
                            altura
                    )
            );
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2
                    = (Graphics2D) g.create();

            //---------------------- ANTIALIASING
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            //---------------------- CENTRO
            int centroX
                    = getWidth() / 2;

            int centroY
                    = getHeight() / 2;

            int tamaño
                    = 7;

            //---------------------- FLECHA
            Polygon flecha
                    = new Polygon();

            flecha.addPoint(
                    centroX - tamaño,
                    centroY - 3
            );

            flecha.addPoint(
                    centroX + tamaño,
                    centroY - 3
            );

            flecha.addPoint(
                    centroX,
                    centroY + 5
            );

            //---------------------- COLOR
            g2.setColor(
                    colorFlecha
            );

            g2.fillPolygon(
                    flecha
            );

            g2.dispose();
        }
    }
}