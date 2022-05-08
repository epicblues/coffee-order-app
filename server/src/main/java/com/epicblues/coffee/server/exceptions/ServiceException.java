package com.epicblues.coffee.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST)
public class ServiceException extends RuntimeException {

  public ServiceException(Throwable exception) {
    super(exception);
  }
}
