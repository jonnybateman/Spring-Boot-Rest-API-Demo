package com.cqueltech.restapi.dto;

/*
 * A data transfer object used to map the login http request body. From it
 * the username and password for a user can be extracted for authentication.
 */

public class JwtLoginRequestDTO {

  /*
   * Declare class fields
   */
  
  private String username;
  private String password;

  /*
   * Declare class constructors
   */

  public JwtLoginRequestDTO() {
    super();
  }

  public JwtLoginRequestDTO(String username, String password) {
    this.username = username;
    this.password = password;
  }

  /*
   * Declare class getter and setter methods
   */

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
