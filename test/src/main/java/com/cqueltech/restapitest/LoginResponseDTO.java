package com.cqueltech.restapitest;

import java.util.ArrayList;
import java.util.List;

public class LoginResponseDTO {
  
  private User user;
  private String jwt;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
  
  public String getJwt() {
    return jwt;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }

  public class User {

    private String username;
    private List<Authorities> authorities;

    public User() {
      this.authorities = new ArrayList<Authorities>();
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public List<Authorities> getAuthorities() {
      return authorities;
    }

    public void setAuthorities(List<Authorities> authorities) {
      this.authorities = authorities;
    }

    public class Authorities {

      private String role;

      public String getRole() {
        return role;
      }

      public void setRole(String role) {
        this.role = role;
      }
    }
  }
}
