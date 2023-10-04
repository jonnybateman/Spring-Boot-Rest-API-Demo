package com.cqueltech.restapi.entity;

/*
 * Entity class to define the fields and joins of the Course table.
 */

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

  /*
   * Define entity fields
   */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private int id;

  @Column(name="title")
  private String title;

  // Create join to Instructor entity.
  @ManyToOne(fetch = FetchType.EAGER,
             cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
  @JoinColumn(name="instructor_id") // By default will join using the primary key of the instructor table.
                                    // Use referencedColumnName to explicitly specify the join column.
  private Instructor instructor;

  // Create join to CourseReview entity.
  // Set 'fetch' to FetchType.LAZY to only load associated instructors when required (e.g. by using Course
  // getInstructor() method)
  @OneToMany(fetch = FetchType.LAZY,
             cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
  @JoinColumn(name = "course_id", referencedColumnName = "id")
  private Set<Review> review;

  // Create join to Student entity.
  // Every Many-to-Many has two sides, the owning side and the non-owning (reverse side). In our use
  // the Course entity is the owner of the relationship and Student entity is the inverse side. Join
  // table is specified on the owning side.
  // In a many-to-many relationship, both entities are independent of each other. We have two entities,
  // Course and Student. When removing the record from the Course entity, we usually don't want to remove
  // associated Student entities. With CascadeType.REMOVE, JPA will remove all associated entities,
  // even those that might still be connected to other entities.
  @ManyToMany(fetch = FetchType.LAZY,
              cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
  @JoinTable(name = "course_student",
             joinColumns = {@JoinColumn(name = "course_id")},
             inverseJoinColumns = {@JoinColumn(name = "student_id")})
  // Use Set<> instead of List<> as Hibernate does not remove entities from a List in an efficient way.
  private Set<Student> students = new HashSet<>();

  /*
   * Define the constructors
   */

  public Course( ) {
  }

  public Course(String courseTitle, Instructor instructor) {
    this.title = courseTitle;
    this.instructor = instructor;
  }

  /*
   * Define getter and setter methods
   */

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

  public Set<Review> getReview() {
    return review;
  }

  public void setReview(Set<Review> reviews) {
    this.review = reviews;
  }

  public Set<Student> getStudents() {
    return students;
  }

  public void setStudents(Set<Student> students) {
    this.students = students;
  }

  public void addStudent(Student student) {
    students.add(student);
  }

  @Override
  public String toString() {
    return "Course [id=" + id + ", title=" + title + ", instructor=" + instructor + ", review=" + review + ", students="
        + students + "]";
  }

}
