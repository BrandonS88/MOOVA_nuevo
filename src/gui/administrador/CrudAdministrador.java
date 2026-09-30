//---------------------- C R U D   A D M I N I S T R A D O R ----------------------
package gui.administrador;

import gui.componentes.Dialogo;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import javax.swing.*;


public final class CrudAdministrador {

    private static int ingredienteMovimiento = 0;

    // Dominio institucional obligatorio para el correo de los usuarios.
    // MOOVA es una empresa ficticia, así que todos sus empleados deben
    // registrarse con un correo que termine en "@moova.com".
    private static final String DOMINIO_MOOVA = "@moova.com";

    private CrudAdministrador() {}

    //---------------------- I N I C I A L I Z A R ----------------------
    public static void inicializar(VentanaCRUD v) {
        try {
            if (v instanceof CategoriaCRUD) preparar(v, "Categoria", "id_Categoria");
            else if (v instanceof SubcategoriaCRUD) {
                SubcategoriaCRUD x = (SubcategoriaCRUD) v;
                v.adminDAO.cargarCombo(x.getComboCategoria(),
                        "SELECT id_Categoria,nombre FROM Categoria WHERE estado=TRUE ORDER BY nombre");
                preparar(v, "Subcategoria", "id_Subcategoria");
            } else if (v instanceof ProductoCRUD) {
                ProductoCRUD x = (ProductoCRUD) v;
                v.adminDAO.cargarCombo(x.getComboSubcategoria(),
                        "SELECT id_Subcategoria,nombre FROM Subcategoria WHERE estado=TRUE ORDER BY nombre");
                preparar(v, "Producto", "id_Producto");
            } else if (v instanceof IngredienteCRUD) {
                IngredienteCRUD x = (IngredienteCRUD) v;
                x.getCampoCantidad().setEditable(false);
                preparar(v, "Ingrediente", "id_Ingrediente");
                x.getCampoCantidad().setText("100");
            } else if (v instanceof RecetaCRUD) {
                RecetaCRUD x = (RecetaCRUD) v;
                v.adminDAO.cargarCombo(x.getComboProducto(),
                        "SELECT id_Producto,nombre FROM Producto WHERE estado=TRUE ORDER BY nombre");
                v.adminDAO.cargarCombo(x.getComboIngrediente(),
                        "SELECT id_Ingrediente,nombre FROM Ingrediente WHERE estado=TRUE ORDER BY nombre");
                preparar(v, "Receta", "id_Receta");
            } else if (v instanceof ComboCRUD) {
                ComboCRUD x = (ComboCRUD) v;

                // El campo "tipo" ahora sí se llena con los valores del
                // ENUM de la tabla Combo ('Complementario', 'Independiente').
                // Antes se deshabilitaba con el texto "No aplica en la BD
                // actual", pero la columna sí existe en el esquema, así que
                // el administrador podía administrarla.
                x.getCampoTipo().removeAllItems();
                x.getCampoTipo().addItem("Complementario");
                x.getCampoTipo().addItem("Independiente");
                x.getCampoTipo().setEnabled(true);
                x.getCampoTipo().setToolTipText(
                        "Complementario: su precio se suma al producto.\n"
                                +"Independiente: es un combo con precio propio.");

                // La subcategoría se carga desde la base de datos, igual que
                // en el CRUD de productos, y se deja habilitada.
                v.adminDAO.cargarCombo(x.getCampoSubcategoria(),
                        "SELECT id_Subcategoria,nombre FROM Subcategoria WHERE estado=TRUE ORDER BY nombre");
                x.getCampoSubcategoria().setEnabled(true);
                x.getCampoSubcategoria().setToolTipText(
                        "Solo para combos de tipo 'Independiente'.");

                // El precio cambia de significado según el tipo, así que se
                // avisa de ello en el tooltip.
                x.getCampoPrecio().setToolTipText("Precio extra del combo");

                preparar(v, "Combo", "id_Combo");
            } else if (v instanceof GrupoComboCRUD) {
                GrupoComboCRUD x = (GrupoComboCRUD) v;
                v.adminDAO.cargarCombo(x.getCampoCombo(),
                        "SELECT id_Combo,nombre FROM Combo WHERE estado=TRUE ORDER BY nombre");
                preparar(v, "Grupo_Combo", "id_GrupoCombo");
            } else if (v instanceof OpcionComboCRUD) {
                OpcionComboCRUD x = (OpcionComboCRUD) v;
                v.adminDAO.cargarCombo(x.getCampoGrupoCombo(),
                        "SELECT id_GrupoCombo,nombre FROM Grupo_Combo ORDER BY nombre");
                v.adminDAO.cargarCombo(x.getCampoProducto(),
                        "SELECT id_Producto,nombre FROM Producto WHERE estado=TRUE ORDER BY nombre");
                preparar(v, "Opcion_Combo", "id_OpcionCombo");
            } else if (v instanceof ProductoComboCRUD) {
                ProductoComboCRUD x = (ProductoComboCRUD) v;
                v.adminDAO.cargarCombo(x.getCampoProducto(),
                        "SELECT id_Producto,nombre FROM Producto WHERE estado=TRUE ORDER BY nombre");
                v.adminDAO.cargarCombo(x.getCampoCombo(),
                        "SELECT id_Combo,nombre FROM Combo WHERE estado=TRUE ORDER BY nombre");
                preparar(v, "Producto_Combo", "id_ProductoCombo");
            } else if (v instanceof DescuentoCRUD) preparar(v, "Descuento", "id_Descuento");
            else if (v instanceof UsuarioCRUD) preparar(v, "Usuario", "id_Usuario");
            else if (v instanceof GrupoPersonalizacionCRUD) {
                GrupoPersonalizacionCRUD x = (GrupoPersonalizacionCRUD) v;
                v.adminDAO.cargarCombo(x.getCampoProducto(),
                        "SELECT id_Producto,nombre FROM Producto WHERE estado=TRUE ORDER BY nombre");
                preparar(v, "Grupo_Personalizacion", "id_GrupoPersonalizacion");
            } else if (v instanceof OpcionPersonalizacionCRUD) {
                OpcionPersonalizacionCRUD x = (OpcionPersonalizacionCRUD) v;
                v.adminDAO.cargarCombo(x.getCampoGrupoPersonalizacion(),
                        "SELECT id_GrupoPersonalizacion,nombre FROM Grupo_Personalizacion ORDER BY nombre");
                v.adminDAO.cargarCombo(x.getCampoIngrediente(),
                        "SELECT id_Ingrediente,nombre FROM Ingrediente WHERE estado=TRUE ORDER BY nombre");
                preparar(v, "Opcion_Personalizacion", "id_OpcionPersonalizacion");
            } else if (v instanceof MovimientoInventarioCRUD) inicializarMovimiento((MovimientoInventarioCRUD) v);
        } catch (Exception e) {
            System.out.println("No se pudo inicializar CRUD: " + e.getMessage());
        }
    }

    private static void preparar(VentanaCRUD v, String tabla, String id) {
        recargar(v);
        v.prepararId(tabla, id);
    }

    //---------------------- T A B L A ----------------------
    /**
     * Vuelve a llenar la tabla del CRUD con los datos de la base de datos.
     *
     * Todas las consultas ordenan por el ID del registro, de menor a
     * mayor. Ordenar por ID tiene dos ventajas: el orden nunca cambia
     * entre recargas (si se ordenara por nombre, un cambio de nombre
     * movería la fila de lugar) y se puede ver de un vistazo en qué
     * orden se fueron creando los registros.
     *
     * Las columnas booleanas se traducen con IF(...,'Sí','No') o
     * IF(...,'Activo','Inactivo') para que se lean igual que el
     * formulario, en vez de mostrar el 1, 0 o true crudo de MySQL.
     */
    public static void recargar(VentanaCRUD v) {
        if (v instanceof CategoriaCRUD)
            v.cargarTabla("SELECT id_Categoria,nombre,descripcion,IF(estado,'Activo','Inactivo') FROM Categoria ORDER BY id_Categoria");
        else if (v instanceof SubcategoriaCRUD)
            v.cargarTabla("SELECT s.id_Subcategoria,c.nombre,s.nombre,s.descripcion,IF(s.estado,'Activo','Inactivo') FROM Subcategoria s JOIN Categoria c ON c.id_Categoria=s.id_Categoria ORDER BY s.id_Subcategoria");
        else if (v instanceof ProductoCRUD)
            v.cargarTabla("SELECT p.id_Producto,p.nombre,p.descripcion,p.precio,p.imagen,IF(p.estado,'Activo','Inactivo'),s.nombre FROM Producto p JOIN Subcategoria s ON s.id_Subcategoria=p.id_Subcategoria ORDER BY p.id_Producto");
        else if (v instanceof IngredienteCRUD)
            v.cargarTabla("SELECT id_Ingrediente,nombre,cantidad,cantidad_minima,fecha_creacion,fecha_actualizacion,descripcion,precio,imagen,IF(estado,'Activo','Inactivo') FROM Ingrediente ORDER BY id_Ingrediente");
        else if (v instanceof RecetaCRUD)
            v.cargarTabla("SELECT r.id_Receta,r.cantidad,p.nombre,i.nombre FROM Receta r JOIN Producto p ON p.id_Producto=r.id_Producto JOIN Ingrediente i ON i.id_Ingrediente=r.id_Ingrediente ORDER BY r.id_Receta");
        else if (v instanceof ComboCRUD)
            // Se muestra el tipo y la subcategoría, que antes quedaban fuera
            // de la tabla, y se indica cuál es el precio que aplica según
            // el tipo del combo.
            v.cargarTabla("SELECT c.id_Combo,c.nombre,c.descripcion,c.tipo,IFNULL(s.nombre,'-'),IF(c.tipo='Independiente',c.precio,c.precio_extra),c.imagen,IF(c.estado,'Activo','Inactivo') FROM Combo c LEFT JOIN Subcategoria s ON s.id_Subcategoria=c.id_Subcategoria ORDER BY c.id_Combo");
        else if (v instanceof GrupoComboCRUD)
            // IF(obligatorio,...) traduce el booleano a "Sí"/"No" para que
            // la tabla muestre lo mismo que el formulario, en vez de "true".
            v.cargarTabla("SELECT g.id_GrupoCombo,c.nombre,g.nombre,IF(g.obligatorio,'Sí','No'),g.cantidad_minima,g.cantidad_maxima FROM Grupo_Combo g JOIN Combo c ON c.id_Combo=g.id_Combo ORDER BY g.id_GrupoCombo");
        else if (v instanceof OpcionComboCRUD)
            v.cargarTabla("SELECT o.id_OpcionCombo,o.precio_extra,g.nombre,p.nombre FROM Opcion_Combo o JOIN Grupo_Combo g ON g.id_GrupoCombo=o.id_GrupoCombo JOIN Producto p ON p.id_Producto=o.id_Producto ORDER BY o.id_OpcionCombo");
        else if (v instanceof ProductoComboCRUD)
            v.cargarTabla("SELECT pc.id_ProductoCombo,p.nombre,c.nombre FROM Producto_Combo pc JOIN Producto p ON p.id_Producto=pc.id_Producto JOIN Combo c ON c.id_Combo=pc.id_Combo ORDER BY pc.id_ProductoCombo");
        else if (v instanceof DescuentoCRUD)
            v.cargarTabla("SELECT id_Descuento,nombre,descripcion,valor,cantidad_minima,fecha_inicio,fecha_fin,IF(estado,'Activo','Inactivo'),tipo FROM Descuento ORDER BY id_Descuento");
        else if (v instanceof UsuarioCRUD)
            // Se usa IF(estado,...) para mostrar "Activo"/"Inactivo" igual que
            // en el resto de tablas, en vez de mostrar el 1/0 crudo del BOOLEAN.
            v.cargarTabla("SELECT id_Usuario,nombre_usuario,nombre,apellido,"
                    // La contraseña NO se muestra. Antes esta columna
                    // traía el texto plano y cualquier persona que pasara
                    // frente al monitor podía leer las contraseñas de
                    // todos los usuarios, incluido el administrador.
                    // Ahora solo se dice si la contraseña está puesta o no,
                    // que es toda la información que un administrador
                    // necesita ver sin conocer el secreto.
                    + "CASE WHEN contrasena IS NULL OR contrasena='' "
                    + "THEN 'Sin asignar' ELSE 'Asignada' END AS contrasena,"
                    + "correo_electronico,rol,IF(estado,'Activo','Inactivo') "
                    + "FROM Usuario ORDER BY id_Usuario");
        else if (v instanceof GrupoPersonalizacionCRUD)
            v.cargarTabla("SELECT g.id_GrupoPersonalizacion,p.nombre,g.nombre,IF(g.obligatorio,'Sí','No'),g.cantidad_minima,g.cantidad_maxima FROM Grupo_Personalizacion g JOIN Producto p ON p.id_Producto=g.id_Producto ORDER BY g.id_GrupoPersonalizacion");
        else if (v instanceof OpcionPersonalizacionCRUD)
            v.cargarTabla("SELECT o.id_OpcionPersonalizacion,o.precio_extra,g.nombre,i.nombre,IF(o.es_predeterminada,'Sí','No') FROM Opcion_Personalizacion o JOIN Grupo_Personalizacion g ON g.id_GrupoPersonalizacion=o.id_GrupoPersonalizacion JOIN Ingrediente i ON i.id_Ingrediente=o.id_Ingrediente ORDER BY o.id_OpcionPersonalizacion");
        else if (v instanceof MovimientoInventarioCRUD)
            // Se usa LEFT JOIN para que un movimiento siga apareciendo
            // aunque su ingrediente o su usuario ya no existan: si se
            // Inner Join, esos movimientos desaparecerían del historial.
            v.cargarTabla("SELECT m.id_Movimiento,i.nombre,m.tipo,m.cantidad,m.cantidad_anterior,m.cantidad_total,m.motivo,m.fecha_movimiento,u.nombre_usuario,IFNULL(m.id_Venta,'-') FROM Movimiento_Inventario m LEFT JOIN Ingrediente i ON i.id_Ingrediente=m.id_Ingrediente LEFT JOIN Usuario u ON u.id_Usuario=m.id_Usuario ORDER BY m.id_Movimiento");
    }

    //---------------------- B U S C A R ----------------------
    /**
     * Busca un registro por su ID y lo muestra en el formulario.
     *
     * El ID se toma del campo de texto de la pantalla, no de una ventana
     * emergente aparte. Antes se pedía con JOptionPane, lo que obligaba a
     * teclear el ID en un cuadro flotante mientras el campo de la pantalla
     * quedaba vacío; ahora se usa el mismo campo que ya muestra el ID del
     * registro seleccionado.
     *
     * Además, la tabla queda con una sola fila: la del registro buscado.
     * Así el resultado de la búsqueda se ve en la tabla y no solo en los
     * campos, que es lo que se espera al buscar.
     */
    public static void buscar(VentanaCRUD v) {

        String idTexto = v.getCampoId().getText().trim();

        if (idTexto.isEmpty()) {

            v.mensaje("Ingrese el ID del registro a buscar.");

            return;
        }

        int id;

        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {

            v.mensaje("El ID debe ser un número.");

            return;
        }

        if (id <= 0) {

            v.mensaje("El ID debe ser un número mayor que cero.");

            return;
        }

        if (!cargarRegistro(v, id)) {

            v.mensaje("No se encontró el registro con ID " + id + ".");

            return;
        }

        // Antes de reducir la tabla se recarga completa. Si el usuario
        // busca dos veces seguidas, la segunda búsqueda debe funcionar
        // igual: la tabla quedó con una sola fila la primera vez, y sin
        // recargarla el segundo registro ya no estaría en ella.
        recargar(v);

        // Se deja la tabla mostrando solo la fila buscada, para que el
        // resultado de la búsqueda se vea claramente.
        v.encontrarEnTabla(id);

        actualizarBotonEstado(v, id);
    }

    //---------------------- S E L E C C I Ó N ----------------------
    public static void seleccionada(VentanaCRUD v) {
        int id = v.idSeleccionado();
        if (id > 0) {
            cargarRegistro(v, id);
            // El botón de estado debe reflejar lo que el usuario tiene
            // seleccionado: si el registro está activo ofrezco
            // "Desactivar"; si ya está inactivo ofrezco "Activar".
            actualizarBotonEstado(v, id);
        }
    }

    /**
     * Consulta si el registro seleccionado está activo y ajusta el texto
     * y el color del botón "Desactivar"/"Activar".
     *
     * No todas las tablas tienen columna "estado" (por ejemplo Receta o
     * Producto_Combo no la tienen), así que primero se identifica a qué
     * tabla y columna de ID corresponde el CRUD. Si la tabla no tiene
     * estado, el botón se deja como "Desactivar" sin consultar nada.
     */
    private static void actualizarBotonEstado(VentanaCRUD v, int id) {

        String tabla = null;
        String columnaId = null;

        if (v instanceof CategoriaCRUD) { tabla = "Categoria"; columnaId = "id_Categoria"; }
        else if (v instanceof SubcategoriaCRUD) { tabla = "Subcategoria"; columnaId = "id_Subcategoria"; }
        else if (v instanceof ProductoCRUD) { tabla = "Producto"; columnaId = "id_Producto"; }
        else if (v instanceof IngredienteCRUD) { tabla = "Ingrediente"; columnaId = "id_Ingrediente"; }
        else if (v instanceof ComboCRUD) { tabla = "Combo"; columnaId = "id_Combo"; }
        else if (v instanceof DescuentoCRUD) { tabla = "Descuento"; columnaId = "id_Descuento"; }
        else if (v instanceof UsuarioCRUD) { tabla = "Usuario"; columnaId = "id_Usuario"; }

        // La tabla no tiene columna "estado": el CRUD ni siquiera muestra
        // el botón, así que no hay nada que ajustar.
        if (tabla == null) {
            return;
        }

        Object[] f = v.adminDAO.consultarUno(
                "SELECT estado FROM " + tabla + " WHERE " + columnaId + " = ?",
                id
        );

        if (f == null) {
            return;
        }

        v.actualizarBotonEstado(bool(f[0]));
    }

    private static boolean cargarRegistro(VentanaCRUD v, int id) {
        Object[] f;
        if (v instanceof CategoriaCRUD) {
            f=v.adminDAO.consultarUno("SELECT id_Categoria,nombre,descripcion FROM Categoria WHERE id_Categoria=?",id); if(f==null)return false;
            CategoriaCRUD x=(CategoriaCRUD)v; v.getCampoId().setText(f[0]+""); x.getCampoNombre().setText(txt(f[1])); x.getCampoDescripcion().setText(txt(f[2]));
        } else if (v instanceof SubcategoriaCRUD) {
            f=v.adminDAO.consultarUno("SELECT id_Subcategoria,id_Categoria,nombre,descripcion FROM Subcategoria WHERE id_Subcategoria=?",id); if(f==null)return false;
            SubcategoriaCRUD x=(SubcategoriaCRUD)v; v.getCampoId().setText(f[0]+""); v.adminDAO.seleccionarCombo(x.getComboCategoria(),num(f[1])); x.getCampoNombre().setText(txt(f[2])); x.getCampoDescripcion().setText(txt(f[3]));
        } else if (v instanceof ProductoCRUD) {
            f=v.adminDAO.consultarUno("SELECT id_Producto,nombre,descripcion,precio,imagen,id_Subcategoria FROM Producto WHERE id_Producto=?",id); if(f==null)return false;
            ProductoCRUD x=(ProductoCRUD)v; v.getCampoId().setText(f[0]+""); x.getCampoNombre().setText(txt(f[1])); x.getCampoDescripcion().setText(txt(f[2])); x.getCampoPrecio().setText(txt(f[3]));
            // Se trae también la imagen para poder mostrarla en el
            // componente al seleccionar el registro.
            mostrarImagenGuardada(v,txt(f[4]));
            v.adminDAO.seleccionarCombo(x.getComboSubcategoria(),num(f[5]));
        } else if (v instanceof IngredienteCRUD) {
            f=v.adminDAO.consultarUno("SELECT id_Ingrediente,nombre,cantidad,cantidad_minima,descripcion,precio,imagen FROM Ingrediente WHERE id_Ingrediente=?",id); if(f==null)return false;
            IngredienteCRUD x=(IngredienteCRUD)v; v.getCampoId().setText(f[0]+""); x.getCampoNombre().setText(txt(f[1])); x.getCampoCantidad().setText(txt(f[2])); x.getCampoCantidadMinima().setText(txt(f[3])); x.getCampoDescripcion().setText(txt(f[4])); x.getCampoPrecio().setText(txt(f[5]));
            // Se muestra la imagen del ingrediente seleccionado.
            mostrarImagenGuardada(v,txt(f[6]));
        } else if (v instanceof RecetaCRUD) {
            f=v.adminDAO.consultarUno("SELECT id_Receta,cantidad,id_Producto,id_Ingrediente FROM Receta WHERE id_Receta=?",id); if(f==null)return false;
            RecetaCRUD x=(RecetaCRUD)v; v.getCampoId().setText(f[0]+""); x.getCampoCantidad().setText(txt(f[1])); v.adminDAO.seleccionarCombo(x.getComboProducto(),num(f[2])); v.adminDAO.seleccionarCombo(x.getComboIngrediente(),num(f[3]));
        } else if (v instanceof ComboCRUD) {
            // Se trae también el tipo y la subcategoría, y se calcula qué
            // precio corresponde mostrar en el formulario: el propio si el
            // combo es Independiente, o el extra si es Complementario.
            f=v.adminDAO.consultarUno("SELECT id_Combo,nombre,descripcion,tipo,precio,precio_extra,imagen,id_Subcategoria FROM Combo WHERE id_Combo=?",id); if(f==null)return false;
            ComboCRUD x=(ComboCRUD)v; v.getCampoId().setText(f[0]+""); x.getCampoNombre().setText(txt(f[1])); x.getCampoDescripcion().setText(txt(f[2]));
            x.getCampoTipo().setSelectedItem(txt(f[3]));
            boolean comboIndependiente="Independiente".equals(txt(f[3]));
            x.getCampoPrecio().setText(txt(comboIndependiente?f[4]:f[5]));
            // Se muestra la imagen del combo seleccionado.
            mostrarImagenGuardada(v,txt(f[6]));
            if(f[7]!=null) v.adminDAO.seleccionarCombo(x.getCampoSubcategoria(),num(f[7]));
        } else if (v instanceof GrupoComboCRUD) {
            f=v.adminDAO.consultarUno("SELECT id_GrupoCombo,id_Combo,nombre,obligatorio,cantidad_minima,cantidad_maxima FROM Grupo_Combo WHERE id_GrupoCombo=?",id); if(f==null)return false;
            GrupoComboCRUD x=(GrupoComboCRUD)v; v.getCampoId().setText(f[0]+""); v.adminDAO.seleccionarCombo(x.getCampoCombo(),num(f[1])); x.getCampoNombre().setText(txt(f[2])); x.getCampoObligatorio().setSelectedItem(siNo(f[3])); x.getCampoCantidadMinima().setText(txt(f[4])); x.getCampoCantidadMaxima().setText(txt(f[5]));
        } else if (v instanceof OpcionComboCRUD) {
            f=v.adminDAO.consultarUno("SELECT id_OpcionCombo,precio_extra,id_GrupoCombo,id_Producto FROM Opcion_Combo WHERE id_OpcionCombo=?",id); if(f==null)return false;
            OpcionComboCRUD x=(OpcionComboCRUD)v; v.getCampoId().setText(f[0]+""); x.getCampoPrecioExtra().setText(txt(f[1])); v.adminDAO.seleccionarCombo(x.getCampoGrupoCombo(),num(f[2])); v.adminDAO.seleccionarCombo(x.getCampoProducto(),num(f[3]));
        } else if (v instanceof ProductoComboCRUD) {
            f=v.adminDAO.consultarUno("SELECT id_ProductoCombo,id_Producto,id_Combo FROM Producto_Combo WHERE id_ProductoCombo=?",id); if(f==null)return false;
            ProductoComboCRUD x=(ProductoComboCRUD)v; v.getCampoId().setText(f[0]+""); v.adminDAO.seleccionarCombo(x.getCampoProducto(),num(f[1])); v.adminDAO.seleccionarCombo(x.getCampoCombo(),num(f[2]));
        } else if (v instanceof DescuentoCRUD) {
            f=v.adminDAO.consultarUno("SELECT id_Descuento,nombre,descripcion,valor,cantidad_minima,fecha_inicio,fecha_fin,tipo FROM Descuento WHERE id_Descuento=?",id); if(f==null)return false;
            DescuentoCRUD x=(DescuentoCRUD)v; v.getCampoId().setText(f[0]+""); x.getCampoNombre().setText(txt(f[1])); x.getCampoDescripcion().setText(txt(f[2])); x.getCampoValor().setText(txt(f[3])); x.getCampoCantidadMinima().setText(txt(f[4])); setFecha(x.getCalendarioInicio(),f[5]); setFecha(x.getCalendarioFin(),f[6]); x.getComboTipo().setSelectedItem(txt(f[7]));
        } else if (v instanceof UsuarioCRUD) {
            f=v.adminDAO.consultarUno("SELECT id_Usuario,nombre_usuario,nombre,apellido,correo_electronico,rol FROM Usuario WHERE id_Usuario=?",id); if(f==null)return false;
            // La contraseña no se trae de la base de datos ni se pone en
            // el formulario. Antes sí se cargaba, y eso dejaba la
            // contraseña escrita en pantalla cada vez que se seleccionaba
            // un usuario para editarlo. El campo queda vacío a propósito:
            // si el administrador quiere cambiarla, la escribe; si lo deja
            // vacío, se conserva la que ya tenía (ver actualizar()).
            UsuarioCRUD x=(UsuarioCRUD)v; v.getCampoId().setText(f[0]+""); x.getCampoNombreUsuario().setText(txt(f[1])); x.getCampoNombre().setText(txt(f[2])); x.getCampoApellido().setText(txt(f[3])); x.getCampoCorreo().setText(txt(f[4])); x.getComboRol().setSelectedItem(txt(f[5]));
        } else if (v instanceof GrupoPersonalizacionCRUD) {
            f=v.adminDAO.consultarUno("SELECT id_GrupoPersonalizacion,id_Producto,nombre,obligatorio,cantidad_minima,cantidad_maxima FROM Grupo_Personalizacion WHERE id_GrupoPersonalizacion=?",id); if(f==null)return false;
            GrupoPersonalizacionCRUD x=(GrupoPersonalizacionCRUD)v; v.getCampoId().setText(f[0]+""); v.adminDAO.seleccionarCombo(x.getCampoProducto(),num(f[1])); x.getCampoNombre().setText(txt(f[2])); x.getCampoObligatorio().setSelectedItem(siNo(f[3])); x.getCampoCantidadMinima().setText(txt(f[4])); x.getCampoCantidadMaxima().setText(txt(f[5]));
        } else if (v instanceof OpcionPersonalizacionCRUD) {
            f=v.adminDAO.consultarUno("SELECT id_OpcionPersonalizacion,id_GrupoPersonalizacion,id_Ingrediente,precio_extra,es_predeterminada FROM Opcion_Personalizacion WHERE id_OpcionPersonalizacion=?",id); if(f==null)return false;
            OpcionPersonalizacionCRUD x=(OpcionPersonalizacionCRUD)v; v.getCampoId().setText(f[0]+""); v.adminDAO.seleccionarCombo(x.getCampoGrupoPersonalizacion(),num(f[1])); v.adminDAO.seleccionarCombo(x.getCampoIngrediente(),num(f[2])); x.getCampoPrecioExtra().setText(txt(f[3])); x.getCampoEsPredeterminada().setSelectedItem(siNo(f[4]));
        } else return false;
        return true;
    }

    //---------------------- C R E A R ----------------------
    public static void crear(VentanaCRUD v) {
        boolean ok=false;
        try {
            if (v instanceof CategoriaCRUD) {
                CategoriaCRUD x=(CategoriaCRUD)v;
                validarCategoria(v,x.getCampoNombre().getText(),x.getCampoDescripcion().getText(),true,0);
                ok=v.adminDAO.ejecutar("INSERT INTO Categoria(nombre,descripcion) VALUES(?,?)",x.getCampoNombre().getText().trim(),x.getCampoDescripcion().getText().trim());
            } else if (v instanceof SubcategoriaCRUD) {
                SubcategoriaCRUD x=(SubcategoriaCRUD)v;
                validarCategoria(v,x.getCampoNombre().getText(),x.getCampoDescripcion().getText(),true,0);
                ok=v.adminDAO.ejecutar("INSERT INTO Subcategoria(id_Categoria,nombre,descripcion) VALUES(?,?,?)",v.adminDAO.idCombo(x.getComboCategoria()),x.getCampoNombre().getText().trim(),x.getCampoDescripcion().getText().trim());
            } else if (v instanceof ProductoCRUD) {
                ProductoCRUD x=(ProductoCRUD)v; requerido(x.getCampoNombre().getText()); double precio=decimal(x.getCampoPrecio().getText());
                validarTexto(v,"Producto",x.getCampoNombre().getText(),x.getCampoDescripcion().getText(),"nombre",true,0);
                precioPositivo(precio);
                ok=v.adminDAO.ejecutar("INSERT INTO Producto(nombre,descripcion,precio,imagen,id_Subcategoria) VALUES(?,?,?,?,?)",x.getCampoNombre().getText().trim(),x.getCampoDescripcion().getText().trim(),precio,imagen(v),v.adminDAO.idCombo(x.getComboSubcategoria()));
            } else if (v instanceof IngredienteCRUD) {
                IngredienteCRUD x=(IngredienteCRUD)v; requerido(x.getCampoNombre().getText()); int min=entero(x.getCampoCantidadMinima().getText()); double precio=decimalOpcional(x.getCampoPrecio().getText());
                validarTexto(v,"Ingrediente",x.getCampoNombre().getText(),x.getCampoDescripcion().getText(),"nombre",true,0);
                ok=v.adminDAO.ejecutar("INSERT INTO Ingrediente(nombre,cantidad,cantidad_minima,descripcion,precio,imagen) VALUES(?,?,?,?,?,?)",x.getCampoNombre().getText().trim(),100,min,x.getCampoDescripcion().getText().trim(),precio,imagen(v));
            } else if (v instanceof RecetaCRUD) {
                RecetaCRUD x=(RecetaCRUD)v; int cantidad=entero(x.getCampoCantidad().getText());
                // Una receta con cantidad 0 o negativa no describe nada útil
                // sobre cómo preparar el producto.
                if(cantidad<=0)throw new IllegalArgumentException("La cantidad de la receta debe ser mayor que cero.");
                ok=v.adminDAO.ejecutar("INSERT INTO Receta(cantidad,id_Producto,id_Ingrediente) VALUES(?,?,?)",cantidad,v.adminDAO.idCombo(x.getComboProducto()),v.adminDAO.idCombo(x.getComboIngrediente()));
            } else if (v instanceof ComboCRUD) {
                ComboCRUD x=(ComboCRUD)v; requerido(x.getCampoNombre().getText()); double precio=decimal(x.getCampoPrecio().getText());
                // La tabla Combo tiene una restricción CHECK que define cómo
                // se usan sus dos columnas de precio según el tipo:
                //   - "Independiente":  tiene precio propio (precio > 0),
                //     precio_extra = 0 y sí necesita subcategoría.
                //   - "Complementario": no tiene precio propio (precio = 0),
                //     su precio_extra se suma al producto y no lleva
                //     subcategoría.
                // El formulario tiene un solo campo de precio, que se
                // interpreta según el tipo elegido. Si no se respetara esta
                // regla, MySQL rechazaría el INSERT con un error poco claro.
                String tipoCombo=String.valueOf(x.getCampoTipo().getSelectedItem());
                int subcategoriaCombo=v.adminDAO.idCombo(x.getCampoSubcategoria());
                boolean independiente="Independiente".equals(tipoCombo);

                if(independiente){
                    precioPositivo(precio);
                    if(subcategoriaCombo<=0)
                        throw new IllegalArgumentException("Un combo Independiente debe tener una subcategoría.");
                }else{
                    // En un Complementario el precio extra puede ser 0
                    // (por ejemplo "papas gratis"), pero nunca negativo.
                    if(precio<0)
                        throw new IllegalArgumentException("El precio del combo no puede ser negativo.");
                }

                ok=v.adminDAO.ejecutar("INSERT INTO Combo(nombre,descripcion,tipo,precio,precio_extra,imagen,id_Subcategoria) VALUES(?,?,?,?,?,?,?)",x.getCampoNombre().getText().trim(),x.getCampoDescripcion().getText().trim(),tipoCombo,independiente?precio:0.0,independiente?0.0:precio,imagen(v),independiente?subcategoriaCombo:null);
            } else if (v instanceof GrupoComboCRUD) {
                GrupoComboCRUD x=(GrupoComboCRUD)v; requerido(x.getCampoNombre().getText());
                rangoCantidades(entero(x.getCampoCantidadMinima().getText()),entero(x.getCampoCantidadMaxima().getText()),"el grupo");
                ok=v.adminDAO.ejecutar("INSERT INTO Grupo_Combo(id_Combo,nombre,obligatorio,cantidad_minima,cantidad_maxima) VALUES(?,?,?,?,?)",v.adminDAO.idCombo(x.getCampoCombo()),x.getCampoNombre().getText().trim(),verdadero(x.getCampoObligatorio()),entero(x.getCampoCantidadMinima().getText()),entero(x.getCampoCantidadMaxima().getText()));
            } else if (v instanceof OpcionComboCRUD) {
                OpcionComboCRUD x=(OpcionComboCRUD)v;
                ok=v.adminDAO.ejecutar("INSERT INTO Opcion_Combo(precio_extra,id_GrupoCombo,id_Producto) VALUES(?,?,?)",decimalOpcional(x.getCampoPrecioExtra().getText()),v.adminDAO.idCombo(x.getCampoGrupoCombo()),v.adminDAO.idCombo(x.getCampoProducto()));
            } else if (v instanceof ProductoComboCRUD) {
                ProductoComboCRUD x=(ProductoComboCRUD)v;
                ok=v.adminDAO.ejecutar("INSERT INTO Producto_Combo(id_Producto,id_Combo) VALUES(?,?)",v.adminDAO.idCombo(x.getCampoProducto()),v.adminDAO.idCombo(x.getCampoCombo()));
            } else if (v instanceof DescuentoCRUD) {
                DescuentoCRUD x=(DescuentoCRUD)v; requerido(x.getCampoNombre().getText());
                // El valor de un descuento debe ser positivo y, si es un
                // porcentaje, no puede pasar del 100% porque sería absurdo.
                double valor=decimal(x.getCampoValor().getText());
                precioPositivo(valor);
                if("Porcentaje".equals(x.getComboTipo().getSelectedItem())&&valor>100)
                    throw new IllegalArgumentException("El porcentaje del descuento no puede superar el 100%.");
                rangoFechas(x.getCalendarioInicio().getFecha(),x.getCalendarioFin().getFecha());
                ok=v.adminDAO.ejecutar("INSERT INTO Descuento(nombre,descripcion,valor,cantidad_minima,fecha_inicio,fecha_fin,tipo) VALUES(?,?,?,?,?,?,?)",x.getCampoNombre().getText().trim(),x.getCampoDescripcion().getText().trim(),valor,entero(x.getCampoCantidadMinima().getText()),Timestamp.valueOf(x.getCalendarioInicio().getFecha().atStartOfDay()),Timestamp.valueOf(x.getCalendarioFin().getFecha().atStartOfDay()),x.getComboTipo().getSelectedItem());
            } else if (v instanceof UsuarioCRUD) {
                UsuarioCRUD x=(UsuarioCRUD)v;
                // Validaciones del usuario antes de guardarlo.
                nombreUsuario(x.getCampoNombreUsuario().getText());
                requerido(x.getCampoNombre().getText());
                requerido(x.getCampoApellido().getText());
                contrasena(x.getCampoContrasena().getText());
                correoMoova(x.getCampoCorreo().getText());
                ok=v.adminDAO.ejecutar("INSERT INTO Usuario(nombre_usuario,nombre,apellido,contrasena,correo_electronico,rol) VALUES(?,?,?,?,?,?)",x.getCampoNombreUsuario().getText().trim(),x.getCampoNombre().getText().trim(),x.getCampoApellido().getText().trim(),x.getCampoContrasena().getText(),x.getCampoCorreo().getText().trim(),x.getComboRol().getSelectedItem());
            } else if (v instanceof GrupoPersonalizacionCRUD) {
                GrupoPersonalizacionCRUD x=(GrupoPersonalizacionCRUD)v; requerido(x.getCampoNombre().getText());
                rangoCantidades(entero(x.getCampoCantidadMinima().getText()),entero(x.getCampoCantidadMaxima().getText()),"el grupo");
                ok=v.adminDAO.ejecutar("INSERT INTO Grupo_Personalizacion(id_Producto,nombre,obligatorio,cantidad_minima,cantidad_maxima) VALUES(?,?,?,?,?)",v.adminDAO.idCombo(x.getCampoProducto()),x.getCampoNombre().getText().trim(),verdadero(x.getCampoObligatorio()),entero(x.getCampoCantidadMinima().getText()),entero(x.getCampoCantidadMaxima().getText()));
            } else if (v instanceof OpcionPersonalizacionCRUD) {
                OpcionPersonalizacionCRUD x=(OpcionPersonalizacionCRUD)v;
                ok=v.adminDAO.ejecutar("INSERT INTO Opcion_Personalizacion(id_GrupoPersonalizacion,id_Ingrediente,precio_extra,es_predeterminada) VALUES(?,?,?,?)",v.adminDAO.idCombo(x.getCampoGrupoPersonalizacion()),v.adminDAO.idCombo(x.getCampoIngrediente()),decimalOpcional(x.getCampoPrecioExtra().getText()),verdadero(x.getCampoEsPredeterminada()));
            }
            fin(v, ok, "Registro creado.");
        } catch (Exception e) { v.mensaje(e.getMessage()); }
    }

    //---------------------- A C T U A L I Z A R ----------------------
    public static void actualizar(VentanaCRUD v) {
        int id=idFormulario(v); if(id<=0){v.mensaje("Seleccione o busque un registro.");return;} boolean ok=false;
        try {
            if (v instanceof CategoriaCRUD) { CategoriaCRUD x=(CategoriaCRUD)v;
                // Al actualizar se pasa el id para que la búsqueda de
                // duplicados no detecte el propio registro.
                validarCategoria(v,x.getCampoNombre().getText(),x.getCampoDescripcion().getText(),false,id);
                ok=v.adminDAO.ejecutar("UPDATE Categoria SET nombre=?,descripcion=? WHERE id_Categoria=?",x.getCampoNombre().getText().trim(),x.getCampoDescripcion().getText().trim(),id); }
            else if (v instanceof SubcategoriaCRUD) { SubcategoriaCRUD x=(SubcategoriaCRUD)v;
                validarCategoria(v,x.getCampoNombre().getText(),x.getCampoDescripcion().getText(),false,id);
                ok=v.adminDAO.ejecutar("UPDATE Subcategoria SET id_Categoria=?,nombre=?,descripcion=? WHERE id_Subcategoria=?",v.adminDAO.idCombo(x.getComboCategoria()),x.getCampoNombre().getText().trim(),x.getCampoDescripcion().getText().trim(),id); }
            else if (v instanceof ProductoCRUD) { ProductoCRUD x=(ProductoCRUD)v;
                validarTexto(v,"Producto",x.getCampoNombre().getText(),x.getCampoDescripcion().getText(),"nombre",false,id);
                precioPositivo(decimal(x.getCampoPrecio().getText()));
                ok=v.adminDAO.ejecutar("UPDATE Producto SET nombre=?,descripcion=?,precio=?,id_Subcategoria=?,imagen=COALESCE(?,imagen) WHERE id_Producto=?",x.getCampoNombre().getText().trim(),x.getCampoDescripcion().getText().trim(),decimal(x.getCampoPrecio().getText()),v.adminDAO.idCombo(x.getComboSubcategoria()),imagen(v),id); }
            else if (v instanceof IngredienteCRUD) { IngredienteCRUD x=(IngredienteCRUD)v;
                validarTexto(v,"Ingrediente",x.getCampoNombre().getText(),x.getCampoDescripcion().getText(),"nombre",false,id);
                ok=v.adminDAO.ejecutar("UPDATE Ingrediente SET nombre=?,cantidad_minima=?,descripcion=?,precio=?,imagen=COALESCE(?,imagen) WHERE id_Ingrediente=?",x.getCampoNombre().getText().trim(),entero(x.getCampoCantidadMinima().getText()),x.getCampoDescripcion().getText().trim(),decimalOpcional(x.getCampoPrecio().getText()),imagen(v),id); }
            else if (v instanceof RecetaCRUD) { RecetaCRUD x=(RecetaCRUD)v; ok=v.adminDAO.ejecutar("UPDATE Receta SET cantidad=?,id_Producto=?,id_Ingrediente=? WHERE id_Receta=?",entero(x.getCampoCantidad().getText()),v.adminDAO.idCombo(x.getComboProducto()),v.adminDAO.idCombo(x.getComboIngrediente()),id); }
            else if (v instanceof ComboCRUD) { ComboCRUD x=(ComboCRUD)v; requerido(x.getCampoNombre().getText());
                // Se respeta la misma regla del CHECK de la tabla Combo que
                // al crear: el único campo de precio del formulario se
                // guarda en "precio" si el combo es Independiente, o en
                // "precio_extra" si es Complementario.
                String tipoCombo=String.valueOf(x.getCampoTipo().getSelectedItem());
                double precioCombo=decimal(x.getCampoPrecio().getText());
                int subcategoriaCombo=v.adminDAO.idCombo(x.getCampoSubcategoria());
                boolean independiente="Independiente".equals(tipoCombo);

                if(independiente){
                    precioPositivo(precioCombo);
                    if(subcategoriaCombo<=0)
                        throw new IllegalArgumentException("Un combo Independiente debe tener una subcategoría.");
                }else if(precioCombo<0){
                    throw new IllegalArgumentException("El precio del combo no puede ser negativo.");
                }

                ok=v.adminDAO.ejecutar("UPDATE Combo SET nombre=?,descripcion=?,tipo=?,precio=?,precio_extra=?,imagen=COALESCE(?,imagen),id_Subcategoria=? WHERE id_Combo=?",x.getCampoNombre().getText().trim(),x.getCampoDescripcion().getText().trim(),tipoCombo,independiente?precioCombo:0.0,independiente?0.0:precioCombo,imagen(v),independiente?subcategoriaCombo:null,id); }
            else if (v instanceof GrupoComboCRUD) { GrupoComboCRUD x=(GrupoComboCRUD)v; ok=v.adminDAO.ejecutar("UPDATE Grupo_Combo SET id_Combo=?,nombre=?,obligatorio=?,cantidad_minima=?,cantidad_maxima=? WHERE id_GrupoCombo=?",v.adminDAO.idCombo(x.getCampoCombo()),x.getCampoNombre().getText().trim(),verdadero(x.getCampoObligatorio()),entero(x.getCampoCantidadMinima().getText()),entero(x.getCampoCantidadMaxima().getText()),id); }
            else if (v instanceof OpcionComboCRUD) { OpcionComboCRUD x=(OpcionComboCRUD)v; ok=v.adminDAO.ejecutar("UPDATE Opcion_Combo SET precio_extra=?,id_GrupoCombo=?,id_Producto=? WHERE id_OpcionCombo=?",decimalOpcional(x.getCampoPrecioExtra().getText()),v.adminDAO.idCombo(x.getCampoGrupoCombo()),v.adminDAO.idCombo(x.getCampoProducto()),id); }
            else if (v instanceof ProductoComboCRUD) { ProductoComboCRUD x=(ProductoComboCRUD)v; ok=v.adminDAO.ejecutar("UPDATE Producto_Combo SET id_Producto=?,id_Combo=? WHERE id_ProductoCombo=?",v.adminDAO.idCombo(x.getCampoProducto()),v.adminDAO.idCombo(x.getCampoCombo()),id); }
            else if (v instanceof DescuentoCRUD) { DescuentoCRUD x=(DescuentoCRUD)v; ok=v.adminDAO.ejecutar("UPDATE Descuento SET nombre=?,descripcion=?,valor=?,cantidad_minima=?,fecha_inicio=?,fecha_fin=?,tipo=? WHERE id_Descuento=?",x.getCampoNombre().getText().trim(),x.getCampoDescripcion().getText().trim(),decimal(x.getCampoValor().getText()),entero(x.getCampoCantidadMinima().getText()),Timestamp.valueOf(x.getCalendarioInicio().getFecha().atStartOfDay()),Timestamp.valueOf(x.getCalendarioFin().getFecha().atStartOfDay()),x.getComboTipo().getSelectedItem(),id); }
            else if (v instanceof UsuarioCRUD) { UsuarioCRUD x=(UsuarioCRUD)v;
                // Se aplican las mismas validaciones que al crear, porque si no
                // un usuario podría cambiar su correo a un dominio externo.
                nombreUsuario(x.getCampoNombreUsuario().getText());
                requerido(x.getCampoNombre().getText());
                requerido(x.getCampoApellido().getText());
                // La contraseña solo se valida si se escribió algo. Vacía
                // significa "no la quiero cambiar", no "quitarle la
                // contraseña", porque el campo nunca se llena al
                // seleccionar un registro.
                if(!x.getCampoContrasena().getText().isEmpty())
                    contrasena(x.getCampoContrasena().getText());
                correoMoova(x.getCampoCorreo().getText());
                // Se hacen dos UPDATE distintos según el caso, porque el
                // campo de contraseña llega vacío cuando el administrador
                // solo quiere corregir otro dato (por ejemplo el correo).
                //
                // Si se mandara el valor vacío siempre, cualquier edición
                // borraría la contraseña del usuario y nadie podría volver
                // a entrar con ella.
                String nueva = x.getCampoContrasena().getText();
                if(nueva.isEmpty()){
                    // Se actualizan todos los campos MENOS la contraseña,
                    // así esta queda como estaba.
                    ok=v.adminDAO.ejecutar("UPDATE Usuario SET nombre_usuario=?,nombre=?,apellido=?,correo_electronico=?,rol=? WHERE id_Usuario=?",x.getCampoNombreUsuario().getText().trim(),x.getCampoNombre().getText().trim(),x.getCampoApellido().getText().trim(),x.getCampoCorreo().getText().trim(),x.getComboRol().getSelectedItem(),id);
                }else{
                    // Sí escribió una contraseña: esta reemplaza la anterior.
                    ok=v.adminDAO.ejecutar("UPDATE Usuario SET nombre_usuario=?,nombre=?,apellido=?,contrasena=?,correo_electronico=?,rol=? WHERE id_Usuario=?",x.getCampoNombreUsuario().getText().trim(),x.getCampoNombre().getText().trim(),x.getCampoApellido().getText().trim(),nueva,x.getCampoCorreo().getText().trim(),x.getComboRol().getSelectedItem(),id);
                } }
            else if (v instanceof GrupoPersonalizacionCRUD) { GrupoPersonalizacionCRUD x=(GrupoPersonalizacionCRUD)v; ok=v.adminDAO.ejecutar("UPDATE Grupo_Personalizacion SET id_Producto=?,nombre=?,obligatorio=?,cantidad_minima=?,cantidad_maxima=? WHERE id_GrupoPersonalizacion=?",v.adminDAO.idCombo(x.getCampoProducto()),x.getCampoNombre().getText().trim(),verdadero(x.getCampoObligatorio()),entero(x.getCampoCantidadMinima().getText()),entero(x.getCampoCantidadMaxima().getText()),id); }
            else if (v instanceof OpcionPersonalizacionCRUD) { OpcionPersonalizacionCRUD x=(OpcionPersonalizacionCRUD)v; ok=v.adminDAO.ejecutar("UPDATE Opcion_Personalizacion SET id_GrupoPersonalizacion=?,id_Ingrediente=?,precio_extra=?,es_predeterminada=? WHERE id_OpcionPersonalizacion=?",v.adminDAO.idCombo(x.getCampoGrupoPersonalizacion()),v.adminDAO.idCombo(x.getCampoIngrediente()),decimalOpcional(x.getCampoPrecioExtra().getText()),verdadero(x.getCampoEsPredeterminada()),id); }
            fin(v,ok,"Registro actualizado.");

            // Si lo que se actualizó fue un ingrediente, se revisa si su
            // stock quedó por debajo del mínimo, porque al haber cambiado
            // el mínimo o alguna cantidad pudo generarse una alerta nueva.
            if(ok) revisarAlertaStock(v);
        } catch(Exception e){v.mensaje(e.getMessage());}
    }

    //------------------------------------------------------------------------
    //        A L E R T A   A U T O M Á T I C A   D E   S T O C K
    //------------------------------------------------------------------------
    /**
     * Revisa si algún ingrediente tiene menos existencias de las que
     * indica su cantidad mínima y, si es así, registra un incidente.
     *
     * ¿Por qué automáticamente? Si el stock baja del mínimo y nadie se
     * entera, el restaurante se queda sin un ingrediente a mitad del día
     * sin aviso. El incidente aparece en el contador "Alertas actuales"
     * del dashboard y en la pantalla de incidentes.
     *
     * La alerta desaparece sola cuando el stock vuelve a estar por encima
     * del mínimo: los incidentes de tipo "Ingrediente" que sigan abiertos
     * se marcan como resueltos en ese momento.
     *
     * Se usa al guardar un movimiento de inventario y al crear o
     * actualizar un ingrediente, que son las dos veces que el stock
     * puede cambiar de nivel.
     *
     * @param v ventana desde la que se llama, solo para el DAO.
     */
    public static void revisarAlertaStock(VentanaCRUD v) {

        // Se necesita un administrador activo al que atribuya el reporte.
        Object[] admin = v.adminDAO.consultarUno(
                "SELECT id_Usuario FROM Usuario WHERE rol='Administrador' AND estado=TRUE ORDER BY id_Usuario LIMIT 1");

        if (admin == null) {
            return;
        }

        int idAdmin = num(admin[0]);

        // Se buscan los ingredientes que están por debajo del mínimo.
        java.util.List<Object[]> bajos = v.adminDAO.consultar(
                "SELECT id_Ingrediente, nombre, cantidad, cantidad_minima "
                        + "FROM Ingrediente "
                        + "WHERE cantidad < cantidad_minima");

        for (Object[] ing : bajos) {

            int idIngrediente = num(ing[0]);
            String nombre = txt(ing[1]);
            int cantidad = (int) Math.floor(datoNumero(ing[2]));
            int minimo = (int) Math.floor(datoNumero(ing[3]));

            // Se revisa si ya existe una alerta abierta para este
            // ingrediente, para no crear una nueva en cada revisión y
            // llenar la pantalla de incidentes repetidos.
            Object[] abierta = v.adminDAO.consultarUno(
                    "SELECT id_Incidente FROM Incidente "
                            + "WHERE tipo = 'Ingrediente' "
                            + "AND id_Ingrediente = ? "
                            + "AND estado IN ('Pendiente','En proceso')",
                    idIngrediente);

            if (abierta == null) {

                // No hay alerta abierta: se crea una nueva.
                v.adminDAO.ejecutar(
                        "INSERT INTO Incidente(titulo,descripcion,fecha_reporte,fecha_resolucion,"
                                + "solucion,tipo,prioridad,estado,id_Usuario,id_Ingrediente,id_Producto) "
                                + "VALUES(?,?,CURRENT_TIMESTAMP,NULL,NULL,'Ingrediente',?,'Pendiente',?,?,NULL)",
                        "Stock bajo: " + nombre,
                        "El ingrediente \"" + nombre + "\" tiene " + cantidad
                                + " unidades, por debajo del mínimo requerido de "
                                + minimo + ". Reponer antes de que se agote.",
                        "Alta",
                        idAdmin,
                        idIngrediente);

            } else {
                // Ya hay una alerta abierta: se actualiza el texto con las
                // cantidades actuales, porque pueden haber cambiado.
                v.adminDAO.ejecutar(
                        "UPDATE Incidente SET descripcion = ? WHERE id_Incidente = ?",
                        "El ingrediente \"" + nombre + "\" tiene " + cantidad
                                + " unidades, por debajo del mínimo requerido de "
                                + minimo + ". Reponer antes de que se agote.",
                        num(abierta[0]));
            }
        }

        // Ahora se cierran las alertas de los ingredientes que ya
        // volvieron a tener stock suficiente.
        v.adminDAO.ejecutar(
                "UPDATE Incidente SET estado='Resuelta', "
                        + "solucion='El stock volvió al nivel requerido.', "
                        + "fecha_resolucion=CURRENT_TIMESTAMP "
                        + "WHERE tipo='Ingrediente' "
                        + "AND id_Ingrediente IS NOT NULL "
                        + "AND estado IN ('Pendiente','En proceso') "
                        + "AND id_Ingrediente IN ("
                        + "SELECT id_Ingrediente FROM Ingrediente WHERE cantidad >= cantidad_minima)");
    }

    //---------------------- A C T I V A R / D E S A C T I V A R ----------------------
    public static void desactivar(VentanaCRUD v) {
        int id=idFormulario(v); if(id<=0){v.mensaje("Seleccione o busque un registro.");return;}
        String tabla=null,col=null; boolean usuario=false;
        if(v instanceof CategoriaCRUD){tabla="Categoria";col="id_Categoria";}
        else if(v instanceof SubcategoriaCRUD){tabla="Subcategoria";col="id_Subcategoria";}
        else if(v instanceof ProductoCRUD){tabla="Producto";col="id_Producto";}
        else if(v instanceof IngredienteCRUD){tabla="Ingrediente";col="id_Ingrediente";}
        else if(v instanceof ComboCRUD){tabla="Combo";col="id_Combo";}
        else if(v instanceof DescuentoCRUD){tabla="Descuento";col="id_Descuento";}
        else if(v instanceof UsuarioCRUD){tabla="Usuario";col="id_Usuario";usuario=true;}
        if(tabla==null){v.mensaje("Este registro no utiliza estado.");return;}
        boolean ok;
        if(usuario){
            // La columna "estado" de Usuario es BOOLEAN, por eso se lee y se
            // escribe como booleano. Antes se comparaba contra el texto
            // "Activa", lo que hacía que el usuario quedara siempre
            // desactivado y no se pudiera volver a activar.
            Object[] f=v.adminDAO.consultarUno("SELECT estado FROM Usuario WHERE id_Usuario=?",id); if(f==null)return;
            ok=v.adminDAO.ejecutar("UPDATE Usuario SET estado=? WHERE id_Usuario=?",!bool(f[0]),id);
        }else{
            Object[] f=v.adminDAO.consultarUno("SELECT estado FROM "+tabla+" WHERE "+col+"=?",id); if(f==null)return;
            ok=v.adminDAO.ejecutar("UPDATE "+tabla+" SET estado=? WHERE "+col+"=?",!bool(f[0]),id);
        }

        // Se avisa al usuario qué se hizo, en lugar de un mensaje genérico.
        // Además se actualiza el texto del botón para que en el siguiente
        // clic ofrezca la acción contraria a la que se acaba de ejecutar.
        if(ok){
            recargar(v);
            actualizarBotonEstado(v,id);
        }

        fin(v,ok,ok?"Estado actualizado correctamente.":"No se pudo cambiar el estado.");
    }

    //---------------------- M O V I M I E N T O   S T O C K ----------------------
    public static void abrirMovimiento(IngredienteCRUD v) {
        int id=idFormulario(v);
        if(id<=0){ v.mensaje("Seleccione primero un ingrediente."); return; }
        ingredienteMovimiento=id;
        new MovimientoInventarioCRUD();
    }

    private static void inicializarMovimiento(MovimientoInventarioCRUD v) {
        v.prepararId("Movimiento_Inventario","id_Movimiento");
        recargar(v);
        if(ingredienteMovimiento==0){
            String s=JOptionPane.showInputDialog(v,"ID del ingrediente a modificar:");
            try{ ingredienteMovimiento=Integer.parseInt(s); }catch(Exception e){ ingredienteMovimiento=0; }
        }
        Object[] f=v.adminDAO.consultarUno("SELECT cantidad FROM Ingrediente WHERE id_Ingrediente=?",ingredienteMovimiento);
        v.getCampoCantidadActual().setEditable(false);
        v.getCampoCantidadActual().setText(f==null?"0":txt(f[0]));
        v.getBotonGuardar().addActionListener(e->guardarMovimiento(v));
    }

    private static void guardarMovimiento(MovimientoInventarioCRUD v) {
        try {
            int mov=entero(v.getCampoCantidad().getText());

            // La cantidad del movimiento debe ser positiva: un movimiento
            // de 0 o negativo no describe nada y desconfigura el inventario.
            if(mov<=0){v.mensaje("La cantidad del movimiento debe ser mayor que cero.");return;}

            requerido(v.getCampoMotivo().getText());

            Object[] fi=v.adminDAO.consultarUno("SELECT cantidad FROM Ingrediente WHERE id_Ingrediente=?",ingredienteMovimiento);
            if(fi==null){v.mensaje("Ingrediente no encontrado.");return;}
            int anterior=num(fi[0]); String tipo=String.valueOf(v.getCampoTipo().getSelectedItem()); int total;
            if("Ajuste".equals(tipo)) total=mov;
            else if("Salida".equals(tipo)||"Venta".equals(tipo)||"Merma".equals(tipo)) total=anterior-mov;
            else total=anterior+mov;
            if(total<0){v.mensaje("El stock no puede quedar negativo.");return;}

            // Se pide confirmación porque la operación descuenta inventario
            // de verdad: un clic por error afectaría el stock real.
            int confirmacion=Dialogo.confirmar(
                    v,
                    "Confirmar movimiento",
                    "Se registrará un movimiento de "+mov+" unidad(es) ("+tipo+").\n"
                            +"Stock actual: "+anterior+"  ->  Nuevo stock: "+total+"\n\n¿Desea continuar?");

            if(confirmacion!=Dialogo.SI) return;
            // La columna "estado" de Usuario es BOOLEAN, así que se compara con
            // TRUE. Si se comparara contra el texto 'Activa', MySQL lo
            // convertiría a 0 y la búsqueda nunca devolvería un administrador,
            // dejando bloqueado el registro de movimientos de inventario.
            Object[] fu=v.adminDAO.consultarUno("SELECT id_Usuario FROM Usuario WHERE rol='Administrador' AND estado=TRUE ORDER BY id_Usuario LIMIT 1");
            if(fu==null){v.mensaje("No hay un administrador activo para registrar el movimiento.");return;}
            int usuario=num(fu[0]);

            //---------------------- T R A N S A C C I Ó N
            // El cambio de stock y el registro del movimiento tienen que
            // ocurrir sí o sí juntos. Antes se hacían con dos llamadas
            // independientes a AdminDAO, y cada una abría su propia
            // conexión: si el INSERT fallaba, el UPDATE del stock ya
            // estaba hecho y no había forma de deshacerlo. El inventario
            // quedaba descuadrado sin ningún movimiento que lo explicara.
            //
            // Aquí ambas cosas usan la misma conexión y, si alguna falla,
            // se hace rollback para devolver el stock a su valor anterior.
            boolean okMovimiento = registrarMovimientoConTransaccion(
                    v,
                    ingredienteMovimiento,
                    mov,
                    anterior,
                    total,
                    tipo,
                    v.getCampoMotivo().getText().trim(),
                    usuario
            );

            boolean b=okMovimiento;

            if(b){
                // Se revisa el stock después de moverlo: si el ingrediente
                // quedó por debajo de su mínimo se crea la alerta, y si
                // vuelve a estar por encima se cierra la que tuviera.
                revisarAlertaStock(v);

                v.mensaje("Stock actualizado.");
                v.getCampoCantidad().setText("");
                v.getCampoMotivo().setText("");
                v.getCampoCantidadActual().setText(String.valueOf(total));
                recargar(v);
                v.prepararId("Movimiento_Inventario","id_Movimiento");
            }
            else v.mensaje("No se pudo guardar el movimiento.");
        }catch(Exception e){v.mensaje(e.getMessage());}
    }

    //------------------------------------------------------------------------
    //        R E G I S T R A R   M O V I M I E N T O   C O N   T R A N S A C C I Ó N
    //------------------------------------------------------------------------
    /**
     * Actualiza el stock del ingrediente y registra el movimiento dentro de
     * una transacción, de manera que ambos cambios se apliquen o ninguno.
     *
     * Por qué importa: son dos operaciones sobre datos que dependen entre
     * sí. Si el stock baja a 40 pero el asiento del movimiento falla por un
     * error, el inventario muestra 40 y no hay ningún registro que explique
     * por qué. Con la transacción, un error en cualquiera de las dos
     * deshace todo.
     *
     * MySQL tiene el control automático de transacciones activo por
     * defecto, así que alcanza con poner setAutoCommit(false) y luego
     * commit() o rollback() explícitos.
     *
     * @return true si las dos operaciones quedaron aplicadas.
     */
    private static boolean registrarMovimientoConTransaccion(
            VentanaCRUD v,
            int idIngrediente,
            int cantidad,
            int anterior,
            int total,
            String tipo,
            String motivo,
            int idUsuario
    ) {

        java.sql.Connection con = new conexion.Conexion_MOOVA().getConnection();

        if (con == null) {

            v.mensaje("No se pudo conectar a la base de datos.");

            return false;
        }

        try {

            // Se desactiva el guardado automático para poder deshacer todo
            // junto si algo sale mal.
            con.setAutoCommit(false);

            try (java.sql.PreparedStatement psStock = con.prepareStatement(
                    "UPDATE Ingrediente SET cantidad = ? WHERE id_Ingrediente = ?")) {

                psStock.setInt(1, total);
                psStock.setInt(2, idIngrediente);

                // Si no se actualizó ninguna fila es que el ingrediente ya
                // no existe, así que no tiene sentido seguir.
                if (psStock.executeUpdate() == 0) {

                    con.rollback();
                    v.mensaje("El ingrediente ya no existe.");
                    return false;
                }
            }

            try (java.sql.PreparedStatement psMov = con.prepareStatement(
                    "INSERT INTO Movimiento_Inventario "
                            + "(cantidad,cantidad_anterior,cantidad_total,motivo,"
                            + "tipo,id_Ingrediente,id_Usuario,id_Venta) "
                            + "VALUES(?,?,?,?,?,?,?,NULL)")) {

                psMov.setInt(1, cantidad);
                psMov.setInt(2, anterior);
                psMov.setInt(3, total);
                psMov.setString(4, motivo);
                psMov.setString(5, tipo);
                psMov.setInt(6, idIngrediente);
                psMov.setInt(7, idUsuario);

                psMov.executeUpdate();
            }

            // Las dos operaciones salieron bien: se confirma.
            con.commit();

            return true;

        } catch (java.sql.SQLException e) {

            // Cualquier error deshace el cambio de stock, para que el
            // inventario no quede descuadrado.
            try {
                con.rollback();
            } catch (java.sql.SQLException rollbackError) {
                System.out.println(
                        "No se pudo deshacer el movimiento: "
                                + rollbackError.getMessage()
                );
            }

            v.mensaje(
                    "No se pudo registrar el movimiento: "
                            + e.getMessage()
            );

            return false;

        } finally {

            try {
                con.close();
            } catch (java.sql.SQLException e) {
                System.out.println(
                        "Error al cerrar la conexión: "
                                + e.getMessage()
                );
            }
        }
    }

    //---------------------- H E L P E R S ----------------------
    private static void fin(VentanaCRUD v, boolean ok, String msg) {
        if(ok) v.mensaje(msg); else v.mensaje("No se pudo realizar la operación. Revise datos duplicados o la conexión.");
        if(ok){ v.limpiarCampos(); recargar(v); prepararIdSegun(v); if(v instanceof IngredienteCRUD)((IngredienteCRUD)v).getCampoCantidad().setText("100"); }
    }

    private static void prepararIdSegun(VentanaCRUD v){
        if(v instanceof CategoriaCRUD)v.prepararId("Categoria","id_Categoria");
        else if(v instanceof SubcategoriaCRUD)v.prepararId("Subcategoria","id_Subcategoria");
        else if(v instanceof ProductoCRUD)v.prepararId("Producto","id_Producto");
        else if(v instanceof IngredienteCRUD)v.prepararId("Ingrediente","id_Ingrediente");
        else if(v instanceof RecetaCRUD)v.prepararId("Receta","id_Receta");
        else if(v instanceof ComboCRUD)v.prepararId("Combo","id_Combo");
        else if(v instanceof GrupoComboCRUD)v.prepararId("Grupo_Combo","id_GrupoCombo");
        else if(v instanceof OpcionComboCRUD)v.prepararId("Opcion_Combo","id_OpcionCombo");
        else if(v instanceof ProductoComboCRUD)v.prepararId("Producto_Combo","id_ProductoCombo");
        else if(v instanceof DescuentoCRUD)v.prepararId("Descuento","id_Descuento");
        else if(v instanceof UsuarioCRUD)v.prepararId("Usuario","id_Usuario");
        else if(v instanceof GrupoPersonalizacionCRUD)v.prepararId("Grupo_Personalizacion","id_GrupoPersonalizacion");
        else if(v instanceof OpcionPersonalizacionCRUD)v.prepararId("Opcion_Personalizacion","id_OpcionPersonalizacion");
    }

    private static int idFormulario(VentanaCRUD v){try{return Integer.parseInt(v.getCampoId().getText().trim());}catch(Exception e){return v.idSeleccionado();}}
    private static int entero(String s){try{return Integer.parseInt(s.trim());}catch(Exception e){throw new IllegalArgumentException("Ingrese un número entero válido.");}}
    private static double decimal(String s){try{return Double.parseDouble(s.trim());}catch(Exception e){throw new IllegalArgumentException("Ingrese un número válido.");}}
    private static double decimalOpcional(String s){return s==null||s.trim().isEmpty()?0.0:decimal(s);}
    // Convierte a double un valor numérico que viene de la base de datos.
    // MySQL devuelve los DECIMAL y los enteros como tipos que no son
    // double, así que la conversión se centraliza aquí.
    private static double datoNumero(Object o){
        if(o instanceof Number)return ((Number)o).doubleValue();
        try{return Double.parseDouble(txt(o));}catch(Exception e){return 0.0;}
    }
    private static void requerido(String s){if(s==null||s.trim().isEmpty())throw new IllegalArgumentException("Complete los campos obligatorios.");}

    // Límite de caracteres de los nombres de usuario de la base de datos
    // (columna nombre_usuario VARCHAR(25)).
    private static final int MAX_NOMBRE_USUARIO = 25;

    // Valida el nombre de usuario de los empleados.
    // Se revisan tres cosas:
    //   1. Que no esté vacío.
    //   2. Que no supere los 25 caracteres (límite de la columna en la BD).
    //   3. Que solo tenga letras, números y guion bajo, porque es lo mismo
    //      que exige la pantalla de login para poder entrar al sistema.
    // Si el login acepta "Admin_01" pero el CRUD dejara guardar
    // "Admin 01", el usuario quedaría registrado con un nombre con el que
    // nunca podría iniciar sesión.
    private static void nombreUsuario(String s){
        requerido(s);
        String v=s.trim();
        if(v.length()>MAX_NOMBRE_USUARIO)
            throw new IllegalArgumentException(
                    "El usuario no puede superar los "+MAX_NOMBRE_USUARIO+" caracteres.");
        if(!v.matches("[a-zA-Z0-9_]+"))
            throw new IllegalArgumentException(
                    "El usuario solo puede contener letras, números y guion bajo (_).");
    }

    // Longitud mínima de la contraseña. Es una medida de seguridad
    // mínima: una contraseña de 1 o 2 caracteres sería fácil de adivinar.
    private static final int MIN_CONTRASENA = 4;

    // Valida la contraseña del usuario. La de la base de datos es
    // VARCHAR(20), así que tampoco puede pasar de 20 caracteres.
    private static void contrasena(String s){
        requerido(s);
        if(s.length()<MIN_CONTRASENA)
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos "+MIN_CONTRASENA+" caracteres.");
        if(s.length()>20)
            throw new IllegalArgumentException(
                    "La contraseña no puede superar los 20 caracteres.");
        if(s.contains(" "))
            throw new IllegalArgumentException(
                    "La contraseña no puede contener espacios.");
    }

    // Valida que un precio sea mayor que cero, porque un producto o combo
    // con precio 0 o negativo no tendría sentido en el punto de venta.
    private static void precioPositivo(double p){
        if(p<=0)
            throw new IllegalArgumentException(
                    "El precio debe ser mayor que cero.");
    }

    // Valida un rango de cantidades usado en los grupos de combos y de
    // personalización: la mínima no puede ser mayor que la máxima, y ambas
    // deben ser al menos 1 porque un grupo sin opciones no sirve de nada.
    private static void rangoCantidades(int min,int max,String etiqueta){
        if(min<1)
            throw new IllegalArgumentException(
                    "La cantidad mínima de "+etiqueta+" debe ser al menos 1.");
        if(max<1)
            throw new IllegalArgumentException(
                    "La cantidad máxima de "+etiqueta+" debe ser al menos 1.");
        if(min>max)
            throw new IllegalArgumentException(
                    "La cantidad mínima de "+etiqueta+" no puede ser mayor que la máxima.");
    }

    // Valida las fechas de un descuento: la fecha de inicio debe ser
    // anterior o igual a la de fin, porque un descuento que "termina"
    // antes de empezar nunca se aplicaría.
    private static void rangoFechas(LocalDate inicio,LocalDate fin){
        if(inicio==null||fin==null)
            throw new IllegalArgumentException("Complete las fechas de inicio y fin.");
        if(fin.isBefore(inicio))
            throw new IllegalArgumentException(
                    "La fecha de fin no puede ser anterior a la fecha de inicio.");
    }

    // Valida que el correo tenga el dominio institucional @moova.com.
    // Se hace en dos pasos:
    //   1. Que el correo no esté vacío.
    //   2. Que termine en "@moova.com" y que tenga algo escrito antes del "@",
    //      porque un correo no puede ser solo el dominio.
    // La comparación se hace en minúsculas porque los dominios de correo
    // no distinguen entre mayúsculas y minúsculas (así "Subbu@Moova.com"
    // es válido igual que "subbu@moova.com").
    // Si la validación falla se lanza la excepción y el flujo de crear()
    // o actualizar() se detiene, mostrando el mensaje al usuario.
    private static void correoMoova(String s){
        if(s==null||s.trim().isEmpty())
            throw new IllegalArgumentException("Ingrese el correo electrónico.");

        String correo=s.trim();

        if(!correo.toLowerCase().endsWith(DOMINIO_MOOVA))
            throw new IllegalArgumentException(
                    "El correo debe ser del dominio "+DOMINIO_MOOVA+". Ejemplo: nombre"+DOMINIO_MOOVA);

        // Lo que queda antes del "@" es el nombre del usuario dentro del correo.
        // Si queda vacío, el correo sería solo el dominio y no sería válido.
        String parteUsuario=correo.substring(0,correo.length()-DOMINIO_MOOVA.length()).trim();

        if(parteUsuario.isEmpty()||parteUsuario.contains(" "))
            throw new IllegalArgumentException(
                    "El correo no es válido. Ejemplo: nombre"+DOMINIO_MOOVA);
    }
    // Carpeta del proyecto donde se guardan las imágenes de productos,
    // combos e ingredientes. Las imágenes se copian aquí para que el
    // programa no dependa de archivos sueltos en el disco del usuario.
    private static final String CARPETA_IMAGENES = "src/utiles/imagenes/menu/productos";

    /**
     * Copia la imagen seleccionada dentro de la carpeta de imágenes del
     * proyecto y devuelve la ruta relativa con la que se guarda en la base
     * de datos.
     *
     * Antes se guardaba la ruta absoluta del archivo (por ejemplo
     * "C:\Users\brandon\Desktop\miFoto.png"). Eso rompía el programa en
     * cualquier otra computadora, porque la ruta ya no existía. Al copiar
     * la imagen al proyecto y guardar solo la ruta relativa, la imagen
     * siempre se encuentra.
     *
     * @return la ruta relativa guardada, o null si no se eligió imagen.
     */
    private static String imagen(VentanaCRUD v) {

        // Si el CRUD no tiene componente de imagen (por ejemplo, en el CRUD
        // de recetas) no hay nada que devolver.
        if (v.getCargarImagen() == null) {
            return null;
        }

        File f = v.getCargarImagen().getArchivo();

        if (f == null) {
            // La imagen es obligatoria: sin ella no se puede guardar el
            // registro, porque en el menú y en las facturas el usuario vería
            // un espacio vacío donde debería verse el producto.
            throw new IllegalArgumentException(
                    "Seleccione una imagen. Es obligatoria."
            );
        }

        // Si el archivo ya está dentro de la carpeta de imágenes del
        // proyecto, no se copia de nuevo: se devuelve su ruta.
        //
        // Esto importa al editar un registro que ya tiene imagen. Al
        // seleccionarlo en la tabla, el componente de imagen carga el
        // archivo existente; si se copiara otra vez, se crearía un
        // duplicado del mismo archivo cada vez que se guardara.
        String carpetaImagenesProyecto
                = new File("src/utiles/imagenes").getAbsolutePath();

        if (f.getAbsolutePath().startsWith(carpetaImagenesProyecto)) {
            return rutaRelativa(f);
        }

        // Solo si la imagen viene de afuera del proyecto se copia.
        // Se crea la carpeta de destino si todavía no existe.
        File destino = new File(CARPETA_IMAGENES);

        if (!destino.exists() && !destino.mkdirs()) {
            throw new IllegalArgumentException(
                    "No se pudo crear la carpeta de imágenes del proyecto.");
        }

        // Si el nombre ya existe, se le agrega un número para no
        // sobrescribir la imagen que ya estaba guardada.
        String nombre = f.getName();

        File copia = new File(destino, nombre);

        int contador = 1;

        while (copia.exists()) {
            int punto = nombre.lastIndexOf('.');

            String base = punto > 0 ? nombre.substring(0, punto) : nombre;
            String extension = punto > 0 ? nombre.substring(punto) : "";

            copia = new File(destino, base + "_" + contador + extension);

            contador++;
        }

        try {
            java.nio.file.Files.copy(
                    f.toPath(),
                    copia.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );
        } catch (java.io.IOException e) {
            throw new IllegalArgumentException(
                    "No se pudo copiar la imagen al proyecto: " + e.getMessage());
        }

        // Se actualiza el componente para que muestre la copia y no el
        // archivo original, y así el usuario vea lo que realmente se guardó.
        v.getCargarImagen().setArchivo(copia);

        return rutaRelativa(copia);
    }

    /**
     * Convierte una ruta del proyecto en una ruta que empieza con "/",
     * que es el formato que usan las imágenes guardadas en la base de datos
     * (por ejemplo "/utiles/imagenes/menu/productos/Hamoorguesa.png").
     */
    private static String rutaRelativa(File f) {

        String ruta = f.getPath().replace('\\', '/');

        // Si la ruta todavía incluye la carpeta "src/", se recorta para
        // dejar solo la parte que el programa usa como recurso.
        int indice = ruta.indexOf("src/");

        if (indice >= 0) {
            ruta = ruta.substring(indice + 4);
        }

        return "/" + ruta;
    }

    /**
     * Resuelve la imagen guardada en la base de datos y la muestra en el
     * componente de imagen de la pantalla.
     *
     * En la base de datos la imagen se guarda como una ruta que empieza con
     * "/" (por ejemplo "/utiles/imagenes/menu/productos/Moo.png"). Esta
     * función convierte esa ruta en un archivo real dentro del proyecto
     * para poder abrirla.
     *
     * Si el archivo no existe (por ejemplo, si se borró de la carpeta
     * "src"), no se rompe nada: simplemente se avisa en consola y el
     * componente de imagen se deja vacío.
     *
     * @param v        ventana CRUD que tiene el componente de imagen.
     * @param rutaBD   ruta tal como está guardada en la base de datos.
     */
    private static void mostrarImagenGuardada(VentanaCRUD v, String rutaBD) {

        // Si el CRUD no tiene componente de imagen, no hay nada que hacer.
        if (v.getCargarImagen() == null) {
            return;
        }

        if (rutaBD == null || rutaBD.trim().isEmpty()) {

            v.getCargarImagen().limpiarImagen();

            return;
        }

        // Se convierte "/utiles/..." en "src/utiles/...", que es donde
        // están realmente los archivos dentro del proyecto.
        String rutaProyecto = rutaBD.startsWith("/")
                ? "src" + rutaBD
                : rutaBD;

        File archivo = new File(rutaProyecto);

        if (!archivo.exists()) {

            // Se intenta con la ruta tal cual, por si la base de datos
            // guardó una ruta con barras invertidas o sin el "/".
            archivo = new File(rutaBD);
        }

        if (archivo.exists()) {

            v.getCargarImagen().setArchivo(archivo);

        } else {

            System.out.println(
                    "No se encontró la imagen: " + rutaBD
            );

            v.getCargarImagen().limpiarImagen();
        }
    }

    //------------------------------------------------------------------------
    //            V A L I D A C I Ó N   D E   T E X T O S
    //------------------------------------------------------------------------
    /**
     * Revisa que un texto solo tenga los caracteres permitidos.
     *
     * Se usa en los campos donde no tiene sentido escribir números (por
     * ejemplo el nombre de un producto) o donde solo caben números (por
     * ejemplo un precio). Se comparan minúsculas sin importar porque los
     * caracteres permitidos son los mismos en mayúscula y minúscula.
     *
     * @param texto    valor a revisar.
     * @param patron   expresión regular con lo permitido.
     * @param mensaje  texto que se le muestra al usuario si no cumple.
     * @param nombre   nombre del campo, para armar el mensaje.
     */
    private static void soloPermitidos(String texto,String patron,String mensaje,String nombre){
        if(texto==null||texto.trim().isEmpty())return;
        if(!texto.trim().matches(patron))
            throw new IllegalArgumentException(mensaje.replace("{campo}",nombre));
    }

    /**
     * Limita la cantidad de caracteres de un texto al máximo de la
     * columna en la base de datos.
     *
     * Si no se revisa, MySQL trunca el texto en silencio (si el modo no
     * estricto está activo) y se guardan nombres cortados a la mitad, o
     * la operación falla con un error poco claro si está en modo estricto.
     */
    private static void longitud(String texto,int maximo,String nombre){
        if(texto!=null&&texto.trim().length()>maximo)
            throw new IllegalArgumentException(
                    nombre+" no puede superar los "+maximo+" caracteres.");
    }

    // Expresiones regulares de los patrones permitidos.
    private static final String SOLO_LETRAS="[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+";
    private static final String SOLO_NUMEROS="[0-9]+";
    private static final String LETRAS_Y_ESPACIOS="[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+";

    // Longitudes máximas, tomadas de las columnas del esquema SQL.
    private static final int MAX_NOMBRE=100;
    private static final int MAX_NOMBRE_INGREDIENTE=50;

    /**
     * Revisa que un registro con ese nombre no exista ya en la tabla.
     *
     * Se usa al crear. Al actualizar se omite el propio registro, porque
     * si no, al guardar los datos sin cambios se rechazaría por
     * "duplicado" consigo mismo.
     *
     * La comparación no distingue mayúsculas de minúsculas, porque para
     * el usuario "MooFin" y "moofin" son el mismo producto y tener los
     * dos en el menú sería un error.
     *
     * @param v         ventana CRUD (para el DAO y los mensajes).
     * @param tablaBD   tabla donde buscar.
     * @param columnaId columna del ID, para poder excluir el propio.
     * @param columna   columna donde va el nombre.
     * @param nombre    nombre a buscar.
     * @param idExcluir id a ignorar, o 0 si se está creando.
     */
    private static void sinDuplicados(VentanaCRUD v,String tablaBD,String columnaId,String columna,String nombre,int idExcluir){
        if(nombre==null||nombre.trim().isEmpty())return;
        // Se comparan los nombres sin espacios al principio o al final
        // y sin distinguir mayúsculas.
        String sql="SELECT COUNT(*) FROM "+tablaBD+" WHERE LOWER(TRIM("+columna+")) = LOWER(TRIM(?))"
                +(idExcluir>0?" AND "+columnaId+" <> ?":"");
        Object[] r=idExcluir>0
                ?v.adminDAO.consultarUno(sql,nombre.trim(),idExcluir)
                :v.adminDAO.consultarUno(sql,nombre.trim());
        if(r!=null&&((Number)r[0]).intValue()>0)
            throw new IllegalArgumentException(
                    "Ya existe un registro con ese nombre. No se pueden crear duplicados.");
    }

    /**
     * Valida los campos de texto de una categoría o subcategoría.
     */
    private static void validarCategoria(VentanaCRUD v,String nombre,String descripcion,boolean creando,int idExcluir){
        requerido(nombre);
        longitud(nombre,MAX_NOMBRE,"El nombre");
        longitud(descripcion,2000,"La descripción");
        soloPermitidos(nombre,LETRAS_Y_ESPACIOS,
                "En {campo} solo se permiten letras.","El nombre");
        sinDuplicados(v,"Categoria","id_Categoria","nombre",nombre,creando?0:idExcluir);
    }

    /**
     * Valida los campos de texto de un producto, combo o ingrediente.
     *
     * El nombre no puede llevar números porque en el menú un producto se
     * distingue por su nombre, y la descripción sí puede llevarlos (por
     * ejemplo "MooFfin de Tocino y Queso 2x").
     *
     * @param v          ventana CRUD.
     * @param tablaBD    tabla donde se busca el duplicado.
     * @param nombre     nombre del producto o ingrediente.
     * @param descripcion descripción.
     * @param idExcluir  id a ignorar en la búsqueda de duplicados.
     */
    private static void validarTexto(VentanaCRUD v,String tablaBD,String nombre,String descripcion,String columna,boolean creando,int idExcluir){
        requerido(nombre);
        longitud(nombre,tablaBD.equals("Ingrediente")?MAX_NOMBRE_INGREDIENTE:MAX_NOMBRE,"El nombre");
        longitud(descripcion,2000,"La descripción");
        // El nombre solo lleva letras, espacios y signos de puntuación
        // habituales (guiones, comas, apóstrofes), pero no números.
        soloPermitidos(nombre,"[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ \\-',.()]+",
                "En {campo} no se permiten números. Use solo letras.","El nombre");
        String columnaId="id_"+tablaBD;
        sinDuplicados(v,tablaBD,columnaId,columna,nombre,creando?0:idExcluir);
    }

    // Convierte lo que hay seleccionado en el combo "Obligatorio" a un
    // booleano de Java para mandarlo a la base de datos.
    //
    // El combo muestra "Sí" y "No" porque así se entiende mejor que
    // "TRUE" y "FALSE". Antes solo se aceptaba el texto "TRUE", así que
    // marcar "Sí" terminaba guardando FALSE: el campo obligatorio quedaba
    // siempre desactivado sin importar lo que el usuario eligiera.
    private static boolean verdadero(JComboBox<?> c){
        String v=String.valueOf(c.getSelectedItem()).trim();
        return "Sí".equalsIgnoreCase(v)
                || "Si".equalsIgnoreCase(v)
                || "TRUE".equalsIgnoreCase(v)
                || "1".equals(v);
    }

    // Convierte un booleano de la base de datos al texto que muestra el
    // combo ("Sí" o "No"). Se usa al cargar un registro en el formulario.
    private static String siNo(Object o){
        return bool(o)?"Sí":"No";
    }
    private static boolean bool(Object o){if(o instanceof Boolean)return (Boolean)o;if(o instanceof Number)return ((Number)o).intValue()!=0;return "true".equalsIgnoreCase(txt(o))||"1".equals(txt(o));}
    private static int num(Object o){return o instanceof Number?((Number)o).intValue():Integer.parseInt(txt(o));}
    private static String txt(Object o){return o==null?"":String.valueOf(o);}
    private static void setFecha(gui.componentes.Calendario c,Object o){if(o==null)return; if(o instanceof Timestamp)c.setFecha(((Timestamp)o).toLocalDateTime().toLocalDate()); else if(o instanceof java.sql.Date)c.setFecha(((java.sql.Date)o).toLocalDate());}
}
