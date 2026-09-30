//---------------------- S U B C A T E G O R I A ----------------------
package modelo;

public class Subcategoria {

    //------------------- ATRIBUTOS ----------------
    private int id_Subcategoria;
    private int id_Categoria;
    private String nombre;
    private String descripcion;
    private boolean estado;

    //------------------- CONSTRUCTOR -------------
    public Subcategoria() {
    }

    public Subcategoria(
            int id_Subcategoria,
            int id_Categoria,
            String nombre,
            String descripcion,
            boolean estado
    ) {
        this.id_Subcategoria = id_Subcategoria;
        this.id_Categoria = id_Categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    //--------------------- SETTERS Y GETTERS --------------------

    public int getId_Subcategoria() {
        return id_Subcategoria;
    }

    public void setId_Subcategoria(int id_Subcategoria) {
        this.id_Subcategoria = id_Subcategoria;
    }

    public int getId_Categoria() {
        return id_Categoria;
    }

    public void setId_Categoria(int id_Categoria) {
        this.id_Categoria = id_Categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    //---------------- TO STRING ------------

    @Override
    public String toString() {
        return nombre;
    }
}