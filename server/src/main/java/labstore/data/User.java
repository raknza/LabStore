package labstore.data;

import java.io.Serializable;

import labstore.service.RoleEnum;

@SuppressWarnings("serial")
public class User implements Serializable {

  private int id; // ??��?��?�蕭

  private String username; // ??�緯??��?��?��?�蕭(??�稷??��?�蕭)
                           // ??��?��?�蕭

  private String name; // ??��?�蕭??��?�蕭??��?�蕭??��(??��?�蕭??��?�蕭)

  private String password; // ??��?��?�蕭

  private RoleEnum role;

  public User() {
  }

  /**
   * (to do)
   * 
   * @param username    (to do)
   * @param name        (to do)
   * @param password    (to do)
   */
  public User(String username, String name, String password, RoleEnum role) {
    this.username = username;
    this.name = name;
    this.password = password;
    this.role = role;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  /**
   * to do
   * 
   * @param password password
   */
  public void setPassword(String password) {
    if (password == null || password.isEmpty()) {
      this.password = this.username;
    } else {
      this.password = password;
    }
  }

  public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }

}
