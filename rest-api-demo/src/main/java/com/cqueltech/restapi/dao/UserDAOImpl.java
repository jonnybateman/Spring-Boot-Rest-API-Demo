package com.cqueltech.restapi.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Instructor;
import com.cqueltech.restapi.entity.Role;
import com.cqueltech.restapi.entity.Student;
import com.cqueltech.restapi.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class UserDAOImpl implements UserDAO {

  // Define field for Entity Manager. EntityManager is an API that manages the
  // lifecycle of entity instances.
  private EntityManager entityManager;

  // Inject EntityManager using constructor injection.
  @Autowired
  public UserDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }
  
  @Override
  public User findUserByUsername(String username) {
    
    return entityManager.find(User.class, username);
  }

  @Override
  public void save(User user) {
    
    // Store the User object in the database.
    entityManager.persist(user);
  }

  @Override
  public void save(Role role) {
    
    // Store the Role object in the database.
    entityManager.persist(role);
  }

  @Override
  public List<Course> findAllCourses() {
    
    // Create a query.
    TypedQuery<Course> query = entityManager.createQuery("select c from Course c", Course.class);

    // Execute query and get the result list.
    List<Course> courses = query.getResultList();

    // Return the results
    return courses;
  }

  @Override
  public Instructor findInstructorById(Integer instructorId) {

    // Return instructor according to instructor id. By default the find method will search by primary key.
    return entityManager.find(Instructor.class, instructorId);
  }

  @Override
  public List<Instructor> findAllInstructors() {
    
    // Create a query.
    TypedQuery<Instructor> query = entityManager.createQuery("select i from Instructor i", Instructor.class);

    // Execute the query and get the result list.
    List<Instructor> instructors = query.getResultList();

    // Return the results.
    return instructors;
  }

  @Override
  public void deleteStudentFromCourse(Integer studentId, Integer courseId) {

    // Delete the relation, identified using the student and course ids, between
    // the course and the student. This will delete the record from the join table
    // removing the association between course and student. Course and Student
    // entities will remain. 
    Course course = entityManager.find(Course.class, courseId);
    Student student = entityManager.find(Student.class, studentId);
    course.getStudents().remove(student);
  }

  @Override
  public Course findCourseById(Integer courseId) {
    
    // Return course according to course id. By default the find method will search by primary key.
    return entityManager.find(Course.class, courseId);
  }

  @Override
  public Student findStudentById(Integer studentId) {
    
    // Return student according to student id. By default the find method will search by primary key.
    return entityManager.find(Student.class, studentId);
  }

  @Override
  public void save(Course course) {
    
    // Save course entity or student enrollment to the database.
    entityManager.persist(course);
  }

  @Override
  public void save(Student student) {
    
    // Save the student entity to the database.
    entityManager.persist(student);
  }

  @Override
  public void save(Instructor instructor) {
    
    // Save the instructor and associated instructor detail entities to the database.
    entityManager.persist(instructor);
  }

}
