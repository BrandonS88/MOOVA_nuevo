//---------------------- O P C I O N   P E R S O N A L I Z A C I O N   D A O ----------------------
package dao;

import conexion.*;
import modelo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OpcionPersonalizacionDAO extends DAO<Opcion_Personalizacion> {

    //------------------------ S Q L   B A S E ----------------
    /*Consulta utilizada por buscarPorId(), listarTodos() y listarPorGrupo().
    Cada opción viene acompañada por su Ingrediente y por la cantidad que ese
    ingrediente tiene en la receta.
     */
    
    private String getSqlBase() {

        return "SELECT "
                + "o.id_OpcionPersonalizacion, "
                + "o.id_GrupoPersonalizacion, "
                + "o.id_Ingrediente, "
                + "o.precio_extra, "
                + "o.es_predeterminada, "
                + "i.nombre AS ingrediente_nombre, "
                + "i.cantidad AS ingrediente_cantidad, "
                + "i.cantidad_minima AS ingrediente_cantidad_minima, "
                + "i.fecha_creacion AS ingrediente_fecha_creacion, "
                + "i.fecha_actualizacion AS ingrediente_fecha_actualizacion, "
                + "i.descripcion AS ingrediente_descripcion, "
                + "i.precio AS ingrediente_precio, "
                + "i.imagen AS ingrediente_imagen, "
                + "i.estado AS ingrediente_estado, "
                + "r.cantidad AS cantidad_receta "
                + "FROM Opcion_Personalizacion o "
                + "INNER JOIN Ingrediente i "
                + "ON o.id_Ingrediente = i.id_Ingrediente "
                + "INNER JOIN Grupo_Personalizacion gp "
                + "ON o.id_GrupoPersonalizacion = gp.id_GrupoPersonalizacion "
                + "LEFT JOIN Receta r "
                + "ON r.id_Producto = gp.id_Producto "
                + "AND r.id_Ingrediente = i.id_Ingrediente ";
    }

    //------------------------ S Q L ----------------
    @Override
    protected String getSqlBuscarPorId() {

        return getSqlBase()
                + "WHERE o.id_OpcionPersonalizacion = ?";
    }

    @Override
    protected String getSqlListarTodos() {

        return getSqlBase()
                + "ORDER BY o.id_OpcionPersonalizacion";
    }

    //------------------------ M A P E A R ----------------
    @Override
    protected Opcion_Personalizacion mapear(
            ResultSet rs
    ) throws SQLException {

        Opcion_Personalizacion opcion
                = new Opcion_Personalizacion();

        //------------------------ O P C I O N ----------------
        opcion.setId_OpcionPersonalizacion(
                rs.getInt("id_OpcionPersonalizacion")
        );

        opcion.setId_GrupoPersonalizacion(
                rs.getInt("id_GrupoPersonalizacion")
        );

        opcion.setId_Ingrediente(
                rs.getInt("id_Ingrediente")
        );

        opcion.setPrecio_extra(
                rs.getDouble("precio_extra")
        );

        opcion.setEs_predeterminada(
                rs.getBoolean("es_predeterminada")
        );

        opcion.setCantidadBaseReceta(
                rs.getInt("cantidad_receta")
        );

        //------------------------ I N G R E D I E N T E ----------------
        Ingrediente ingrediente
                = new Ingrediente();

        ingrediente.setId_Ingrediente(
                rs.getInt("id_Ingrediente")
        );

        ingrediente.setNombre(
                rs.getString("ingrediente_nombre")
        );

        ingrediente.setCantidad(
                rs.getInt("ingrediente_cantidad")
        );

        ingrediente.setCantidad_minima(
                rs.getInt("ingrediente_cantidad_minima")
        );

        Timestamp fechaCreacion
                = rs.getTimestamp("ingrediente_fecha_creacion");

        if (fechaCreacion != null) {

            ingrediente.setFecha_creacion(
                    fechaCreacion.toLocalDateTime()
            );
        }

        Timestamp fechaActualizacion
                = rs.getTimestamp("ingrediente_fecha_actualizacion");

        if (fechaActualizacion != null) {

            ingrediente.setFecha_actualizacion(
                    fechaActualizacion.toLocalDateTime()
            );
        }

        ingrediente.setDescripcion(
                rs.getString("ingrediente_descripcion")
        );

        ingrediente.setPrecio(
                rs.getDouble("ingrediente_precio")
        );

        ingrediente.setImagen(
                rs.getString("ingrediente_imagen")
        );

        ingrediente.setEstado(
                rs.getBoolean("ingrediente_estado")
        );

        //------------------------ R E L A C I O N ----------------
        opcion.setIngrediente(
                ingrediente
        );

        return opcion;
    }

    //------------------------ L I S T A R   P O R   G R U P O ----------------
    public List<Opcion_Personalizacion> listarPorGrupo(
            int idGrupoPersonalizacion
    ) {

        List<Opcion_Personalizacion> lista
                = new ArrayList<>();

        String sql = getSqlBase()
                + "WHERE o.id_GrupoPersonalizacion = ? "
                + "ORDER BY o.id_OpcionPersonalizacion";

        try (
                Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    idGrupoPersonalizacion
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
                    "Error al listar opciones de personalización: "
                    + e.getMessage()
            );
        }

        return lista;
    }

    //------------------------ C R E A R ----------------
    @Override
    public boolean crear(
            Opcion_Personalizacion opcion
    ) {

        String sql = "INSERT INTO Opcion_Personalizacion "
                + "(id_GrupoPersonalizacion, id_Ingrediente, "
                + "precio_extra, es_predeterminada) "
                + "VALUES (?, ?, ?, ?)";

        return ejecutarActualizacion(
                sql,
                opcion.getId_GrupoPersonalizacion(),
                opcion.getId_Ingrediente(),
                opcion.getPrecio_extra(),
                opcion.isEs_predeterminada()
        );
    }

    //------------------------ A C T U A L I Z A R ----------------
    @Override
    public boolean actualizar(
            Opcion_Personalizacion opcion
    ) {

        String sql = "UPDATE Opcion_Personalizacion "
                + "SET id_GrupoPersonalizacion = ?, "
                + "id_Ingrediente = ?, "
                + "precio_extra = ?, "
                + "es_predeterminada = ? "
                + "WHERE id_OpcionPersonalizacion = ?";

        return ejecutarActualizacion(
                sql,
                opcion.getId_GrupoPersonalizacion(),
                opcion.getId_Ingrediente(),
                opcion.getPrecio_extra(),
                opcion.isEs_predeterminada(),
                opcion.getId_OpcionPersonalizacion()
        );
    }
}
