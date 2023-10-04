package com.cqueltech.restapi.dto;

/*
 * A DTO query projection class to store the result set of a JPQL query. The fields
 * contained in this class map to the fields projected by the query.
 */

public class StudentsDTO {

  /*
   * Declare class fields
   */
  
  private int courseId;
  private String title;
  private String firstName;
  private String lastName;
  private String email;
  private int studentId;

  /*
   * Define class constructor
   */

  public StudentsDTO(int courseId,
                     String title,
                     String firstName,
                     String lastName,
                     String email,
                     int studentId) {
    this.courseId = courseId;
    this.title = title;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.studentId = studentId;
  }

  /*
   * Define class getter and setter methods
   */

  public int getCourseId() {
    return courseId;
  }

  public void setCourseId(int courseId) {
    this.courseId = courseId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public int getStudentId() {
    return studentId;
  }

  public void setStudentId(int studentId) {
    this.studentId = studentId;
  }

  
}
