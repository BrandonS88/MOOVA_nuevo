//---------------------- O P C I O N   C O M B O   C R U D ----------------------
package gui.administrador;

import gui.componentes.*;
import java.awt.*;

public class OpcionComboCRUD extends VentanaCRUD {

    //------------------------ A T R I B U T O S ----------------

    //-------------- Campos de texto
    private CampoTexto TprecioExtra;

    //-------------- Campos ComboBox
    private CampoComboBox<String> CgrupoCombo;
    private CampoComboBox<String> Cproducto;


    //------------------------ C O N S T R U C T O R ----------------
    public OpcionComboCRUD() {

        super(
                "Administración de Opciones de Combos"
        );

        //---------------------- TÍTULO
        setTituloCRUD(
                "Opción Combo"
        );

        //---------------------- ID
        setTextoId(
                "ID_OpcionCombo"
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
                    "Id_OpcionCombo",
                    "Precio extra",
                    "Grupo Combo",
                    "Producto"
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


        //---------------------- GRUPO COMBO
        CgrupoCombo = new CampoComboBox<>();

        CgrupoCombo.setPreferredSize(
                tamañoCampo
        );

        CgrupoCombo.setMinimumSize(
                tamañoCampo
        );

        CgrupoCombo.setMaximumSize(
                tamañoCampo
        );

        agregarCampo(
                "Grupo Combo",
                CgrupoCombo,
                0,
                2,
                6
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
                6,
                2,
                6
        );


        //---------------------- PRECIO EXTRA
        TprecioExtra = new CampoTexto();

        TprecioExtra.setPreferredSize(
                tamañoCampo
        );

        TprecioExtra.setMinimumSize(
                tamañoCampo
        );

        TprecioExtra.setMaximumSize(
                tamañoCampo
        );

        agregarCampo(
                "Precio extra",
                TprecioExtra,
                0,
                4,
                6
        );
    }


    //---------------------- G E T T E R S ----------------

    public CampoComboBox<String> getCampoGrupoCombo() {

        return CgrupoCombo;
    }

    public CampoComboBox<String> getCampoProducto() {

        return Cproducto;
    }

    public CampoTexto getCampoPrecioExtra() {

        return TprecioExtra;
    }
}