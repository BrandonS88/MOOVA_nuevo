//---------------------------------- B A R R A   S U P E R I O R -----------------------
package gui.cajero;

import gui.componentes.Boton;
import gui.componentes.Dialogo;
import java.awt.*;
import javax.swing.*;
import gui.estilo.*;
import gui.login.PantallaLogin;
import gui.administrador.PantallaDashboard;
import modelo.Sesion;

public class BarraSuperior extends JPanel {

    //------------------------  A T R I B U T O S ----------------
    //---------------------- Botones
    private Boton Bmenu;
    private Boton BcerrarSesion;

    // Botón para volver al panel del administrador.
    // Solo se crea y se muestra cuando quien está conectado es
    // administrador; un cajero nunca debe verlo.
    private Boton Bregresar;

    // Botón para cerrar el programa por completo.
    // Se agrega a la derecha de "Cerrar Sesión" para poder salir de MOOVA
    // sin cerrar sesión primero, que es distinto: cerrar sesión devuelve
    // al login, mientras que este botón termina la aplicación.
    private Boton Bsalir;

    //---------------------- Alturas
    private static final int ALTURA = 70;

    //-------------- Confirmar regreso al menú
    private boolean confirmarMenu = true;

    //-------------------- C O N S T R U C T O R -----------------
    public BarraSuperior() {

        //---------------------- C O L O R
        setBackground(
                Colores.VERDE_OSCURO
        );

        //---------------------- O R G A N I Z A D O R
        setLayout(
                new GridBagLayout()
        );

        //---------------------- C R E A R   B O T O N E S
        Bmenu = new Boton(
                "Menú"
        );

        BcerrarSesion = new Boton(
                "Cerrar Sesión"
        );

        //---------------------- E S T I L O   C E R R A R   S E S I Ó N
        BcerrarSesion.rojo();

        //---------------------- A C C I O N E S
        Bmenu.addActionListener(e -> irAlMenu());

        BcerrarSesion.addActionListener(e -> cerrarSesion());

        //---------------------- B O T Ó N   R E G R E S A R
        // El botón "Regresar" devuelve al administrador a su panel de
        // control. Se crea únicamente si la sesión activa pertenece a un
        // administrador: si quien entró fue un cajero, el botón no existe
        // para que no pueda abrir la parte de administración.
        if (Sesion.esAdministrador()) {

            Bregresar = new Boton(
                    "Regresar"
            );

            Bregresar.verde();

            Bregresar.addActionListener(
                    e -> irAlAdministrador()
            );
        }

        //---------------------- B O T Ó N   S A L I R
        // Cierra el programa por completo. Es distinto de "Cerrar Sesión":
        // ese devuelve al login para poder entrar con otro usuario, este
        // termina la aplicación. Se necesita porque las ventanas del
        // programa no tienen bordes, así que no hay una X para cerrar.
        Bsalir = new Boton(
                "Salir"
        );

        Bsalir.rojo();

        Bsalir.addActionListener(
                e -> salirDelPrograma()
        );

        //---------------------- B O T O N E S
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.anchor = GridBagConstraints.CENTER;

        gbc.fill = GridBagConstraints.NONE;

        gbc.insets = new Insets(
                0,
                20,
                0,
                20
        );

        //---------------------- M E N Ú
        gbc.gridx = 0;

        add(
                Bmenu,
                gbc
        );

        //---------------------- R E G R E S A R   A D M I N
        // Se coloca entre "Menú" y "Cerrar Sesión" y solo si existe.
        if (Bregresar != null) {

            gbc.gridx = 1;

            add(
                    Bregresar,
                    gbc
            );
        }

        //---------------------- C E R R A R   S E S I Ó N
        gbc.gridx = 2;

        add(
                BcerrarSesion,
                gbc
        );

        //---------------------- S A L I R
        gbc.gridx = 3;

        add(
                Bsalir,
                gbc
        );

        //---------------------- T A M A Ñ O
        setPreferredSize(
                new Dimension(
                        0,
                        ALTURA
                )
        );
    }

    //---------------------- G E T T E R S
    public Boton getBmenu() {

        return Bmenu;
    }

    public Boton getBcerrarSesion() {

        return BcerrarSesion;
    }

    //---------------------- M É T O D O S

    //---------------------- S A L I R   D E L   P R O G R A M A
    /**
     * Cierra MOOVA por completo.
     *
     * Se diferencia de "Cerrar Sesión" en que este botón termina la
     * aplicación y el otro devuelve al login. Se pregunta antes de salir
     * para que el usuario no pierda un pedido a medio hacer.
     *
     * System.exit(0) es necesario porque Swing mantiene vivos varios
     * hilos en segundo plano (el reloj del encabezado, los temporizadores
     * de las ventanas), y con solo dispose() el proceso seguiría
     * ejecutándose sin ventana visible.
     */
    private void salirDelPrograma() {

        // Se usa Dialogo para que los botones salgan como "Sí" y "No"
        // en vez de "Yes" y "No", que es lo que pone Swing cuando la
        // computadora está en inglés.
        int respuesta = Dialogo.confirmar(
                this,
                "Salir",
                "¿Deseas salir del programa?"
        );

        if (respuesta != Dialogo.SI) {

            return;
        }

        // Se limpia la sesión para que si alguien volviera a abrir el
        // programa no quedara un usuario anterior registrado.
        Sesion.cerrar();

        Window ventana = SwingUtilities.getWindowAncestor(this);

        if (ventana != null) {

            ventana.dispose();
        }

        System.exit(0);
    }

    //---------------------- I R   A L   A D M I N I S T R A D O R
    /**
     * Regresa al panel del administrador.
     *
     * Antes de cerrar la ventana se pregunta al usuario, porque puede
     * tener un pedido a medio hacer y no querer perderlo.
     * La comprobación de rol se repite aquí (aunque el botón ya solo se crea
     * para administradores) para que nadie pueda invocar este método
     * manualmente y saltarse la restricción.
     */
    private void irAlAdministrador() {

        if (!Sesion.esAdministrador()) {

            Dialogo.aviso(
                    this,
                    "Acceso restringido",
                    "Solo un administrador puede regresar a esta sección."
            );

            return;
        }

        int respuesta = Dialogo.confirmar(
                this,
                "Regresar",
                "¿Deseas regresar al panel del administrador?\n"
                        + "Si tienes un pedido en curso se perderá."
        );

        if (respuesta != Dialogo.SI) {

            return;
        }

        // Se abre la ventana nueva ANTES de cerrar la vieja.
        //
        // Al revés (cerrar primero, abrir después) se ve el escritorio
        // durante el tiempo que tarda la ventana nueva en construirse, y
        // eso se ve como un parpadeo: la pantalla desaparece y vuelve a
        // aparecer.
        new PantallaDashboard();

        Window ventana = SwingUtilities.getWindowAncestor(this);

        if (ventana != null) {

            ventana.dispose();
        }
    }

    private void cerrarSesion() {

        int respuesta = Dialogo.confirmar(
                this,
                "Cerrar sesión",
                "¿Deseas cerrar sesión?"
        );

        if (respuesta == Dialogo.SI) {

            // Se limpia la sesión para que ningún botón que dependa del
            // rol (como "Regresar") siga creyéndose que hay un
            // administrador conectado.
            Sesion.cerrar();

            // Se abre el login antes de cerrar la ventana actual. Si se
            // cerrara primero, se vería el escritorio hasta que el login
            // terminara de construirse.
            new PantallaLogin().setVisible(true);

            Window ventana = SwingUtilities.getWindowAncestor(this);

            if (ventana != null) {
                ventana.dispose();
            }
        }
    }

    //---------------------- C O N F I R M A R   M E N Ú
    public void setConfirmarMenu(
            boolean confirmar
    ) {

        confirmarMenu = confirmar;
    }

    //---------------------- I R   A L   M E N Ú
    private void irAlMenu() {

        //---------------------- C O N F I R M A C I Ó N
        if (confirmarMenu) {

            int respuesta = Dialogo.confirmar(
                    this,
                    "Ir al menú",
                    "¿Deseas regresar al menú?"
            );

            if (respuesta != Dialogo.SI) {

                return;
            }
        }

        //---------------------- A B R I R   M E N Ú
        //
        // Se abre el menú antes de cerrar la ventana actual. Cerrar
        // primero dejaba la pantalla vacía durante el tiempo que tarda el
        // menú en construirse, y eso se veía como un parpadeo.
        new Menu().setVisible(true);

        //---------------------- C E R R A R   V E N T A N A
        Window ventana = SwingUtilities.getWindowAncestor(this);

        if (ventana != null) {

            ventana.dispose();
        }
    }
}
