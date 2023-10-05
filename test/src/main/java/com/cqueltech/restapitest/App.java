package com.cqueltech.restapitest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import com.cqueltech.restapitest.CoursesResponse.Course;
import com.google.gson.Gson;

public class App {
  public static void main(String[] args) throws Exception {

    // Login the user and receive JWT token for further HTTP requests

    // Create instance of JwtLoginRequestDTO, this will be used for the body of
    // our login HTTP request.
    JwtLoginRequest jwtLoginRequest = new JwtLoginRequest("mike", "fun123");

    // Need to convert the JwtLoginRequestDTO instance to a JSON string.
    Gson gson = new Gson();
    String loginBody = gson.toJson(jwtLoginRequest);

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

    System.out.println(authenticationResponse.body());

    // Use GSON to parse json data to our response class. This allows the data to be
    // easily accessible.
    JwtLoginResponse jwtLoginResponse = gson.fromJson(authenticationResponse.body(), JwtLoginResponse.class);

    // Display the contents of the response class instance.
    System.out.println(jwtLoginResponse.getUser().getUsername());
    for (int i=0; i<jwtLoginResponse.getUser().getAuthorities().size(); i++) {
      System.out.println(jwtLoginResponse.getUser().getAuthorities().get(i).getRole());
    }
    System.out.println(jwtLoginResponse.getJwt());

    // Now lets use the JWT token in the web service to return a list of courses.

    // Get the JWT token.
    String jwt = jwtLoginResponse.getJwt();

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

    System.out.println(coursesResponse.body());

    // Use GSON to parse json data to our response class. This allows the data to be
    // easily accessible.
    //JwtCoursesResponse jwtCoursesResponse = gson.fromJson(coursesResponse.body(), JwtCoursesResponse.class);
    CoursesResponse response = gson.fromJson(coursesResponse.body(), CoursesResponse.class);

    System.out.println("Status: " + response.getStatus());
    System.out.println("Message: " + response.getMessage());
    System.out.println("Timestamp: " + response.getTimestamp());

    // Display the courses.
    for (Course course : response.getArray()) {
      System.out.println(course.getTitle() + ", " + course.getInstructorId());
    }
/*
    // Create a course using the JWT token for authentication.

    // Create request body class instance, contains information to create the new course.
    CreateCourseRequestDTO createCourseDTO = new CreateCourseRequestDTO("Economics", 2);
    // Using GSON convert createCourseDTO instance to a JSON string.
    String createCourseBody = gson.toJson(createCourseDTO);

    // Formulate a HTTP request to create a course
    HttpRequest createCourseRequest = HttpRequest.newBuilder()
        .uri(new URI("http://127.0.0.1:8080/api/create-course"))
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + jwt)
        .POST(BodyPublishers.ofString(createCourseBody))
        .build();
*/
  }
}
