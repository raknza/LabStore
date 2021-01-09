package labstore.service;

import labstore.data.Category;
import labstore.data.Product;
import labstore.data.RoleEnum;
import labstore.data.User;
import labstore.database.ProductDbManager;
import labstore.database.UserDbManager;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

  private ProductDbManager productDbManager = ProductDbManager.getInstance();
  private CategoryService categoryService = new CategoryService();

  /**
   * Get Product by id.
   *
   * @param id id
   * @return Product
   * @throws SQLException Exception
   */
  public Product getProduct(int id) throws SQLException {
    return productDbManager.getProductById(id);
  }

  /**
   * Get Product by name.
   *
   * @param name name
   * @return Product
   * @throws SQLException Exception
   */
  public Product getProduct(String name) throws SQLException {
    return productDbManager.getProductByName(name);
  }

  /**
   * Get all products.
   *
   * @return List of Product
   * @throws SQLException Exception
   */
  public List<Product> getAllProductList() throws SQLException {
    return productDbManager.getAllProduct();
  }

  /**
   * Add new product.
   *
   * @param categoryId category
   * @param name name
   * @param description description
   * @throws SQLException Exception
   */
  public void addProduct(int categoryId, String name, String description) throws SQLException {
    Category category = categoryService.getCategory(categoryId);
    productDbManager.addProduct(category, name, description);
  }

  /**
   * Get all products.
   *
   * @return JSONObject
   * @throws SQLException Exception
   */
  public JSONObject getAllProducts() throws SQLException {
    List<Product> products = getAllProductList();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Products", products);
    return jsonObject;
  }

  /**
   * Get all products by specific category
   *
   * @param category Category
   * @return List of Product
   * @throws SQLException Exception
   */
  public JSONObject getAllProducts(Category category) throws SQLException {
    List<Product> products = new ArrayList<>();
    List<Product> allProducts = getAllProductList();
    JSONObject jsonObject = new JSONObject();

    for (Product product : allProducts) {
      if (product.getCategory().equals(category)) {
        products.add(product);
      }
    }

    jsonObject.put("Products", products);
    return jsonObject;
  }

}
