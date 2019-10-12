

import freemarker.template.Configuration;
import freemarker.template.Template;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import static spark.Spark.staticFiles;

import java.util.HashMap;
import java.util.Map;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicReference;

import static spark.Spark.*;


public class Main {

    public static void main(String[] args){
        staticFiles.location("/templates");
        Configuration configuration=new Configuration(Configuration.getVersion());
        configuration.setClassForTemplateLoading(Main.class, "/");

        Spark.get("/login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Login");
            return new ModelAndView(attributes, "login.ftl");
        });

        ///Creo que fuera de funciones fuera mejor tenerlo definido
        AtomicReference<Usuario> user = new AtomicReference<>(new Usuario());


        Spark.get("/login", (request, response) -> {
            Template inicio = configuration.getTemplate("templates/login.ftl");
            StringWriter writer = new StringWriter();

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("user", user);
            inicio.process(attributes, writer);
            return writer;
        });


        before("/",(request, response) -> {
            Usuario usuario=request.session(true).attribute("usuario");
            if(usuario==null){
                //parada del request, enviando un codigo.

                    response.redirect("/login");

                ;
            }
        });

        Spark.post("/procesarFormulario", (request, response) -> {
            //obteniendo la matricula.

            String usuario =request.queryParams("usuario");
            String contrasena =request.queryParams("contrasena");

            user.set(new Usuario(usuario, contrasena));
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Completo");
            attributes.put("usuario", usuario);
            System.out.println(usuario);
            System.out.println(contrasena);
            return new ModelAndView(attributes, "formulario.ftl");
        });

        /*post("/procesarFormulario", (request, response) -> {
            //obteniendo la matricula.

            String usuario =request.queryParams("usuario");
            String contrasena =request.queryParams("contrasena");

            Usuario user= new Usuario(usuario, contrasena);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Completo");
            attributes.put("usuario", usuario);
            System.out.println(usuario);
            System.out.println(contrasena);
            return new ModelAndView(attributes, "formulario.ftl");
        }, freeMarkerEngine);*/

        get("/autenticar", (request, response)->{
            //
            Session session=request.session(true);

            //
            //Usuario usuario= null;//FakeServices.getInstancia().autenticarUsuario(request.params("usuario"), request.params("contrasena"));
            if(request.params("usuario").equalsIgnoreCase("moxy") && request.params("contrasena").equalsIgnoreCase("2014")){
                //Buscar el usuario en la base de datos..
                //usuario = new Usuario("Barcamp", "2014");
                //session.attribute("usuario", user);
                //redireccionado a la otra URL.
                response.redirect("/hello");
            }else{
                halt(401,"Credenciales no validas...");
            }

            return "";
        });


    }


}
