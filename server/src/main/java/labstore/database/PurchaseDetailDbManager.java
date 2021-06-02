package labstore.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import labstore.data.Product;
import labstore.data.PurchaseDetail;
import labstore.data.User;

public class PurchaseDetailDbManager {

  private static PurchaseDetailDbManager dbManager = new PurchaseDetailDbManager();

  public static PurchaseDetailDbManager getInstance() {
    return dbManager;
  }

  private IDatabase database = new MySqlDatabase();

  private UserDbManager userDbManager = UserDbManager.getInstance();
  private ProductDbManager productDbManager = ProductDbManager.getInstance();

  /**
   * Add new Purchase detail.
   *
   * @param user User boss
   * @param product product
   * @param cost cost
   * @param count count
   * @param time time
   * @throws SQLException Exception
   */
  public void addPurchaseDetail(
      User user, Product product, int cost, int count, Date time) throws SQLException {
    String sql = "INSERT INTO LabStore.Purchase_Detail(uId, pId, cost, count, time)"
        + " VALUES(?, ?, ?, ?, ?)";
    Timestamp timestamp = new Timestamp(time.getTime());

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, user.getId());
      preStmt.setInt(2, product.getId());
      preStmt.setInt(3, cost);
      preStmt.setInt(4, count);
      preStmt.setTimestamp(5, timestamp);
      preStmt.executeUpdate();
    }
  }

  /**
   * Update specific Purchase count when it is listed.
   *
   * @param id Purchase id
   * @param count count
   * @throws SQLException Exception
   */
  public void updatePurchaseDetailCountById(int id, int count) throws SQLException {
    String sql = "UPDATE LabStore.Purchase_Detail SET count = ? WHERE (id = ?)";

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, count);
      preStmt.setInt(2, id);
      preStmt.executeUpdate();
    }
  }

  /**
   * Get Purchase detail by id.
   *
   * @param id id
   * @return Purchase Detail
   * @throws SQLException Exception
   */
  public PurchaseDetail getPurchaseDetailById(int id) throws SQLException {
    String sql = "SELECT * FROM LabStore.Purchase_Detail WHERE id = ?";
    PurchaseDetail purchaseDetail = new PurchaseDetail();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, id);
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          purchaseDetail.setId(id);
          purchaseDetail.setUser(userDbManager.getUser(rs.getInt("uId")));
          purchaseDetail.setProduct(productDbManager.getProductById(rs.getInt("pId")));
          purchaseDetail.setCost(rs.getInt("cost"));
          purchaseDetail.setCount(rs.getInt("count"));
          purchaseDetail.setTime(rs.getTimestamp("time"));
        }
      }
    }

    return purchaseDetail;
  }

  /**
   * Get all purchase by specific user.
   *
   * @param user User
   * @return List of Purchase
   * @throws SQLException Exception
   */
  public List<PurchaseDetail> getPurchaseDetailListByUser(User user) throws SQLException {
    String sql = "SELECT * FROM LabStore.Purchase_Detail WHERE uId = ?";
    List<PurchaseDetail> purchaseDetails = new ArrayList<>();

    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, user.getId());
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          PurchaseDetail purchaseDetail = new PurchaseDetail();
          purchaseDetail.setId(rs.getInt("id"));
          purchaseDetail.setUser(user);
          purchaseDetail.setProduct(productDbManager.getProductById(rs.getInt("pId")));
          purchaseDetail.setCost(rs.getInt("cost"));
          purchaseDetail.setCount(rs.getInt("count"));
          purchaseDetail.setTime(rs.getTimestamp("time"));
          purchaseDetails.add(purchaseDetail);
        }
      }
    }

    return purchaseDetails;
  }

  /**
   * Get all Purchase.
   *
   * @return List of Purchase
   * @throws SQLException Exception
   */
  public List<PurchaseDetail> getAllPurchaseDetail() throws SQLException {
    String sql = "SELECT * FROM LabStore.Purchase_Detail";
    List<PurchaseDetail> purchaseDetails = new ArrayList<>();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          PurchaseDetail purchaseDetail = new PurchaseDetail();
          purchaseDetail.setId(rs.getInt("id"));
          purchaseDetail.setUser(userDbManager.getUser(rs.getInt("uId")));
          purchaseDetail.setProduct(productDbManager.getProductById(rs.getInt("pId")));
          purchaseDetail.setCost(rs.getInt("cost"));
          purchaseDetail.setCount(rs.getInt("count"));
          purchaseDetail.setTime(rs.getTimestamp("time"));
          purchaseDetails.add(purchaseDetail);
        }
      }
    }

    return purchaseDetails;
  }

  /**
   * Delete Purchase Detail
   *
   * @param id id
   * @throws SQLException Exception
   */
  public void deletePurchaseDetail(int id) throws SQLException {
    String sql = "DELETE FROM LabStore.Purchase_Detail WHERE id = ?";

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, id);
      preStmt.executeUpdate();
    }
  }
}
