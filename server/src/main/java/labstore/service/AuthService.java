package labstore.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import labstore.config.JwtConfig;
import io.jsonwebtoken.Claims;

@Path("auth/")
public class AuthService {
  JwtConfig jwt = JwtConfig.getInstance();

  /**
   * @param token test
   * @return Response
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response checkAuth(@FormParam("token") String token) {
    JSONObject ob = new JSONObject();
    if (!token.equals("null") && jwt.validateToken(token)) {
      Claims body = jwt.decodeToken(token);
      System.out.print("body" + body);
      RoleEnum roleEnum = RoleEnum.getRoleEnum((String) body.get("sub"));
      
      switch (roleEnum) {
        case BOSS: {
          ob.put("isLogin", true);
          ob.put("isBoss", true);
          break;
        }
        case CUSTOMER: {
          ob.put("isLogin", true);
          ob.put("isCustomer", true);
          break;
        }
        default: {
          ob.put("isLogin", false);
        }
      }

    } else {
      ob.put("isLogin", false);
    }
    return Response.ok().entity(ob.toString()).build();
  }
}
