//---------------------- R E C E T A   D A O ----------------------
package dao;

import conexion.*;
import modelo.Receta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la tabla Receta.
 * 
 * Maneja todas las operaciones de base de datos relacionadas con las recetas:
 * buscar, listar, crear y actualizar recetas.
 * 
 * Una receta define qué ingredientes se utilizan para preparar un producto.
 * 
 * NOTA: La tabla Receta NO tiene columna "estado", por lo que no se puede
 * usar el método cambiarEstado del DAO genérico.
 */
public class RecetaDAO extends DAO<Receta> {

    //------------------------ S Q L ----------------
    
    /**
     * SQL para buscar una receta por su ID.
     */
    @Override
    protected String getSqlBuscarPorId() {
        return "SELECT * "
                + "FROM Receta "
                + "WHERE id_Receta = ?";
    }

    /**
     * SQL para listar todas las recetas ordenadas por ID.
     */
    @Override
    protected String getSqlListarTodos() {
        return "SELECT * "
                + "FROM Receta "
                + "ORDER BY id_Receta";
    }

    //------------------------ M A P E A R ----------------
    
    /**
     * Convierte un ResultSet en un objeto Receta.
     * 
     * @param rs ResultSet con los datos de la receta
     * @return Objeto Receta con los datos del ResultSet
     * @throws SQLException Si hay error al leer el ResultSet
     */
    @Override
    protected Receta mapear(ResultSet rs) throws SQLException {
        Receta receta = new Receta();

        receta.setId_Receta(
                rs.getInt("id_Receta")
        );

        // La columna "cantidad" de la tabla Receta es INT en MySQL, así
        // que se lee con getInt. Con getDouble MySQL la convertiría a
        // decimal y el modelo la devolvería como 200.0 en vez de 200.
        receta.setCantidad(
                rs.getInt("cantidad")
        );

        receta.setId_Producto(
                rs.getInt("id_Producto")
        );

        receta.setId_Ingrediente(
                rs.getInt("id_Ingrediente")
        );

        return receta;
    }

    //------------------------ C R E A R ----------------
    
    /**
     * Inserta una nueva receta en la base de datos.
     * 
     * @param receta Objeto Receta con los datos a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    @Override
    public boolean crear(Receta receta) {
        String sql = "INSERT INTO Receta "
                + "(cantidad, id_Producto, id_Ingrediente) "
                + "VALUES (?, ?, ?)";

        return ejecutarActualizacion(
                sql,
                receta.getCantidad(),
                receta.getId_Producto(),
                receta.getId_Ingrediente()
        );
    }

    //------------------------ A C T U A L I Z A R ----------------
    
    /**
     * Actualiza una receta existente en la base de datos.
     * 
     * @param receta Objeto Receta con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    @Override
    public boolean actualizar(Receta receta) {
        String sql = "UPDATE Receta "
                + "SET cantidad = ?, "
                + "id_Producto = ?, "
                + "id_Ingrediente = ? "
                + "WHERE id_Receta = ?";

        return ejecutarActualizacion(
                sql,
                receta.getCantidad(),
                receta.getId_Producto(),
                receta.getId_Ingrediente(),
                receta.getId_Receta()
        );
    }

    //------------------------ L I S T A R   P O R   P R O D U C T O ----------------
    
    /**
     * Lista todas las recetas de un producto específico.
     * 
     * @param idProducto ID del producto del cual se quieren las recetas
     * @return Lista de recetas del producto
     */
    public List<Receta> listarPorProducto(int idProducto) {
        List<Receta> lista = new ArrayList<>();
        String sql = "SELECT * FROM Receta WHERE id_Producto = ? ORDER BY id_Receta";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar recetas por producto: "
                    + e.getMessage()
            );
        }

        return lista;
    }

    //------------------------ L I S T A R   P O R   I N G R E D I E N T E ----------------
    
    /**
     * Lista todas las recetas que usan un ingrediente específico.
     * 
     * @param idIngrediente ID del ingrediente del cual se quieren las recetas
     * @return Lista de recetas que usan el ingrediente
     */
    public List<Receta> listarPorIngrediente(int idIngrediente) {
        List<Receta> lista = new ArrayList<>();
        String sql = "SELECT * FROM Receta WHERE id_Ingrediente = ? ORDER BY id_Receta";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idIngrediente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar recetas por ingrediente: "
                    + e.getMessage()
            );
        }

        return lista;
    }
}
