package com.cqueltech.restapi.exceptionhandling;

/*
 * An Advice Controller acts as a layer above the Rest Controller. It is used to
 * pre-process requests from the client before going onto the actual Rest
 * Controller, or, post-process responses from the Rest Controller to handle
 * exceptions. When exception logic is placed here it is regarded as global exception
 * handling. There may be multiple rest controllers in a project which all use the
 * Advice Controller to handle exceptions thrown.
 */

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionController {

  private final static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
  
  // Exception handling.

  // Add generic exception handler to catch any exception that is not already handled.
  @ExceptionHandler(Exception.class)
  public String handleException(Exception exc, Model model) {

    // Create a student response to be sent to Rest Client.
    ErrorResponse error = new ErrorResponse();
    error.setStatus(HttpStatus.BAD_REQUEST.value());
    error.setMessage(exc.getMessage());
    error.setTimestamp(dateTimeFormat.format(ZonedDateTime.now()));

    // Add attribute to Spring model to make it accessible to Thymeleaf template.
    model.addAttribute("errorResponse", error);

    return "errors";
  }

}
