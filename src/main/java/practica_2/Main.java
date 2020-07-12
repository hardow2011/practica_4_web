/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package practica_2;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.jasypt.util.password.StrongPasswordEncryptor;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.http.staticfiles.Location;
import practica_2.controladores.*;
import practica_2.entidades.Producto;
import practica_2.entidades.ProductoVendido;
import practica_2.entidades.Usuario;
import practica_2.services.BootStrapServices;
import practica_2.services.ProductoServices;
import practica_2.services.ProductoVendidoServices;
import practica_2.services.UsuarioServices;

public class Main {

    static StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

    public static void main(String[] args) throws SQLException {

        // Iniciando la base de datos.
        BootStrapServices.getInstancia().init();

        Javalin app = Javalin.create(config -> {
            if (UsuarioServices.getInstancia().listar().size() == 0) {
                Usuario primerAdmin = new Usuario("admin", "admin", passwordEncryptor.encryptPassword("admin"));
                primerAdmin.setAdmin(true);
                UsuarioServices.getInstancia().crear(primerAdmin);
                ProductoServices.getInstancia().crear(new Producto("mascarilla", "", 152));
                ProductoServices.getInstancia().crear(new Producto("ropa", "", 874));
                ProductoServices.getInstancia().crear(new Producto("pantalones", "", 7400));
                ProductoServices.getInstancia().crear(new Producto("vaso", "", 25));
                ProductoServices.getInstancia().crear(new Producto("uber", "", 374));
                ProductoServices.getInstancia().crear(new Producto("sábanas", "", 9500));
                ProductoServices.getInstancia().crear(new Producto("lápiz", "", 15));
                ProductoServices.getInstancia().crear(new Producto("calculadora", "", 2000));
            }
            // Si la carpeta /publico no tiene ningún archivo, el build de Gradle fallará.
             config.addStaticFiles("/publico");
            // config.registerPlugin(new RouteOverviewPlugin("/rutas"));
        }).start();

        new LoginControlador(app).aplicarRutas();
        new CookiesSesionesControlador(app).aplicarRutas();
        new CrudControlador(app).aplicarRutas();
        new CarroCompraControlador(app).aplicarRutas();

    }
}
