//---------------------- V E N T A N A   D A T O S   T A R J E T A ----------------------
package gui.ventanitas;

import java.awt.*;
import javax.swing.*;

import gui.componentes.*;
import gui.estilo.*;

public class VentanaDatosTarjeta extends VentanaDatos {

    //------------------------ A T R I B U T O S ----------------

    //-------------- Componentes
    private JLabel Ltitulo;
    private JLabel Ltarjeta;
    private JLabel Ldpi;

    private CampoTexto Ttarjeta;
    private CampoTexto Tdpi;

    private Boton Baceptar;

    //-------------- Datos
    private String numeroTarjeta;
    private String dpi;

    private boolean aceptado;


    //-------------------- C O N S T R U C T O R -----------------

    public VentanaDatosTarjeta(Window padre) {

        super(
                padre,
                "Datos de tarjeta"
        );

        //----------------- I N I C I A R

        inicializarContenido();

        setVisible(true);
    }


    //----------------------- C O N T E N I D O

    private void inicializarContenido() {

        //---------------- T Í T U L O

        Ltitulo = new JLabel(
                "Datos de tarjeta"
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


        //---------------- N Ú M E R O   D E   T A R J E T A

        Ltarjeta = new JLabel(
                "Número de tarjeta:"
        );

        Ltarjeta.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Ltarjeta.setForeground(
                Colores.NEGRO
        );


        GridBagConstraints gbcLtarjeta
                = new GridBagConstraints();

        gbcLtarjeta.gridx = 0;
        gbcLtarjeta.gridy = 2;

        gbcLtarjeta.gridwidth = 2;

        gbcLtarjeta.weightx = 1.0;

        gbcLtarjeta.anchor =
                GridBagConstraints.LINE_START;

        gbcLtarjeta.insets =
                new Insets(
                        5, 45, 5, 45
                );

        Pcontenido.add(
                Ltarjeta,
                gbcLtarjeta
        );


        //---------------- C A M P O   T A R J E T A

        Ttarjeta = new CampoTexto();

        Ttarjeta.setPlaceholder(
                "Ingrese el número de tarjeta..."
        );


        GridBagConstraints gbcTtarjeta
                = new GridBagConstraints();

        gbcTtarjeta.gridx = 0;
        gbcTtarjeta.gridy = 3;

        gbcTtarjeta.gridwidth = 2;

        gbcTtarjeta.weightx = 1.0;

        gbcTtarjeta.fill =
                GridBagConstraints.HORIZONTAL;

        gbcTtarjeta.insets =
                new Insets(
                        0, 45, 20, 45
                );

        Pcontenido.add(
                Ttarjeta,
                gbcTtarjeta
        );


        //---------------- D P I

        Ldpi = new JLabel(
                "DPI:"
        );

        Ldpi.setFont(
                Fuentes.LFL.deriveFont(
                        Fuentes.NORMAL
                )
        );

        Ldpi.setForeground(
                Colores.NEGRO
        );


        GridBagConstraints gbcLdpi
                = new GridBagConstraints();

        gbcLdpi.gridx = 0;
        gbcLdpi.gridy = 4;

        gbcLdpi.gridwidth = 2;

        gbcLdpi.weightx = 1.0;

        gbcLdpi.anchor =
                GridBagConstraints.LINE_START;

        gbcLdpi.insets =
                new Insets(
                        5, 45, 5, 45
                );

        Pcontenido.add(
                Ldpi,
                gbcLdpi
        );


        //---------------- C A M P O   D P I

        Tdpi = new CampoTexto();

        Tdpi.setPlaceholder(
                "Ingrese su DPI..."
        );


        GridBagConstraints gbcTdpi
                = new GridBagConstraints();

        gbcTdpi.gridx = 0;
        gbcTdpi.gridy = 5;

        gbcTdpi.gridwidth = 2;

        gbcTdpi.weightx = 1.0;

        gbcTdpi.fill =
                GridBagConstraints.HORIZONTAL;

        gbcTdpi.insets =
                new Insets(
                        0, 45, 25, 45
                );

        Pcontenido.add(
                Tdpi,
                gbcTdpi
        );


        //---------------- B O T O N   A C E P T A R

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
        gbcAceptar.gridy = 6;

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

        String tarjeta =
                Ttarjeta.getText().trim();

        String dpi =
                Tdpi.getText().trim();


        //---------------- V A L I D A C I Ó N

        if (tarjeta.isEmpty()) {

            Dialogo.aviso(
                    this,
                    "Datos de tarjeta",
                    "Ingrese el número de tarjeta."
            );

            Ttarjeta.requestFocus();

            return;
        }


        if (dpi.isEmpty()) {

            Dialogo.aviso(
                    this,
                    "Datos de tarjeta",
                    "Ingrese el DPI."
            );

            Tdpi.requestFocus();

            return;
        }


        //---------------- G U A R D A R

        numeroTarjeta = tarjeta;

        this.dpi = dpi;

        aceptado = true;

        dispose();
    }


    //---------------------- G E T T E R S

    public String getNumeroTarjeta() {

        return numeroTarjeta;
    }


    public String getDPI() {

        return dpi;
    }


    public boolean fueAceptado() {

        return aceptado;
    }
}
