//---------------------- C A M P O   A R E A ----------------------
package gui.componentes;

//------------- IMPORTS
import gui.estilo.*;
import javax.swing.*;
import java.awt.*;

public class CampoArea extends JScrollPane {

    //------------------ A T R I B U T O S -----------------

    //-------------- ÁREA DE TEXTO
    private JTextArea areaTexto;

    //-------------- BORDE
    private Color colorBorde = Colores.VERDE_OSCURO;
    private int grosorBorde = 2;
    private int radio = 15;

    //-------------- FONDO
    private Color colorFondo = Colores.VERDE_CLARO;

    //-------------- TEXTO
    private Color colorTexto = Colores.VERDE_OSCURO;

    //-------------- TAMAÑO
    private int ancho = 300;
    private int altura = 150;


    //------------------ C O N S T R U C T O R -----------------

    public CampoArea() {

        super();

        //---------------------- ÁREA DE TEXTO
        areaTexto = new JTextArea();

        // Fuente
        areaTexto.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        // Color del texto
        areaTexto.setForeground(
                colorTexto
        );

        // Transparencia
        areaTexto.setOpaque(false);

        // Ajuste de línea
        areaTexto.setLineWrap(true);

        areaTexto.setWrapStyleWord(true);

        // Margen interno
        areaTexto.setMargin(
                new Insets(
                        10,
                        12,
                        10,
                        12
                )
        );

        //---------------------- VIEWPORT
        getViewport().setOpaque(false);

        getViewport().setView(
                areaTexto
        );

        //---------------------- PANEL
        setOpaque(false);

        //---------------------- BARRA VERTICAL
        setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        //---------------------- BARRA HORIZONTAL
        setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        //---------------------- BORDE
        actualizarBorde();

        //---------------------- TAMAÑO
        setPreferredSize(
                new Dimension(
                        ancho,
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

        repaint();
    }

    //--------------- Texto
    public void setColorTexto(
            Color color
    ) {

        this.colorTexto = color;

        areaTexto.setForeground(
                color
        );

        repaint();
    }

    //--------------- Borde
    public void setColorBorde(
            Color color
    ) {

        this.colorBorde = color;

        actualizarBorde();
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


    //------------------ T A M A Ñ O -----------------

    public void setAncho(
            int ancho
    ) {

        this.ancho = ancho;

        actualizarTamaño();
    }

    public void setAltura(
            int altura
    ) {

        this.altura = altura;

        actualizarTamaño();
    }

    public void setTamaño(
            int ancho,
            int altura
    ) {

        this.ancho = ancho;
        this.altura = altura;

        actualizarTamaño();
    }

    private void actualizarTamaño() {

        setPreferredSize(
                new Dimension(
                        ancho,
                        altura
                )
        );

        revalidate();
        repaint();
    }


    //------------------ T E X T O -----------------

    public void setText(
            String texto
    ) {

        areaTexto.setText(
                texto
        );
    }

    public String getText() {

        return areaTexto.getText();
    }

    public void append(
            String texto
    ) {

        areaTexto.append(
                texto
        );
    }

    public void setEditable(
            boolean editable
    ) {

        areaTexto.setEditable(
                editable
        );
    }

    public boolean isEditable() {

        return areaTexto.isEditable();
    }


    //------------------ Á R E A   D E   T E X T O -----------------

    public JTextArea getAreaTexto() {

        return areaTexto;
    }


    //------------------ P I N T A D O -----------------

    @Override
    protected void paintComponent(
            Graphics g
    ) {

        Graphics2D g2
                = (Graphics2D) g.create();

        // Antialiasing
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

        super.paintComponent(g);
    }
}