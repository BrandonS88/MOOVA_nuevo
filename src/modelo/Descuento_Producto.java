//---------------------- D E S C U E N T O _ P R O D U C T O ----------------------
package modelo;

public class Descuento_Producto {
    //------------------- ATRIBUTOS ----------------
    private int id_DescuentoProducto;
    private int id_Descuento;
    private int id_Producto;

    //------------------- CONSTRUCTOR -------------

    public Descuento_Producto() {
    }

    public Descuento_Producto(int id_DescuentoProducto, int id_Descuento, int id_Producto) {
        this.id_DescuentoProducto = id_DescuentoProducto;
        this.id_Descuento = id_Descuento;
        this.id_Producto = id_Producto;
    }

    //--------------------- SETTERS Y GETTERS --------------------

    public int getId_DescuentoProducto() {
        return id_DescuentoProducto;
    }

    public int getId_Descuento() {
        return id_Descuento;
    }

    public int getId_Producto() {
        return id_Producto;
    }

    public void setId_DescuentoProducto(int id_DescuentoProducto) {
        this.id_DescuentoProducto = id_DescuentoProducto;
    }

    public void setId_Descuento(int id_Descuento) {
        this.id_Descuento = id_Descuento;
    }

    public void setId_Producto(int id_Producto) {
        this.id_Producto = id_Producto;
    }

    //---------------- TO STRING ------------
    @Override
    public String toString() {
        return "Descuento_Producto{" + "id_DescuentoProducto=" + id_DescuentoProducto + ", id_Descuento=" + id_Descuento + ", id_Producto=" + id_Producto + '}';
    }

}
