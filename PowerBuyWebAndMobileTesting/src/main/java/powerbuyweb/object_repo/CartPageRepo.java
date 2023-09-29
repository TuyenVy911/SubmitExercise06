package powerbuyweb.object_repo;

public class CartPageRepo {
  public static final String LBL_SKU_NUMBER = "//div[starts-with(@class,'CartItem__CardBody')]/div[starts-with(@class,'Row__Wrapper')]/div[starts-with(@class,'Col__Column-sc')]//span[contains(text(),'SKU')]/following-sibling::span[starts-with(@class,'Text-sc')]";
  public static final String LBL_PRODUCT_BRANCH_NAME = "//div[starts-with(@class,'CartItem__CardBody')]/div[starts-with(@class,'Row__Wrapper')]/div[starts-with(@class,'Col__Column-sc')]//div[starts-with(@class,'MultipleLineClamp__ProductCutoffDot')]//span";
  public static final String LBL_PRODUCT_NAME = "//div[starts-with(@class,'CartItems__CartItemsContainer')][${param}]/div[1]/div[1]/div[1]/div[2]/div[1]/a/div";
  public static final String LBL_PRODUCT_PRICE = "//div[starts-with(@class,'CartItems__CartItemsContainer')][${param}]/div[1]/div[3]//div[contains(@id,'productTotal')]";
  public static final String LBL_PRODUCT_CONTAINER = "//div[starts-with(@class,'CartItems__CartItemsContainer')]";
}
