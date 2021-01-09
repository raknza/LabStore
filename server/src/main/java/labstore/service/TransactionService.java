package labstore.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import labstore.data.PurchaseDetail;
import labstore.data.Transaction;
import labstore.data.User;
import labstore.database.TransactionDetailDbManager;

import org.json.JSONObject;

public class TransactionService {
  private TransactionDetailDbManager transactionDetailDbManager =
      TransactionDetailDbManager.getInstance();
  private PurchaseService purchaseService = new PurchaseService();

  /**
   * Add transaction.
   *
   * @param user User
   * @param purchaseDetail Purchase detail
   * @param count count
   * @throws SQLException Exception
   */
  public void updateTransaction(
      User user, PurchaseDetail purchaseDetail, int count) throws SQLException {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
    Date time = new Date();
    transactionDetailDbManager.addTransactionDetail(user, purchaseDetail, count, time);
  }

  /**
   * Get all transactions.
   *
   * @return List of Transaction
   * @throws SQLException Exception
   */
  public List<Transaction> getAllTransaction() throws SQLException {
    return transactionDetailDbManager.getAllTransaction();
  }

  /**
   * Get transaction by specific customer.
   *
   * @param user customer
   * @return JSONObject
   * @throws SQLException Exception
   */
  public JSONObject getTransactionByCus(User user) throws SQLException {
    List<Transaction> stock = new ArrayList<>();
    List<Transaction> transactions = getAllTransaction();
    JSONObject jsonObject = new JSONObject();

    for (Transaction transaction : transactions) {
      if (transaction.getUser().equals(user)) {
        stock.add(transaction);
      }
    }

    jsonObject.put("Transactions", stock);
    return jsonObject;
  }

  /**
   * Get transaction by specific boss.
   *
   * @param user boss
   * @return JSONObject
   * @throws SQLException Exception
   */
  public JSONObject getTransactionByBoss(User user) throws SQLException {
    List<Transaction> stock = new ArrayList<>();
    List<Transaction> transactions = getAllTransaction();
    List<PurchaseDetail> purchaseDetails = purchaseService.getPurchasesList(user);
    JSONObject jsonObject = new JSONObject();

    for (Transaction transaction : transactions) {
      for (PurchaseDetail purchaseDetail : purchaseDetails) {
        if (transaction.getPurchaseDetail().equals(purchaseDetail)) {
          stock.add(transaction);
          break;
        }
      }
    }

    jsonObject.put("Transactions", stock);
    return jsonObject;
  }
}
