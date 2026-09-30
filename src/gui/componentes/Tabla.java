//---------------------- T A B L A ----------------------
package gui.componentes;

//------------- IMPORTS
import gui.estilo.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;

public class Tabla extends JTable {

    //------------------ ATRIBUTOS -----------------
    
    //-------------- COLORES
    private Color colorBorde = Colores.NARANJA;
    private Color colorEncabezado = Colores.NARANJA_SUAVE;
    private Color colorFilas = Colores.BLANCO;

    // Color alterno para las filas impares. Sirve para pintar "por fila"
    // (como las hojas de cálculo) y así leer mejor los datos de una
    // columna, en lugar de que todas las celdas se vean iguales.
    private Color colorFilasAlternas = Colores.AMARILLO;

    private Color colorSeleccion = Colores.AMARILLO;

    //-------------- ALTURA
    private int alturaFila = 40;

    //------------------ CONSTRUCTOR -----------------
    public Tabla() {

        super();

        //---------------------- FUENTE
        setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        //---------------------- ENCABEZADO
        getTableHeader().setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.GRANDE
                )
        );

        //---------------------- COLOR ENCABEZADO
        getTableHeader().setBackground(
                colorEncabezado
        );

        getTableHeader().setForeground(
                Colores.CORINTO
        );

        //---------------------- SELECCIÓN
        setSelectionBackground(
                colorSeleccion
        );

        setSelectionForeground(
                Colores.CORINTO
        );

        //---------------------- FONDO
        setBackground(
                colorFilas
        );

        //---------------------- BORDES
        setGridColor(
                colorBorde
        );

        setShowGrid(true);

        setBorder(
                new LineBorder(
                        colorBorde,
                        2
                )
        );

        //---------------------- FILAS
        setRowHeight(
                alturaFila
        );

        //---------------------- SELECCIÓN POR CELDA
        setCellSelectionEnabled(true);

        setRowSelectionAllowed(false);

        setColumnSelectionAllowed(false);

        //---------------------- ENCABEZADO
        getTableHeader().setReorderingAllowed(false);

        //---------------------- RENDERIZADOR
        personalizarRenderizador();
        personalizarRenderizadorEncabezado();
    }

    //------------------ R E N D E R I Z A D O R
    private void personalizarRenderizador() {

        DefaultTableCellRenderer renderizador
                = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable tabla,
                    Object valor,
                    boolean seleccionado,
                    boolean tieneFoco,
                    int fila,
                    int columna
            ) {

                JLabel label
                        = (JLabel) super
                                .getTableCellRendererComponent(
                                        tabla,
                                        valor,
                                        seleccionado,
                                        tieneFoco,
                                        fila,
                                        columna
                                );

                //---------------------- FUENTE
                label.setFont(
                        Fuentes.LFL.deriveFont(
                                Fuentes.GRANDE
                        )
                );

                //---------------------- TEXTO
                label.setForeground(
                        Colores.CORINTO
                );

                //---------------------- FONDO
                // El color se elige por FILA, no por columna: las filas
                // pares van con colorFilas y las impares con
                // colorFilasAlternas. Así el ojo puede seguir una fila
                // completa de izquierda a derecha sin perderse.
                if (seleccionado) {

                    label.setBackground(
                            colorSeleccion
                    );

                } else if (fila % 2 == 1) {

                    label.setBackground(
                            colorFilasAlternas
                    );

                } else {

                    label.setBackground(
                            colorFilas
                    );
                }

                //---------------------- ALINEACIÓN
                label.setHorizontalAlignment(
                        SwingConstants.CENTER
                );

                //---------------------- BORDE
                // El borde inferior se dibuja una sola vez por fila, en la
                // última columna. Si se dibujara en todas las celdas se
                // vería una línea doble entre celdas de una misma fila.
                label.setBorder(
                        BorderFactory.createMatteBorder(
                                0,
                                0,
                                columna == tabla.getColumnCount() - 1 ? 2 : 0,
                                1,
                                colorBorde
                        )
                );

                return label;
            }
        };

        //---------------------- APLICAR A TODAS LAS COLUMNAS
        setDefaultRenderer(
                Object.class,
                renderizador
        );
    }

    //------------------ R E N D E R I Z A D O R   E N C A B E Z A D O
    private void personalizarRenderizadorEncabezado() {

        DefaultTableCellRenderer renderizador
                = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable tabla,
                    Object valor,
                    boolean seleccionado,
                    boolean tieneFoco,
                    int fila,
                    int columna
            ) {

                JLabel label
                        = (JLabel) super
                                .getTableCellRendererComponent(
                                        tabla,
                                        valor,
                                        seleccionado,
                                        tieneFoco,
                                        fila,
                                        columna
                                );

                //---------------------- FUENTE
                label.setFont(
                        Fuentes.LFL.deriveFont(
                                Fuentes.GRANDE
                        )
                );

                //---------------------- TEXTO
                label.setForeground(
                        Colores.CORINTO
                );

                //---------------------- FONDO
                label.setBackground(
                        colorEncabezado
                );

                //---------------------- ALINEACIÓN
                label.setHorizontalAlignment(
                        SwingConstants.CENTER
                );

                //---------------------- BORDE
                label.setBorder(
                        BorderFactory.createMatteBorder(
                                0,
                                0,
                                2,
                                1,
                                colorBorde
                        )
                );

                return label;
            }
        };

        getTableHeader().setDefaultRenderer(
                renderizador
        );
    }

    //------------------ C O L O R E S

    public void setColorBorde(
            Color color
    ) {

        this.colorBorde = color;

        setGridColor(
                color
        );

        setBorder(
                new LineBorder(
                        color,
                        2
                )
        );

        // Se repinta la tabla completa para que el nuevo color de fondo
        // por fila se aplique a todas las celdas, no solo a las nuevas.
        repaint();
    }

    public void setColorEncabezado(
            Color color
    ) {

        this.colorEncabezado = color;

        getTableHeader().setBackground(
                color
        );

        repaint();
    }

    public void setColorFilas(
            Color color
    ) {

        this.colorFilas = color;

        setBackground(
                color
        );

        repaint();
    }

    //------------------ COLOR DE FILAS ALTERNAS
    public void setColorFilasAlternas(
            Color color
    ) {

        this.colorFilasAlternas = color;

        repaint();
    }

    public Color getColorFilasAlternas() {

        return colorFilasAlternas;
    }

    public void setColorSeleccion(
            Color color
    ) {

        this.colorSeleccion = color;

        setSelectionBackground(
                color
        );

        repaint();
    }

    //------------------ A L T U R A

    public void setAlturaFila(
            int altura
    ) {

        this.alturaFila = altura;

        setRowHeight(
                altura
        );
    }
}