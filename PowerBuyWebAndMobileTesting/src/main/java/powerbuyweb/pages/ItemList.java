package powerbuyweb.pages;

import io.qameta.allure.Step;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.WebElement;
import powerbuyweb.object_repo.FilterItemRepo;
import powerbuyweb.object_repo.HeaderRepo;
import powerbuyweb.object_repo.ItemListRepo;
import powerbuyweb.utils.keywords.WebUI;

public class ItemList extends BasePage{
  public ItemList(WebUI webUI) {
    super(webUI);
  }
  public ItemDetail chosingOneItem() {
    List<WebElement> listItems = webUI.findElements(ItemListRepo.LNK_ITEMS);
//    int random_index = (int)Math.floor(Math.random() * listItems.size()); //web low performance
    int random_index = (int)Math.floor(Math.random() * 10); //using random [0 to 10]
    //int random_index = 9;
    System.out.println("Random index: " + random_index);
    webUI.scrollToElementCenter(listItems.get(random_index));
    webUI.takeScreenShotAndHighLightElement(listItems.get(random_index));
    webUI.clickUsingActions(listItems.get(random_index));
//    boolean isVisible = webUI.waitForElementPresent(listItems.get(random_index));
//    while (!isVisible) {
//      webUI.clickUsingActions(listItems.get(random_index));
//      isVisible = webUI.waitForElementPresent(listItems.get(random_index));
//    }
    webUI.delayInSecond(5);
    return new ItemDetail(webUI);
  }

  @Step("Click to checkbox screen size group at left menu")
  public ItemList clickToCheckBoxScreenSizeGroup(String text) {
    webUI.scrollToElementCenter(FilterItemRepo.BTN_SCREEN_SIZE_GROUP);
    if(webUI.waitForElementPresent(FilterItemRepo.BTN_VIEW_MORE_CLICK,4)) {
      webUI.clickJS(FilterItemRepo.BTN_VIEW_MORE_CLICK);
    }
    List<WebElement> listSizes = webUI.findElements(FilterItemRepo.BTN_SCREEN_SIZE_GROUP);
    for(int i = 0; i < listSizes.size(); i++){
      webUI.scrollToElementCenter(listSizes.get(i));
      String sizeText = webUI.getElementText(listSizes.get(i));
      sizeText = sizeText.lines().collect(Collectors.joining());
      int lengths = sizeText.length();
      int temp = 0;
      for(int j = 0; j < lengths; j++){
        if(sizeText.charAt(j) == '('){
          temp = j;
          break;
        }
      }
      String size = sizeText.substring(0, temp);
      if(size.equals(text)){
        webUI.clickJS(listSizes.get(i));
        break;
      }
    }
    webUI.delayInSecond(5);
    return new ItemList(webUI);
  }

  @Step("Unpick the checkbox screen size group at left menu")
  public ItemList uncheckTheCheckBoxScreenSizeGroup(String text) {
    webUI.scrollToElementCenter(FilterItemRepo.BTN_SCREEN_SIZE_GROUP);
    if(webUI.waitForElementPresent(FilterItemRepo.BTN_VIEW_MORE_CLICK,4)) {
      webUI.clickJS(FilterItemRepo.BTN_VIEW_MORE_CLICK);
    }
    List<WebElement> listSizes = webUI.findElements(FilterItemRepo.BTN_SCREEN_SIZE_GROUP);
    for(int i = 0; i < listSizes.size(); i++){
      webUI.scrollToElementCenter(listSizes.get(i));
      String sizeText = webUI.getElementText(listSizes.get(i));
      sizeText = sizeText.lines().collect(Collectors.joining());
      int lengths = sizeText.length();
      int temp = 0;
      for(int j = 0; j < lengths; j++){
        if(sizeText.charAt(j) == '('){
          temp = j;
          break;
        }
      }
      String size = sizeText.substring(0, temp);
      if(size.equals(text)){
        webUI.clickJS(listSizes.get(i));
        break;
      }
    }
    webUI.delayInSecond(5);
    return new ItemList(webUI);
  }
}
