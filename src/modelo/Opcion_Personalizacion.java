//---------------------- O P C I O N   P E R S O N A L I Z A C I O N ----------------------
package modelo;

public class Opcion_Personalizacion {

    //--------------- ATRIBUTOS -----------------
    private int id_OpcionPersonalizacion;
    private int id_GrupoPersonalizacion;
    private int id_Ingrediente;
    private double precio_extra;
    private boolean es_predeterminada;
    private int cantidadBaseReceta;

    // Ingrediente relacionado.
    // Se obtiene mediante JOIN en OpcionPersonalizacionDAO.
    private Ingrediente ingrediente;

    //----------------- CONSTRUCTOR --------------
    public Opcion_Personalizacion() {
    }

    public Opcion_Personalizacion(
            int id_OpcionPersonalizacion,
            int id_GrupoPersonalizacion,
            int id_Ingrediente,
            double precio_extra,
            boolean es_predeterminada
    ) {

        this.id_OpcionPersonalizacion = id_OpcionPersonalizacion;
        this.id_GrupoPersonalizacion = id_GrupoPersonalizacion;
        this.id_Ingrediente = id_Ingrediente;
        this.precio_extra = precio_extra;
        this.es_predeterminada = es_predeterminada;
    }

    //------------- GETTERS Y SETTERS ---------------
    public int getId_OpcionPersonalizacion() {
        return id_OpcionPersonalizacion;
    }

    public void setId_OpcionPersonalizacion(int id_OpcionPersonalizacion) {
        this.id_OpcionPersonalizacion = id_OpcionPersonalizacion;
    }

    public int getId_GrupoPersonalizacion() {
        return id_GrupoPersonalizacion;
    }

    public void setId_GrupoPersonalizacion(int id_GrupoPersonalizacion) {
        this.id_GrupoPersonalizacion = id_GrupoPersonalizacion;
    }

    public int getId_Ingrediente() {
        return id_Ingrediente;
    }

    public void setId_Ingrediente(int id_Ingrediente) {
        this.id_Ingrediente = id_Ingrediente;
    }

    public double getPrecio_extra() {
        return precio_extra;
    }

    public int getCantidadBaseReceta() {
        return cantidadBaseReceta;
    }

    public void setCantidadBaseReceta(
            int cantidadBaseReceta
    ) {
        this.cantidadBaseReceta
                = cantidadBaseReceta;
    }

    public void setPrecio_extra(double precio_extra) {
        this.precio_extra = precio_extra;
    }

    public boolean isEs_predeterminada() {
        return es_predeterminada;
    }

    public void setEs_predeterminada(boolean es_predeterminada) {
        this.es_predeterminada = es_predeterminada;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }
}
