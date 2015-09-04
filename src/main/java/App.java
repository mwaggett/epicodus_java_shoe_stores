import java.util.Random;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.Map;

public class App {

  public static void main(String[] args) {

    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      List<Store> stores = Store.all();
      List<Brand> brands = Brand.all();

      model.put("stores", stores);
      model.put("brands", brands);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stores/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("template", "templates/new-store-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stores/create", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Store newStore = new Store(request.queryParams("name"));
      newStore.save();

      response.redirect("/");
      return null;
    });

    get("/brands/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("template", "templates/new-brand-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/brands/create", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Brand newBrand = new Brand(request.queryParams("name"));
      newBrand.save();

      response.redirect("/");
      return null;
    });

  }

}
