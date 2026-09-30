//---------------------- D E S C U E N T O ----------------------
package modelo;

import java.time.LocalDateTime;

public class Descuento {
    //------------------- ATRIBUTOS ----------------
    private int id_Descuento;
    private String nombre;
    private String descripcion;
    private double valor;
    private int cantidad_minima;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_fin;
    private boolean estado;
    private String tipo;

    //------------------- CONSTRUCTOR -------------

    public Descuento() {
    }

    public Descuento(int id_Descuento, String nombre, String descripcion, double valor, int cantidad_minima, LocalDateTime fecha_inicio, LocalDateTime fecha_fin, boolean estado, String tipo) {
        this.id_Descuento = id_Descuento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valor = valor;
        this.cantidad_minima = cantidad_minima;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.estado = estado;
        this.tipo = tipo;
    }

    //--------------------- SETTERS Y GETTERS --------------------

    public int getId_Descuento() {
        return id_Descuento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getValor() {
        return valor;
    }

    public int getCantidad_minima() {
        return cantidad_minima;
    }

    public LocalDateTime getFecha_inicio() {
        return fecha_inicio;
    }

    public LocalDateTime getFecha_fin() {
        return fecha_fin;
    }

    public boolean isEstado() {
        return estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setId_Descuento(int id_Descuento) {
        this.id_Descuento = id_Descuento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setCantidad_minima(int cantidad_minima) {
        this.cantidad_minima = cantidad_minima;
    }

    public void setFecha_inicio(LocalDateTime fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFecha_fin(LocalDateTime fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    //---------------- TO STRING ------------
    @Override
    public String toString() {
        return nombre;
    }

}
