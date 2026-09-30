//---------------------- B O T O N ----------------------
package gui.componentes;

import gui.estilo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Boton extends JButton {

    //------------------------ A T R I B U T O S ----------------
    private Color fondo = Colores.AMARILLO_FUERTE;
    private Color texto = Colores.NEGRO;
    private Color borde = Colores.NARANJA;

    private Color fondoHover = Colores.NARANJA;
    private Color textoHover = Colores.BLANCO;
    private Color bordeHover = Colores.ROJO;

    private Color fondoDeshabilitado = Colores.ROJO_APAGADO;
    private Color textoDeshabilitado = Colores.BLANCO;
    private Color bordeDeshabilitado = Colores.AMARILLO_APAGADO;

    private int grosor = 3;
    private int radio = 15;

    private boolean hover = false;

    //------------------------ C O N S T R U C T O R ----------------
    public Boton(String texto) {

        super(texto);

        setFont(Fuentes.LFL.deriveFont(Fuentes.NORMAL));

        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        setPreferredSize(
                new Dimension(200, 50)
        );

        agregarEventosMouse();
    }

    //---------------------- SETTERS ----------------
    public void setColorFondo(Color color) {
        fondo = color;
        repaint();
    }

    public void setColorTexto(Color color) {
        texto = color;
        repaint();
    }

    public void setColorBorde(Color color) {
        borde = color;
        repaint();
    }

    public void setColorFondoHover(Color color) {
        fondoHover = color;
        repaint();
    }

    public void setColorTextoHover(Color color) {
        textoHover = color;
        repaint();
    }

    public void setColorBordeHover(Color color) {
        bordeHover = color;
        repaint();
    }

    public void setGrosorBorde(int grosor) {
        this.grosor = grosor;
        repaint();
    }

    public void setRadio(int radio) {
        this.radio = radio;
        repaint();
    }

    public void setAltura(int altura) {

        setPreferredSize(
                new Dimension(
                        getPreferredSize().width,
                        altura
                )
        );
    }

    //--------------------- E V E N T O  ----------------
    private void agregarEventosMouse() {

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                if (isEnabled()) {
                    hover = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

                hover = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2
                = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        //---------------- FONDO
        g2.setColor(
                !isEnabled()
                        ? fondoDeshabilitado
                        : hover
                                ? fondoHover
                                : fondo
        );

        g2.fillRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                radio,
                radio
        );

        //---------------- BORDE
        g2.setColor(
                !isEnabled()
                        ? bordeDeshabilitado
                        : hover
                                ? bordeHover
                                : borde
        );

        g2.setStroke(
                new BasicStroke(grosor)
        );

        g2.drawRoundRect(
                grosor / 2,
                grosor / 2,
                getWidth() - grosor - 1,
                getHeight() - grosor - 1,
                radio,
                radio
        );

        g2.dispose();

        //---------------- TEXTO
        setForeground(
                !isEnabled()
                        ? textoDeshabilitado
                        : hover
                                ? textoHover
                                : texto
        );

        super.paintComponent(g);
    }

    //========================================================
    //                    BOTÓN MENÚ
    //========================================================
    public static Boton menu(String texto) {

        Boton boton = new Boton(texto);

        boton.setColorFondo(Colores.ROJO);
        boton.setColorTexto(Colores.BLANCO);
        boton.setColorBorde(Colores.CORINTO);

        boton.setColorFondoHover(
                Colores.CORINTO
        );

        boton.setColorTextoHover(
                Colores.BLANCO
        );

        boton.setRadio(0);
        boton.setAltura(48);

        return boton;
    }

    //========================================================
    //                BOTÓN ACCIÓN NORMAL
    //========================================================
    public static Boton accion(
            String texto,
            Color fondo,
            Color colorTexto
    ) {

        return accion(
                texto,
                fondo,
                colorTexto,
                70
        );
    }

    //========================================================
    //              BOTÓN ACCIÓN CON ANCHO
    //========================================================
    public static Boton accion(
            String texto,
            Color fondo,
            Color colorTexto,
            int ancho
    ) {

        Boton boton = new Boton(texto);

        boton.setColorFondo(fondo);
        boton.setColorBorde(fondo);
        boton.setColorTexto(colorTexto);

        boton.setRadio(12);

        boton.setPreferredSize(
                new Dimension(
                        ancho,
                        32
                )
        );

        return boton;
    }

    //========================================================
    //                  CERRAR SESIÓN
    //========================================================
    public static Boton cerrarSesion() {

        Boton boton
                = new Boton("Cerrar sesión");

        boton.setColorFondo(
                Colores.NARANJA
        );

        boton.setColorBorde(
                Colores.CORINTO
        );

        boton.setColorTexto(
                Colores.BLANCO
        );

        boton.setRadio(0);
        boton.setAltura(45);

        return boton;
    }

    //========================================================
    //                 MOSTRAR ALERTAS
    //========================================================
    public static Boton mostrarAlertas() {

        Boton boton
                = new Boton("MOSTRAR ALERTAS");

        boton.setColorFondo(
                Colores.CORINTO
        );

        boton.setColorTexto(
                Colores.BLANCO
        );

        boton.setColorBorde(
                Colores.CORINTO
        );

        boton.setColorFondoHover(
                Colores.ROJO
        );

        boton.setColorTextoHover(
                Colores.BLANCO
        );

        boton.setRadio(15);
        boton.setAltura(35);

        return boton;
    }

    //========================================================
    //                     REGRESAR
    //========================================================
    public static Boton regresar() {

        Boton boton
                = new Boton("Regresar");

        boton.setColorFondo(
                Colores.ROJO
        );

        boton.setColorBorde(
                Colores.ROJO
        );

        boton.setColorTexto(
                Colores.NEGRO
        );

        boton.setColorFondoHover(
                Colores.CORINTO
        );

        boton.setColorTextoHover(
                Colores.BLANCO
        );

        boton.setRadio(20);
        boton.setAltura(40);

        return boton;
    }

    //------------------------ VERDE
    public void verde() {
        setColorFondo(
                Colores.VERDE_CLARO
        );

        setColorBorde(
                Colores.VERDE_OSCURO
        );

        setColorFondoHover(
                Colores.VERDE_OSCURO
        );

        setColorBordeHover(
                Colores.VERDE_CLARO
        );
    }

    //------------------------ ROJO
    public void rojo() {
        setColorFondo(
                Colores.ROSADO
        );

        setColorBorde(
                Colores.ROJO
        );

        setColorFondoHover(
                Colores.ROJO
        );

        setColorBordeHover(
                Colores.ROSADO
        );
    }

    //-------------------------- AMARILLO
    public void amarillo() {
        setColorFondo(
                Colores.AMARILLO_FUERTE
        );

        setColorBorde(
                Colores.NARANJA
        );

        setColorFondoHover(
                Colores.NARANJA
        );

        setColorBordeHover(
                Colores.ROJO
        );
    }

    //-------------------------- CREMA
    public void crema() {
        setColorFondo(
                Colores.AMARILLO
        );

        setColorBorde(
                Colores.AMARILLO_FUERTE
        );

        setColorBordeHover(
                Colores.AMARILLO
        );

        setColorFondoHover(
                Colores.VERDE_CLARO
        );
    }
}
