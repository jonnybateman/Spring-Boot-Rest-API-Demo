package com.cqueltech.restapi.exceptionhandling;

/*
 * An Advice Controller acts as a layer above the Rest Controller. It is used to
 * pre-process requests from the client before going onto the actual Rest
 * Controller, or, post-process responses from the Rest Controller to handle
 * exceptions. When exception logic is placed here it is regarded as global exception
 * handling. There may be multiple rest controllers in a project which all use the
 * Advice Controller to handle exceptions thrown.
 * 
 * A ControllerAdvice to handle exceptions raised during web application http requests.
 * Base package set to only handle exceptions originating from controllers in the stated package.
 * 
 * Adds an attribute ('errorResponse') to the spring model containing the exception details,
 * which can then be utilised by the exception handling Thymeleaf template errors.html.
 */

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.cqueltech.restapi.dto.ServiceResponseDTO;
import org.springframework.ui.Model;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(basePackages = "com.cqueltech.restapi.controller.webappcontroller")
    // Comment out this annotation to see full exception stack trace in terminal.
@Slf4j
public class WebAppGlobalExceptionHandler<T> {

  private final static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

  /*
   * Generic exception handler to catch any exception that is not already handled.
   */
  @ExceptionHandler(Exception.class)
  public String handleException(Exception exc, Model model) {

    // Create a student response to be sent to Rest Client.
    ServiceResponseDTO<T> errorResponse = new ServiceResponseDTO<>();
    errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
    errorResponse.setMessage(exc.getMessage());
    errorResponse.setTimestamp(dateTimeFormat.format(ZonedDateTime.now()));
    errorResponse.setObject(null);

    // Add attribute to Spring model to make it accessible to Thymeleaf template.
    model.addAttribute("errorResponse", errorResponse);

    // Display the exception handling Thymeleaf template 'errors.html'.
    return "errors";
  }

}
