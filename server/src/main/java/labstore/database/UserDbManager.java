package labstore.database;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import labstore.data.User;
import labstore.service.RoleEnum;
import labstore.utils.ExceptionUtil;

public class UserDbManager {

  private static final String USERNAME = "username";
  private static final String NAME = "name";
  private static final String PASSWORD = "password";
  private static final String ROLE = "role";
  private static UserDbManager dbManager = null;
  private static final Logger LOGGER = LoggerFactory.getLogger(UserDbManager.class);

  /**
   * 
   * @return dbManager
   */
  public static UserDbManager getInstance() {
    if (dbManager == null) {
      dbManager = new UserDbManager();
    }
    return dbManager;
  }

  private IDatabase database = new MySqlDatabase();

  private UserDbManager() {

  }

  /**
   * Add user to database
   * 
   * @param user The user
   */
  public void addUser(User user) {
    String sql = "INSERT INTO " + "User" + "(" + USERNAME + "," + PASSWORD + ","
        + NAME + "," + ROLE + ")" + "VALUES(?, ?, ?, ?)" ;

    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setString(1, user.getUsername());
      preStmt.setString(2, passwordMD5(user.getPassword()));
      preStmt.setString(3, user.getName());
      preStmt.setString(4, user.getRole().name());
      preStmt.executeUpdate();
    } catch (SQLException e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }

  /**
   * encrypt the user password
   * 
   * @param password The user's password
   * @return MD5 string
   * @throws NoSuchAlgorithmException on security api call error
   */
  public String passwordMD5(String password) {
    String hashtext = "";
    try {
      String msg = password;
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] messageDigest = md.digest(msg.getBytes());
      BigInteger number = new BigInteger(1, messageDigest);
      hashtext = number.toString(16);

      StringBuilder bld = new StringBuilder();
      for (int count = hashtext.length(); count < 32; count++) {
        bld.append("0");
      }
      hashtext = bld.toString() + hashtext;
    } catch (NoSuchAlgorithmException e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }

    return hashtext;
  }

  /**
   * get user password
   * 
   * @param username user stu id
   * @return password
   */
  public String getPassword(String username) {
    String token = "";
    String query = "SELECT password FROM User WHERE username = ?";

    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(query)) {
      preStmt.setString(1, username);
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          token = rs.getString(PASSWORD);
        }
      }
    } catch (SQLException e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
    return token;
  }

  /**
   * update user db password
   * 
   * @param username user stu id
   * @param password user new password
   */
  public void modifiedUserPassword(String username, String password) {
    String query = "UPDATE User SET password=? WHERE username = ?";

    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(query)) {
      String newPass = passwordMD5(password);
      preStmt.setString(1, newPass);
      preStmt.setString(2, username);
      preStmt.executeUpdate();
    } catch (SQLException e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }

  /**
   * check old password
   * 
   * @param username user stu id
   * @param password user old password
   * @return T or F
   */
  public boolean checkPassword(String username, String password) {
    boolean check = false;
    String currPassword = getPassword(username);
    if (currPassword.equals(passwordMD5(password))) {
      check = true;
    }
    return check;
  }

  /**
   * user name to find userId in db
   * 
   * @return id
   */
  public int getUserIdByUsername(String username) {
    String query = "SELECT id FROM User WHERE username = ?";
    int id = -1;
    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(query)) {
      preStmt.setString(1, username);
      try (ResultSet rs = preStmt.executeQuery();) {
        while (rs.next()) {
          id = rs.getInt("id");
        }
      }
    } catch (SQLException e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
    return id;
  }

  /**
   * Get user from database
   * 
   * @param username The gitlab user name
   * @return user
   */
  public User getUser(String username) {
    return getUser( getUserIdByUsername(username) );
  }

  /**
   * Get user from database
   * 
   * @param id user id
   * @return user
   */
  public User getUser(int id) {
    User user = new User();
    String query = "SELECT * FROM User WHERE id = ?";

    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(query)) {
      preStmt.setInt(1, id);
      try (ResultSet rs = preStmt.executeQuery()) {
        while (rs.next()) {
          String username = rs.getString("username");
          String name = rs.getString(NAME);
          String password = rs.getString(PASSWORD);
          String role = rs.getString(ROLE);
          user.setId(id);
          user.setUsername(username);
          user.setName(name);
          user.setPassword(password);
          user.setRole(RoleEnum.getRoleEnum(role));
        }
      }
    } catch (SQLException e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
    return user;
  }


  /**
   * List all the database user
   * 
   * @return list of user
   */
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM User";

    try (Connection conn = database.getConnection(); Statement stmt = conn.createStatement()) {
      try (ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
          int id = rs.getInt("id");
          String username = rs.getString(USERNAME);
          String name = rs.getString(NAME);
          String password = rs.getString(PASSWORD);
          String role = rs.getString(ROLE);

          User user = new User();
          user.setId(id);
          user.setUsername(username);
          user.setName(name);
          user.setPassword(password);
          user.setRole(RoleEnum.BOSS);
          users.add(user);
        }
      }

    } catch (SQLException e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
    return users;
  }

  /**
   * check username
   * 
   * @param username studentId
   * @return isExist
   */
  public boolean checkUsername(String username) {
    boolean isExist = false;
    String query = "SELECT count(*) FROM User WHERE username = ?";

    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(query)) {
      preStmt.setString(1, username);
      try (ResultSet rs = preStmt.executeQuery()) {
        rs.next();
        if (rs.getInt("count(*)") > 0) {
          isExist = true;
        }
      }
    } catch (SQLException e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
    return isExist;
  }

  /**
   * Get user from database
   *
   * @param id The db user id
   * @return user
   */
  public String getUsername(int id) {
    String name = "";
    String query = "SELECT username FROM User WHERE id = ?";
    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(query)) {
      preStmt.setInt(1, id);
      try (ResultSet rs = preStmt.executeQuery();) {
        while (rs.next()) {
          name = rs.getString(USERNAME);
        }
      }
    } catch (SQLException e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
    return name;
  }

  /**
   * delete User from id
   *
   * @param id The user id
   *
   */
  public void deleteUser(int id) {
    String query = "DELETE FROM LabStore.User WHERE id = ?";
    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(query)) {

      preStmt.setInt(1, id);
      preStmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
