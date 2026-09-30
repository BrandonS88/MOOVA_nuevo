//---------------------- V E N T A N A   C O M P R A ----------------------
package gui.cajero;

import java.awt.*;
import javax.swing.*;
import gui.componentes.*;
import gui.estilo.*;

public class VentanaCompra extends JFrame {

    //------------------------ A T R I B U T O S ----------------
    //-------------- Panel principal
    protected PanelFondo Pprincipal;

    //-------------- Panel central
    protected JPanel Pcontenido;

    //-------------- Panel izquierdo
    protected PanelColor Pizquierdo;

    //-------------- Panel derecho
    protected PanelColor Pderecho;

    //-------------- Encabezado
    protected JPanel Pencabezado;
    protected BotonRegresar Bregresar;
    protected JLabel Ltitulo;

    //-------------- Barras
    protected BarraSuperior barraSuperior;
    protected BarraInferior barraInferior;

    //------------------------ C O N S T R U C T O R ----------------
    public VentanaCompra(String titulo) {

        setTitle(titulo);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);

        inicializarComponentes();
    }

    //---------------------- C O M P O N E N T E S ----------------
    private void inicializarComponentes() {

        inicializarPrincipal();
        inicializarBarraSuperior();
        inicializarContenido();
        inicializarBarraInferior();
    }

    //---------------------- P A N E L   P R I N C I P A L ----------------
    private void inicializarPrincipal() {

        Pprincipal = new PanelFondo(
                "/utiles/imagenes/fondos/fondo_A.png"
        );

        Pprincipal.setLayout(new GridBagLayout());

        add(Pprincipal);
    }

    //---------------------- B A R R A   S U P E R I O R ----------------
    private void inicializarBarraSuperior() {

        barraSuperior = new BarraSuperior();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        gbc.fill = GridBagConstraints.HORIZONTAL;

        Pprincipal.add(
                barraSuperior,
                gbc
        );
    }

    //---------------------- C O N T E N I D O ----------------
    private void inicializarContenido() {

        Pcontenido = new JPanel(
                new Layout25_75()
        );

        Pcontenido.setOpaque(false);

        inicializarPanelIzquierdo();
        inicializarPanelDerecho();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.fill = GridBagConstraints.BOTH;

        Pprincipal.add(Pcontenido, gbc);
    }

    //---------------------- P A N E L   I Z Q U I E R D O ----------------
    private void inicializarPanelIzquierdo() {

        Pizquierdo = new PanelColor();

        Pizquierdo.setColorFondo(
                Colores.AMARILLO
        );

        Pizquierdo.setOpacidad(220);

        Pizquierdo.setLayout(
                new BorderLayout()
        );

        Pcontenido.add(
                Pizquierdo
        );
    }

    //---------------------- P A N E L   D E R E C H O ----------------
    private void inicializarPanelDerecho() {

        Pderecho = new PanelColor();

        Pderecho.setColorFondo(
                Colores.DURAZNO
        );

        Pderecho.setOpacidad(200);

        Pderecho.setLayout(
                new BorderLayout()
        );

        inicializarEncabezado();

        Pcontenido.add(
                Pderecho
        );
    }

    //---------------------- E N C A B E Z A D O ----------------
    private void inicializarEncabezado() {

        Pencabezado = new JPanel(
                new BorderLayout()
        );

        Pencabezado.setOpaque(false);

        //---------------------- Botón regresar
        Bregresar = new BotonRegresar();

        Pencabezado.add(
                Bregresar,
                BorderLayout.WEST
        );

        //---------------------- Título
        Ltitulo = new JLabel();

        Ltitulo.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.TITULO
                )
        );

        Ltitulo.setForeground(
                Colores.CORINTO
        );

        Ltitulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        Pencabezado.add(
                Ltitulo,
                BorderLayout.CENTER
        );

        Pderecho.add(
                Pencabezado,
                BorderLayout.NORTH
        );
    }

    //---------------------- B A R R A   I N F E R I O R ----------------
    private void inicializarBarraInferior() {

        barraInferior = new BarraInferior(
                Colores.CORINTO,
                Colores.NARANJA
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 2;

        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;

        Pprincipal.add(
                barraInferior,
                gbc
        );
    }

    //---------------------- M É T O D O S   P R O T E G I D O S ----------------
    protected void configurarTitulo(String titulo) {

        Ltitulo.setText(titulo);
    }

    //---------------------- M O S T R A R ----------------
    protected void mostrarVentana() {

        setVisible(true);
    }
}

//------------------------------------- 25----------------------75
class Layout25_75 implements LayoutManager {

    @Override
    public void addLayoutComponent(
            String nombre,
            Component componente
    ) {
    }

    @Override
    public void removeLayoutComponent(
            Component componente
    ) {
    }

    @Override
    public Dimension preferredLayoutSize(
            Container padre
    ) {

        return new Dimension(0, 0);
    }

    @Override
    public Dimension minimumLayoutSize(
            Container padre
    ) {

        return new Dimension(0, 0);
    }

    @Override
    public void layoutContainer(
            Container padre
    ) {

        int ancho = padre.getWidth();
        int alto = padre.getHeight();

        int anchoIzquierdo
                = (int) (ancho * 0.25);

        int anchoDerecho
                = ancho - anchoIzquierdo;

        Component izquierdo
                = padre.getComponent(0);

        Component derecho
                = padre.getComponent(1);

        izquierdo.setBounds(
                0,
                0,
                anchoIzquierdo,
                alto
        );

        derecho.setBounds(
                anchoIzquierdo,
                0,
                anchoDerecho,
                alto
        );
    }

}
