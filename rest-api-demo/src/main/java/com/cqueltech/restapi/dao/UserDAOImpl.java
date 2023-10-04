package com.cqueltech.restapi.dao;

/*
 * The User Data Access Object. Used as an interface between our app and the database.
 * 
 * The DAO needs a JPA Entity Manager for saving/deleting/changing/retrieving entities (records).
 * The JPA Entity Manager will need to be injected into our DAO.
 */

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cqueltech.restapi.dto.CoursesDTO;
import com.cqueltech.restapi.dto.InstructorsDTO;
import com.cqueltech.restapi.dto.ReviewsDTO;
import com.cqueltech.restapi.dto.StudentsDTO;
import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Instructor;
import com.cqueltech.restapi.entity.Role;
import com.cqueltech.restapi.entity.Student;
import com.cqueltech.restapi.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class UserDAOImpl implements UserDAO {

  // Define field for Entity Manager. EntityManager is one of the key abstractions
  // that JPA (Java Persistance API) specification defines. It sits between the
  // database and the application and plays the responsibility of managing entities
  // in context.
  private EntityManager entityManager;

  /*
   * Inject EntityManager using constructor injection.
   */
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
  public List<CoursesDTO> findAllCourses() {
    
    // Create a JPQL query.
    TypedQuery<CoursesDTO> query = entityManager.createQuery(
          "SELECT new com.cqueltech.restapi.dto.CoursesDTO(c.id, c.title, i.firstName, i.lastName, i.id) " +
          "FROM Course c JOIN c.instructor i", CoursesDTO.class);

    // Execute query and get the result list.
    List<CoursesDTO> courses = query.getResultList();

    // Return the results
    return courses;
  }

  @Override
  public List<ReviewsDTO> findAllReviews() {

    // Create a JPQL query.
    TypedQuery<ReviewsDTO> query = entityManager.createQuery(
        "SELECT new com.cqueltech.restapi.dto.ReviewsDTO(" +
            "c.id, c.title, r.comment) FROM Course c JOIN c.review r", ReviewsDTO.class);

    // Execute query and get the result list.
    List<ReviewsDTO> reviews = query.getResultList();

    // Return the results.
    return reviews;
  }

  @Override
  public Instructor findInstructorById(Integer instructorId) {

    // Return instructor according to instructor id. By default the find method will search by primary key.
    return entityManager.find(Instructor.class, instructorId);
  }

  @Override
  public List<InstructorsDTO> findAllInstructors() {
    
    // Create a JPQL query to retrieve a list of instructors and associated instructor detail.
    TypedQuery<InstructorsDTO> query = entityManager.createQuery(
      "SELECT new com.cqueltech.restapi.dto.InstructorsDTO(" +
          "i.id, i.firstName, i.lastName, i.email, d.age, d.sex, d.address) " +
          "FROM Instructor i JOIN i.instructorDetail d", InstructorsDTO.class);

    // Execute the query and get the result list.
    List<InstructorsDTO> instructors = query.getResultList();

    // Return the results.
    return instructors;
  }

  @Override
  public List<StudentsDTO> findAllStudents() {

    // Create a JPQL query to retrieve a list of students enrolled on each course.
    TypedQuery<StudentsDTO> query = entityManager.createQuery(
        "SELECT new com.cqueltech.restapi.dto.StudentsDTO(" +
        "c.id, c.title, s.firstName, s.lastName, s.email, s.id) " +
        "FROM Course c JOIN c.students s", StudentsDTO.class);

    // Execute the query and get the result set.
    List<StudentsDTO> students = query.getResultList();

    // Return the results
    return students;
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
