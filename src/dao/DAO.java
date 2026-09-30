//---------------------- D A O   G E N É R I C O ----------------------
package dao;

import conexion.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DAO<T> {

    //------------------------ A T R I B U T O S ----------------
    protected Conexion_MOOVA conexion;

    //------------------------ C O N S T R U C T O R ----------------
    public DAO() {
        conexion = new Conexion_MOOVA();
    }

    //------------------------ M É T O D O S   A B S T R A C T O S ----------------
    // Cada DAO hijo arma su propio SELECT (columnas, JOIN, etc.)
    protected abstract String getSqlBuscarPorId();
    
    protected abstract String getSqlListarTodos();

    // Convierte una fila del ResultSet en el objeto del modelo.
    protected abstract T mapear(ResultSet rs) throws SQLException;

    // Cada tabla tiene columnas distintas para insertar/actualizar,
    // por eso el crear y el actualizar los implementa cada DAO hijo.
    public abstract boolean crear(T objeto);

    public abstract boolean actualizar(T objeto);

    //------------------------ B U S C A R   P O R   I D ----------------
    public T buscarPorId(int id) {

        Connection con = conexion.getConnection();

        // Si la conexión falló (por ejemplo, MySQL está apagado) no se
        // intenta usarla: se devuelve null de inmediato. Sin esta
        // comprobación, prepareStatement sobre una conexión nula lanzaría
        // un NullPointerException, que no es un SQLException y por lo tanto
        // no lo atrapa el catch de abajo, y el programa se caería.
        if (con == null) {
            return null;
        }

        try (Connection c = con; PreparedStatement ps = c.prepareStatement(getSqlBuscarPorId())) {

            ps.setInt(
                    1,
                    id
            );

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapear(rs);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar registro: "
                    + e.getMessage()
            );
        }

        return null;
    }

    //------------------------ L I S T A R   T O D O S ----------------
    public List<T> listarTodos() {

        List<T> lista = new ArrayList<>();

        Connection con = conexion.getConnection();

        // Misma razón que en buscarPorId(): si no hay conexión se
        // devuelve una lista vacía en lugar de fallar con una excepción
        // que el catch de SQLException no podría atrapar.
        if (con == null) {
            return lista;
        }

        try (Connection c = con; PreparedStatement ps = c.prepareStatement(getSqlListarTodos()); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(
                        mapear(rs)
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar registros: "
                    + e.getMessage()
            );
        }

        return lista;
    }

    //------------------------ C A M B I A R   E S T A D O ----------------
    // Usar solo en los DAO cuya tabla SÍ tenga columna "estado"
    // (Categoria, Subcategoria, Usuario, Ingrediente, Producto, Combo, Descuento...).
    // Las tablas sin estado (Receta, Producto_Combo, Grupo_Combo, Opcion_Combo,
    // Grupo_Personalizacion, Opcion_Personalizacion, Detalle_Venta, etc.)
    // no deben llamar este método.
    protected boolean cambiarEstado(
            String tabla,
            String columnaId,
            int id,
            boolean estado
    ) {

        String sql = "UPDATE " + tabla
                + " SET estado = ? "
                + "WHERE " + columnaId + " = ?";

        Connection con = conexion.getConnection();

        // Si no se pudo conectar se devuelve false sin intentar el UPDATE.
        if (con == null) {
            return false;
        }

        try (Connection c = con; PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setBoolean(
                    1,
                    estado
            );

            ps.setInt(
                    2,
                    id
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al cambiar estado en " + tabla + ": "
                    + e.getMessage()
            );
        }

        return false;
    }

    //------------------------ E J E C U T A R   A C T U A L I Z A C I Ó N ----------------
    // Helper opcional para que un DAO hijo ejecute un INSERT/UPDATE/DELETE
    // ya armado sin repetir el try/catch de conexión cada vez.
    // Ej: ejecutarActualizacion(sql, nombre, precio, idCategoria);
    protected boolean ejecutarActualizacion(
            String sql,
            Object... parametros
    ) {

        String sqlLocal = sql;

        Connection con = conexion.getConnection();

        // Si la conexión es nula se devuelve false sin intentar nada, en
        // lugar de dejar que prepareStatement lance un
        // NullPointerException que el catch de SQLException no atrapa.
        if (con == null) {
            System.out.println("No se pudo conectar a la base de datos.");
            return false;
        }

        try (Connection c = con; PreparedStatement ps = c.prepareStatement(sqlLocal)) {

            for (int i = 0; i < parametros.length; i++) {

                ps.setObject(
                        i + 1,
                        parametros[i]
                );
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al ejecutar actualización: "
                    + e.getMessage()
            );
        }

        return false;
    }
}