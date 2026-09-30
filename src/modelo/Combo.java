//---------------------------------- C O M B O -----------------------
package modelo;

public class Combo {

    //------------------------ A T R I B U T O S ----------------
    private int id_Combo;
    private String nombre;
    private String descripcion;
    private String tipo;
    private double precio;
    private double precio_extra;
    private String imagen;
    private boolean estado;
    private Integer id_Subcategoria;

    //-------------------- C O N S T R U C T O R -----------------
    public Combo() {
    }

    public Combo(
            int id_Combo,
            String nombre,
            String descripcion,
            String tipo,
            double precio,
            double precio_extra,
            String imagen,
            boolean estado,
            Integer id_Subcategoria
    ) {

        this.id_Combo = id_Combo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.precio_extra = precio_extra;
        this.imagen = imagen;
        this.estado = estado;
        this.id_Subcategoria = id_Subcategoria;
    }

    //---------------------- G E T T E R S
    public int getId_Combo() {
        return id_Combo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public double getPrecio_extra() {
        return precio_extra;
    }

    public String getImagen() {
        return imagen;
    }

    public boolean isEstado() {
        return estado;
    }

    public Integer getId_Subcategoria() {
        return id_Subcategoria;
    }

    //---------------------- S E T T E R S
    public void setId_Combo(int id_Combo) {
        this.id_Combo = id_Combo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setPrecio_extra(double precio_extra) {
        this.precio_extra = precio_extra;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setId_Subcategoria(Integer id_Subcategoria) {
        this.id_Subcategoria = id_Subcategoria;
    }

    //---------------------- T O S T R I N G
    @Override
    public String toString() {
        return nombre;
    }
}
