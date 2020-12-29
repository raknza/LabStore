package labstore.database;

import labstore.data.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDbManager {

  private static RoleDbManager dbManager = new RoleDbManager();

  public static RoleDbManager getInstance() {
    return dbManager;
  }

  private IDatabase database = new MySqlDatabase();

  private static final Logger LOGGER = LoggerFactory.getLogger(RoleDbManager.class);

  /**
   * Get Role Id by role name
   * 
   * @param roleName role name
   * @return roleId role Id
   */
  public int getRoleIdByName(String roleName) throws SQLException {
    String query = "SELECT id FROM Role WHERE role = ?";
    int roleId = 0;

    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(query)) {
      preStmt.setString(1, roleName);
      try (ResultSet rs = preStmt.executeQuery();) {
        while (rs.next()) {
          roleId = rs.getInt("id");
        }
      }
    }
    return roleId;
  }

  /**
   * Get Role name by status Id
   * 
   * @param roleId Id
   * @return statusEnum statusEnum
   */
  public RoleEnum getRoleNameById(int roleId) throws SQLException {
    String query = "SELECT role FROM Role WHERE id = ?";
    RoleEnum roleEnum;
    String roleName = null;

    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(query)) {
      preStmt.setInt(1, roleId);
      try (ResultSet rs = preStmt.executeQuery();) {
        while (rs.next()) {
          roleName = rs.getString("role");
        }
      }
      roleEnum = RoleEnum.getRoleEnum(roleName);
    }
    return roleEnum;
  }

}
