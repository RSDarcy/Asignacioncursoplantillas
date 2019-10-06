

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;


public class Main {



    /*public static String renderFreemarker(Map<String, Object> model, String templatePath) {
        Configuration configuration=new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(Main.class, "/resources/templates/");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);
        return freeMarkerEngine.render(new ModelAndView(model, templatePath));*/


    // Declaración para simplificar el uso del motor de template Thymeleaf.
    public static String renderThymeleaf(Map<String, Object> model, String templatePath) {
        return new ThymeleafTemplateEngine().render(new ModelAndView(model, templatePath)); }

    public static void main(String[] args){
        Configuration configuration=new Configuration(Configuration.getVersion());
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

        Spark.get("/login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Login");
            return new ModelAndView(attributes, "login.ftl");
        }, freeMarkerEngine);
        /*Configuration configuration=new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(Main.class, "/resources/templates/");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);
*/

        get("/hello", (req, res) -> "Hello World");

        //get("/formulario/", Main::handle);



        before("/",(request, response) -> {
            Usuario usuario=request.session(true).attribute("usuario");
            if(usuario==null){
                //parada del request, enviando un codigo.

                    response.redirect("/login");

                ;
            }
        });
        post("/procesarFormulario", (request, response) -> {
            //obteniendo la matricula.

            String usuario =request.queryParams("usuario");
            String contrasena =request.queryParams("contrasena");

            Usuario user= new Usuario(usuario, contrasena);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Completo");
            attributes.put("usuario", usuario);
            System.out.println(usuario);
            System.out.println(contrasena);
            System.out.println(attributes);
            return new ModelAndView(attributes, "formulario.ftl");
        }, freeMarkerEngine);




    }


}
