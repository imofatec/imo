package com.imo.backend.exceptions;

import com.imo.backend.exceptions.custom.EmailConflitException;
import com.imo.backend.exceptions.custom.UsernameConflitException;
import com.imo.backend.exceptions.custom.UserNotFoundException;
import com.imo.backend.exceptions.custom.WrongPasswordException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

//     400
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorMessages = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        String errorMessage = errorMessages.get(0);
        String description = String.format("path: %s", request.getDescription(false));

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorMessage, description);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

//     403
    @ExceptionHandler(WrongPasswordException.class)
    public final ResponseEntity<Object> handleWrongPasswordException(WrongPasswordException ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        String description = String.format("path: %s", request.getDescription(false));

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorMessage, description);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.FORBIDDEN);
    }

//     404
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        String description = String.format("path: %s", request.getDescription(false));

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorMessage, description);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

//    409
    @ExceptionHandler(EmailConflitException.class)
    public final ResponseEntity<Object> handleEmailAndNameConflitException(EmailConflitException ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        String description = String.format("path: %s", request.getDescription(false));

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorMessage, description);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.CONFLICT);
    }

//    409
    @ExceptionHandler(UsernameConflitException.class)
    public final ResponseEntity<Object> handleNameConflitException(UsernameConflitException ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        String description = String.format("path: %s", request.getDescription(false));

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorMessage, description);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.CONFLICT);
    }
}
