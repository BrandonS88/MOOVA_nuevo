//---------------------- G R U P O   C O M B O    D A O ----------------------
package dao;

import modelo.Grupo_Combo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoComboDAO extends DAO<Grupo_Combo> {

    //------------------------ S Q L   B U S C A R   P O R   I D ----------------
    @Override
    protected String getSqlBuscarPorId() {

        return "SELECT id_GrupoCombo, id_Combo, "
                + "nombre, obligatorio, cantidad_minima, "
                + "cantidad_maxima "
                + "FROM Grupo_Combo "
                + "WHERE id_GrupoCombo = ?";
    }

    //------------------------ S Q L   L I S T A R   T O D O S ----------------
    @Override
    protected String getSqlListarTodos() {

        return "SELECT id_GrupoCombo, id_Combo, "
                + "nombre, obligatorio, cantidad_minima, "
                + "cantidad_maxima "
                + "FROM Grupo_Combo";
    }

    //------------------------ M A P E A R ----------------
    @Override
    protected Grupo_Combo mapear(ResultSet rs) throws SQLException {

        return new Grupo_Combo(
                rs.getInt("id_GrupoCombo"),
                rs.getInt("id_Combo"),
                rs.getString("nombre"),
                rs.getBoolean("obligatorio"),
                rs.getInt("cantidad_minima"),
                rs.getInt("cantidad_maxima")
        );
    }

    //------------------------ C R E A R ----------------
    @Override
    public boolean crear(Grupo_Combo grupo) {

        String sql = "INSERT INTO Grupo_Combo "
                + "(id_Combo, nombre, obligatorio, cantidad_minima, cantidad_maxima) "
                + "VALUES (?, ?, ?, ?, ?)";

        return ejecutarActualizacion(
                sql,
                grupo.getId_Combo(),
                grupo.getNombre(),
                grupo.isObligatorio(),
                grupo.getCantidad_minima(),
                grupo.getCantidad_maxima()
        );
    }

    //------------------------ A C T U A L I Z A R ----------------
    @Override
    public boolean actualizar(Grupo_Combo grupo) {

        String sql = "UPDATE Grupo_Combo "
                + "SET id_Combo = ?, nombre = ?, obligatorio = ?, "
                + "cantidad_minima = ?, cantidad_maxima = ? "
                + "WHERE id_GrupoCombo = ?";

        return ejecutarActualizacion(
                sql,
                grupo.getId_Combo(),
                grupo.getNombre(),
                grupo.isObligatorio(),
                grupo.getCantidad_minima(),
                grupo.getCantidad_maxima(),
                grupo.getId_GrupoCombo()
        );
    }

    // Nota: Grupo_Combo no tiene columna "estado", por lo que no se
    // expone un método cambiarEstado (ver comentario en DAO genérico).

    //------------------------ P O R   C O M B O ----------------
    public List<Grupo_Combo> listarPorCombo(int idCombo) {

        List<Grupo_Combo> grupos = new ArrayList<>();

        String sql = "SELECT id_GrupoCombo, id_Combo, "
                + "nombre, obligatorio, cantidad_minima, "
                + "cantidad_maxima "
                + "FROM Grupo_Combo "
                + "WHERE id_Combo = ? "
                + "ORDER BY id_GrupoCombo";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCombo);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    grupos.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar grupos del combo: "
                    + e.getMessage());
        }

        return grupos;
    }
}