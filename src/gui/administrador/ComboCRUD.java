//---------------------- C O M B O   C R U D ----------------------
package gui.administrador;

import gui.componentes.*;
import java.awt.*;

public class ComboCRUD extends VentanaCRUD {

    //------------------------ A T R I B U T O S ----------------

    //-------------- Campos de texto
    private CampoTexto Tnombre;
    private CampoTexto Tprecio;

    //-------------- Campos ComboBox
    private CampoComboBox<String> Ctipo;
    private CampoComboBox<String> Csubcategoria;

    //-------------- Área de texto
    private CampoArea Adescripcion;


    //------------------------ C O N S T R U C T O R ----------------
    public ComboCRUD() {

        super(
                "Administración de Combos"
        );

        //---------------------- TÍTULO
        setTituloCRUD(
                "Combo"
        );

        //---------------------- ID
        setTextoId(
                "ID_Combo"
        );

        //---------------------- CONFIGURACIÓN
        mostrarImagen(
                true
        );

        mostrarBotonDesactivar(
                true
        );

        //---------------------- CAMPOS
        inicializarCampos();

        //---------------------- TABLA
        // Las columnas coinciden con el SELECT de CrudAdministrador.recargar():
        // ahora se muestra el tipo del combo y su subcategoría, porque antes
        // esa información se guardaba en la base de datos pero no se veía.
        finalizarCRUD(
                new String[]{
                    "Id_Combo",
                    "Nombre",
                    "Descripción",
                    "Tipo",
                    "Subcategoría",
                    "Precio",
                    "Imagen",
                    "Estado"
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

        //---------------------- NOMBRE
        Tnombre = new CampoTexto();

        Tnombre.setPreferredSize(
                tamañoCampo
        );

        Tnombre.setMinimumSize(
                tamañoCampo
        );

        Tnombre.setMaximumSize(
                tamañoCampo
        );

        agregarCampoDerechaHorizontal(
                "Nombre",
                Tnombre,
                0,
                0
        );


        //---------------------- PRECIO
        Tprecio = new CampoTexto();

        Tprecio.setPreferredSize(
                tamañoCampo
        );

        Tprecio.setMinimumSize(
                tamañoCampo
        );

        Tprecio.setMaximumSize(
                tamañoCampo
        );

        agregarCampoDerechaHorizontal(
                "Precio",
                Tprecio,
                0,
                1
        );


        //---------------------- TIPO
        Ctipo = new CampoComboBox<>();

        Ctipo.addItem(
                "Complementario"
        );

        Ctipo.addItem(
                "Independiente"
        );

        Ctipo.setPreferredSize(
                tamañoCampo
        );

        Ctipo.setMinimumSize(
                tamañoCampo
        );

        Ctipo.setMaximumSize(
                tamañoCampo
        );

        agregarCampoDerechaHorizontal(
                "Tipo",
                Ctipo,
                1,
                0
        );


        //---------------------- SUBCATEGORÍA
        Csubcategoria = new CampoComboBox<>();

        Csubcategoria.setPreferredSize(
                tamañoCampo
        );

        Csubcategoria.setMinimumSize(
                tamañoCampo
        );

        Csubcategoria.setMaximumSize(
                tamañoCampo
        );

        agregarCampoDerechaHorizontal(
                "Subcategoría",
                Csubcategoria,
                1,
                1
        );


        //---------------------- DESCRIPCIÓN
        Adescripcion = new CampoArea();

        Dimension tamañoDescripcion = new Dimension(
                350,
                130
        );

        Adescripcion.setTamaño(
                350,
                130
        );

        Adescripcion.setMinimumSize(
                tamañoDescripcion
        );

        Adescripcion.setMaximumSize(
                tamañoDescripcion
        );

        agregarCampoDerechaHorizontal(
                "Descripción",
                Adescripcion,
                2,
                0
        );
    }


    //---------------------- G E T T E R S ----------------

    public CampoTexto getCampoNombre() {

        return Tnombre;
    }

    public CampoTexto getCampoPrecio() {

        return Tprecio;
    }

    public CampoComboBox<String> getCampoTipo() {

        return Ctipo;
    }

    public CampoComboBox<String> getCampoSubcategoria() {

        return Csubcategoria;
    }

    public CampoArea getCampoDescripcion() {

        return Adescripcion;
    }
}