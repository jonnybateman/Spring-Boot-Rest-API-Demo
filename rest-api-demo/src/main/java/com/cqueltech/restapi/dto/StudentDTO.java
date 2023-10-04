package com.cqueltech.restapi.dto;

/*
 * A DTO class (data transfer object) used for transferring data between different
 * layers or components of the application. Also used to transfer data in http
 * request/response between client and server. DTOs encapsulate and organise data,
 * making communication between layers more efficient.
 */

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class StudentDTO {

  /*
   * Declare class fields
   */
  
  @NotNull(message = "is required")
  @Size(min = 1, message = "is required")
  @Pattern(regexp = "[a-zA-Z]*")
  private String firstName;

  @NotNull(message = "is required")
  @Size(min = 1, message = "is required")
  @Pattern(regexp = "[a-zA-Z]*")
  private String lastName;

  // Regex pattern has the following traits:-
  //    - It allows numeric values from 0 to 9.
  //    - Both uppercase and lowercase letters from A to Z are allowed.
  //    - Allowed are underscore, hyphen, and dot.
  //    - Dot isn't allowed at the beginning or end of the local part.
  //    - Consecutive dots are not allowed.
  //    - For the local part a maximum of 64 characters are allowed.
  @NotNull(message = "is required")
  @Size(min = 1, message = "is required")
  @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
  private String email;

  /*
   * Define class constructors
   */

  public StudentDTO() {
  }

  /*
   * Define class getter and setter methods
   */
  
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
}
