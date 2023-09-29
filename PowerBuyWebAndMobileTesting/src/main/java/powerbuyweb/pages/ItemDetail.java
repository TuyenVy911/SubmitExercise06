package powerbuyweb.pages;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import powerbuyweb.object_repo.ItemDetailRepo;
import powerbuyweb.tests.BaseTest;
import powerbuyweb.utils.helper.LogHelper;
import powerbuyweb.utils.keywords.WebUI;

public class ItemDetail extends BasePage{
  protected static Logger logger = LogHelper.getLogger();

  public ItemDetail(WebUI webUI) {
    super(webUI);
  }

  @Step("Add item to cart")
  public ItemDetail clickButtonAddToCart(){
    webUI.clickJS(ItemDetailRepo.BTN_ADD_TO_CART);
    return new ItemDetail(webUI);
  }

  public ItemList backToItemListPage() {
    webUI.back();
    return new ItemList(webUI);
  }

  public CartPage navigateToCartPage() {
    webUI.navigateToUrl(BaseTest.POWER_BUY_CART_URL);
    return new CartPage(webUI);
  }
  public String getItemSKUByElementAttribute() {
    String sku = webUI.getElementAttributeValue(ItemDetailRepo.TXT_PRODUCT_ATTRIBUTE,"data-productid");
    return sku;
  }

  public boolean verifyAddToCartButtonVisible() {
    if(webUI.waitForElementPresent(ItemDetailRepo.BTN_ADD_TO_CART, 5)) {
      return true;
    }
    return false;
  }
  public String getItemBranch() {
    String textbranch = webUI.getElementTextByMethodGetAttributeInnerHTML(ItemDetailRepo.TXT_PRODUCT_BRANCH_NAME);
    return textbranch;
  }
  public String getItemName() {
    String textname = webUI.getElementTextByMethodGetAttributeInnerHTML(ItemDetailRepo.TXT_PRODUCT_NAME);
    return textname;
  }
  public String getItemPrice() {
    String textprice = webUI.getElementTextByMethodGetAttributeInnerHTML(ItemDetailRepo.TXT_PRODUCT_PRICE);
    return textprice;
  }
}
