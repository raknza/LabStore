package labstore.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import labstore.data.Category;
import labstore.data.Product;

public class ProductDbManager {

  private static ProductDbManager dbManager = new ProductDbManager();

  private static ProductDbManager getInstance() {
    return dbManager;
  }

  private IDatabase database = new MySqlDatabase();

  private CategoryDbManager categoryDbManager = CategoryDbManager.getInstance();

  /**
   * Add new product.
   *
   * @param category category
   * @param name product name
   * @param description description
   * @throws SQLException Exception
   */
  public void addProduct(Category category, String name, String description) throws SQLException {
    String sql = "INSERT INTO LabStore.Product(category, name, description) VALUES(?, ?, ?)";

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, category.getId());
      preStmt.setString(2, name);
      preStmt.setString(3, description);
      preStmt.executeUpdate();
    }
  }

  /**
   * Get product by id
   *
   * @param id id
   * @return Product
   * @throws SQLException Exception
   */
  public Product getProductById(int id) throws SQLException {
    String sql = "SELECT * FROM LabStore.Product WHERE id = ?";
    Product product = new Product();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, id);
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          product.setId(id);
          product.setCategory(categoryDbManager.getCategoryById(rs.getInt("category")));
          product.setName(rs.getString("name"));
          product.setDescription(rs.getString("description"));
        }
      }
    }

    return product;
  }

  /**
   * Get product by name.
   *
   * @param name product name
   * @return Product
   * @throws SQLException Exception
   */
  public Product getProductByName(String name) throws SQLException {
    String sql = "SELECT * FROM LabStore.Product WHERE name = ?";
    Product product = new Product();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setString(1, name);
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          product.setId(rs.getInt("id"));
          product.setCategory(categoryDbManager.getCategoryById(rs.getInt("category")));
          product.setName(name);
          product.setDescription(rs.getString("description"));
        }
      }
    }

    return product;
  }

  /**
   * Get all Products.
   *
   * @return List of Products
   * @throws SQLException Exception
   */
  public List<Product> getAllProduct() throws SQLException {
    String sql = "SELECT * FROM LabStore.Product";
    List<Product> products = new ArrayList<>();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          Product product = new Product();
          product.setId(rs.getInt("id"));
          product.setCategory(categoryDbManager.getCategoryById(rs.getInt("category")));
          product.setName(rs.getString("name"));
          product.setDescription(rs.getString("description"));
          products.add(product);
        }
      }
    }

    return products;
  }

  /**
   * Delete product by id.
   *
   * @param id id
   * @throws SQLException Exception
   */
  public void deleteProduct(int id) throws SQLException {
    String sql = "DELETE FROM LabStore.Product WHERE id = ?";

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, id);
      preStmt.executeUpdate();
    }
  }
}
