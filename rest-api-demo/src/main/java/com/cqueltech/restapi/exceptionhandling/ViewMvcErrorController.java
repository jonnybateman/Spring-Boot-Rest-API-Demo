package com.cqueltech.restapi.exceptionhandling;

/*
 * A global error handling controller to handle View MVC (Model View Controller -
 * a design pattern developers use to manage the user interface of applications)
 * errors. This will catch any errors that occur during the rendering of a view/
 * template.
 * 
 * Adds an attribute ('errorResponse') to the spring model containing the exception details,
 * which can then be utilised by the exception handling Thymeleaf template 'errors.html'.
 */

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.cqueltech.restapi.dto.ServiceResponseDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ViewMvcErrorController<T> implements ErrorController {

  private final static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

  // Create error response object.
  ServiceResponseDTO<T> errorResponse = new ServiceResponseDTO<>();
  
  // If an error is returned from the client handle it.
  @GetMapping("/error")
  public String handleError(HttpServletRequest request, Model model) {

    // Get the status of the error from the client request.
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      int statusCode = Integer.valueOf(status.toString());

      if (statusCode == HttpStatus.NOT_FOUND.value()) {
        // Set error response object.
        setErrorResponse(statusCode, "Server cannot find the requested resource.");
      } else {
        setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
      }
    } else {
      setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
    }

    // Add attribute to the Spring model so that the error information can be passed to
    // the applicable Thymeleaf template.
    model.addAttribute("errorResponse", errorResponse);

    return "errors";
  }

  /*
   * Initialise error response object that will be added to Spring Boot model.
   */
  public void setErrorResponse(int status, String message) {

    errorResponse.setStatus(status);
    errorResponse.setMessage(message);
    errorResponse.setTimestamp(dateTimeFormat.format(ZonedDateTime.now()));
  }
}
