package labstore.controller;

import labstore.service.PurchaseService;
import labstore.service.ShelfProductService;

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

import labstore.utils.ExceptionUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("shelfProduct/")
public class ShelfProductController {
  private ShelfProductService shelfProductService = new ShelfProductService();
  private PurchaseService purchaseService = new PurchaseService();

  private static final Logger LOGGER = LoggerFactory.getLogger(ShelfProductController.class);

  @POST
  @Path("new")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createShelfProduct(
      @FormParam("purchaseId") int pdId,
      @FormParam("price") int price,
      @FormParam("count") int count
  ) {
    Response response;
    try {

      response = Response.accepted().build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }
    return response;
  }
}
