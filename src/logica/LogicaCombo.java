//---------------------------------- L O G I C A   C O M B O -----------------------
package logica;

import dao.GrupoComboDAO;
import dao.OpcionComboDAO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import modelo.Combo;
import modelo.Grupo_Combo;
import modelo.ItemPedido;
import modelo.Opcion_Combo;
import modelo.Producto;
import modelo.SeleccionCombo;

public class LogicaCombo {

    //------------------------ A T R I B U T O S ----------------
    private Combo combo;
    private Producto productoBase;

    private List<Grupo_Combo> grupos;

    private Map<Integer, List<SeleccionCombo>> selecciones;

    private GrupoComboDAO grupoComboDAO;
    private OpcionComboDAO opcionComboDAO;

    //-------------------- C O N S T R U C T O R -----------------
    public LogicaCombo(
            Combo combo,
            Producto productoBase
    ) {

        this.combo = combo;
        this.productoBase = productoBase;

        grupoComboDAO
                = new GrupoComboDAO();

        opcionComboDAO
                = new OpcionComboDAO();

        grupos
                = new ArrayList<>();

        selecciones
                = new LinkedHashMap<>();

        inicializarGrupos();
    }

    //---------------------- C O N S T R U C T O R   C A J I T A ----------------
    public LogicaCombo(
            Combo combo
    ) {

        this(
                combo,
                null
        );
    }

    //---------------------- I N I C I A L I Z A R   G R U P O S
    private void inicializarGrupos() {

        grupos
                = grupoComboDAO.listarPorCombo(
                        combo.getId_Combo()
                );

        for (Grupo_Combo grupo : grupos) {

            selecciones.put(
                    grupo.getId_GrupoCombo(),
                    new ArrayList<>()
            );
        }
    }

    //---------------------- S I G U I E N T E   G R U P O
    public Grupo_Combo obtenerSiguienteGrupoPendiente() {

        for (Grupo_Combo grupo : grupos) {

            List<SeleccionCombo> seleccionados
                    = selecciones.get(
                            grupo.getId_GrupoCombo()
                    );

            int cantidad
                    = seleccionados.size();

            if (cantidad
                    < grupo.getCantidad_minima()) {

                return grupo;
            }
        }

        return null;
    }

    //---------------------- A S I G N A R   P R O D U C T O
    public boolean asignarProducto(
            Grupo_Combo grupo,
            Producto producto
    ) {

        List<SeleccionCombo> seleccionados
                = selecciones.get(
                        grupo.getId_GrupoCombo()
                );

        if (seleccionados == null) {

            return false;
        }

        if (seleccionados.size()
                >= grupo.getCantidad_maxima()) {

            return false;
        }

        //---------------------- O P C I Ó N
        Opcion_Combo opcion
                = opcionComboDAO
                        .buscarPorGrupoYProducto(
                                grupo.getId_GrupoCombo(),
                                producto.getId_Producto()
                        );

        if (opcion == null) {

            return false;
        }

        //---------------------- G U A R D A R
        seleccionados.add(
                new SeleccionCombo(
                        grupo,
                        producto,
                        opcion
                )
        );

        return true;
    }

    //---------------------- C O M P L E T O
    public boolean estaCompleto() {

        return obtenerSiguienteGrupoPendiente()
                == null;
    }

    //---------------------- S E L E C C I O N E S
    public List<SeleccionCombo>
            obtenerSelecciones() {

        List<SeleccionCombo> resultado
                = new ArrayList<>();

        for (Grupo_Combo grupo
                : grupos) {

            List<SeleccionCombo> seleccionados
                    = selecciones.get(
                            grupo.getId_GrupoCombo()
                    );

            if (seleccionados != null) {

                resultado.addAll(
                        seleccionados
                );
            }
        }

        return resultado;
    }

    //---------------------- C R E A R   I T E M
    public ItemPedido crearItemPedido() {

        return new ItemPedido(
                productoBase,
                combo,
                obtenerSelecciones()
        );
    }

    //---------------------- P R O D U C T O S
    // Método conservado para partes antiguas
    // de la interfaz que todavía necesiten
    // solamente una lista de productos.
    public List<Producto>
            obtenerProductosSeleccionados() {

        List<Producto> resultado
                = new ArrayList<>();

        if (productoBase != null) {

            resultado.add(
                    productoBase
            );
        }

        for (SeleccionCombo seleccion
                : obtenerSelecciones()) {

            resultado.add(
                    seleccion.getProducto()
            );
        }

        return resultado;
    }

    //---------------------- G E T T E R S
    public Combo getCombo() {
        return combo;
    }

    public Producto getProductoBase() {
        return productoBase;
    }

    public List<Grupo_Combo> getGrupos() {
        return grupos;
    }
}
