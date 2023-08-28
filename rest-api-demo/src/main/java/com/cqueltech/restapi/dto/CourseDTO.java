package com.cqueltech.restapi.dto;

/*
 * A DTO class (data transfer object) used for transferring data between different
 * layers or components of the application. DTOs encapsulate and organise data,
 * making communication between layers more efficient.
 */

import org.hibernate.validator.constraints.Range;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CourseDTO extends EntityDTO{

  @NotNull(message = "is required")
  @Size(min = 1, message = "is required")
  private String title;

  @NotNull(message = "is required")
  @Range(min = 1, max = 10, message = "is required")
  private Integer instructorId;
  
  // Declare constructor
  public CourseDTO() {
  }

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
