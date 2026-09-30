//---------------------------------- S E L E C C I Ó N   C O M B O -----------------------
package modelo;

public class SeleccionCombo {

    //------------------------ A T R I B U T O S ----------------
    private Grupo_Combo grupo;
    private Producto producto;
    private Opcion_Combo opcion;

    //-------------------- C O N S T R U C T O R -----------------
    public SeleccionCombo(
            Grupo_Combo grupo,
            Producto producto,
            Opcion_Combo opcion
    ) {

        this.grupo = grupo;
        this.producto = producto;
        this.opcion = opcion;
    }

    //---------------------- G E T T E R S ----------------

    public Grupo_Combo getGrupo() {
        return grupo;
    }

    public Producto getProducto() {
        return producto;
    }

    public Opcion_Combo getOpcion() {
        return opcion;
    }
}