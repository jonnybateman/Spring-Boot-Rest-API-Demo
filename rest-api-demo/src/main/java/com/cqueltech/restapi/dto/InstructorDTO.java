package com.cqueltech.restapi.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class InstructorDTO {
  
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

  @NotNull(message = "is required")
  @Range(min = 1, max = 100, message = "is required")
  private Integer age;

  @NotNull(message = "is required")
  @Size(min = 1, max = 1, message = "is required")
  @Pattern(regexp = "^(?:M|F)$")
  private String sex;

  @NotNull(message = "is required")
  @Size(min = 1, max = 255, message = "is required")
  private String address;

  public InstructorDTO() {
  }

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

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

}
