//---------------------------------- S U B C A T E G O R I A   M E N U -----------------------
package modelo;

import java.util.List;

public class SubcategoriaMenu {

    //------------------------  A T R I B U T O S ----------------
    private String nombre;
    private List<Producto> productos;

    //-------------------- C O N S T R U C T O R -----------------
    public SubcategoriaMenu(
            String nombre,
            List<Producto> productos
    ) {
        this.nombre = nombre;
        this.productos = productos;
    }

    //---------------------- G E T T E R S
    public String getNombre() {
        return nombre;
    }

    public List<Producto> getProductos() {
        return productos;
    }
}