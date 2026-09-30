//---------------------- S E S I Ó N ----------------------
package modelo;

/**
 * Guarda el usuario que inició sesión en la aplicación.
 *
 * ¿Por qué existe esta clase?
 * Antes, cada pantalla se abría "por su cuenta" sin saber quién la abrió.
 * Eso traía un problema: el botón "Regresar" de la pantalla del cajero
 * debía mostrarse únicamente si quien está usando el sistema es
 * administrador, y no había forma de saber el rol en ese momento.
 *
 * Esta clase resuelve eso guardando el usuario autenticado en un único
 * lugar. Se llena una sola vez, en PantallaLogin, justo después de que
 * LogicaLogin confirme las credenciales. Cualquier pantalla puede luego
 * consultarlo con los métodos estáticos.
 *
 * No se guarda aquí la contraseña ni ningún dato sensible.
 */
public final class Sesion {

    // Usuario conectado. Es null cuando nadie ha iniciado sesión.
    private static Usuario usuario;

    // La clase es únicamente de consulta, no se instancia.
    private Sesion() {}

    /**
     * Registra el usuario que acaba de iniciar sesión.
     * Se llama desde PantallaLogin después de validar las credenciales.
     */
    public static void iniciar(Usuario u) {
        usuario = u;
    }

    /**
     * Cierra la sesión del usuario actual.
     * Se llama al pulsar "Cerrar Sesión" o al salir del programa.
     */
    public static void cerrar() {
        usuario = null;
    }

    /**
     * Devuelve el usuario conectado, o null si no hay nadie conectado.
     */
    public static Usuario getUsuario() {
        return usuario;
    }

    /**
     * Indica si hay un usuario conectado.
     */
    public static boolean haySesion() {
        return usuario != null;
    }

    /**
     * Devuelve el id del usuario conectado, o 0 si no hay sesión.
     * Se usa para registrar ventas, incidentes o movimientos de inventario
     * atribuidos a la persona que los está haciendo.
     */
    public static int getId() {
        return usuario == null ? 0 : usuario.getId_Usuario();
    }

    /**
     * Indica si el usuario conectado es administrador.
     * La comparación no distingue mayúsculas porque el rol puede venir
     * como "Administrador" o "administrador" según cómo se haya guardado.
     */
    public static boolean esAdministrador() {
        return usuario != null
                && "Administrador".equalsIgnoreCase(usuario.getRol());
    }

    /**
     * Indica si el usuario conectado es cajero.
     */
    public static boolean esCajero() {
        return usuario != null
                && "Cajero".equalsIgnoreCase(usuario.getRol());
    }

    /**
     * Devuelve el nombre del usuario conectado, o "Invitado" si no hay sesión.
     * Se usa para mostrar mensajes personalizados en las pantallas.
     */
    public static String getNombre() {
        return usuario == null ? "Invitado" : usuario.getNombre();
    }
}
