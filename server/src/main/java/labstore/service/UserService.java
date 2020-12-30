package labstore.service;

import labstore.data.RoleEnum;
import labstore.data.User;
import labstore.database.UserDbManager;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {

  private UserDbManager userDbManager = UserDbManager.getInstance();
  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  public int getId(String username) throws SQLException {
    return userDbManager.getUserIdByUsername(username);
  }

  public String getName(int userId) throws SQLException {
    return userDbManager.getUsername(userId);
  }

  public void deleteUser(int userId) throws SQLException {
    userDbManager.deleteUser(userId);
  }

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

  public void updatePassword(String username, String currentPassword, String newPassword)
      throws Exception {
    boolean isSame = userDbManager.checkPassword(username, currentPassword);
    if (isSame) {
      userDbManager.modifiedUserPassword(username, newPassword);
    } else {
      throw new Exception("The current password is wrong");
    }
  }

  public JSONObject getUsers() throws SQLException {
    List<User> users = userDbManager.getAllUsers();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Users", users);
    return jsonObject;
  }

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

  private boolean isPasswordTooShort(String password) {
    boolean isPasswordTooShort = false;
    if (password.length() < 8) {
      isPasswordTooShort = true;
    }
    return isPasswordTooShort;
  }

  private boolean isDuplicateUsername(String username) throws SQLException {
    boolean isDuplicateUsername = false;
    if (userDbManager.checkUsername(username)) {
      isDuplicateUsername = true;
    }
    return isDuplicateUsername;
  }

  private String getErrorMessage(User user) throws SQLException{
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
