package labstore.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import labstore.config.JwtConfig;
import labstore.database.UserDbManager;
import labstore.exception.LoadConfigFailureException;
import labstore.database.UserRoleDbManager;
import labstore.utils.ExceptionUtil;
import labstore.data.RoleEnum;
import labstore.database.RoleDbManager;

/**
 * Servlet implementation class AfterEnter
 */
public class LoginAuth extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  JwtConfig jwt = JwtConfig.getInstance();
  private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuth.class);

  /**
   * @throws LoadConfigFailureException .
   * @see HttpServlet#HttpServlet()
   */
  public LoginAuth() throws LoadConfigFailureException {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    doPost(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    String username = request.getParameter(USERNAME);
    String password = request.getParameter(PASSWORD);
    String role = "";
    boolean checkPass;
    try {
      if (checkPassword(username, password)) {
        role = getRole(username).toString();
      }
      checkPass = true;
    } catch (SQLException | NoSuchAlgorithmException e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      checkPass = false;
    }
    String token;
    JSONObject ob = new JSONObject();

    try {
      if (checkPass) {
        if (!role.equals("")) {
          ob.put("isLogin", true);
          ob.put("role", role);
          String name = getNameByUsername(username);
          token = jwt.generateToken(role, username, name);
          ob.put("token", token);
        } else {
          ob.put("isLogin", false);
        }
      } else {
        throw new SQLException("Check Password Failed");
      }
    } catch (JSONException | SQLException e) {
      try {
        ob.put("isLogin", false);
        ob.put("password", password);
      } catch (JSONException ex) {
        LOGGER.debug(ExceptionUtil.getErrorInfoFromException(ex));
        LOGGER.error(ex.getMessage());
      }
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }

    response.setStatus(200);
    
    try {
      PrintWriter pw = response.getWriter();
      pw.print(ob);
      pw.flush();
      pw.close();
    } catch (IOException e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
    }
  }

  private boolean checkPassword(String username, String password)
      throws SQLException, NoSuchAlgorithmException {
    return UserDbManager.getInstance().checkPassword(username, password);
  }

  private RoleEnum getRole(String username) throws SQLException {
    UserDbManager userDb = UserDbManager.getInstance();
    UserRoleDbManager roleUserDb = UserRoleDbManager.getInstance();
    int uid = userDb.getUserIdByUsername(username);
    int rid = roleUserDb.getTopRid(uid);
    RoleDbManager roleDb = RoleDbManager.getInstance();
    return roleDb.getRoleNameById(rid);
  }

  private String getNameByUsername(String username) throws SQLException {
    return UserDbManager.getInstance().getUser(username).getName();
  }

}
