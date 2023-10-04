package com.cqueltech.restapi.dao;

import java.util.List;

import com.cqueltech.restapi.dto.CoursesDTO;
import com.cqueltech.restapi.dto.InstructorsDTO;
import com.cqueltech.restapi.dto.ReviewsDTO;
import com.cqueltech.restapi.dto.StudentsDTO;
import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Instructor;
import com.cqueltech.restapi.entity.Role;
import com.cqueltech.restapi.entity.Student;
import com.cqueltech.restapi.entity.User;

public interface UserDAO {

  User findUserByUsername(String username);

  void save(User user);

  void save(Role role);

  void save(Course course);

  void save(Student student);

  void save(Instructor instructor);

  Course findCourseById(Integer courseId);

  List<CoursesDTO> findAllCourses();

  List<ReviewsDTO> findAllReviews();

  Instructor findInstructorById(Integer instructorId);
  
  List<InstructorsDTO> findAllInstructors();

  List<StudentsDTO> findAllStudents();

  Student findStudentById(Integer studentId);

  void deleteStudentFromCourse(Integer studentId, Integer courseId);

}
