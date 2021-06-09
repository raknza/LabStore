package labstore.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import labstore.data.PurchaseDetail;
import labstore.data.ShelfProduct;
import labstore.database.ShelfProductsDbManager;
import netscape.javascript.JSObject;
import org.json.JSONObject;

public class ShelfProductService {

  private ShelfProductsDbManager shelfProductsDbManager = ShelfProductsDbManager.getInstance();
  private PurchaseService purchaseService = new PurchaseService();

  /**
   * Get all shelf product.
   *
   * @return List of shelf product
   * @throws SQLException Exception
   */
  public List<ShelfProduct> getAllShelfProductList() throws SQLException{
    return shelfProductsDbManager.getAllShelfProducts();
  }

  /**
   * Shelf products.
   *
   * @param purchaseDetailId purchaseDetailId
   * @param price price
   * @param count count
   * @throws SQLException Exception
   */
  public void addShelfProduct(int purchaseDetailId,int price, int count)throws SQLException {
    PurchaseDetail purchaseDetail = purchaseService.getPurchase(purchaseDetailId);
    shelfProductsDbManager.addShelfProduct(purchaseDetail,price,count);
  }

  /**
   *  Get all shelf products.
   * @return JSONObject
   * @throws SQLException Exception
   */
  public JSONObject getAllShelfProducts() throws  SQLException{
    List<ShelfProduct> shelfProducts = shelfProductsDbManager.getAllShelfProducts();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Shelf_Products", shelfProducts);
    return jsonObject;
  }

  public JSONObject getAllShelfProducts(PurchaseDetail purchaseDetail)throws  SQLException{
    List<ShelfProduct> shelfProducts = new ArrayList<>();
    List<ShelfProduct> allShelfProducts = getAllShelfProductList();
    JSONObject jsonObject = new JSONObject();

    for (ShelfProduct shelfProduct : allShelfProducts){
      if(shelfProduct.getPurchaseDetail().getId() == purchaseDetail.getId()){
        allShelfProducts.add(shelfProduct);
      }
    }

    jsonObject.put("Shelf_Products", shelfProducts);
    return jsonObject;
  }
}
