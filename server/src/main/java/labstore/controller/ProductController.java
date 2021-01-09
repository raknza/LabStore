package labstore.controller;

import labstore.data.Category;
import labstore.data.User;
import labstore.service.CategoryService;
import labstore.service.ProductService;
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

@Path("product/")
public class ProductController {
  private ProductService productService = new ProductService();
  private CategoryService categoryService = new CategoryService();

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

  @GET
  @Path("all")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllProduct() {
    Response response;
    try {
      JSONObject jsonObject = productService.getAllProducts();
      response = Response.ok().entity(jsonObject.toString()).build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }
    return response;
  }

  @GET
  @Path("all/category")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getProductByCategory(@QueryParam("category") int categoryId) {
    Response response;
    try {
      Category category = categoryService.getCategory(categoryId);
      JSONObject jsonObject = productService.getAllProducts(category);
      response = Response.ok().entity(jsonObject.toString()).build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }
    return response;
  }

  @POST
  @Path("new")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addProduct(
      @QueryParam("category") int categoryId,
      @QueryParam("name") String name,
      @QueryParam("description") String description) {
    Response response;
    try {
      productService.addProduct(categoryId, name, description);
      response = Response.ok().build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }
    return response;
  }
}
