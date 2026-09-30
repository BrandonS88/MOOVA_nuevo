//---------------------- C A T E G O R I A   C R U D ----------------------
package gui.administrador;

import gui.componentes.*;
import java.awt.*;

public class CategoriaCRUD extends VentanaCRUD {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Campos
    private CampoTexto Tnombre;
    private CampoArea Adescripcion;
    
     //------------------------ C O N S T R U C T O R ----------------
    public CategoriaCRUD() {

        super("Administración de Categorías");

        //---------------------- TÍTULO
        setTituloCRUD("Categoría");

        //---------------------- ID
        setTextoId(
                "ID_Categoria"
        );

        //---------------------- CONFIGURACIÓN
        mostrarImagen(false);

        mostrarBotonDesactivar(true);

        //---------------------- CAMPOS
        inicializarCampos();

        //---------------------- TABLA
        finalizarCRUD(
                new String[]{
                    "id_Categoria",
                    "nombre",
                    "descripcion",
                    "estado"
                }
        );

        setVisible(true);
    }

    //------------------------ I N I C I A L I Z A R   C A M P O S ----------------
    private void inicializarCampos() {

        //---------------------- NOMBRE
        Tnombre = new CampoTexto();

        Tnombre.setPreferredSize(
                new Dimension(
                        250,
                        45
                )
        );

        agregarCampo(
                "Nombre",
                Tnombre,
                0,
                2,
                6
        );

        //---------------------- DESCRIPCIÓN
        Adescripcion = new CampoArea();

        Adescripcion.setTamaño(
                400,
                130
        );

        agregarCampo(
                "Descripción",
                Adescripcion,
                6,
                2,
                6
        );
    }

    //---------------------- G E T T E R S ----------------
    public CampoTexto getCampoNombre() {

        return Tnombre;
    }

    public CampoArea getCampoDescripcion() {

        return Adescripcion;
    }
}
