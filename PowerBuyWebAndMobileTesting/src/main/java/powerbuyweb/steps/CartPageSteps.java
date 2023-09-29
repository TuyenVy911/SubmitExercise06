package powerbuyweb.steps;

import static org.testng.Assert.assertTrue;

import io.cucumber.java.en.Then;
import java.text.MessageFormat;
import powerbuyweb.models.Item;
import powerbuyweb.pages.CartPage;
import powerbuyweb.tests.TestNGRunner;

public class CartPageSteps extends TestNGRunner {
  public CartPageSteps(){
    super();
    objCartPage = new CartPage(webUI);
  }

  @Then("^[a-zA-Z].* should be shown a list containing two items that were just picked up")
  public void shouldBeShownAListContainingTwoItemsThatWereJustPickedUp() {
    Item i1 = listitems.get(0);
    Item i2 = listitems.get(1);
    assertTrue(objCartPage.shouldBeShowCorrectItem(i1, i2),
        MessageFormat.format("The items in your shopping cart match the ones you have selected. Specifically ''{0}'' and ''{1}''",
            i1.getBranch() + " " + i1.getName(), i2.getBranch() + " " + i2.getName()));
  }
}
