package powerbuyweb.tests;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import powerbuyweb.models.Item;
import powerbuyweb.pages.Home;
import powerbuyweb.pages.ItemList;
import powerbuyweb.utils.helper.FileHelper;
import powerbuyweb.utils.helper.LogHelper;
import powerbuyweb.utils.keywords.WebUI;

public class BaseTest {
  private static final String POWER_BUY_URL = "https://www.powerbuy.co.th/th";
  public static final String POWER_BUY_CART_URL = "https://www.powerbuy.co.th/en/cart";
  protected static Logger logger = LogHelper.getLogger();

  protected final WebUI webUI;
  protected Home objHome;
  protected ItemList objItemList;
//  protected ItemDetail objItemDetail;
//  protected CartPage objCartPage;
  protected ArrayList<Item> listitems = new ArrayList<>();

  private HashMap<String, String> dataFile;

  public BaseTest() {
    webUI = new WebUI();
  }

  private static Class<?> getCallerClass(final int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(Integer.toString(index));
    }
    try {
      return getCallerClassFromStackTrace(index + 1);
    } catch (final ClassNotFoundException e) {
      logger.error("Could not find class in ReflectionUtil.getCallerClass({}), index<" + index + ">"
          + ", exception< " + e + ">");
    }
    return null;
  }

  public String getDataFile() {
    String strClassName = getCallerClass(3).getSimpleName();
    return this.dataFile.get(strClassName);
  }


  public String setDataFile(String dataFileName) {
    if (this.dataFile == null) {
      this.dataFile = new HashMap<>();
    }
    String strClassName = getCallerClass(2).getSimpleName();
    dataFileName = FileHelper.getDataJSONFilePath(dataFileName);
    return this.dataFile.put(strClassName, dataFileName);
  }

  private static Class<?> getCallerClassFromStackTrace(final int index)
      throws ClassNotFoundException {

    final StackTraceElement[] elements = new Throwable().getStackTrace();
    int i = 0;
    for (final StackTraceElement element : elements) {
      if (isValidMethod(element)) {
        if (i == index) {
          return Class.forName(element.getClassName());
        }
        ++i;
      }
    }
    throw new IndexOutOfBoundsException(Integer.toString(index));
  }

  private static boolean isValidMethod(final StackTraceElement element) {
    if (element.isNativeMethod()) {
      return false;
    }
    final String cn = element.getClassName();
    if (cn.startsWith("sun.reflect.")) {
      return false;
    }
    final String mn = element.getMethodName();
    if (cn.startsWith("java.lang.reflect.") && (mn.equals("invoke") || mn.equals("newInstance"))) {
      return false;
    }
    if (cn.equals("java.lang.Class") && mn.equals("newInstance")) {
      return false;
    }
    return true;
  }


  public Home goToHomePage() {
    return new Home(this.webUI);
  }


  @BeforeSuite(alwaysRun = true)
  public void beforeSuite() {
    logger.info("====================BeforeSuite: Cleaning allure-results==================");
    String allure_results_path = FileHelper.getTargetAllureResultsFilePath();
    File file = new File(allure_results_path);
    if (file.exists()) {
      webUI.deleteDirectory(file);
      file.delete();
    }
  }

  @BeforeClass
  public void beforeClass() {
    logger.info("====================BeforeClass: " + ItemsInCartTest.class.getName());
  }

  @Parameters({"browser"})
  @BeforeTest(alwaysRun = true)
  public void beforeTest(String browser) {
    webUI.openBrowser(browser, POWER_BUY_URL);
    webUI.maximizeWindow();

    objHome = goToHomePage();
    objHome.objAdvertisementPopup.closePopup();

    objHome.objNavigationBar.changeLanguageToEnglish();
    objHome.objAdvertisementPopup.closePopup();
    objHome.objAdvertisementPopup.closePopup();
    webUI.delayInSecond(5);
    objItemList = objHome.objNavigationBar.inputTVToSearchTextbox("TV");
    objItemList.clickToCheckBoxScreenSizeGroup("44 - 55 inches");
  }

  @BeforeMethod
  public void beforeMethod(Method method) {
    logger.info("=====================BeforeMethod: " + method.getName());
  }

  @AfterMethod
  public void afterMethod(Method method) {
    logger.info("=====================AfterMethod: " + method.getName());

  }

  @AfterTest(alwaysRun = true)
  public void afterTest() {
    webUI.closeBrowser();
  }

  @AfterClass
  public void afterClass() {
    logger.info("====================AfterClass: " + ItemsInCartTest.class.getName());
  }

  @AfterSuite
  public void afterSuite() {
    logger.info("====================AfterSuite:==================");
  }

}
