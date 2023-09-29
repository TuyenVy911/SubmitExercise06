package powerbuyweb.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import java.util.ArrayList;
import org.slf4j.Logger;
import powerbuyweb.models.Item;
import powerbuyweb.pages.CartPage;
import powerbuyweb.pages.Home;
import powerbuyweb.pages.ItemDetail;
import powerbuyweb.pages.ItemList;
import powerbuyweb.utils.helper.LogHelper;
import powerbuyweb.utils.keywords.WebUI;

@CucumberOptions(
    features = {"src/main/resources/features/itemsincart.feature"},
    glue = {"powerbuyweb.steps","powerbuyweb.steps.Hooks"},
    plugin = {"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)
public class TestNGRunner extends AbstractTestNGCucumberTests {
  protected static Logger logger = LogHelper.getLogger();
  protected static final String POWER_BUY_URL = "https://www.powerbuy.co.th/th";
  protected static final String POWER_BUY_CART_URL = "https://www.powerbuy.co.th/en/cart";
  protected ItemList objItemList;
  protected ItemDetail objItemDetail;
  protected CartPage objCartPage;
  protected static ArrayList<Item> listitems = new ArrayList<>();
  protected static WebUI webUI;

  public TestNGRunner(){
    webUI = new WebUI();
  }
}
