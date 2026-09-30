//---------------------- M O V I M I E N T O _ I N V E N T A R I O ----------------------
package modelo;

import java.time.LocalDateTime;

public class Movimiento_Inventario {
    //------------------- ATRIBUTOS ----------------
    private int id_Movimiento;
    //Egreso o ingreso
    private int cantidad;
    //Cantidad que se tiene antes del egreso o ingreso del ingrediente
    private int cantidad_anterior;
    //TOTAL
    private int cantidad_total;
    private String motivo;
    private LocalDateTime fecha_movimiento;
    private String tipo;
    private int id_Ingrediente;
    private int id_Usuario;
    private int id_Venta;

    //------------------- CONSTRUCTOR -------------

    public Movimiento_Inventario() {
    }

    public Movimiento_Inventario(int id_Movimiento, int cantidad, int cantidad_anterior, int cantidad_total, String motivo, LocalDateTime fecha_movimiento, String tipo, int id_Ingrediente, int id_Usuario, int id_Venta) {
        this.id_Movimiento = id_Movimiento;
        this.cantidad = cantidad;
        this.cantidad_anterior = cantidad_anterior;
        this.cantidad_total = cantidad_total;
        this.motivo = motivo;
        this.fecha_movimiento = fecha_movimiento;
        this.tipo = tipo;
        this.id_Ingrediente = id_Ingrediente;
        this.id_Usuario = id_Usuario;
        this.id_Venta = id_Venta;
    }

    //--------------------- SETTERS Y GETTERS --------------------

    public int getId_Movimiento() {
        return id_Movimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getCantidad_anterior() {
        return cantidad_anterior;
    }

    public int getCantidad_total() {
        return cantidad_total;
    }

    public String getMotivo() {
        return motivo;
    }

    public LocalDateTime getFecha_movimiento() {
        return fecha_movimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public int getId_Ingrediente() {
        return id_Ingrediente;
    }

    public int getId_Usuario() {
        return id_Usuario;
    }

    public int getId_Venta() {
        return id_Venta;
    }

    public void setId_Movimiento(int id_Movimiento) {
        this.id_Movimiento = id_Movimiento;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCantidad_anterior(int cantidad_anterior) {
        this.cantidad_anterior = cantidad_anterior;
    }

    public void setCantidad_total(int cantidad_total) {
        this.cantidad_total = cantidad_total;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setFecha_movimiento(LocalDateTime fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setId_Ingrediente(int id_Ingrediente) {
        this.id_Ingrediente = id_Ingrediente;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public void setId_Venta(int id_Venta) {
        this.id_Venta = id_Venta;
    }

    //---------------- TO STRING ------------
    @Override
    public String toString() {
        return "Movimiento_Inventario{" + "id_Movimiento=" + id_Movimiento + ", cantidad=" + cantidad + ", cantidad_anterior=" + cantidad_anterior + ", cantidad_total=" + cantidad_total + ", motivo=" + motivo + ", fecha_movimiento=" + fecha_movimiento + ", tipo=" + tipo + ", id_Ingrediente=" + id_Ingrediente + ", id_Usuario=" + id_Usuario + ", id_Venta=" + id_Venta + '}';
    }

}
