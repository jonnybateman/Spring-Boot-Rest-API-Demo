package com.cqueltech.restapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
  
  @GetMapping("/")
  public String showHome() {
    return "home";
  }

  // Add a request mapping for /instructors.
  @GetMapping("/instructors")
  public String showInstructors() {
    return "instructors";
  }

  // Add a request mapping for /courses.
  @GetMapping("/courses")
  public String showCourses() {
    return "courses";
  }

  // Add a request mapping for /courses.
  @GetMapping("/students")
  public String showStudents() {
    return "students";
  }
}
