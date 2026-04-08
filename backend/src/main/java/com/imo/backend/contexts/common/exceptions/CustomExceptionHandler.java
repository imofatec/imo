package com.imo.backend.contexts.common.exceptions;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.common.exceptions.custom.ForbiddenException;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    String message =
        Optional.ofNullable(ex.getBindingResult().getFieldError())
            .map(FieldError::getDefaultMessage)
            .orElse("Erro de validação");

    return new ResponseEntity<>(
        new ErrorResponseDto("VALIDATION_ERROR", message), HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    log.warn("JSON malformado em {}", request.getDescription(false));
    return new ResponseEntity<>(
        new ErrorResponseDto("VALIDATION_ERROR", "Argumento inválido"), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
    return new ResponseEntity<>(
        new ErrorResponseDto("BAD_REQUEST", ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ForbiddenException.class)
  public final ResponseEntity<Object> handleForbiddenException(ForbiddenException ex) {
    return new ResponseEntity<>(
        new ErrorResponseDto("FORBIDDEN", ex.getMessage()), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
    return new ResponseEntity<>(
        new ErrorResponseDto("NOT_FOUND", ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ConflictException.class)
  public final ResponseEntity<Object> handleConflictException(ConflictException ex) {
    return new ResponseEntity<>(
        new ErrorResponseDto("CONFLICT", ex.getMessage()), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    log.error("Erro não tratado em {}", request.getDescription(false), ex);
    return new ResponseEntity<>(
        new ErrorResponseDto("INTERNAL_ERROR", "Erro interno do servidor"),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
