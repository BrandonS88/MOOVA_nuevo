package gui.componentes;

import java.awt.*;
import javax.swing.*;
import gui.estilo.*;

public class PanelFactura extends PanelColor {

    //------------------------ A T R I B U T O S ----------------
    private JTextArea Ttexto;

    private static final int ANCHO = 46;

    private static final int MARGEN = 20;

    //------------------------ C O N S T R U C T O R ----------------
    public PanelFactura() {

        setColorFondo(
                Colores.BLANCO
        );

        setOpacidad(255);

        setRadio(30);

        setLayout(
                new BorderLayout()
        );

        inicializarTexto();
    }

    //------------------------ T E X T O ----------------
    private void inicializarTexto() {

        Ttexto = new JTextArea();

        Ttexto.setEditable(false);

        Ttexto.setOpaque(false);

        Ttexto.setLineWrap(false);

        Ttexto.setWrapStyleWord(false);

        Ttexto.setFont(
                new Font(
                        "Consolas",
                        Font.PLAIN,
                        14
                )
        );

        Ttexto.setForeground(
                Colores.CORINTO
        );

        Ttexto.setColumns(
                ANCHO
        );

        Ttexto.setBorder(
                BorderFactory.createEmptyBorder(
                        MARGEN,
                        MARGEN,
                        MARGEN,
                        MARGEN
                )
        );

        add(
                Ttexto,
                BorderLayout.CENTER
        );
    }

    //------------------------ C E N T R A R ----------------
    private String centrar(
            String texto
    ) {

        int espacios
                = (ANCHO - texto.length()) / 2;

        if (espacios <= 0) {
            return texto;
        }

        return " ".repeat(
                espacios
        ) + texto;
    }

    //------------------------ L Í N E A   C O N   G U I O N E S ----------------
    private String lineaConGuiones(
            String prefijo,
            String etiqueta,
            String valor
    ) {

        String base
                = prefijo + etiqueta;

        int espacioDisponible
                = ANCHO
                - base.length()
                - valor.length()
                - 2;

        if (espacioDisponible < 3) {

            espacioDisponible = 3;
        }

        return base
                + " "
                + "-".repeat(
                        espacioDisponible
                )
                + " "
                + valor;
    }

    //------------------------ F A C T U R A   D E   E J E M P L O ----------------
    public void mostrarFacturaEjemplo() {

        StringBuilder texto
                = new StringBuilder();

        //---------------------- ENCABEZADO
        texto.append(
                centrar(
                        "FACTURA"
                )
        ).append("\n\n");

        texto.append(
                centrar(
                        "MOOVA, S.A"
                )
        ).append("\n");

        texto.append(
                centrar(
                        "FECHA DE EMISIÓN: DD/MM/AAAA"
                )
        ).append("\n");

        texto.append(
                centrar(
                        "HORA DE EMISIÓN: HH:MM:SS"
                )
        ).append("\n");

        texto.append(
                centrar(
                        "ENCARGADO: SARA XD"
                )
        ).append("\n");

        texto.append(
                centrar(
                        "NO. FACTURA 0000-1"
                )
        ).append("\n");

        texto.append(
                centrar(
                        "NIT: 10101010-7"
                )
        ).append("\n\n");

        //---------------------- DESCRIPCIÓN
        texto.append(
                "DESCRIPCIÓN"
        ).append("\n\n");

        texto.append(
                "* MOO INSANA (COMBO #2)"
        ).append("\n");

        texto.append(
                lineaConGuiones(
                        "   * ",
                        "HAMBURGUESA",
                        "Q.25.00"
                )
        ).append("\n");

        texto.append(
                lineaConGuiones(
                        "   * ",
                        "ADICIONALES",
                        "Q.15.00"
                )
        ).append("\n");

        texto.append(
                "      * SODA"
        ).append("\n");

        texto.append(
                "      * PAPAS"
        ).append("\n\n\n");

        //---------------------- PAGO
        texto.append(
                "NIT: 7889877-7 / CF"
        ).append("\n");

        texto.append(
                "MÉTODO DE PAGO: Efectivo/Tarjeta"
        ).append("\n");

        texto.append(
                lineaConGuiones(
                        "",
                        "SUBTOTAL",
                        "Q.35.20"
                )
        ).append("\n");

        texto.append(
                lineaConGuiones(
                        "",
                        "IVA",
                        "Q.4.80"
                )
        ).append("\n");

        texto.append(
                lineaConGuiones(
                        "",
                        "TOTAL",
                        "Q.40.00"
                )
        ).append("\n\n");

        texto.append(
                lineaConGuiones(
                        "",
                        "CAMBIO",
                        "Q.00.00"
                )
        ).append("\n");

        texto.append(
                "CUARENTA QUETZALES EXACTOS "
                + "CON 00/00 CENTAVOS"
        ).append("\n\n");

        texto.append(
                "\"Nos robamos las vacas, "
                + "te dejamos la comida\""
        );

        //---------------------- MOSTRAR
        Ttexto.setText(
                texto.toString()
        );

        //---------------------- AJUSTAR TAMAÑO
        actualizarTamaño();
    }

    //------------------------ F A C T U R A   D E   U N A   V E N T A ----------------
    /**
     * Muestra la factura de una venta real de la base de datos.
     *
     * SeArma el mismo formato de la factura de ejemplo, pero con los datos
     * que realmente trae la venta. Se usa un formato de dinero sin
     * separador de miles para que el ancho de la factura no cambie y las
     * columnas queden alineadas.
     *
     * @param idVenta       id de la venta.
     * @param fecha         fecha de realización de la venta.
     * @param nit           NIT del cliente.
     * @param metodoPago    método de pago usado.
     * @param estado        estado de la venta (Pagado, Cancelado...).
     * @param cajero        nombre de usuario del cajero que la atendió.
     * @param detalles      filas del detalle. Cada fila trae:
     *                      {descripcion, cantidad, precioUnitario, subtotal,
     *                       impuesto, montoDescuento}
     * @param total         total pagado de la venta.
     */
    public void mostrarFacturaVenta(
            int idVenta,
            Object fecha,
            String nit,
            String metodoPago,
            String estado,
            String cajero,
            java.util.List<Object[]> detalles,
            double total
    ) {

        StringBuilder texto = new StringBuilder();

        //---------------------- ENCABEZADO
        texto.append(
                centrar("FACTURA")
        ).append("\n\n");

        texto.append(
                centrar("MOOVA, S.A")
        ).append("\n\n");

        texto.append(
                centrar("VENTA N° " + idVenta)
        ).append("\n\n");

        texto.append(
                centrar("FECHA: " + fecha)
        ).append("\n");

        texto.append(
                centrar("CAJERO: " + (cajero == null || cajero.isEmpty() ? "-" : cajero))
        ).append("\n");

        texto.append(
                centrar("NIT CLIENTE: " + (nit == null || nit.isEmpty() ? "C/F" : nit))
        ).append("\n");

        texto.append(
                centrar("ESTADO: " + estado)
        ).append("\n\n");

        //---------------------- D E T A L L E
        texto.append(
                lineaConGuiones("DESCRIPCION", "CANT", "SUBTOTAL")
        ).append("\n");

        if (detalles == null || detalles.isEmpty()) {

            texto.append(
                    "  (sin productos en esta venta)"
            ).append("\n");

        } else {

            for (Object[] d : detalles) {

                String descripcion = d[0] == null ? "" : String.valueOf(d[0]);

                texto.append(
                        lineaConGuiones(
                                "  " + descripcion,
                                String.valueOf(d[1]),
                                dinero(d[3])
                        )
                ).append("\n");
            }
        }

        texto.append("\n");

        //---------------------- T O T A L E S
        texto.append(
                lineaConGuiones("TOTAL", "", dinero(total))
        ).append("\n\n");

        texto.append(
                centrar("METODO DE PAGO: " + (metodoPago == null ? "-" : metodoPago))
        ).append("\n\n");

        texto.append(
                centrar("\"Nos robamos las vacas,")
        ).append("\n");

        texto.append(
                centrar("te dejamos la comida\"")
        ).append("\n");

        //---------------------- MOSTRAR
        Ttexto.setText(
                texto.toString()
        );

        actualizarTamaño();
    }

    /**
     * Da formato a un importe con dos decimales.
     * Se usa %.2f para que los importes siempre se vean igual de alineados.
     */
    private String dinero(Object valor) {

        double numero;

        if (valor instanceof Number) {
            numero = ((Number) valor).doubleValue();
        } else {
            try {
                numero = Double.parseDouble(String.valueOf(valor));
            } catch (Exception e) {
                numero = 0.0;
            }
        }

        return String.format("Q.%.2f", numero);
    }

    //------------------------ A V I S O   S I N   V E N T A ----------------
    /**
     * Avisa que esta pantalla necesita una venta seleccionada para poder
     * mostrar algo útil. Se usa cuando se abre sin id.
     */
    public void mostrarAvisoSinVenta() {

        Ttexto.setText(
                centrar("SIN VENTA SELECCIONADA") + "\n\n"
                        + "Vuelve al historial y elige una venta\n"
                        + "de la tabla para ver su detalle."
        );

        actualizarTamaño();
    }

    //------------------------ A C T U A L I Z A R   T A M A Ñ O
    private void actualizarTamaño() {

        FontMetrics fm
                = Ttexto.getFontMetrics(
                        Ttexto.getFont()
                );

        int anchoTexto
                = fm.charWidth('M') * ANCHO;

        int numeroLineas
                = Ttexto.getLineCount();

        int alturaTexto
                = numeroLineas
                * fm.getHeight();

        int anchoTotal
                = anchoTexto
                + (MARGEN * 2);

        int alturaTotal
                = alturaTexto
                + (MARGEN * 2);

        setPreferredSize(
                new Dimension(
                        anchoTotal,
                        alturaTotal
                )
        );

        revalidate();

        repaint();
    }
}