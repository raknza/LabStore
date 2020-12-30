package labstore.controller;

import labstore.service.UserService;
import labstore.utils.ExceptionUtil;

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

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("user/")
public class UserController {
  private UserService userService = new UserService();

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  /**
   * Create new user in date base.
   *
   * @param name name
   * @param username id
   * @param password password
   * @param role role
   * @return response
   */
  @POST
  @Path("new")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createAccount(
      @FormParam("name") String name,
      @FormParam("username") String username,
      @FormParam("password") String password,
      @FormParam("role") String role) {
    Response response;
    try {
      userService.createAccount(name, username, password, role);
      response = Response.accepted().build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }
    return response;
  }

  /**
   * Update the password for specify user.
   *
   * @param username id
   * @param currentPassword current password
   * @param newPassword new password
   * @return response
   */
  @POST
  @Path("updatePassword")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updatePassword(
      @FormDataParam("username") String username,
      @FormDataParam("currentPassword") String currentPassword,
      @FormDataParam("newPassword") String newPassword) {
    Response response;
    try {
      userService.updatePassword(username, currentPassword, newPassword);
      response = Response.accepted().build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }

    return response;
  }

  /**
   * Get all user which role is student
   *
   * @return all GitLab users
   */
  @GET
  @Path("getUsers")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUsers() {
    Response response;
    try {
      JSONObject jsonObject = userService.getUsers();
      response = Response.ok().entity(jsonObject.toString()).build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }

    return response;
  }

  /**
   * Delete user but gitlab repository not delete so you need manual delete
   *
   * @param username user name
   */
  @DELETE
  @Path("{username}")
  public Response deleteUser(@PathParam("username") String username) {
    Response response;
    try {
      int userId = userService.getId(username);
      userService.deleteUser(userId);
      response = Response.accepted().build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }

    return response;
  }
}
