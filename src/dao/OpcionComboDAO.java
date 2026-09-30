//---------------------- O P C I O N   C O M B O    D A O ----------------------
package dao;

import modelo.Opcion_Combo;
import modelo.Producto;
import modelo.Subcategoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OpcionComboDAO extends DAO<Opcion_Combo> {

    // ------------------------ S Q L B U S C A R P O R I D ----------------
    @Override
    protected String getSqlBuscarPorId() {
        return "SELECT id_OpcionCombo, precio_extra, "
                + "id_GrupoCombo, id_Producto "
                + "FROM Opcion_Combo "
                + "WHERE id_OpcionCombo = ?";
    }

    // ------------------------ S Q L L I S T A R T O D O S ----------------
    @Override
    protected String getSqlListarTodos() {
        return "SELECT id_OpcionCombo, precio_extra, "
                + "id_GrupoCombo, id_Producto "
                + "FROM Opcion_Combo";
    }

    // ------------------------ M A P E A R ----------------
    @Override
    protected Opcion_Combo mapear(ResultSet rs) throws SQLException {
        return mapearOpcionCombo(rs);
    }

    // ------------------------ C R E A R ----------------
    @Override
    public boolean crear(Opcion_Combo opcion) {
        String sql = "INSERT INTO Opcion_Combo "
                + "(precio_extra, id_GrupoCombo, id_Producto) "
                + "VALUES (?, ?, ?)";

        return ejecutarActualizacion(
                sql,
                opcion.getPrecio_extra(),
                opcion.getId_GrupoCombo(),
                opcion.getId_Producto());
    }

    // ------------------------ A C T U A L I Z A R ----------------
    @Override
    public boolean actualizar(Opcion_Combo opcion) {
        String sql = "UPDATE Opcion_Combo "
                + "SET precio_extra = ?, id_GrupoCombo = ?, id_Producto = ? "
                + "WHERE id_OpcionCombo = ?";

        return ejecutarActualizacion(
                sql,
                opcion.getPrecio_extra(),
                opcion.getId_GrupoCombo(),
                opcion.getId_Producto(),
                opcion.getId_OpcionCombo());
    }

    // ------------------------ P O R G R U P O ----------------
    public List<Opcion_Combo> listarPorGrupo(int idGrupoCombo) {
        List<Opcion_Combo> opciones = new ArrayList<>();

        String sql = "SELECT id_OpcionCombo, precio_extra, "
                + "id_GrupoCombo, id_Producto "
                + "FROM Opcion_Combo "
                + "WHERE id_GrupoCombo = ? "
                + "ORDER BY id_OpcionCombo";

        try (Connection con = conexion.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idGrupoCombo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    opciones.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar opciones del grupo: " + e.getMessage());
        }

        return opciones;
    }

    // ------------------------ P O R P R O D U C T O ----------------
    public List<Opcion_Combo> listarPorProducto(int idProducto) {
        List<Opcion_Combo> opciones = new ArrayList<>();

        String sql = "SELECT id_OpcionCombo, precio_extra, "
                + "id_GrupoCombo, id_Producto "
                + "FROM Opcion_Combo "
                + "WHERE id_Producto = ?";

        try (Connection con = conexion.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    opciones.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar opciones por producto: " + e.getMessage());
        }

        return opciones;
    }

    // ------------------------ S U B C A T E G O R I A S P O R G R U P O ----------------
    public List<Subcategoria> listarSubcategoriasPorGrupo(int idGrupoCombo) {
        List<Subcategoria> subcategorias = new ArrayList<>();

        String sql = "SELECT DISTINCT "
                + "s.id_Subcategoria, "
                + "s.id_Categoria, "
                + "s.nombre, "
                + "s.descripcion, "
                + "s.estado "
                + "FROM Opcion_Combo oc "
                + "INNER JOIN Producto p ON oc.id_Producto = p.id_Producto "
                + "INNER JOIN Subcategoria s ON p.id_Subcategoria = s.id_Subcategoria "
                + "WHERE oc.id_GrupoCombo = ? "
                + "AND p.estado = TRUE "
                + "AND s.estado = TRUE "
                + "ORDER BY s.id_Subcategoria";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idGrupoCombo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subcategoria subcategoria = new Subcategoria();
                    subcategoria.setId_Subcategoria(rs.getInt("id_Subcategoria"));
                    subcategoria.setId_Categoria(rs.getInt("id_Categoria"));
                    subcategoria.setNombre(rs.getString("nombre"));
                    subcategoria.setDescripcion(rs.getString("descripcion"));
                    subcategoria.setEstado(rs.getBoolean("estado"));

                    subcategorias.add(subcategoria);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar subcategorías por grupo: " + e.getMessage());
        }

        return subcategorias;
    }

    // ------------------------ P R O D U C T O S P O R G R U P O Y S U B C A T E G O R I A ----------------
    public List<Producto> listarProductosPorGrupoYSubcategoria(int idGrupoCombo, int idSubcategoria) {
        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT p.id_Producto, "
                + "s.id_Categoria, "
                + "p.id_Subcategoria, "
                + "p.nombre, "
                + "p.descripcion, "
                + "p.precio, "
                + "p.imagen, "
                + "p.estado "
                + "FROM Opcion_Combo oc "
                + "INNER JOIN Producto p ON oc.id_Producto = p.id_Producto "
                + "INNER JOIN Subcategoria s ON p.id_Subcategoria = s.id_Subcategoria "
                + "WHERE oc.id_GrupoCombo = ? "
                + "AND p.id_Subcategoria = ? "
                + "AND p.estado = TRUE "
                + "ORDER BY p.id_Producto";

        try (Connection con = conexion.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idGrupoCombo);
            ps.setInt(2, idSubcategoria);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productos.add(new Producto(
                            rs.getInt("id_Producto"),
                            rs.getInt("id_Categoria"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio"),
                            rs.getString("imagen"),
                            rs.getBoolean("estado"),
                            rs.getInt("id_Subcategoria")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar productos del grupo y subcategoría: " + e.getMessage());
        }

        return productos;
    }

    // ---------------------- B U S C A R O P C I Ó N ----------------
    public Opcion_Combo buscarPorGrupoYProducto(int idGrupoCombo, int idProducto) {
        String sql = "SELECT id_OpcionCombo, precio_extra, "
                + "id_GrupoCombo, id_Producto "
                + "FROM Opcion_Combo "
                + "WHERE id_GrupoCombo = ? "
                + "AND id_Producto = ?";

        try (Connection cn = conexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idGrupoCombo);
            ps.setInt(2, idProducto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearOpcionCombo(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar opción de combo: " + e.getMessage());
        }

        return null;
    }

    // ------------------------ M A P E A R O P C I O N C O M B O ----------------
    private Opcion_Combo mapearOpcionCombo(ResultSet rs) throws SQLException {
        return new Opcion_Combo(
                rs.getInt("id_OpcionCombo"),
                rs.getDouble("precio_extra"),
                rs.getInt("id_GrupoCombo"),
                rs.getInt("id_Producto")
        );
    }
}