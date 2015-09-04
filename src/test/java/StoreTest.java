import org.junit.*;
import static org.junit.Assert.*;

public class StoreTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Store.all().size());
  }

  @Test
  public void getName_returnsName() {
    Store myStore = new Store("DSW");
    assertEquals("DSW", myStore.getName());
  }

  @Test
  public void getId_returnsIdAfterSave() {
    Store myStore = new Store("DSW");
    myStore.save();
    assertEquals(Store.all().get(0).getId(), myStore.getId());
  }

  @Test
  public void equals_returnsTrueWhenParamsMatch() {
    Store firstStore = new Store("DSW");
    Store secondStore = new Store("DSW");
    assertEquals(true, firstStore.equals(secondStore));
  }

  @Test
  public void equals_returnsFalseWhenParamsDiffer() {
    Store firstStore = new Store("DSW");
    Store secondStore = new Store("Steve Madden");
    assertEquals(false, firstStore.equals(secondStore));
  }

  @Test
  public void save_addsToDatabase() {
    Store myStore = new Store("DSW");
    myStore.save();
    assertEquals(Store.all().get(0), myStore);
  }

  @Test
  public void update_changesName() {
    Store myStore = new Store("DSW");
    myStore.save();
    myStore.update("Steve Madden");
    assertEquals("Steve Madden", Store.all().get(0).getName());
    assertEquals("Steve Madden", myStore.getName());
  }

  @Test
  public void delete_removesFromStoreDatabase() {
    Store myStore = new Store("DSW");
    myStore.save();
    myStore.delete();
    assertEquals(0, Store.all().size());
  }

  @Test
  public void find_findsStoreInDatabase() {
    Store myStore = new Store("DSW");
    myStore.save();
    assertEquals(myStore, Store.find(myStore.getId()));
  }

}
