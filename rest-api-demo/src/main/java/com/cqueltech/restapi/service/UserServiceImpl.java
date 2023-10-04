package com.cqueltech.restapi.service;

/*
 * This Service layer lies between the Rest Controller and the DAO. It acts as an intermediate
 * layer for custom business logic. It is able to integrate data from multiple sources (DAOs/
 * repositories) and pass the data to our rest controller.
 * The service has the @Service annotation to allow Spring Boot to see it as a component
 * and automatically register it thanks to component scanning.
 */

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cqueltech.restapi.dao.UserDAO;
import com.cqueltech.restapi.dto.CoursesDTO;
import com.cqueltech.restapi.dto.InstructorsDTO;
import com.cqueltech.restapi.dto.NewUserDTO;
import com.cqueltech.restapi.dto.ReviewsDTO;
import com.cqueltech.restapi.dto.StudentsDTO;
import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Instructor;
import com.cqueltech.restapi.entity.Role;
import com.cqueltech.restapi.entity.Student;
import com.cqueltech.restapi.entity.User;

@Service
public class UserServiceImpl implements UserService {

  private UserDAO userDAO;

  @Autowired
  private PasswordEncoder passwordEncoder;

  /*
   * Inject the UserDAO component using constructor injection.
   */
  public UserServiceImpl(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  /*
   * Find the user using the primary key username from the User table.
   * Returns an object of type User.
   */
  @Override
  public User findUserByUsername(String username) {

    // Using the userDAO to access the database return a User object for the supplied username
    // if one exists.
    return userDAO.findUserByUsername(username);
  }

  /*
   * Save a new user and associated roles to the database. The transacltional annotation is used
   * to commit the change to the database.
   */
  @Override
  @Transactional
  public void save(NewUserDTO newUser) {

    // Encrypt the password using the BCryptPasswordEncoder.
    String password = passwordEncoder.encode(newUser.getPassword1());

    // Using the user DAO to access the database save the new user.
    User user = new User(newUser.getUsername(), password, 1);
    userDAO.save(user);
    
    // Using the user DAO to access the database save the default STUDENT role for the new user.
    Role roleStudent = new Role(newUser.getUsername(), "STUDENT");
    userDAO.save(roleStudent);

    // If the instructor role has also been requested, create INSTRUCTOR role for new user.
    int result = newUser.getRoleInstructor() == null ? 0 : 1;
    if (result == 1) {
      Role roleInstructor = new Role(newUser.getUsername(), "INSTRUCTOR");
      userDAO.save(roleInstructor);
    }
  }

  /*
   * Return a list of Course objects.
   */
  @Override
  public List<CoursesDTO> findAllCourses() {
    
    return userDAO.findAllCourses();
  }

  /*
   * Return a list reviews for all courses.
   */
  @Override
  public List<ReviewsDTO> findAllReviews() {
    
    return userDAO.findAllReviews();
  }

  /*
   * Return an Instructor object using the primary key instrucotr id.
   */
  @Override
  public Instructor findInstructorById(Integer instructorId) {

    // Check if the passed instructorId exists.
    Instructor instructor = userDAO.findInstructorById(instructorId);
    if (instructor == null) {

    }

    return userDAO.findInstructorById(instructorId);
  }

  /*
   * Using the UserDAO to access the database, return a list of all Instructors.
   */
  @Override
  public List<InstructorsDTO> findAllInstructors() {
    
    return userDAO.findAllInstructors();
  }

  /*
   * Using the UserDAO to access the database, return a list students enrolled
   * on each course.
   */
  @Override
  public List<StudentsDTO> findAllStudents() {

    return userDAO.findAllStudents();
  }

  /*
   * Disenroll a student from a course. The UserDAO will remove the record from
   * the course_student join table that associates a student with a course.
   */
  @Override
  @Transactional
  public void deleteStudentFromCourse(Integer studentId, Integer courseId) {
    
    userDAO.deleteStudentFromCourse(studentId, courseId);
  }

  /*
   * Return a Course object using the primary key course id.
   */
  @Override
  public Course findCourseById(Integer courseId) {
    
    return userDAO.findCourseById(courseId);
  }

  /*
   * Return a Student object using the primary key student id.
   */
  @Override
  public Student findStudentById(Integer studentId) {
    
    return userDAO.findStudentById(studentId);
  }

  @Override
  @Transactional
  public void save(Course course) {
    
    // Using the user DAO to access the database enroll the student on the course.
    userDAO.save(course);
  }

  @Override
  @Transactional
  public void save(Student student) {

    // Using the user DAO to access the database save the student entity.
    userDAO.save(student);
  }

  @Override
  @Transactional
  public void save(Instructor instructor) {
    
    // Using the user DAO to access the database save the instructor entity.
    userDAO.save(instructor);
  }
  
}
