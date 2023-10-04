package com.cqueltech.restapi.dto;

/*
 * A Spring model class used to enroll a student on a course. It maps to the
 * student enrollment form of the enroll-student template. A model class permits
 * a bi-directional flow of data between the controller and the template.
 */

import org.hibernate.validator.constraints.Range;
import jakarta.validation.constraints.NotNull;

public class StudentEnrollmentDTO {

  /*
   * Declare class fields
   */

  @NotNull(message = "is required")
  @Range(min = 1, max = 10, message = "is required")
  // Declaring as integer rather than int ensures a default value of '0' is not displayed in form field.
  private Integer courseId;

  @NotNull(message = "is required")
  @Range(min = 1, max = 10, message = "is required")
  private Integer studentId;

  /*
   * Define class constructors
   */

  public StudentEnrollmentDTO() {
  }

  public StudentEnrollmentDTO(int courseId, int studentId) {
    this.courseId = courseId;
    this.studentId = studentId;
  }

  /*
   * Define class getter and setter methods
   */

  public Integer getCourseId() {
    return courseId;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public void setCourseId(Integer courseId) {
    this.courseId = courseId;
  }

  public void setStudentId(Integer studentId) {
    this.studentId = studentId;
  }

}