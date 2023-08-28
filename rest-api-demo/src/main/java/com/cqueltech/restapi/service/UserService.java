package com.cqueltech.restapi.service;

/* 
 * A Service component used to register a new user.
 */

import java.util.List;

import com.cqueltech.restapi.dto.NewUserDTO;
import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Instructor;
import com.cqueltech.restapi.entity.Student;
import com.cqueltech.restapi.entity.User;

public interface UserService {

  User findUserByUsername(String username);

  void save(NewUserDTO newUser);

  Course findCourseById(Integer courseId);

  List<Course> findAllCourses();

  Instructor findInstructorById(Integer instructorId);

  List<Instructor> findAllInstructors();

  Student findStudentById(Integer studentId);

  void deleteStudentFromCourse(Integer studentId, Integer courseId);

  void save(Course course);

  void save(Student student);

  void save(Instructor instructor);
}

