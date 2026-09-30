//---------------------- S E L E C C I O N   P E R S O N A L I Z A C I O N ----------------------
package modelo;

public class SeleccionPersonalizacion {

    //------------------------ T I P O ----------------
    public enum Tipo {

        SELECCION,
        AGREGAR,
        QUITAR
    }

    //------------------------ A T R I B U T O S ----------------
    private Grupo_Personalizacion grupo;
    private Opcion_Personalizacion opcion;

    private int cantidad;

    // Precio extra por unidad de la opción.
    // Para QUITAR normalmente será 0.
    private double precioExtra;

    private Tipo tipo;

    //------------------------ C O N S T R U C T O R ----------------
    public SeleccionPersonalizacion() {
    }

    public SeleccionPersonalizacion(
            Grupo_Personalizacion grupo,
            Opcion_Personalizacion opcion,
            int cantidad,
            double precioExtra,
            Tipo tipo
    ) {

        this.grupo = grupo;
        this.opcion = opcion;
        this.cantidad = cantidad;
        this.precioExtra = precioExtra;
        this.tipo = tipo;
    }

    //------------------------ G E T T E R S ----------------

    public Grupo_Personalizacion getGrupo() {

        return grupo;
    }

    public Opcion_Personalizacion getOpcion() {

        return opcion;
    }

    public int getCantidad() {

        return cantidad;
    }

    public double getPrecioExtra() {

        return precioExtra;
    }

    public Tipo getTipo() {

        return tipo;
    }

    //------------------------ S E T T E R S ----------------

    public void setGrupo(
            Grupo_Personalizacion grupo
    ) {

        this.grupo = grupo;
    }

    public void setOpcion(
            Opcion_Personalizacion opcion
    ) {

        this.opcion = opcion;
    }

    public void setCantidad(
            int cantidad
    ) {

        this.cantidad = cantidad;
    }

    public void setPrecioExtra(
            double precioExtra
    ) {

        this.precioExtra = precioExtra;
    }

    public void setTipo(
            Tipo tipo
    ) {

        this.tipo = tipo;
    }

    //---------------------- P R E C I O   T O T A L ----------------
    public double getPrecioTotal() {

        if (tipo == Tipo.QUITAR) {

            return 0.00;
        }

        return cantidad * precioExtra;
    }
}
