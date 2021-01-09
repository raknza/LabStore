package labstore.service;

import labstore.data.RoleEnum;
import labstore.data.User;
import labstore.database.UserDbManager;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

  private UserDbManager userDbManager = UserDbManager.getInstance();

  /**
   * Get user id by username.
   *
   * @param username username
   * @return id
   * @throws SQLException Exception
   */
  public int getId(String username) throws SQLException {
    return userDbManager.getUserIdByUsername(username);
  }

  /**
   * Get user username by id.
   *
   * @param userId id
   * @return username
   * @throws SQLException Exception
   */
  public String getName(int userId) throws SQLException {
    return userDbManager.getUsername(userId);
  }

  /**
   * Get user by username.
   *
   * @param username username
   * @return User
   * @throws SQLException Exception
   */
  public User getUser(String username) throws SQLException {
    return userDbManager.getUser(username);
  }

  /**
   * Get user by id.
   *
   * @param id id
   * @return User
   * @throws SQLException Exception
   */
  public User getUser(int id) throws  SQLException {
    return userDbManager.getUser(id);
  }

  /**
   * Delete user by id.
   *
   * @param userId id
   * @throws SQLException Exception
   */
  public void deleteUser(int userId) throws SQLException {
    userDbManager.deleteUser(userId);
  }

  /**
   * Create new user.
   *
   * @param name name
   * @param username username
   * @param password password
   * @param role role enum
   * @throws Exception Exception
   */
  public void createAccount(String name, String username, String password, String role)
      throws Exception {

    User user = new User(username, name, password, RoleEnum.getRoleEnum(role));
    String errorMessage = getErrorMessage(user);
    if (errorMessage.isEmpty()) {
      userDbManager.addUser(user);
    } else {
      throw new Exception(errorMessage);
    }
  }

  /**
   * Update info for user.
   *
   * @param username username
   * @param currentPassword old password
   * @param newPassword new password
   * @throws Exception Exception
   */
  public void updatePassword(String username, String currentPassword, String newPassword)
      throws Exception {
    boolean isSame = userDbManager.checkPassword(username, currentPassword);
    if (isSame) {
      userDbManager.modifiedUserPassword(username, newPassword);
    } else {
      throw new Exception("The current password is wrong");
    }
  }

  /**
   * Get all users.
   *
   * @return JSONObject
   * @throws SQLException Exception
   */
  public JSONObject getUsers() throws SQLException {
    List<User> users = userDbManager.getAllUsers();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Users", users);
    return jsonObject;
  }

  /**
   * Get the users who is customer.
   *
   * @return JSONObject
   * @throws SQLException Exception
   */
  public JSONObject getCustomers() throws SQLException {
    List<User> customerUsers = new ArrayList<>();
    List<User> users = userDbManager.getAllUsers();
    JSONObject jsonObject = new JSONObject();

    for (User user : users) {
      if (user.getRole().equals(RoleEnum.CUSTOMER)) {
        customerUsers.add(user);
      }
    }

    jsonObject.put("Customers", customerUsers);
    return jsonObject;
  }

  /**
   * Check the password is it too short or not.
   *
   * @param password password
   * @return boolean
   */
  private boolean isPasswordTooShort(String password) {
    boolean isPasswordTooShort = false;
    if (password.length() < 8) {
      isPasswordTooShort = true;
    }
    return isPasswordTooShort;
  }

  /**
   * Check if the username has been repeated.
   *
   * @param username username
   * @return boolean
   * @throws SQLException Exception
   */
  private boolean isDuplicateUsername(String username) throws SQLException {
    boolean isDuplicateUsername = false;
    if (userDbManager.checkUsername(username)) {
      isDuplicateUsername = true;
    }
    return isDuplicateUsername;
  }

  /**
   * Check all the rules and return the error message.
   *
   * @param user User
   * @return String
   * @throws SQLException Exception
   */
  private String getErrorMessage(User user) throws SQLException {
    String errorMessage = "";
    if (isPasswordTooShort(user.getPassword())) {
      errorMessage = user.getName() + " : Password must be at least 8 characters.";
    } else if (isDuplicateUsername(user.getUsername())) {
      errorMessage = "username : " + user.getUsername() + " already exists.";
    } else if (user.getRole() == null) {
      errorMessage = "Role is empty";
    }
    return errorMessage;
  }

}
