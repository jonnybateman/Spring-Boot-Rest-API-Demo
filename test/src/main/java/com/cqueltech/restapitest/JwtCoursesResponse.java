package com.cqueltech.restapitest;

import java.util.List;

public class JwtCoursesResponse {

  private List<Course> courses;

  public List<Course> getCourses() {
    return courses;
  }

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }

  public class Course {

    private String title;
    private Integer instructorId;

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
  
}
