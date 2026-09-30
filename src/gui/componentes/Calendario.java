//---------------------- C A L E N D A R I O ----------------------
package gui.componentes;

//------------- IMPORTS
import gui.estilo.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.*;

public class Calendario extends JPanel {

    //------------------ A T R I B U T O S -----------------

    //-------------- FECHA
    private LocalDate fechaSeleccionada;
    private YearMonth mesActual;

    private DateTimeFormatter formatoFecha;

    //-------------- COMPONENTES
    private JLabel Lfecha;
    private JLabel Lmes;

    private JButton Bexpandir;
    private JButton Banterior;
    private JButton Bsiguiente;

    private JPanel Pcontenido;
    private JPanel Pcalendario;

    //-------------- POPUP
    private JPopupMenu popupCalendario;

    //-------------- ESTADO
    private boolean expandido = false;

    //-------------- COLORES
    private Color colorFondo = Colores.AMARILLO;
    private Color colorBorde = Colores.AMARILLO_FUERTE;
    private Color colorTexto = Colores.CORINTO;

    private Color colorCalendario = Colores.DURAZNO;
    private Color colorSeleccion = Colores.AMARILLO_FUERTE;
    private Color colorHover = Colores.NARANJA_SUAVE;

    //-------------- BORDE
    private int grosorBorde = 2;
    private int radio = 15;

    //-------------- TAMAÑO
    private int ancho = 300;
    private int altura = 45;


    //------------------ C O N S T R U C T O R -----------------

    public Calendario() {

        super();

        //---------------------- FECHA
        fechaSeleccionada = LocalDate.now();

        mesActual = YearMonth.from(
                fechaSeleccionada
        );

        //---------------------- FORMATO
        formatoFecha = DateTimeFormatter.ofPattern(
                "dd/MM/yyyy"
        );

        //---------------------- PANEL
        setOpaque(false);

        setLayout(
                new BorderLayout()
        );

        setPreferredSize(
                new Dimension(
                        ancho,
                        altura
                )
        );

        //---------------------- COMPONENTES
        inicializarEncabezado();

        inicializarCalendario();

        //---------------------- FECHA
        actualizarFecha();
    }


    //------------------ E N C A B E Z A D O -----------------

    private void inicializarEncabezado() {

        JPanel Pencabezado
                = new JPanel(
                        new BorderLayout()
                );

        Pencabezado.setOpaque(false);

        //---------------------- FECHA
        Lfecha = new JLabel();

        Lfecha.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lfecha.setForeground(
                colorTexto
        );

        Lfecha.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        Lfecha.setVerticalAlignment(
                SwingConstants.CENTER
        );

        Lfecha.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        Lfecha.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        alternarCalendario();
                    }
                }
        );

        //---------------------- BOTÓN
        Bexpandir = new JButton(
                "▼"
        );

        Bexpandir.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        Bexpandir.setForeground(
                colorTexto
        );

        Bexpandir.setOpaque(false);

        Bexpandir.setContentAreaFilled(false);

        Bexpandir.setBorderPainted(false);

        Bexpandir.setFocusPainted(false);

        Bexpandir.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        Bexpandir.setPreferredSize(
                new Dimension(
                        40,
                        altura
                )
        );

        Bexpandir.addActionListener(
                e -> alternarCalendario()
        );

        //---------------------- AGREGAR
        Pencabezado.add(
                Lfecha,
                BorderLayout.CENTER
        );

        Pencabezado.add(
                Bexpandir,
                BorderLayout.EAST
        );

        add(
                Pencabezado,
                BorderLayout.CENTER
        );
    }


    //------------------ C A L E N D A R I O -----------------

    private void inicializarCalendario() {

        //---------------------- CONTENIDO
        Pcontenido
                = new JPanel(
                        new BorderLayout()
                );

        Pcontenido.setOpaque(false);

        Pcontenido.setPreferredSize(
                new Dimension(
                        300,
                        290
                )
        );

        //---------------------- CABECERA
        JPanel Pmes
                = new JPanel(
                        new BorderLayout()
                );

        Pmes.setOpaque(false);

        //---------------------- ANTERIOR
        Banterior = crearBotonNavegacion(
                "<"
        );

        Banterior.addActionListener(
                e -> cambiarMes(-1)
        );

        //---------------------- MES
        Lmes = new JLabel();

        Lmes.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lmes.setForeground(
                colorTexto
        );

        Lmes.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        //---------------------- SIGUIENTE
        Bsiguiente = crearBotonNavegacion(
                ">"
        );

        Bsiguiente.addActionListener(
                e -> cambiarMes(1)
        );

        //---------------------- AGREGAR
        Pmes.add(
                Banterior,
                BorderLayout.WEST
        );

        Pmes.add(
                Lmes,
                BorderLayout.CENTER
        );

        Pmes.add(
                Bsiguiente,
                BorderLayout.EAST
        );

        //---------------------- DÍAS
        Pcalendario
                = new JPanel(
                        new GridLayout(
                                7,
                                7,
                                2,
                                2
                        )
                );

        Pcalendario.setOpaque(false);

        //---------------------- AGREGAR
        Pcontenido.add(
                Pmes,
                BorderLayout.NORTH
        );

        Pcontenido.add(
                Pcalendario,
                BorderLayout.CENTER
        );

        //---------------------- POPUP
        popupCalendario = new JPopupMenu();

        popupCalendario.setLayout(
                new BorderLayout()
        );

        popupCalendario.setOpaque(true);

        popupCalendario.setBackground(
                colorCalendario
        );

        popupCalendario.setBorder(
                BorderFactory.createLineBorder(
                        colorBorde,
                        grosorBorde
                )
        );

        popupCalendario.add(
                Pcontenido,
                BorderLayout.CENTER
        );

        actualizarCalendario();
    }


    //------------------ B O T Ó N   N A V E G A C I Ó N

    private JButton crearBotonNavegacion(
            String texto
    ) {

        JButton boton
                = new JButton(
                        texto
                );

        boton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        boton.setForeground(
                colorTexto
        );

        boton.setOpaque(false);

        boton.setContentAreaFilled(false);

        boton.setBorderPainted(false);

        boton.setFocusPainted(false);

        boton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        boton.setPreferredSize(
                new Dimension(
                        40,
                        35
                )
        );

        return boton;
    }


    //------------------ A C T U A L I Z A R   F E C H A

    private void actualizarFecha() {

        Lfecha.setText(
                fechaSeleccionada.format(
                        formatoFecha
                )
        );
    }


    //------------------ A C T U A L I Z A R   C A L E N D A R I O

    private void actualizarCalendario() {

        Pcalendario.removeAll();

        //---------------------- MES
        String mes
                = mesActual
                        .getMonth()
                        .getDisplayName(
                                TextStyle.FULL,
                                Locale.forLanguageTag(
                                        "es-GT"
                                )
                        );

        mes
                = Character.toUpperCase(
                        mes.charAt(0)
                )
                + mes.substring(1);

        Lmes.setText(
                mes
                + " "
                + mesActual.getYear()
        );

        //---------------------- DÍAS
        String[] nombresDias = {
            "Lu",
            "Ma",
            "Mi",
            "Ju",
            "Vi",
            "Sá",
            "Do"
        };

        for (String dia : nombresDias) {

            JLabel label
                    = new JLabel(
                            dia,
                            SwingConstants.CENTER
                    );

            label.setFont(
                    Fuentes.LFL.deriveFont(
                            Fuentes.PEQUENO
                    )
            );

            label.setForeground(
                    colorTexto
            );

            Pcalendario.add(
                    label
            );
        }

        //---------------------- PRIMER DÍA
        LocalDate primerDia
                = mesActual.atDay(1);

        int desplazamiento
                = primerDia
                        .getDayOfWeek()
                        .getValue()
                - 1;

        //---------------------- ESPACIOS
        for (int i = 0;
                i < desplazamiento;
                i++) {

            Pcalendario.add(
                    new JLabel()
            );
        }

        //---------------------- DÍAS
        for (int dia = 1;
                dia <= mesActual.lengthOfMonth();
                dia++) {

            LocalDate fecha
                    = mesActual.atDay(
                            dia
                    );

            Pcalendario.add(
                    crearBotonDia(
                            fecha
                    )
            );
        }

        Pcalendario.revalidate();

        Pcalendario.repaint();
    }


    //------------------ C R E A R   B O T Ó N   D Í A

    private JButton crearBotonDia(
            LocalDate fecha
    ) {

        JButton boton
                = new JButton(
                        String.valueOf(
                                fecha.getDayOfMonth()
                        )
                );

        boton.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.PEQUENO
                )
        );

        boton.setForeground(
                colorTexto
        );

        boton.setBackground(
                esFechaSeleccionada(fecha)
                        ? colorSeleccion
                        : colorCalendario
        );

        boton.setOpaque(true);

        boton.setContentAreaFilled(true);

        boton.setFocusPainted(false);

        boton.setBorder(
                BorderFactory.createLineBorder(
                        colorBorde,
                        1
                )
        );

        boton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        //---------------------- HOVER
        boton.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        if (!esFechaSeleccionada(
                                fecha
                        )) {

                            boton.setBackground(
                                    colorHover
                            );
                        }
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        boton.setBackground(
                                esFechaSeleccionada(
                                        fecha
                                )
                                        ? colorSeleccion
                                        : colorCalendario
                        );
                    }
                }
        );

        //---------------------- SELECCIÓN
        boton.addActionListener(
                e -> seleccionarFecha(
                        fecha
                )
        );

        return boton;
    }


    //------------------ S E L E C C I O N A R   F E C H A

    private void seleccionarFecha(
            LocalDate fecha
    ) {

        fechaSeleccionada = fecha;

        mesActual = YearMonth.from(
                fechaSeleccionada
        );

        actualizarFecha();

        actualizarCalendario();

        cerrarCalendario();
    }


    //------------------ C A M B I A R   M E S

    private void cambiarMes(
            int cantidad
    ) {

        mesActual = mesActual.plusMonths(
                cantidad
        );

        actualizarCalendario();
    }


    //------------------ A L T E R N A R

    private void alternarCalendario() {

        if (expandido) {

            cerrarCalendario();

        } else {

            abrirCalendario();
        }
    }


    //------------------ A B R I R

    private void abrirCalendario() {

        expandido = true;

        Bexpandir.setText(
                "▲"
        );

        popupCalendario.pack();

        popupCalendario.show(
                this,
                0,
                getHeight()
        );
    }


    //------------------ C E R R A R

    private void cerrarCalendario() {

        expandido = false;

        Bexpandir.setText(
                "▼"
        );

        popupCalendario.setVisible(
                false
        );
    }


    //------------------ F E C H A   S E L E C C I O N A D A

    private boolean esFechaSeleccionada(
            LocalDate fecha
    ) {

        return fecha.equals(
                fechaSeleccionada
        );
    }


    //------------------ T A M A Ñ O

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

        Bexpandir.setPreferredSize(
                new Dimension(
                        40,
                        altura
                )
        );

        revalidate();

        repaint();
    }


    //------------------ F E C H A

    public LocalDate getFecha() {

        return fechaSeleccionada;
    }

    public void setFecha(
            LocalDate fecha
    ) {

        if (fecha == null) {
            return;
        }

        fechaSeleccionada = fecha;

        mesActual = YearMonth.from(
                fecha
        );

        actualizarFecha();

        actualizarCalendario();
    }


    //------------------ C O L O R E S

    public void setColorFondo(
            Color color
    ) {

        colorFondo = color;

        repaint();
    }

    public void setColorBorde(
            Color color
    ) {

        colorBorde = color;

        popupCalendario.setBorder(
                BorderFactory.createLineBorder(
                        colorBorde,
                        grosorBorde
                )
        );

        actualizarCalendario();

        repaint();
    }

    public void setColorSeleccion(
            Color color
    ) {

        colorSeleccion = color;

        actualizarCalendario();
    }

    public void setColorHover(
            Color color
    ) {

        colorHover = color;

        actualizarCalendario();
    }

    public void setColorCalendario(
            Color color
    ) {

        colorCalendario = color;

        popupCalendario.setBackground(
                colorCalendario
        );

        actualizarCalendario();
    }

    public void setColorTexto(
            Color color
    ) {

        colorTexto = color;

        Lfecha.setForeground(
                color
        );

        Lmes.setForeground(
                color
        );

        actualizarCalendario();

        repaint();
    }


    //------------------ B O R D E

    public void setGrosorBorde(
            int grosor
    ) {

        grosorBorde = grosor;

        popupCalendario.setBorder(
                BorderFactory.createLineBorder(
                        colorBorde,
                        grosorBorde
                )
        );

        repaint();
    }

    public void setRadio(
            int radio
    ) {

        this.radio = radio;

        repaint();
    }


    //------------------ P I N T A D O

    @Override
    protected void paintComponent(
            Graphics g
    ) {

        Graphics2D g2
                = (Graphics2D) g.create();

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

        //---------------------- BORDE
        g2.setColor(
                colorBorde
        );

        g2.setStroke(
                new BasicStroke(
                        grosorBorde
                )
        );

        g2.drawRoundRect(
                grosorBorde / 2,
                grosorBorde / 2,
                getWidth() - grosorBorde - 1,
                getHeight() - grosorBorde - 1,
                radio,
                radio
        );

        g2.dispose();

        super.paintComponent(g);
    }
}