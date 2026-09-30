//-------------------------- F U E N T E S --------------------------
package gui.estilo;

import java.awt.Font;

public class Fuentes {

    //--------------- FUENTES ------------------
    public static Font BUTFLOW;
    public static Font LFL;
    public static Font LFL_OUTLINE;
    public static Font VC;

    //----------------- TAMAÑOS -----------------
    public static final float TITULO = 55f;
    public static final float SUBTITULO = 45f;
    public static final float GRANDE = 37f;
    public static final float NORMAL = 30f;
    public static final float PEQUENO = 20f;

    static {
        try {
            BUTFLOW = Font.createFont(
                    Font.TRUETYPE_FONT,
                    Fuentes.class.getResourceAsStream(
                            "/utiles/fuentes/Butflow.ttf"
                    )
            );

            LFL = Font.createFont(
                    Font.TRUETYPE_FONT,
                    Fuentes.class.getResourceAsStream(
                            "/utiles/fuentes/Letters-for-Learners.ttf"
                    )
            );

            LFL_OUTLINE = Font.createFont(
                    Font.TRUETYPE_FONT,
                    Fuentes.class.getResourceAsStream(
                            "/utiles/fuentes/Letters-for-Learners-Outline.ttf"
                    )
            );

            VC = Font.createFont(
                    Font.TRUETYPE_FONT,
                    Fuentes.class.getResourceAsStream(
                            "/utiles/fuentes/Vanilla-Caramel.ttf"
                    )
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
