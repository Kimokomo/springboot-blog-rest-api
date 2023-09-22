package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)  //ResponseStatus annotation cause Spring boot to respond with the specified HTTP status code whenever this exception is thrown from your controller
public class ResourceNotFoundException extends RuntimeException {  // need to extend RuntimeException
  /*
                           Creating Custom Exception
The Apis will throw a ResourceNotFoundException whenever a Post with a given id is not found in the database.
We use @ResponseStatus annotation in the above exception class.
This will cause Springboot to respond with the specified HTTP Status code whenever this exception is thrown from your controller.
 */

  private String resourceName;
  private String fieldName;
  private long fieldValue;

  public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
    super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));    // example message: Post not found with id : 1
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }

  public String getResourceName() {
    return resourceName;
  }

  public String getFieldName() {
    return fieldName;
  }

  public long getFieldValue() {
    return fieldValue;
  }
}
