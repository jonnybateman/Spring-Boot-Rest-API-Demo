package com.cqueltech.restapi.dto;

/*
 * A Data Transfer Object used as the response for the JWT Login request. Includes the
 * JWT token for the user that the client can use to access the web service APIs.
 */

import com.cqueltech.restapi.entity.User;

public class JwtLoginResponseDTO {

  /*
   * Declare class fields
   */

  private User user;
  private String jwt;

  /*
   * Declare class constructors
   */

  public JwtLoginResponseDTO() {
    super();
  }

  public JwtLoginResponseDTO(User user, String jwt) {
    this.user = user;
    this.jwt = jwt;
  }

  /*
   * Declare class getter and setter methods
   */

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
  
}

