package com.cqueltech.restapitest;

public class CreateCourseRequestDTO {
  
  private String title;
  private int instructorId;

  public CreateCourseRequestDTO(String title, int instructorId) {
    this.title = title;
    this.instructorId = instructorId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getInstructorId() {
    return instructorId;
  }

  public void setInstructorId(int instructorId) {
    this.instructorId = instructorId;
  }

}
