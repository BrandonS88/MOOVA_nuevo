//---------------------------------- C A R R U S E L   I M A G E N E S -----------------------
package gui.componentes;

import javax.swing.*;
import java.awt.*;
import gui.estilo.*;

public class CarruselImagenes extends JPanel {

    //------------------------  A T R I B U T O S ----------------
    //------------------------ Guarda las imágenes
    private ImagenRecortada[] imagenes;

    //----------------------- Temporizador
    private Timer temporizadorCambio;

    //-------------------- Número de imagen
    private int imagenActual;
    private int imagenSiguiente;

    //-------------------- Temporizador de animación
    private Timer temporizadorAnimacion;

    //-------------------- Posición de la animación
    private double posicionAnimacion;

    //-------------------- Duración de la animación
    private final int DURACION_ANIMACION = 600;

    //-------------------- Intervalo entre movimientos
    private final int INTERVALO_ANIMACION = 20;

    //-------------------- Progreso de la animación
    private double progresoAnimacion;

    //-------------------- Panel de animación
    private JPanel Panimacion;

    //-------------------- Panel de indicadores
    private JPanel Pindicadores;

    //-------------------- Indicadores
    private IndicadorCarrusel[] indicadores;

    //-------------------- C O N S T R U C T O R -----------------
    public CarruselImagenes(
            String[] rutas,
            int intervalo
    ) {

        //---------------------- TRANSPARENCIA
        setOpaque(false);

        //---------------------- PANEL DE ANIMACIÓN
        Panimacion = new JPanel();
        Panimacion.setOpaque(false);
        Panimacion.setLayout(null);

        //---------------------- CARRUSEL
        setLayout(new BorderLayout());

        add(
                Panimacion,
                BorderLayout.CENTER
        );

        //---------------------- PANEL DE INDICADORES
        Pindicadores = new JPanel();

        Pindicadores.setOpaque(false);

        Pindicadores.setLayout(
                new FlowLayout(
                        FlowLayout.CENTER,
                        10,
                        5
                )
        );

        add(
                Pindicadores,
                BorderLayout.SOUTH
        );

        //---------------------- VALIDAR RUTAS
        if (rutas == null || rutas.length == 0) {

            throw new IllegalArgumentException(
                    "El carrusel debe contener al menos una imagen."
            );
        }

        //---------------------- VALIDAR INTERVALO
        if (intervalo <= 0) {

            throw new IllegalArgumentException(
                    "El intervalo debe ser mayor que cero."
            );
        }

        //---------------------- CREAR ARREGLO
        imagenes = new ImagenRecortada[rutas.length];

        //---------------------- CREAR INDICADORES
        indicadores = new IndicadorCarrusel[rutas.length];

        for (int i = 0; i < rutas.length; i++) {

            indicadores[i] = new IndicadorCarrusel();

            indicadores[i].setTamano(20);

            Pindicadores.add(
                    indicadores[i]
            );
        }

        //---------------------- IMÁGENES
        for (int i = 0; i < rutas.length; i++) {

            imagenes[i] = new ImagenRecortada(
                    rutas[i]
            );
        }

        //---------------------- IMAGEN INICIAL
        imagenActual = 0;
        imagenSiguiente = 0;

        //---------------------- ACTUALIZAR INDICADORES
        actualizarIndicadores();

        Panimacion.add(
                imagenes[imagenActual]
        );

        //---------------------- TEMPORIZADOR
        temporizadorCambio = new Timer(
                intervalo,
                e -> cambiarImagen()
        );

        temporizadorCambio.start();
    }

    //---------------------- C A M B I A R   I M A G E N
    private void cambiarImagen() {

        //---------------------- DETENER CAMBIO AUTOMÁTICO
        temporizadorCambio.stop();

        //---------------------- SIGUIENTE IMAGEN
        imagenSiguiente = imagenActual + 1;

        if (imagenSiguiente >= imagenes.length) {

            imagenSiguiente = 0;
        }

        //---------------------- POSICIÓN INICIAL
        posicionAnimacion = 0;

        //---------------------- PROGRESO INICIAL
        progresoAnimacion = 0;

        //---------------------- PREPARAR IMAGEN SIGUIENTE
        Panimacion.add(
                imagenes[imagenSiguiente]
        );

        //---------------------- OBTENER TAMAÑO
        int ancho = Panimacion.getWidth();
        int alto = Panimacion.getHeight();

        //---------------------- COLOCAR IMAGEN ACTUAL
        imagenes[imagenActual].setBounds(
                0,
                0,
                ancho,
                alto
        );

        //---------------------- COLOCAR IMAGEN SIGUIENTE
        imagenes[imagenSiguiente].setBounds(
                ancho,
                0,
                ancho,
                alto
        );

        //---------------------- INICIAR ANIMACIÓN
        temporizadorAnimacion = new Timer(
                INTERVALO_ANIMACION,
                e -> animarCambio()
        );

        temporizadorAnimacion.start();

        //---------------------- ACTUALIZAR PANTALLA
        Panimacion.repaint();
    }

    //---------------------- A N I M A R   C A M B I O
    private void animarCambio() {

        //---------------------- OBTENER TAMAÑO
        int ancho = Panimacion.getWidth();

        //---------------------- AVANZAR PROGRESO
        progresoAnimacion
                += (double) INTERVALO_ANIMACION
                / DURACION_ANIMACION;

        //---------------------- EVITAR SUPERAR 1
        if (progresoAnimacion > 1) {

            progresoAnimacion = 1;
        }

        //---------------------- APLICAR SUAVIZADO
        double suavizado
                = 1 - Math.pow(1 - progresoAnimacion, 3);

        //---------------------- CALCULAR POSICIÓN
        posicionAnimacion
                = ancho * suavizado;

        //---------------------- MOVER IMAGEN ACTUAL
        imagenes[imagenActual].setLocation(
                (int) -posicionAnimacion,
                0
        );

        /*¿Por qué (int) Porque setLocation() trabaja con coordenadas enteras:
        setLocation(int x, int y), Mientras que nuestra posición ahora puede ser decimal.
        Por eso hacemos la conversión solamente cuando necesitamos mover físicamente el componente.
         */
        //---------------------- MOVER IMAGEN SIGUIENTE
        imagenes[imagenSiguiente].setLocation(
                (int) (ancho - posicionAnimacion),
                0
        );

        //---------------------- ACTUALIZAR PANTALLA
        Panimacion.repaint();

        //---------------------- COMPROBAR SI TERMINÓ
        if (posicionAnimacion >= ancho) {

            //---------------------- DETENER ANIMACIÓN
            temporizadorAnimacion.stop();

            //---------------------- REACTIVAR CAMBIO AUTOMÁTICO
            temporizadorCambio.start();

            //---------------------- ACTUALIZAR IMAGEN ACTUAL
            imagenActual = imagenSiguiente;

            //---------------------- ACTUALIZAR INDICADORES
            actualizarIndicadores();

            //---------------------- ELIMINAR IMAGEN ANTERIOR
            Panimacion.removeAll();

            //---------------------- AGREGAR LA NUEVA IMAGEN
            Panimacion.add(
                    imagenes[imagenActual]
            );

            //---------------------- POSICIONAR NUEVA IMAGEN
            imagenes[imagenActual].setBounds(
                    0,
                    0,
                    ancho,
                    Panimacion.getHeight()
            );

            //---------------------- ACTUALIZAR PANTALLA
            Panimacion.revalidate();
            Panimacion.repaint();
        }
    }

    //---------------------- A C T U A L I Z A R   I N D I C A D O R E S
    private void actualizarIndicadores() {

        for (int i = 0; i < indicadores.length; i++) {

            if (i == imagenActual) {

                indicadores[i].setColor(
                        Colores.CORINTO
                );

            } else {

                indicadores[i].setColor(
                        Colores.AMARILLO
                );
            }
        }

        Pindicadores.repaint();
    }

    //Cuando conozca cuánto mide realmente el carrusel, haz que la imagen ocupe ese espacio.
    @Override
    public void doLayout() {

        super.doLayout();

        int ancho = Panimacion.getWidth();
        int alto = Panimacion.getHeight();

        if (Panimacion.getComponentCount() > 0) {

            imagenes[imagenActual].setBounds(
                    0,
                    0,
                    ancho,
                    alto
            );
        }
    }

    //---------------------- C A M B I A R   I M A G E N E S ----------------
    public void setImagenes(String[] rutas) {

        //---------------------- VALIDAR RUTAS
        if (rutas == null || rutas.length == 0) {

            throw new IllegalArgumentException(
                    "El carrusel debe contener al menos una imagen."
            );
        }

        //---------------------- DETENER TEMPORIZADORES
        temporizadorCambio.stop();

        if (temporizadorAnimacion != null) {
            temporizadorAnimacion.stop();
        }

        //---------------------- CREAR NUEVAS IMÁGENES
        imagenes = new ImagenRecortada[rutas.length];

        for (int i = 0; i < rutas.length; i++) {

            imagenes[i] = new ImagenRecortada(
                    rutas[i]
            );
        }

        //---------------------- REINICIAR POSICIONES
        imagenActual = 0;
        imagenSiguiente = 0;

        //---------------------- ACTUALIZAR INDICADORES
        Pindicadores.removeAll();

        indicadores
                = new IndicadorCarrusel[rutas.length];

        for (int i = 0; i < rutas.length; i++) {

            indicadores[i]
                    = new IndicadorCarrusel();

            indicadores[i].setTamano(20);

            Pindicadores.add(
                    indicadores[i]
            );
        }

        actualizarIndicadores();

        //---------------------- MOSTRAR PRIMERA IMAGEN
        Panimacion.removeAll();

        Panimacion.add(
                imagenes[imagenActual]
        );

        //---------------------- ACTUALIZAR TAMAÑO
        int ancho = Panimacion.getWidth();
        int alto = Panimacion.getHeight();

        imagenes[imagenActual].setBounds(
                0,
                0,
                ancho,
                alto
        );

        //---------------------- ACTUALIZAR PANTALLA
        Pindicadores.revalidate();
        Pindicadores.repaint();

        Panimacion.revalidate();
        Panimacion.repaint();

        //---------------------- REINICIAR TEMPORIZADOR
        temporizadorCambio.start();
    }
}
