package powerbuyweb.components;

import io.qameta.allure.Step;
import powerbuyweb.object_repo.AdvertisementPopupRepo;
import powerbuyweb.utils.keywords.WebUI;

public class AdvertisementPopup extends BaseComponent{

  public AdvertisementPopup(WebUI webUI) {
    super(webUI);
  }

  @Step("Click Close button on Advertisement popup")
  public void closePopup() {
    if (webUI.waitForElementVisible(AdvertisementPopupRepo.BTN_CLOSE,5)) {
      webUI.clickJS(AdvertisementPopupRepo.BTN_CLOSE);
    }
  }
}
