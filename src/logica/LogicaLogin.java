//--------------------------------- L O G I C A   L O G I N  ----------------------------------
package logica;

import dao.UsuarioDAO;
import modelo.Usuario;

public class LogicaLogin {

    //------------------------  A T R I B U T O S ---------------
    private UsuarioDAO usuarioDAO;

    //-------------------- C O N S T R U C T O R -----------------
    public LogicaLogin() {
        usuarioDAO = new UsuarioDAO();
    }

    //--------------- L O G I N -----------------
    public Usuario iniciarSesion(
            String nombreUsuario,
            String contrasena) {

        // Buscar usuario en la base de datos
        Usuario usuario = usuarioDAO.buscarNombre(nombreUsuario);

        // Verificar si existe
        if (usuario == null) {
            return null;
        }

        // Verificar contraseña
        if (!usuario.getContrasena().equals(contrasena)) {
            return null;
        }

        // Verificar estado
        if (!usuario.isEstado()) {
            return null;
        }

        // Login correcto
        return usuario;
    }
}
