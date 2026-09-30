//---------------------- G R U P O   P E R S O N A L I Z A C I O N ----------------------
package modelo;

public class Grupo_Personalizacion {

    //------------------- A T R I B U T O S ----------------

    private int id_GrupoPersonalizacion;
    private int id_Producto;
    private String nombre;

    private boolean obligatorio;
    private int cantidad_minima;
    private int cantidad_maxima;

    //------------------- C O N S T R U C T O R E S ----------------

    public Grupo_Personalizacion() {
    }

    public Grupo_Personalizacion(
            int id_GrupoPersonalizacion,
            int id_Producto,
            String nombre,
            boolean obligatorio,
            int cantidad_minima,
            int cantidad_maxima
    ) {

        this.id_GrupoPersonalizacion
                = id_GrupoPersonalizacion;

        this.id_Producto = id_Producto;

        this.nombre = nombre;

        this.obligatorio = obligatorio;

        this.cantidad_minima
                = cantidad_minima;

        this.cantidad_maxima
                = cantidad_maxima;
    }

    //------------------- G E T T E R S ----------------

    public int getId_GrupoPersonalizacion() {
        return id_GrupoPersonalizacion;
    }

    public int getId_Producto() {
        return id_Producto;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isObligatorio() {
        return obligatorio;
    }

    public int getCantidad_minima() {
        return cantidad_minima;
    }

    public int getCantidad_maxima() {
        return cantidad_maxima;
    }

    //------------------- S E T T E R S ----------------

    public void setId_GrupoPersonalizacion(
            int id_GrupoPersonalizacion
    ) {

        this.id_GrupoPersonalizacion
                = id_GrupoPersonalizacion;
    }

    public void setId_Producto(
            int id_Producto
    ) {

        this.id_Producto = id_Producto;
    }

    public void setNombre(
            String nombre
    ) {

        this.nombre = nombre;
    }

    public void setObligatorio(
            boolean obligatorio
    ) {

        this.obligatorio = obligatorio;
    }

    public void setCantidad_minima(
            int cantidad_minima
    ) {

        this.cantidad_minima
                = cantidad_minima;
    }

    public void setCantidad_maxima(
            int cantidad_maxima
    ) {

        this.cantidad_maxima
                = cantidad_maxima;
    }

    //------------------- T O   S T R I N G ----------------

    @Override
    public String toString() {

        return nombre;
    }
}
