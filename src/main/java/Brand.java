import org.sql2o.*;
import java.util.List;

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

      // String joinDelete = "DELETE FROM stores_brands WHERE brand_id = :id;";
      // con.createQuery(joinDelete)
      //   .addParameter("id", id)
      //   .executeUpdate();
    }
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
