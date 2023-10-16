package com.cqueltech.restapi.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

public class AnnotationResponseStatus {

  /*
   * Determine the HTTP status code of the thrown exception by using the @ResponseStatus
   * annotation to grab the status code.
   */
  public static HttpStatus resolveAnnotationResponseStatus(Exception exc) {

    ResponseStatus annotation = findMergedAnnotation(exc.getClass(), ResponseStatus.class);
    if (annotation != null) {
      return annotation.value();
    }
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
  
}
