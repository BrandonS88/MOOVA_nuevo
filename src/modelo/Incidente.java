//---------------------- I N C I D E N T E ----------------------
package modelo;

import java.time.LocalDateTime;

public class Incidente {
    //------------------- ATRIBUTOS ----------------
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

    // Entidades opcionalmente involucradas en el incidente.
    // Se declaran como Integer (y no int) porque en la tabla Incidente
    // estas dos columnas permiten NULL: un reporte puede ser de equipo
    // o de sistema, sin ingrediente ni producto de por medio.
    private Integer id_Ingrediente;
    private Integer id_Producto;

    //------------------- CONSTRUCTOR -------------

    public Incidente() {
    }

    public Incidente(int id_Incidente, String titulo, String descripcion, LocalDateTime fecha_reporte, LocalDateTime fecha_resolucion, String solucion, String tipo, String prioridad, String estado, int id_Usuario) {
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
    }

    //--------------------- SETTERS Y GETTERS --------------------

    public int getId_Incidente() {
        return id_Incidente;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFecha_reporte() {
        return fecha_reporte;
    }

    public LocalDateTime getFecha_resolucion() {
        return fecha_resolucion;
    }

    public String getSolucion() {
        return solucion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public int getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Incidente(int id_Incidente) {
        this.id_Incidente = id_Incidente;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha_reporte(LocalDateTime fecha_reporte) {
        this.fecha_reporte = fecha_reporte;
    }

    public void setFecha_resolucion(LocalDateTime fecha_resolucion) {
        this.fecha_resolucion = fecha_resolucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    //---------------- TO STRING ------------
    @Override
    public String toString() {
        return "Incidente{" + "id_Incidente=" + id_Incidente + ", titulo=" + titulo + ", tipo=" + tipo + ", prioridad=" + prioridad + ", estado=" + estado + ", id_Usuario=" + id_Usuario + '}';
    }

    public void setId_Ingrediente(Integer id_Ingrediente) {
        this.id_Ingrediente = id_Ingrediente;
    }

    public Integer getId_Ingrediente() {
        return id_Ingrediente;
    }

    public void setId_Producto(Integer id_Producto) {
        this.id_Producto = id_Producto;
    }

    public Integer getId_Producto() {
        return id_Producto;
    }

}
