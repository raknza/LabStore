package labstore.controller;

import labstore.data.User;
import labstore.service.PurchaseService;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("purchase/")
public class PurchaseController {
  private PurchaseService purchaseService = new PurchaseService();
  private UserService userService = new UserService();

  private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseController.class);

  @GET
  @Path("all")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllPurchase() {
    Response response;
    try {
      JSONObject jsonObject = purchaseService.getAllPurchase();
      response = Response.ok().entity(jsonObject.toString()).build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }
    return response;
  }

  @GET
  @Path("all/boss")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPurchaseByUser(@QueryParam("username") String username) {
    Response response;
    try {
      User user = userService.getUser(username);
      JSONObject jsonObject = purchaseService.getPurchases(user);
      response = Response.ok().entity(jsonObject.toString()).build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }
    return response;
  }
}
