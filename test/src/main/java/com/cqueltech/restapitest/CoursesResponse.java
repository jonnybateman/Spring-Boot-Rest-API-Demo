package com.cqueltech.restapitest;

import java.util.List;

public class CoursesResponse {
  
  private int status;
  private String message;
  private String timestamp;
  private List<Course> array;

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public List<Course> getArray() {
    return array;
  }

  public void setArray(List<Course> array) {
    this.array = array;
  }

  public class Course {

    private int courseId;
    private String title;
    private String firstName;
    private String lastName;
    private int instructorId;

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

}
