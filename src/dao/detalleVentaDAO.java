//---------------------- D E T A L L E   V E N T A    D A O ----------------------
package dao;

import modelo.Detalle_Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class detalleVentaDAO extends DAO<Detalle_Venta> {

    //------------------------ S Q L   B U S C A R   P O R   I D ----------------
    @Override
    protected String getSqlBuscarPorId() {

        return "SELECT id_DetalleVenta, cantidad, precio_unitario, subtotal, "
                + "impuesto, descuento, monto_descuento, id_Venta, "
                + "id_Producto, id_Combo, id_Descuento "
                + "FROM Detalle_Venta "
                + "WHERE id_DetalleVenta = ?";
    }

    //------------------------ S Q L   L I S T A R   T O D O S ----------------
    @Override
    protected String getSqlListarTodos() {

        return "SELECT id_DetalleVenta, cantidad, precio_unitario, subtotal, "
                + "impuesto, descuento, monto_descuento, id_Venta, "
                + "id_Producto, id_Combo, id_Descuento "
                + "FROM Detalle_Venta";
    }

    //------------------------ M A P E A R ----------------
    @Override
    protected Detalle_Venta mapear(ResultSet rs) throws SQLException {

        Detalle_Venta detalle = new Detalle_Venta();

        detalle.setId_DetalleVenta(
                rs.getInt("id_DetalleVenta")
        );

        detalle.setCantidad(
                rs.getInt("cantidad")
        );

        detalle.setPrecio_unitario(
                rs.getDouble("precio_unitario")
        );

        detalle.setSubtotal(
                rs.getDouble("subtotal")
        );

        detalle.setImpuesto(
                rs.getDouble("impuesto")
        );

        detalle.setDescuento(
                rs.getDouble("descuento")
        );

        detalle.setMonto_descuento(
                rs.getDouble("monto_descuento")
        );

        detalle.setId_Venta(
                rs.getInt("id_Venta")
        );

        // id_Producto, id_Combo e id_Descuento son nullable en la tabla.
        detalle.setId_Producto(
                rs.getObject("id_Producto", Integer.class)
        );

        detalle.setId_Combo(
                rs.getObject("id_Combo", Integer.class)
        );

        detalle.setId_Descuento(
                rs.getObject("id_Descuento", Integer.class)
        );

        return detalle;
    }

    //------------------------ C R E A R ----------------
    // Nota: la tabla tiene CK_DetalleVenta_ProductoOCombo (id_Producto IS
    // NOT NULL OR id_Combo IS NOT NULL). Si el objeto trae ambos en null,
    // el INSERT fallará en la base de datos.
    @Override
    public boolean crear(Detalle_Venta detalle) {

        String sql = "INSERT INTO Detalle_Venta "
                + "(cantidad, precio_unitario, subtotal, impuesto, "
                + "descuento, monto_descuento, id_Venta, id_Producto, "
                + "id_Combo, id_Descuento) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return ejecutarActualizacion(
                sql,
                detalle.getCantidad(),
                detalle.getPrecio_unitario(),
                detalle.getSubtotal(),
                detalle.getImpuesto(),
                detalle.getDescuento(),
                detalle.getMonto_descuento(),
                detalle.getId_Venta(),
                detalle.getId_Producto(),
                detalle.getId_Combo(),
                detalle.getId_Descuento()
        );
    }

    //------------------------ A C T U A L I Z A R ----------------
    // id_Venta no se actualiza: un detalle no debería moverse de venta.
    @Override
    public boolean actualizar(Detalle_Venta detalle) {

        String sql = "UPDATE Detalle_Venta "
                + "SET cantidad = ?, precio_unitario = ?, subtotal = ?, "
                + "impuesto = ?, descuento = ?, monto_descuento = ?, "
                + "id_Producto = ?, id_Combo = ?, id_Descuento = ? "
                + "WHERE id_DetalleVenta = ?";

        return ejecutarActualizacion(
                sql,
                detalle.getCantidad(),
                detalle.getPrecio_unitario(),
                detalle.getSubtotal(),
                detalle.getImpuesto(),
                detalle.getDescuento(),
                detalle.getMonto_descuento(),
                detalle.getId_Producto(),
                detalle.getId_Combo(),
                detalle.getId_Descuento(),
                detalle.getId_DetalleVenta()
        );
    }

    // Nota: Detalle_Venta no tiene columna "estado" (igual que se señala
    // en el comentario del DAO genérico), así que no se expone
    // cambiarEstado aquí.

    //------------------------ P O R   V E N T A ----------------
    // El método clave para armar la factura de DetalleVenta.java: todas
    // las líneas que pertenecen a una venta.
    public List<Detalle_Venta> listarPorVenta(int idVenta) {

        List<Detalle_Venta> detalles = new ArrayList<>();

        String sql = "SELECT id_DetalleVenta, cantidad, precio_unitario, "
                + "subtotal, impuesto, descuento, monto_descuento, "
                + "id_Venta, id_Producto, id_Combo, id_Descuento "
                + "FROM Detalle_Venta "
                + "WHERE id_Venta = ? "
                + "ORDER BY id_DetalleVenta";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVenta);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    detalles.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar detalles de la venta: "
                    + e.getMessage());
        }

        return detalles;
    }
}