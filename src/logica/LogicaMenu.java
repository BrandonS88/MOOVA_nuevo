//---------------------- L O G I C A   M E N U -----------------------
package logica;

import dao.CategoriaDAO;
import dao.SubcategoriaDAO;
import dao.ProductoDAO;
import dao.ComboDAO;

import java.util.ArrayList;
import java.util.List;

import modelo.*;

public class LogicaMenu {

    //------------------------ A T R I B U T O S ----------------
    private static final CategoriaDAO categoriaDAO
            = new CategoriaDAO();

    private static final SubcategoriaDAO subcategoriaDAO
            = new SubcategoriaDAO();

    private static final ProductoDAO productoDAO
            = new ProductoDAO();

    private static final ComboDAO comboDAO
            = new ComboDAO();

    //------------------------ O B T E N E R   S U B C A T E G O R I A S ----------------
    public static List<SubcategoriaMenu> obtenerSubcategorias(
            String categoria
    ) {

        switch (categoria) {

            case "Hamburguesas":
                return obtenerHamburguesas();

            case "Bebidas":
                return obtenerBebidas();

            case "Postres":
                return obtenerPostres();

            case "Antojos":
                return obtenerAntojos();

            case "Cajita Feliz":
                return new ArrayList<>();

            default:
                return new ArrayList<>();
        }
    }

    //---------------------- H A M B U R G U E S A S ----------------
    public static List<SubcategoriaMenu> obtenerHamburguesas() {

        if (Reloj.esManana()) {

            return obtenerSubcategoriasDeCategoria(
                    "Desayuno"
            );

        } else {

            return obtenerSubcategoriasDeCategoria(
                    "Almuerzo"
            );
        }
    }

    //---------------------- B E B I D A S ----------------
    public static List<SubcategoriaMenu> obtenerBebidas() {

        List<SubcategoriaMenu> resultado
                = new ArrayList<>();

        agregarSubcategoriasDeCategoria(
                resultado,
                "Gaseosas"
        );

        agregarSubcategoriasDeCategoria(
                resultado,
                "Bebidas Frías"
        );

        agregarSubcategoriasDeCategoria(
                resultado,
                "Bebidas Calientes"
        );

        agregarSubcategoriasDeCategoria(
                resultado,
                "Jugo Natural"
        );

        return resultado;
    }

    //---------------------- P O S T R E S ----------------
    public static List<SubcategoriaMenu> obtenerPostres() {

        return obtenerSubcategoriasDeCategoria(
                "Postres"
        );
    }

    //---------------------- A N T O J O S ----------------
    public static List<SubcategoriaMenu> obtenerAntojos() {

        return obtenerSubcategoriasDeCategoria(
                "Antojos"
        );
    }

    //---------------------- C A J I T A   F E L I Z ----------------
    public static List<Combo> obtenerCajitas() {

        String nombreSubcategoria;

        if (Reloj.esManana()) {

            nombreSubcategoria
                    = "Cajita Infantil Desayuno";

        } else {

            nombreSubcategoria
                    = "Cajita Infantil Almuerzo";
        }

        Subcategoria subcategoria
                = subcategoriaDAO.buscarPorNombre(
                        nombreSubcategoria
                );

        if (subcategoria == null) {
            return new ArrayList<>();
        }

        return comboDAO.listarPorSubcategoria(
                subcategoria.getId_Subcategoria()
        );
    }

    //---------------------- O B T E N E R   S U B C A T E G O R I A S ----------------
    private static List<SubcategoriaMenu> obtenerSubcategoriasDeCategoria(
            String nombreCategoria
    ) {

        List<SubcategoriaMenu> resultado
                = new ArrayList<>();

        Categoria categoria
                = categoriaDAO.buscarPorNombre(
                        nombreCategoria
                );

        if (categoria == null) {
            return resultado;
        }

        List<Subcategoria> subcategorias
                = subcategoriaDAO.listarPorCategoria(
                        categoria.getId_Categoria()
                );

        for (Subcategoria subcategoria : subcategorias) {

            List<Producto> productos
                    = productoDAO.listarPorSubcategoria(
                            subcategoria.getId_Subcategoria()
                    );

            if (productos.isEmpty()) {
                continue;
            }

            resultado.add(
                    new SubcategoriaMenu(
                            subcategoria.getNombre(),
                            productos
                    )
            );
        }

        return resultado;
    }

    //---------------------- A G R E G A R   S U B C A T E G O R I A S ----------------
    private static void agregarSubcategoriasDeCategoria(
            List<SubcategoriaMenu> lista,
            String nombreCategoria
    ) {

        lista.addAll(
                obtenerSubcategoriasDeCategoria(
                        nombreCategoria
                )
        );
    }
}
