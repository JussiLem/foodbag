package me.foodbag.hello.web.controller;

import lombok.extern.slf4j.Slf4j;
import me.foodbag.hello.web.exception.InvalidOldPasswordException;
import me.foodbag.hello.web.exception.UserAlreadyExistException;
import me.foodbag.hello.web.exception.UserNotFoundException;
import me.foodbag.hello.web.util.GenericResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Foodbags exception handling logic is done through here ControllerAdvice deals with the exceptions
 * thrown by the application
 */
@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired private MessageSource messages;

  public RestResponseEntityExceptionHandler() {
    super();
  }

  /**
   * Is thrown when the UserDto validated(if invalid). Override the default
   *
   * @param ex cannot hold null value
   * @param headers cannot hold null value
   * @param status cannot hold null value
   * @param request cannot hold null value
   * @return return value from a @Controller method
   */
  // 400
  @NotNull
  @Override
  protected ResponseEntity<Object> handleBindException(
      @NotNull final BindException ex,
      @NotNull final HttpHeaders headers,
      @NotNull final HttpStatus status,
      @NotNull final WebRequest request) {
    log.error("400 Status Code", ex);
    final BindingResult result = ex.getBindingResult();
    final GenericResponse bodyOfResponse =
        new GenericResponse(result.getAllErrors(), "Invalid" + result.getObjectName());
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @NotNull
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      @NotNull MethodArgumentNotValidException ex,
      @NotNull HttpHeaders headers,
      @NotNull HttpStatus status,
      @NotNull WebRequest request) {
    log.error("400 Status Code", ex);
    final BindingResult result = ex.getBindingResult();
    final GenericResponse bodyOfResponse =
        new GenericResponse(result.getAllErrors(), "Invalid" + result.getObjectName());
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @NotNull
  @ExceptionHandler({InvalidOldPasswordException.class})
  public ResponseEntity<Object> handleInvalidOldPassword(
          @NotNull final RuntimeException ex, @NotNull final WebRequest request) {
    log.error("400 Status Code", ex);
    final GenericResponse bodyOfResponse =
        new GenericResponse(
            messages.getMessage("message.invalidOldPassword", null, request.getLocale()),
            "InvalidOldPassword");
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  // 404
  @NotNull
  @ExceptionHandler({UserNotFoundException.class})
  public ResponseEntity<Object> handleUserNotFound(
          @NotNull final RuntimeException ex, @NotNull final WebRequest request) {
    log.error("404 Status Code", ex);
    final GenericResponse bodyOfResponse =
        new GenericResponse(
            messages.getMessage("message.userNotFound", null, request.getLocale()), "UserNotFound");
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  /**
   * Is thrown when the user to register with an email that already exists:
   *
   * @param ex not null value
   * @param request not null value
   * @return return value from a @Controller method
   */
  // 409
  @NotNull
  @ExceptionHandler({UserAlreadyExistException.class})
  public ResponseEntity<Object> handleUserAlreadyExist(
          @NotNull final RuntimeException ex, @NotNull final WebRequest request) {
    log.error("409 Status Code", ex);
    final GenericResponse bodyOfResponse =
        new GenericResponse(
            messages.getMessage("message.regError", null, request.getLocale()), "UserAlreadyExist");
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @NotNull
  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleInternal(
          final RuntimeException ex, @NotNull final WebRequest request) {
    log.error("500 Status Code", ex);
    final GenericResponse bodyOfResponse =
        new GenericResponse(
            messages.getMessage("message.error", null, request.getLocale()), "InternalError");
    return new ResponseEntity<>(
        bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // 500
  @NotNull
  @ExceptionHandler({ MailAuthenticationException.class })
  public ResponseEntity<Object> handleMail(final RuntimeException ex, final WebRequest request) {
    logger.error("500 Status Code", ex);
    final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.email.config.error", null, request.getLocale()), "MailError");
    return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
