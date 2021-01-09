package labstore.controller;

import labstore.service.CategoryService;
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

@Path("category/")
public class CategoryController {
  private CategoryService categoryService = new CategoryService();

  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

  /**
   * Add new category.
   *
   * @param name name
   * @return Response
   */
  @POST
  @Path("new")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addCategory(@QueryParam("name") String name) {
    Response response;
    try {
      categoryService.addCategory(name);
      response = Response.ok().build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }
    return response;
  }

  /**
   * Get all Category.
   *
   * @return Response
   */
  @GET
  @Path("all")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllCategory() {
    Response response;
    try {
      JSONObject jsonObject = categoryService.getAllCategory();
      response = Response.ok().entity(jsonObject.toString()).build();
    } catch (Exception e) {
      LOGGER.debug(ExceptionUtil.getErrorInfoFromException(e));
      LOGGER.error(e.getMessage());
      response = Response.serverError().entity(e.getMessage()).build();
    }
    return response;
  }

}
