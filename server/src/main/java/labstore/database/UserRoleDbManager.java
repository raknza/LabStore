package labstore.database;

import labstore.data.User;
import labstore.data.RoleEnum;
import labstore.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDbManager {
  private static UserRoleDbManager dbManager = new UserRoleDbManager();

  public static UserRoleDbManager getInstance() {
    return dbManager;
  }

  private IDatabase database = new MySqlDatabase();
  RoleDbManager rdb = RoleDbManager.getInstance();
  UserDbManager udb = UserDbManager.getInstance();

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleDbManager.class);

  /**
   * Add RoleUser to database by User
   * 
   * @param user User
   */
  public void addRoleUser(User user) throws SQLException {
    String username = user.getUsername();
    int uid = udb.getUserIdByUsername(username);
    int rid = rdb.getRoleIdByName(user.getRole().getTypeName());
    addRoleUser(rid, uid);
  }

  /**
   * Add RoleUser to database by rid and uid
   * 
   * @param rid Role Id
   * @param uid User Id
   */
  public void addRoleUser(int rid, int uid) throws SQLException {
    String sql = "INSERT INTO User_Role(rId, uId)  VALUES(?, ?)";

    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, rid);
      preStmt.setInt(2, uid);
      preStmt.executeUpdate();
    }
  }

  /**
   * get Top Role by user Id
   * 
   * @param uid User Id
   * @return topRid Top Role Id
   */
  public int getTopRid(int uid) throws SQLException {
    int topRid = 0;
    String sql = "SELECT rid from User_Role a where uId = ? "
        + "AND (a.rId =(SELECT min(rId) FROM User_Role WHERE uId = ?));";
    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, uid);
      preStmt.setInt(2, uid);
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          topRid = rs.getInt("rid");
        }
      }
    }
    return topRid;
  }

  /**
   * get ruid by Role Id and user Id
   * 
   * @param rid Role Id
   * @param uid User Id
   * @return ruId RoleUser Id
   */
  public int getRuid(int rid, int uid) throws SQLException {
    int ruid = 0;
    String sql = "SELECT id FROM User_Role WHERE rId=? AND uId=?";
    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, rid);
      preStmt.setInt(2, uid);
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          ruid = rs.getInt("id");
        }
      }
    }
    return ruid;
  }

  /**
   * get RoleList by User Id
   * 
   * @return lsRids List RoleEnum
   */
  public List<RoleEnum> getRoleList(int uid) throws SQLException {
    List<RoleEnum> lsRole = new ArrayList<>();
    String sql = "SELECT rId FROM User_Role WHERE uId = ?";
    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, uid);
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          int rid = rs.getInt("rId");
          RoleEnum role = rdb.getRoleNameById(rid);
          lsRole.add(role);
        }
      }
    }
    return lsRole;
  }

  /**
   * get uids by role Id
   * 
   * @return lsUids role id
   */
  public List<Integer> getUids(int rid) throws SQLException {
    List<Integer> lsUids = new ArrayList<>();
    String sql = "SELECT uId FROM User_Role WHERE rId = ?";
    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setInt(1, rid);
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          int uid = rs.getInt("uId");
          lsUids.add(uid);
        }
      }
    }
    return lsUids;
  }

  /**
   * delete User_Role by uid
   *
   * @param userId The user id
   *
   */
  public void deleteRoleUserByUserId(int userId) throws SQLException {
    String query = "DELETE FROM User_Role WHERE uid = ?";
    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(query)) {

      preStmt.setInt(1, userId);
      preStmt.executeUpdate();

    }
  }

}