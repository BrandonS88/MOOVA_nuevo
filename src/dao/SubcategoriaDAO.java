//---------------------- S U B C A T E G O R I A    D A O ----------------------
package dao;

import modelo.Subcategoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubcategoriaDAO extends DAO<Subcategoria> {

    //------------------------ S Q L   B U S C A R   P O R   I D ----------------
    @Override
    protected String getSqlBuscarPorId() {

        return "SELECT id_Subcategoria, id_Categoria, nombre, "
                + "descripcion, estado "
                + "FROM Subcategoria "
                + "WHERE id_Subcategoria = ?";
    }

    //------------------------ S Q L   L I S T A R   T O D O S ----------------
    @Override
    protected String getSqlListarTodos() {

        return "SELECT id_Subcategoria, id_Categoria, nombre, "
                + "descripcion, estado "
                + "FROM Subcategoria";
    }

    //------------------------ M A P E A R ----------------
    @Override
    protected Subcategoria mapear(ResultSet rs) throws SQLException {

        return new Subcategoria(
                rs.getInt("id_Subcategoria"),
                rs.getInt("id_Categoria"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getBoolean("estado")
        );
    }

    //------------------------ C R E A R ----------------
    @Override
    public boolean crear(Subcategoria subcategoria) {

        String sql = "INSERT INTO Subcategoria "
                + "(id_Categoria, nombre, descripcion, estado) "
                + "VALUES (?, ?, ?, ?)";

        return ejecutarActualizacion(
                sql,
                subcategoria.getId_Categoria(),
                subcategoria.getNombre(),
                subcategoria.getDescripcion(),
                subcategoria.isEstado()
        );
    }

    //------------------------ A C T U A L I Z A R ----------------
    @Override
    public boolean actualizar(Subcategoria subcategoria) {

        String sql = "UPDATE Subcategoria "
                + "SET id_Categoria = ?, nombre = ?, descripcion = ?, estado = ? "
                + "WHERE id_Subcategoria = ?";

        return ejecutarActualizacion(
                sql,
                subcategoria.getId_Categoria(),
                subcategoria.getNombre(),
                subcategoria.getDescripcion(),
                subcategoria.isEstado(),
                subcategoria.getId_Subcategoria()
        );
    }

    //------------------------ C A M B I A R   E S T A D O ----------------
    public boolean cambiarEstado(int idSubcategoria, boolean estado) {

        return cambiarEstado(
                "Subcategoria",
                "id_Subcategoria",
                idSubcategoria,
                estado
        );
    }

    //------------------------ L I S T A R   A C T I V A S ----------------
    public List<Subcategoria> listarActivas() {

        List<Subcategoria> subcategorias = new ArrayList<>();

        String sql = "SELECT id_Subcategoria, id_Categoria, nombre, "
                + "descripcion, estado "
                + "FROM Subcategoria "
                + "WHERE estado = TRUE";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                subcategorias.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar subcategorías activas: "
                    + e.getMessage());
        }

        return subcategorias;
    }

    //------------------------ P O R   C A T E G O R I A ----------------
    public List<Subcategoria> listarPorCategoria(int idCategoria) {

        List<Subcategoria> subcategorias = new ArrayList<>();

        String sql = "SELECT id_Subcategoria, id_Categoria, nombre, "
                + "descripcion, estado "
                + "FROM Subcategoria "
                + "WHERE id_Categoria = ? "
                + "AND estado = TRUE "
                + "ORDER BY id_Subcategoria";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    subcategorias.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar subcategorías por categoría: "
                    + e.getMessage());
        }

        return subcategorias;
    }

    //------------------------ B U S C A R   P O R   N O M B R E ----------------
    public Subcategoria buscarPorNombre(String nombre) {

        String sql = "SELECT id_Subcategoria, id_Categoria, nombre, "
                + "descripcion, estado "
                + "FROM Subcategoria "
                + "WHERE nombre = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar subcategoría: "
                    + e.getMessage());
        }

        return null;
    }
}