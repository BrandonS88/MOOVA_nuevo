//---------------------- I N G R E D I E N T E   D A O ----------------------
package dao;

import conexion.*;
import modelo.Ingrediente;

import java.sql.*;
import java.util.List;

public class IngredienteDAO extends DAO<Ingrediente> {

    //------------------------ S Q L ----------------
    @Override
    protected String getSqlBuscarPorId() {

        return "SELECT * "
                + "FROM Ingrediente "
                + "WHERE id_Ingrediente = ?";
    }

    @Override
    protected String getSqlListarTodos() {

        return "SELECT * "
                + "FROM Ingrediente "
                + "ORDER BY id_Ingrediente";
    }

    //------------------------ M A P E A R ----------------
    @Override
    protected Ingrediente mapear(ResultSet rs) throws SQLException {

        Ingrediente ingrediente = new Ingrediente();

        ingrediente.setId_Ingrediente(
                rs.getInt("id_Ingrediente")
        );

        ingrediente.setNombre(
                rs.getString("nombre")
        );

        ingrediente.setCantidad(
                rs.getInt("cantidad")
        );

        ingrediente.setCantidad_minima(
                rs.getInt("cantidad_minima")
        );

        Timestamp fechaCreacion =
                rs.getTimestamp("fecha_creacion");

        if (fechaCreacion != null) {

            ingrediente.setFecha_creacion(
                    fechaCreacion.toLocalDateTime()
            );
        }

        Timestamp fechaActualizacion =
                rs.getTimestamp("fecha_actualizacion");

        if (fechaActualizacion != null) {

            ingrediente.setFecha_actualizacion(
                    fechaActualizacion.toLocalDateTime()
            );
        }

        ingrediente.setDescripcion(
                rs.getString("descripcion")
        );

        ingrediente.setPrecio(
                rs.getDouble("precio")
        );

        ingrediente.setImagen(
                rs.getString("imagen")
        );

        ingrediente.setEstado(
                rs.getBoolean("estado")
        );

        return ingrediente;
    }

    //------------------------ C R E A R ----------------
    @Override
    public boolean crear(Ingrediente ingrediente) {

        String sql = "INSERT INTO Ingrediente "
                + "(nombre, cantidad, cantidad_minima, descripcion, precio, imagen, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        return ejecutarActualizacion(
                sql,
                ingrediente.getNombre(),
                ingrediente.getCantidad(),
                ingrediente.getCantidad_minima(),
                ingrediente.getDescripcion(),
                ingrediente.getPrecio(),
                ingrediente.getImagen(),
                ingrediente.isEstado()
        );
    }

    //------------------------ A C T U A L I Z A R ----------------
    @Override
    public boolean actualizar(Ingrediente ingrediente) {

        String sql = "UPDATE Ingrediente "
                + "SET nombre = ?, "
                + "cantidad = ?, "
                + "cantidad_minima = ?, "
                + "descripcion = ?, "
                + "precio = ?, "
                + "imagen = ?, "
                + "estado = ? "
                + "WHERE id_Ingrediente = ?";

        return ejecutarActualizacion(
                sql,
                ingrediente.getNombre(),
                ingrediente.getCantidad(),
                ingrediente.getCantidad_minima(),
                ingrediente.getDescripcion(),
                ingrediente.getPrecio(),
                ingrediente.getImagen(),
                ingrediente.isEstado(),
                ingrediente.getId_Ingrediente()
        );
    }

    //------------------------ C A M B I A R   E S T A D O ----------------
    public boolean cambiarEstado(
            int idIngrediente,
            boolean estado
    ) {

        return super.cambiarEstado(
                "Ingrediente",
                "id_Ingrediente",
                idIngrediente,
                estado
        );
    }
}
