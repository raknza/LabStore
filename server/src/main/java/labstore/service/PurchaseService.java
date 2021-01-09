package labstore.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import labstore.data.PurchaseDetail;
import labstore.data.User;
import labstore.database.PurchaseDetailDbManager;

import org.json.JSONObject;

public class PurchaseService {

  private PurchaseDetailDbManager purchaseDetailDbManager = PurchaseDetailDbManager.getInstance();

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

  public JSONObject getPurchases(User user) throws SQLException {
    List<PurchaseDetail> purchaseDetails = getPurchasesList(user);
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("Purchases", purchaseDetails);
    return jsonObject;
  }
}
