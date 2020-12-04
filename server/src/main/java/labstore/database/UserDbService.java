package labstore.database;


import labstore.data.User;
import labstore.database.UserDbManager;

import java.util.ArrayList;
import java.util.List;


public class UserDbService {
  private static UserDbService instance = new UserDbService();

  public static UserDbService getInstance() {
    return instance;
  }

  private UserDbManager udb = UserDbManager.getInstance();

  public int getId(String username) {
    return udb.getUserIdByUsername(username);
  }

  public String getName(int userId) {
    return udb.getUsername(userId);
  }

  /**
   * Delete user
   *
   * @param userId user id
   */
  public void deleteUser(int userId) {
    udb.deleteUser(userId);
  }

}
