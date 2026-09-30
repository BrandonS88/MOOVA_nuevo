//---------------------------------- V E N T A N A   A D M I N I S T R A D O R -----------------------
package gui.administrador;

import java.awt.*;
import javax.swing.*;
import gui.componentes.*;
import gui.estilo.*;
import gui.login.PantallaLogin;
// Se importa la pantalla principal del cajero porque el botón "Pedidos"
// del panel lateral del administrador abre esa interfaz.
import gui.cajero.PantallaPrincipal;

public class VentanaAdministrador extends JFrame {

    //------------------------ A T R I B U T O S ----------------
    private JPanel Pprincipal;
    private Encabezado encabezado;
    private PanelFondo Pfondo;
    private PanelContenidoAdministrador panelAdministrador;
    private BarraInferior barraInferior;

    protected PanelColor Pcentral;

    // Registro de ventanas del módulo administrador.
    //
    // ¿Para qué sirve esto? Al navegar por los menús se abría una ventana
    // nueva cada vez, y como ninguna se cerraba realmente al cambiar de
    // sección quedaban varias ventanas de MOOVA apiladas. Con este registro
    // se sabe qué ventanas hay abiertas para poder cerrar la anterior antes
    // de abrir la siguiente.
    //
    // Además guarda la ventana YA CONSTRUIDA de cada tipo. Antes cada
    // visita a una pantalla armaba la ventana desde cero: volvía a leer la
    // imagen del fondo, a crear todos los componentes y a consultar la base
    // de datos. Como eso tarda, se veía el escritorio un instante antes
    // de que apareciera la pantalla nueva (el "parpadeo"). Reutilizando la
    // ventana ya armada, volver a una pantalla es instantáneo.
    private static final java.util.Set<Window> VENTANAS_ABIERTAS
            = java.util.Collections.synchronizedSet(
                    new java.util.HashSet<>()
            );

    // Ventanas ya construidas, guardadas por su clase.
    private static final java.util.Map<Class<?>, VentanaAdministrador> VENTANAS
            = java.util.Collections.synchronizedMap(
                    new java.util.HashMap<>()
            );

    //------------------------ C O N S T R U C T O R ----------------
    public VentanaAdministrador(String titulo) {

        setTitle(titulo);

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setDefaultCloseOperation(
                EXIT_ON_CLOSE
        );

        setResizable(false);

        setUndecorated(true);

        // Las ventanas anteriores NO se esconden todavía. Se esconden
        // cuando esta ya esté visible (ver windowOpened), para que el
        // usuario nunca se quede mirando el escritorio vacío mientras la
        // ventana nueva se construye.
        inicializarComponentes();

        // Se registra esta ventana para poder cerrarla después.
        VENTANAS_ABIERTAS.add(this);

        // windowOpened se dispara después de setVisible(true), es decir,
        // cuando esta ventana ya está en pantalla. Ese es el momento
        // exacto en que hay que esconder las anteriores: antes se vería
        // un hueco sin ventanas y después se verían las dos juntas.
        addWindowListener(
                new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowOpened(java.awt.event.WindowEvent e) {
                        VentanaAdministrador.this.cerrarVentanasAdministrador();
                    }
                }
        );
    }

    //---------------------- A B R I R   V E N T A N A ----------------
    /**
     * Abre la ventana del tipo indicado, reutilizándola si ya existe.
     *
     * Antes todas las pantallas se abrían con "new": cada visita creaba
     * una ventana desde cero (leyendo imágenes, armando componentes y
     * consultando la base de datos) y cerraba la anterior. Ese tiempo de
     * construcción era lo que se veía como parpadeo.
     *
     * Ahora la ventana se construye una sola vez. Las siguientes visitas
     * solo la vuelven a mostrar, que es inmediato.
     *
     * @param tipo    clase de la ventana, por ejemplo PantallaDashboard.class
     * @param fabrica se usa para construir la ventana la primera vez
     * @return la ventana, ya visible
     */
    public static <T extends VentanaAdministrador> T abrir(
            Class<T> tipo,
            java.util.function.Supplier<T> fabrica
    ) {

        T ventana = (T) VENTANAS.get(tipo);

        if (ventana != null && ventana.isDisplayable()) {

            // La ventana ya existe: solo hay que volver a mostrarla.
            // Se llama a alMostrar() para que recargue sus datos, porque
            // mientras estuvo escondida la base de datos pudo cambiar.
            ventana.alMostrar();
            ventana.setVisible(true);
            ventana.toFront();

            return ventana;
        }

        // Primera visita a esta pantalla: se construye.
        return fabrica.get();
    }

    /**
     * Se llama cada vez que la ventana vuelve a la pantalla después de
     * haber estado escondida.
     *
     * Por defecto no hace nada. Cada pantalla la sobrescribe si necesita
     * recargar datos (por ejemplo, volver a consultar la tabla o los
     * contadores).
     */
    protected void alMostrar() {
    }

    /**
     * Esconde esta ventana sin cerrarla, para poder volver a mostrarla
     * después.
     *
     * No se usa dispose() porque esa llamada destruye la ventana: si se
     * destruyera, la próxima visita tendría que construirla de nuevo y
     * volvería el parpadeo.
     */
    public void ocultar() {

        setVisible(false);
    }

    /**
     * Esconde todas las ventanas del administrador que sigan visibles,
     * menos la que se está abriendo.
     *
     * Se usa setVisible(false) y no dispose() por lo mismo que en
     * ocultar(): la ventana se conserva para reutilizarla.
     */
    private void cerrarVentanasAdministrador() {

        synchronized (VENTANAS_ABIERTAS) {

            for (Window w : new java.util.ArrayList<>(VENTANAS_ABIERTAS)) {

                // Se esconde la anterior, pero no se destruye: al
                // esconderla con setVisible(false) sigue viva en memoria
                // y se puede volver a mostrar al instante.
                if (w != null && w != this && w.isDisplayable()) {

                    w.setVisible(false);
                }
            }
        }
    }

    //------------------------ C O M P O N E N T E S ----------------
    public void inicializarComponentes() {

        inicializarPrincipal();

        inicializarEncabezado();

        inicializarIzquierda();

        inicializarCentral();

        inicializarInferior();
    }

    //------------------------ P R I N C I P A L ----------------
    public void inicializarPrincipal() {

        Pprincipal = new JPanel();

        Pprincipal.setLayout(
                new BorderLayout()
        );

        //---------------------- FONDO
        Pfondo
                = new PanelFondo(
                        "/utiles/imagenes/fondos/fondo_A.png"
                );

        Pfondo.setLayout(
                new BorderLayout()
        );

        add(Pprincipal);

        Pprincipal.add(
                Pfondo,
                BorderLayout.CENTER
        );
    }

    //------------------------ E N C A B E Z A D O ----------------
    public void inicializarEncabezado() {

        encabezado
                = new Encabezado();

        // El botón de la flecha (BotonRegresar) vive dentro del encabezado.
        // Antes se dibujaba pero no tenía ninguna acción, por eso al
        // pulsarlo no pasaba nada.
        //
        // Por defecto se conecta con la acción segura: volver al panel del
        // administrador. Cada pantalla que tenga un destino más específico
        // lo cambia con setAccionRegreso().
        encabezado.getBregresar().addActionListener(
                e -> ejecutarRegreso()
        );

        // En la pantalla principal del administrador no hay a dónde
        // regresar, así que el botón se oculta.
        if (this instanceof PantallaDashboard) {

            encabezado.getBregresar().setVisible(false);
        }

        Pfondo.add(
                encabezado,
                BorderLayout.NORTH
        );
    }

    //---------------------- R E G R E S A R ----------------
    // Acción que se ejecuta al pulsar la flecha. Cada pantalla la cambia
    // por la que le corresponde: por ejemplo, un CRUD de combos debe
    // volver al menú de combos, no al panel principal.
    private Runnable accionRegreso = null;

    /**
     * Define a qué pantalla se vuelve al pulsar la flecha de regresar.
     * Si no se llama a este método, se vuelve al panel del administrador.
     */
    protected void setAccionRegreso(Runnable accion) {

        this.accionRegreso = accion;
    }

    /**
     * Ejecuta la acción de regreso: usa la personalizada por la pantalla o,
     * si no tiene ninguna, vuelve al panel del administrador.
     */
    private void ejecutarRegreso() {

        if (accionRegreso != null) {

            accionRegreso.run();

        } else {

            abrir(PantallaDashboard.class, PantallaDashboard::new);
        }

        // Se esconde en vez de destruirse: así, si se vuelve a entrar a
        // esta pantalla, aparece al instante sin tener que armarla otra
        // vez.
        ocultar();
    }

    //------------------------ I Z Q U I E R D A ----------------
    public void inicializarIzquierda() {

        panelAdministrador
                = new PanelContenidoAdministrador();

        panelAdministrador.setAdministradorListener(
                opcion
                -> opcionSeleccionada(opcion)
        );

        Pprincipal.add(
                panelAdministrador,
                BorderLayout.WEST
        );
    }

    //------------------------ C E N T R A L ----------------
    public void inicializarCentral() {

        Pcentral
                = new PanelColor();

        Pcentral.setColorFondo(
                Colores.DURAZNO
        );

        Pcentral.setOpacidad(
                200
        );

        Pfondo.add(
                Pcentral,
                BorderLayout.CENTER
        );
    }

    //------------------------ I N F E R I O R ----------------
    public void inicializarInferior() {

        barraInferior
                = new BarraInferior(
                        Colores.CORINTO,
                        Colores.NARANJA
                );

        Pprincipal.add(
                barraInferior,
                BorderLayout.SOUTH
        );
    }

    //------------------------ O P C I Ó N ----------------
    /**
     * Cada opción del menú lateral abre su pantalla con abrir(), que
     * reutiliza la ventana si ya fue construida. Por eso no hace falta
     * llamar a dispose() ni a ocultar(): abrir() ya se encarga de
     * esconder la ventana anterior cuando la nueva esté visible.
     */
    protected void opcionSeleccionada(
            String opcion
    ) {

        switch (opcion) {

            case "Dashboard":
                abrir(PantallaDashboard.class, PantallaDashboard::new);
                break;

            case "Administración de Datos":
                abrir(AdministracionDatos.class, AdministracionDatos::new);
                break;

            // El botón "Pedidos" lleva a la pantalla principal del cajero,
            // que es donde se toman los pedidos. Esa pantalla no es de este
            // módulo, así que aquí sí se destruye la del administrador: si
            // solo se escondiera, el usuario podría volver a entrar al
            // panel con el mismo usuario sin haber vuelto a iniciar sesión.
            case "Pedidos":
                cerrarSesionAlIrAlCajero();
                break;

            case "Historial":
                abrir(PantallaHistorial.class, PantallaHistorial::new);
                break;

            case "Cerrar Sesión":
                cerrarSesion();
                break;

            default:
                break;
        }
    }

    /**
     * Cierra la sesión del administrador y abre el punto de venta del
     * cajero.
     *
     * Se pregunta antes porque el usuario podría tener un pedido a medio
     * hacer, y eso se perdería.
     */
    private void cerrarSesionAlIrAlCajero() {

        int respuesta = Dialogo.confirmar(
                this,
                "Ir a pedidos",
                "¿Deseas pasar al punto de venta de pedidos?"
        );

        if (respuesta != Dialogo.SI) {

            return;
        }

        // Se destruyen las ventanas del administrador, porque ya no
        // debe quedar ninguna abierta de ese usuario.
        cerrarTodoElModulo();

        modelo.Sesion.cerrar();

        // Se abre el punto de venta antes de terminar de cerrarlo todo.
        // Al revés se ve el escritorio hasta que la ventana nueva esté
        // lista, y eso se ve como un parpadeo.
        new PantallaPrincipal();

        dispose();
    }

    //------------------------ C E R R A R   S E S I Ó N ----------------
    private void cerrarSesion() {

        // Se usa Dialogo para que los botones salgan como "Sí" y "No",
        // en vez de "Yes" y "No" cuando la computadora está en inglés.
        int respuesta = Dialogo.confirmar(
                this,
                "Cerrar sesión",
                "¿Deseas cerrar sesión?"
        );

        if (respuesta == Dialogo.SI) {

            // Se limpia la sesión para que el login no recuerde que
            // había un administrador conectado.
            modelo.Sesion.cerrar();

            // Se muestra el login antes de cerrar las ventanas del
            // administrador: si se cerraran primero, quedaría un instante
            // sin ninguna ventana de MOOVA en pantalla.
            new PantallaLogin().setVisible(true);

            cerrarTodoElModulo();

            dispose();
        }
    }

    /**
     * Destruye todas las ventanas del módulo administrador y vacía el
     * registro de ventanas reutilizables.
     *
     * Se usa al cerrar sesión o al salir del panel, no al cambiar de
     * pantalla. Cambiar de pantalla solo las esconde (ocultar), para
     * poder volver a mostrarlas sin reconstruirlas.
     */
    private static void cerrarTodoElModulo() {

        synchronized (VENTANAS_ABIERTAS) {

            for (Window w : new java.util.ArrayList<>(VENTANAS_ABIERTAS)) {

                if (w != null) {

                    // dispose() sí destruye la ventana. Como además se
                    // borra del registro de ventanas reutilizables, la
                    // próxima vez que se abra esa pantalla se
                    // construirá desde cero, que es lo correcto: ya no
                    // debe quedar nada de la sesión anterior.
                    if (w instanceof VentanaAdministrador va) {
                        va.dispose();
                    } else {
                        w.dispose();
                    }
                }
            }

            VENTANAS_ABIERTAS.clear();
            VENTANAS.clear();
        }
    }

    //---------------------- G E T T E R S
    public Encabezado getEncabezado() {

        return encabezado;
    }

    public PanelContenidoAdministrador getPanelAdministrador() {

        return panelAdministrador;
    }

    //---------------------- C E R R A R
    /**
     * Al destruir la ventana se quita de los dos registros.
     *
     * Sin esto, el registro de ventanas abiertas iría acumulando objetos
     * ya destruidos, y el registro de ventanas reutilizables apuntaría a
     * una ventana que ya no existe: la siguiente vez que se pidiera esa
     * pantalla, abrir() la mostraría sin haberla construido.
     *
     * También se detienen los temporizadores del encabezado (el reloj) y
     * del dashboard (la actualización automática de las estadísticas),
     * porque si no seguirían corriendo en segundo plano aunque la
     * ventana ya no exista, gastando CPU sin que nadie los mire.
     */
    @Override
    public void dispose() {

        VENTANAS_ABIERTAS.remove(this);

        // Solo se borra del registro de reutilización si la ventana que
        // está guardada es esta misma. Puede no serlo si el usuario
        // cambió de sesión mientras esta ventana seguía escondida.
        if (VENTANAS.get(getClass()) == this) {
            VENTANAS.remove(getClass());
        }

        if (encabezado != null) {
            encabezado.detenerReloj();
        }

        super.dispose();
    }

    public BarraInferior getBarraInferior() {

        return barraInferior;
    }

    public PanelColor getPcentral() {

        return Pcentral;
    }
}