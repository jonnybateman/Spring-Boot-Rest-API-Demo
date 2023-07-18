package com.cqueltech.restapi.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cqueltech.restapi.dao.UserDAO;
import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Instructor;
import com.cqueltech.restapi.entity.Role;
import com.cqueltech.restapi.entity.User;
import com.cqueltech.restapi.user.NewUser;

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
  public void save(NewUser newUser) {

    // Encrupt the password using the BCryptPasswordEncoder.
    String password = passwordEncoder.encode(newUser.getPassword1());

    // Using the user DAO to access the database save the new user.
    User user = new User(newUser.getUsername(), password, 1);
    userDAO.save(user);
    
    // Using the user DAO to access the database save the default STUDENT role for the new user.
    Role role = new Role(newUser.getUsername(), "ROLE_STUDENT");
    userDAO.save(role);

    // If the instructor role has also been requested, create INSTRUCTOR role for new user.
    if (newUser.getRoleInstructor() == 1) {
      role.setRole("ROLE_INSTRUCTOR");
      userDAO.save(role);
    }
  }

  @Override
  public List<Course> findAllCourses() {
    
    return userDAO.findAllCourses();
  }

  @Override
  public List<Instructor> findAllInstructors() {
    
    return userDAO.findAllInstructors();
  }
  
}
