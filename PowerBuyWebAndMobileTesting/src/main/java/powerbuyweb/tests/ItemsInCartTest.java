package powerbuyweb.tests;

import static org.testng.Assert.assertTrue;

import io.qameta.allure.Description;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import org.testng.annotations.Test;
import powerbuyweb.models.Item;
import powerbuyweb.object_repo.ItemDetailRepo;
import powerbuyweb.pages.CartPage;
import powerbuyweb.pages.ItemDetail;

public class ItemsInCartTest extends BaseTest {

  private ItemDetail objItemDetail;
  private CartPage objCartPage;


  public ItemsInCartTest() {
    super();
  }

  @Test(description = "TC001: Items in cart match the picked items")
  @Description("Items in cart match the picked items")
  public void TC001_Items_in_cart_match_the_picked_items(Method method) {
    logger.info("Test case: " + method.getName());
    objItemDetail = objItemList.chosingOneItem();

    boolean isVisible = objItemDetail.verifyAddToCartButtonVisible();
    while (!isVisible) {
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
    if (webUI.waitForElementPresent(ItemDetailRepo.LBL_ADD_TO_CART_SUCCESS_ALERT, 5)) {
      objItemList = objItemDetail.backToItemListPage();
    }

    objItemList.uncheckTheCheckBoxScreenSizeGroup("44 - 55 inches");
    objItemList.clickToCheckBoxScreenSizeGroup("56 - 65 inches");

    objItemDetail = objItemList.chosingOneItem();
    isVisible = objItemDetail.verifyAddToCartButtonVisible();
    while (!isVisible) {
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
    if (webUI.waitForElementPresent(ItemDetailRepo.LBL_ADD_TO_CART_SUCCESS_ALERT, 5)) {
      objCartPage = objItemDetail.navigateToCartPage();
    }
    assertTrue(objCartPage.shouldBeShowCorrectItem(item1, item2),
        MessageFormat.format(
            "The items in your shopping cart match the ones you have selected. Specifically ''{0}'' and ''{1}''",
            item1.getBranch() + " " + item1.getName(), item2.getBranch() + " " + item2.getName()));
  }

}
