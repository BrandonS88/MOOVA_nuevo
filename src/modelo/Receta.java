//---------------------- R E C E T A ----------------------
package modelo;

/**
 * Clase que representa la tabla Receta de la base de datos.
 * 
 * La receta define qué ingredientes se utilizan para preparar un producto
 * y en qué cantidad. Es la relación entre Producto e Ingrediente.
 * 
 * Ejemplo: Una hamburguesa (Producto) tiene 200g de carne (Ingrediente)
 * en su receta.
 */
public class Receta {
    
    //--------------- ATRIBUTOS -----------------
    
    /** Identificador único de la receta */
    private int id_Receta;
    
    /**
     * Cantidad del ingrediente necesaria para preparar el producto.
     * 
     * Se declara como int y no como double porque así está definida la
     * columna "cantidad" de la tabla Receta en MySQL (INT). Si el modelo
     * dijera double, MySQL truncaría los decimales al guardar y la
     * aplicación mostraría una cantidad que nunca existió en la base de
     * datos.
     */
    private int cantidad;
    
    /** ID del producto al que pertenece esta receta */
    private int id_Producto;
    
    /** ID del ingrediente que forma parte de la receta */
    private int id_Ingrediente;

    //----------------- CONSTRUCTOR --------------
    
    /**
     * Constructor vacío.
     * Necesario para crear objetos Receta sin datos iniciales.
     */
    public Receta() {
    }

    /**
     * Constructor con todos los parámetros.
     * 
     * @param id_Receta Identificador único de la receta
     * @param cantidad Cantidad del ingrediente
     * @param id_Producto ID del producto asociado
     * @param id_Ingrediente ID del ingrediente asociado
     */
    public Receta(int id_Receta, int cantidad, int id_Producto, int id_Ingrediente) {
        this.id_Receta = id_Receta;
        this.cantidad = cantidad;
        this.id_Producto = id_Producto;
        this.id_Ingrediente = id_Ingrediente;
    }

    //------------- GETTERS Y SETTERS ---------------
    
    public int getId_Receta() {
        return id_Receta;
    }

    public void setId_Receta(int id_Receta) {
        this.id_Receta = id_Receta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_Producto() {
        return id_Producto;
    }

    public void setId_Producto(int id_Producto) {
        this.id_Producto = id_Producto;
    }

    public int getId_Ingrediente() {
        return id_Ingrediente;
    }

    public void setId_Ingrediente(int id_Ingrediente) {
        this.id_Ingrediente = id_Ingrediente;
    }

    //----------------- TO STRING -----------
    
    /**
     * Devuelve una representación en texto de la receta.
     * Muestra el ID de la receta y la cantidad.
     */
    @Override
    public String toString() {
        return "Receta " + id_Receta + " - Cantidad: " + cantidad;
    }
}
