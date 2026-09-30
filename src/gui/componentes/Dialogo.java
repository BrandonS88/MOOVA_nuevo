//---------------------- D I A L O G O ----------------------
package gui.componentes;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * Diálogos con los botones traducidos al español.
 *
 * ¿Por qué existe esta clase?
 * JOptionPane.showConfirmDialog con la opción YES_NO_OPTION pone los
 * botones en el idioma del sistema operativo. Si la computadora está
 * configurada en inglés, al cajero le salen los botones "Yes" y "No"
 * mezclados con todos los textos del proyecto, que están en español.
 *
 * Esta clase reemplaza esa llamada por una que siempre muestra "Sí" y
 * "No", sin importar el idioma de la computadora.
 *
 * Para eso se usa showOptionDialog en vez de showConfirmDialog: el
 * primero recibe un arreglo con los textos de los botones, y el segundo
 * los pone con un idioma fijo que Swing no deja cambiar.
 *
 * Los valores que devuelve son los mismos de Swing, para no cambiar la
 * forma de comparar el resultado en el resto del código:
 *   0  = Sí
 *   1  = No
 *   -1 = el usuario cerró el cuadro con la X
 */
public final class Dialogo {

    // Etiquetas de los botones, siempre en español.
    // Se comparan con equals() y no con == porque Swing devuelve un
    // objeto nuevo en cada llamada, no el mismo String de aquí.
    private static final Object[] OPCIONES = {"Sí", "No"};

    // Botón de los cuadros que solo piden "entendido".
    private static final Object[] OPCION_ACEPTAR = {"Aceptar"};

    // El botón que aparece enfocado al abrir el cuadro. Se elige "No"
    // para que un doble clic accidental en Enter no ejecute algo
    // destructivo, como cancelar una venta o salir del programa.
    private static final Object OPCION_INICIAL = "No";

    // Lo mismo, pero para los cuadros de un solo botón: el foco queda en
    // "Aceptar" porque en esos no hay nada destructivo que evitar.
    private static final Object OPCION_INICIAL_ACEPTAR = "Aceptar";

    // Valores que devuelve confirmar(), con nombre para no acordarse
    // de los números al compararlos en el resto del código.
    public static final int SI = JOptionPane.YES_OPTION;
    public static final int NO = JOptionPane.NO_OPTION;
    public static final int CERRADO = JOptionPane.CLOSED_OPTION;

    // La clase solo tiene métodos estáticos, así que no se instancia.
    private Dialogo() {
    }

    /**
     * Muestra una pregunta de sí o no con los botones en español.
     *
     * @param padre componente sobre el que se centra el cuadro. Puede
     *             ser null, en cuyo caso sale centrado en la pantalla.
     * @param titulo título de la ventana del diálogo.
     * @param texto  pregunta que se le muestra al usuario.
     * @return Dialogo.SI, Dialogo.NO o Dialogo.CERRADO.
     */
    public static int confirmar(
            Component padre,
            String titulo,
            String texto
    ) {

        return confirmar(
                padre,
                titulo,
                texto,
                JOptionPane.QUESTION_MESSAGE
        );
    }

    /**
     * Igual que confirmar(), pero permite elegir el ícono del cuadro.
     *
     * Se usa JOptionPane.WARNING_MESSAGE cuando la acción es
     * destructiva (borrar algo, cerrar sesión, salir del programa) para
     * que el usuario la note antes de confirmar.
     *
     * @param padre      componente sobre el que se centra el cuadro.
     * @param titulo     título de la ventana del diálogo.
     * @param texto      pregunta que se le muestra al usuario.
     * @param tipoMensaje una de las constantes de JOptionPane.
     * @return Dialogo.SI, Dialogo.NO o Dialogo.CERRADO.
     */
    public static int confirmar(
            Component padre,
            String titulo,
            String texto,
            int tipoMensaje
    ) {

        // Se convierte a entero porque showOptionDialog devuelve Integer
        // (un objeto) y los métodos se comparan con int en el resto del
        // proyecto.
        //
        // OJO con el orden de los parámetros de showOptionDialog: el
        // cuarto es optionType y el quinto es messageType, al revés de
        // showConfirmDialog, donde el cuarto era messageType y no había
        // optionType. Si se confundieran, Swing recibe el tipo del ícono
        // donde espera el tipo de botón y lanza esta excepción:
        //   "JOptionPane: option type must be one of ..."
        //
        // El cuarto parámetro tiene que ser JOptionPane.YES_NO_OPTION
        // (no DEFAULT_OPTION, que Swing rechaza). Ese valor solo le dice
        // a Swing "este diálogo tiene dos respuestas"; los textos de los
        // botones salen del arreglo OPCIONES, no de su propio idioma.
        return (int) JOptionPane.showOptionDialog(
                padre,
                texto,
                titulo,
                JOptionPane.YES_NO_OPTION,
                tipoMensaje,
                null,
                OPCIONES,
                OPCION_INICIAL
        );
    }

    /**
     * Muestra una advertencia con el botón "Aceptar" en lugar del "OK"
     * que pone Swing cuando la computadora está en inglés.
     *
     * @param padre  componente sobre el que se centra el cuadro.
     * @param titulo título de la ventana del diálogo.
     * @param texto  mensaje a mostrar.
     */
    public static void aviso(
            Component padre,
            String titulo,
            String texto
    ) {

        mostrarConBoton(
                padre,
                titulo,
                texto,
                JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Muestra un mensaje informativo con el botón "Aceptar" en lugar
     * del "OK" que pone Swing cuando la computadora está en inglés.
     *
     * @param padre  componente sobre el que se centra el cuadro.
     * @param titulo título de la ventana del diálogo.
     * @param texto  mensaje a mostrar.
     */
    public static void informacion(
            Component padre,
            String titulo,
            String texto
    ) {

        mostrarConBoton(
                padre,
                titulo,
                texto,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Muestra un cuadro de error con el botón "Aceptar".
     *
     * @param padre  componente sobre el que se centra el cuadro.
     * @param titulo título de la ventana del diálogo.
     * @param texto  mensaje a mostrar.
     */
    public static void error(
            Component padre,
            String titulo,
            String texto
    ) {

        mostrarConBoton(
                padre,
                titulo,
                texto,
                JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Cuadro con un solo botón, "Aceptar".
     *
     * ¿Por qué no se usa JOptionPane.showMessageDialog? Porque ese
     * método pone el botón "OK" con el idioma del sistema operativo. En
     * una computadora en inglés salía "OK" en medio de textos en
     * español. showMessageDialog no permite cambiar ese texto, en
     * cambio showOptionDialog sí: basta con pasarle el arreglo de
     * botones que se quiere ver.
     *
     * @param padre       componente sobre el que se centra el cuadro.
     * @param titulo      título de la ventana del diálogo.
     * @param texto       mensaje a mostrar.
     * @param tipoMensaje una de las constantes de JOptionPane, que es
     *                    lo que decide el ícono del cuadro.
     */
    private static void mostrarConBoton(
            Component padre,
            String titulo,
            String texto,
            int tipoMensaje
    ) {

        // OJO con el orden: el cuarto parámetro es optionType y el
        // quinto es messageType, al revés de showConfirmDialog.
        // JOptionPane.DEFAULT_OPTION (-1) es el valor correcto aquí
        // porque este cuadro no pregunta nada, solo informa.
        //
        // El valor que devuelve se ignora: en este tipo de cuadro solo
        // hay una manera de salir, que es el botón.
        JOptionPane.showOptionDialog(
                padre,
                texto,
                titulo,
                JOptionPane.DEFAULT_OPTION,
                tipoMensaje,
                null,
                OPCION_ACEPTAR,
                OPCION_INICIAL_ACEPTAR
        );
    }
}
