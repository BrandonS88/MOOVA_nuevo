//---------------------- I N G R E D I E N T E ----------------------
package modelo;

import java.time.LocalDateTime;

public class Ingrediente {
    //--------------- ATRIBUTOS -----------------
    private int id_Ingrediente;
    private String nombre;
    private int cantidad;
    private int cantidad_minima;
    private LocalDateTime fecha_creacion;
    private LocalDateTime fecha_actualizacion;
    private String descripcion;
    private double precio;
    private String imagen;
    private boolean estado;

    //----------------- CONSTRUCTOR --------------

    public Ingrediente() {
    }

    public Ingrediente(int id_Ingrediente, String nombre, int cantidad, int cantidad_minima, LocalDateTime fecha_creacion, LocalDateTime fecha_actualizacion, String descripcion, double precio, String imagen, boolean estado) {
        this.id_Ingrediente = id_Ingrediente;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.cantidad_minima = cantidad_minima;
        this.fecha_creacion = fecha_creacion;
        this.fecha_actualizacion = fecha_actualizacion;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.estado = estado;
    }

    //------------- GETTERS Y SETTERS ---------------

    public int getId_Ingrediente() {
        return id_Ingrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getCantidad_minima() {
        return cantidad_minima;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public LocalDateTime getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setId_Ingrediente(int id_Ingrediente) {
        this.id_Ingrediente = id_Ingrediente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCantidad_minima(int cantidad_minima) {
        this.cantidad_minima = cantidad_minima;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setFecha_actualizacion(LocalDateTime fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    //----------------- TO STRING -----------
    @Override
    public String toString() {
        return nombre;
    }

}
