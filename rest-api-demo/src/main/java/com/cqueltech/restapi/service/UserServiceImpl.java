package com.cqueltech.restapi.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cqueltech.restapi.dao.UserDAO;
import com.cqueltech.restapi.dto.NewUserDTO;
import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Instructor;
import com.cqueltech.restapi.entity.Role;
import com.cqueltech.restapi.entity.Student;
import com.cqueltech.restapi.entity.User;

/*
 * This Service layer lies between the Rest Controller and the DAO. It acts as an intermediate
 * layer for custom business logic. It is able to integrate data from multiple sources (DAOs/
 * repositories) and pass the data to our rest controller.
 * The service has the @Service annotation to allow Spring Boot to see it as a component
 * and automatically register it thanks to component scanning.
 */

 @Service
public class UserServiceImpl implements UserService {

  private UserDAO userDAO;

  @Autowired
  private PasswordEncoder passwordEncoder;

  // Inject the UserDAO component using constructor injection.
  public UserServiceImpl(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public User findUserByUsername(String username) {

    // Using the userDAO to access the database return a User object for the supplied username
    // if one exists.
    return userDAO.findUserByUsername(username);
  }

  @Override
  @Transactional
  public void save(NewUserDTO newUser) {

    // Encrupt the password using the BCryptPasswordEncoder.
    String password = passwordEncoder.encode(newUser.getPassword1());

    // Using the user DAO to access the database save the new user.
    User user = new User(newUser.getUsername(), password, 1);
    userDAO.save(user);
    
    // Using the user DAO to access the database save the default STUDENT role for the new user.
    Role roleStudent = new Role(newUser.getUsername(), "ROLE_STUDENT");
    userDAO.save(roleStudent);

    // If the instructor role has also been requested, create INSTRUCTOR role for new user.
    int result = newUser.getRoleInstructor() == null ? 0 : 1;
    if (result == 1) {
      Role roleInstructor = new Role(newUser.getUsername(), "ROLE_INSTRUCTOR");
      userDAO.save(roleInstructor);
    }
  }

  @Override
  public List<Course> findAllCourses() {
    
    return userDAO.findAllCourses();
  }

  @Override
  public Instructor findInstructorById(Integer instructorId) {

    return userDAO.findInstructorById(instructorId);
  }

  @Override
  public List<Instructor> findAllInstructors() {
    
    return userDAO.findAllInstructors();
  }

  @Override
  @Transactional
  public void deleteStudentFromCourse(Integer studentId, Integer courseId) {
    
    userDAO.deleteStudentFromCourse(studentId, courseId);
  }

  @Override
  public Course findCourseById(Integer courseId) {
    
    return userDAO.findCourseById(courseId);
  }

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
