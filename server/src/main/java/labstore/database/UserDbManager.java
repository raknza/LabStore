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

import labstore.data.User;
import labstore.data.RoleEnum;

public class UserDbManager {

  private static UserDbManager dbManager = new UserDbManager();

  public static UserDbManager getInstance() {
    return dbManager;
  }

  private IDatabase database = new MySqlDatabase();

  private UserRoleDbManager userRoleDbManager = UserRoleDbManager.getInstance();
  private RoleDbManager roleDbManager = RoleDbManager.getInstance();

  private static final String USERNAME = "username";
  private static final String NAME = "name";
  private static final String PASSWORD = "password";
  private static final String ROLE = "role";

  private UserDbManager() {

  }

  /**
   * Add user to database
   * 
   * @param user The user
   */
  public void addUser(User user) throws NoSuchAlgorithmException, SQLException {
    String sql = "INSERT INTO " + "User" + "(" + USERNAME + "," + PASSWORD + ","
        + NAME + ")" + "VALUES(?, ?, ?)" ;

    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(sql)) {
      preStmt.setString(1, user.getUsername());
      preStmt.setString(2, passwordMD5(user.getPassword()));
      preStmt.setString(3, user.getName());
      preStmt.executeUpdate();
    }
  }

  /**
   * encrypt the user password
   * 
   * @param password The user's password
   * @return MD5 string
   * @throws NoSuchAlgorithmException on security api call error
   */
  public String passwordMD5(String password) throws NoSuchAlgorithmException {
    String hashText = "";
    String msg = password;
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] messageDigest = md.digest(msg.getBytes());
    BigInteger number = new BigInteger(1, messageDigest);
    hashText = number.toString(16);
    StringBuilder bld = new StringBuilder();
    for (int count = hashText.length(); count < 32; count++) {
      bld.append("0");
    }
    hashText = bld.toString() + hashText;

    return hashText;
  }

  /**
   * get user password
   * 
   * @param username user stu id
   * @return password
   */
  public String getPassword(String username) throws SQLException {
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
    }
    return token;
  }

  /**
   * update user db password
   * 
   * @param username user stu id
   * @param password user new password
   */
  public void modifiedUserPassword(String username, String password)
      throws NoSuchAlgorithmException, SQLException {
    String query = "UPDATE User SET password=? WHERE username = ?";

    try (Connection conn = database.getConnection();
        PreparedStatement preStmt = conn.prepareStatement(query)) {
      String newPass = passwordMD5(password);
      preStmt.setString(1, newPass);
      preStmt.setString(2, username);
      preStmt.executeUpdate();
    }
  }

  /**
   * check old password
   * 
   * @param username user stu id
   * @param password user old password
   * @return T or F
   */
  public boolean checkPassword(String username, String password)
      throws NoSuchAlgorithmException, SQLException {
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
  public int getUserIdByUsername(String username) throws SQLException {
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
    }
    return id;
  }

  /**
   * Get user from database
   * 
   * @param username The gitlab user name
   * @return user
   */
  public User getUser(String username) throws SQLException {
    return getUser( getUserIdByUsername(username) );
  }

  /**
   * Get user from database
   * 
   * @param id user id
   * @return user
   */
  public User getUser(int id) throws SQLException {
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
          RoleEnum roleEnum = roleDbManager.getRoleNameById(userRoleDbManager.getTopRid(id));
          user.setId(id);
          user.setUsername(username);
          user.setName(name);
          user.setPassword(password);
          user.setRole(roleEnum);
        }
      }
    }
    return user;
  }


  /**
   * List all the database user
   * 
   * @return list of user
   */
  public List<User> getAllUsers() throws SQLException {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM User";

    try (Connection conn = database.getConnection(); Statement stmt = conn.createStatement()) {
      try (ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
          int id = rs.getInt("id");
          String username = rs.getString(USERNAME);
          String name = rs.getString(NAME);
          String password = rs.getString(PASSWORD);
          RoleEnum roleEnum = roleDbManager.getRoleNameById(userRoleDbManager.getTopRid(id));

          User user = new User();
          user.setId(id);
          user.setUsername(username);
          user.setName(name);
          user.setPassword(password);
          user.setRole(roleEnum);
          users.add(user);
        }
      }

    }
    return users;
  }

  /**
   * check username
   * 
   * @param username studentId
   * @return isExist
   */
  public boolean checkUsername(String username) throws SQLException {
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
    }
    return isExist;
  }

  /**
   * Get user from database
   *
   * @param id The db user id
   * @return user
   */
  public String getUsername(int id) throws SQLException {
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
    }
    return name;
  }

  /**
   * delete User from id
   *
   * @param id The user id
   *
   */
  public void deleteUser(int id) throws SQLException {
    String query = "DELETE FROM LabStore.User WHERE id = ?";
    try (Connection conn = database.getConnection();
         PreparedStatement preStmt = conn.prepareStatement(query)) {
      preStmt.setInt(1, id);
      preStmt.executeUpdate();
    }
  }

}
