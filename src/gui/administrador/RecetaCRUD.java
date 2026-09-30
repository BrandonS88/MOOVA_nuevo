//---------------------- R E C E T A   C R U D ----------------------
package gui.administrador;

import gui.componentes.*;
import java.awt.*;

public class RecetaCRUD extends VentanaCRUD {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Campos
    private CampoComboBox<String> Cproducto;
    private CampoComboBox<String> Cingrediente;
    private CampoTexto Tcantidad;

    //------------------------ C O N S T R U C T O R ----------------
    public RecetaCRUD() {

        super("Administración de Recetas");
        
        //---------------------- TÍTULO
        setTituloCRUD("Receta");

        //---------------------- ID
        setTextoId(
                "ID_Receta"
        );

        //---------------------- CONFIGURACIÓN
        mostrarImagen(false);

        mostrarBotonDesactivar(false);

        //---------------------- CAMPOS
        inicializarCampos();

        //---------------------- TABLA
        finalizarCRUD(
                new String[]{
                    "Id_receta",
                    "Cantidad",
                    "Producto",
                    "Ingrediente"
                }
        );

        setVisible(true);
    }

    //------------------------ I N I C I A L I Z A R   C A M P O S ----------------
    private void inicializarCampos() {

        //---------------------- PRODUCTO
        Cproducto = new CampoComboBox<>();

        Cproducto.setPreferredSize(
                new Dimension(
                        220,
                        45
                )
        );

        agregarCampo(
                "Producto",
                Cproducto,
                0,
                2,
                4
        );

        //---------------------- INGREDIENTE
        Cingrediente = new CampoComboBox<>();

        Cingrediente.setPreferredSize(
                new Dimension(
                        220,
                        45
                )
        );

        agregarCampo(
                "Ingrediente",
                Cingrediente,
                4,
                2,
                4
        );

        //---------------------- CANTIDAD
        Tcantidad = new CampoTexto();

        Tcantidad.setPreferredSize(
                new Dimension(
                        180,
                        45
                )
        );

        agregarCampo(
                "Cantidad",
                Tcantidad,
                8,
                2,
                4
        );
    }

    //---------------------- G E T T E R S ----------------

    public CampoComboBox<String> getComboProducto() {

        return Cproducto;
    }

    public CampoComboBox<String> getComboIngrediente() {

        return Cingrediente;
    }

    public CampoTexto getCampoCantidad() {

        return Tcantidad;
    }
}