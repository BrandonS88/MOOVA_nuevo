//---------------------- V E N T A N A   D A T O S   R E G A L O ----------------------
package gui.ventanitas;

import java.awt.*;
import javax.swing.*;

import gui.componentes.*;
import gui.estilo.*;

public class VentanaDatosRegalo extends VentanaDatos {

    //------------------------ A T R I B U T O S ----------------

    //-------------- Componentes
    private JLabel Ltitulo;
    private JLabel Lcodigo;

    private CampoTexto Tcodigo;

    private Boton Baceptar;

    //-------------- Datos
    private String codigoRegalo;

    private boolean aceptado;


    //-------------------- C O N S T R U C T O R -----------------

    public VentanaDatosRegalo(Window padre) {

        super(
                padre,
                "Código de regalo"
        );

        inicializarContenido();

        setVisible(true);
    }


    //----------------------- C O N T E N I D O

    private void inicializarContenido() {

        //---------------- T Í T U L O

        Ltitulo = new JLabel(
                "Código de regalo"
        );

        Ltitulo.setFont(
                Fuentes.VC.deriveFont(
                        Fuentes.SUBTITULO
                )
        );

        Ltitulo.setForeground(
                Colores.CORINTO
        );


        GridBagConstraints gbcTitulo
                = new GridBagConstraints();

        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 1;

        gbcTitulo.gridwidth = 2;

        gbcTitulo.weightx = 1.0;
        gbcTitulo.weighty = 0.20;

        gbcTitulo.anchor =
                GridBagConstraints.CENTER;

        gbcTitulo.insets =
                new Insets(
                        5, 20, 10, 20
                );

        Pcontenido.add(
                Ltitulo,
                gbcTitulo
        );


        //---------------- C Ó D I G O

        Lcodigo = new JLabel(
                "Código de regalo:"
        );

        Lcodigo.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Lcodigo.setForeground(
                Colores.NEGRO
        );


        GridBagConstraints gbcLcodigo
                = new GridBagConstraints();

        gbcLcodigo.gridx = 0;
        gbcLcodigo.gridy = 2;

        gbcLcodigo.gridwidth = 2;

        gbcLcodigo.weightx = 1.0;

        gbcLcodigo.anchor =
                GridBagConstraints.LINE_START;

        gbcLcodigo.insets =
                new Insets(
                        5, 45, 5, 45
                );

        Pcontenido.add(
                Lcodigo,
                gbcLcodigo
        );


        //---------------- C A M P O   C Ó D I G O

        Tcodigo = new CampoTexto();

        Tcodigo.setPlaceholder(
                "Ingrese el código de regalo..."
        );


        GridBagConstraints gbcTcodigo
                = new GridBagConstraints();

        gbcTcodigo.gridx = 0;
        gbcTcodigo.gridy = 3;

        gbcTcodigo.gridwidth = 2;

        gbcTcodigo.weightx = 1.0;

        gbcTcodigo.fill =
                GridBagConstraints.HORIZONTAL;

        gbcTcodigo.insets =
                new Insets(
                        0, 45, 30, 45
                );

        Pcontenido.add(
                Tcodigo,
                gbcTcodigo
        );


        //---------------- B O T Ó N   A C E P T A R

        Baceptar = new Boton(
                "Aceptar"
        );

        Baceptar.amarillo();

        Baceptar.addActionListener(
                e -> aceptar()
        );


        GridBagConstraints gbcAceptar
                = new GridBagConstraints();

        gbcAceptar.gridx = 0;
        gbcAceptar.gridy = 4;

        gbcAceptar.gridwidth = 2;

        gbcAceptar.weightx = 1.0;
        gbcAceptar.weighty = 0.15;

        gbcAceptar.anchor =
                GridBagConstraints.CENTER;

        gbcAceptar.fill =
                GridBagConstraints.HORIZONTAL;

        gbcAceptar.insets =
                new Insets(
                        5, 100, 30, 100
                );

        Pcontenido.add(
                Baceptar,
                gbcAceptar
        );
    }


    //---------------------- A C E P T A R

    private void aceptar() {

        String codigo =
                Tcodigo.getText().trim();


        //---------------- V A L I D A C I Ó N

        if (codigo.isEmpty()) {

            Dialogo.aviso(
                    this,
                    "Código de regalo",
                    "Ingrese el código de regalo."
            );

            Tcodigo.requestFocus();

            return;
        }


        //---------------- G U A R D A R

        codigoRegalo = codigo;

        aceptado = true;

        dispose();
    }


    //---------------------- G E T T E R S

    public String getCodigoRegalo() {

        return codigoRegalo;
    }


    public boolean fueAceptado() {

        return aceptado;
    }
}
