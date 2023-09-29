package powerbuyweb.steps;

import powerbuyweb.pages.CartPage;
import powerbuyweb.pages.ItemDetail;
import powerbuyweb.tests.TestNGRunner;

public class ItemDetailSteps extends TestNGRunner {
  public ItemDetailSteps(){
    super();
    objItemDetail = new ItemDetail(webUI);
  }
}
