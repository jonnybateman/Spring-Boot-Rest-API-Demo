package com.cqueltech.restapi.dto;

/*
 * A DTO query projection class to store the result set of a JPQL query. The fields
 * contained in this class map to the fields projected by the query.
 */

public class ReviewsDTO {

  /*
   * Declare class fields
   */
  
  private int courseId;
  private String title;
  private String comment;

  /*
   * Define class constructor
   */
  
  public ReviewsDTO(int courseId, String title, String comment) {
    this.courseId = courseId;
    this.title = title;
    this.comment = comment;
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

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

}
