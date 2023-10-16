package com.cqueltech.restapi.exceptionhandling;

/*
 * An Advice Controller acts as a layer above the Rest Controller. It is used to
 * pre-process requests from the client before going onto the actual Rest
 * Controller, or, post-process responses from the Rest Controller to handle
 * exceptions. When exception logic is placed here it is regarded as global exception
 * handling. There may be multiple rest controllers in a project which all use the
 * Advice Controller to handle exceptions thrown.
 * 
 * A RestControllerAdvice to handle exceptions raised during web service API http requests.
 * Base package set to only handle exceptions originating from controllers in the stated package.
 * 
 * Returns a ErrorResponseDTO (Data Transfer Object) containing exception details to the client.
 */

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.cqueltech.restapi.dto.ServiceResponseDTO;
import com.cqueltech.restapi.utils.AnnotationResponseStatus;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice(basePackages = "com.cqueltech.restapi.controller.webservicecontroller")
    // Comment out this annotation to see full exception stack trace in terminal.
@Slf4j
public class WebServiceGlobalExceptionHandler<T> implements AccessDeniedHandler {

  private final static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

  /*
   * Generic exception handler to catch any exception that is not already handled.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ServiceResponseDTO<T>> handleException(HttpServletRequest request, Exception exc) {

    log.info("Calling Exception Handler");
  
    // Resolve the HTTP status code for the exception.
    HttpStatus status = AnnotationResponseStatus.resolveAnnotationResponseStatus(exc);

    // Generate a response to be sent to Rest Client.
    ServiceResponseDTO<T> errorResponse = new ServiceResponseDTO<>();
    errorResponse.setStatus(status.value());
    errorResponse.setMessage(exc.getMessage());
    errorResponse.setTimestamp(dateTimeFormat.format(ZonedDateTime.now()));
    //errorResponse.setObject(null);

    return new ResponseEntity<ServiceResponseDTO<T>>(errorResponse, null, status);
  }

  /*
   * A role based authorization failure for a particular resource is not caught by the
   * global exception handler. To catch these exceptions we use AccessDeniedHandler which
   * is configured in the security config's filter chain and calls the overriden handle()
   * method to setup a custom response for the client.
   */
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {

    // Set the response code.
    response.setStatus(HttpStatus.FORBIDDEN.value());

    // Create the response object and convert to JSON string.
    ServiceResponseDTO<T> serviceResponseDTO = new ServiceResponseDTO<>(
        HttpStatus.FORBIDDEN.value(),
        "Access denied.",
        dateTimeFormat.format(ZonedDateTime.now()),
        null);

    Gson gson = new Gson();
    String serviceResponseJSON = gson.toJson(serviceResponseDTO);

    // Create response content.
    response.getWriter().write(serviceResponseJSON);
  }

}