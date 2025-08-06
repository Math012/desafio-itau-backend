package com.math012.desafio_itau_backend.infra.exception;

public class RequestInvalidException extends RuntimeException {
  public RequestInvalidException(String message) {
    super(message);
  }
}
