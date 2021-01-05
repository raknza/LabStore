package labstore.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import labstore.data.Category;
import labstore.data.User;
import labstore.data.Wish;

public class WishDbManager {

  private static WishDbManager dbManager = new WishDbManager();

  private static WishDbManager getInstance() {
    return dbManager;
  }

  private IDatabase database = new MySqlDatabase();

  private UserDbManager userDbManager = UserDbManager.getInstance();
  private CategoryDbManager categoryDbManager = CategoryDbManager.getInstance();

  /**
   * Add new wish.
   *
   * @param user User
   * @param category Category
   * @param name name
   * @param description description
   * @param time time
   * @throws SQLException Exception
   */
  public void addWish(User user, Category category, String name, String description, Date time)
      throws SQLException {
    String sql = "INSERT INTO LabStore.Wish(uId, category, name, description, time)"
        + " VALUES(?, ?, ?, ?, ?)";
    Timestamp timestamp = new Timestamp(time.getTime());

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, user.getId());
      preStmt.setInt(2, category.getId());
      preStmt.setString(3, name);
      preStmt.setString(4, description);
      preStmt.setTimestamp(5, timestamp);
      preStmt.executeUpdate();
    }
  }

  /**
   * Get wish by id.
   *
   * @param id id
   * @return Wish
   * @throws SQLException Exception
   */
  public Wish getWishById(int id) throws SQLException {
    String sql = "SELECT * FROM LabStore.Wish WHERE id = ?";
    Wish wish = new Wish();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, id);
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          wish.setId(id);
          wish.setUser(userDbManager.getUser(rs.getInt("uId")));
          wish.setCategory(categoryDbManager.getCategoryById(rs.getInt("category")));
          wish.setName(rs.getString("name"));
          wish.setDescription(rs.getString("description"));
          wish.setTime(rs.getTimestamp("time"));
        }
      }
    }

    return wish;
  }

  /**
   * Get wish by name.
   *
   * @param name name
   * @return Wish
   * @throws SQLException Exception
   */
  public Wish getWishByName(String name) throws SQLException {
    String sql = "SELECT * FROM LabStore.Wish WHERE name = ?";
    Wish wish = new Wish();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setString(1, name);
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          wish.setId(rs.getInt("id"));
          wish.setUser(userDbManager.getUser(rs.getInt("uId")));
          wish.setCategory(categoryDbManager.getCategoryById(rs.getInt("category")));
          wish.setName(name);
          wish.setDescription(rs.getString("description"));
          wish.setTime(rs.getTimestamp("time"));
        }
      }
    }

    return wish;
  }

  /**
   * Get all wishs.
   *
   * @return List of Wish
   * @throws SQLException Exception
   */
  public List<Wish> getAllWish() throws SQLException {
    String sql = "SELECT * FROM LabStore.Wish";
    List<Wish> wishes = new ArrayList<>();

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          Wish wish = new Wish();
          wish.setId(rs.getInt("id"));
          wish.setUser(userDbManager.getUser(rs.getInt("uId")));
          wish.setCategory(categoryDbManager.getCategoryById(rs.getInt("category")));
          wish.setName(rs.getString("name"));
          wish.setDescription(rs.getString("description"));
          wish.setTime(rs.getTimestamp("time"));
          wishes.add(wish);
        }
      }
    }

    return wishes;
  }

  /**
   * Delete wish
   *
   * @param id id
   * @throws SQLException Exception
   */
  public void deleteWish(int id) throws SQLException {
    String sql = "DELETE FROM LabStore.Wish WHERE id = ?";

    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, id);
      preStmt.executeUpdate();
    }
  }
}
