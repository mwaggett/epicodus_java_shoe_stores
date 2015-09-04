import org.sql2o.*;
import java.util.List;

public class Store {

  private int id;
  private String name;

  public Store (String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object otherStoreInstance) {
    if (!(otherStoreInstance instanceof Store)) {
      return false;
    } else {
      Store newStoreInstance = (Store) otherStoreInstance;
      return this.getName().equals(newStoreInstance.getName()) &&
             this.getId() == newStoreInstance.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stores (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
          .addParameter("name", name)
          .executeUpdate()
          .getKey();
    }
  }

  public void update(String newName) {
    this.name = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stores SET name = :newName WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("newName", newName)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String delete = "DELETE FROM stores WHERE id = :id;";
      con.createQuery(delete)
        .addParameter("id", id)
        .executeUpdate();

      // String joinDelete = "DELETE FROM stores_brands WHERE store_id = :id;";
      // con.createQuery(joinDelete)
      //   .addParameter("id", id)
      //   .executeUpdate();
    }
  }

  public static List<Store> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stores;";
      return con.createQuery(sql).executeAndFetch(Store.class);
    }
  }

  public static Store find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stores WHERE id = :id;";
      return con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Store.class);
    }
  }

}
