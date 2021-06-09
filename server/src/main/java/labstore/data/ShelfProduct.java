package labstore.data;

import java.util.Date;

public class ShelfProduct {

  private int id;

  private PurchaseDetail purchaseDetail;

  private int price;

  private int count;

  private Date shelfTime;

  private Date lastestTradeTime;

  public int getId(){
    return id;
  }

  public void setId(int id){
    this.id = id;
  }

  public PurchaseDetail getPurchaseDetail(){
    return purchaseDetail;
  }

  public void setPurchaseDetail(PurchaseDetail purchaseDetail){
    this.purchaseDetail = purchaseDetail;
  }

  public int getPrice(){
  return price;
}

  public void setPrice(int price){
    this.price = price;
  }

  public int getCount(){
    return count;
  }

  public void setCount(int count){
    this.count = count;
  }

  public Date getShelfTime(){
    return shelfTime;
  }

  public void setShelfTime(Date shelfTime) {
    this.shelfTime = shelfTime;
  }

  public Date getLastestTradeTime(){
    return lastestTradeTime;
  }

  public void setLastestTradeTime(Date lastestTradeTime){
    this.lastestTradeTime = lastestTradeTime;
  }
}
