//------------------ P A N T A L L A   D A S H B O A R D ------------------
package gui.administrador;

import gui.componentes.Boton;
import gui.componentes.PanelColor;
import gui.estilo.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class PantallaDashboard extends VentanaAdministrador {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Panel de fecha
    private PanelColor Pfecha;

    //-------------- Labels de fecha
    private JLabel Ldatos;
    private JLabel Lfecha;

    //-------------- Paneles estadísticos
    private PanelColor Pventas;
    private PanelColor Palertas;
    private PanelColor Pingresos;

    //-------------- Labels estadísticos
    private JLabel Lventas;
    private JLabel Lalertas;
    private JLabel Lingresos;

    //-------------- Panel eventos
    private PanelColor Peventos;

    //-------------- Label eventos
    private JLabel Leventos;

    //-------------- Botón
    private Boton Bincidentes;

    // Panel del gráfico de ventas por día
    private GraficoVentas grafico;

    // Temporizador que refresca los datos cada 30 segundos.
    // Se guarda como atributo para poder detenerlo al cerrar la ventana:
    // si siguiera vivo, el programa seguiría consultando la base de datos
    // aunque el dashboard ya no esté visible.
    //
    // javax.swing.Timer se detiene con stop(), no con cancel() (cancel() es
    // de java.util.Timer, el otro tipo de temporizador).
    private javax.swing.Timer temporizador;

    //------------------------ C O N S T R U C T O R ----------------
    public PantallaDashboard() {

        super("Dashboard");

        inicializarDashboard();

        setVisible(true);
    }

    //------------------------ D A S H B O A R D ----------------
    private void inicializarDashboard() {

        //---------------------- PANEL CENTRAL
        Pcentral.setLayout(
                new GridBagLayout()
        );

        //---------------------- CREAR COMPONENTES
        inicializarFecha();

        inicializarEstadisticas();

        inicializarEventos();

        //---------------------- ACTUALIZAR ESTADÍSTICAS
        // Carga inicial de datos desde la base de datos
        actualizarEstadisticas();

        // Actualización automática cada 30 segundos
        // para que los datos se mantengan frescos sin recargar la pantalla
        //
        // Se usa javax.swing.Timer y NO java.util.Timer. La diferencia es
        // importante: javax.swing.Timer dispara su evento en el mismo hilo
        // que dibuja la ventana (el EDT), así que puede tocar etiquetas y
        // gráficos sin riesgo. java.util.Timer corre en un hilo aparte, y
        // modificar componentes de Swing desde ahí es un error: Swing no
        // está preparado para dos hilos pintando a la vez, y el síntoma
        // típico son parpadeos y la interfaz que se queda pegada.
        temporizador = new javax.swing.Timer(
                30000,
                e -> actualizarEstadisticas()
        );

        temporizador.start();
    }

    /**
     * Detiene el refresco automático al cerrar la pantalla.
     * Sin esto el programa seguiría consultando la base de datos cada
     * 30 segundos aunque el dashboard ya no se vea.
     */
    @Override
    public void dispose() {

        if (temporizador != null) {

            temporizador.stop();
        }

        super.dispose();
    }

    /**
     * Vuelve a consultar los contadores cada vez que se muestra la
     * pantalla.
     *
     * La ventana se reutiliza en vez de construirse de nuevo, así que
     * los números que tenía de la última visita pueden estar viejos: si
     * mientras tanto se registraron ventas o se crearon incidentes, hay
     * que volver a leerlos de la base de datos.
     */
    @Override
    protected void alMostrar() {

        actualizarEstadisticas();
    }

    //------------------------ F E C H A ----------------
    private void inicializarFecha() {

        Pfecha = new PanelColor();

        Pfecha.setColorFondo(
                Colores.AMARILLO
        );

        Pfecha.setOpacidad(255);

        Pfecha.setRadio(15);

        //---------------------- LABEL IZQUIERDO
        Ldatos = new JLabel(
                "Datos correspondientes a:"
        );

        Ldatos.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.PEQUENO
                )
        );

        Ldatos.setForeground(
                Colores.CORINTO
        );

        Ldatos.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        //---------------------- FECHA ACTUAL
        DateTimeFormatter formato
                = DateTimeFormatter.ofPattern(
                        "dd/MM/yyyy"
                );

        Lfecha = new JLabel(
                LocalDate.now().format(formato)
        );

        Lfecha.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lfecha.setForeground(
                Colores.CORINTO
        );

        Lfecha.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        //---------------------- PANEL
        Pfecha.setLayout(
                new GridBagLayout()
        );

        //---------------------- DATOS
        GridBagConstraints gbcDatos
                = new GridBagConstraints();

        gbcDatos.gridx = 0;
        gbcDatos.gridy = 0;

        gbcDatos.weightx = 0.5;
        gbcDatos.weighty = 1.0;

        gbcDatos.anchor =
                GridBagConstraints.CENTER;

        gbcDatos.fill =
                GridBagConstraints.HORIZONTAL;

        gbcDatos.insets = new Insets(
                0,
                10,
                0,
                5
        );

        Pfecha.add(
                Ldatos,
                gbcDatos
        );

        //---------------------- FECHA
        GridBagConstraints gbcFecha
                = new GridBagConstraints();

        gbcFecha.gridx = 1;
        gbcFecha.gridy = 0;

        gbcFecha.weightx = 0.5;
        gbcFecha.weighty = 1.0;

        gbcFecha.anchor =
                GridBagConstraints.CENTER;

        gbcFecha.fill =
                GridBagConstraints.HORIZONTAL;

        gbcFecha.insets = new Insets(
                0,
                5,
                0,
                10
        );

        Pfecha.add(
                Lfecha,
                gbcFecha
        );

        //---------------------- POSICIÓN
        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridwidth = 3;

        gbc.weightx = 1.0;
        gbc.weighty = 0.045;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.BOTH;

        gbc.insets = new Insets(
                5,
                15,
                3,
                15
        );

        Pcentral.add(
                Pfecha,
                gbc
        );
    }

    //------------------------ E S T A D Í S T I C A S ----------------
    private void inicializarEstadisticas() {

        //---------------------- PANEL VENTAS
        Pventas = crearPanelEstadistica(
                Colores.NARANJA_SUAVE,
                Colores.NARANJA_FUERTE,
                "Ventas actuales"
        );

        //---------------------- PANEL ALERTAS
        Palertas = crearPanelEstadistica(
                Colores.NARANJA_ROJIZO_SUAVE,
                Colores.NARANJA_ROJIZO_FUERTE,
                "Alertas actuales"
        );

        //---------------------- PANEL INGRESOS
        Pingresos = crearPanelEstadistica(
                Colores.VERDE_SUAVE,
                Colores.VERDE_FUERTE,
                "Ingresos actuales"
        );

        //---------------------- NUMEROS
        Lventas = new JLabel(
                "0"
        );

        Lalertas = new JLabel(
                "0"
        );

        Lingresos = new JLabel(
                "0"
        );

        //---------------------- AGREGAR NUMEROS
        agregarNumero(
                Pventas,
                Lventas
        );

        agregarNumero(
                Palertas,
                Lalertas
        );

        agregarNumero(
                Pingresos,
                Lingresos
        );

        //---------------------- POSICIONES
        agregarPanelEstadistica(
                Pventas,
                0
        );

        agregarPanelEstadistica(
                Palertas,
                1
        );

        agregarPanelEstadistica(
                Pingresos,
                2
        );
    }

    //------------------------ A C T U A L I Z A R   E S T A D Í S T I C A S ----------------
    /**
     * Consulta la base de datos y actualiza los contadores del dashboard
     * con datos reales: ventas del día, alertas pendientes e ingresos del día.
     * 
     * Este método se llama al abrir la pantalla y cada 30 segundos
     * para mantener la información actualizada.
     */
    private void actualizarEstadisticas() {

        // Usamos un hilo separado para no congelar la interfaz gráfica
        // mientras se realizan las consultas a la base de datos
        new Thread(() -> {
            try {
                // Conexión a la base de datos usando la clase de conexión del proyecto
                Connection con = new conexion.Conexion_MOOVA().getConnection();
                
                if (con == null) {
                    System.out.println("No se pudo conectar a la base de datos para actualizar estadísticas.");
                    return;
                }

                //---------------------- VENTAS DEL DÍA
                // Cuenta cuántas ventas se han realizado hoy
                String sqlVentas = "SELECT COUNT(*) FROM Venta WHERE DATE(fecha_realizacion) = CURDATE()";
                try (Statement stmt = con.createStatement();
                     ResultSet rs = stmt.executeQuery(sqlVentas)) {
                    if (rs.next()) {
                        int ventas = rs.getInt(1);
                        // Actualizamos el label en el hilo de eventos de Swing
                        SwingUtilities.invokeLater(() -> Lventas.setText(String.valueOf(ventas)));
                    }
                }

                //---------------------- ALERTAS PENDIENTES
                // Cuenta cuántos incidentes están pendientes o en proceso
                String sqlAlertas = "SELECT COUNT(*) FROM Incidente WHERE estado IN ('Pendiente', 'En proceso')";
                try (Statement stmt = con.createStatement();
                     ResultSet rs = stmt.executeQuery(sqlAlertas)) {
                    if (rs.next()) {
                        int alertas = rs.getInt(1);
                        SwingUtilities.invokeLater(() -> Lalertas.setText(String.valueOf(alertas)));
                    }
                }

                //---------------------- INGRESOS DEL DÍA
                // Suma el total de todas las ventas realizadas hoy
                String sqlIngresos = "SELECT COALESCE(SUM(total), 0) FROM Venta WHERE DATE(fecha_realizacion) = CURDATE()";
                try (Statement stmt = con.createStatement();
                     ResultSet rs = stmt.executeQuery(sqlIngresos)) {
                    if (rs.next()) {
                        double ingresos = rs.getDouble(1);
                        // Formateamos el número con 2 decimales
                        SwingUtilities.invokeLater(() -> Lingresos.setText(String.format("%.2f", ingresos)));
                    }
                }

                con.close();

                //---------------------- G R Á F I C O
                // El gráfico se consulta aparte porque abriría su propia
                // conexión. Se hace aquí dentro del hilo para que la
                // interfaz no se congele esperando a MySQL.
                final double[] ventasPorDia = consultarVentasPorDia();

                // El pintado de Swing debe ocurrir en el hilo de eventos,
                // nunca desde este hilo secundario.
                SwingUtilities.invokeLater(
                        () -> grafico.setVentas(ventasPorDia)
                );

            } catch (SQLException e) {
                System.out.println("Error al actualizar estadísticas: " + e.getMessage());
            }
        }).start();
    }

    //------------------------ C R E A R   P A N E L
    private PanelColor crearPanelEstadistica(
            Color fondo,
            Color borde,
            String texto
    ) {

        PanelColor panel
                = new PanelColor();

        panel.setColorFondo(
                fondo
        );

        panel.setOpacidad(255);

        panel.setColorBorde(
                borde
        );

        panel.setGrosorBorde(3);

        panel.setRadio(25);

        panel.setLayout(
                new GridBagLayout()
        );

        //---------------------- TEXTO
        JLabel labelTexto
                = new JLabel(
                        texto
                );

        labelTexto.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        labelTexto.setForeground(
                Colores.CORINTO
        );

        labelTexto.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        GridBagConstraints gbcTexto
                = new GridBagConstraints();

        gbcTexto.gridx = 0;
        gbcTexto.gridy = 1;

        gbcTexto.weightx = 1.0;
        gbcTexto.weighty = 0.45;

        gbcTexto.anchor =
                GridBagConstraints.CENTER;

        gbcTexto.fill =
                GridBagConstraints.HORIZONTAL;

        gbcTexto.insets = new Insets(
                0,
                10,
                10,
                10
        );

        panel.add(
                labelTexto,
                gbcTexto
        );

        return panel;
    }

    //------------------------ A G R E G A R   N Ú M E R O
    private void agregarNumero(
            PanelColor panel,
            JLabel label
    ) {

        label.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.TITULO
                )
        );

        label.setForeground(
                Colores.CORINTO
        );

        label.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 0.55;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                10,
                10,
                0,
                10
        );

        panel.add(
                label,
                gbc
        );
    }

    //------------------------ A G R E G A R   E S T A D Í S T I C A
    private void agregarPanelEstadistica(
            PanelColor panel,
            int columna
    ) {

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = columna;
        gbc.gridy = 1;

        gbc.weightx = 1.0;
        gbc.weighty = 0.28;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.BOTH;

        gbc.insets = new Insets(
                6,
                15,
                6,
                15
        );

        Pcentral.add(
                panel,
                gbc
        );
    }

    //------------------------------------------------------------------------
    //                    G R Á F I C O   D E   V E N T A S
    //------------------------------------------------------------------------
    /**
     * Arma el panel con el gráfico de barras de ventas por día.
     *
     * Los tres contadores de arriba dicen cuánta plata entró HOY, pero no
     * muestran cómo van los últimos días. Este gráfico llena ese hueco: se
     * ve de un vistazo qué día se vendió más y si las ventas están subiendo
     * o bajando.
     */
    private void inicializarGrafico() {

        PanelColor Pgrafico = new PanelColor();

        Pgrafico.setColorFondo(
                Colores.DURAZNO
        );

        Pgrafico.setOpacidad(255);

        Pgrafico.setColorBorde(
                Colores.NARANJA
        );

        Pgrafico.setGrosorBorde(3);

        Pgrafico.setRadio(25);

        Pgrafico.setLayout(
                new BorderLayout()
        );

        //---------------------- T I T U L O
        JLabel LtituloGrafico = new JLabel(
                "Ventas de los últimos 7 días"
        );

        LtituloGrafico.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        LtituloGrafico.setForeground(
                Colores.CORINTO
        );

        LtituloGrafico.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        Pgrafico.add(
                LtituloGrafico,
                BorderLayout.NORTH
        );

        //---------------------- D I B U J O
        grafico = new GraficoVentas();

        grafico.setPreferredSize(
                new Dimension(600, 220)
        );

        Pgrafico.add(
                grafico,
                BorderLayout.CENTER
        );

        //---------------------- P O S I C I Ó N
        // El gráfico va en la fila 3 ocupando las 3 columnas, porque la
        // fila 2 ya la usa el panel de eventos en la columna 2. Si se
        // pusiera también en la fila 2, los dos paneles se superpondrían y
        // el gráfico taparía el botón de Incidentes.
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0.30;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(
                10,
                15,
                15,
                15
        );

        Pcentral.add(
                Pgrafico,
                gbc
        );
    }

    /**
     * Consulta el total de ventas de cada uno de los últimos 7 días.
     *
     * Se usa DATE(fecha_realizacion) para agrupar por día, porque la columna
     * guarda fecha con hora y sin ese recorte MySQL contaría cada venta
     * como un día distinto.
     *
     * Solo se suman las ventas Pagadas: una venta cancelada o sin pagar no
     * es ingreso real de la empresa.
     *
     * @return arreglo con 7 valores, del más antiguo al más reciente.
     */
    private double[] consultarVentasPorDia() {

        double[] ventas = new double[7];

        String sql = "SELECT DATE(v.fecha_realizacion) AS dia, "
                + "COALESCE(SUM(v.total), 0) AS total "
                + "FROM Venta v "
                + "WHERE v.estado = ? "
                + "AND DATE(v.fecha_realizacion) >= DATE_SUB(CURDATE(), INTERVAL 6 DAY) "
                + "AND DATE(v.fecha_realizacion) <= CURDATE() "
                + "GROUP BY DATE(v.fecha_realizacion)";

        try (Connection con = new conexion.Conexion_MOOVA().getConnection()) {

            if (con == null) {
                return ventas;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, "Pagado");

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        // Se convierte la fecha de MySQL a LocalDate.
                        LocalDate dia = rs.getDate("dia").toLocalDate();

                        // Se calcula cuántos días atrás fue esa venta.
                        // El rango son 7 días: hoy (0) y 6 días atrás.
                        long diasAtras = java.time.temporal.ChronoUnit
                                .DAYS.between(
                                        dia,
                                        LocalDate.now()
                                );

                        if (diasAtras >= 0 && diasAtras < 7) {

                            // El índice 6 es hoy, y el 0 es hace 6 días.
                            ventas[6 - (int) diasAtras]
                                    = rs.getDouble("total");
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al consultar ventas por día: "
                            + e.getMessage()
            );
        }

        return ventas;
    }

    //------------------------------------------------------------------------
    //        C O M P O N E N T E   D E L   G R Á F I C O
    //------------------------------------------------------------------------
    /**
     * Panel que dibuja el gráfico de barras.
     *
     * Se dibuja a mano con paintComponent en vez de usar una librería
     * externa: así el proyecto no necesita dependencias nuevas y el
     * gráfico usa los mismos colores del resto de la aplicación.
     */
    private static class GraficoVentas extends JPanel {

        // Totales de cada día, del más antiguo (índice 0) al más
        // reciente (índice 6).
        private double[] ventas = new double[7];

        GraficoVentas() {

            setOpaque(false);
        }

        void setVentas(double[] valores) {

            this.ventas = valores == null ? new double[7] : valores;

            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // Se reserva espacio abajo para los nombres de los días.
            int margenInferior = 30;
            int margenSuperior = 25;
            int margenLateral = 10;

            int altoUtil = getHeight() - margenInferior - margenSuperior;
            int anchoUtil = getWidth() - (margenLateral * 2);

            if (anchoUtil <= 0 || altoUtil <= 0) {
                g2.dispose();
                return;
            }

            // Se busca el mayor total para escalar las barras. Si todos los
            // días están en cero, se usa 1 para no dividir entre cero.
            double maximo = 1;

            for (double v : ventas) {
                if (v > maximo) {
                    maximo = v;
                }
            }

            int cantidad = ventas.length;
            int anchoBarra = anchoUtil / cantidad;

            // Se calcula el día de la semana de cada punto para rotularlo.
            LocalDate hoy = LocalDate.now();

            for (int i = 0; i < cantidad; i++) {

                int x = margenLateral + (i * anchoBarra);

                // La barra del último índice es la de hoy y se pinta de
                // otro color para que destaque entre las anteriores.
                boolean esHoy = i == cantidad - 1;

                // Se calcula la altura proporcional al total.
                int altoBarra = (int) (
                        (ventas[i] / maximo) * altoUtil
                );

                int y = margenSuperior + (altoUtil - altoBarra);

                // Si no hubo ventas ese día, se dibuja una línea base para
                // que se note que el díaexists aunque el valor sea cero.
                if (altoBarra < 2) {
                    altoBarra = 2;
                    y = margenSuperior + altoUtil - 2;
                }

                g2.setColor(
                        esHoy ? Colores.ROJO : Colores.NARANJA_FUERTE
                );

                g2.fillRoundRect(
                        x + 8,
                        y,
                        anchoBarra - 16,
                        altoBarra,
                        10,
                        10
                );

                //---------------------- V A L O R
                // Se escribe el monto encima de la barra, salvo si es cero,
                // para no saturar el gráfico con ceros.
                if (ventas[i] > 0) {

                    g2.setFont(
                            Fuentes.LFL.deriveFont(
                                    Fuentes.PEQUENO
                            )
                    );

                    g2.setColor(
                            Colores.CORINTO
                    );

                    String monto
                            = String.format("%.0f", ventas[i]);

                    int anchoTexto
                            = g2.getFontMetrics()
                            .stringWidth(monto);

                    g2.drawString(
                            monto,
                            x + ((anchoBarra - anchoTexto) / 2),
                            Math.max(
                                    margenSuperior - 5,
                                    y - 5
                            )
                    );
                }

                //---------------------- D Í A
                g2.setFont(
                        Fuentes.LFL.deriveFont(
                                Fuentes.PEQUENO
                        )
                );

                g2.setColor(
                        Colores.CORINTO
                );

                // Se rotula con el nombre corto del día de la semana.
                String diaSemana
                        = hoy.minusDays(6 - (long) i)
                        .getDayOfWeek()
                        .getDisplayName(
                                java.time.format.TextStyle.SHORT,
                                java.util.Locale.forLanguageTag("es")
                        );

                int anchoDia
                        = g2.getFontMetrics()
                        .stringWidth(diaSemana);

                g2.drawString(
                        diaSemana,
                        x + ((anchoBarra - anchoDia) / 2),
                        getHeight() - 10
                );
            }

            g2.dispose();
        }
    }

    //------------------------ E V E N T O S ----------------
    private void inicializarEventos() {

        Peventos = new PanelColor();

        Peventos.setColorFondo(
                Colores.AMARILLO
        );

        Peventos.setOpacidad(255);

        Peventos.setColorBorde(
                Colores.AMARILLO_PATITO
        );

        Peventos.setGrosorBorde(3);

        Peventos.setRadio(25);

        Peventos.setLayout(
                new GridBagLayout()
        );

        //---------------------- TITULO
        Leventos = new JLabel(
                "Gestión de eventos"
        );

        Leventos.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Leventos.setForeground(
                Colores.CORINTO
        );

        Leventos.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        GridBagConstraints gbcTitulo
                = new GridBagConstraints();

        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;

        gbcTitulo.weightx = 1.0;
        gbcTitulo.weighty = 0.40;

        gbcTitulo.anchor =
                GridBagConstraints.CENTER;

        gbcTitulo.fill =
                GridBagConstraints.HORIZONTAL;

        gbcTitulo.insets = new Insets(
                10,
                10,
                5,
                10
        );

        Peventos.add(
                Leventos,
                gbcTitulo
        );

        //---------------------- BOTÓN
        Bincidentes = new Boton(
                "Incidentes"
        );

        Bincidentes.verde();

        Bincidentes.addActionListener(e -> {
            abrir(PantallaIncidentes.class, PantallaIncidentes::new);
        });

        //---------------------- G R Á F I C O
        // Se arma al final para que quede debajo de los paneles de
        // estadísticas y no desplace los contadores que ya están listos.
        inicializarGrafico();

        GridBagConstraints gbcBoton
                = new GridBagConstraints();

        gbcBoton.gridx = 0;
        gbcBoton.gridy = 1;

        gbcBoton.weightx = 1.0;
        gbcBoton.weighty = 0.60;

        gbcBoton.anchor =
                GridBagConstraints.CENTER;

        gbcBoton.fill =
                GridBagConstraints.NONE;

        gbcBoton.insets = new Insets(
                5,
                10,
                15,
                10
        );

        Peventos.add(
                Bincidentes,
                gbcBoton
        );

        //---------------------- POSICIÓN
        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.gridx = 2;
        gbc.gridy = 2;

        gbc.weightx = 1.0;
        gbc.weighty = 0.57;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.BOTH;

        gbc.insets = new Insets(
                6,
                15,
                10,
                15
        );

        Pcentral.add(
                Peventos,
                gbc
        );
    }
}