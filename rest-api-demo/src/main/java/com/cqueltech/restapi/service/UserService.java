package com.cqueltech.restapi.service;

/* 
 * A Service component used to register a new user.
 */

import java.util.List;
import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Instructor;
import com.cqueltech.restapi.entity.Student;
import com.cqueltech.restapi.entity.User;
import com.cqueltech.restapi.modelclasses.NewUser;

public interface UserService {

  User findUserByUsername(String username);

  void save(NewUser newUser);

  Course findCourseById(Integer courseId);

  List<Course> findAllCourses();

  List<Instructor> findAllInstructors();

  Student findStudentById(Integer studentId);

  void deleteStudentFromCourse(Integer studentId, Integer courseId);

  void save(Course course);
}

