//---------------------- M O V I M I E N T O   I N V E N T A R I O   C R U D ----------------------
package gui.administrador;

import gui.componentes.*;
import java.awt.*;

public class MovimientoInventarioCRUD extends VentanaCRUD {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Campos
    private CampoComboBox<String> Ctipo;
    private CampoTexto Tcantidad;
    private CampoTexto TcantidadActual;
    private CampoArea Amotivo;

    //-------------- Botón
    private Boton Bguardar;


    //------------------------ C O N S T R U C T O R ----------------
    public MovimientoInventarioCRUD() {

        super("Movimiento de Inventario");

        //---------------------- TÍTULO
        setTituloCRUD(
                "Inventario"
        );

        //---------------------- ID
        setTextoId(
                "ID_Inventario"
        );

        //---------------------- CONFIGURACIÓN
        mostrarImagen(false);

        mostrarBotones(false);

        mostrarBotonDesactivar(false);

        //---------------------- CAMPOS
        inicializarCampos();

        //---------------------- BOTÓN
        inicializarBotonGuardar();

        //---------------------- TABLA
        // Las columnas deben coincidir con el SELECT de
        // CrudAdministrador.recargar(): ahora muestran el nombre del
        // ingrediente y del usuario en vez de solo sus ids, que no
        // significarían nada para quien está leyendo.
        finalizarCRUD(
                new String[]{
                    "id_Movimiento",
                    "Ingrediente",
                    "Tipo",
                    "Cantidad",
                    "Anterior",
                    "Total",
                    "Motivo",
                    "Fecha",
                    "Usuario",
                    "Venta"
                }
        );

        setVisible(true);
    }


    //------------------------ I N I C I A L I Z A R   C A M P O S ----------------
    private void inicializarCampos() {

        //---------------------- TIPO DE MOVIMIENTO
        Ctipo = new CampoComboBox<>();

        Ctipo.addItem("Ingreso");
        Ctipo.addItem("Salida");
        Ctipo.addItem("Venta");
        Ctipo.addItem("Merma");
        Ctipo.addItem("Ajuste");
        Ctipo.addItem("Devolucion");

        agregarCampo(
                "Tipo de movimiento",
                Ctipo,
                0,
                2,
                6
        );


        //---------------------- CANTIDAD DE MOVIMIENTO
        Tcantidad = new CampoTexto();

        Tcantidad.setPreferredSize(
                new Dimension(
                        250,
                        45
                )
        );

        agregarCampo(
                "Cantidad de movimiento",
                Tcantidad,
                6,
                2,
                6
        );


        //---------------------- CANTIDAD ACTUAL
        TcantidadActual = new CampoTexto();

        TcantidadActual.setPreferredSize(
                new Dimension(
                        250,
                        45
                )
        );

        agregarCampo(
                "Cantidad Actual",
                TcantidadActual,
                0,
                4,
                6
        );


        //---------------------- MOTIVO
        Amotivo = new CampoArea();

        Amotivo.setTamaño(
                400,
                100
        );

        agregarCampo(
                "Motivo",
                Amotivo,
                0,
                6,
                12
        );
    }


    //------------------------ I N I C I A L I Z A R   B O T Ó N ----------------
    private void inicializarBotonGuardar() {

        Bguardar = new Boton(
                "Guardar"
        );

        Bguardar.amarillo();

        //---------------------- TAMAÑO
        Bguardar.setPreferredSize(
                new Dimension(
                        180,
                        45
                )
        );

        //---------------------- RESTRICCIONES
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = filaSiguiente;

        gbc.gridwidth = 12;

        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        gbc.insets = new Insets(
                10,
                5,
                15,
                5
        );

        //---------------------- AGREGAR BOTÓN
        Pcontenido.add(
                Bguardar,
                gbc
        );

        //---------------------- SIGUIENTE FILA
        filaSiguiente++;
    }


    //------------------------ G E T T E R S ----------------

    public CampoComboBox<String> getCampoTipo() {

        return Ctipo;
    }

    public CampoTexto getCampoCantidad() {

        return Tcantidad;
    }

    public CampoTexto getCampoCantidadActual() {

        return TcantidadActual;
    }

    public CampoArea getCampoMotivo() {

        return Amotivo;
    }

    public Boton getBotonGuardar() {

        return Bguardar;
    }
}