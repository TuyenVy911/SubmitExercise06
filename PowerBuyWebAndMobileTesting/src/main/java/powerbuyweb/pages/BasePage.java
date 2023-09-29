package powerbuyweb.pages;

import powerbuyweb.components.AdvertisementPopup;
import powerbuyweb.components.FilterItem;
import powerbuyweb.components.Header;
import powerbuyweb.components.NavigationBar;
import powerbuyweb.utils.keywords.WebUI;

public class BasePage {
  protected WebUI webUI;

  //has a: Page has a advertisement popup, left menu
  // => Manager or New Customer has components: advertisement popup, left menu
  public AdvertisementPopup objAdvertisementPopup;
  public Header objHeader;
  public FilterItem objFilterItem;
  public NavigationBar objNavigationBar;


  public BasePage(WebUI webUI){
    this.webUI = webUI;
    objAdvertisementPopup = new AdvertisementPopup(this.webUI);
    objHeader = new Header(this.webUI);
    objFilterItem = new FilterItem(this.webUI);
    objNavigationBar = new NavigationBar(this.webUI);
  }

}
