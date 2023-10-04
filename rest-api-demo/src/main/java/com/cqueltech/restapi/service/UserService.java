package com.cqueltech.restapi.service;

import java.util.List;

import com.cqueltech.restapi.dto.CoursesDTO;
import com.cqueltech.restapi.dto.InstructorsDTO;
import com.cqueltech.restapi.dto.NewUserDTO;
import com.cqueltech.restapi.dto.ReviewsDTO;
import com.cqueltech.restapi.dto.StudentsDTO;
import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Instructor;
import com.cqueltech.restapi.entity.Student;
import com.cqueltech.restapi.entity.User;

public interface UserService {

  User findUserByUsername(String username);

  void save(NewUserDTO newUser);

  Course findCourseById(Integer courseId);

  List<CoursesDTO> findAllCourses();

  List<ReviewsDTO> findAllReviews();

  Instructor findInstructorById(Integer instructorId);

  List<InstructorsDTO> findAllInstructors();

  List<StudentsDTO> findAllStudents();

  Student findStudentById(Integer studentId);

  void deleteStudentFromCourse(Integer studentId, Integer courseId);

  void save(Course course);

  void save(Student student);

  void save(Instructor instructor);
}

