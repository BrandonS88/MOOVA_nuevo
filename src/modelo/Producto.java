//---------------------- P R O D U C T O ----------------------
package modelo;

public class Producto {
    //------------------- ATRIBUTOS ----------------

    private int id_Producto;
    private int id_Categoria;
    private String nombre;
    private String descripcion;
    private double precio;
    private String imagen;
    private boolean estado;
    private int id_Subcategoria;

    //------------------- CONSTRUCTOR -------------
    public Producto() {
    }

    //SIN SUBCATEGORÍA
    public Producto(int id_Producto, int id_Categoria, String nombre, String descripcion, double precio, String imagen, boolean estado) {
        this.id_Producto = id_Producto;
        this.id_Categoria = id_Categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.estado = estado;
    }

    //CON SUBCATEGORÍA
    public Producto(int id_Producto, int id_Categoria, String nombre, String descripcion, double precio, String imagen, boolean estado, int id_Subcategoria) {
        this.id_Producto = id_Producto;
        this.id_Categoria = id_Categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.estado = estado;
        this.id_Subcategoria = id_Subcategoria;
    }

    //--------------------- SETTERS Y GETTERS --------------------
    public int getId_Producto() {
        return id_Producto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isEstado() {
        return estado;
    }

    public int getId_Categoria() {
        return id_Categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public int getId_Subcategoria() {
        return id_Subcategoria;
    }

    public void setId_Subcategoria(int id_Subcategoria) {
        this.id_Subcategoria = id_Subcategoria;
    }

    public void setId_Producto(int id_Producto) {
        this.id_Producto = id_Producto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setId_Categoria(int id_Categoria) {
        this.id_Categoria = id_Categoria;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    //---------------- TO STRING ------------
    @Override
    public String toString() {
        return nombre;
    }

}
