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

      Store newStore = new Store(request.queryParams("name"));
      newStore.save();

      response.redirect("/");
      return null;
    });

    get("/stores/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Store store = Store.find(Integer.parseInt(request.params(":id")));

      model.put("store", store);
      model.put("template", "templates/store.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stores/:id/add-brand", (request, response) -> {

      Store store = Store.find(Integer.parseInt(request.params(":id")));
      Brand addedBrand = Brand.find(Integer.parseInt(request.queryParams("brandId")));
      store.addBrand(addedBrand);

      String redirectPath = String.format("/stores/%d", store.getId());
      response.redirect(redirectPath);
      return null;
    });

    post("/stores/:store_id/remove-brand/:id", (request, response) -> {

      Store store = Store.find(Integer.parseInt(request.params(":store_id")));
      Brand removedBrand = Brand.find(Integer.parseInt(request.params(":id")));
      store.removeBrand(removedBrand);

      String redirectPath = String.format("/stores/%d", store.getId());
      response.redirect(redirectPath);
      return null;
    });

    post("/stores/:id/update", (request, response) -> {

      Store store = Store.find(Integer.parseInt(request.params(":id")));
      store.update(request.queryParams("name"));

      String redirectPath = String.format("/stores/%d", store.getId());
      response.redirect(redirectPath);
      return null;
    });

    post("/stores/:id/delete", (request, response) -> {

      Store store = Store.find(Integer.parseInt(request.params(":id")));
      store.delete();

      response.redirect("/");
      return null;
    });

    get("/brands/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("template", "templates/new-brand-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/brands/create", (request, response) -> {

      Brand newBrand = new Brand(request.queryParams("name"));
      newBrand.save();

      response.redirect("/");
      return null;
    });

    get("/brands/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Brand brand = Brand.find(Integer.parseInt(request.params(":id")));

      model.put("brand", brand);
      model.put("template", "templates/brand.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/brands/:id/add-store", (request, response) -> {

      Brand brand = Brand.find(Integer.parseInt(request.params(":id")));
      Store addedStore = Store.find(Integer.parseInt(request.queryParams("storeId")));
      brand.addStore(addedStore);

      String redirectPath = String.format("/brands/%d", brand.getId());
      response.redirect(redirectPath);
      return null;
    });

    post("/brands/:brand_id/remove-store/:id", (request, response) -> {

      Brand brand = Brand.find(Integer.parseInt(request.params(":brand_id")));
      Store removedStore = Store.find(Integer.parseInt(request.params(":id")));
      brand.removeStore(removedStore);

      String redirectPath = String.format("/brands/%d", brand.getId());
      response.redirect(redirectPath);
      return null;
    });

  }

}
