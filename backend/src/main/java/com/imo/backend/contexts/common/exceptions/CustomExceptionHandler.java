package com.imo.backend.contexts.common.exceptions;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.common.exceptions.custom.ForbiddenException;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.common.exceptions.custom.UnauthorizedException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
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

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    return new ResponseEntity<>(
        new ErrorResponseDto(
            "VALIDATION_ERROR",
            String.format("O parâmetro %s é obrigatório", ex.getParameterName())),
        HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMaxUploadSizeExceededException(
      MaxUploadSizeExceededException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    return new ResponseEntity<>(
        new ErrorResponseDto(
            "PAYLOAD_TOO_LARGE", "O arquivo enviado excede o tamanho máximo permitido"),
        HttpStatus.PAYLOAD_TOO_LARGE);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public final ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex) {
    return new ResponseEntity<>(
        new ErrorResponseDto(
            "VALIDATION_ERROR", String.format("O parâmetro %s é inválido", ex.getName())),
        HttpStatus.BAD_REQUEST);
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

  @ExceptionHandler(DuplicateKeyException.class)
  public final ResponseEntity<Object> handleDuplicateKeyException(DuplicateKeyException ex) {
    return new ResponseEntity<>(
        new ErrorResponseDto("CONFLICT", this.resolveDuplicateKeyMessage(ex)), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public final ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
    return new ResponseEntity<>(
        new ErrorResponseDto("UNAUTHORIZED", ex.getMessage()), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    log.error("Erro não tratado em {}", request.getDescription(false), ex);
    return new ResponseEntity<>(
        new ErrorResponseDto("INTERNAL_ERROR", "Erro interno do servidor"),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private String resolveDuplicateKeyMessage(DuplicateKeyException ex) {
    String message =
        Optional.ofNullable(ex.getMostSpecificCause()).map(Throwable::getMessage).orElse("");

    if (message.contains("uk_users_email")) {
      return "O email já existe";
    }

    if (message.contains("uk_courses_contributor_slug")) {
      return "Já existe um curso com esse nome para este usuário";
    }

    if (message.contains("uk_lessons_course_title")) {
      return "Já existe uma aula com esse título";
    }

    if (message.contains("uk_lessons_course_youtube_link")) {
      return "Já existe uma aula com este link";
    }

    if (message.contains("uk_lessons_course_index")) {
      return "Já existe uma aula nessa posição";
    }

    return "Conflito de dados duplicados";
  }
}
