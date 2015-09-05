import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Brand {

  private int id;
  private String name;

  public Brand (String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object otherBrandInstance) {
    if (!(otherBrandInstance instanceof Brand)) {
      return false;
    } else {
      Brand newBrandInstance = (Brand) otherBrandInstance;
      return this.getName().equals(newBrandInstance.getName()) &&
             this.getId() == newBrandInstance.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO brands (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
          .addParameter("name", name)
          .executeUpdate()
          .getKey();
    }
  }

  public void update(String newName) {
    this.name = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE brands SET name = :newName WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("newName", newName)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String delete = "DELETE FROM brands WHERE id = :id;";
      con.createQuery(delete)
        .addParameter("id", id)
        .executeUpdate();

      String joinDelete = "DELETE FROM stores_brands WHERE brand_id = :id;";
      con.createQuery(joinDelete)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void addStore(Store store) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stores_brands (store_id, brand_id) VALUES (:store_id, :brand_id);";
      con.createQuery(sql)
        .addParameter("store_id", store.getId())
        .addParameter("brand_id", id)
        .executeUpdate();
    }
  }

  public void removeStore(Store store) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stores_brands WHERE store_id = :store_id AND brand_id = :brand_id;";
      con.createQuery(sql)
        .addParameter("store_id", store.getId())
        .addParameter("brand_id", id)
        .executeUpdate();
    }
  }

  public List<Store> getStores() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT stores.* FROM brands JOIN stores_brands ON (brands.id = stores_brands.brand_id) JOIN stores ON (stores_brands.store_id = stores.id) WHERE brands.id = :id;";
      return con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetch(Store.class);
    }
  }

  public List<Store> unavailableStores() {
    ArrayList<Store> unavailableStores = new ArrayList<Store>();
    List<Store> allStores = Store.all();
    for (Store store : allStores) {
      if (!this.getStores().contains(store)) {
        unavailableStores.add(store);
      }
    }
    return unavailableStores;
  }

  public static List<Brand> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM brands;";
      return con.createQuery(sql).executeAndFetch(Brand.class);
    }
  }

  public static Brand find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM brands WHERE id = :id;";
      return con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Brand.class);
    }
  }

}
