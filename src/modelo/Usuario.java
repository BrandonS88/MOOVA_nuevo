//---------------------- U S U A R I O ----------------------
package modelo;

public class Usuario {
    //---------------- ATRIBUTOS ----------------
    private int id_Usuario;
    private String nombre_usuario;
    private String nombre;
    private String apellido;
    private String contrasena;
    private String correo_electronico;
    private String rol;
    private boolean estado;

    //--------------- CONSTRUCTOR ---------------

    public Usuario() {
    }

    public Usuario(int id_Usuario, String nombre_usuario, String nombre, String apellido, String contrasena, String correo_electronico, String rol, boolean estado) {
        this.id_Usuario = id_Usuario;
        this.nombre_usuario = nombre_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.correo_electronico = correo_electronico;
        this.rol = rol;
        this.estado = estado;
    }

    //---------------- SETTERS Y GETTERS --------------

    public int getId_Usuario() {
        return id_Usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public String getRol() {
        return rol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    //----------------- TO STRING --------------------
    @Override
    public String toString() {
        return nombre_usuario;
    }
}