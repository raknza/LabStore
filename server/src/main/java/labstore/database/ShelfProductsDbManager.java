package labstore.database;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import labstore.data.PurchaseDetail;
import labstore.data.ShelfProduct;

public class ShelfProductsDbManager {

  private  static ShelfProductsDbManager dbManager = new ShelfProductsDbManager();

  public static ShelfProductsDbManager getInstance() {
    return dbManager;
  }

  private IDatabase database = new MySqlDatabase();

  private UserDbManager userDbManager = UserDbManager.getInstance();
  private PurchaseDetailDbManager purchaseDetailDbManager =  PurchaseDetailDbManager.getInstance();

  /**
   * Shelf a new product.
   *
   * @param purchaseDetail purchaseDetail
   * @param price price
   * @param count count
   * @throws SQLException Exception
   */
  public void addShelfProduct(PurchaseDetail purchaseDetail, int price, int count) throws SQLException{
    String sql = "INSERT INTO LabStore.ShelfProducts(purchaseDetail,price,count) VALUES(?,?,?)";

    try (Connection conn = database.getConnection();
    PreparedStatement preStmt = conn.prepareStatement(sql)){
      preStmt.setInt(1,purchaseDetail.getId());
      preStmt.setInt(2,price);
      preStmt.setInt(3,count);
      preStmt.executeUpdate();
    }
  }

  public List<ShelfProduct> getAllShelfProducts()throws SQLException{
    String sql = "SELECT * FROM LabStore.ShelfProduct";
    List<ShelfProduct> shelfProducts = new ArrayList<>();

    try (Connection conn = database.getConnection();
    PreparedStatement preStmt = conn.prepareStatement(sql)){
      try (ResultSet rs = preStmt.executeQuery()){
        while (rs.next()){
          ShelfProduct shelfProduct = new ShelfProduct();
          shelfProduct.setId(rs.getInt("id"));
          shelfProduct.setPurchaseDetail(purchaseDetailDbManager.getPurchaseDetailById(rs.getInt("purchaseDetail")));
          shelfProduct.setPrice(rs.getInt("price"));
          shelfProduct.setCount(rs.getInt("count"));

        }
      }
    }
  return shelfProducts;
  }
}
