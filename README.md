# Spring Boot Rest API Demo

RESTful API is an interface that two computer systems use to exchange information securely over the internet. An application programming interface (API) defines the rules that you must follow to communicate with other software systems. Developers expose or create APIs so that other applications can communicate with their applications programmatically. You can think of a web API as a gateway between clients and resources on the web.

### Client

Clients are users who want to access information from the web. The client can be a person or a software system that uses the API.

### Resources

Resources are the information that different applications provide to their clients. Resources can be images, videos, text, numbers, or any type of data. The machine that gives the resource to the client is also called the server. Organizations use APIs to share resources and provide web services while maintaining security, control, and authentication.

### What is a REST API

REST stands for Representational State Transfer. This means that when a client requests a resource using a REST API, the server transfers back the current state of the resource in a standardized representation. API developers can design APIs using several different architectures. APIs that follow the REST architectural style are called REST APIs. Web services that implement REST architecture are called RESTful web services.

## RestApi Demo

The demo in this repository is for a RESTful API Web Service. It has a MySQL backend database which the web service can view and modify. In this demo the database tracks students and instructors in relation to courses. The entity relationship diagram for the database can be viewed below.

In addition to the Rest Web Service API a Web Application has also been developed to view and modify the resources hosted by the server. It can be accessed through the link [RestApi-Application](https://cqueltech.com/restapi-0.0.1-SNAPSHOT).

### Instructor-Student-Tracking schema
![Alt text](./RestApi-ERD-Dark-SVG.svg)

To use the web service/application one must login in first. A user has associated role(s), each role has it's own level of access to the database. The role depicts what can be viewed, modified or created within the database. The roles, and their associated database access, are listed below.

- Student role:
  - view courses and associated instructor conducting the course
  - view course reviews.
- Instructor role:
  - view instructors and the associated instructor detail
  - view students enrolled on courses
  - remove students from courses
  - enroll student onto course
- Admin role:
  - create a new course
  - create a new student
  - create a new instructor
 
To create a user just press the `Register User` button in the web application sign-in page. It just requires a username and password, no personal details are required or stored. By default the user will be created with the `Student` role. The `Instructor` role can also be assigned to the user by checking the applicable check box. The `Admin` role is reserved for database administrators only.

The web service/application is deployed on an Apache Tomcat server with an Apache server acting as a reverse proxy to it.

## Web Service HTTP Request Configuration

### Request Types

All requests are made by adding a specified end point to the following URL: https://cqueltech.com/restapi-0.0.1-SNAPSHOT/api

|Method|Endpoint|Description|
|------|--------|-----------|
|POST|/login|Login to the service passing a JSON request body as set out in the parameters below. Returns an Oauth2 JWT Token to be used in authorizing further requests.|
|GET|/courses|This request returns a list of courses with associated instructor ids. Request requires an Authorization header of format "Bearer your-jwt-token".|
|GET|/course-reviews|Returns a list of submitted reviews for each course. Request requires an Authorization header of format "Bearer your-jwt-token".|
|GET|/instructors|Request returns a list of instructors with associated instructor detail. Requires the aforementioned Authorization header.|
|GET|/students|This request will return a list of courses with all students enrolled on these courses. Requires the aforementioned Authorization header.|
|POST|/enrol-student|This post request enrols a student onto a course. Requires the aforementioned Authorization header.|
|POST|/create-course|Will create a new course using parameters found in the supplied request body. Requires the aforementioned Authorization header.|
|POST|/create-student|Will create a new student using parameters found in the supplied request body. Requires the aforementioned Authorization header.|
|POST|/create-instructor|Will create a new instructor using parameters found in the supplied request body. Requires the aformentioned Authorization header.|

### Request Parameters

Parameters for endpoints are sent via the request body in JSON format, for example the body for the `/login` endpoint is as follows:

```
    {
      "username" : "your-username",
      "password" : "your-password"
    }
```

|Endpoint|Parameter|Type|Description|Required|
|--------|---------|----|-----------|--------|
|/login|username|String|The username for the user logging in to the service.|Yes|
|/login|password|String|The password for the user logging in to the service.|Yes|
|/enrol-student|courseId|Integer|Course id of the course that the student is to be enroled onto.|Yes|
|/enrol-student|studentId|Integer|Id of student to be enroled.|Yes|
|/create-course|title|String|Title of the course.|Yes|
|/create-course|instructorId|Integer|Id of the instructor that teaches the course. Id must be for an instructor that already exists.|Yes|
|/create-student|firstName|String|The first name of the student.|Yes|
|/create-student|lastName|String|The last name of the student.|Yes|
|/create-student|email|String|Email address of the student.|Yes|
|/create-instructor|firstName|String|The first name of the instructor.|Yes|
|/create-instructor|lastName|String|The last Name of the instructor.|Yes|
|/create-instructor|email|String|Email address of instructor.|Yes|
|/create-instructor|age|Integer|Age of instructor.|Yes|
|/create-instructor|sex|String|The sex of the instructor.|Yes
|/create-instructor|address|String|The address for the instructor.|Yes|

### Request Response

All responses to HTTP requests are returned in a specific JSON format as detailed in the table below.

|Endpoint|Response Attribute|Type|Descrition|
|--------|------------------|----|----------|
|/login|user|Object|An object containing all attributes associated with the user account.|
|/login|user:username|String|Username of user account|
|/login|user:password|String|Password of user account. All passwords stored with BCrypt encryption.|
|/login|user:active|Integer|Defines whether user account is currenly active. 1 -> Active; 0 -> Non-active.|
|/login|user:accountNonLocked|Boolean|Defines if account is loacked or not.|
|/login|user:accountNonExpired|Boolean|Has the accoount expired or not.|
|/login|user:credentialsNonExpired|Boolean|Indicates whether the user's credentials(password) has expired.|
|/login|user:authorities|Array|A set of authorities or roles linked to the user|
|/login|user:authorities:id|Integer|Id of authority/role for the user.|
|/login|user:authorities:username|String|Username identifying the user that the authority/role belongs to.|
|/login|user:authorities:role|String|Role that the user has been authorized to use.|
|/login|user:authorities:authority|String|Authority that the user has been authorized to use.|
|/login|user:enabled|Boolean|Indicates whether the user is enabled or disabled.|
|/login|jwt|String|The JWT Token that has been generated for the user upon successful authorization. Token should be used in further HTTP requests to authorize those requests.|
|/courses; /course-reviews; /students; /instructors; /enrol-student; /create-student; /create-course; /create-instructor|status|Integer|HTTP status of request.|
|/courses; /course-reviews; /students; /instructors; /enrol-student; /create-student; /create-course; /create-instructor|message|String|Request message/information/error|
|/courses; /course-reviews; /students; /instructors; /enrol-student; /create-student; /create-course; /create-instructor|timestamp|String|Timestamp that the request was received.|
|/courses; /course-reviews; /students; /instructors; /enrol-student; /create-student; /create-course; /create-instructor|array|Array|An array of records retrieved from the web services' database. In the event of a POST request endpoint the `array` attribute in the response will be null.|
|/courses|array:courseId|Integer|Course Id.|
|/courses|array:title|String|The title of the course.|
|/courses|array:firstName|String|First name of instructor conducting course.|
|/courses|array:lastName|String|Last name of instructor conducting the course.|
|/courses|array:instructorId|Integer|Id of instructor conducting the course.|
|/course-reviews|array:courseId|Integer|Course Id.|
|/course-reviews|array:title|String|The title of the course.|
|/course-reviews|array:comment|String|Student comment on the course.|
|/students|array:courseId|Integer|Course Id that the student is enroled on.|
|/students|array:title|String|Title of the course that the student is enroled on.|
|/students|array:firstName|String|First name of student enroled on course.|
|/students|array:lastName|String|Last name of student enroled on course.|
|/students|array:email|String|Email address of student enroled on course.|
|/students|array:studentId|Integer|Id of student enroled on course.|
|/instructors|array:instructorId|Integer|Id of instructor.|
|/instructors|array:firstName|String|First name of instructor.|
|/instructors|array:lastName|String|Last name of instructor.|
|/instructors|array:age|Integer|Age of instructor.|
|/instructors|array:sex|String|Sex of instructor(M/F).|
|/instructors|array:address|String|Address of instructor.|

An example of a login response in JSON format is as follows

```
    {
      "user": {
          "username": "mike",
          "password": "{bcrypt}$2a$10$S5G9Gmmdtjg06xCq58.1eOC2Go62amzp2EXVYbTfy871WF5cSID4O",
          "active": 1,
          "accountNonLocked": true,
          "accountNonExpired": true,
          "credentialsNonExpired": true,
          "authorities": [
              {
                  "id": 6,
                  "username": "mike",
                  "role": "INSTRUCTOR",
                  "authority": "INSTRUCTOR"
              },
              {
                  "id": 5,
                  "username": "mike",
                  "role": "STUDENT",
                  "authority": "STUDENT"
              }
          ],
          "enabled": true
      },
      "jwt": "jwt-token"
    }
```

What follows is an example of the standardized JSON response for all HTTP requests except `/login`, this example is for the `/courses` endpoint.

```
    {
    "status": 200,
    "message": "SUCCESS",
    "timestamp": "09-10-2023 17:59:17",
    "array": [
        {
            "courseId": 1,
            "title": "Maths",
            "firstName": "John",
            "lastName": "Smith",
            "instructorId": 1
        },
        {
            "courseId": 2,
            "title": "Physics",
            "firstName": "John",
            "lastName": "Smith",
            "instructorId": 1
        },
        {
            "courseId": 3,
            "title": "Biology",
            "firstName": "Joe",
            "lastName": "Bloggs",
            "instructorId": 2
        }
    ]
}
```

Should an error occur or a bad request is sent the response will be in the same format detailing the error. The array attribute will of course be empty or null.

## Using the Web Service

The following pieces of code demonstrate the use of the web service in a client application.

To use the web service we first need to retrieve an Oauth2 JWT token from the service by passing user credentials in a request to the `/login` endpoint. On successful authentication of the user a token will be returned in the response. A new user can be registed in the Web Application, use this [RestApi-Application](https://cqueltech.com/restapi-0.0.1-SNAPSHOT).

```
    import java.net.URI;
    import java.net.http.HttpClient;
    import java.net.http.HttpRequest;
    import java.net.http.HttpRequest.BodyPublishers;
    import java.net.http.HttpResponse;
    import java.net.http.HttpResponse.BodyHandlers;
    import com.cqueltech.restapitest.ResponseDTO.Course;
    import com.google.gson.Gson;

    public class App {
      public static void main(String[] args) throws Exception {

        // Login the user and receive JWT token for further HTTP requests

        // Create instance of JwtLoginRequestDTO, this will be used for the body of
        // our login HTTP request.
        LoginRequestDTO jwtLoginRequestDTO = new LoginRequestDTO("susan", "fun123");

        // Need to convert the JwtLoginRequestDTO instance to a JSON string.
        Gson gson = new Gson();
        String loginBody = gson.toJson(jwtLoginRequestDTO);

        // Formulate the HTTP request for user authentication.
        HttpRequest authenticationRequest = HttpRequest.newBuilder()
          .uri(new URI("https://cqueltech.com/restapi-0.0.1-SNAPSHOT/api/login"))
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString(loginBody))
          .build();

        // Send the authentication request to the web service. We will recieve a string
        // in JSON format as a response.
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> authenticationResponse =
            httpClient.send(authenticationRequest, BodyHandlers.ofString());

        // If we don't have a status in the range of 200 - 299 then the request failed.
        // Display details of failure.
        if (authenticationResponse.statusCode() < 200 || authenticationResponse.statusCode() >= 300) {
          ResponseDTO<?> authenticationResponseDTO =
              gson.fromJson(authenticationResponse.body(), ResponseDTO.class);
          System.out.println("HTTP Response Status: " + authenticationResponseDTO.getStatus());
          System.out.println("Error: " + authenticationResponseDTO.getMessage());
          return;
        }

        // Use GSON to parse JSON response body to our response class. This allows the data to be
        // easily accessible.
        LoginResponseDTO loginResponse = gson.fromJson(authenticationResponse.body(), LoginResponseDTO.class);

        // Display the contents of the response class instance.
        System.out.println(loginResponse.getUser().getUsername());
        for (int i=0; i<loginResponse.getUser().getAuthorities().size(); i++) {
          System.out.println(loginResponse.getUser().getAuthorities().get(i).getRole());
        }
    
        // Get the JWT token.
        String jwt = loginResponse.getJwt();

        ...
      }
    }
```

Now that we have retrieved the JWT token, requests to retrieve data from the service can be made. In the code snippet below we submit a request to retrieve information on available courses.

```
    // Additional import statements
    import java.lang.reflect.Type;
    import com.google.gson.reflect.TypeToken;

    ...
    // Now lets use the JWT token in the web service to return a list of courses.

    // Formulate a new HTTP get request to return a list of courses.
    HttpRequest coursesRequest = HttpRequest.newBuilder()
        .uri(new URI("https://cqueltech.com/restapi-0.0.1-SNAPSHOT/api/courses"))
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + jwt)
        .GET()
        .build();

    // Send the request for a list of courses to the web service.
    HttpResponse<String> coursesResponse =
        httpClient.send(coursesRequest, BodyHandlers.ofString());
    
    // If we don't have a HTTP response status in the range of 200 - 299 then the request failed.
    // Display details of failure.
    if (coursesResponse.statusCode() < 200 || coursesResponse.statusCode() >= 300) {
      ResponseDTO<?> responseDTO = gson.fromJson(coursesResponse.body(), ResponseDTO.class);
      System.out.println("HTTP Response Status: " + responseDTO.getStatus());
      System.out.println("Error: " + responseDTO.getMessage());
      return;
    }

    // The TypeToken instance is used by GSON to provide type information when converting a JSON
    // string into an instance of our response DTO class. The TypeToken instance is needed in this
    // case since the list of objects (in `array` attribute) in our ResponseDTO class is generic.
    Type responseType = new TypeToken<ResponseDTO<CourseDTO>>(){}.getType();

    // Use Google's GSON library to parse JSON data to our response class. This allows the data to be
    // easily accessible.
    ResponseDTO<CourseDTO> coursesResponseDTO = gson.fromJson(coursesResponse.body(), responseType);

    // Display the courses.
    for (CourseDTO course : coursesResponseDTO.getArray()) {
      System.out.println(course.getTitle() + ", " + course.getInstructorId());
    }
    ...
```

Here we have an example of a HTTP request to create a new course and store it in the server's database. Again we will be using the JWT token to authorise the request. The HTTP response will indicate whether the request succeeded or failed.

```
    // Create request body class instance, contains information to create the new course.
    CreateCourseRequestDTO createCourseRequestDTO = new CreateCourseRequestDTO("Economics", 2);
    // Using GSON convert createCourseDTO instance to a JSON string.
    String createCourseBody = gson.toJson(createCourseRequestDTO);

    // Formulate a HTTP request to create a course
    HttpRequest createCourseRequest = HttpRequest.newBuilder()
        .uri(new URI("https://cqueltech.com/restapi-0.0.1-SNAPSHOT/api/create-course"))
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + jwt)
        .POST(BodyPublishers.ofString(createCourseBody))
        .build();

    // Send the request for a list of courses to the web service.
    HttpResponse<String> createCourseResponse =
        httpClient.send(createCourseRequest, BodyHandlers.ofString());

    // Use Google's GSON library to parse response body JSON data to our response class.
    // This allows the data to be easily accessible.
    ResponseDTO<?> createCourseResponseDTO = gson.fromJson(createCourseResponse.body(), ResponseDTO.class);

    // Check if the result of the create course request.
    System.out.println("HTTP Response Status: " + createCourseResponseDTO.getStatus());
    System.out.println("Message: " + createCourseResponseDTO.getMessage());
```

The snippets of code seen here utilize DTO classes in the process of sending HTTP requests or receiving HTTP responses to/from a web service (API). The DTO classes used in these examples can be seen below.

### LoginRequestDTO.java
```
    public class LoginRequestDTO {
  
       private String username;
       private String password;
 
       public LoginRequestDTO() {
         super();
       }
 
       public LoginRequestDTO(String username, String password) {
         this.username = username;
         this.password = password;
       }
 
       public String getUsername() {
         return username;
       }
 
       public void setUsername(String username) {
         this.username = username;
       }
 
       public String getPassword() {
         return password;
       }
 
       public void setPassword(String password) {
         this.password = password;
       }
    }
```

### LoginResponseDTO.java
```
    import java.util.ArrayList;
    import java.util.List;
    
    public class LoginResponseDTO {
      
      private User user;
      private String jwt;
    
      public User getUser() {
        return user;
      }
    
      public void setUser(User user) {
        this.user = user;
      }
      
      public String getJwt() {
        return jwt;
      }
    
      public void setJwt(String jwt) {
        this.jwt = jwt;
      }
    
      public class User {
    
        private String username;
        private List<Authorities> authorities;
    
        public User() {
          this.authorities = new ArrayList<Authorities>();
        }
    
        public String getUsername() {
          return username;
        }
    
        public void setUsername(String username) {
          this.username = username;
        }
    
        public List<Authorities> getAuthorities() {
          return authorities;
        }
    
        public void setAuthorities(List<Authorities> authorities) {
          this.authorities = authorities;
        }
    
        public class Authorities {
    
          private String role;
    
          public String getRole() {
            return role;
          }
    
          public void setRole(String role) {
            this.role = role;
          }
        }
      }
    }
```

### CourseDTO.java
```
    public class CourseDTO {

      private int courseId;
      private String title;
      private String firstName;
      private String lastName;
      private int instructorId;
    
      public int getCourseId() {
        return courseId;
      }
    
      public void setCourseId(int courseId) {
        this.courseId = courseId;
      }
    
      public String getTitle() {
        return title;
      }
    
      public void setTitle(String title) {
        this.title = title;
      }
    
      public String getFirstName() {
        return firstName;
      }
    
      public void setFirstName(String firstName) {
        this.firstName = firstName;
      }
    
      public String getLastName() {
        return lastName;
      }
    
      public void setLastName(String lastName) {
        this.lastName = lastName;
      }
    
      public int getInstructorId() {
        return instructorId;
      }
    
      public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
      }
    }
```

### CreateCourseRequestDTO.java
```
    public class CreateCourseRequestDTO {
  
      private String title;
      private int instructorId;
    
      public CreateCourseRequestDTO(String title, int instructorId) {
        this.title = title;
        this.instructorId = instructorId;
      }
    
      public String getTitle() {
        return title;
      }
    
      public void setTitle(String title) {
        this.title = title;
      }
    
      public int getInstructorId() {
        return instructorId;
      }
    
      public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
      }
    }
```

### ResponseDTO.java
```
    import java.util.List;

    public class ResponseDTO<T> {
      
      private int status;
      private String message;
      private String timestamp;
      private List<T> array;
    
      public int getStatus() {
        return status;
      }
    
      public void setStatus(int status) {
        this.status = status;
      }
    
      public String getMessage() {
        return message;
      }
    
      public void setMessage(String message) {
        this.message = message;
      }
    
      public String getTimestamp() {
        return timestamp;
      }
    
      public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
      }
    
      public List<T> getArray() {
        return array;
      }
    
      public void setArray(List<T> array) {
        this.array = array;
      }
    }
```
