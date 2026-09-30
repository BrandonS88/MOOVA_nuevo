//---------------------- I N C I D E N T E ----------------------
package logica;

import java.time.LocalDateTime;

public class logicaIncidente {
    //---------------- ATRIBUTOS ----------------
    private int id_Incidente;
    private String titulo;
    private String descripcion;
    private LocalDateTime fecha_reporte;
    private LocalDateTime fecha_resolucion;
    private String solucion;
    private String tipo;
    private String prioridad;
    private String estado;
    private int id_Usuario;

    // Nullable: un incidente puede no estar ligado a un ingrediente
    // o a un producto (ver CK de la tabla).
    private Integer id_Ingrediente;
    private Integer id_Producto;

    //--------------- CONSTRUCTOR ---------------

    public logicaIncidente() {
    }

    public logicaIncidente(
            int id_Incidente,
            String titulo,
            String descripcion,
            LocalDateTime fecha_reporte,
            LocalDateTime fecha_resolucion,
            String solucion,
            String tipo,
            String prioridad,
            String estado,
            int id_Usuario,
            Integer id_Ingrediente,
            Integer id_Producto
    ) {
        this.id_Incidente = id_Incidente;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha_reporte = fecha_reporte;
        this.fecha_resolucion = fecha_resolucion;
        this.solucion = solucion;
        this.tipo = tipo;
        this.prioridad = prioridad;
        this.estado = estado;
        this.id_Usuario = id_Usuario;
        this.id_Ingrediente = id_Ingrediente;
        this.id_Producto = id_Producto;
    }

    //---------------- SETTERS Y GETTERS --------------

    public int getId_Incidente() {
        return id_Incidente;
    }

    public void setId_Incidente(int id_Incidente) {
        this.id_Incidente = id_Incidente;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha_reporte() {
        return fecha_reporte;
    }

    public void setFecha_reporte(LocalDateTime fecha_reporte) {
        this.fecha_reporte = fecha_reporte;
    }

    public LocalDateTime getFecha_resolucion() {
        return fecha_resolucion;
    }

    public void setFecha_resolucion(LocalDateTime fecha_resolucion) {
        this.fecha_resolucion = fecha_resolucion;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public Integer getId_Ingrediente() {
        return id_Ingrediente;
    }

    public void setId_Ingrediente(Integer id_Ingrediente) {
        this.id_Ingrediente = id_Ingrediente;
    }

    public Integer getId_Producto() {
        return id_Producto;
    }

    public void setId_Producto(Integer id_Producto) {
        this.id_Producto = id_Producto;
    }

    //----------------- TO STRING --------------------
    @Override
    public String toString() {
        return titulo;
    }
}