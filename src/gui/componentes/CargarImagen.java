//---------------------- C A R G A R   I M A G E N ----------------------
package gui.componentes;

//------------- IMPORTS
import gui.estilo.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CargarImagen extends PanelColor {

    //------------------ A T R I B U T O S -----------------

    //-------------- IMAGEN
    private BufferedImage imagen;

    //-------------- TEXTO
    private JLabel Ltexto;

    //-------------- ARCHIVO
    private File archivo;

    //-------------- TAMAÑO
    private int ancho = 300;
    private int altura = 200;

    //-------------- BORDE
    private int radio = 20;

    //-------------- TAMAÑO MÁXIMO
    private static final long MAXIMO_BYTES
            = 2 * 1024 * 1024;


    //------------------ C O N S T R U C T O R -----------------

    public CargarImagen() {

        super();

        //---------------------- FONDO
        setColorFondo(
                Colores.AMARILLO
        );

        setOpacidad(255);

        //---------------------- BORDE
        setColorBorde(
                Colores.AMARILLO_FUERTE
        );

        setGrosorBorde(3);

        setRadio(
                radio
        );

        //---------------------- TAMAÑO
        setPreferredSize(
                new Dimension(
                        ancho,
                        altura
                )
        );

        //---------------------- LAYOUT
        setLayout(
                new BorderLayout()
        );

        //---------------------- TEXTO
        inicializarTexto();

        //---------------------- EVENTO
        inicializarEvento();
    }


    //------------------ T E X T O -----------------

    private void inicializarTexto() {

        Ltexto = new JLabel(
                "2MB como máximo"
        );

        Ltexto.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Ltexto.setForeground(
                Colores.CORINTO
        );

        Ltexto.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        Ltexto.setVerticalAlignment(
                SwingConstants.CENTER
        );

        Ltexto.setOpaque(false);

        add(
                Ltexto,
                BorderLayout.CENTER
        );
    }


    //------------------ E V E N T O -----------------

    private void inicializarEvento() {

        MouseAdapter evento
                = new MouseAdapter() {

            @Override
            public void mouseClicked(
                    MouseEvent e
            ) {

                seleccionarImagen();
            }
        };

        addMouseListener(
                evento
        );

        Ltexto.addMouseListener(
                evento
        );

        setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }


    //------------------ S E L E C C I O N A R -----------------

    private void seleccionarImagen() {

        JFileChooser selector
                = new JFileChooser();

        selector.setDialogTitle(
                "Seleccionar imagen"
        );

        //---------------------- FILTRO
        FileNameExtensionFilter filtro
                = new FileNameExtensionFilter(
                        "Imágenes (*.jpg, *.jpeg, *.png)",
                        "jpg",
                        "jpeg",
                        "png"
                );

        selector.setFileFilter(
                filtro
        );

        //---------------------- ABRIR
        int resultado
                = selector.showOpenDialog(
                        this
                );

        if (resultado
                != JFileChooser.APPROVE_OPTION) {

            return;
        }

        //---------------------- ARCHIVO
        File seleccionado
                = selector.getSelectedFile();

        //---------------------- TAMAÑO
        if (seleccionado.length()
                > MAXIMO_BYTES) {

            Dialogo.aviso(
                    this,
                    "Imagen demasiado grande",
                    "La imagen no puede superar los 2MB."
            );

            return;
        }

        //---------------------- LEER IMAGEN
        try {

            BufferedImage nuevaImagen
                    = ImageIO.read(
                            seleccionado
                    );

            //---------------------- VALIDAR
            if (nuevaImagen == null) {

                Dialogo.aviso(
                        this,
                        "Archivo inválido",
                        "El archivo seleccionado no es una imagen válida."
                );

                return;
            }

            //---------------------- GUARDAR
            imagen = nuevaImagen;

            archivo = seleccionado;

            //---------------------- MOSTRAR
            Ltexto.setVisible(
                    false
            );

            repaint();

        } catch (Exception e) {

            Dialogo.error(
                    this,
                    "Error",
                    "No se pudo cargar la imagen."
            );
        }
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


    //------------------ G E T T E R S -----------------

    public BufferedImage getImagen() {

        return imagen;
    }

    public File getArchivo() {

        return archivo;
    }

    /**
     * Cambia el archivo que representa este componente.
     *
     * Se usa cuando el programa copia la imagen elegida a la carpeta de
     * imágenes del proyecto: así el componente pasa a mostrar la copia
     * (la que realmente quedó guardada) y no el archivo original que
     * estaba en el escritorio del usuario.
     *
     * @param archivo nuevo archivo, o null para dejar el componente vacío.
     */
    public void setArchivo(File archivo) {

        this.archivo = archivo;

        if (archivo == null) {

            imagen = null;

            Ltexto.setVisible(true);

            repaint();

            return;
        }

        try {

            BufferedImage cargada = ImageIO.read(archivo);

            if (cargada == null) {
                return;
            }

            imagen = cargada;

            Ltexto.setVisible(false);

            repaint();

        } catch (Exception e) {
            // Si el archivo no se puede leer se deja el componente como
            // estaba, sin interrumpir la operación que lo está usando.
            System.out.println(
                    "No se pudo mostrar la imagen: " + e.getMessage()
            );
        }
    }

    public boolean tieneImagen() {

        return imagen != null;
    }


    //------------------ L I M P I A R -----------------

    public void limpiarImagen() {

        imagen = null;

        archivo = null;

        Ltexto.setVisible(
                true
        );

        repaint();
    }


    //------------------ P I N T A D O -----------------

    @Override
    protected void paintComponent(
            Graphics g
    ) {

        //---------------------- FONDO Y BORDE
        super.paintComponent(g);

        //---------------------- SIN IMAGEN
        if (imagen == null) {

            return;
        }

        Graphics2D g2
                = (Graphics2D) g.create();

        //---------------------- ANTIALIASING
        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );

        g2.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
        );

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        //---------------------- TAMAÑO ORIGINAL
        int anchoOriginal
                = imagen.getWidth();

        int alturaOriginal
                = imagen.getHeight();

        //---------------------- ESPACIO INTERNO
        int margen = 10;

        int anchoDisponible
                = getWidth()
                - (margen * 2);

        int alturaDisponible
                = getHeight()
                - (margen * 2);

        //---------------------- ESCALA
        double escala
                = Math.min(
                        (double) anchoDisponible
                        / anchoOriginal,
                        (double) alturaDisponible
                        / alturaOriginal
                );

        //---------------------- NUEVO TAMAÑO
        int nuevoAncho
                = (int) (
                        anchoOriginal
                        * escala
                );

        int nuevaAltura
                = (int) (
                        alturaOriginal
                        * escala
                );

        //---------------------- CENTRAR
        int x
                = (getWidth()
                - nuevoAncho) / 2;

        int y
                = (getHeight()
                - nuevaAltura) / 2;

        //---------------------- DIBUJAR
        g2.drawImage(
                imagen,
                x,
                y,
                nuevoAncho,
                nuevaAltura,
                this
        );

        g2.dispose();
    }
}
