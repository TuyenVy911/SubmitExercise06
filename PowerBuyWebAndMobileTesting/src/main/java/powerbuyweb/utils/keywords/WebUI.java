package powerbuyweb.utils.keywords;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import powerbuyweb.utils.helper.AllureLogger;
import powerbuyweb.utils.helper.FileHelper;
import powerbuyweb.utils.helper.LogHelper;

public class WebUI {

  private static final int defaultTimeOut = 60;
  private static final Logger logger = LogHelper.getLogger();
  private static WebDriver driver;

  public void openBrowser(String browser, String... url) {
    logger.info(MessageFormat.format("Launching {0}", browser.toUpperCase()));
    AllureLogger.info(MessageFormat.format("Launching {0}", browser.toUpperCase()));
    try {
      switch (browser.toUpperCase()) {
        case "CHROME":
          WebDriverManager.chromedriver().setup();
          ChromeOptions options = new ChromeOptions();
          options.addArguments("--remote-allow-origins=*");
          driver = new ChromeDriver(options);
          break;
        case "FIREFOX":
          WebDriverManager.firefoxdriver().setup();
          driver = new FirefoxDriver();
          break;
      }
      logger.info(MessageFormat.format("Launched {0} successfully", browser.toUpperCase()));
      AllureLogger.info(MessageFormat.format("Launched {0} successfully", browser.toUpperCase()));
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot launch {0}. Root cause: {1}", browser, e.getMessage()));
    }
    String rawUrl = url.length != 0 ? url[0] : "";
    if (rawUrl.length() != 0) {
      try {
        logger.info(MessageFormat.format("Navigating to ''{0}''", rawUrl));
        driver.get(rawUrl);
        logger.info(MessageFormat.format("Navigated to ''{0}'' successfully", rawUrl));
      } catch (Exception e) {
        logger.error(MessageFormat.format("Cannot navigate to ''{0}''. Root cause: {1}", rawUrl,
            e.getMessage()));
      }
    }
  }

  public void closeBrowser() {
    logger.info("Closing the browser");
    try {
      driver.close();
      logger.info("Closed the browser successfully");
      AllureLogger.info("Closed the browser successfully");
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot close the browser. Root cause: {0}", e.getMessage()));
    }
  }

  public String getTitle() {
    logger.info("Getting title of the page");
    String title = null;
    try {
      title = driver.getTitle();
      logger.info(MessageFormat.format("Title is ''{0}''", title));
      AllureLogger.info(MessageFormat.format("Title is ''{0}''", title));
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot get title of the page. Root cause: {0}", e.getMessage()));
    }
    return title;
  }

  public String getUrl() {
    logger.info("Getting url of the page");
    String url = null;
    try {
      url = driver.getCurrentUrl();
      logger.info(MessageFormat.format("Url of the page is ''{0}''", url));
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot get url of the page. Root cause: {0}", e.getMessage()));
    }
    return url;
  }

  public void navigateToUrl(String url) {
    try {
      logger.info(MessageFormat.format("Navigating to ''{0}''", url));
      driver.navigate().to(url);
      logger.info(MessageFormat.format("Navigated to ''{0}'' successfully", url));
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot navigate to ''{0}''. Root cause: {1}", url, e.getMessage()));
    }
  }

  public void forward() {
    try {
      logger.info("Forwarding to next page");
      driver.navigate().forward();
      logger.info("Forwarded to next page successfully");
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot forward to next page. Root cause: {0}", e.getMessage()));
    }
  }

  public void back() {
    try {
      logger.info("Backing to previous page");
      driver.navigate().back();
      logger.info("Back to previous page successfully");
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot back to previous page. Root cause: {0}", e.getMessage()));
    }
  }

  public void maximizeWindow() {
    try {
      driver.manage().window().maximize();
      logger.info("Window maximized successfully");
    } catch (Exception e) {
      logger.error("Cannot maximize window. Root cause: " + e.getMessage());
    }
  }

  public WebElement findElement(By locator, int... timeOut) {
    WebElement element = null;
    int waitTime = timeOut.length != 0 ? timeOut[0] : defaultTimeOut;
    logger.info(MessageFormat.format("Finding web element located by ''{0}''", locator));
    long startTime = 0;
    long endTime = 0;
    double total;
    try {
      startTime = System.currentTimeMillis();
      Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(
              Duration.ofSeconds(waitTime))
          .pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
      element = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
        @Override
        public WebElement apply(WebDriver driver) {
          return driver.findElement(locator);
        }
      });
      endTime = System.currentTimeMillis();
      logger.info(MessageFormat.format("Found {0} web element located by ''{1}''", 1, locator));
    } catch (Exception e) {
      endTime = System.currentTimeMillis();
      logger.error(
          MessageFormat.format("Cannot find web element located by ''{0}''. Root cause: {1}",
              locator, e.getMessage()));
    }
    total = (double) (endTime - startTime) / 1000;
    logger.info(MessageFormat.format("Timeout: ''{0}''", total));
    return element;
  }

  public void setImplicitlyWait(int seconds) {
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
  }

  public WebElement findElement(String locator, int... timeOut) {
    WebElement element = null;
    logger.info(MessageFormat.format("Finding web element located by ''{0}''", locator));
    int waitTime = timeOut.length != 0 ? timeOut[0] : defaultTimeOut;
    long startTime = 0;
    long endTime = 0;
    double total;
    try {
      startTime = System.currentTimeMillis();
      Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(
              Duration.ofSeconds(waitTime))
          .pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
      element = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
        @Override
        public WebElement apply(WebDriver driver) {
          return driver.findElement(By.xpath(locator));
        }
      });
      endTime = System.currentTimeMillis();
      logger.info(MessageFormat.format("Found {0} web element located by ''{1}''", 1, locator));
    } catch (Exception e) {
      endTime = System.currentTimeMillis();
      logger.error(
          MessageFormat.format("Cannot find web element located by ''{0}''. Root cause: {1}",
              locator, e.getMessage()));
    }
    total = (double) (endTime - startTime) / 1000;
    logger.info(MessageFormat.format("Timeout: ''{0}''", total));
    return element;
  }


  public List<WebElement> findElements(By locator) {
    List<WebElement> elements = null;
    logger.info(MessageFormat.format("Finding web element located by ''{0}''", locator));
    try {
      elements = driver.findElements(locator);
      logger.info(
          MessageFormat.format("Found {0} web element(s) located by ''{1}''", elements.size(),
              locator));
    } catch (NoSuchElementException e) {
      logger.error(
          MessageFormat.format("Cannot find web element located by ''{0}''. Root cause: {1}",
              locator, e.getMessage()));
    }
    return elements;
  }

  public List<WebElement> findElements(String locator) {
    List<WebElement> elements = null;
    logger.info(MessageFormat.format("Finding web element located by ''{0}''", locator));
    try {
      elements = driver.findElements(By.xpath(locator));
      logger.info(
          MessageFormat.format("Found {0} web element(s) located by ''{1}''", elements.size(),
              locator));
    } catch (NoSuchElementException e) {
      logger.error(
          MessageFormat.format("Cannot find web element located by ''{0}''. Root cause: {1}",
              locator, e.getMessage()));
    }
    return elements;
  }

  public void clearText(By locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        element.clear();
        logger.info("Cleared element: " + locator.toString());
      } catch (Exception e) {
        logger.error(
            "Cannot clear element: " + locator.toString() + ". Root cause: " + e.getMessage());
      }
    }
  }

  public void clearText(String locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        element.clear();
        logger.info("Cleared element: " + locator);
      } catch (Exception e) {
        logger.error(
            "Cannot clear element: " + locator + ". Root cause: " + e.getMessage());
      }
    }
  }


  public void setText(By locator, String value) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        element.sendKeys(value);
        logger.info("Entered text '" + value + "' into element: " + locator.toString());
      } catch (Exception e) {
        logger.error("Cannot enter text into element: " + locator.toString() + ". Root cause: "
            + e.getMessage());
      }
    }
  }

  public void setText(String locator, String value) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        element.sendKeys(value);
        logger.info("Entered text '" + value + "' into element: " + locator);
      } catch (Exception e) {
        logger.error("Cannot enter text into element: " + locator + ". Root cause: "
            + e.getMessage());
      }
    }
  }

  public void sendKeys(By locator, String value) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        element.sendKeys(value);
        logger.info("Entered text '" + value + "' into element: " + locator.toString());
      } catch (Exception e) {
        logger.error("Cannot enter text into element: " + locator.toString() + ". Root cause: "
            + e.getMessage());
      }
    }
  }

  public void sendKeys(String locator, String value) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        element.clear();
        element.sendKeys(value);
        logger.info("Entered text '" + value + "' into element: " + locator);
      } catch (Exception e) {
        logger.error("Cannot enter text into element: " + locator + ". Root cause: "
            + e.getMessage());
      }
    }
  }

  public void clickElement(By locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        element.click();
        logger.info("Clicked element: " + locator.toString());
      } catch (Exception e) {
        logger.error(
            MessageFormat.format("Cannot click element: {0}. Root cause: {1}", locator.toString(),
                e.getMessage()));
      }
    }
  }

  public void clickElement(String locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        element.click();
        logger.info("Clicked element: " + locator);
      } catch (Exception e) {
        logger.error(
            MessageFormat.format("Cannot click element: {0}. Root cause: {1}", locator,
                e.getMessage()));
      }
    }
  }

  public void clickElement(WebElement we) {
    if (we != null) {
      try {
        we.click();
        logger.info("Clicked element: " + we);
      } catch (Exception e) {
        logger.error(
            MessageFormat.format("Cannot click element: {0}. Root cause: {1}", we,
                e.getMessage()));
      }
    }
  }

  public void getElementAttributeValue(By locator, String attributeName) {
    WebElement element = findElement(locator);
    String value;
    if (element != null) {
      try {
        value = element.getAttribute(attributeName);
        logger.info("Got value '" + value + "' from element: " + locator.toString());
      } catch (Exception e) {
        logger.error("Cannot get value from element: " + locator.toString() + ". Root cause: "
            + e.getMessage());
      }
    }
  }


  public boolean verifyElementVisible(By locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        if (element.isDisplayed()) {
          logger.info(MessageFormat.format("Web element located by ''{0}'' is visible", locator));
          return true;
        }
      } catch (Exception e) {
        logger.error(
            "Cannot determine if element is displayed: " + locator.toString() + ". Root cause: "
                + e.getMessage());
      }
    }
    logger.error(MessageFormat.format("Web element located by ''{0}'' is not visible", locator));
    return false;
  }

  public boolean verifyElementVisible(String locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        if (element.isDisplayed()) {
          logger.info(MessageFormat.format("Web element located by ''{0}'' is visible", locator));
          return true;
        }
      } catch (Exception e) {
        logger.error(
            "Cannot determine if element is displayed: " + locator + ". Root cause: "
                + e.getMessage());
      }
    }
    logger.error(MessageFormat.format("Web element located by ''{0}'' is not visible", locator));
    return false;
  }

  public boolean verifyElementNotVisible(By locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        if (!element.isDisplayed()) {
          logger.info(
              MessageFormat.format("Web element located by ''{0}'' is not visible", locator));
          return true;
        }
      } catch (Exception e) {
        logger.error(
            "Cannot determine if element is displayed: " + locator.toString() + ". Root cause: "
                + e.getMessage());
      }
    }
    logger.error(MessageFormat.format("Web element located by ''{0}'' is visible", locator));
    return false;
  }

  public boolean verifyElementNotVisible(String locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        if (!element.isDisplayed()) {
          logger.info(
              MessageFormat.format("Web element located by ''{0}'' is not visible", locator));
          return true;
        }
      } catch (Exception e) {
        logger.error(
            "Cannot determine if element is displayed: " + locator.toString() + ". Root cause: "
                + e.getMessage());
      }
    }
    logger.error(MessageFormat.format("Web element located by ''{0}'' is visible", locator));
    return false;
  }

  public boolean verifyElementPresent(By locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      logger.info(MessageFormat.format("Web element located by ''{0}'' is present", locator));
      return true;
    } else {
      logger.error(MessageFormat.format("Web element located by ''{0}'' is not present", locator));
      return false;
    }
  }

  public boolean verifyElementPresent(String locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      logger.info(MessageFormat.format("Web element located by ''{0}'' is present", locator));
      return true;
    } else {
      logger.error(MessageFormat.format("Web element located by ''{0}'' is not present", locator));
      return false;
    }
  }

  public boolean verifyElementNotPresent(By locator) {
    WebElement element = findElement(locator);
    if (element == null) {
      logger.info(
          MessageFormat.format("Web element located by ''{0}'' is not present", locator));
      return true;
    } else {
      logger.error(MessageFormat.format("Web element located by ''{0}'' is present", locator));
      return false;
    }
  }

  public boolean verifyElementNotPresent(String locator) {
    WebElement element = findElement(locator);
    if (element == null) {
      logger.info(
          MessageFormat.format("Web element located by ''{0}'' is not present", locator));
      return true;
    } else {
      logger.error(MessageFormat.format("Web element located by ''{0}'' is present", locator));
      return false;
    }
  }


  public boolean verifyElementClickable(By locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        if (element.isEnabled()) {
          logger.info(MessageFormat.format("Web element located by ''{0}'' is clickable", locator));
          return true;
        }
      } catch (Exception e) {
        logger.error(
            "Cannot determine if element is enabled: " + locator.toString() + ". Root cause: "
                + e.getMessage());
      }
    }
    logger.error(MessageFormat.format("Web element located by ''{0}'' is not clickable", locator));
    return false;
  }


  public boolean verifyElementClickable(String locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        if (element.isEnabled()) {
          logger.info(MessageFormat.format("Web element located by ''{0}'' is clickable", locator));
          return true;
        }
      } catch (Exception e) {
        logger.error(
            "Cannot determine if element is enabled: " + locator.toString() + ". Root cause: "
                + e.getMessage());
      }
    }
    logger.error(MessageFormat.format("Web element located by ''{0}'' is not clickable", locator));
    return false;
  }

  public boolean verifyElementNotClickable(By locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        if (!element.isEnabled()) {
          logger.info(
              MessageFormat.format("Web element located by ''{0}'' is not clickable", locator));
          return true;
        }
      } catch (Exception e) {
        logger.error(
            "Cannot determine if element is enabled: " + locator.toString() + ". Root cause: "
                + e.getMessage());
      }
    }
    logger.error(MessageFormat.format("Web element located by ''{0}'' is clickable", locator));
    return false;
  }

  public boolean verifyElementNotClickable(String locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        if (!element.isEnabled()) {
          logger.info(
              MessageFormat.format("Web element located by ''{0}'' is not clickable", locator));
          return true;
        }
      } catch (Exception e) {
        logger.error(
            "Cannot determine if element is enabled: " + locator.toString() + ". Root cause: "
                + e.getMessage());
      }
    }
    logger.error(MessageFormat.format("Web element located by ''{0}'' is clickable", locator));
    return false;
  }


  public boolean verifyElementSelected(By locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        if (element.isSelected()) {
          logger.info(MessageFormat.format("Web element located by ''{0}'' is selected", locator));
          return true;
        }
      } catch (Exception e) {
        logger.error(
            "Cannot determine if element is selected: " + locator.toString() + ". Root cause: "
                + e.getMessage());
      }
    }
    return false;
  }

  public void submit(By locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        element.submit();
        logger.info("Submitted form: " + locator.toString());
      } catch (Exception e) {
        logger.error(
            "Cannot submit form: " + locator.toString() + ". Root cause: " + e.getMessage());
      }
    }
  }


  public String getElementText(By locator) {
    WebElement element = findElement(locator);
    String text = null;
    if (element != null) {
      try {
        text = element.getText();
        logger.info(
            MessageFormat.format("Text of web element located by ''{0}'' is ''{1}''", locator,
                text));
      } catch (Exception e) {
        logger.error("Cannot get text from element: " + locator.toString() + ". Root cause: "
            + e.getMessage());
      }
    }
    return text;
  }

  public String getElementTextByMethodGetText(String locator) {
    WebElement element = findElement(locator);
    String text = null;
    if (element != null) {
      try {
        text = element.getText();
        logger.info(
            MessageFormat.format("Text of web element located by ''{0}'' is ''{1}''", locator,
                text));
      } catch (Exception e) {
        logger.error("Cannot get text from element: " + locator + ". Root cause: "
            + e.getMessage());
      }
    }
    return text;
  }

  public String getElementTextByMethodGetText(WebElement element) {
    String text = null;
    if (element != null) {
      try {
        text = element.getText();
        logger.info(
            MessageFormat.format("Text of web element is ''{0}''", text));
      } catch (Exception e) {
        logger.error("Cannot get text from element: " + element + ". Root cause: "
            + e.getMessage());
      }
    }
    return text;
  }

  public String getElementTextByMethodGetAttributeInnerHTML(String locator) {
    WebElement element = findElement(locator);
    String text = null;
    if (element != null) {
      try {
        text = element.getAttribute("innerHTML");
        logger.info(
            MessageFormat.format("Text of web element located by ''{0}'' is ''{1}''", locator,
                text));
      } catch (Exception e) {
        logger.error("Cannot get text from element: " + locator + ". Root cause: "
            + e.getMessage());
      }
    }
    return text;
  }

  public String getElementTextByMethodGetAttributeInnerHTML(WebElement element) {
    String text = null;
    if (element != null) {
      try {
        text = element.getAttribute("innerHTML");
        logger.info(
            MessageFormat.format("Text of web element is ''{0}''", text));
      } catch (Exception e) {
        logger.error("Cannot get text from element: " + element + ". Root cause: "
            + e.getMessage());
      }
    }
    return text;
  }

  public String getElementText(WebElement we) {
    String text = null;
    if (we != null) {
      try {
        text = we.getText();
        logger.info(
            MessageFormat.format("Text of web element located by ''{0}'' is ''{1}''", we, text));
      } catch (Exception e) {
        logger.error("Cannot get text from element: " + we + ". Root cause: "
            + e.getMessage());
      }
    }
    return text;
  }

  public boolean verifyElementText(By locator, String expectedText) {
    WebElement element = findElement(locator);
    String actualText = null;
    if (element != null) {
      try {
        actualText = element.getText();
        if (actualText.equals(expectedText)) {
          logger.info(MessageFormat.format(
              "Actual text ''{0}'' and expected text ''{1}'' of web element located by ''{2}'' are the same",
              actualText, expectedText, locator));
          return true;
        }
      } catch (Exception e) {
        logger.error("Cannot get text from element: " + locator.toString() + ". Root cause: "
            + e.getMessage());
      }
    }
    logger.error(MessageFormat.format(
        "Actual text ''{0}'' and expected text ''{1}'' of web element located by ''{2}'' are not the same",
        actualText, expectedText, locator));
    return false;
  }

  public boolean verifyElementText(String locator, String expectedText) {
    WebElement element = findElement(locator);
    String actualText = null;
    if (element != null) {
      try {
        actualText = element.getText();
        if (actualText.equals(expectedText)) {
          logger.info(MessageFormat.format(
              "Actual text ''{0}'' and expected text ''{1}'' of web element located by ''{2}'' are the same",
              actualText, expectedText, locator));
          return true;
        }
      } catch (Exception e) {
        logger.error("Cannot get text from element: " + locator.toString() + ". Root cause: "
            + e.getMessage());
      }
    }
    logger.error(MessageFormat.format(
        "Actual text ''{0}'' and expected text ''{1}'' of web element located by ''{2}'' are not the same",
        actualText, expectedText, locator));
    return false;
  }

  public boolean verifyElementAttributeValue(String locator, String attributeName,
      String expectedAttributeValue) {
    WebElement element = findElement(locator);
    String actualAttributeValue = null;
    if (element != null) {
      try {
        actualAttributeValue = element.getAttribute(attributeName);
        if (actualAttributeValue.equals(expectedAttributeValue)) {
          logger.info(MessageFormat.format(
              "Actual attribute value ''{0}'' and expected attribute value ''{1}'' of web element located by ''{2}'' are the same",
              actualAttributeValue, expectedAttributeValue, locator));
          return true;
        }
      } catch (Exception e) {
        logger.error(
            "Cannot get attribute value from element: " + locator.toString() + ". Root cause: "
                + e.getMessage());
      }
    }
    logger.error(MessageFormat.format(
        "Actual attribute value ''{0}'' and expected attribute value ''{1}'' of web element located by ''{2}'' are not the same",
        actualAttributeValue, expectedAttributeValue, locator));
    return false;
  }

  public boolean verifyElementAttributeValue(By locator, String attributeName,
      String expectedAttributeValue) {
    WebElement element = findElement(locator);
    String actualAttributeValue = null;
    if (element != null) {
      try {
        actualAttributeValue = element.getAttribute(attributeName);
        if (actualAttributeValue.equals(expectedAttributeValue)) {
          logger.info(MessageFormat.format(
              "Actual attribute value ''{0}'' and expected attribute value ''{1}'' of web element located by ''{2}'' are the same",
              actualAttributeValue, expectedAttributeValue, locator));
          return true;
        }
      } catch (Exception e) {
        logger.error(
            "Cannot get attribute value from element: " + locator.toString() + ". Root cause: "
                + e.getMessage());
      }
    }
    logger.error(MessageFormat.format(
        "Actual attribute value ''{0}'' and expected attribute value ''{1}'' of web element located by ''{2}'' are not the same",
        actualAttributeValue, expectedAttributeValue, locator));
    return false;
  }

  public boolean verifyElementAttributeValue(WebElement element, String attributeName,
      String expectedAttributeValue) {
    String actualAttributeValue = null;
    if (element != null) {
      try {
        actualAttributeValue = element.getAttribute(attributeName);
        if (actualAttributeValue.equals(expectedAttributeValue)) {
          logger.info(MessageFormat.format(
              "Actual attribute value ''{0}'' and expected attribute value ''{1}'' of web element located by ''{2}'' are the same",
              actualAttributeValue, expectedAttributeValue, element));
          return true;
        }
      } catch (Exception e) {
        logger.error("Cannot get attribute value from element: " + element + ". Root cause: "
            + e.getMessage());
      }
    }
    logger.error(MessageFormat.format(
        "Actual attribute value ''{0}'' and expected attribute value ''{1}'' of web element located by ''{2}'' are not the same",
        actualAttributeValue, expectedAttributeValue, element));
    return false;
  }


  public String getElementTagName(By locator) {
    WebElement element = findElement(locator);
    String tagName = null;
    if (element != null) {
      try {
        tagName = element.getTagName();
        logger.info("Got tag name '" + tagName + "' from element: " + locator.toString());
      } catch (Exception e) {
        logger.error("Cannot get tag name from element: " + locator.toString() + ". Root cause: "
            + e.getMessage());
      }
    }
    return tagName;
  }


  public String getElementCssValue(By locator, String propertyName) {
    WebElement element = findElement(locator);
    String cssValue = null;
    if (element != null) {
      try {
        cssValue = element.getCssValue(propertyName);
        logger.info(
            "Got CSS value '" + cssValue + "' for property '" + propertyName + "' from element: "
                + locator.toString());
      } catch (Exception e) {
        logger.error("Cannot get CSS value for property '" + propertyName + "' from element: "
            + locator.toString() + ". Root cause: " + e.getMessage());
      }
    }
    return cssValue;
  }


  public String getElementAttribute(By locator, String attributeName) {
    WebElement element = findElement(locator);
    String attributeValue = null;
    if (element != null) {
      try {
        attributeValue = element.getAttribute(attributeName);
        logger.info("Got attribute value '" + attributeValue + "' for attribute '" + attributeName
            + "' from element: " + locator.toString());
      } catch (Exception e) {
        logger.error(
            "Cannot get attribute value for attribute '" + attributeName + "' from element: "
                + locator.toString() + ". Root cause: " + e.getMessage());
      }
    }
    return attributeName;
  }

  public String getElementAttribute(String locator, String attributeName) {
    WebElement element = findElement(locator);
    String attributeValue = null;
    if (element != null) {
      try {
        attributeValue = element.getAttribute(attributeName);
        logger.info("Got attribute value '" + attributeValue + "' for attribute '" + attributeName
            + "' from element: " + locator.toString());
      } catch (Exception e) {
        logger.error(
            "Cannot get attribute value for attribute '" + attributeName + "' from element: "
                + locator.toString() + ". Root cause: " + e.getMessage());
      }
    }
    return attributeValue;
  }


  public Dimension getElementSize(By locator) {
    WebElement element = findElement(locator);
    Dimension size = null;
    if (element != null) {
      try {
        size = element.getSize();
        logger.info("Got size: " + size.toString() + " for element: " + locator.toString());
      } catch (Exception e) {
        logger.error("Cannot get size for element: " + locator.toString() + ". Root cause: "
            + e.getMessage());
      }
    }
    return size;
  }

  public int getElementHeight(By locator) {
    WebElement element = findElement(locator);
    int height = 0;
    if (element != null) {
      try {
        height = element.getSize().getHeight();
        logger.info(MessageFormat.format("Height of web element located by ''{0}'' is {1}", locator,
            height));
      } catch (Exception e) {
        logger.error("Cannot get size for element: " + locator.toString() + ". Root cause: "
            + e.getMessage());
      }
    }
    return height;
  }

  public int getElementWidth(By locator) {
    WebElement element = findElement(locator);
    int width = 0;
    if (element != null) {
      try {
        width = element.getSize().getWidth();
        logger.info(
            MessageFormat.format("Width of web element located by ''{0}'' is {1}", locator, width));
      } catch (Exception e) {
        logger.error("Cannot get size for element: " + locator.toString() + ". Root cause: "
            + e.getMessage());
      }
    }
    return width;
  }

  public Point getElementLocation(By locator) {
    WebElement element = findElement(locator);
    Point location = null;
    if (element != null) {
      try {
        location = element.getLocation();
        logger.info("Got location: " + location.toString() + " for element: " + locator.toString());
      } catch (Exception e) {
        logger.error("Cannot get location for element: " + locator.toString() + ". Root cause: "
            + e.getMessage());
      }
    }
    return location;
  }

  //x
  public void getElementLeftPosition(By locator) {

  }


  //y
  public void getElementVerticalPosition(By locator) {

  }

  public void scrollDown() {
    try {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
      logger.info("Scrolled down successfully");
    } catch (Exception e) {
      logger.error("Cannot scroll down. Root cause: " + e.getMessage());
    }
  }


  public void waitFor(ExpectedCondition<Boolean> condition, int timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    try {
      wait.until(condition);
    } catch (TimeoutException e) {
      logger.error("Timeout waiting for condition: " + e.getMessage());
    }
  }


  public void selectOptionByIndex(String locator, int index) {
    if (index < 0) {
      throw new IllegalArgumentException("Index is greater than 0. Pls re-input index");
    }
    WebElement we = findElement(locator);
    try {
      logger.info(MessageFormat.format(
          "Selecting option of web element located by ''{0}'' with index ''{1}''", locator, index));
      Select select = new Select(we);
      select.selectByIndex(index);
      logger.info(MessageFormat.format(
          "Selected option of web element located by ''{0}'' with index ''{1}'' successfully",
          locator, index));
    } catch (Exception e) {
      logger.error(MessageFormat.format(
          "Cannot select option of web element located by ''{0}'' with index ''{1}''. Root cause: {2}",
          locator, index, e.getMessage()));
    }
  }

  public void selectOptionByValue(String locator, String value) {
    if (value == null) {
      throw new IllegalArgumentException("Value is not null. Pls re-input value");
    }
    WebElement we = findElement(locator);
    try {
      logger.info(MessageFormat.format(
          "Selecting option of web element located by ''{0}'' with value ''{1}''", locator, value));
      Select select = new Select(we);
      select.selectByValue(value);
      logger.info(MessageFormat.format(
          "Selected option of web element located by ''{0}'' with value ''{1}'' successfully",
          locator, value));
    } catch (Exception e) {
      logger.error(MessageFormat.format(
          "Cannot select option of web element located by ''{0}'' with value ''{1}''. Root cause: {2}",
          locator, value, e.getMessage()));
    }
  }

  public void selectOptionByLabel(String locator, String label) {
    if (label == null) {
      throw new IllegalArgumentException("Label is not null. Pls re-input label");
    }
    WebElement we = findElement(locator);
    try {
      logger.info(MessageFormat.format(
          "Selecting option of web element located by ''{0}'' with label ''{1}''", locator, label));
      Select select = new Select(we);
      select.selectByVisibleText(label);
      logger.info(MessageFormat.format(
          "Selected option of web element located by ''{0}'' with label ''{1}'' successfully",
          locator, label));
    } catch (Exception e) {
      logger.error(MessageFormat.format(
          "Cannot select option of web element located by ''{0}'' with label ''{1}''. Root cause: {2}",
          locator, label, e.getMessage()));
    }
  }

  public void delayInSecond(int seconds) {
    try {
      Thread.sleep(seconds * 1000L);
      logger.info(MessageFormat.format("Delayed ''{0}'' seconds", seconds));
    } catch (InterruptedException e) {
      logger.error(MessageFormat.format("Cannot delay ''{0}'' second(s)", seconds));
    }
  }

  public boolean waitForElementVisible(String locator, int... timeOut) {
    logger.info(MessageFormat.format("Wait for web element located by ''{0}'' visible", locator));
    int waitTime = timeOut.length > 0 ? timeOut[0] : defaultTimeOut; // ? :
    WebElement we = findElement(locator, waitTime);
    try {
      logger.info(
          MessageFormat.format("Waiting for web element located by ''{0}'' visible", locator));
      Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
      WebElement foundWe = (WebElement) wait.until(ExpectedConditions.visibilityOf(we));
      if (foundWe != null) {
        logger.info(MessageFormat.format(
            "Waited for web element located by ''{0}'' visible successfully within ''{1}'' second(s)",
            locator, waitTime));
        return true;
      } else {
        logger.error(
            MessageFormat.format(
                "Web element located by ''{0}'' is still not visible after ''{1}'' second(s)",
                locator,
                waitTime));
      }
    } catch (Exception e) {
      logger.error(
          MessageFormat.format(
              "Cannot wait for web element located by ''{0}'' visible after ''{1}'' second(s). Root cause: {2}",
              locator, waitTime, e.getMessage()));
    }
    return false;
  }

  public void clickJS(String locator) {
    WebElement we = findElement(locator);
    try {
      logger.info(MessageFormat.format("Clicking web element located by ''{0}''", locator));
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].click();", we);
      logger.info(
          MessageFormat.format("Click web element located by ''{0}'' successfully", locator));
    } catch (Exception e) {
      logger.error(MessageFormat.format("Cannot click web element located by ''{0}''", locator));
    }
  }

  public void clickJS(WebElement we) {
    try {
      logger.info("Clicking web element");
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].click();", we);
      logger.info("Click web element successfully");
    } catch (Exception e) {
      logger.error("Cannot click web element");
    }
  }

  public void clickElementByActionsClass(String locator) {
    try {
      logger.info(MessageFormat.format("Clicking web element located by ''{0}''", locator));
      Actions builder = new Actions(driver);
      builder.moveToElement(driver.findElement(By.xpath(locator))).click();

      logger.info(
          MessageFormat.format("Click web element located by ''{0}'' successfully", locator));
    } catch (Exception e) {
      logger.error(MessageFormat.format("Cannot click web element located by ''{0}''", locator));
    }
  }

  public void acceptAlert() {
    try {
      logger.info("Accepting alert");
      Alert alert = driver.switchTo().alert();
      alert.accept();
      logger.info("Accepted alert successfully");
    } catch (Exception e) {
      logger.error(MessageFormat.format("Cannot accept alert. Root cause: {0}", e.getMessage()));
    }
  }

  public void dismissAlert() {
    try {
      logger.info("Dismissing alert");
      Alert alert = driver.switchTo().alert();
      alert.dismiss();
      logger.info("Dismissed alert successfully");
    } catch (Exception e) {
      logger.error(MessageFormat.format("Cannot dismiss alert. Root cause: {0}", e.getMessage()));
    }
  }

  public String getAlertText() {
    String text = null;
    try {
      logger.info("Getting alert text");
      Alert alert = driver.switchTo().alert();
      text = alert.getText();
      logger.info(MessageFormat.format("Text on alert is ''{0}''", text));
    } catch (Exception e) {
      logger.error(MessageFormat.format("Cannot get alert text. Root cause: {0}", e.getMessage()));
    }
    return text;
  }

  public void setAlertText(String text) {
    try {
      logger.info(MessageFormat.format("Setting alert text: ''{0}''", text));
      Alert alert = driver.switchTo().alert();
      alert.sendKeys(text);
      logger.info(MessageFormat.format("Set alert text ''{0}'' successfully", text));
    } catch (Exception e) {
      logger.error(MessageFormat.format("Cannot set alert text. Root cause: {0}", e.getMessage()));
    }
  }

  public Integer getNumberOfFramesByJavaScript() {
    Integer numberOffFrames = null;
    try {
      logger.info("Getting total number of iFrames on a webpage by executing a java script");
      JavascriptExecutor exe = (JavascriptExecutor) driver;
      numberOffFrames = Integer.parseInt(exe.executeScript("return window.length").toString());
      logger.info(MessageFormat.format("Number of iframe on webpage is: ", numberOffFrames));
    } catch (Exception e) {
      logger.error(MessageFormat.format("Cannot get number of frame by javascript. Root cause: {0}",
          e.getMessage()));
    }
    return numberOffFrames;
  }

  public int getNumberOfFramesUsingTag() {
    int ans = 0;
    List<WebElement> iframeElements = null;
    try {
      logger.info("Getting total number of iFrames by using iframe tag");
      iframeElements = driver.findElements(By.tagName("iframe"));
      ans = iframeElements.size();
      logger.info(MessageFormat.format("Number of iframe on webpage is: ", ans));
      ans = iframeElements.size();
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot get number of frame by using iframe tag. Root cause: {0}",
              e.getMessage()));
    }
    return ans;
  }

  public void switchToFramesByWebElement(By locator) {
    WebElement element = null;
    logger.info(MessageFormat.format("Switching to frame located by ''{0}''", locator));
    try {
      element = driver.findElement(locator);
      driver.switchTo().frame(element);
      logger.info(
          MessageFormat.format("Switched successfully {0} frame located by ''{1}''", 1, locator));
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot switch frame by ''{0}''. Root cause: {1}",
              locator, e.getMessage()));
    }
  }

  public void switchToIFrame(String locator) {
    WebElement we = findElement(locator);
    logger.info(MessageFormat.format("Switching to iframe located by ''{0}''", locator));
    try {
      driver.switchTo().frame(we);
      logger.info(
          MessageFormat.format("Switched to iframe located by ''{0}'' successfully", locator));
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot switch to iframe located by ''{0}''. Root cause: {1}",
              locator, e.getMessage()));
    }
  }

  public void switchToFramesByID(String id) {
    logger.info("Switch to frame by id");
    try {
      driver.switchTo().frame(id);
      logger.info(
          MessageFormat.format("Switched successfully {0} frame located by ''{1}''", 1, id));
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot switch frame by ''{0}''. Root cause: {1}",
              id, e.getMessage()));
    }
  }

  public void switchToFramesByName(String name) {
    logger.info("Switch to frame by name");
    try {
      driver.switchTo().frame(name);
      logger.info(
          MessageFormat.format("Switched successfully {0} frame located by ''{1}''", 1, name));
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot switch frame by ''{0}''. Root cause: {1}",
              name, e.getMessage()));
    }
  }

  public void switchToDefaultContext() {
    logger.info("Switching to default context");
    try {
      driver.switchTo().defaultContent();
      logger.info("Switched to default context successfully");
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot switch to default context. Root cause:{0}", e.getMessage()));
    }
  }

  public void scrollUp() {
    logger.info("Scrolling up");
    try {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("window.scrollBy(0,-250)");
      logger.info("Scrolled up successfully");
    } catch (Exception e) {
      logger.error("Cannot scroll up. Root cause: " + e.getMessage());
    }
  }

  public void scrollRight() {
    logger.info("Scrolling right");
    try {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("window.scrollBy(200,-0)");
      logger.info("Scrolled right successfully");
    } catch (Exception e) {
      logger.error("Cannot scroll right. Root cause: " + e.getMessage());
    }
  }


  public void clickUsingActions(String locator) {
    WebElement element = findElement(locator);
    if (element != null) {
      try {
        Actions actions = new Actions(driver);
//        actions.moveToElement(element);
//        actions.click();
//        Action action = actions.build();
//        action.perform();
        actions.moveToElement(element).click().build().perform();
        logger.info("Clicked element: " + locator);
      } catch (Exception e) {
        logger.error(
            MessageFormat.format("Cannot click element: {0}. Root cause: {1}", locator,
                e.getMessage()));
      }
    }
  }

  public void clickUsingActions(WebElement we) {
    if (we != null) {
      try {
        Actions actions = new Actions(driver);
//        actions.moveToElement(we);
//        actions.click();
//        Action action = actions.build();
//        action.perform();
        actions.moveToElement(we).click().build().perform();
        logger.info("Clicked element: " + we);
      } catch (Exception e) {
        logger.error(
            MessageFormat.format("Cannot click element: {0}. Root cause: {1}", we,
                e.getMessage()));
      }
    }
  }

  public void clickOffset(String locator, int offsetX, int offsetY) {
    WebElement we = findElement(locator);
    if (we != null) {
      try {
        Actions actions = new Actions(driver);
        actions.moveToElement(we, offsetX, offsetY).click().build().perform();
        logger.info("Click offset element:" + we);
      } catch (Exception e) {
        logger.error(MessageFormat.format("Can not click offset element:{0}.Root cause:{1}",
            we, offsetX, offsetY, e.getMessage()));
      }
    }
  }

  public void dragAndDropByOffset(String locator, int offsetX, int offsetY) {
    WebElement we = findElement(locator);
    logger.info(
        MessageFormat.format("Dragging web element located by {0} to [{1},{2}]", locator, offsetX,
            offsetY));
    if (we != null) {
      try {
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(we, offsetX, offsetY).build().perform();
        logger.info(
            MessageFormat.format("Dropped web element located by {0} at [{1},{2}] successfully",
                locator, offsetX, offsetY));
      } catch (Exception e) {
        logger.error(MessageFormat.format(
            "Can not drag and drop web element located by {0} to [{1},{2}] . Root cause: {3}",
            locator, offsetX, offsetY, e.getMessage()));
      }
    }
  }

  public void clickOffset(WebElement we, int xOffset, int yOffset) {
    if (we != null) {
      try {
        Actions actions = new Actions(driver);
//        actions.moveToElement(we);
//        actions.click();
//        Action action = actions.build();
//        action.perform();
        actions.moveToElement(we, xOffset, yOffset).click().build().perform();
        logger.info("Clicked element: " + we);
      } catch (Exception e) {
        logger.error(
            MessageFormat.format("Cannot click element: {0}. Root cause: {1}", we,
                e.getMessage()));
      }
    }
  }

  public void doubleClick(WebElement we) {
    if (we != null) {
      try {
        Actions actions = new Actions(driver);
        actions.doubleClick(we).build().perform();
        logger.info("Double-clicked element: " + we);
      } catch (Exception e) {
        logger.error(
            MessageFormat.format("Cannot perform double-click on element: {0}. Root cause: {1}",
                we, e.getMessage()));
      }
    }
  }

  public void doubleClick(String locator) {
    WebElement we = findElement(locator);
    if (we != null) {
      try {
        Actions actions = new Actions(driver);
        actions.doubleClick(we).build().perform();
        logger.info("Double-clicked element: " + we);
      } catch (Exception e) {
        logger.error(
            MessageFormat.format("Cannot perform double-click on element: {0}. Root cause: {1}",
                we, e.getMessage()));
      }
    }
  }

  public void dragAndDropToElement(WebElement sourceElement, WebElement destinationElement) {
    if (sourceElement != null && destinationElement != null) {
      try {
        Actions actions = new Actions(driver);
        actions.dragAndDrop(sourceElement, destinationElement).build().perform();
        logger.info("Dragged and dropped element to target element");
      } catch (Exception e) {
        logger.error(MessageFormat.format(
            "Cannot perform drag and drop to target element: {0}. Root cause: {1}",
            destinationElement, e.getMessage()));
      }
    }
  }

  public void dragAndDropToElement(String sourceLocator, String desinationLocator) {
    WebElement sourceElement = findElement(sourceLocator);
    WebElement destinationElement = findElement(desinationLocator);
    if (sourceElement != null && destinationElement != null) {
      try {
        Actions actions = new Actions(driver);
        actions.dragAndDrop(sourceElement, destinationElement).build().perform();
        logger.info("Dragged and dropped element to target element");
      } catch (Exception e) {
        logger.error(MessageFormat.format(
            "Cannot perform drag and drop to target element: {0}. Root cause: {1}",
            destinationElement, e.getMessage()));
      }
    }
  }

  public void clickAndHold(WebElement we) {
    if (we != null) {
      try {
        Actions actions = new Actions(driver);
        actions.clickAndHold(we).build().perform();
        logger.info("Clicked and held element: " + we);
      } catch (Exception e) {
        logger.error(
            MessageFormat.format("Cannot perform click and hold on element: {0}. Root cause: {1}",
                we, e.getMessage()));
      }
    }
  }

  public void clickAndHold(String locator) {
    WebElement we = findElement(locator);
    if (we != null) {
      try {
        Actions actions = new Actions(driver);
        actions.clickAndHold(we).build().perform();
        logger.info("Clicked and held element: " + we);
      } catch (Exception e) {
        logger.error(
            MessageFormat.format("Cannot perform click and hold on element: {0}. Root cause: {1}",
                we, e.getMessage()));
      }
    }
  }

  public void contextClick(WebElement we) {
    if (we != null) {
      try {
        Actions actions = new Actions(driver);
        actions.contextClick(we).build().perform();
        logger.info("Performed context click on element: " + we);
      } catch (Exception e) {
        logger.error(
            MessageFormat.format("Cannot perform context click on element: {0}. Root cause: {1}",
                we, e.getMessage()));
      }
    }
  }

  public void contextClick(String locator) {
    WebElement we = findElement(locator);
    if (we != null) {
      try {
        Actions actions = new Actions(driver);
        actions.contextClick(we).build().perform();
        logger.info("Performed context click on element: " + we);
      } catch (Exception e) {
        logger.error(
            MessageFormat.format("Cannot perform context click on element: {0}. Root cause: {1}",
                we, e.getMessage()));
      }
    }
  }

  public void keyDown(Keys key) {
    try {
      Actions actions = new Actions(driver);
      actions.keyDown(key).build().perform();
      logger.info("Key down: " + key);
    } catch (Exception e) {
      logger.error(MessageFormat.format("Cannot perform key down: {0}. Root cause: {1}",
          key, e.getMessage()));
    }
  }

  public void keyUp(Keys key) {
    try {
      Actions actions = new Actions(driver);
      actions.keyUp(key).build().perform();
      logger.info("Key up: " + key);
    } catch (Exception e) {
      logger.error(MessageFormat.format("Cannot perform key up: {0}. Root cause: {1}",
          key, e.getMessage()));
    }
  }

  public void scrollToElement(WebElement we) {
    if (we != null) {
      try {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", we);
        logger.info("Scrolled to element: " + we);
      } catch (Exception e) {
        logger.error(MessageFormat.format("Cannot scroll to element: {0}. Root cause: {1}",
            we, e.getMessage()));
      }
    }
  }

  public void scrollToElementTop(String locator) {
    WebElement we = findElement(locator);
    if (we != null) {
      try {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", we);
        logger.info("Scrolled to element: " + we);
      } catch (Exception e) {
        logger.error(MessageFormat.format("Cannot scroll to element: {0}. Root cause: {1}",
            we, e.getMessage()));
      }
    }
  }

  public void scrollToElementBottom(String locator) {
    WebElement we = findElement(locator);
    if (we != null) {
      try {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(false);", we);
        logger.info("Scrolled to element: " + we);
      } catch (Exception e) {
        logger.error(MessageFormat.format("Cannot scroll to element: {0}. Root cause: {1}",
            we, e.getMessage()));
      }
    }
  }

  public void scrollToElementCenter(String locator) {
    WebElement we = findElement(locator);
    if (we != null) {
      try {
        String scrollElementIntoMiddle =
            "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, we);

        logger.info("Scrolled to element: " + we);
      } catch (Exception e) {
        logger.error(MessageFormat.format("Cannot scroll to element: {0}. Root cause: {1}",
            we, e.getMessage()));
      }
    }
  }

  public void scrollToElementCenter(WebElement we) {
    if (we != null) {
      try {
        String scrollElementIntoMiddle =
            "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, we);

        logger.info("Scrolled to element: " + we);
      } catch (Exception e) {
        logger.error(MessageFormat.format("Cannot scroll to element: {0}. Root cause: {1}",
            we, e.getMessage()));
      }
    }
  }

  public void scrollByAmount(int xOffset, int yOffset) {
    try {
      JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
      jsExecutor.executeScript("window.scrollBy(arguments[0], arguments[1]);", xOffset, yOffset);
      logger.info("Scrolled by amount: xOffset = " + xOffset + ", yOffset = " + yOffset);
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot scroll by amount. Root cause: {0} ", e.getMessage()));
    }
  }

  public void scrollFromOrigin(int xOffset, int yOffset) {
    try {
      JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
      jsExecutor.executeScript("window.scrollTo(arguments[0], arguments[1]);", xOffset, yOffset);
      logger.info("Scrolled from origin: xOffset = " + xOffset + ", yOffset = " + yOffset);
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot scroll from origin. Root cause: {0}", e.getMessage()));
    }
  }


  public void selectByIndexDropdownList(int index, String xpathdropdown) {
    logger.info("Select by index on dropdown");
    try {
      logger.info("Selecting by index on dropdown");
      Select select = new Select(findElement(xpathdropdown));
      select.selectByIndex(index);
    } catch (Exception e) {
      logger.error(MessageFormat.format("Cannot select by index on dropdown list. Root cause: {0}",
          e.getMessage()));
    }
  }

  public void selectByVisibleTextDropdownList(int index, String xpathdropdown) {
    logger.info("Select by visible text on dropdown list");
    try {
      logger.info("Selecting by visible text on dropdown");
      Select select = new Select(findElement(xpathdropdown));
      select.selectByVisibleText(String.valueOf(index));
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot select by visible text on dropdown list. Root cause: {0}",
              e.getMessage()));
    }
  }

  public void selectByVisibleTextDropdownList(String text, String xpathdropdown) {
    logger.info("Select by visible text on dropdown list");
    try {
      logger.info("Selecting by visible text on dropdown");
      Select select = new Select(findElement(xpathdropdown));
      select.selectByVisibleText(text);
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Cannot select by visible text on dropdown list. Root cause: {0}",
              e.getMessage()));
    }
  }

  public void uploadFileUsingSendKeysMethod(By locator, String filePath) {
    logger.info(MessageFormat.format("Upload file located by {0} ", locator));
    try {
      WebElement upload = driver.findElement(locator);
      upload.sendKeys(filePath);
      logger.info(MessageFormat.format("Upload file located by {0} successfully", locator));
    } catch (Exception e) {
      logger.error(MessageFormat.format("Can not upload file. Root cause: {0}", e.getMessage()));
    }
  }

  public void uploadFileUsingAutoIT(String filePath) {
    //CTRL+C
    StringSelection stringSelection = new StringSelection(filePath);
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    logger.info("Upload file using AutoIT");
    try {
      Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
      String browserName = cap.getBrowserName();
      System.out.println("Browser name: " + browserName);
      String autoItexePath = FileHelper.getAutoItFilePath("FileUpload.exe");
      //String autoItexePath = null;
//      switch (browserName) {
//        case "chrome":
//          autoItexePath = FileHelper.getAutoItFilePath("FileUpload.exe");
//          break;
//        case "firefox":
//          autoItexePath = FileHelper.getAutoItFilePath("FileUpload.exe");
//          break;
//        case "msedge": //msedge
//          autoItexePath = FileHelper.getAutoItFilePath("FileUpload.exe");
//          break;
//      }
      Process p = Runtime.getRuntime().exec(autoItexePath);
      p.waitFor();
      logger.info("Upload file located using AutoIT successfully");
    } catch (Exception e) {
      logger.error(MessageFormat.format("Can not upload file. Root cause: {0}", e.getMessage()));
    }
  }

  public void uploadFileUsingRobotClass(String filePath) {
    logger.info("Upload file located by " + filePath);
    try {
      Robot robot = new Robot();
      robot.delay(2000);
      //CTRL+C
      StringSelection stringSelection = new StringSelection(filePath);
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
      //CTRL+V
      robot.keyPress(KeyEvent.VK_CONTROL);
      robot.keyPress(KeyEvent.VK_V);
      robot.delay(3000);

      robot.keyRelease(KeyEvent.VK_CONTROL);
      robot.keyRelease(KeyEvent.VK_V);
      robot.delay(3000);
      //ENTER
      robot.keyPress(KeyEvent.VK_ENTER);
      robot.keyRelease(KeyEvent.VK_ENTER);
      logger.info("Upload file located successfully");
    } catch (Exception e) {
      logger.error(MessageFormat.format("Can not upload file. Root cause: {0}", e.getMessage()));
    }
  }

  public void getTooltipsText(By locator) {
    Actions actions = new Actions(driver);
    WebElement element = findElement(locator);
    actions.moveToElement(element).perform();
  }

  public void moveMouseToElement(By locator) {
    logger.info(MessageFormat.format("Moving mouse to center of element located: ''{0}''",
        locator.toString()));
    try {
      Actions actions = new Actions(driver);
      WebElement element = findElement(locator);
      actions.moveToElement(element).perform();
      logger.info(
          MessageFormat.format("Moved mouse to center of element located: ''{0}'' successfully",
              locator.toString()));
    } catch (Exception e) {
      logger.error(MessageFormat.format("Can not move to element located ''{0}''. Root cause: {1}",
          locator.toString(), e.getMessage()));
    }
  }

  public void moveMouseToElement(String locator) {
    logger.info(MessageFormat.format("Moving mouse to center of element located: ''{0}''",
        locator.toString()));
    try {
      Actions actions = new Actions(driver);
      WebElement element = findElement(locator);
      actions.moveToElement(element).perform();
      logger.info(
          MessageFormat.format("Moved mouse to center of element located: ''{0}'' successfully",
              locator.toString()));
    } catch (Exception e) {
      logger.error(MessageFormat.format("Can not move to element located ''{0}''. Root cause: {1}",
          locator.toString(), e.getMessage()));
    }
  }

  @Attachment(value = "ScreenShot", type = "image/png")
  public byte[] takeScreenShot() {
    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
  }

  public Object executeJavascript(String script, Object... args) {
    try {
      if (args != null) {
        logger.info(
            MessageFormat.format("Executing script ''{0}'' with argument ''{1}''", script, args));
      } else {
        logger.info(MessageFormat.format("Executing script ''{0}''", script));
      }
      JavascriptExecutor js = (JavascriptExecutor) driver;
      Object result = js.executeScript(script, args);
      if (args != null) {
        logger.info(
            MessageFormat.format("Executed script ''{0}'' with argument ''{1}'' successfully",
                script, args));
      } else {
        logger.info(MessageFormat.format("Executed script ''{0}'' successfully", script));
      }
      return result;
    } catch (Exception e) {
      if (args != null) {
        logger.error(MessageFormat.format(
            "Cannot execute script ''{0}'' with argument ''{1}''. Root cause: {2}", script, args,
            e.getMessage()));
      } else {
        logger.error(MessageFormat.format("Cannot execute script ''{0}''. Root cause: {1}", script,
            e.getMessage()));
      }
    }
    return null;
  }

  @Attachment(value = "ScreenShot", type = "image/png")
  public byte[] takeScreenShotAndHighLightElement(String locator) {
    byte[] image = null;
    try {
      logger.info(
          MessageFormat.format("Highlight web element located by ''{0}'' and taking screenshot",
              locator));
      WebElement we = findElement(locator);
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", we);
      image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
      logger.info("Taken screenshot successfully");
      js.executeScript("arguments[0].setAttribute('style', '');", we);
    } catch (Exception e) {
      logger.error(MessageFormat.format("Cannot take screenshot. Root cause: {1}", e.getMessage()));
    }
    return image;
  }

  @Attachment(value = "ScreenShot", type = "image/png")
  public byte[] takeScreenShotAndHighLightElement(WebElement we) {
    byte[] image = null;
    try {
      logger.info("Highlight web element located and taking screenshot");
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", we);
      image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
      logger.info("Taken screenshot successfully");
      js.executeScript("arguments[0].setAttribute('style', '');", we);
    } catch (Exception e) {
      logger.error(MessageFormat.format("Cannot take screenshot. Root cause: {1}", e.getMessage()));
    }
    return image;
  }

  public void deleteDirectory(File file) {
    logger.info(MessageFormat.format("Delete folder in directory {0} ", file.getPath()));
    try {
      for (File subfile : file.listFiles()) {
        if (subfile.isDirectory()) {
          deleteDirectory(subfile);
        }
        subfile.delete();
      }
      logger.info("Delete folder allure-results successfully");
    } catch (Exception e) {
      logger.error(MessageFormat.format("Can not delete folder. Root cause: {0}", e.getMessage()));
    }
  }

  public boolean verifyButtonRedirectToAnotherWebPage(String locator) {
    logger.info("Verify the button element redirect to another web page");
    try {
      String current_url = driver.getCurrentUrl();
      clickElement(locator);
      String redirect_url = driver.getCurrentUrl();
      if (current_url.equals(redirect_url)) {
        return false;
      }
    } catch (Exception e) {
      logger.error(
          MessageFormat.format("Button can not redirect to another web page. Root cause: {0}",
              e.getMessage()));
    }
    return true;
  }

  public boolean verifyElementHasAttribute(String locator, String attribute) {
    WebElement we = findElement(locator);
    if (we != null) {
      try {
        String value = we.getAttribute(attribute);
        if (value != null && !value.isEmpty()) {
          logger.info(
              MessageFormat.format("Web element located by ''{0}'' has attribute ''{1}''", locator,
                  attribute));
          AllureLogger.info(
              MessageFormat.format("Web element located by ''{0}'' has attribute ''{1}''", locator,
                  attribute));
          return true;
        } else {
          logger.info(
              MessageFormat.format("Web element located by ''{0}'' has not attribute ''{1}''",
                  locator, attribute));
          AllureLogger.info(
              MessageFormat.format("Web element located by ''{0}'' has not attribute ''{1}''",
                  locator, attribute));
        }
      } catch (Exception e) {
        logger.error(
            "Cannot determine if element is enabled: " + locator.toString() + ". Root cause: {0}",
            e.getMessage());
      }
    }
    return false;
  }

  public String getElementAttributeValue(String locator, String attributeName) {
    WebElement element = findElement(locator);
    String value = null;
    if (element != null) {
      try {
        value = element.getAttribute(attributeName);
        logger.info("Got value '" + value + "' from element: " + locator);
      } catch (Exception e) {
        logger.error("Cannot get value from element: " + locator + ". Root cause: "
            + e.getMessage());
      }
    }
    return value;
  }

  public boolean waitForElementPresent(String locator, int... timeOut) {
    int waitTime = timeOut.length > 0 ? timeOut[0] : defaultTimeOut;
    try {
      logger.info(
          MessageFormat.format("Waiting for web element located by ''{0}'' visible", locator));
      Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
      WebElement foundWe = (WebElement) wait.until(ExpectedConditions.presenceOfElementLocated(
          By.xpath(locator)));
      if (foundWe != null) {
        logger.info(MessageFormat.format(
            "Waited for web element located by ''{0}'' visible successfully within ''{1}'' second(s)",
            locator, waitTime));
        return true;
      } else {
        logger.error(
            MessageFormat.format(
                "Web element located by ''{0}'' is still not visible after ''{1}'' second(s)",
                locator,
                waitTime));
      }
    } catch (Exception e) {
      logger.error(
          MessageFormat.format(
              "Cannot wait for web element located by ''{0}'' visible after ''{1}'' second(s). Root cause: {2}",
              locator, waitTime, e.getMessage()));
    }
    return false;
  }

  public boolean waitForElementPresent(WebElement we, int... timeOut) {
    int waitTime = timeOut.length > 0 ? timeOut[0] : defaultTimeOut;
    try {
      logger.info("Waiting for web element visible");
      Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
      WebElement foundWe = wait.until(ExpectedConditions.presenceOfElementLocated((By) we));
      if (foundWe != null) {
        logger.info(MessageFormat.format(
            "Waited for web element visible successfully within ''{0}'' second(s)", waitTime));
        return true;
      } else {
        logger.error(
            MessageFormat.format(
                "Web element is still not visible after ''{0}'' second(s)", waitTime));
      }
    } catch (Exception e) {
      logger.error(
          MessageFormat.format(
              "Cannot wait for web element visible after ''{0}'' second(s). Root cause: {2}",
              waitTime, e.getMessage()));
    }
    return false;
  }

  public WebElement findElementWithParam(String locator, String param, int... timeOut) {
    String finalLocator = locator.replace("${param}", param);
    WebElement element = null;
    logger.info(MessageFormat.format("Finding web element located by ''{0}''", finalLocator));
    int waitTime = timeOut.length != 0 ? timeOut[0] : defaultTimeOut;
    try {
      Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(
              Duration.ofSeconds(waitTime))
          .pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
      element = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
        @Override
        public WebElement apply(WebDriver driver) {
          return driver.findElement(By.xpath(finalLocator));
        }
      });
      logger.info(
          MessageFormat.format("Found {0} web element located by ''{1}''", 1, finalLocator));
    } catch (TimeoutException e) {
      logger.error(
          MessageFormat.format("Cannot find web element located by ''{0}''. Root cause: {1}",
              finalLocator, e.getMessage()));
    }
    logger.info(MessageFormat.format("Timeout: ''{0}''", timeOut[0]));
    return element;
  }
}









