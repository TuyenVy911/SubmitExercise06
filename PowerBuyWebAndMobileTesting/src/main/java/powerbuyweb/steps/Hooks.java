package powerbuyweb.steps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import powerbuyweb.tests.TestNGRunner;

public class Hooks extends TestNGRunner {
  //  public Hooks() {
//    super();
//  }

  @BeforeAll
  public static void beforeAll() {
    logger.info("Before All");
    webUI.openBrowser("Chrome", POWER_BUY_URL);
    webUI.maximizeWindow();
  }


  @AfterAll
  public static void afterAll() {
    webUI.closeBrowser();
    logger.info("After All");
  }
}
