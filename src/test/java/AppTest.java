import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {

  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("SHOES!");
  }

  @Test
  public void index_displaysStores() {
    Store store = new Store("DSW");
    store.save();
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("DSW");
  }

  @Test
  public void index_displaysBrands() {
    Brand brand = new Brand("Converse");
    brand.save();
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Converse");
  }

  @Test
  public void index_navigatesToNewStoreForm() {
    goTo("http://localhost:4567/");
    click("a", withText("Add a new store"));
    assertThat(pageSource()).contains("Store Name:");
  }

  @Test
  public void index_navigatesToNewBrandForm() {
    goTo("http://localhost:4567/");
    click("a", withText("Add a new brand"));
    assertThat(pageSource()).contains("Brand Name:");
  }

  @Test
  public void newStoreForm_addsStore() {
    goTo("http://localhost:4567/stores/new");
    fill("#name").with("DSW");
    submit(".btn");
    assertThat(pageSource()).contains("DSW");
  }

  @Test
  public void newBrandForm_addsBrand() {
    goTo("http://localhost:4567/brands/new");
    fill("#name").with("Converse");
    submit(".btn");
    assertThat(pageSource()).contains("Converse");
  }

  @Test
  public void index_navigatesToStorePage() {
    Store store = new Store("DSW");
    store.save();
    goTo("http://localhost:4567/");
    click("a", withText("DSW"));
    assertThat(pageSource()).contains("Brands Carried by DSW");
  }

  @Test
  public void storePage_addsBrand() {
    Store store = new Store("DSW");
    store.save();
    Brand brand = new Brand("Converse");
    brand.save();
    String brandId = String.format("%d", brand.getId());
    String storePath = String.format("http://localhost:4567/stores/%d", store.getId());
    goTo(storePath);
    fillSelect("#brandId").withValue(brandId);
    submit("#add");
    assertThat(pageSource().contains("Remove Brand"));
  }

  @Test
  public void storePage_removesBrand() {
    Store store = new Store("DSW");
    store.save();
    Brand brand = new Brand("Converse");
    brand.save();
    store.addBrand(brand);
    String brandId = String.format("%d", brand.getId());
    String storePath = String.format("http://localhost:4567/stores/%d", store.getId());
    goTo(storePath);
    submit("#remove"+brandId);
    assertThat(!pageSource().contains("Remove Brand"));
  }

  @Test
  public void storePage_updatesStore() {
    Store store = new Store("DSW");
    store.save();
    String storePath = String.format("http://localhost:4567/stores/%d", store.getId());
    goTo(storePath);
    fill("#name").with("Designer Shoe Warehouse");
    submit("#update");
    assertThat(pageSource()).contains("Brands Carried by Designer Shoe Warehouse");
  }

  @Test
  public void storePage_deletesStore() {
    Store store = new Store("DSW");
    store.save();
    String storePath = String.format("http://localhost:4567/stores/%d", store.getId());
    goTo(storePath);
    submit("#delete");
    assertThat(!pageSource().contains("DSW"));
  }

  @Test
  public void index_navigatesToBrandPage() {
    Brand brand = new Brand("Converse");
    brand.save();
    goTo("http://localhost:4567/");
    click("a", withText("Converse"));
    assertThat(pageSource()).contains("Stores That Carry Converse");
  }

  @Test
  public void brandPage_addsStore() {
    Store store = new Store("DSW");
    store.save();
    Brand brand = new Brand("Converse");
    brand.save();
    String storeId = String.format("%d", store.getId());
    String brandPath = String.format("http://localhost:4567/brands/%d", brand.getId());
    goTo(brandPath);
    fillSelect("#storeId").withValue(storeId);
    submit("#add");
    assertThat(pageSource().contains("Remove Store"));
  }

  @Test
  public void brandPage_removesStore() {
    Store store = new Store("DSW");
    store.save();
    Brand brand = new Brand("Converse");
    brand.save();
    brand.addStore(store);
    String storeId = String.format("%d", store.getId());
    String brandPath = String.format("http://localhost:4567/brands/%d", brand.getId());
    goTo(brandPath);
    submit("#remove"+storeId);
    assertThat(!pageSource().contains("Remove Store"));
  }

}
