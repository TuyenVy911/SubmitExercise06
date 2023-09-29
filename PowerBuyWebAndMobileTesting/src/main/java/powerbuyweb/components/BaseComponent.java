package powerbuyweb.components;

import org.slf4j.Logger;
import powerbuyweb.utils.helper.LogHelper;
import powerbuyweb.utils.keywords.WebUI;

public class BaseComponent {
  protected Logger logger = LogHelper.getLogger();
  protected WebUI webUI;

  public BaseComponent(WebUI webUI){ this.webUI = webUI; }

}
