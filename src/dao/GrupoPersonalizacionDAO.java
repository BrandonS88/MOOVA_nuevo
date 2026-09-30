//---------------------- G R U P O   P E R S O N A L I Z A C I O N   D A O ----------------------
package dao;

import modelo.Grupo_Personalizacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoPersonalizacionDAO extends DAO<Grupo_Personalizacion> {

    //------------------------ S Q L ----------------
    @Override
    protected String getSqlBuscarPorId() {

        return "SELECT * "
                + "FROM Grupo_Personalizacion "
                + "WHERE id_GrupoPersonalizacion = ?";
    }

    @Override
    protected String getSqlListarTodos() {

        return "SELECT * "
                + "FROM Grupo_Personalizacion "
                + "ORDER BY id_GrupoPersonalizacion";
    }

    //------------------------ M A P E A R ----------------
    @Override
    protected Grupo_Personalizacion mapear(ResultSet rs) throws SQLException {

        Grupo_Personalizacion grupo = new Grupo_Personalizacion();

        grupo.setId_GrupoPersonalizacion(
                rs.getInt("id_GrupoPersonalizacion")
        );

        grupo.setId_Producto(
                rs.getInt("id_Producto")
        );

        grupo.setNombre(
                rs.getString("nombre")
        );

        grupo.setObligatorio(
                rs.getBoolean("obligatorio")
        );

        grupo.setCantidad_minima(
                rs.getInt("cantidad_minima")
        );

        grupo.setCantidad_maxima(
                rs.getInt("cantidad_maxima")
        );

        return grupo;
    }

    //------------------------ L I S T A R   P O R   P R O D U C T O ----------------
    public List<Grupo_Personalizacion> listarPorProducto(int idProducto) {

        List<Grupo_Personalizacion> lista = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM Grupo_Personalizacion "
                + "WHERE id_Producto = ? "
                + "ORDER BY id_GrupoPersonalizacion";

        try (
                Connection con = conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    idProducto
            );

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    lista.add(
                            mapear(rs)
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar grupos de personalización: "
                    + e.getMessage()
            );
        }

        return lista;
    }

    //------------------------ C R E A R ----------------
    @Override
    public boolean crear(Grupo_Personalizacion grupo) {

        String sql = "INSERT INTO Grupo_Personalizacion "
                + "(id_Producto, nombre, obligatorio, cantidad_minima, cantidad_maxima) "
                + "VALUES (?, ?, ?, ?, ?)";

        return ejecutarActualizacion(
                sql,
                grupo.getId_Producto(),
                grupo.getNombre(),
                grupo.isObligatorio(),
                grupo.getCantidad_minima(),
                grupo.getCantidad_maxima()
        );
    }

    //------------------------ A C T U A L I Z A R ----------------
    @Override
    public boolean actualizar(Grupo_Personalizacion grupo) {

        String sql = "UPDATE Grupo_Personalizacion "
                + "SET id_Producto = ?, "
                + "nombre = ?, "
                + "obligatorio = ?, "
                + "cantidad_minima = ?, "
                + "cantidad_maxima = ? "
                + "WHERE id_GrupoPersonalizacion = ?";

        return ejecutarActualizacion(
                sql,
                grupo.getId_Producto(),
                grupo.getNombre(),
                grupo.isObligatorio(),
                grupo.getCantidad_minima(),
                grupo.getCantidad_maxima(),
                grupo.getId_GrupoPersonalizacion()
        );
    }
}

