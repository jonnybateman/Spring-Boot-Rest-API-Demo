package com.cqueltech.restapi.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Instructor;
import com.cqueltech.restapi.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AppController {

  private UserService userService;

  // Inject the UserService using constructor injection. The UserService acts as an
  // intermediary layer between the controller and the DAO that accesses the database.
  public AppController(UserService userService) {
    this.userService = userService;
  }
  
  @GetMapping("/")
  public String showHome() {
    return "home";
  }

  // Add a request mapping for /instructors.
  @GetMapping("/instructors")
  public String showInstructors(Model model) {

    // Get list of all instructors and their associated instructor detail.
    List<Instructor> instructors = userService.findAllInstructors();

    // Add to the Spring model so that the list of instructors is available to the Thymeleaf template.
    model.addAttribute("instructors", instructors);

    // Display the Thymeleaf template 'instructors.html'
    return "instructors";
  }

  // Add mapping for /courses, used to display all available courses.
  @GetMapping("/courses")
  public String displayCourses(Model model) {

    // Get list of all courses and associated instructors.
    List<Course> courses = userService.findAllCourses();

    // Add to the spring model so that the list of courses is available to the Thymeleaf template.
    model.addAttribute("courses", courses);

    // Display the Thymeleaf template 'courses.html'.
    return "courses";
  }

  // Add mapping for /course-reviews, used to display courses and their associated reviews
  @GetMapping("/course-reviews")
  public String displayCourseReviews(Model model) {

    // Get list of all courses and associated reviews.
    List<Course> courseReviews = userService.findAllCourses();

    // Add to the Spring model so that a list of reviews is available to the Thymeleaf template.
    model.addAttribute("courseReviews", courseReviews);

    // Display the Thymeleaf template.
    return "reviews";
  }

  // Add a mapping to display details of the course student relationships.
  @GetMapping("/courses-students")
  public String displayCoursesStudents(Model model) {

    // Get list of all courses and associated students.
    List<Course> courseStudents = userService.findAllCourses();

    // Add attribute to the Spring model so that a list of courses and students to the applicable
    // Thymeleaf template.
    model.addAttribute("courseStudents", courseStudents);

    // Display the Thymeleaf template.
    return "students";
  }
}
