package powerbuyweb.components;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import powerbuyweb.object_repo.HeaderRepo;
import powerbuyweb.pages.Home;
import powerbuyweb.pages.ItemList;
import powerbuyweb.utils.keywords.WebUI;

public class NavigationBar extends BaseComponent{

  public NavigationBar(WebUI webUI) {
    super(webUI);
  }

  @Step("Change language from Thai to English")
  public Home changeLanguageToEnglish(){
    webUI.clickJS(HeaderRepo.LNK_ENGLISH_LANGUAGE);
    webUI.delayInSecond(5);
    return new Home(webUI);
  }
  @Step("Enter TV to search textbox")
  public ItemList inputTVToSearchTextbox(String text){
    webUI.sendKeys(HeaderRepo.INP_SEARCH_PRODUCT, Keys.chord(text,Keys.ENTER));
    webUI.delayInSecond(5);
    return new ItemList(webUI);
  }
}
