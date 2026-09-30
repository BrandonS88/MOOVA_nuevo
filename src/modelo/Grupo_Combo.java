//---------------------------------- G R U P O   C O M B O -----------------------
package modelo;

public class Grupo_Combo {

    //------------------------ A T R I B U T O S ----------------
    private int id_GrupoCombo;
    private int id_Combo;
    private String nombre;
    private boolean obligatorio;
    private int cantidad_minima;
    private int cantidad_maxima;

    //-------------------- C O N S T R U C T O R -----------------
    public Grupo_Combo() {
    }

    public Grupo_Combo(
            int id_GrupoCombo,
            int id_Combo,
            String nombre,
            boolean obligatorio,
            int cantidad_minima,
            int cantidad_maxima
    ) {

        this.id_GrupoCombo = id_GrupoCombo;
        this.id_Combo = id_Combo;
        this.nombre = nombre;
        this.obligatorio = obligatorio;
        this.cantidad_minima = cantidad_minima;
        this.cantidad_maxima = cantidad_maxima;
    }

    //---------------------- G E T T E R S
    public int getId_GrupoCombo() {
        return id_GrupoCombo;
    }

    public int getId_Combo() {
        return id_Combo;
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

    //---------------------- S E T T E R S
    public void setId_GrupoCombo(int id_GrupoCombo) {
        this.id_GrupoCombo = id_GrupoCombo;
    }

    public void setId_Combo(int id_Combo) {
        this.id_Combo = id_Combo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public void setCantidad_minima(int cantidad_minima) {
        this.cantidad_minima = cantidad_minima;
    }

    public void setCantidad_maxima(int cantidad_maxima) {
        this.cantidad_maxima = cantidad_maxima;
    }

    //---------------------- T O S T R I N G
    @Override
    public String toString() {
        return nombre;
    }
}
