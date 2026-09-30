//---------------------- C O M B O    D A O ----------------------
package dao;

import conexion.*;
import modelo.Combo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComboDAO {

    //------------------------ A T R I B U T O S ----------------
    private Conexion_MOOVA conexion;

    //------------------------ C O N S T R U C T O R ----------------
    public ComboDAO() {
        conexion = new Conexion_MOOVA();
    }

    //---------------------- A B R I R   C O N E X I Ó N ----------------------
    /**
     * Pide una conexión a la base de datos y avisa por consola si no la
     * consigue.
     *
     * ¿Por qué este método? Conexion_MOOVA.getConnection() devuelve null
     * cuando MySQL no está prendido o la contraseña está mal. Si una
     * clase usara esa conexión sin revisarla, la siguiente línea
     * (prepareStatement) lanzaría un NullPointerException, que no es un
     * SQLException, y el programa se cerraría de golpe sin avisar nada.
     *
     * A diferencia de los demás DAOs, ComboDAO no hereda de DAO<Combo>
     * porque tiene consultas propias del negocio (listarPorCategoria,
     * listarPorProducto, etc.) que no encajan en el modelo genérico.
     * Por eso necesita su propia revisión de la conexión.
     *
     * @return la conexión, o null si no se pudo abrir.
     */
    private Connection abrir() {

        Connection con = conexion.getConnection();

        if (con == null) {
            System.out.println(
                    "No se pudo conectar a la base de datos."
            );
        }

        return con;
    }

    //------------------------ B U S C A R   P O R   I D ----------------
    public Combo buscarPorId(int idCombo) {

        String sql = "SELECT id_Combo, nombre, descripcion, "
                + "tipo, precio, precio_extra, imagen, estado, "
                + "id_Subcategoria "
                + "FROM Combo "
                + "WHERE id_Combo = ?";

        Connection cnx = abrir();

        if (cnx == null) {
            return null;
        }

        try (Connection con = cnx; PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    idCombo
            );

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapearCombo(rs);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar combo: "
                    + e.getMessage()
            );
        }

        return null;
    }

    //------------------------ L I S T A R   T O D O S ----------------
    public List<Combo> listarTodos() {

        List<Combo> combos = new ArrayList<>();

        String sql = "SELECT id_Combo, nombre, descripcion, "
                + "tipo, precio, precio_extra, imagen, estado, "
                + "id_Subcategoria "
                + "FROM Combo "
                + "ORDER BY id_Combo";

        Connection cnx = abrir();

        if (cnx == null) {
            return combos;
        }

        try (Connection con = cnx; PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                combos.add(
                        mapearCombo(rs)
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar combos: "
                    + e.getMessage()
            );
        }

        return combos;
    }

    //------------------------ L I S T A R   A C T I V O S ----------------
    public List<Combo> listarActivos() {

        List<Combo> combos = new ArrayList<>();

        String sql = "SELECT id_Combo, nombre, descripcion, "
                + "tipo, precio, precio_extra, imagen, estado, "
                + "id_Subcategoria "
                + "FROM Combo "
                + "WHERE estado = TRUE "
                + "ORDER BY id_Combo";

        Connection cnx = abrir();

        if (cnx == null) {
            return combos;
        }

        try (Connection con = cnx; PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                combos.add(
                        mapearCombo(rs)
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar combos activos: "
                    + e.getMessage()
            );
        }

        return combos;
    }

    //------------------------ P O R   C A T E G O R I A ----------------
    public List<Combo> listarPorCategoria(int idCategoria) {

        List<Combo> combos = new ArrayList<>();

        String sql = "SELECT c.id_Combo, c.nombre, c.descripcion, "
                + "c.tipo, c.precio, c.precio_extra, "
                + "c.imagen, c.estado, c.id_Subcategoria "
                + "FROM Combo c "
                + "INNER JOIN Subcategoria s "
                + "ON c.id_Subcategoria = s.id_Subcategoria "
                + "WHERE s.id_Categoria = ? "
                + "AND c.estado = TRUE "
                + "ORDER BY c.id_Combo";

        Connection cnx = abrir();

        if (cnx == null) {
            return combos;
        }

        try (Connection con = cnx; PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    idCategoria
            );

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    combos.add(
                            mapearCombo(rs)
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar combos por categoría: "
                    + e.getMessage()
            );
        }

        return combos;
    }

//------------------------ P O R   P R O D U C T O ----------------
    public List<Combo> listarPorProducto(
            int idProducto
    ) {

        List<Combo> combos = new ArrayList<>();

        String sql = "SELECT c.id_Combo, c.nombre, c.descripcion, "
                + "c.tipo, c.precio, c.precio_extra, "
                + "c.imagen, c.estado, c.id_Subcategoria "
                + "FROM Combo c "
                + "INNER JOIN Producto_Combo pc "
                + "ON c.id_Combo = pc.id_Combo "
                + "WHERE pc.id_Producto = ? "
                + "AND c.tipo = 'Complementario' "
                + "AND c.estado = TRUE "
                + "ORDER BY c.id_Combo";

        Connection cnx = abrir();

        if (cnx == null) {
            return combos;
        }

        try (Connection con = cnx; PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    idProducto
            );

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    combos.add(
                            mapearCombo(rs)
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar combos por producto: "
                    + e.getMessage()
            );
        }

        return combos;
    }

    //------------------------ M A P E A R   C O M B O ----------------
    private Combo mapearCombo(
            ResultSet rs
    ) throws SQLException {

        return new Combo(
                rs.getInt("id_Combo"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("tipo"),
                rs.getDouble("precio"),
                rs.getDouble("precio_extra"),
                rs.getString("imagen"),
                rs.getBoolean("estado"),
                rs.getObject(
                        "id_Subcategoria",
                        Integer.class
                )
        );
    }

//------------------------ P O R   S U B C A T E G O R I A ----------------
    public List<Combo> listarPorSubcategoria(
            int idSubcategoria
    ) {

        List<Combo> combos = new ArrayList<>();

        String sql = "SELECT id_Combo, nombre, descripcion, "
                + "tipo, precio, precio_extra, imagen, estado, "
                + "id_Subcategoria "
                + "FROM Combo "
                + "WHERE id_Subcategoria = ? "
                + "AND estado = TRUE "
                + "ORDER BY id_Combo";

        Connection cnx = abrir();

        if (cnx == null) {
            return combos;
        }

        try (Connection con = cnx; PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    idSubcategoria
            );

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    combos.add(
                            mapearCombo(rs)
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar combos por subcategoría: "
                    + e.getMessage()
            );
        }

        return combos;
    }

}