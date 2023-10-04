package com.cqueltech.restapi.dto;

/*
 * A DTO class (data transfer object) used for transferring data between different
 * layers or components of the application. Also used to transfer data in http
 * request/response between client and server. DTOs encapsulate and organise data,
 * making communication between layers more efficient.
 */

import org.hibernate.validator.constraints.Range;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CourseDTO {

  /*
   * Declare class fields
   */

  @NotNull(message = "is required")
  @Size(min = 1, message = "is required")
  private String title;

  @NotNull(message = "is required")
  @Range(min = 1, max = 10, message = "is required")
  private Integer instructorId;
  
  /*
   * Declare constructors
   */

  public CourseDTO() {
  }

  public CourseDTO(String title, Integer instructorId) {
    this.title = title;
    this.instructorId = instructorId;
  }

  /*
   * Declare getter and setter methods
   */

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getInstructorId() {
    return instructorId;
  }

  public void setInstructorId(Integer instructorId) {
    this.instructorId = instructorId;
  }

}
