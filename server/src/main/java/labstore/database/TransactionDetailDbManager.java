package labstore.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import labstore.data.PurchaseDetail;
import labstore.data.Transaction;
import labstore.data.User;

public class TransactionDetailDbManager {

  private static TransactionDetailDbManager dbManager = new TransactionDetailDbManager();

  public static TransactionDetailDbManager getInstance() {
    return dbManager;
  }

  private IDatabase database = new MySqlDatabase();

  private UserDbManager userDbManager = UserDbManager.getInstance();
  private PurchaseDetailDbManager purchaseDetailDbManager = PurchaseDetailDbManager.getInstance();

  /**
   * Add new transaction detail
   *
   * @param user User
   * @param purchaseDetail Purchase Detail
   * @param count count
   * @param time time
   * @throws SQLException Exception
   */
  public void addTransactionDetail(
      User user, PurchaseDetail purchaseDetail, int count, Date time) throws SQLException {
    String sql = "INSERT INTO LabStore.Transaction_Detail(uId, pdId, count, time)"
        + " VALUES(?, ?, ?, ?);";
    Timestamp timestamp = new Timestamp(time.getTime());

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, user.getId());
      preStmt.setInt(2, purchaseDetail.getId());
      preStmt.setInt(3, count);
      preStmt.setTimestamp(4, timestamp);
      preStmt.executeUpdate();
    }
  }

  /**
   *  Get Transaction detail by id
   *
   * @param id id
   * @return Transaction detail
   * @throws SQLException Exception
   */
  public Transaction getTransactionById(int id) throws SQLException {
    String sql = "SELECT * FROM LabStore.Transaction_Detail WHERE id = ?";
    Transaction transaction = new Transaction();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, id);
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          transaction.setId(id);
          transaction.setUser(userDbManager.getUser(rs.getInt("uId")));
          transaction.setPurchaseDetail(purchaseDetailDbManager
              .getPurchaseDetailById(rs.getInt("pdId")));
          transaction.setCount(rs.getInt("count"));
          transaction.setTime(rs.getTimestamp("time"));
        }
      }
    }

    return transaction;
  }

  /**
   * Get all transaction detail by specific user
   *
   * @param user User
   * @return List of Transaction
   * @throws SQLException Exception
   */
  public List<Transaction> getTransactionByUser(User user) throws SQLException {
    String sql = "SELECT * FROM LabStore.Transaction_Detail WHERE uId = ?";
    List<Transaction> transactions = new ArrayList<>();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, user.getId());
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          Transaction transaction = new Transaction();
          transaction.setId(rs.getInt("id"));
          transaction.setUser(user);
          transaction.setPurchaseDetail(purchaseDetailDbManager
              .getPurchaseDetailById(rs.getInt("pdId")));
          transaction.setCount(rs.getInt("count"));
          transaction.setTime(rs.getTimestamp("time"));
          transactions.add(transaction);
        }
      }
    }

    return transactions;
  }

  /**
   * Get all transaction detail by specific Purchase
   *
   * @param purchaseDetail Purchase detail
   * @return List of Transaction
   * @throws SQLException Exception
   */
  public List<Transaction> getTransactionByPurchase(PurchaseDetail purchaseDetail)
      throws SQLException {
    String sql = "SELECT * FROM LabStore.Transaction_Detail WHERE pdId = ?";
    List<Transaction> transactions = new ArrayList<>();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, purchaseDetail.getId());
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          Transaction transaction = new Transaction();
          transaction.setId(rs.getInt("id"));
          transaction.setUser(userDbManager.getUser(rs.getInt("uId")));
          transaction.setPurchaseDetail(purchaseDetail);
          transaction.setCount(rs.getInt("count"));
          transaction.setTime(rs.getTimestamp("time"));
          transactions.add(transaction);
        }
      }
    }

    return transactions;
  }

  /**
   * Get all transaction detail.
   *
   * @return Transaction
   * @throws SQLException Exception
   */
  public List<Transaction> getAllTransaction() throws SQLException {
    String sql = "SELECT * FROM LabStore.Transaction_Detail";
    List<Transaction> transactions = new ArrayList<>();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          Transaction transaction = new Transaction();
          transaction.setId(rs.getInt("id"));
          transaction.setUser(userDbManager.getUser(rs.getInt("uId")));
          transaction.setPurchaseDetail(purchaseDetailDbManager
              .getPurchaseDetailById(rs.getInt("pdId")));
          transaction.setCount(rs.getInt("count"));
          transaction.setTime(rs.getTimestamp("time"));
          transactions.add(transaction);
        }
      }
    }

    return transactions;
  }

  /**
   * Delete transaction by specific id.
   *
   * @param id id
   * @throws SQLException Exception
   */
  public void deleteTransaction(int id) throws SQLException {
    String sql = "DELETE FROM LabStore.Transaction_Detail WHERE id = ?";

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, id);
      preStmt.executeUpdate();
    }
  }
}
