package labstore.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import labstore.data.Category;
import labstore.database.CategoryDbManager;

import org.json.JSONObject;

public class CategoryService {

  private CategoryDbManager categoryDbManager = CategoryDbManager.getInstance();

  /**
   * Add new category.
   *
   * @param name name
   * @throws SQLException Exception
   */
  public void addCategory(String name) throws SQLException {
    categoryDbManager.addCategory(name);
  }

  /**
   * Get category by id.
   *
   * @param id id
   * @return Category
   * @throws SQLException Exception
   */
  public Category getCategory(int id) throws SQLException {
    return categoryDbManager.getCategoryById(id);
  }

  /**
   * Get all category.
   *
   * @return JSONObject
   * @throws SQLException Exception
   */
  public JSONObject getAllCategory() throws SQLException {
    List<Category> categories = categoryDbManager.getAllCategory();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Categories", categories);

    return jsonObject;
  }
}
