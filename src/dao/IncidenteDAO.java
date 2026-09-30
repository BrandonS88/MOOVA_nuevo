//---------------------- I N C I D E N T E    D A O ----------------------
package dao;

import modelo.Incidente;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IncidenteDAO extends DAO<Incidente> {

    //------------------------ S Q L   B U S C A R   P O R   I D ----------------
    @Override
    protected String getSqlBuscarPorId() {

        return "SELECT id_Incidente, titulo, descripcion, fecha_reporte, "
                + "fecha_resolucion, solucion, tipo, prioridad, estado, "
                + "id_Usuario, id_Ingrediente, id_Producto "
                + "FROM Incidente "
                + "WHERE id_Incidente = ?";
    }

    //------------------------ S Q L   L I S T A R   T O D O S ----------------
    @Override
    protected String getSqlListarTodos() {

        return "SELECT id_Incidente, titulo, descripcion, fecha_reporte, "
                + "fecha_resolucion, solucion, tipo, prioridad, estado, "
                + "id_Usuario, id_Ingrediente, id_Producto "
                + "FROM Incidente";
    }

    //------------------------ M A P E A R ----------------
    @Override
    protected Incidente mapear(ResultSet rs) throws SQLException {

        Incidente incidente = new Incidente();

        incidente.setId_Incidente(
                rs.getInt("id_Incidente")
        );

        incidente.setTitulo(
                rs.getString("titulo")
        );

        incidente.setDescripcion(
                rs.getString("descripcion")
        );

        Timestamp reporte = rs.getTimestamp("fecha_reporte");

        incidente.setFecha_reporte(
                reporte != null ? reporte.toLocalDateTime() : null
        );

        Timestamp resolucion = rs.getTimestamp("fecha_resolucion");

        incidente.setFecha_resolucion(
                resolucion != null ? resolucion.toLocalDateTime() : null
        );

        incidente.setSolucion(
                rs.getString("solucion")
        );

        incidente.setTipo(
                rs.getString("tipo")
        );

        incidente.setPrioridad(
                rs.getString("prioridad")
        );

        incidente.setEstado(
                rs.getString("estado")
        );

        incidente.setId_Usuario(
                rs.getInt("id_Usuario")
        );

        // id_Ingrediente e id_Producto son nullable en la tabla, por eso
        // se leen como Integer. Antes estos valores se escribían
        // equivocadamente sobre setId_Incidente(), lo que destruía el id
        // del incidente al momento de leerlo de la base de datos.
        incidente.setId_Ingrediente(
                rs.getObject("id_Ingrediente", Integer.class)
        );

        incidente.setId_Producto(
                rs.getObject("id_Producto", Integer.class)
        );

        return incidente;
    }

    //------------------------ C R E A R ----------------
    // fecha_reporte se deja al DEFAULT CURRENT_TIMESTAMP de la tabla, así
    // que no se incluye en el INSERT.
    @Override
    public boolean crear(Incidente incidente) {

        String sql = "INSERT INTO Incidente "
                + "(titulo, descripcion, tipo, prioridad, estado, "
                + "id_Usuario, id_Ingrediente, id_Producto) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return ejecutarActualizacion(
                sql,
                incidente.getTitulo(),
                incidente.getDescripcion(),
                incidente.getTipo(),
                incidente.getPrioridad(),
                incidente.getEstado(),
                incidente.getId_Usuario(),
                // Antes se enviaba getId_Incidente() en la columna
                // id_Ingrediente, lo que guardaba el id equivocado.
                incidente.getId_Ingrediente(),
                incidente.getId_Producto()
        );
    }

    //------------------------ A C T U A L I Z A R ----------------
    @Override
    public boolean actualizar(Incidente incidente) {

        String sql = "UPDATE Incidente "
                + "SET titulo = ?, descripcion = ?, fecha_resolucion = ?, "
                + "solucion = ?, tipo = ?, prioridad = ?, estado = ?, "
                + "id_Ingrediente = ?, id_Producto = ? "
                + "WHERE id_Incidente = ?";

        Timestamp resolucion = incidente.getFecha_resolucion() != null
                ? Timestamp.valueOf(incidente.getFecha_resolucion())
                : null;

        return ejecutarActualizacion(
                sql,
                incidente.getTitulo(),
                incidente.getDescripcion(),
                resolucion,
                incidente.getSolucion(),
                incidente.getTipo(),
                incidente.getPrioridad(),
                incidente.getEstado(),
                // Antes se enviaba getId_Incidente() en la columna
                // id_Ingrediente. El id del incidente solo va en el WHERE.
                incidente.getId_Ingrediente(),
                incidente.getId_Producto(),
                incidente.getId_Incidente()
        );
    }

    //------------------------ C A M B I A R   E S T A D O ----------------
    // "estado" es ENUM('Pendiente','En proceso','Resuelta','Cancelada'),
    // no boolean, así que no se usa el cambiarEstado del DAO genérico.
    public boolean cambiarEstado(int idIncidente, String estado) {

        String sql = "UPDATE Incidente "
                + "SET estado = ? "
                + "WHERE id_Incidente = ?";

        return ejecutarActualizacion(
                sql,
                estado,
                idIncidente
        );
    }

    //------------------------ M A R C A R   R E S U E L T A ----------------
    // Conveniencia: cierra el incidente con fecha de resolución = ahora.
    public boolean marcarResuelta(int idIncidente, String solucion) {

        String sql = "UPDATE Incidente "
                + "SET estado = 'Resuelta', solucion = ?, "
                + "fecha_resolucion = ? "
                + "WHERE id_Incidente = ?";

        return ejecutarActualizacion(
                sql,
                solucion,
                Timestamp.valueOf(LocalDateTime.now()),
                idIncidente
        );
    }

    //------------------------ P O R   U S U A R I O ----------------
    public List<Incidente> listarPorUsuario(int idUsuario) {

        List<Incidente> incidentes = new ArrayList<>();

        String sql = "SELECT id_Incidente, titulo, descripcion, fecha_reporte, "
                + "fecha_resolucion, solucion, tipo, prioridad, estado, "
                + "id_Usuario, id_Ingrediente, id_Producto "
                + "FROM Incidente "
                + "WHERE id_Usuario = ? "
                + "ORDER BY fecha_reporte DESC";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    incidentes.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar incidentes por usuario: "
                    + e.getMessage());
        }

        return incidentes;
    }

    //------------------------ P O R   E S T A D O ----------------
    public List<Incidente> listarPorEstado(String estado) {

        List<Incidente> incidentes = new ArrayList<>();

        String sql = "SELECT id_Incidente, titulo, descripcion, fecha_reporte, "
                + "fecha_resolucion, solucion, tipo, prioridad, estado, "
                + "id_Usuario, id_Ingrediente, id_Producto "
                + "FROM Incidente "
                + "WHERE estado = ? "
                + "ORDER BY fecha_reporte DESC";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    incidentes.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar incidentes por estado: "
                    + e.getMessage());
        }

        return incidentes;
    }
}