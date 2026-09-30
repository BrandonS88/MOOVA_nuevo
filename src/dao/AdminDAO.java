//---------------------- A D M I N   D A O ----------------------
package dao;

import conexion.Conexion_MOOVA;
import java.sql.*;
import java.util.*;


public class AdminDAO {
    private final Conexion_MOOVA conexion = new Conexion_MOOVA();

    public List<Object[]> consultar(String sql, Object... parametros) {
        List<Object[]> filas = new ArrayList<>();
        try (Connection con = conexion.getConnection()) {
            if (con == null) return filas;
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                parametros(ps, parametros);
                try (ResultSet rs = ps.executeQuery()) {
                    int n = rs.getMetaData().getColumnCount();
                    while (rs.next()) {
                        Object[] fila = new Object[n];
                        for (int i = 0; i < n; i++) fila[i] = rs.getObject(i + 1);
                        filas.add(fila);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DAO administrador: " + e.getMessage());
        }
        return filas;
    }

    public Object[] consultarUno(String sql, Object... parametros) {
        List<Object[]> filas = consultar(sql, parametros);
        return filas.isEmpty() ? null : filas.get(0);
    }

    public boolean ejecutar(String sql, Object... parametros) {
        try (Connection con = conexion.getConnection()) {
            if (con == null) return false;
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                parametros(ps, parametros);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error DAO administrador: " + e.getMessage());
            return false;
        }
    }

    public int siguienteId(String tabla, String id) {
        Object[] fila = consultarUno("SELECT COALESCE(MAX(" + id + "),0)+1 FROM " + tabla);
        return fila == null ? 1 : ((Number) fila[0]).intValue();
    }

    /**
     * Llena un combo con los resultados de una consulta.
     *
     * Cada opción se guarda como un objeto OpcionCombo, que contiene el
     * id y el nombre por separado. Antes se armaba un texto suelto del
     * tipo "1 - MooFfin", y el id se recuperaba después/partiendo ese
     * texto. Ese método era frágil: si un producto se llamaba "3 - algo"
     * o si el nombre traía guiones, la extracción del id fallaba y se
     * enviaba 0 a la base de datos.
     *
     * Con el objeto, el id viaja intacto y el nombre solo se usa para
     * mostrarlo.
     */
    public <E> void cargarCombo(javax.swing.JComboBox<E> combo, String sql) {
        combo.removeAllItems();
        for (Object[] f : consultar(sql)) {
            int id = f[0] instanceof Number ? ((Number) f[0]).intValue() : 0;
            // Se castea a E porque el combo fue declarado como
            // CampoComboBox<String> en los CRUDs, pero en realidad
            // guardará objetos OpcionCombo. El renderer del combo usa
            // toString(), así que se sigue viendo el nombre.
           @SuppressWarnings("unchecked")
            E opcion = (E) new OpcionCombo(id, txt(f[1]));
            combo.addItem(opcion);
        }
    }

    /**
     * Devuelve el id del elemento seleccionado en el combo.
     * Si no hay nada seleccionado devuelve 0.
     */
    public int idCombo(javax.swing.JComboBox<?> combo) {
        Object o = combo.getSelectedItem();
        if (o instanceof OpcionCombo) return ((OpcionCombo) o).getId();
        if (o == null) return 0;
        // Respaldo para combos que guarden texto plano.
        try { return Integer.parseInt(o.toString().trim()); }
        catch (NumberFormatException e) { return 0; }
    }

    /**
     * Selecciona en el combo la opción cuyo id coincida con el indicado.
     * Se usa al cargar un registro para que sus claves foráneas queden
     * reflejadas en el formulario.
     */
    public void seleccionarCombo(javax.swing.JComboBox<?> combo, int id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            Object o = combo.getItemAt(i);
            if (o instanceof OpcionCombo && ((OpcionCombo) o).getId() == id) {
                combo.setSelectedIndex(i);
                return;
            }
            // Respaldo para combos con texto plano "id - nombre".
            if (o != null && o.toString().startsWith(id + " - ")) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    private static String txt(Object o) {
        return o == null ? "" : String.valueOf(o);
    }

    /**
     * Elemento que se guarda dentro de los ComboBox del administrador.
     * Lleva el id y el nombre del registro en campos separados, para no
     * depender de un texto que haya que partir.
     */
    public static class OpcionCombo {

        private final int id;
        private final String nombre;

        public OpcionCombo(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        // El texto que ve el usuario en la lista desplegable.
        @Override
        public String toString() {
            return nombre;
        }
    }

    private void parametros(PreparedStatement ps, Object... parametros) throws SQLException {
        for (int i = 0; i < parametros.length; i++) ps.setObject(i + 1, parametros[i]);
    }
}
