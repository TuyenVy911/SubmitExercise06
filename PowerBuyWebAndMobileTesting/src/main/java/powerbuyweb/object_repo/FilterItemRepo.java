package powerbuyweb.object_repo;

public class FilterItemRepo {
  public static final String BTN_SCREEN_SIZE_GROUP = "//div[starts-with(@title,'Screen Size Group')]//div[starts-with(@class,'styled__TopLine')]/div//div[contains(text(),'inches')]";
  public static final String BTN_VIEW_MORE_CLICK = "//div[starts-with(@title,'Screen Size Group')]/div[2]//div[starts-with(@class,'styled__TopLine')]//span[@data-testid='btn-viewMoreClick' and text()='+1 more']";
}
