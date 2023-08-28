package com.cqueltech.restapi.dto;

import org.hibernate.validator.constraints.Range;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/*
 * A Spring model class used to create an instance of course, student or instructor.
 * It maps to the form of the create entity template. A model class permits a bi-directional
 * flow of data between the controller and the template.
 */

public class Entity {

  @NotNull(message = "is required")
  @Size(min = 1, message = "is required")
  private String courseTitle;

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
  @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$")
  private String email;

  @NotNull(message = "is required")
  @Range(min = 1, max = 10, message = "is required")
  private Integer instructorId;

  @NotNull(message = "is required")
  @Range(min = 1, max = 3, message = "is required")
  private Integer age;

  @NotNull(message = "is required")
  //@Pattern(regexp = "[MF]")
  private char sex;

  @NotNull(message = "is required")
  @Size(min = 1, max = 255, message = "is required")
  private String address;

  private String entityType;

  // Define constructors.
  public Entity() {
  }

  // Define the getters and setters.
  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTital) {
    this.courseTitle = courseTital;
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

  public Integer getInstructorId() {
    return instructorId;
  }

  public void setInstructorId(Integer instructorId) {
    this.instructorId = instructorId;
  }

  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public char getSex() {
    return sex;
  }

  public void setSex(char sex) {
    this.sex = sex;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
  
}
