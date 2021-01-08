package labstore.data;

import java.util.Date;

public class Transaction {

  private int id;

  private User user;

  private PurchaseDetail purchaseDetail;

  private int count;

  private Date time;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public PurchaseDetail getPurchaseDetail() {
    return purchaseDetail;
  }

  public void setPurchaseDetail(PurchaseDetail purchaseDetail) {
    this.purchaseDetail = purchaseDetail;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }
}
