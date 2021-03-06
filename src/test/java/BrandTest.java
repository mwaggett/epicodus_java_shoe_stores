import org.junit.*;
import static org.junit.Assert.*;

public class BrandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Brand.all().size());
  }

  @Test
  public void getName_returnsName() {
    Brand myBrand = new Brand("Converse");
    assertEquals("Converse", myBrand.getName());
  }

  @Test
  public void getId_returnsIdAfterSave() {
    Brand myBrand = new Brand("Converse");
    myBrand.save();
    assertEquals(Brand.all().get(0).getId(), myBrand.getId());
  }

  @Test
  public void equals_returnsTrueWhenParamsMatch() {
    Brand firstBrand = new Brand("Converse");
    Brand secondBrand = new Brand("Converse");
    assertEquals(true, firstBrand.equals(secondBrand));
  }

  @Test
  public void equals_returnsFalseWhenParamsDiffer() {
    Brand firstBrand = new Brand("Converse");
    Brand secondBrand = new Brand("Keds");
    assertEquals(false, firstBrand.equals(secondBrand));
  }

  @Test
  public void save_addsToDatabase() {
    Brand myBrand = new Brand("Converse");
    myBrand.save();
    assertEquals(Brand.all().get(0), myBrand);
  }

  @Test
  public void update_changesName() {
    Brand myBrand = new Brand("Converse");
    myBrand.save();
    myBrand.update("Keds");
    assertEquals("Keds", Brand.all().get(0).getName());
    assertEquals("Keds", myBrand.getName());
  }

  @Test
  public void delete_removesFromBrandTable() {
    Brand myBrand = new Brand("Converse");
    myBrand.save();
    myBrand.delete();
    assertEquals(0, Brand.all().size());
  }

  @Test
  public void getStores_returnsAddedStores() {
    Store myStore = new Store("DSW");
    myStore.save();
    Brand myBrand = new Brand("Converse");
    myBrand.save();
    myBrand.addStore(myStore);
    assertTrue(myBrand.getStores().contains(myStore));
  }

  @Test
  public void unavailableStores_returnsNonAddedStores() {
    Store myStore = new Store("DSW");
    myStore.save();
    Brand myBrand = new Brand("Converse");
    myBrand.save();
    assertTrue(myBrand.unavailableStores().contains(myStore));
  }

  @Test
  public void removeStores_removesAddedStores() {
    Store myStore = new Store("DSW");
    myStore.save();
    Brand myBrand = new Brand("Converse");
    myBrand.save();
    myBrand.addStore(myStore);
    myBrand.removeStore(myStore);
    assertEquals(0, myBrand.getStores().size());
  }

  @Test
  public void delete_removesFromJoinTable() {
    Store myStore = new Store("DSW");
    myStore.save();
    Brand myBrand = new Brand("Converse");
    myBrand.save();
    myBrand.addStore(myStore);
    myBrand.delete();
    assertEquals(0, myStore.getBrands().size());
  }

  @Test
  public void find_findsBrandInDatabase() {
    Brand myBrand = new Brand("Converse");
    myBrand.save();
    assertEquals(myBrand, Brand.find(myBrand.getId()));
  }

}
