//---------------------- D E S C U E N T O   C R U D ----------------------
package gui.administrador;

import gui.componentes.*;
import java.awt.*;

public class DescuentoCRUD extends VentanaCRUD {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Campos de texto
    private CampoTexto Tnombre;
    private CampoTexto TcantidadMinima;
    private CampoTexto Tvalor;

    //-------------- Combobox
    private CampoComboBox<String> Ctipo;

    //-------------- Calendarios
    private Calendario Cinicio;
    private Calendario Cfin;

    //-------------- Área de texto
    private CampoArea Adescripcion;

    //------------------------ C O N S T R U C T O R ----------------
    public DescuentoCRUD() {

        super("Administración de Descuentos");
        
        //---------------------- TÍTULO
        setTituloCRUD("Descuento");
        
        //---------------------- ID
        setTextoId(
                "ID_Descuento"
        );

        //---------------------- CONFIGURACIÓN
        mostrarImagen(false);

        mostrarBotonDesactivar(true);

        //---------------------- CAMPOS
        inicializarCampos();

        //---------------------- TABLA
        finalizarCRUD(
                new String[]{
                    "id_Descuento",
                    "nombre",
                    "descripcion",
                    "valor",
                    "cantidad_minima",
                    "fecha_inicio",
                    "fecha_fin",
                    "estado",
                    "tipo"
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
                        180,
                        45
                )
        );

        agregarCampo(
                "Nombre",
                Tnombre,
                0,
                2,
                4
        );

        //---------------------- CANTIDAD MÍNIMA
        TcantidadMinima = new CampoTexto();

        TcantidadMinima.setPreferredSize(
                new Dimension(
                        180,
                        45
                )
        );

        agregarCampo(
                "Cantidad mínima",
                TcantidadMinima,
                4,
                2,
                4
        );

        //---------------------- VALOR
        Tvalor = new CampoTexto();

        Tvalor.setPreferredSize(
                new Dimension(
                        180,
                        45
                )
        );

        agregarCampo(
                "Valor",
                Tvalor,
                8,
                2,
                4
        );

        //---------------------- TIPO
        Ctipo = new CampoComboBox<>();

        Ctipo.setPreferredSize(
                new Dimension(
                        180,
                        45
                )
        );

        //---------------------- ENUM
        Ctipo.addItem(
                "Porcentaje"
        );

        Ctipo.addItem(
                "Monto"
        );

        agregarCampo(
                "Tipo",
                Ctipo,
                0,
                3,
                4
        );

        //---------------------- INICIO
        Cinicio = new Calendario();

        Cinicio.setTamaño(
                180,
                45
        );

        agregarCampo(
                "Inicio",
                Cinicio,
                4,
                3,
                4
        );

        //---------------------- FIN
        Cfin = new Calendario();

        Cfin.setTamaño(
                180,
                45
        );

        agregarCampo(
                "Fin",
                Cfin,
                8,
                3,
                4
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
                4,
                12
        );
    }

    //---------------------- G E T T E R S ----------------

    public CampoTexto getCampoNombre() {

        return Tnombre;
    }

    public CampoTexto getCampoCantidadMinima() {

        return TcantidadMinima;
    }

    public CampoTexto getCampoValor() {

        return Tvalor;
    }

    public CampoComboBox<String> getComboTipo() {

        return Ctipo;
    }

    public Calendario getCalendarioInicio() {

        return Cinicio;
    }

    public Calendario getCalendarioFin() {

        return Cfin;
    }

    public CampoArea getCampoDescripcion() {

        return Adescripcion;
    }
}