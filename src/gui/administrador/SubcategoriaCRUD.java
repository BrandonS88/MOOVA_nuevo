//---------------------- S U B C A T E G O R I A   C R U D ----------------------
package gui.administrador;

import gui.componentes.*;
import java.awt.*;

public class SubcategoriaCRUD extends VentanaCRUD {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Campos
    private CampoComboBox<String> Ccategoria;
    private CampoTexto Tnombre;
    private CampoArea Adescripcion;

    //------------------------ C O N S T R U C T O R ----------------
    public SubcategoriaCRUD() {

        super("Administración de Subcategorías");
        
        //---------------------- TÍTULO
        setTituloCRUD("Subcategoría");
        
        //---------------------- ID
        setTextoId(
                "ID_Subcategoria"
        );

        //---------------------- CONFIGURACIÓN
        mostrarImagen(false);

        mostrarBotonDesactivar(true);

        //---------------------- CAMPOS
        inicializarCampos();

        //---------------------- TABLA
        finalizarCRUD(
                new String[]{
                    "Id_subcategoría",
                    "Categoría",
                    "Nombre",
                    "Descripción",
                    "Estado"
                }
        );

        setVisible(true);
    }

    //------------------------ I N I C I A L I Z A R   C A M P O S ----------------
    private void inicializarCampos() {

        //---------------------- CATEGORÍA
        Ccategoria = new CampoComboBox<>();

        Ccategoria.setPreferredSize(
                new Dimension(
                        220,
                        45
                )
        );

        //---------------------- OPCIONES
        Ccategoria.addItem(
                "Hamburguesas"
        );

        Ccategoria.addItem(
                "Bebidas"
        );

        Ccategoria.addItem(
                "Postres"
        );

        Ccategoria.addItem(
                "Antojos"
        );

        Ccategoria.addItem(
                "Cajita Feliz"
        );

        agregarCampo(
                "Categoría",
                Ccategoria,
                0,
                2,
                6
        );

        //---------------------- NOMBRE
        Tnombre = new CampoTexto();

        Tnombre.setPreferredSize(
                new Dimension(
                        220,
                        45
                )
        );

        agregarCampo(
                "Nombre",
                Tnombre,
                6,
                2,
                6
        );

        //---------------------- DESCRIPCIÓN
        Adescripcion = new CampoArea();

        Adescripcion.setTamaño(
                500,
                130
        );

        agregarCampo(
                "Descripción",
                Adescripcion,
                0,
                3,
                12
        );
    }

    //---------------------- G E T T E R S ----------------

    public CampoComboBox<String> getComboCategoria() {

        return Ccategoria;
    }

    public CampoTexto getCampoNombre() {

        return Tnombre;
    }

    public CampoArea getCampoDescripcion() {

        return Adescripcion;
    }
}