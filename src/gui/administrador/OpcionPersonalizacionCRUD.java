//---------------------- O P C I O N   P E R S O N A L I Z A C I O N   C R U D ----------------------
package gui.administrador;

import gui.componentes.*;
import java.awt.*;

public class OpcionPersonalizacionCRUD extends VentanaCRUD {

    //------------------------ A T R I B U T O S ----------------

    //-------------- Campo de texto
    private CampoTexto TprecioExtra;

    //-------------- Campos ComboBox
    private CampoComboBox<String> CgrupoPersonalizacion;
    private CampoComboBox<String> Cingrediente;
    private CampoComboBox<String> CesPredeterminada;


    //------------------------ C O N S T R U C T O R ----------------
    public OpcionPersonalizacionCRUD() {

        super(
                "Administración de Opciones de Personalización"
        );

        //---------------------- TÍTULO
        setTituloCRUD(
                "Opción Personalización"
        );

        //---------------------- ID
        setTextoId(
                "ID_OpcionPersonalizacion"
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
                    "id_OpcionPersonalizacion",
                    "id_GrupoPersonalizacion",
                    "id_Ingrediente",
                    "precio_extra",
                    "es_predeterminada"
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


        //---------------------- GRUPO PERSONALIZACIÓN
        CgrupoPersonalizacion = new CampoComboBox<>();

        CgrupoPersonalizacion.setPreferredSize(
                tamañoCampo
        );

        CgrupoPersonalizacion.setMinimumSize(
                tamañoCampo
        );

        CgrupoPersonalizacion.setMaximumSize(
                tamañoCampo
        );

        agregarCampo(
                "Grupo Personalización",
                CgrupoPersonalizacion,
                0,
                2,
                6
        );


        //---------------------- INGREDIENTE
        Cingrediente = new CampoComboBox<>();

        Cingrediente.setPreferredSize(
                tamañoCampo
        );

        Cingrediente.setMinimumSize(
                tamañoCampo
        );

        Cingrediente.setMaximumSize(
                tamañoCampo
        );

        agregarCampo(
                "Ingrediente",
                Cingrediente,
                6,
                2,
                6
        );


        //---------------------- ES PREDETERMINADA
        CesPredeterminada = new CampoComboBox<>();

        CesPredeterminada.addItem(
                "Sí"
        );

        CesPredeterminada.addItem(
                "No"
        );

        CesPredeterminada.setPreferredSize(
                tamañoCampo
        );

        CesPredeterminada.setMinimumSize(
                tamañoCampo
        );

        CesPredeterminada.setMaximumSize(
                tamañoCampo
        );

        agregarCampo(
                "Es predeterminada",
                CesPredeterminada,
                0,
                4,
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
                6,
                4,
                6
        );
    }


    //---------------------- G E T T E R S ----------------

    public CampoComboBox<String> getCampoGrupoPersonalizacion() {

        return CgrupoPersonalizacion;
    }

    public CampoComboBox<String> getCampoIngrediente() {

        return Cingrediente;
    }

    public CampoComboBox<String> getCampoEsPredeterminada() {

        return CesPredeterminada;
    }

    public CampoTexto getCampoPrecioExtra() {

        return TprecioExtra;
    }
}