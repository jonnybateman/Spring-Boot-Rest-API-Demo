package com.cqueltech.restapi.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="course")
public class Course {

  // Define entity fields
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private int id;

  @Column(name="title")
  private String title;

  // Create join to Instructor entity. Set 'fetch' to FetchType.LAZY to only load associated instructors
  // when required (e.g. by using Course getInstructor() method)
  @ManyToOne(fetch = FetchType.LAZY,
             cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
  @JoinColumn(name="instructor_id") // By default will join using the primary key of the instructor table.
                                    // Use referencedColumnName to explicitly specify the join column.
  private Instructor instructor;

  // Create join to CourseReview entity.
  @OneToMany(fetch = FetchType.LAZY,
             cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
  @JoinColumn(name = "course_id", referencedColumnName = "id")
  private List<Review> review;

  // Create join to Student entity.
  // Every Many-to-Many has two sides, the owning side and the non-owning (reverse side). In our use
  // the Course entity is the owner of the relationship and Student entity is the inverse side. Join
  // table is specified on the owning side.
  @ManyToMany(fetch = FetchType.LAZY,
              cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
  @JoinTable(name = "course_student",
             joinColumns = {@JoinColumn(name = "course_id")},
             inverseJoinColumns = {@JoinColumn(name = "student_id")})
  private List<Student> students;

  // Define the constructors
  public Course( ) {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Instructor getInstructor() {
    return instructor;
  }

  public void setInstructor(Instructor instructor) {
    this.instructor = instructor;
  }

  public List<Review> getReview() {
    return review;
  }

  public void setReview(List<Review> reviews) {
    this.review = reviews;
  }

  public List<Student> getStudents() {
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

}
