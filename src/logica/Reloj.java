//---------------------------------- R E L O J -----------------------
package logica;

import java.time.LocalTime;

public class Reloj {

    //---------------------- HORA ACTUAL
    public static LocalTime obtenerHoraActual() {

        return LocalTime.now();
    }

    //---------------------- HORA
    public static int obtenerHora() {

        return obtenerHoraActual().getHour();
    }

    //---------------------- MAÑANA
    public static boolean esManana() {

        int hora = obtenerHora();

        return hora >= 4 && hora < 11;
    }

    //---------------------- TARDE
    public static boolean esTarde() {

        int hora = obtenerHora();

        return hora >= 11 && hora < 18;
    }

    //---------------------- NOCHE
    public static boolean esNoche() {

        int hora = obtenerHora();

        return hora >= 18 || hora < 4;
    }

    public static String obtenerPeriodo() {

        if (esManana()) {
            return "Manana";

        } else if (esTarde()) {
            return "Tarde";

        } else {
            return "Noche";
        }
    }
}
