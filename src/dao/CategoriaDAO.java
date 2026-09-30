//---------------------- C A T E G O R I A    D A O ----------------------
package dao;

import modelo.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO extends DAO<Categoria> {

    //------------------------ S Q L   B U S C A R   P O R   I D ----------------
    @Override
    protected String getSqlBuscarPorId() {

        return "SELECT id_Categoria, nombre, descripcion, estado "
                + "FROM Categoria "
                + "WHERE id_Categoria = ?";
    }

    //------------------------ S Q L   L I S T A R   T O D O S ----------------
    @Override
    protected String getSqlListarTodos() {

        return "SELECT id_Categoria, nombre, descripcion, estado "
                + "FROM Categoria";
    }

    //------------------------ M A P E A R ----------------
    @Override
    protected Categoria mapear(ResultSet rs) throws SQLException {

        return new Categoria(
                rs.getInt("id_Categoria"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getBoolean("estado")
        );
    }

    //------------------------ C R E A R ----------------
    @Override
    public boolean crear(Categoria categoria) {

        String sql = "INSERT INTO Categoria "
                + "(nombre, descripcion, estado) "
                + "VALUES (?, ?, ?)";

        return ejecutarActualizacion(
                sql,
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.isEstado()
        );
    }

    //------------------------ A C T U A L I Z A R ----------------
    @Override
    public boolean actualizar(Categoria categoria) {

        String sql = "UPDATE Categoria "
                + "SET nombre = ?, descripcion = ?, estado = ? "
                + "WHERE id_Categoria = ?";

        return ejecutarActualizacion(
                sql,
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.isEstado(),
                categoria.getId_Categoria()
        );
    }

    //------------------------ C A M B I A R   E S T A D O ----------------
    public boolean cambiarEstado(int idCategoria, boolean estado) {

        return cambiarEstado(
                "Categoria",
                "id_Categoria",
                idCategoria,
                estado
        );
    }

    //------------------------ L I S T A R   A C T I V A S ----------------
    public List<Categoria> listarActivas() {

        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT id_Categoria, nombre, descripcion, estado "
                + "FROM Categoria "
                + "WHERE estado = TRUE";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categorias.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar categorías activas: "
                    + e.getMessage());
        }

        return categorias;
    }

    //------------------------ B U S C A R   P O R   N O M B R E ----------------
    public Categoria buscarPorNombre(String nombre) {

        String sql = "SELECT id_Categoria, nombre, descripcion, estado "
                + "FROM Categoria "
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
            System.out.println("Error al buscar categoría: "
                    + e.getMessage());
        }

        return null;
    }
}