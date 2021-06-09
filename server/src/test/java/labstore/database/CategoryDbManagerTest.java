package labstore.database;

import labstore.data.Category;

import labstore.utils.ExceptionUtil;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryDbManagerTest {

  CategoryDbManager categoryDbManager;
  Category category;

  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDbManagerTest.class);

  @Before
  public void init() {
    categoryDbManager = CategoryDbManager.getInstance();
    category = new Category();
    category.setName("unit_test_name");
  }

  @Test
  public void addCategoryTest() {
    try {
      categoryDbManager.addCategory(category.getName());
      Category categoryFromDb = categoryDbManager.getCategoryByName(category.getName());
      assertEquals(category.getName(), categoryFromDb.getName());
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  public void deleteCategoryTest() {
    try {
      category.setId(categoryDbManager.getCategoryByName(category.getName()).getId());
      categoryDbManager.deleteCategory(category.getId());
      Category categoryFromDb = categoryDbManager.getCategoryById(category.getId());
      assertEquals("", categoryFromDb.getName());
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }

}
