package com.cqueltech.restapi.controller.webservicecontroller;

/*
 * Controller class to handle web service http requests. All requests need
 * to include the /api path segment to be processed by this controller.
 * e.g. http://localhost:8080/api/login
 */

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cqueltech.restapi.dto.CourseDTO;
import com.cqueltech.restapi.dto.CoursesDTO;
import com.cqueltech.restapi.dto.ServiceResponseDTO;
import com.cqueltech.restapi.dto.ReviewsDTO;
import com.cqueltech.restapi.dto.InstructorDTO;
import com.cqueltech.restapi.dto.InstructorsDTO;
import com.cqueltech.restapi.dto.JwtLoginResponseDTO;
import com.cqueltech.restapi.dto.JwtLoginRequestDTO;
import com.cqueltech.restapi.dto.StudentDTO;
import com.cqueltech.restapi.dto.StudentEnrollmentDTO;
import com.cqueltech.restapi.dto.StudentsDTO;
import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Instructor;
import com.cqueltech.restapi.entity.InstructorDetail;
import com.cqueltech.restapi.entity.Student;
import com.cqueltech.restapi.service.JwtAuthenticationService;
import com.cqueltech.restapi.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class ServiceController<T> {

  private UserService userService;
  private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

  /*
   * Inject the UserService using constructor injection. The UserService acts as an
   * intermediary layer between the controller and the DAO that accesses the database.
   */ 
  public ServiceController(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  private JwtAuthenticationService authenticationService;
  
  /*
   * The web service login mapping. A login http request should have a JSON body of
   * the following format:
   *    {
   *      "username" : "my-user-name",
   *      "password" : "my-password"
   *    }
   * The body will be mapped to JwtLoginUserDTO data transfer object and the username
   * and password extracted to be passed into the authentication service to verify the
   * users credentials. On successful authorization a response will be sent back to
   * the client containing a JWT token that the client app can use to access the web
   * service APIs.
   */
  @PostMapping("/login")
  public ResponseEntity<JwtLoginResponseDTO> loginUser(@RequestBody JwtLoginRequestDTO body) {

    return authenticationService.loginUser(body.getUsername(), body.getPassword());
  }

  /*
   * Mapping to handle a http request to return a list of courses
   */
  @SuppressWarnings("unchecked")
  @GetMapping("/courses")
  public ResponseEntity<ServiceResponseDTO<T>> getCourses() {

    log.info("Calling courses endpoint");

    // Get a list of courses and associated instructors.
    List<CoursesDTO> courses = userService.findAllCourses();

    // Generate the response body object and set attribute values.
    ServiceResponseDTO<T> serviceResponseDTO = new ServiceResponseDTO<>(HttpStatus.OK.value(),
                                                            "SUCCESS",
                                                            dateTimeFormat.format(ZonedDateTime.now()),
                                                            (T)courses);

    return new ResponseEntity<ServiceResponseDTO<T>>(serviceResponseDTO, null, HttpStatus.OK);
  }

  /*
   * Mapping for a HTTP request to return a list of course reviews.
   */
  @SuppressWarnings("unchecked")
  @GetMapping("/course-reviews")
  public ResponseEntity<ServiceResponseDTO<T>> getCourseReviews() {
    
    // Get a list or reviews for every course.
    List<ReviewsDTO> reviews = userService.findAllReviews();

    // Generate the response object and set attribute values.
    ServiceResponseDTO<T> serviceResponseDTO = new ServiceResponseDTO<>(HttpStatus.OK.value(),
                                                            "SUCCESS",
                                                            dateTimeFormat.format(ZonedDateTime.now()),
                                                            (T)reviews);

    // Return the response object to the client.
    return new ResponseEntity<ServiceResponseDTO<T>>(serviceResponseDTO, null, HttpStatus.OK);
  }

  /*
   * Mapping for a HTTP request to return a list of instructors.
   */
  @SuppressWarnings("unchecked")
  @GetMapping("/instructors")
  public ServiceResponseDTO<T> getInstructors() {

    // Get the list of instructors
    List<InstructorsDTO> instructors = userService.findAllInstructors();

    // Generate the HTTP response object and set attribute values.
    ServiceResponseDTO<T> serviceResponseDTO = new ServiceResponseDTO<>(HttpStatus.OK.value(),
                                                            "SUCCESS",
                                                            dateTimeFormat.format(ZonedDateTime.now()),
                                                            (T)instructors);

    // Return the response object to the client.
    return serviceResponseDTO;
  }

  /*
   * Maping for a HTTP request to return list of students enrolled on each course.
   */
  @SuppressWarnings("unchecked")
  @GetMapping("/students")
  public ServiceResponseDTO<T> getStudents() {

    // Get list of students.
    List<StudentsDTO> students = userService.findAllStudents();

    // Generate the HTTP response object and set attribute values.
    ServiceResponseDTO<T> serviceResponseDTO = new ServiceResponseDTO<>(HttpStatus.OK.value(),
                                                            "SUCCESS",
                                                            dateTimeFormat.format(ZonedDateTime.now()),
                                                            (T)students);

    // Return the response object to the client.
    return serviceResponseDTO;
  }

  /*
   * Mapping to enrol a student onto a course.
   * An enrol student request should have a JSON body of the following format:
   *    {
   *      "courseId" : course-id,
   *      "studentId" : student-id
   *    }
   * 
   * @Valid annotation used to ensure field validation is performed as setup
   * in the CourseDTO class. Validation errors caught and handled using
   * BindingResult class.
   */
  @PostMapping("/enrol-student")
  public ServiceResponseDTO<T> enrolStudent(@Valid
                                            @RequestBody StudentEnrollmentDTO body,
                                            BindingResult bindingResult) {

    // Initialise a new HTTP response object.
    ServiceResponseDTO<T> serviceResponseDTO = new ServiceResponseDTO<>();
    serviceResponseDTO.setTimestamp(dateTimeFormat.format(ZonedDateTime.now()));

    // Check for JSON attribute validation errors.
    if (bindingResult.hasErrors()) {
      serviceResponseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
      serviceResponseDTO.setMessage("Invalid request body.");
      return serviceResponseDTO;
    }

    // Check that course exists.
    Course course = userService.findCourseById(body.getCourseId());
    if (course == null) {
      serviceResponseDTO.setStatus(HttpStatus.NOT_FOUND.value());
      serviceResponseDTO.setMessage("Course Id does not exist");
      return serviceResponseDTO;
    }

    // Check that student exists.
    Student student = userService.findStudentById(body.getStudentId());
    if (student == null) {
      serviceResponseDTO.setStatus(HttpStatus.NOT_FOUND.value());
      serviceResponseDTO.setMessage("Student Id does not exist");
      return serviceResponseDTO;
      
    }

    // Enroll the student.
    course.addStudent(student);
    userService.save(course);

    // Return successful response to the client.
    serviceResponseDTO.setStatus(HttpStatus.OK.value());
    serviceResponseDTO.setMessage("SUCCESS");
    return serviceResponseDTO;
  }

  /*
   * A mapping endpoint to allow a HTTP request to create a course.
   * A create course request should have a JSON body of the following format:
   *    {
   *      "title" : "course-title",
   *      "instructorId" : instructor-id
   *    }
   */
  @PostMapping("/create-course")
  public ResponseEntity<ServiceResponseDTO<T>> createCourse(@Valid
                                            @RequestBody CourseDTO body,
                                            BindingResult bindingResult) {

    // Initialise the response body object to the client.
    ServiceResponseDTO<T> serviceResponseDTO = new ServiceResponseDTO<>();
    serviceResponseDTO.setTimestamp(dateTimeFormat.format(ZonedDateTime.now()));

    // Check for JSON attribute validation errors.
    if (bindingResult.hasErrors()) {
      serviceResponseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
      serviceResponseDTO.setMessage("Invalid request body.");
      return new ResponseEntity<ServiceResponseDTO<T>>(serviceResponseDTO, null, HttpStatus.BAD_REQUEST);
    }

    // Ensure the instructor id provided exists.
    Instructor instructor = userService.findInstructorById(body.getInstructorId());
    if (instructor == null) {
      serviceResponseDTO.setStatus(HttpStatus.NOT_FOUND.value());
      serviceResponseDTO.setMessage("Instructor Id does not exist");
      return new ResponseEntity<ServiceResponseDTO<T>>(serviceResponseDTO, null, HttpStatus.NOT_FOUND);
    }

    // Create the course entity to be saved from the request body.
    Course course = new Course(body.getTitle(), instructor);
    // Save the course to the database.
    userService.save(course);

    // Return a successful response to the client.
    serviceResponseDTO.setStatus(HttpStatus.OK.value());
    serviceResponseDTO.setMessage("SUCCESS");
    return new ResponseEntity<ServiceResponseDTO<T>>(serviceResponseDTO, null, HttpStatus.OK);
  }

  /*
   * A mapping endpoint to allow a HTTP request to create a student.
   * A create student request should have a JSON body of the following format:
   *    {
   *      "firstName" : "first-name",
   *      "lastName" : "last-name",
   *      "email" : "email-address"
   *    }
   */
  @PostMapping("/create-student")
  public ServiceResponseDTO<T> createStudent(@Valid
                                             @RequestBody StudentDTO body,
                                             BindingResult bindingResult) {
    
    // Initialise the response to the client.
    ServiceResponseDTO<T> serviceResponseDTO = new ServiceResponseDTO<>();
    serviceResponseDTO.setTimestamp(dateTimeFormat.format(ZonedDateTime.now()));

    // Check for JSON attribute validation errors.
    if (bindingResult.hasErrors()) {
      serviceResponseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
      serviceResponseDTO.setMessage("Invalid request body.");
      return serviceResponseDTO;
    }

    // Create the student entity to be saved from the request body.
    Student student = new Student(body.getFirstName(),
                                  body.getLastName(),
                                  body.getEmail());
    
    // Save the student to the database.
    userService.save(student);

    // Generate the HTTP response object and set attribute values.
    serviceResponseDTO.setStatus(HttpStatus.OK.value());
    serviceResponseDTO.setMessage("SUCCESS");

    // Return a successful HTTP response object to the client.
    return serviceResponseDTO;
  }

  /*
   * A mapping endpoint to allow a HTTP request to create an instructor.
   * A create instructor request should have a JSON body of the following format:
   *    {
   *      "firstName" : "first-name",
   *      "lastName" : "last-name",
   *      "email" : "email-address",
   *      "age" : age,
   *      "sex" : sex (M/F),
   *      "address" : "address"
   *    }
   */
  @PostMapping("/create-instructor")
  public ServiceResponseDTO<T> createInstructor(@Valid
                                                @RequestBody InstructorDTO body,
                                                BindingResult bindingResult) {

    // Initialise the response to the client.
    ServiceResponseDTO<T> serviceResponseDTO = new ServiceResponseDTO<>();
    serviceResponseDTO.setTimestamp(dateTimeFormat.format(ZonedDateTime.now()));

    // Check for JSON attribute validation errors.
    if (bindingResult.hasErrors()) {
      serviceResponseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
      serviceResponseDTO.setMessage("Invalid request body.");
      return serviceResponseDTO;
    }

    // Create the instructor entity to be saved from the request body.
    InstructorDetail instructorDetail = new InstructorDetail(body.getAge(),
                                                             body.getSex(),
                                                             body.getAddress());
    Instructor instructor = new Instructor(body.getFirstName(),
                                           body.getLastName(),
                                           body.getEmail());
    instructor.setInstructorDetail(instructorDetail);

    // Save the instructor to the database.
    userService.save(instructor);

    // Return a successful HTTP response object to the client.
    serviceResponseDTO.setStatus(HttpStatus.OK.value());
    serviceResponseDTO.setMessage("SUCCESS");
    return serviceResponseDTO;
  }

}