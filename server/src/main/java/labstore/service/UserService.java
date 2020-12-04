package labstore.service;

import labstore.data.User;
import labstore.database.UserDbManager;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import labstore.utils.ExceptionUtil;
import labstore.database.UserDbService;


@Path("user")
public class UserService {
  private static UserService instance = new UserService();

  public static UserService getInstance() {
    return instance;
  }

  private UserDbManager dbManager = UserDbManager.getInstance();
  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  /**
   * @param name name
   * @param username id
   * @param password password
   * @param role role
   * @return response
   */
  @POST
  @Path("/new")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createAccount(
      @FormParam("name") String name,
      @FormParam("username") String username,
      @FormParam("password") String password,
      @FormParam("role") String role) {

    Response response = null;

    User user = new User(username, name, password, RoleEnum.getRoleEnum(role));
    String errorMessage = getErrorMessage(user);
    if (errorMessage.isEmpty()) {
      try {
        register(user);
        response = Response.ok().build();
      } catch (IOException e) {
        response = Response.serverError().entity("Failed !").build();
        LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
        LOGGER.error(e.getMessage());
      }
    } else {
      response = Response.serverError().entity(errorMessage).build();
    }

    return response;
  }

  /**
   * (to do )
   *
   * @param username (to do )
   * @param currentPassword (to do )
   * @param newPassword (to do )
   * @return response (to do)
   */
  @POST
  @Path("updatePassword")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updatePassword(
      @FormDataParam("username") String username,
      @FormDataParam("currentPassword") String currentPassword,
      @FormDataParam("newPassword") String newPassword) {
    Response response = null;
    boolean isSame = dbManager.checkPassword(username, currentPassword);
    if (isSame) {
      dbManager.modifiedUserPassword(username, newPassword);
      response = Response.ok().build();
    } else {
      response = Response.ok().entity("The current password is wrong").build();
    }

    return response;
  }

  /**
   * Get all user which role is student
   *
   * @return all GitLab users
   */
  @GET
  @Path("/getUsers")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUsers() {
    List<User> users = dbManager.getAllUsers();

    JSONObject ob = new JSONObject();
    ob.put("Users", users);
    return Response.ok().entity(ob.toString()).build();
  }

  /**
   * Get all user which role is customer
   *
   * @return all customers
   */
  public List<User> getCustomers() {
    List<User> customerUsers = new ArrayList<>();
    List<User> users = dbManager.getAllUsers();

    for (User user : users) {
      if (user.getRole().equals(RoleEnum.CUSTOMER)) {
        customerUsers.add(user);
      }
    }
    return customerUsers;
  }

  /**
   * Delete user but gitlab repository not delete so you need manual delete
   *
   * @param username user name
   */
  @DELETE
  @Path("/{username}")
  public Response deleteUser(@PathParam("username") String username) {
    UserDbService userDbService = UserDbService.getInstance();
    int userId = userDbService.getId(username);
    return deleteUser(userId);
  }

  /**
   * Delete user
   *
   * @param userId user id
   */
  public Response deleteUser(int userId) {
    UserDbService userDbService = UserDbService.getInstance();
    //remove db
    userDbService.deleteUser(userId);
    return Response.ok().build();
  }

  /**
   * Register user
   *
   * @param user user of ProgEdu
   */
  private void register(User user) throws IOException {
    dbManager.addUser(user);
  }

  private boolean isPasswordTooShort(String password) {
    boolean isPasswordTooShort = false;
    if (password.length() < 8) {
      isPasswordTooShort = true;
    }
    return isPasswordTooShort;
  }

  private boolean isDuplicateUsername(String username) {
    boolean isDuplicateUsername = false;
    if (dbManager.checkUsername(username)) {
      isDuplicateUsername = true;
    }
    return isDuplicateUsername;
  }

  private boolean isDuplicateUsername(List<User> users, String username) {
    boolean isDuplicateUsername = false;
    for (User user : users) {
      if (username.equals(user.getUsername())) {
        isDuplicateUsername = true;
        break;
      }
    }
    return isDuplicateUsername;
  }



  private String getErrorMessage(List<User> users, User user) {
    String errorMessage = getErrorMessage(user);
    if (errorMessage.isEmpty()) {
      if (isDuplicateUsername(users, user.getUsername())) {
        errorMessage = "username : " + user.getUsername() + " is duplicated in student list.";
      }
    }
    return errorMessage;
  }

  private String getErrorMessage(User user) {
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
