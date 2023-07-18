package com.cqueltech.restapi.user;

/*
 * The html register user form will make use of this class to store the entries from
 * the add new user form.
 */

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class NewUser {
  
  @NotNull(message = "is required")
  @Size(min = 1, message = "is required")
  @Pattern(regexp = "[a-zA-Z0-9]*")
  private String username;

  @NotNull(message = "is required")
  @Size(min = 1, message = "is required")
  @Pattern(regexp = "[a-zA-Z0-9@!\\-_*&%$\\/]*")
  private String password1;

  @NotNull(message = "is required")
  @Size(min = 1, message = "is required")
  @Pattern(regexp = "[a-zA-Z0-9@!\\-_*&%$\\/]*")
  private String password2;

  private int roleStudent;

  private int roleInstructor;

  public NewUser() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword1() {
    return password1;
  }

  public void setPassword1(String password1) {
    this.password1 = password1;
  }

  public String getPassword2() {
    return password2;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
  }

  public int getRoleStudent() {
    return roleStudent;
  }

  public void setRoleStudent(int roleStudent) {
    this.roleStudent = roleStudent;
  }

  public int getRoleInstructor() {
    return roleInstructor;
  }

  public void setRoleInstructor(int roleInstructor) {
    this.roleInstructor = roleInstructor;
  }

}
