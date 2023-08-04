package com.cqueltech.restapi.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Instructor;
import com.cqueltech.restapi.entity.Student;
import com.cqueltech.restapi.modelclasses.StudentEnrollment;
import com.cqueltech.restapi.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AppController {

  private UserService userService;

  /*
   * Inject the UserService using constructor injection. The UserService acts as an
   * intermediary layer between the controller and the DAO that accesses the database.
   */ 
  public AppController(UserService userService) {
    this.userService = userService;
  }
  
  /*
   * Show the default hoe page after login.
   */
  @GetMapping("/")
  public String showHome() {
    return "home";
  }

  /*
   * Add a request mapping for /instructors.
   */
  @GetMapping("/instructors")
  public String showInstructors(Model model) {

    // Get list of all instructors and their associated instructor detail.
    List<Instructor> instructors = userService.findAllInstructors();

    // Add to the Spring model so that the list of instructors is available to the Thymeleaf template.
    model.addAttribute("instructors", instructors);

    // Display the Thymeleaf template 'instructors.html'
    return "instructors";
  }

  /*
   * Add mapping for /courses, used to display all available courses.
   */
  @GetMapping("/courses")
  public String displayCourses(Model model) {

    // Get list of all courses and associated instructors.
    List<Course> courses = userService.findAllCourses();

    // Add to the spring model so that the list of courses is available to the Thymeleaf template.
    model.addAttribute("courses", courses);

    // Display the Thymeleaf template 'courses.html'.
    return "courses";
  }

  /*
   * Add mapping for /course-reviews, used to display courses and their associated reviews
   */
  @GetMapping("/course-reviews")
  public String displayCourseReviews(Model model) {

    // Get list of all courses and associated reviews.
    List<Course> courseReviews = userService.findAllCourses();

    // Add to the Spring model so that a list of reviews is available to the Thymeleaf template.
    model.addAttribute("courseReviews", courseReviews);

    // Display the Thymeleaf template.
    return "reviews";
  }

  /*
   * Add a mapping to display details of the course student relationships.
   */
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

  /*
   * Add mapping for removing a student from a course. Student and course identified via
   * request parameters passed in the URL from the client.
   */
  @GetMapping("/courses-students/delete")
  public String delete(@RequestParam("studentId") Integer studentId, @RequestParam("courseId") Integer courseId) {

    // Remove the student from the course.
    userService.deleteStudentFromCourse(studentId, courseId);

    // Re-list the course-students table.
    return "redirect:/courses-students";
  }

  /*
   * Add mapping for enrolling a student on a course.
   */
  @GetMapping("/enroll-student")
  public String enrollStudent(Model model) {

    // Add a model attribute. This is an object that will hold the form data for the data binding.
    StudentEnrollment studentEnrollment = new StudentEnrollment();
    model.addAttribute("studentEnrollment", studentEnrollment);

    return "enroll-student";
  }

  /*
   * Process student enrollment form data and save course student relationship to database.
   * @Valid annotation instructs Spring to pass HTTP request to a Validator to check form
   *    entries against the validation annotations set in the StudentEnrollment class.
   * @ModelAttribute annotation is used here to retrieve a Spring model attribute, in this
   *    case a StudentEnrollment object that holds student enrollment data.
   * BindingResult - is Spring's object that holds the result of the validation and binding
   *    and contains errors that may have occurred.
   */
  @PostMapping("/authenticateEnrollment")
  public String authenticateStudentEnrollment(@Valid
                                              @ModelAttribute("studentEnrollment") StudentEnrollment studentEnrollment,
                                              BindingResult bindingResult,
                                              Model model) {
    
    if (bindingResult.hasErrors()) {
      // Course id and/or student id are not valid entries. Add attribute to the Spring model
      // to display the error on the enrollment form.
      model.addAttribute("enrollmentError", "Course Id and/or Student Id not valid");

      return "enroll-student";
    }

    // Check that the supplied course and student are valid entries in the database.
    Course course = userService.findCourseById(studentEnrollment.getCourseId());
    if (course == null) {
      model.addAttribute("enrollmentError", "Course does not exist");

      return "enroll-student";
    }

    Student student = userService.findStudentById(studentEnrollment.getStudentId());
    if (student == null) {
      model.addAttribute("enrollmentError", "Student does not exist");

      return "enroll-student";
    }

    // Add the student to the course object.
    course.addStudent(student);

    // Save the student enrollment to the database.
    //userService.save(courseStudent);
    userService.save(course);

    // Use a redirect to prevent duplicate submissions and send user to home page.
    return "redirect:/";
  }
}
