package labstore.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import labstore.data.Category;

public class CategoryDbManager {

  private static CategoryDbManager dbManager = new CategoryDbManager();

  public static CategoryDbManager getInstance() {
    return dbManager;
  }

  private IDatabase database = new MySqlDatabase();

  /**
   * Add new category
   *
   * @param name name
   * @throws SQLException exception
   */
  public void addCategory(String name) throws SQLException {
    String sql = "INSERT INTO LabStore.Category(name) VALUES(?)";

    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setString(1, name);
      preStmt.executeUpdate();
    }
  }

  /**
   * Get category by id
   *
   * @param id id
   * @return Category
   * @throws SQLException exception
   */
  public Category getCategoryById(int id) throws SQLException {
    String sql = "SELECT * FROM LabStore.Category WHERE id = ?";
    Category category = new Category();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, id);
      String name = "";
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          name = rs.getString("name");
        }
      }
      category.setId(id);
      category.setName(name);
    }

    return category;
  }

  /**
   * Get category by name
   *
   * @param name name
   * @return Category
   * @throws SQLException exception
   */
  public Category getCategoryByName(String name) throws SQLException {
    String sql = "SELECT * FROM LabStore.Category WHERE name = ?";
    Category category = new Category();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setString(1, name);
      int id = 0;
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          id = rs.getInt("id");
        }
      }
      category.setId(id);
      category.setName(name);
    }

    return category;
  }

  /**
   * Get all categories
   *
   * @return Category list
   * @throws SQLException exception
   */
  public List<Category> getAllCategory() throws SQLException {
    String sql = "SELECT * FROM LabStore.Category";
    List<Category> categories = new ArrayList<>();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          Category category = new Category();
          category.setId(rs.getInt("id"));
          category.setName(rs.getString("name"));
          categories.add(category);
        }
      }
    }

    return categories;
  }

  /**
   * Delete category by id
   *
   * @param id id
   * @throws SQLException exception
   */
  public void deleteCategory(int id) throws SQLException {
    String sql = "DELETE FROM LabStore.Category WHERE id = ?";

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, id);
      preStmt.executeUpdate();
    }
  }
}
