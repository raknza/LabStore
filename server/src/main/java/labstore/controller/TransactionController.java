package labstore.controller;

import labstore.data.PurchaseDetail;
import labstore.data.User;
import labstore.service.PurchaseService;
import labstore.service.TransactionService;
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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("transaction/")
public class TransactionController {

  private TransactionService transactionService = new TransactionService();
  private UserService userService = new UserService();
  private PurchaseService purchaseService = new PurchaseService();

  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

  /**
   * Add new transaction.
   *
   * @param username user name
   * @param purchaseId purchase
   * @param count count
   * @return Response
   */
  @POST
  @Path("update")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addTransaction(
      @QueryParam("username") String username,
      @QueryParam("purchase") int purchaseId,
      @QueryParam("count") int count) {
    Response response;
    try {
      User user = userService.getUser(username);
      PurchaseDetail purchaseDetail = purchaseService.getPurchase(purchaseId);
      transactionService.updateTransaction(user, purchaseDetail, count);
      response = Response.ok().build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }
    return response;
  }

  /**
   * Get all transaction by specific customer.
   *
   * @param username user name
   * @return Response
   */
  @GET
  @Path("get/all/cus")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTransactionByCus(@QueryParam("username") String username) {
    Response response;
    try {
      User user = userService.getUser(username);
      JSONObject jsonObject = transactionService.getTransactionByCus(user);
      response = Response.ok().entity(jsonObject.toString()).build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }
    return response;
  }

  /**
   * Get all transaction by specific boss.
   *
   * @param username user name
   * @return Response
   */
  @GET
  @Path("get/all/boss")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTransactionByBoss(@QueryParam("username") String username) {
    Response response;
    try {
      User user = userService.getUser(username);
      JSONObject jsonObject = transactionService.getTransactionByBoss(user);
      response = Response.ok().entity(jsonObject.toString()).build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }
    return response;
  }
}
