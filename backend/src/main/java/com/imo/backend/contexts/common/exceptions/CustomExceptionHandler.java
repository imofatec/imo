package com.imo.backend.contexts.common.exceptions;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.common.exceptions.custom.ForbiddenException;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import java.util.List;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
  // 400
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    List<String> errorMessages =
        ex.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList();

    String errorMessage = errorMessages.get(0);
    String description = String.format("path: %s", request.getDescription(false));

    ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorMessage, description);
    System.out.println(errorMessage);
    return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    String errorMessage = "Argumento inválido";
    String description = String.format("path: %s", request.getDescription(false));

    ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorMessage, description);
    return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
  }

  // 400
  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<Object> handleBadRequestException(
      BadRequestException ex, WebRequest request) {
    String errorMessage = ex.getMessage();
    String description = String.format("path: %s", request.getDescription(false));

    ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorMessage, description);
    return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
  }

  // 403
  @ExceptionHandler(ForbiddenException.class)
  public final ResponseEntity<Object> handleForbiddenException(
      ForbiddenException ex, WebRequest request) {
    String errorMessage = ex.getMessage();
    String description = String.format("path: %s", request.getDescription(false));

    ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorMessage, description);
    return new ResponseEntity<>(errorResponseDto, HttpStatus.FORBIDDEN);
  }

  // 404
  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<Object> handleNotFoundException(
      NotFoundException ex, WebRequest request) {
    String errorMessage = ex.getMessage();
    String description = String.format("path: %s", request.getDescription(false));

    ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorMessage, description);
    return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
  }

  // 409
  @ExceptionHandler(ConflictException.class)
  public final ResponseEntity<Object> handleConflictException(
      ConflictException ex, WebRequest request) {
    String errorMessage = ex.getMessage();
    String description = String.format("path: %s", request.getDescription(false));

    ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorMessage, description);
    return new ResponseEntity<>(errorResponseDto, HttpStatus.CONFLICT);
  }
}
