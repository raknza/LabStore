package labstore.database;

import labstore.data.RoleEnum;
import labstore.utils.ExceptionUtil;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleDbManagerTest {

  RoleDbManager roleDbManager;

  private static final Logger LOGGER = LoggerFactory.getLogger(RoleDbManagerTest.class);

  @Before
  public void init() {
    roleDbManager = RoleDbManager.getInstance();
  }

  @Test
  public void getRoleIdByNameTest() {
    try {
      assertEquals(roleDbManager.getRoleIdByName("BOSS"), 1);
      assertEquals(roleDbManager.getRoleIdByName("CUSTOMER"), 2);
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  public void getRoleNameByIdTest() {
    try {
      assertEquals(roleDbManager.getRoleNameById(1), RoleEnum.BOSS);
      assertEquals(roleDbManager.getRoleNameById(2), RoleEnum.CUSTOMER);
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }
}
