//----------------------- C A M P O  C O N T R A S E Ñ A -----------------------------
package gui.componentes;

//----------------- IMPORTS
import gui.estilo.*;
import java.awt.*;
import javax.swing.JPasswordField;
import java.awt.event.*;

public class CampoContrasena extends JPasswordField {

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

    //------------------ VISIBILIDAD
    private boolean mostrarContrasena = false;
    private int anchoBoton = 40;

    //------------------ CONSTRUCTOR ------------
    public CampoContrasena() {

        super();

        // Carácter de contraseña
        setEchoChar('*');

        //Transparencia
        setOpaque(false);

        // Fuente
        setFont(Fuentes.LFL.deriveFont(Fuentes.NORMAL));

        // Color de texto
        setForeground(colorTexto);

        // Color de fondo
        setBackground(colorFondo);

        // Margen
        setMargin(new Insets(5, 12, 5, 45));

        //Altura preferida
        setPreferredSize(new Dimension(300, altura));

        // Borde
        actualizarBorde();

        //ActionListener
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getX() >= getWidth() - anchoBoton) {
                    alternarVisibilidad();
                }
            }
        });

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

    //-------------- B O R D E -------------------
    public void setColorBorde(Color color) {
        this.colorBorde = color;
        actualizarBorde();
    }

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

    //-------------- P L A C E H O L D E R -------------------
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    public void setColorPlaceholder(Color color) {
        this.colorPlaceholder = color;
        repaint();
    }

    //------------------- V I S I B I L I D A D --------------------------
    public void alternarVisibilidad() {

        //La contraseña cambia su estado actual (ej. si se estaba mostrando, se oculta)
        mostrarContrasena = !mostrarContrasena;

        //Si se pidió que se mostrara la contraseña (es true)
        if (mostrarContrasena) {
            //El echochar muestra los caracteres
            setEchoChar((char) 0);
        } else {
            //Sino, que se sigan ocultando a través del punto
            setEchoChar('*');
        }

        repaint();
    }

    //DIbujar el ojo
    private void dibujarIconoOjo(Graphics2D g2) {

        int x = getWidth() - 30;
        int y = getHeight() / 2;

        g2.setColor(colorTexto);

        g2.drawOval(
                x - 10,
                y - 6,
                20,
                12
        );

        g2.fillOval(
                x - 3,
                y - 3,
                6,
                6
        );
    }

    //------------------ PINTAR ---------------
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        //--------------- FONDO
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

        //--------------- COMPONENTE
        super.paintComponent(g);

        //--------------- ELEMENTOS PERSONALIZADOS
        Graphics2D gPersonalizado
                = (Graphics2D) g.create();

        gPersonalizado.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        //--------------- PLACEHOLDER
        if (getPassword().length == 0 && placeholder != null) {

            gPersonalizado.setColor(colorPlaceholder);
            gPersonalizado.setFont(getFont());

            Insets insets = getInsets();

            FontMetrics fm
                    = gPersonalizado.getFontMetrics();

            int x = insets.left;

            int y = (getHeight() - fm.getHeight()) / 2
                    + fm.getAscent();

            gPersonalizado.drawString(
                    placeholder,
                    x,
                    y
            );
        }

        //--------------- OJO
        dibujarIconoOjo(gPersonalizado);

        gPersonalizado.dispose();
    }

    //------------------ MOSTRAR CONTRASEÑA
    //Es solo para saber si se esta mostrando la contraseña
    public boolean isMostrarContrasena() {
        return mostrarContrasena;
    }

}
