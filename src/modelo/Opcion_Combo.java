//---------------------------------- O P C I O N   C O M B O -----------------------
package modelo;

public class Opcion_Combo {

    //------------------------ A T R I B U T O S ----------------
    private int id_OpcionCombo;
    private double precio_extra;
    private int id_GrupoCombo;
    private int id_Producto;

    //-------------------- C O N S T R U C T O R -----------------
    public Opcion_Combo() {
    }

    public Opcion_Combo(
            int id_OpcionCombo,
            double precio_extra,
            int id_GrupoCombo,
            int id_Producto
    ) {

        this.id_OpcionCombo = id_OpcionCombo;
        this.precio_extra = precio_extra;
        this.id_GrupoCombo = id_GrupoCombo;
        this.id_Producto = id_Producto;
    }

    //---------------------- G E T T E R S
    public int getId_OpcionCombo() {
        return id_OpcionCombo;
    }

    public double getPrecio_extra() {
        return precio_extra;
    }

    public int getId_GrupoCombo() {
        return id_GrupoCombo;
    }

    public int getId_Producto() {
        return id_Producto;
    }

    //---------------------- S E T T E R S
    public void setId_OpcionCombo(int id_OpcionCombo) {
        this.id_OpcionCombo = id_OpcionCombo;
    }

    public void setPrecio_extra(double precio_extra) {
        this.precio_extra = precio_extra;
    }

    public void setId_GrupoCombo(int id_GrupoCombo) {
        this.id_GrupoCombo = id_GrupoCombo;
    }

    public void setId_Producto(int id_Producto) {
        this.id_Producto = id_Producto;
    }
}

