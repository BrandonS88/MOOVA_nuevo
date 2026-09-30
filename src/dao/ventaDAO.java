//---------------------- V E N T A    D A O ----------------------
package dao;

import modelo.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ventaDAO extends DAO<Venta> {

    //------------------------ S Q L   B U S C A R   P O R   I D ----------------
    @Override
    protected String getSqlBuscarPorId() {

        return "SELECT id_Venta, fecha_realizacion, total, nit, "
                + "metodo_pago, estado, id_Usuario "
                + "FROM Venta "
                + "WHERE id_Venta = ?";
    }

    //------------------------ S Q L   L I S T A R   T O D O S ----------------
    @Override
    protected String getSqlListarTodos() {

        return "SELECT id_Venta, fecha_realizacion, total, nit, "
                + "metodo_pago, estado, id_Usuario "
                + "FROM Venta";
    }

    //------------------------ M A P E A R ----------------
    @Override
    protected Venta mapear(ResultSet rs) throws SQLException {

        Venta venta = new Venta();

        venta.setId_Venta(
                rs.getInt("id_Venta")
        );

        Timestamp fecha = rs.getTimestamp("fecha_realizacion");

        venta.setFecha_realizacion(
                fecha != null ? fecha.toLocalDateTime() : null
        );

        venta.setTotal(
                rs.getDouble("total")
        );

        venta.setNit(
                rs.getString("nit")
        );

        venta.setMetodo_pago(
                rs.getString("metodo_pago")
        );

        venta.setEstado(
                rs.getString("estado")
        );

        venta.setId_Usuario(
                rs.getInt("id_Usuario")
        );

        return venta;
    }

    //------------------------ C R E A R ----------------
    // fecha_realizacion se deja al DEFAULT CURRENT_TIMESTAMP de la tabla.
    // nit se deja al DEFAULT 'C/F' si el objeto trae null.
    @Override
    public boolean crear(Venta venta) {

        String sql = "INSERT INTO Venta "
                + "(total, nit, metodo_pago, estado, id_Usuario) "
                + "VALUES (?, ?, ?, ?, ?)";

        return ejecutarActualizacion(
                sql,
                venta.getTotal(),
                venta.getNit(),
                venta.getMetodo_pago(),
                venta.getEstado(),
                venta.getId_Usuario()
        );
    }

    //------------------------ A C T U A L I Z A R ----------------
    // fecha_realizacion no se actualiza: representa el momento en que
    // se registró la venta.
    @Override
    public boolean actualizar(Venta venta) {

        String sql = "UPDATE Venta "
                + "SET total = ?, nit = ?, metodo_pago = ?, estado = ?, "
                + "id_Usuario = ? "
                + "WHERE id_Venta = ?";

        return ejecutarActualizacion(
                sql,
                venta.getTotal(),
                venta.getNit(),
                venta.getMetodo_pago(),
                venta.getEstado(),
                venta.getId_Usuario(),
                venta.getId_Venta()
        );
    }

    //------------------------ C A M B I A R   E S T A D O ----------------
    // "estado" es ENUM('Sin Pagar','Cancelado','Pagado'), no boolean, así
    // que no se usa el cambiarEstado del DAO genérico.
    public boolean cambiarEstado(int idVenta, String estado) {

        String sql = "UPDATE Venta "
                + "SET estado = ? "
                + "WHERE id_Venta = ?";

        return ejecutarActualizacion(
                sql,
                estado,
                idVenta
        );
    }

    //------------------------ P O R   U S U A R I O ----------------
    public List<Venta> listarPorUsuario(int idUsuario) {

        List<Venta> ventas = new ArrayList<>();

        String sql = "SELECT id_Venta, fecha_realizacion, total, nit, "
                + "metodo_pago, estado, id_Usuario "
                + "FROM Venta "
                + "WHERE id_Usuario = ? "
                + "ORDER BY fecha_realizacion DESC";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    ventas.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar ventas por usuario: "
                    + e.getMessage());
        }

        return ventas;
    }

    //------------------------ P O R   E S T A D O ----------------
    public List<Venta> listarPorEstado(String estado) {

        List<Venta> ventas = new ArrayList<>();

        String sql = "SELECT id_Venta, fecha_realizacion, total, nit, "
                + "metodo_pago, estado, id_Usuario "
                + "FROM Venta "
                + "WHERE estado = ? "
                + "ORDER BY fecha_realizacion DESC";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    ventas.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar ventas por estado: "
                    + e.getMessage());
        }

        return ventas;
    }
}