package labstore.database;

import labstore.data.RoleEnum;
import labstore.data.User;
import labstore.utils.ExceptionUtil;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRoleDbManagerTest {

  UserDbManager userDbManager;
  RoleDbManager roleDbManager;
  UserRoleDbManager userRoleDbManager;
  User user;

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleDbManagerTest.class);

  @Before
  public void init() {
    userDbManager = UserDbManager.getInstance();
    roleDbManager = RoleDbManager.getInstance();
    userRoleDbManager = UserRoleDbManager.getInstance();
    user = new User("unit_test_username",
        "unit_test_name", "unit_test_password", RoleEnum.BOSS);
  }

  @Test
  public void addRoleUserTest() {
    try {
      userDbManager.addUser(user);
      user.setId(userDbManager.getUserIdByUsername(user.getUsername()));
      userRoleDbManager.addRoleUser(
          roleDbManager.getRoleIdByName(user.getRole().getTypeName()), user.getId());
      int rId = userRoleDbManager.getTopRid(user.getId());
      assertEquals(user.getRole(), roleDbManager.getRoleNameById(rId));
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  public void deleteRoleUserByUserIdTest() {
    try {
      user.setId(userDbManager.getUserIdByUsername(user.getUsername()));
      userRoleDbManager.deleteRoleUserByUserId(user.getId());
      int rId = userRoleDbManager.getTopRid(user.getId());
      assertEquals(null, roleDbManager.getRoleNameById(rId));

      // roll back testing user
      userDbManager.deleteUser(user.getId());
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }
}
