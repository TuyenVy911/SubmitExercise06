package powerbuyweb.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import java.util.List;
import java.util.Map;
import powerbuyweb.models.Item;
import powerbuyweb.object_repo.ItemDetailRepo;
import powerbuyweb.pages.CartPage;
import powerbuyweb.pages.ItemDetail;
import powerbuyweb.pages.ItemList;
import powerbuyweb.tests.TestNGRunner;

public class ItemListSteps extends TestNGRunner {
  private ItemList objItemList;
  private CartPage objCartPage;
  private ItemDetail objItemDetail;
  public ItemListSteps(){
    super();
    objItemList = new ItemList(webUI);
  }

  @And("^[a-zA-Z].* filter all item to <size>")
  public void filterAllItemToSize(DataTable dataTable) {
    List<Map<String, String>> data = dataTable.asMaps();
    String size = data.get(0).get("size");
    objItemList.clickToCheckBoxScreenSizeGroup(size);
  }

  @And("^[a-zA-Z].* choose first item to add to cart")
  public void chooseFirstItemToAddToCart() {
    objItemDetail = objItemList.chosingOneItem();

    boolean isVisible = objItemDetail.verifyAddToCartButtonVisible();
    while(!isVisible){
      objItemDetail.backToItemListPage();
      objItemDetail = objItemList.chosingOneItem();
      isVisible = objItemDetail.verifyAddToCartButtonVisible();
    }

    String sku1 = objItemDetail.getItemSKUByElementAttribute();
    Item item1 = new Item(objItemDetail.getItemBranch(), objItemDetail.getItemName(),
        objItemDetail.getItemPrice(), sku1);
    listitems.add(item1);
//    System.out.println(item1.getBranch() + " | " + item1.getName() + " | " + item1.getPrice());

    objItemDetail.clickButtonAddToCart();
    if(webUI.waitForElementPresent(ItemDetailRepo.LBL_ADD_TO_CART_SUCCESS_ALERT, 5)) {
      objItemList = objItemDetail.backToItemListPage();
    }
  }

  @And("^[a-zA-Z].* uncheck checkbox filter all item to <groupsize>")
  public void heUncheckCheckboxFilterAllItemToGroupsize(DataTable dataTable) {
    List<Map<String, String>> data = dataTable.asMaps();
    String groupsize = data.get(0).get("groupsize");
    objItemList.uncheckTheCheckBoxScreenSizeGroup(groupsize);
  }

  @And("^[a-zA-Z].* choose second item to add to cart")
  public void chooseSecondItemToAddToCart() {
    objItemDetail = objItemList.chosingOneItem();
    boolean isVisible = objItemDetail.verifyAddToCartButtonVisible();
    while(!isVisible){
      objItemDetail.backToItemListPage();
      objItemDetail = objItemList.chosingOneItem();
      isVisible = objItemDetail.verifyAddToCartButtonVisible();
    }

    String sku2 = objItemDetail.getItemSKUByElementAttribute();
    Item item2 = new Item(objItemDetail.getItemBranch(), objItemDetail.getItemName(),
        objItemDetail.getItemPrice(), sku2);
    listitems.add(item2);
//    System.out.println(item2.getBranch() + " | " + item2.getName() + " | " + item2.getPrice());

    objItemDetail.clickButtonAddToCart();
  }

  @And("^[a-zA-Z].* navigate to cart page")
  public void navigateToCartPage() {
    if(webUI.waitForElementPresent(ItemDetailRepo.LBL_ADD_TO_CART_SUCCESS_ALERT, 5)) {
      objCartPage = objItemDetail.navigateToCartPage();
    }
  }

}
