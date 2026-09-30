//---------------------- I N G R E D I E N T E   C R U D ----------------------
package gui.administrador;

import gui.componentes.*;
import java.awt.*;

public class IngredienteCRUD extends VentanaCRUD {

    //------------------------ A T R I B U T O S ----------------

    //-------------- Campos de texto
    private CampoTexto Tnombre;
    private CampoTexto Tcantidad;
    private CampoTexto TcantidadMinima;
    private CampoTexto Tprecio;

    //-------------- Área de texto
    private CampoArea Adescripcion;

    //-------------- Botón
    private Boton BactualizarStock;

    //------------------------ C O N S T R U C T O R ----------------
    public IngredienteCRUD() {

        super(
                "Administración de Ingredientes"
        );
        
        //---------------------- TÍTULO
        setTituloCRUD("Ingrediente");

        //---------------------- ID
        setTextoId(
                "ID_Ingrediente"
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

        //---------------------- BOTÓN STOCK
        inicializarBotonStock();

        //---------------------- TABLA
        finalizarCRUD(
                new String[]{
                    "id_Ingrediente",
                    "nombre",
                    "cantidad",
                    "cantidad_minima",
                    "fecha_creacion",
                    "fecha_actualizacion",
                    "descripcion",
                    "precio",
                    "imagen",
                    "estado"
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

        //---------------------- CANTIDAD
        Tcantidad = new CampoTexto();

        Tcantidad.setPreferredSize(
                tamañoCampo
        );

        Tcantidad.setMinimumSize(
                tamañoCampo
        );

        Tcantidad.setMaximumSize(
                tamañoCampo
        );

        agregarCampoDerechaHorizontal(
                "Cantidad",
                Tcantidad,
                0,
                1
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

        agregarCampoDerechaHorizontal(
                "Cantidad mínima",
                TcantidadMinima,
                1,
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

    //------------------------ A C T U A L I Z A R   S T O C K ----------------
    private void inicializarBotonStock() {

        BactualizarStock = new Boton(
                "Actualizar Stock"
        );

        BactualizarStock.crema();

        BactualizarStock.setPreferredSize(
                new Dimension(
                        200,
                        45
                )
        );

        //---------------------- POSICIÓN
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 3;

        gbc.gridwidth = 2;

        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;

        gbc.insets = new Insets(
                10,
                5,
                10,
                5
        );

        Pderecha.add(
                BactualizarStock,
                gbc
        );

        //---------------------- ACCIÓN
        BactualizarStock.addActionListener(
                e -> actualizarStock()
        );
    }

    //------------------------ A C C I Ó N ----------------
    protected void actualizarStock() {
        CrudAdministrador.abrirMovimiento(this);
    }

    //---------------------- G E T T E R S ----------------

    public CampoTexto getCampoNombre() {

        return Tnombre;
    }

    public CampoTexto getCampoCantidad() {

        return Tcantidad;
    }

    public CampoTexto getCampoCantidadMinima() {

        return TcantidadMinima;
    }

    public CampoTexto getCampoPrecio() {

        return Tprecio;
    }

    public CampoArea getCampoDescripcion() {

        return Adescripcion;
    }

    public Boton getBotonActualizarStock() {

        return BactualizarStock;
    }
}