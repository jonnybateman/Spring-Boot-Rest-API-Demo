package com.cqueltech.restapi.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.cqueltech.restapi.entity.Course;
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
  public String showInstructors() {
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

  // Add a request mapping for /courses.
  @GetMapping("/students")
  public String showStudents() {
    return "students";
  }
}
