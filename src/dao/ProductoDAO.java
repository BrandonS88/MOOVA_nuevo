//---------------------- P R O D U C T O    D A O ----------------------
package dao;

import modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO extends DAO<Producto> {

    //------------------------ S Q L   B U S C A R   P O R   I D ----------------
    @Override
    protected String getSqlBuscarPorId() {

        return "SELECT p.id_Producto, s.id_Categoria, "
                + "p.id_Subcategoria, p.nombre, p.descripcion, "
                + "p.precio, p.imagen, p.estado "
                + "FROM Producto p "
                + "INNER JOIN Subcategoria s "
                + "ON p.id_Subcategoria = s.id_Subcategoria "
                + "WHERE p.id_Producto = ?";
    }

    //------------------------ S Q L   L I S T A R   T O D O S ----------------
    @Override
    protected String getSqlListarTodos() {

        return "SELECT p.id_Producto, s.id_Categoria, "
                + "p.id_Subcategoria, p.nombre, p.descripcion, "
                + "p.precio, p.imagen, p.estado "
                + "FROM Producto p "
                + "INNER JOIN Subcategoria s "
                + "ON p.id_Subcategoria = s.id_Subcategoria";
    }

    //------------------------ M A P E A R ----------------
    @Override
    protected Producto mapear(ResultSet rs) throws SQLException {

        return new Producto(
                rs.getInt("id_Producto"),
                rs.getInt("id_Categoria"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getDouble("precio"),
                rs.getString("imagen"),
                rs.getBoolean("estado"),
                rs.getInt("id_Subcategoria")
        );
    }

    //------------------------ C R E A R ----------------
    @Override
    public boolean crear(Producto producto) {

        String sql = "INSERT INTO Producto "
                + "(id_Subcategoria, nombre, descripcion, precio, imagen, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        return ejecutarActualizacion(
                sql,
                producto.getId_Subcategoria(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getImagen(),
                producto.isEstado()
        );
    }

    //------------------------ A C T U A L I Z A R ----------------
    @Override
    public boolean actualizar(Producto producto) {

        String sql = "UPDATE Producto "
                + "SET id_Subcategoria = ?, nombre = ?, descripcion = ?, "
                + "precio = ?, imagen = ?, estado = ? "
                + "WHERE id_Producto = ?";

        return ejecutarActualizacion(
                sql,
                producto.getId_Subcategoria(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getImagen(),
                producto.isEstado(),
                producto.getId_Producto()
        );
    }

    //------------------------ C A M B I A R   E S T A D O ----------------
    public boolean cambiarEstado(int idProducto, boolean estado) {

        return cambiarEstado(
                "Producto",
                "id_Producto",
                idProducto,
                estado
        );
    }

    //------------------------ L I S T A R   A C T I V O S ----------------
    public List<Producto> listarActivos() {

        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT p.id_Producto, s.id_Categoria, "
                + "p.id_Subcategoria, p.nombre, p.descripcion, "
                + "p.precio, p.imagen, p.estado "
                + "FROM Producto p "
                + "INNER JOIN Subcategoria s "
                + "ON p.id_Subcategoria = s.id_Subcategoria "
                + "WHERE p.estado = TRUE";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar productos activos: "
                    + e.getMessage());
        }

        return productos;
    }

    //------------------------ P O R   C A T E G O R I A ----------------
    public List<Producto> listarPorCategoria(int idCategoria) {

        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT p.id_Producto, s.id_Categoria, "
                + "p.id_Subcategoria, p.nombre, p.descripcion, "
                + "p.precio, p.imagen, p.estado "
                + "FROM Producto p "
                + "INNER JOIN Subcategoria s "
                + "ON p.id_Subcategoria = s.id_Subcategoria "
                + "WHERE s.id_Categoria = ? "
                + "AND p.estado = TRUE";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    productos.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar productos por categoría: "
                    + e.getMessage());
        }

        return productos;
    }

    //------------------------ P O R   S U B C A T E G O R I A ----------------
    public List<Producto> listarPorSubcategoria(int idSubcategoria) {

        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT p.id_Producto, s.id_Categoria, "
                + "p.id_Subcategoria, p.nombre, p.descripcion, "
                + "p.precio, p.imagen, p.estado "
                + "FROM Producto p "
                + "INNER JOIN Subcategoria s "
                + "ON p.id_Subcategoria = s.id_Subcategoria "
                + "WHERE p.id_Subcategoria = ? "
                + "AND p.estado = TRUE "
                + "ORDER BY p.id_Producto";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSubcategoria);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    productos.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar productos por subcategoría: "
                    + e.getMessage());
        }

        return productos;
    }
}