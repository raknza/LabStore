package labstore.service;

import javax.ws.rs.core.Response;

import labstore.data.RoleEnum;
import labstore.config.JwtConfig;

import org.json.JSONObject;

import io.jsonwebtoken.Claims;

public class AuthService {
  private static AuthService instance = new AuthService();

  public static AuthService getInstance() {
    return instance;
  }

  private JwtConfig jwt = JwtConfig.getInstance();

  /**
   * @param token test
   * @return Response
   */
  public Response checkAuth(String token) {
    JSONObject ob = new JSONObject();
    if (!token.equals("null") && jwt.validateToken(token)) {
      Claims body = jwt.decodeToken(token);
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
