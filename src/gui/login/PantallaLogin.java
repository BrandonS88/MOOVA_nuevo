//--------------------------------- L O G I N ----------------------------------
package gui.login;

import java.awt.*;
import javax.swing.*;
import gui.componentes.*;
import gui.estilo.*;

import logica.LogicaLogin;
import modelo.Usuario;
import modelo.Sesion;

import gui.cajero.PantallaPrincipal;
import gui.administrador.*;
import logica.Reloj;

public class PantallaLogin extends JFrame {

    //------------------------  A T R I B U T O S ---------------
    //-------------- Componentes
    private JLabel Lbienvenida, Lusuario, Lcontrasena;
    private ImagenEscalada Ilogo;
    private CampoTexto Tusuario;
    private CampoContrasena Tcontrasena;
    private Boton BiniciarSesion;

    // Botón para salir por completo del programa.
    // Se coloca junto a "Iniciar Sesión" para poder cerrar MOOVA
    // sin tener que buscar la X de la ventana (que está oculta
    // porque la ventana no tiene bordes).
    private Boton Bsalir;

    //--------------- Paneles
    private BarraInferior barraInferior;
    private PanelFondo Pizquierdo, Pprincipal;
    private PanelColor Pderecho;

    //--------------- Login
    private LogicaLogin login;

    //-------------------- C O N S T R U C T O R -----------------
    public PantallaLogin() {

        //----------------- JFRAME
        setTitle("Iniciar Sesión");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        // Quitar bordes y controles de Windows
        setUndecorated(true);

        //Iniciamos el login
        login = new LogicaLogin();

        inicializarComponentes();

        setVisible(true);

    }

    //----------- I N I C I A L I Z A R  C O M P O N E N T E S ----------------
    public void inicializarComponentes() {
        inicializarPrincipal();
        inicializarIzquierdo();
        inicializarDerecho();
        inicializarContenidoDerecho();
        inicializarAbajo();
    }

    //----------------------- PANEL PRINCIPAL
    public void inicializarPrincipal() {
        Pprincipal = new PanelFondo(
                "/utiles/imagenes/fondos/fondo_A.png"
        );

        //organizador
        Pprincipal.setLayout(new GridBagLayout());

        this.add(Pprincipal);
    }

    //----------------------- PANEL IZQUIERDO
    public void inicializarIzquierdo() {
        Pizquierdo = new PanelFondo(
                obtenerImagenLogin()
        );

        //------------------ Panel izquierdo en el principal
        //reglas
        GridBagConstraints gbcPrincipal = new GridBagConstraints();

        //Columna
        gbcPrincipal.gridx = 0;
        //Fila
        gbcPrincipal.gridy = 0;

        //Le dice que tome el 38% de tamaño de forma horizontal RELATIVO
        gbcPrincipal.weightx = 0.38;
        //Le dice que tome el 90% del tamaño de forma vertical RELATIVO
        gbcPrincipal.weighty = 0.90;

        // Separación a la izquierda
        //insets(arriba, izquierda, abajo, derecha)
        gbcPrincipal.insets = new Insets(0, 40, 0, 0);

        //Permite que el panel se expanda tanto horizontal como verticalmente
        gbcPrincipal.fill = GridBagConstraints.BOTH;

        Pprincipal.add(Pizquierdo, gbcPrincipal);
    }

    //---------------------- PANEL DERECHO
    public void inicializarDerecho() {
        Pderecho = new PanelColor();

        Pderecho.setColorFondo(Colores.DURAZNO);
        Pderecho.setOpacidad(180);

        //Este es con el organizador primero que hicimos en referencia al panel principal
        //---------------------- ORGANIZADOR PRINCIPAL
        //reglas
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 0;

        //toma el 62% del tamaño restante de forma horizontal RELATIVO
        gbc.weightx = 0.62;
        //Toma el 90% de forma vertical RELATIVO
        gbc.weighty = 0.90;

        //Se expande en ambos
        gbc.fill = GridBagConstraints.BOTH;

        // Sin separación adicional
        gbc.insets = new Insets(0, 0, 0, 0);

        Pprincipal.add(Pderecho, gbc);
    }

    public void inicializarContenidoDerecho() {

        //-------------------------- CONTENIDO
        Pderecho.setLayout(new GridBagLayout());

        //                         LOGOTIPO
        Ilogo = new ImagenEscalada(
                "/utiles/imagenes/logotipo/logo-texto.png",
                400,
                200
        );

        GridBagConstraints gbcLogotipo = new GridBagConstraints();

        gbcLogotipo.gridx = 0;
        gbcLogotipo.gridy = 0;  // Ahora es la primera fila

        gbcLogotipo.weightx = 1.0;
        gbcLogotipo.weighty = 0.2;

        gbcLogotipo.fill = GridBagConstraints.BOTH;
        gbcLogotipo.anchor = GridBagConstraints.CENTER;

        Pderecho.add(Ilogo, gbcLogotipo);

        //                       BIENVENIDA
        Lbienvenida = new JLabel("¡Bienvenido!");

        Lbienvenida.setFont(
                Fuentes.VC.deriveFont(Fuentes.TITULO)
        );

        Lbienvenida.setForeground(Colores.CORINTO);

        GridBagConstraints gbcBienvenida = new GridBagConstraints();

        gbcBienvenida.gridx = 0;
        gbcBienvenida.gridy = 1;

        gbcBienvenida.weightx = 1.0;
        gbcBienvenida.weighty = 0.08;

        gbcBienvenida.anchor = GridBagConstraints.CENTER;

        gbcBienvenida.insets = new Insets(0, 0, 25, 0);

        Pderecho.add(Lbienvenida, gbcBienvenida);

        //                         USUARIO
        Lusuario = new JLabel("Usuario: ");

        Lusuario.setFont(
                Fuentes.LFL.deriveFont(Fuentes.SUBTITULO)
        );

        Lusuario.setForeground(Colores.NEGRO);

        GridBagConstraints gbcUsuario = new GridBagConstraints();

        gbcUsuario.gridx = 0;
        gbcUsuario.gridy = 2;

        gbcUsuario.weightx = 1.0;
        gbcUsuario.weighty = 0.03;

        gbcUsuario.anchor = GridBagConstraints.LINE_START;
        gbcUsuario.insets = new Insets(0, 80, 0, 0);

        Pderecho.add(Lusuario, gbcUsuario);

        //                    CAMPO DE USUARIO
        Tusuario = new CampoTexto();
        Tusuario.setPlaceholder("Ingrese su nombre de usuario...");

        GridBagConstraints gbcTusuario = new GridBagConstraints();

        gbcTusuario.gridx = 0;
        gbcTusuario.gridy = 3;

        gbcTusuario.weightx = 1.0;
        gbcTusuario.weighty = 0.15;

        gbcTusuario.fill = GridBagConstraints.HORIZONTAL;

        // Lo coloca hacia la izquierda
        gbcTusuario.anchor = GridBagConstraints.LINE_START;

        // Separación del borde izquierdo y derecho
        gbcTusuario.insets = new Insets(
                5, 80, 20, 200
        );

        Pderecho.add(Tusuario, gbcTusuario);

        //                       CONTRASEÑA
        Lcontrasena = new JLabel("Contraseña: ");

        Lcontrasena.setFont(
                Fuentes.LFL.deriveFont(Fuentes.SUBTITULO)
        );

        Lcontrasena.setForeground(Colores.NEGRO);

        GridBagConstraints gbcContrasena = new GridBagConstraints();

        gbcContrasena.gridx = 0;
        gbcContrasena.gridy = 4;

        gbcContrasena.weightx = 1.0;
        gbcContrasena.weighty = 0.03;

        gbcContrasena.anchor = GridBagConstraints.LINE_START;
        gbcContrasena.insets = new Insets(0, 80, 0, 0);

        Pderecho.add(Lcontrasena, gbcContrasena);

        //                  CAMPO DE CONTRASEÑA
        Tcontrasena = new CampoContrasena();
        Tcontrasena.setPlaceholder("Ingrese su contraseña...");

        GridBagConstraints gbcTcontrasena = new GridBagConstraints();

        gbcTcontrasena.gridx = 0;
        gbcTcontrasena.gridy = 5;

        gbcTcontrasena.weightx = 1.0;
        gbcTcontrasena.weighty = 0.15;

        gbcTcontrasena.fill = GridBagConstraints.HORIZONTAL;

        // Lo coloca hacia la izquierda
        gbcTcontrasena.anchor = GridBagConstraints.LINE_START;

        // Separación del borde izquierdo y derecho
        gbcTcontrasena.insets = new Insets(
                5, 80, 25, 200
        );

        Pderecho.add(Tcontrasena, gbcTcontrasena);

        //                          BOTÓN
        BiniciarSesion = new Boton("Iniciar Sesión");

        GridBagConstraints gbcBoton = new GridBagConstraints();

        gbcBoton.gridx = 0;
        gbcBoton.gridy = 6;

        gbcBoton.weightx = 1.0;
        gbcBoton.weighty = 0.10;

        gbcBoton.anchor = GridBagConstraints.CENTER;

        Pderecho.add(BiniciarSesion, gbcBoton);
        //ActionListener
        BiniciarSesion.addActionListener(e -> iniciarSesion());

        //---------------------- B O T Ó N   S A L I R
        // Permite cerrar el programa por completo. Como la ventana no tiene
        // bordes (setUndecorated(true)), no hay una X visible, así que este
        // botón es la única forma directa de salir sin usar atajos de teclado.
        Bsalir = new Boton(
                "Salir"
        );

        Bsalir.rojo();

        Bsalir.addActionListener(
                e -> salir()
        );

        GridBagConstraints gbcSalir = new GridBagConstraints();

        gbcSalir.gridx = 0;
        gbcSalir.gridy = 7;

        gbcSalir.weightx = 1.0;
        gbcSalir.weighty = 0.06;

        gbcSalir.anchor = GridBagConstraints.CENTER;

        gbcSalir.insets = new Insets(
                0,
                0,
                10,
                0
        );

        Pderecho.add(
                Bsalir,
                gbcSalir
        );
    }

    //---------------------- S A L I R   D E L   P R O G R A M A
    /**
     * Cierra MOOVA por completo.
     * Se pregunta antes para no perder lo que el usuario estaba escribiendo.
     * System.exit(0) termina todos los hilos, incluidos los temporizadores
     * de Swing que mantienen viva la aplicación.
     */
    private void salir() {

        // Dialogo se usa para que los botones salgan como "Sí" y "No"
        // en lugar de "Yes" y "No".
        int respuesta = Dialogo.confirmar(
                this,
                "Salir",
                "¿Deseas salir del programa?"
        );

        if (respuesta == Dialogo.SI) {

            Sesion.cerrar();

            dispose();

            System.exit(0);
        }
    }

    //----------------------------- PANELES DE ABAJO
    public void inicializarAbajo() {

        barraInferior = new BarraInferior(
                Colores.CORINTO,
                Colores.NARANJA
        );

        //---------------------- REGLAS
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 1;

        // Ocupa las dos columnas
        gbc.gridwidth = 2;

        gbc.weightx = 1.0;

        // 10% de altura total
        gbc.weighty = 0.10;

        gbc.fill = GridBagConstraints.BOTH;

        Pprincipal.add(barraInferior, gbc);
    }

    //---------------------- I M A G E N   L O G I N ----------------
    private String obtenerImagenLogin() {

        if (Reloj.esManana()) {

            return "/utiles/imagenes/fotografias/Dibu_Login.png";

        } else {

            return "/utiles/imagenes/fotografias/Spreen_PantallaPrincipal.png";
        }
    }

    //----------------------------- BOTÓN
    private void iniciarSesion() {

        //Jala los datos de los campos
        String nombreUsuario = Tusuario.getText().trim();
        String contrasena = new String(Tcontrasena.getPassword());

        //------------------- VALIDACIONES
        //-------------- NOMBRE DEL USUARIO
        //Si está vacío
        if (nombreUsuario.isEmpty()) {

            Dialogo.aviso(
                    this,
                    "Inicio de sesión",
                    "Ingrese su nombre de usuario."
            );

            Tusuario.requestFocus();
            return;
        }

        // Si supera los 25 caracteres
        if (nombreUsuario.length() > 25) {

            Dialogo.aviso(
                    this,
                    "Inicio de sesión",
                    "El nombre de usuario no puede superar los 25 caracteres."
            );

            Tusuario.requestFocus();
            return;
        }

        // Si contiene espacios
        if (nombreUsuario.contains(" ")) {

            Dialogo.aviso(
                    this,
                    "Inicio de sesión",
                    "El nombre de usuario no puede contener espacios."
            );

            Tusuario.requestFocus();
            return;
        }

        // Si contiene caracteres no permitidos
        if (!nombreUsuario.matches("[a-zA-Z0-9_]+")) {

            Dialogo.aviso(
                    this,
                    "Inicio de sesión",
                    "El nombre de usuario solo puede contener letras, números y guion bajo (_)."
            );

            Tusuario.requestFocus();
            return;
        }

        //----------- CONTRASEÑA
        // Si está vacía
        if (contrasena.isEmpty()) {

            Dialogo.aviso(
                    this,
                    "Inicio de sesión",
                    "Ingrese su contraseña."
            );

            Tcontrasena.requestFocus();
            return;
        }

        // Si supera los 20 caracteres
        if (contrasena.length() > 20) {

            Dialogo.aviso(
                    this,
                    "Inicio de sesión",
                    "La contraseña no puede superar los 20 caracteres."
            );

            Tcontrasena.requestFocus();
            return;
        }

        // Si contiene espacios
        if (contrasena.contains(" ")) {

            Dialogo.aviso(
                    this,
                    "Inicio de sesión",
                    "La contraseña no puede contener espacios."
            );

            Tcontrasena.requestFocus();
            return;
        }

        // Si contiene el símbolo *
        if (contrasena.contains("*")) {

            Dialogo.aviso(
                    this,
                    "Inicio de sesión",
                    "La contraseña no puede contener el símbolo (*)."
            );

            Tcontrasena.requestFocus();
            return;
        }

        //------------------- INICIO DE SESIÓN
        //Creamos el objeto
        Usuario usuario = login.iniciarSesion(
                nombreUsuario,
                contrasena
        );

        //Si no encuentra al usuario
        if (usuario == null) {

            Dialogo.error(
                    this,
                    "Inicio de sesión",
                    "Usuario o contraseña incorrectos."
            );

            return;
        }

        //Si encuentra al usuario
        Dialogo.informacion(
                this,
                "Inicio de sesión",
                "Bienvenido, " + usuario.getNombre() + "."
        );

        // Se guarda el usuario en la sesión antes de abrir cualquier
        // pantalla. Gracias a esto, las ventanas que se abran después
        // (como la del cajero) pueden saber si quien entró es
        // administrador o cajero, por ejemplo para mostrar u ocultar
        // el botón "Regresar".
        Sesion.iniciar(usuario);

        //Abrimos la interfaz que corresponda
        abrirPantallaSegunRol(usuario);
    }

    //-------------------- ABRIR PANTALLA 
    private void abrirPantallaSegunRol(Usuario usuario) {

        //Se obtiene el rol del usuario
        String rol = usuario.getRol();

        //si el rol es igual a:
        //cajero
        if (rol.equals("Cajero")) {
            //Se cierra esta ventana
            dispose();

            //Se abre la pantalla Principal
            new PantallaPrincipal();
            //administrador
        } else if (rol.equals("Administrador")) {

            //se cierra esta ventana
            dispose();
            //se abre el menú de administrador
            new PantallaDashboard();
        }
    }
}
