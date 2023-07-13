package com.cqueltech.restapi.dao;

import java.util.List;

import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Role;
import com.cqueltech.restapi.entity.User;

/*
 * The User Data Access Object. Used as an interface between our app and the database.
 * 
 * The DAO needs a JPA Entity Manager for saving/deleting/changing/retrieving entities (records).
 * The JPA Entity Manager will need to be injected into our DAO.
 */

public interface UserDAO {

  User findUserByUsername(String username);

  void save(User user);

  void save(Role role);

  List<Course> findAllCourses();
}
