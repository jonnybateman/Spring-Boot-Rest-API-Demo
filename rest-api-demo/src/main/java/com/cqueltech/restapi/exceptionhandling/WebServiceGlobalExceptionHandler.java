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
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice(basePackages = "com.cqueltech.restapi.controller.webservicecontroller")
    // Comment out this annotation to see full exception stack trace in terminal.
public class WebServiceGlobalExceptionHandler<T> {

  private final static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

  /*
   * Generic exception handler to catch any exception that is not already handled.
   */
  @ExceptionHandler(Exception.class)
  public ServiceResponseDTO<T> handleException(HttpServletRequest request,Exception exc,  Model model) {

    // Generate a response to be sent to Rest Client.
    ServiceResponseDTO<T> errorResponse = new ServiceResponseDTO<>();

    if (exc instanceof AuthenticationException) {
      errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    } else {
      errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
    }
    errorResponse.setMessage(exc.getMessage());
    errorResponse.setTimestamp(dateTimeFormat.format(ZonedDateTime.now()));
    errorResponse.setObject(null);

    return errorResponse;
  }

}