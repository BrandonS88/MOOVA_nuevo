//---------------------- P R O D U C T O   C O M B O   C R U D ----------------------
package gui.administrador;

import gui.componentes.*;
import java.awt.*;

public class ProductoComboCRUD extends VentanaCRUD {

    //------------------------ A T R I B U T O S ----------------

    //-------------- Campos ComboBox
    private CampoComboBox<String> Cproducto;
    private CampoComboBox<String> Ccombo;


    //------------------------ C O N S T R U C T O R ----------------
    public ProductoComboCRUD() {

        super(
                "Administración de Productos Combo"
        );

        //---------------------- TÍTULO
        setTituloCRUD(
                "Producto Combo"
        );

        //---------------------- ID
        setTextoId(
                "ID_ProductoCombo"
        );

        //---------------------- CONFIGURACIÓN
        mostrarImagen(
                false
        );

        mostrarBotonDesactivar(
                false
        );

        //---------------------- CAMPOS
        inicializarCampos();

        //---------------------- TABLA
        finalizarCRUD(
                new String[]{
                    "Id_ProductoCombo",
                    "Producto",
                    "Combo"
                }
        );

        setVisible(
                true
        );
    }


    //------------------------ I N I C I A L I Z A R   C A M P O S ----------------
    private void inicializarCampos() {

        //---------------------- TAMAÑO GENERAL
        Dimension tamañoCampo = new Dimension(
                350,
                45
        );


        //---------------------- PRODUCTO
        Cproducto = new CampoComboBox<>();

        Cproducto.setPreferredSize(
                tamañoCampo
        );

        Cproducto.setMinimumSize(
                tamañoCampo
        );

        Cproducto.setMaximumSize(
                tamañoCampo
        );

        agregarCampo(
                "Producto",
                Cproducto,
                0,
                2,
                6
        );


        //---------------------- COMBO
        Ccombo = new CampoComboBox<>();

        Ccombo.setPreferredSize(
                tamañoCampo
        );

        Ccombo.setMinimumSize(
                tamañoCampo
        );

        Ccombo.setMaximumSize(
                tamañoCampo
        );

        agregarCampo(
                "Combo",
                Ccombo,
                6,
                2,
                6
        );
    }


    //---------------------- G E T T E R S ----------------

    public CampoComboBox<String> getCampoProducto() {

        return Cproducto;
    }

    public CampoComboBox<String> getCampoCombo() {

        return Ccombo;
    }
}