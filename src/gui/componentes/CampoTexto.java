package gui.componentes;

//------------- IMPORTS
import javax.swing.JTextField;
import gui.estilo.*;
import java.awt.*;

public class CampoTexto extends JTextField {

    //------------------ ATRIBUTOS -----------------
    //------------------ BORDE
    private Color colorBorde = Colores.VERDE_OSCURO;
    private int grosorBorde = 1;
    private int radio = 10;

    //------------------ FONDO
    private Color colorFondo = Colores.VERDE_CLARO;

    //------------------ TEXTO
    private Color colorTexto = Colores.VERDE_OSCURO;

    //------------------ PLACEHOLDER
    private String placeholder;
    private Color colorPlaceholder = Colores.AMARILLO;

    //------------------ ALTURA
    private int altura = 45;

    //------------------ CONSTRUCTOR -----------------
    public CampoTexto() {

        super();

        // Transparencia
        setOpaque(false);

        // Fuente
        setFont(Fuentes.LFL.deriveFont(Fuentes.NORMAL));

        // Color de texto
        setForeground(colorTexto);

        // Color de fondo
        setBackground(colorFondo);

        // Margen interno
        setMargin(new Insets(5, 12, 5, 12));

        //Altura preferida
        setPreferredSize(new Dimension(300, altura));

        // Borde
        actualizarBorde();
    }

    //------------------ A L T U R A
    public void setAltura(int altura) {

        this.altura = altura;

        Dimension tamaño = getPreferredSize();

        setPreferredSize(
                new Dimension(tamaño.width, altura)
        );
    }

    //                 C O L O R E S
    //--------------- Fondo ----------------
    public void setColorFondo(Color color) {

        this.colorFondo = color;
        setBackground(color);
        repaint();
    }

    //--------------- Texto ----------------
    public void setColorTexto(Color color) {

        this.colorTexto = color;
        setForeground(color);
        repaint();
    }

    //--------------- Borde ----------------
    public void setColorBorde(Color color) {

        this.colorBorde = color;
        actualizarBorde();
    }

    //                  B O R D E
    public void setGrosorBorde(int grosor) {

        this.grosorBorde = grosor;
        actualizarBorde();
    }

    public void setRadio(int radio) {

        this.radio = radio;
        actualizarBorde();
    }

    private void actualizarBorde() {

        setBorder(new BordeRedondeado(
                colorBorde,
                grosorBorde,
                radio
        ));
    }

    //                 M A R G E N
    public void setMargen(int arriba, int izquierda,
            int abajo, int derecha) {

        setMargin(new Insets(
                arriba,
                izquierda,
                abajo,
                derecha
        ));
    }

    //             P L A C E H O L D E R
    public void setPlaceholder(String placeholder) {

        this.placeholder = placeholder;
        repaint();
    }

    public void setColorPlaceholder(Color color) {

        this.colorPlaceholder = color;
        repaint();
    }

    //                  P I N T A D O
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        // Antialiasing
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Fondo redondeado
        g2.setColor(colorFondo);

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                radio,
                radio
        );

        g2.dispose();

        // Pintar JTextField
        super.paintComponent(g);

        // Placeholder
        if (getText().isEmpty() && placeholder != null) {

            Graphics2D gPlaceholder
                    = (Graphics2D) g.create();

            gPlaceholder.setColor(colorPlaceholder);
            gPlaceholder.setFont(getFont());

            Insets insets = getInsets();

            FontMetrics fm
                    = gPlaceholder.getFontMetrics();

            int x = insets.left;

            int y = (getHeight() - fm.getHeight()) / 2
                    + fm.getAscent();

            gPlaceholder.drawString(
                    placeholder,
                    x,
                    y
            );

            gPlaceholder.dispose();
        }
    }
}
