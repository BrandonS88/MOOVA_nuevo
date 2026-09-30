//---------------------- U S U A R I O    D A O ----------------------
package dao;

import modelo.Usuario;

import java.sql.*;

public class UsuarioDAO extends DAO<Usuario> {

    //------------------------ S Q L   B U S C A R   P O R   I D ----------------
    @Override
    protected String getSqlBuscarPorId() {

        return "SELECT id_Usuario, nombre_usuario, nombre, apellido, "
                + "contrasena, rol, correo_electronico, estado "
                + "FROM Usuario "
                + "WHERE id_Usuario = ?";
    }

    //------------------------ S Q L   L I S T A R   T O D O S ----------------
    @Override
    protected String getSqlListarTodos() {

        return "SELECT id_Usuario, nombre_usuario, nombre, apellido, "
                + "contrasena, rol, correo_electronico, estado "
                + "FROM Usuario";
    }

    //------------------------ M A P E A R ----------------
    @Override
    protected Usuario mapear(ResultSet rs) throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setId_Usuario(
                rs.getInt("id_Usuario")
        );

        usuario.setNombre_usuario(
                rs.getString("nombre_usuario")
        );

        usuario.setNombre(
                rs.getString("nombre")
        );

        usuario.setApellido(
                rs.getString("apellido")
        );

        usuario.setContrasena(
                rs.getString("contrasena")
        );

        usuario.setRol(
                rs.getString("rol")
        );

        usuario.setCorreo_electronico(
                rs.getString("correo_electronico")
        );

        // La columna "estado" de la tabla Usuario es de tipo BOOLEAN en MySQL
        // (guarda 1/0 o true/false, NO el texto "Activa"/"Inactiva").
        // Por eso hay que leerla con getBoolean(), porque si se usara
        // getString() la comparación con "Activa" nunca coincidiría y
        // el usuario siempre se devolvería con estado = false.
        usuario.setEstado(
                rs.getBoolean("estado")
        );

        return usuario;
    }

    //------------------------ R E S U L T A D O   C R E A R ----------------
    // Distingue por qué falló crear() en vez de devolver solo un boolean,
    // porque nombre_usuario y correo_electronico son UNIQUE.
    public enum ResultadoCrear {
        OK,
        USUARIO_DUPLICADO,
        CORREO_DUPLICADO,
        ERROR
    }

    //------------------------ C R E A R ----------------
    // Cumple el contrato de DAO<T>, pero pierde el detalle de por qué
    // falló. Si necesitas ese detalle, usa crearUsuario(Usuario) en su lugar.
    @Override
    public boolean crear(Usuario usuario) {

        return crearUsuario(usuario) == ResultadoCrear.OK;
    }

    //------------------------ C R E A R   U S U A R I O   ( D E T A L L A D O ) ----------------
    // No usa ejecutarActualizacion() del DAO genérico porque ese método
    // atrapa SQLException de forma genérica; aquí se necesita distinguir
    // SQLIntegrityConstraintViolationException para saber si chocó
    // nombre_usuario o correo_electronico.
    public ResultadoCrear crearUsuario(Usuario usuario) {

        String sql = "INSERT INTO Usuario "
                + "(nombre_usuario, nombre, apellido, contrasena, rol, "
                + "correo_electronico, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre_usuario());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getContrasena());
            ps.setString(5, usuario.getRol());
            ps.setString(6, usuario.getCorreo_electronico());
            // Se inserta el booleano directamente porque la columna "estado"
            // es BOOLEAN. Si se mandara el texto "Activa"/"Inactiva",
            // MySQL lo convertiría a 0 (inactivo) en ambos casos.
            ps.setBoolean(7, usuario.isEstado());

            return ps.executeUpdate() > 0
                    ? ResultadoCrear.OK
                    : ResultadoCrear.ERROR;

        } catch (SQLIntegrityConstraintViolationException e) {

            // MySQL (Connector/J) incluye el nombre del constraint en el
            // mensaje, algo como:
            // "Duplicate entry 'x' for key 'UQ_Usuario_Correo'"
            // Si cambias el nombre de las constraints en la tabla, hay que
            // actualizar estos textos también.
            String mensaje = e.getMessage();

            if (mensaje != null && mensaje.contains("UQ_Usuario_Correo")) {
                return ResultadoCrear.CORREO_DUPLICADO;
            }

            if (mensaje != null && mensaje.contains("UQ_Usuario_Nombre")) {
                return ResultadoCrear.USUARIO_DUPLICADO;
            }

            System.out.println("Error de integridad al crear usuario: "
                    + mensaje);

            return ResultadoCrear.ERROR;

        } catch (SQLException e) {
            System.out.println("Error al crear usuario: "
                    + e.getMessage());

            return ResultadoCrear.ERROR;
        }
    }

    //------------------------ A C T U A L I Z A R ----------------
    @Override
    public boolean actualizar(Usuario usuario) {

        String sql = "UPDATE Usuario "
                + "SET nombre_usuario = ?, nombre = ?, apellido = ?, "
                + "contrasena = ?, rol = ?, correo_electronico = ?, estado = ? "
                + "WHERE id_Usuario = ?";

        return ejecutarActualizacion(
                sql,
                usuario.getNombre_usuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getContrasena(),
                usuario.getRol(),
                usuario.getCorreo_electronico(),
                usuario.isEstado(),
                usuario.getId_Usuario()
        );
    }

    //------------------------ C A M B I A R   E S T A D O ----------------
    // Activa o desactiva un usuario. Se manda el booleano directo porque
    // la columna "estado" de la tabla Usuario es de tipo BOOLEAN.
    public boolean cambiarEstado(int idUsuario, boolean estado) {
        return ejecutarActualizacion(
                "UPDATE Usuario SET estado=? WHERE id_Usuario=?",
                estado,
                idUsuario
        );
    }

    //------------------------ B U S C A R   N O M B R E ----------------
    public Usuario buscarNombre(String nombreUsuario) {

        String sql = "SELECT id_Usuario, nombre_usuario, nombre, apellido, "
                + "contrasena, rol, correo_electronico, estado "
                + "FROM Usuario "
                + "WHERE nombre_usuario = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar usuario: "
                    + e.getMessage());
        }

        return null;
    }
}