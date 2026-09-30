//---------------------- D E T A L L E _ V E N T A _ O P C I O N C O M B O ----------------------
package modelo;

public class Detalle_Venta_OpcionCombo {
    //------------------- ATRIBUTOS ----------------
    private int id_DetalleVentaOpcion;
    private int id_DetalleVenta;
    private int id_OpcionCombo;
    private double precio_extra;

    //------------------- CONSTRUCTOR -------------

    public Detalle_Venta_OpcionCombo() {
    }

    public Detalle_Venta_OpcionCombo(int id_DetalleVentaOpcion, int id_DetalleVenta, int id_OpcionCombo, double precio_extra) {
        this.id_DetalleVentaOpcion = id_DetalleVentaOpcion;
        this.id_DetalleVenta = id_DetalleVenta;
        this.id_OpcionCombo = id_OpcionCombo;
        this.precio_extra = precio_extra;
    }

    //--------------------- SETTERS Y GETTERS --------------------

    public int getId_DetalleVentaOpcion() {
        return id_DetalleVentaOpcion;
    }

    public int getId_DetalleVenta() {
        return id_DetalleVenta;
    }

    public int getId_OpcionCombo() {
        return id_OpcionCombo;
    }

    public double getPrecio_extra() {
        return precio_extra;
    }

    public void setId_DetalleVentaOpcion(int id_DetalleVentaOpcion) {
        this.id_DetalleVentaOpcion = id_DetalleVentaOpcion;
    }

    public void setId_DetalleVenta(int id_DetalleVenta) {
        this.id_DetalleVenta = id_DetalleVenta;
    }

    public void setId_OpcionCombo(int id_OpcionCombo) {
        this.id_OpcionCombo = id_OpcionCombo;
    }

    public void setPrecio_extra(double precio_extra) {
        this.precio_extra = precio_extra;
    }

    //---------------- TO STRING ------------
    @Override
    public String toString() {
        return "Detalle_Venta_OpcionCombo{" + "id_DetalleVentaOpcion=" + id_DetalleVentaOpcion + ", id_DetalleVenta=" + id_DetalleVenta + ", id_OpcionCombo=" + id_OpcionCombo + ", precio_extra=" + precio_extra + '}';
    }

}
