package powerbuyweb.models;

import java.io.Serializable;

public class Item implements Serializable {
  private String branch;
  private String name;
  private String price;
  private String sku;

  public Item() {
  }

  public Item(String branch, String name, String price, String sku) {
    this.branch = branch;
    this.name = name;
    this.price = price;
    this.sku = sku;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }
}
