package com.cqueltech.restapi.dto;

/*
 * A DTO query projection class to store the result set of a JPQL query. The fields
 * contained in this class map to the fields projected by the query.
 */

public class CoursesDTO {

  /*
   * Declare class fields
   */
  
  private int courseId;
  private String title;
  private String firstName;
  private String lastName;
  private int instructorId;

  /*
   * Define class constructor
   */
  
  public CoursesDTO(int courseId,
                           String title,
                           String firstName,
                           String lastName,
                           int instructorId) {
    this.courseId = courseId;
    this.title = title;
    this.firstName = firstName;
    this.lastName = lastName;
    this.instructorId = instructorId;
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

  public int getInstructorId() {
    return instructorId;
  }

  public void setInstructorId(int instructorId) {
    this.instructorId = instructorId;
  }

}
