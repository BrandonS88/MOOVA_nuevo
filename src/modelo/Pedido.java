//---------------------------------- P E D I D O -----------------------
package modelo;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    //------------------------ A T R I B U T O S ----------------
    private List<ItemPedido> items;

    //-------------------- C O N S T R U C T O R -----------------
    public Pedido() {

        items = new ArrayList<>();
    }

    //---------------------- A G R E G A R
    public void agregarItem(
            ItemPedido item
    ) {

        items.add(
                item
        );
    }

    //---------------------- O B T E N E R
    public List<ItemPedido> getItems() {

        return items;
    }

    //---------------------- P R O D U C T O S   V I S U A L E S
    public List<Producto> obtenerProductos() {

        List<Producto> productos
                = new ArrayList<>();

        for (ItemPedido item
                : items) {

            if (item.getProductoBase() != null) {

                productos.add(
                        item.getProductoBase()
                );
            }

            for (SeleccionCombo seleccion
                    : item.getSelecciones()) {

                productos.add(
                        seleccion.getProducto()
                );
            }
        }

        return productos;
    }
}