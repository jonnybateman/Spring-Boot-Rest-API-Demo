package com.cqueltech.restapitest;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class App<T> {
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
      .uri(new URI("http://127.0.0.1:8080/api/login"))
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

    // Now lets use the JWT token in the web service to return a list of courses.

    // Get the JWT token.
    String jwt = loginResponse.getJwt();

    // Formulate a new HTTP get request to return a list of courses.
    HttpRequest coursesRequest = HttpRequest.newBuilder()
        .uri(new URI("http://127.0.0.1:8080/api/courses"))
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + jwt)
        .GET()
        .build();

    // Send the request for a list of courses to the web service.
    HttpResponse<String> coursesResponse =
        httpClient.send(coursesRequest, BodyHandlers.ofString());
    
    // If we don't have a status in the range of 200 - 299 then the request failed.
    // Display details of failure.
    if (coursesResponse.statusCode() < 200 || coursesResponse.statusCode() >= 300) {
      ResponseDTO<?> responseDTO = gson.fromJson(coursesResponse.body(), ResponseDTO.class);
      System.out.println("HTTP Response Status: " + responseDTO.getStatus());
      System.out.println("Error: " + responseDTO.getMessage());
      return;
    }

    // The TypeToken instance is used by GSON to provide type information when converting a JSON
    // string into an instance of our response DTO class. The TypeToken instance is needed in this
    // case since the list of objects (array attribute) in our response class is generic.
    Type responseType = new TypeToken<ResponseDTO<CourseDTO>>(){}.getType();

    // Use GSON to parse json data to our response class. This allows the data to be
    // easily accessible.
    ResponseDTO<CourseDTO> coursesResponseDTO = gson.fromJson(coursesResponse.body(), responseType);

    // Display the courses.
    for (CourseDTO course : coursesResponseDTO.getArray()) {
      System.out.println(course.getTitle() + ", " + course.getInstructorId());
    }

    // Create a course using the JWT token for authentication.

    // Create request body class instance, contains information to create the new course.
    CreateCourseRequestDTO createCourseRequestDTO = new CreateCourseRequestDTO("Economics", 2);
    // Using GSON convert createCourseDTO instance to a JSON string.
    String createCourseBody = gson.toJson(createCourseRequestDTO);

    // Formulate a HTTP request to create a course
    HttpRequest createCourseRequest = HttpRequest.newBuilder()
        .uri(new URI("http://127.0.0.1:8080/api/create-course"))
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + jwt)
        .POST(BodyPublishers.ofString(createCourseBody))
        .build();

    // Send the request for a list of courses to the web service.
    HttpResponse<String> createCourseResponse =
        httpClient.send(createCourseRequest, BodyHandlers.ofString());

    // Use Google's GSON library to parse response body json data to our response class.
    // This allows the data to be easily accessible.  
    ResponseDTO<?> createCourseResponseDTO = gson.fromJson(createCourseResponse.body(), ResponseDTO.class);

    // Check the result of the create course request.
    System.out.println("HTTP Response Status: " + createCourseResponseDTO.getStatus());
    System.out.println("Message: " + createCourseResponseDTO.getMessage());
  }

}
