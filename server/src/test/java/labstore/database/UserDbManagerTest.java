package labstore.database;

import labstore.data.RoleEnum;
import labstore.data.User;

import labstore.utils.ExceptionUtil;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDbManagerTest {

  UserDbManager userDbManager;
  User user;

  private static final Logger LOGGER = LoggerFactory.getLogger(UserDbManagerTest.class);

  @Before
  public void init() {
    userDbManager = UserDbManager.getInstance();
    user = new User("unit_test_username",
        "unit_test_name", "unit_test_password",RoleEnum.BOSS);
  }

  @Test
  public void addUserTest() {
    try {
      userDbManager.addUser(user);
      User userFromDb = userDbManager.getUser("unit_test_username");
      assertEquals(user.getUsername(), userFromDb.getUsername());
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  public void checkPasswordTest() {
    try {
      boolean check = userDbManager.checkPassword(user.getUsername(), user.getPassword());
      assertEquals(true, check);
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  public void modifiedUserPasswordTest() {
    try {
      user.setPassword("unit_test_password2");
      userDbManager.modifiedUserPassword(user.getUsername(), user.getPassword());
      boolean check = userDbManager.checkPassword(user.getUsername(), user.getPassword());
      assertEquals(true, check);
      // Roll back to the init data.
      userDbManager.modifiedUserPassword(user.getUsername(), "unit_test_password");
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  public void getUserTest() {
    try {
      User userFromDb = userDbManager.getUser(user.getUsername());
      assertEquals(user.getUsername(), userFromDb.getUsername());
      assertEquals(userDbManager.passwordMD5(user.getPassword()), userFromDb.getPassword());
      assertEquals(user.getName(), userFromDb.getName());
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  public void checkUsernameTest() {
    try {
      boolean check = userDbManager.checkUsername(user.getUsername());
      boolean checkFalse = userDbManager.checkUsername("unit_test_username2");
      assertEquals(true, check);
      assertEquals(false, checkFalse);
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  public void getUsernameTest() {
    try {
      int id = userDbManager.getUserIdByUsername(user.getUsername());
      assertEquals(user.getUsername(), userDbManager.getUsername(id));
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  public void deleteUserTest() {
    try {
      userDbManager.deleteUser(userDbManager.getUserIdByUsername(user.getUsername()));
      boolean check = userDbManager.checkUsername(user.getUsername());
      assertEquals(false, check);
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }

}
