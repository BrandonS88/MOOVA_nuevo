//---------------------- P R O D U C T O _ C O M B O ----------------------
package modelo;

public class Producto_Combo {
    //------------------- ATRIBUTOS ----------------
    private int id_ProductoCombo;
    private int id_Producto;
    private int id_Combo;

    //------------------- CONSTRUCTOR -------------

    public Producto_Combo() {
    }

    public Producto_Combo(int id_ProductoCombo, int id_Producto, int id_Combo) {
        this.id_ProductoCombo = id_ProductoCombo;
        this.id_Producto = id_Producto;
        this.id_Combo = id_Combo;
    }

    //--------------------- SETTERS Y GETTERS --------------------

    public int getId_ProductoCombo() {
        return id_ProductoCombo;
    }

    public int getId_Producto() {
        return id_Producto;
    }

    public int getId_Combo() {
        return id_Combo;
    }

    public void setId_ProductoCombo(int id_ProductoCombo) {
        this.id_ProductoCombo = id_ProductoCombo;
    }

    public void setId_Producto(int id_Producto) {
        this.id_Producto = id_Producto;
    }

    public void setId_Combo(int id_Combo) {
        this.id_Combo = id_Combo;
    }

    //---------------- TO STRING ------------
    @Override
    public String toString() {
        return "Producto_Combo{" + "id_ProductoCombo=" + id_ProductoCombo + ", id_Producto=" + id_Producto + ", id_Combo=" + id_Combo + '}';
    }

}
