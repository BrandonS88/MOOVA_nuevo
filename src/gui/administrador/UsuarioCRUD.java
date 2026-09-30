//---------------------- U S U A R I O   C R U D ----------------------
package gui.administrador;

import gui.componentes.*;
import java.awt.*;

public class UsuarioCRUD extends VentanaCRUD {

    //------------------------ A T R I B U T O S ----------------

    //-------------- Campos de texto
    private CampoTexto TnombreUsuario;
    private CampoTexto Tnombre;
    private CampoTexto Tapellido;
    private CampoTexto Tcontrasena;
    private CampoTexto Tcorreo;

    //-------------- Combobox
    private CampoComboBox<String> Crol;

    //------------------------ C O N S T R U C T O R ----------------
    public UsuarioCRUD() {

        super(
                "Administración de Usuarios"
        );

        //---------------------- TÍTULO
        setTituloCRUD(
                "Usuario"
        );

        //---------------------- ID
        setTextoId(
                "ID_Usuario"
        );

        //---------------------- CONFIGURACIÓN
        mostrarImagen(
                false
        );

        mostrarBotonDesactivar(
                true
        );

        //---------------------- CAMPOS
        inicializarCampos();

        //---------------------- TABLA
        finalizarCRUD(
                new String[]{
                    "id_Usuario",
                    "nombre_usuario",
                    "nombre",
                    "apellido",
                    "contrasena",
                    "correo_electronico",
                    "rol",
                    "estado"
                }
        );

        setVisible(true);
    }

    //------------------------ I N I C I A L I Z A R   C A M P O S ----------------
    private void inicializarCampos() {

        //---------------------- NOMBRE DE USUARIO
        TnombreUsuario = new CampoTexto();

        TnombreUsuario.setPreferredSize(
                new Dimension(
                        350,
                        45
                )
        );

        agregarCampo(
                "Usuario",
                TnombreUsuario,
                0,
                2,
                6
        );

        //---------------------- NOMBRE
        Tnombre = new CampoTexto();

        Tnombre.setPreferredSize(
                new Dimension(
                        350,
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

        //---------------------- APELLIDO
        Tapellido = new CampoTexto();

        Tapellido.setPreferredSize(
                new Dimension(
                        350,
                        45
                )
        );

        agregarCampo(
                "Apellido",
                Tapellido,
                0,
                3,
                6
        );

        //---------------------- ROL
        Crol = new CampoComboBox<>();

        Crol.setPreferredSize(
                new Dimension(
                        350,
                        45
                )
        );

        Crol.addItem("Cajero");
        Crol.addItem("Administrador");

        agregarCampo(
                "Rol",
                Crol,
                6,
                3,
                6
        );

        //---------------------- CONTRASEÑA
        Tcontrasena = new CampoTexto();

        Tcontrasena.setPreferredSize(
                new Dimension(
                        350,
                        45
                )
        );

        // Al seleccionar un registro, el campo queda vacío: las
        // contraseñas no se muestran ni se leen de la base de datos.
        // El texto de ayuda le avisa al administrador que solo escriba
        // aquí si quiere darle una contraseña nueva.
        Tcontrasena.setPlaceholder(
                "Déjalo vacío si no quieres cambiarla"
        );

        agregarCampo(
                "Contraseña",
                Tcontrasena,
                0,
                4,
                6
        );

        //---------------------- CORREO
        Tcorreo = new CampoTexto();

        Tcorreo.setPreferredSize(
                new Dimension(
                        350,
                        45
                )
        );

        agregarCampo(
                "Correo",
                Tcorreo,
                6,
                4,
                6
        );
    }

    //---------------------- G E T T E R S ----------------

    public CampoTexto getCampoNombreUsuario() {

        return TnombreUsuario;
    }

    public CampoTexto getCampoNombre() {

        return Tnombre;
    }

    public CampoTexto getCampoApellido() {

        return Tapellido;
    }

    public CampoTexto getCampoContrasena() {

        return Tcontrasena;
    }

    public CampoTexto getCampoCorreo() {

        return Tcorreo;
    }

    public CampoComboBox<String> getComboRol() {

        return Crol;
    }
}