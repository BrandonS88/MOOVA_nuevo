//---------------------- P R O D U C T O   C R U D ----------------------
package gui.administrador;

import gui.componentes.*;
import java.awt.*;

public class ProductoCRUD extends VentanaCRUD {

    //------------------------ A T R I B U T O S ----------------

    //-------------- Campos de texto
    private CampoTexto Tnombre;
    private CampoTexto Tprecio;

    //-------------- Combobox
    private CampoComboBox<String> Csubcategoria;

    //-------------- Área de texto
    private CampoArea Adescripcion;

    //------------------------ C O N S T R U C T O R ----------------
    public ProductoCRUD() {

        super(
                "Administración de Productos"
        );
        
        //---------------------- TÍTULO
        setTituloCRUD("Producto");

        //---------------------- ID
        setTextoId(
                "ID_Producto"
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
        finalizarCRUD(
                new String[]{
                    "Id_producto",
                    "Nombre",
                    "Descripción",
                    "Precio",
                    "Imagen",
                    "Estado",
                    "Subcategoría"
                }
        );

        setVisible(
                true
        );
    }

    //------------------------ I N I C I A L I Z A R   C A M P O S ----------------
    private void inicializarCampos() {

        //---------------------- NOMBRE
        Tnombre = new CampoTexto();

        Dimension tamañoCampo = new Dimension(
                350,
                45
        );

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
                0
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

    public CampoComboBox<String> getComboSubcategoria() {

        return Csubcategoria;
    }

    public CampoArea getCampoDescripcion() {

        return Adescripcion;
    }
}