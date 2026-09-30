//---------------------- D E T A L L E _ V E N T A ----------------------
package modelo;

public class Detalle_Venta {
    //------------------- ATRIBUTOS ----------------
    private int id_DetalleVenta;
    private int cantidad;
    private double precio_unitario;
    private double subtotal;
    private double impuesto;
    private double descuento;
    private double monto_descuento;
    private int id_Venta;

    // Estas tres columnas permiten NULL en la tabla, y por eso se declaran
    // como Integer y no como int.
    //
    // La tabla tiene una restricción (CK_DetalleVenta_ProductoOCombo) que
    // obliga a que un detalle tenga un producto O un combo, pero nunca
    // ambos. Entonces una línea de producto llega con id_Combo = NULL, y una
    // línea de combo llega con id_Producto = NULL. Además, una venta sin
    // descuentoapplied tiene id_Descuento = NULL.
    //
    // Si se declararan como int, al leer una fila con NULL el driver
    // devuelve null y Java hace unboxing automático a int, lo que provoca un
    // NullPointerException al abrir cualquier detalle de venta.
    private Integer id_Producto;
    private Integer id_Combo;
    private Integer id_Descuento;

    //---------------------- CONSTRUCTOR -------------

    public Detalle_Venta() {
    }

    public Detalle_Venta(int id_DetalleVenta, int cantidad, double precio_unitario, double subtotal, double impuesto, double descuento, double monto_descuento, int id_Venta, Integer id_Producto, Integer id_Combo, Integer id_Descuento) {
        this.id_DetalleVenta = id_DetalleVenta;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
        this.impuesto = impuesto;
        this.descuento = descuento;
        this.monto_descuento = monto_descuento;
        this.id_Venta = id_Venta;
        this.id_Producto = id_Producto;
        this.id_Combo = id_Combo;
        this.id_Descuento = id_Descuento;
    }

    //------------------------ GETTERS Y SETTERS -----------

    public int getId_DetalleVenta() {
        return id_DetalleVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getMonto_descuento() {
        return monto_descuento;
    }

    public int getId_Venta() {
        return id_Venta;
    }

    public Integer getId_Producto() {
        return id_Producto;
    }

    public Integer getId_Combo() {
        return id_Combo;
    }

    public Integer getId_Descuento() {
        return id_Descuento;
    }

    public void setId_DetalleVenta(int id_DetalleVenta) {
        this.id_DetalleVenta = id_DetalleVenta;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public void setMonto_descuento(double monto_descuento) {
        this.monto_descuento = monto_descuento;
    }

    public void setId_Venta(int id_Venta) {
        this.id_Venta = id_Venta;
    }

    public void setId_Producto(Integer id_Producto) {
        this.id_Producto = id_Producto;
    }

    public void setId_Combo(Integer id_Combo) {
        this.id_Combo = id_Combo;
    }

    public void setId_Descuento(Integer id_Descuento) {
        this.id_Descuento = id_Descuento;
    }

    //------------------- TO STRING --------------

    @Override
    public String toString() {
        return "Detalle_Venta{" + "id_DetalleVenta=" + id_DetalleVenta + ", cantidad=" + cantidad + ", precio_unitario=" + precio_unitario + ", subtotal=" + subtotal + ", impuesto=" + impuesto + ", descuento=" + descuento + ", monto_descuento=" + monto_descuento + ", id_Venta=" + id_Venta + ", id_Producto=" + id_Producto + ", id_Combo=" + id_Combo + ", id_Descuento=" + id_Descuento + '}';
    }

}
