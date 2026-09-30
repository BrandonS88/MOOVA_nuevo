//------------------ P A N E L   C O N T E N I D O   A D M I N I S T R A D O R ------------------
package gui.administrador;

import gui.componentes.Boton;
import gui.componentes.ImagenEscalada;
import java.awt.*;
import javax.swing.*;
import gui.estilo.*;

public class PanelContenidoAdministrador extends JPanel {

    //------------------------  A T R I B U T O S ----------------
    //-------------- Logo
    private ImagenEscalada Ilogo;

    //-------------- Botones
    private Boton Bdashboard;
    private Boton Bpedidos;
    private Boton BadministracionDatos;
    private Boton Bhistorial;
    private Boton BcerrarSesion;

    //-------------- Listener
    private AdministradorListener administradorListener;

    //----------------------- L I S T E N E R
    public interface AdministradorListener {

        void opcionSeleccionada(
                String opcion
        );
    }

    //-------------------- C O N S T R U C T O R -----------------
    public PanelContenidoAdministrador() {

        //---------------------- PANEL
        setBackground(
                Colores.VERDE_OSCURO
        );

        setLayout(
                new GridBagLayout()
        );

        setPreferredSize(
                new Dimension(
                        300,
                        0
                )
        );

        //---------------------- INICIALIZAR
        inicializarComponentes();
    }

    //---------------------- I N I C I A L I Z A R
    private void inicializarComponentes() {

        inicializarLogo();
        inicializarBotones();
    }

    //---------------------- L O G O
    private void inicializarLogo() {

        Ilogo = new ImagenEscalada(
                "/utiles/imagenes/logotipo/logo-texto-eslogan.png",
                300,
                300
        );

        Ilogo.setAlineacion(
                SwingConstants.CENTER,
                SwingConstants.CENTER
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 0.20;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        add(
                Ilogo,
                gbc
        );
    }

    //---------------------- B O T O N E S
    private void inicializarBotones() {

        Bdashboard = new Boton(
                "Dashboard"
        );

        Bpedidos = new Boton(
                "Pedidos"
        );

        BadministracionDatos = new Boton(
                "Administración de Datos"
        );

        Bhistorial = new Boton(
                "Historial"
        );

        BcerrarSesion = new Boton(
                "Cerrar Sesión"
        );

        //---------------------- ESTILO
        Bdashboard.amarillo();
        Bpedidos.amarillo();
        BadministracionDatos.amarillo();
        Bhistorial.amarillo();
        BcerrarSesion.rojo();

        //---------------------- POSICIÓN
        agregarBoton(
                Bdashboard,
                1
        );

        agregarBoton(
                Bpedidos,
                2
        );

        agregarBoton(
                BadministracionDatos,
                3
        );

        agregarBoton(
                Bhistorial,
                4
        );

        agregarBoton(
                BcerrarSesion,
                5
        );

        //---------------------- LISTENERS
        agregarAccion(
                Bdashboard,
                "Dashboard"
        );

        agregarAccion(
                Bpedidos,
                "Pedidos"
        );

        agregarAccion(
                BadministracionDatos,
                "Administración de Datos"
        );

        agregarAccion(
                Bhistorial,
                "Historial"
        );

        agregarAccion(
                BcerrarSesion,
                "Cerrar Sesión"
        );
    }

    //---------------------- A G R E G A R   B O T Ó N
    private void agregarBoton(
            Boton boton,
            int fila
    ) {

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = fila;

        gbc.weightx = 1.0;
        gbc.weighty = 0.12;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(
                5,
                15,
                5,
                15
        );

        add(
                boton,
                gbc
        );
    }

    //---------------------- L I S T E N E R
    public void setAdministradorListener(
            AdministradorListener listener
    ) {

        this.administradorListener = listener;
    }

    private void agregarAccion(
            Boton boton,
            String opcion
    ) {

        boton.addActionListener(e -> {

            if (administradorListener != null) {

                administradorListener.opcionSeleccionada(
                        opcion
                );
            }
        });
    }

}