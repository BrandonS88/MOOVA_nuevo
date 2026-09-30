//---------------------- G R U P O   P E R S O N A L I Z A C I O N   C R U D ----------------------
package gui.administrador;

import gui.componentes.*;
import java.awt.*;

public class GrupoPersonalizacionCRUD extends VentanaCRUD {

    //------------------------ A T R I B U T O S ----------------

    //-------------- Campos de texto
    private CampoTexto Tnombre;
    private CampoTexto TcantidadMinima;
    private CampoTexto TcantidadMaxima;

    //-------------- Campos ComboBox
    private CampoComboBox<String> Cproducto;
    private CampoComboBox<String> Cobligatorio;


    //------------------------ C O N S T R U C T O R ----------------
    public GrupoPersonalizacionCRUD() {

        super(
                "Administración de Grupos de Personalización"
        );

        //---------------------- TÍTULO
        setTituloCRUD(
                "Grupo Personalización"
        );

        //---------------------- ID
        setTextoId(
                "ID_GrupoPersonalizacion"
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
                    "Id_GrupoPersonalización",
                    "Producto",
                    "Nombre",
                    "Obligatorio",
                    "Cantidad mínima",
                    "Cantidad máxima"
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


        //---------------------- OBLIGATORIO
        Cobligatorio = new CampoComboBox<>();

        Cobligatorio.addItem(
                "Sí"
        );

        Cobligatorio.addItem(
                "No"
        );

        Cobligatorio.setPreferredSize(
                tamañoCampo
        );

        Cobligatorio.setMinimumSize(
                tamañoCampo
        );

        Cobligatorio.setMaximumSize(
                tamañoCampo
        );

        agregarCampo(
                "Obligatorio",
                Cobligatorio,
                6,
                2,
                6
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

        agregarCampo(
                "Nombre",
                Tnombre,
                0,
                4,
                6
        );


        //---------------------- CANTIDAD MÍNIMA
        TcantidadMinima = new CampoTexto();

        TcantidadMinima.setPreferredSize(
                tamañoCampo
        );

        TcantidadMinima.setMinimumSize(
                tamañoCampo
        );

        TcantidadMinima.setMaximumSize(
                tamañoCampo
        );

        agregarCampo(
                "Cantidad mínima",
                TcantidadMinima,
                6,
                4,
                6
        );


        //---------------------- CANTIDAD MÁXIMA
        TcantidadMaxima = new CampoTexto();

        TcantidadMaxima.setPreferredSize(
                tamañoCampo
        );

        TcantidadMaxima.setMinimumSize(
                tamañoCampo
        );

        TcantidadMaxima.setMaximumSize(
                tamañoCampo
        );

        agregarCampo(
                "Cantidad máxima",
                TcantidadMaxima,
                0,
                6,
                6
        );
    }


    //---------------------- G E T T E R S ----------------

    public CampoComboBox<String> getCampoProducto() {

        return Cproducto;
    }

    public CampoComboBox<String> getCampoObligatorio() {

        return Cobligatorio;
    }

    public CampoTexto getCampoNombre() {

        return Tnombre;
    }

    public CampoTexto getCampoCantidadMinima() {

        return TcantidadMinima;
    }

    public CampoTexto getCampoCantidadMaxima() {

        return TcantidadMaxima;
    }
}