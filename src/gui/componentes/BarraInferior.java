//---------------------------------- B A R R A  I N F E R I O R ----------------------- 
package gui.componentes; 
 
import java.awt.*; 
import javax.swing.*; 
 
public class BarraInferior extends JPanel { 
 
    //------------------------  A T R I B U T O S --------------- 
    //-------------- Paneles 
    private JPanel Pbajo1; 
    private JPanel Pbajo2; 
 
    //-------------- Colores 
    private Color colorBajo1; 
    private Color colorBajo2; 

    //-------------- Altura
    private static final int ALTURA = 60;
 
    //-------------------- C O N S T R U C T O R ----------------- 
    public BarraInferior(Color colorBajo1, Color colorBajo2) { 
 
        this.colorBajo1 = colorBajo1; 
        this.colorBajo2 = colorBajo2; 
 
        inicializarComponentes(); 
    } 
 
    //---------------- I N I C I A L I Z A R  C O M P O N E N T E S ---------------- 
    public void inicializarComponentes() { 
        inicializarPaneles(); 
    } 
 
    //----------------------- P A N E L E S ----------------------- 
    public void inicializarPaneles() { 
 
        // Panel inferior 1 
        Pbajo1 = new JPanel(); 
        Pbajo1.setBackground(colorBajo1); 
 
        // Panel inferior 2 
        Pbajo2 = new JPanel(); 
        Pbajo2.setBackground(colorBajo2); 
 
        //---------------------- ORGANIZADOR 
        setLayout(new GridLayout(2, 1)); 
 
        // Agregar paneles 
        add(Pbajo1); 
        add(Pbajo2); 
    } 

    //---------------------- T A M A Ñ O -----------------------
    @Override
    public Dimension getPreferredSize() {

        return new Dimension(
                super.getPreferredSize().width,
                ALTURA
        );
    }

    @Override
    public Dimension getMinimumSize() {

        return new Dimension(
                0,
                ALTURA
        );
    }

    @Override
    public Dimension getMaximumSize() {

        return new Dimension(
                Integer.MAX_VALUE,
                ALTURA
        );
    }
 
    public void setColorBajo1(Color color) { 
 
        colorBajo1 = color; 
        Pbajo1.setBackground(colorBajo1); 
    } 
 
    public void setColorBajo2(Color color) { 
 
        colorBajo2 = color; 
        Pbajo2.setBackground(colorBajo2); 
    } 
 
}