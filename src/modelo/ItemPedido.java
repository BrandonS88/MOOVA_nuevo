//---------------------------------- I T E M   P E D I D O -----------------------
package modelo;

import java.util.ArrayList;
import java.util.List;

public class ItemPedido {

    //------------------------ A T R I B U T O S ----------------
    private Producto productoBase;
    private Combo combo;

    private List<SeleccionCombo> selecciones;
    private List<SeleccionPersonalizacion> seleccionesPersonalizacion;

    //-------------------- C O N S T R U C T O R -----------------
    public ItemPedido(
            Producto productoBase,
            Combo combo,
            List<SeleccionCombo> selecciones
    ) {

        this.productoBase = productoBase;
        this.combo = combo;

        this.selecciones
                = new ArrayList<>(
                        selecciones
                );

        this.seleccionesPersonalizacion
                = new ArrayList<>();
    }

//---------------------- A G R E G A R   P E R S O N A L I Z A C I O N ----------------
    public void agregarPersonalizacion(
            SeleccionPersonalizacion seleccion
    ) {

        seleccionesPersonalizacion.add(
                seleccion
        );
    }

//---------------------- L I M P I A R   P E R S O N A L I Z A C I O N E S ----------------
    public void limpiarPersonalizaciones() {

        seleccionesPersonalizacion.clear();
    }

    //---------------------- L I M P I A R   P E R S O N A L I Z A C I O N E S   D E   P R O D U C T O ----------------
    public void limpiarPersonalizacionesDeProducto(
            int idProducto
    ) {

        if (seleccionesPersonalizacion == null) {
            return;
        }

        seleccionesPersonalizacion.removeIf(
                seleccion
                -> seleccion.getGrupo() != null
                && seleccion.getGrupo().getId_Producto()
                == idProducto
        );
    }

//---------------------- O B T E N E R   P E R S O N A L I Z A C I O N E S ----------------
    public List<SeleccionPersonalizacion>
            getSeleccionesPersonalizacion() {

        return seleccionesPersonalizacion;
    }

    //---------------------- G E T T E R S ----------------
    public Producto getProductoBase() {
        return productoBase;
    }

    public Combo getCombo() {
        return combo;
    }

    public List<SeleccionCombo> getSelecciones() {
        return selecciones;
    }

    //---------------------- E S   C O M B O ----------------
    public boolean esCombo() {

        return combo != null;
    }

    //---------------------- P R E C I O ----------------
    public double obtenerPrecio() {

        //---------------------- PRODUCTO NORMAL
        if (combo == null) {

            return productoBase.getPrecio();
        }

        double total;

        //---------------------- COMBO COMPLEMENTARIO
        if ("Complementario".equals(
                combo.getTipo()
        )) {

            total = productoBase.getPrecio()
                    + combo.getPrecio_extra();

            //---------------------- COMBO INDEPENDIENTE
        } else {

            total = combo.getPrecio();
        }

        //---------------------- EXTRAS DE OPCIONES
        for (SeleccionCombo seleccion
                : selecciones) {

            total += seleccion
                    .getOpcion()
                    .getPrecio_extra();
        }

        //---------------------- P R E C I O   P E R S O N A L I Z A D O ----------------
        for (SeleccionPersonalizacion seleccion
                : seleccionesPersonalizacion) {

            total += seleccion.getPrecioTotal();
        }

        return total;
    }
}
