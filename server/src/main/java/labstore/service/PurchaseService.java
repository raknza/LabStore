package labstore.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import labstore.data.Product;
import labstore.data.PurchaseDetail;
import labstore.data.User;
import labstore.database.ProductDbManager;
import labstore.database.PurchaseDetailDbManager;

import labstore.database.UserDbManager;
import org.json.JSONObject;

public class PurchaseService {

  private PurchaseDetailDbManager purchaseDetailDbManager = PurchaseDetailDbManager.getInstance();
  private UserDbManager userDbManager = UserDbManager.getInstance();
  private ProductDbManager productDbManager = ProductDbManager.getInstance();

  public PurchaseDetail getPurchase(int id) throws SQLException {
    return purchaseDetailDbManager.getPurchaseDetailById(id);
  }

  /**
   * Get all purchases.
   *
   * @return JSONObject
   * @throws SQLException Exception
   */
  public JSONObject getAllPurchase() throws SQLException {
    List<PurchaseDetail> stock = new ArrayList<>();
    List<PurchaseDetail> purchaseDetails = purchaseDetailDbManager.getAllPurchaseDetail();
    JSONObject jsonObject = new JSONObject();

    for (PurchaseDetail purchaseDetail : purchaseDetails) {
      if (purchaseDetail.getCount() != 0) {
        stock.add(purchaseDetail);
      }
    }

    jsonObject.put("Purchases", stock);
    return jsonObject;
  }

  /**
   * Get purchases by specific user.
   *
   * @param user User
   * @return JSONObject
   * @throws SQLException Exception
   */
  public List<PurchaseDetail> getPurchasesList(User user) throws SQLException {
    List<PurchaseDetail> stock = new ArrayList<>();
    List<PurchaseDetail> purchaseDetails = purchaseDetailDbManager.getAllPurchaseDetail();

    for (PurchaseDetail purchaseDetail : purchaseDetails) {
      if (purchaseDetail.getUser().equals(user)) {
        stock.add(purchaseDetail);
      }
    }

    return stock;
  }

  /**
   * Translate List to JSONObject.
   *
   * @param user User
   * @return JSONObject
   * @throws SQLException Exception
   */
  public JSONObject getPurchases(User user) throws SQLException {
    List<PurchaseDetail> purchaseDetails = getPurchasesList(user);
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("Purchases", purchaseDetails);
    return jsonObject;
  }

  /**
   * Add new Purchase by specific boss.
   *
   * @param username username
   * @param productName product name
   * @param cost cost
   * @param count count
   * @throws SQLException Exception
   */
  public void createPurchase(String username, String productName, int cost, int count)
      throws SQLException {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
    Date time = new Date();
    User user = userDbManager.getUser(username);
    Product product = productDbManager.getProductByName(productName);
    purchaseDetailDbManager.addPurchaseDetail(user, product, cost, count, time);
  }

  /**
   * Update specific Purchase count when it is listed.
   *
   * @param id Purchase id
   * @param count count
   * @throws SQLException Exception
   */
  public void updatePurchase(int id, int count) throws  SQLException {
    purchaseDetailDbManager.updatePurchaseDetailCountById(id, count);
  }
}
