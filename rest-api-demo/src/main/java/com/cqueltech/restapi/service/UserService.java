package com.cqueltech.restapi.service;

import java.util.List;
import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Instructor;

/* 
 * A Service component used to register a new user.
 */

import com.cqueltech.restapi.entity.User;
import com.cqueltech.restapi.user.NewUser;

public interface UserService {

  User findUserByUsername(String username);

  void save(NewUser newUser);

  List<Course> findAllCourses();

  List<Instructor> findAllInstructors();
}

