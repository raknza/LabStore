package labstore.controller;

import labstore.service.AuthService;
import labstore.utils.ExceptionUtil;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("auth/")
public class AuthController {
  private AuthService authService = AuthService.getInstance();
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

  /**
   * @param token test
   * @return Response
   */
  @POST
  @Path("checkLogin")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response checkLogin(@FormParam("token") String token) {
    Response response = null;
    try {
      response = authService.checkAuth(token);
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }

    return response;
  }
}
