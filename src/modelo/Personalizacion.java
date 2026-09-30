//---------------------- P E R S O N A L I Z A C I O N ----------------------
package modelo;

public class Personalizacion {

    //------------------- A T R I B U T O S ----------------
    private int id_Personalizacion;
    private int cantidad;
    private double precio;
    private String tipo;
    private int id_DetalleVenta;
    private int id_Ingrediente;

    //------------------- C O N S T R U C T O R E S ----------------

    public Personalizacion() {
    }

    public Personalizacion(
            int id_Personalizacion,
            int cantidad,
            double precio,
            String tipo,
            int id_DetalleVenta,
            int id_Ingrediente
    ) {

        this.id_Personalizacion = id_Personalizacion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.tipo = tipo;
        this.id_DetalleVenta = id_DetalleVenta;
        this.id_Ingrediente = id_Ingrediente;
    }

    //------------------- G E T T E R S ----------------

    public int getId_Personalizacion() {
        return id_Personalizacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public String getTipo() {
        return tipo;
    }

    public int getId_DetalleVenta() {
        return id_DetalleVenta;
    }

    public int getId_Ingrediente() {
        return id_Ingrediente;
    }

    //------------------- S E T T E R S ----------------

    public void setId_Personalizacion(
            int id_Personalizacion
    ) {

        this.id_Personalizacion
                = id_Personalizacion;
    }

    public void setCantidad(
            int cantidad
    ) {

        this.cantidad = cantidad;
    }

    public void setPrecio(
            double precio
    ) {

        this.precio = precio;
    }

    public void setTipo(
            String tipo
    ) {

        this.tipo = tipo;
    }

    public void setId_DetalleVenta(
            int id_DetalleVenta
    ) {

        this.id_DetalleVenta
                = id_DetalleVenta;
    }

    public void setId_Ingrediente(
            int id_Ingrediente
    ) {

        this.id_Ingrediente
                = id_Ingrediente;
    }

    //------------------- T O   S T R I N G ----------------

    @Override
    public String toString() {

        return "Personalizacion{"
                + "id_Personalizacion="
                + id_Personalizacion
                + ", cantidad="
                + cantidad
                + ", precio="
                + precio
                + ", tipo='"
                + tipo
                + '\''
                + ", id_DetalleVenta="
                + id_DetalleVenta
                + ", id_Ingrediente="
                + id_Ingrediente
                + '}';
    }
}
