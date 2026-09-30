//--------------------------------- C O N E X I Ó N  ----------------------------------
package conexion;

import java.sql.*;

//Clase conexion
public class Conexion_MOOVA {
    //---------------- ATRIBUTOS ----------------
    private static String User = "root";
    private static String Password = "123456789";
    private static String Url = "jdbc:mysql://localhost:3306/MOOVA_DB";
    
    //--------------- CONEXION ---------------
    public Connection getConnection() {
        Connection conx = null;
        try {
            conx = (Connection) DriverManager.getConnection(Url, User, Password);
            System.out.println("Conexion establecida correctamente.");
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e);
        }
        return conx;
    }
}
