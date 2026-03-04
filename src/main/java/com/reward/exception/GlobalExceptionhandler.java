package com.reward.exception;

import com.reward.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionhandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionhandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(
            UserNotFoundException ex,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorResponse response = getErrorResponse(status, ex.getMessage(), request);

        log.warn("User Not Found Exception: {}", ex.getMessage());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(NoTransactionFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoTransactionFoundException(
            NoTransactionFoundException ex,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        ApiErrorResponse response = getErrorResponse(status, ex.getMessage(), request);

        log.warn("No Transaction Found Exception: {}", ex.getMessage());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiErrorResponse response = getErrorResponse(status, "Invalid Parameter", request);

        if (ex.getRequiredType() == LocalDate.class) {
            log.warn("Invalid Date Format, Invalid Date Format, Date must be in ISO format yyyy-MM-dd");
            return ResponseEntity.badRequest()
                    .body(getErrorResponse(status, "Invalid Date Format, Date must be in ISO format yyyy-MM-dd", request));
        }

        log.warn("Invalid Parameter: {}", ex.getMessage());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ApiErrorResponse> handInvalidDateRangeException(
            InvalidDateRangeException ex,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiErrorResponse response = getErrorResponse(status, ex.getMessage(), request);

        log.warn("Invalid Date Range Exception: {}", ex.getMessage());
        return new ResponseEntity<>(response, status);
    }

    private ApiErrorResponse getErrorResponse(HttpStatus status, String message, HttpServletRequest request) {
        LocalDateTime timestamp = LocalDateTime.now();

        String path = request.getRequestURI();
        if (request.getQueryString() != null) {
            path += "?" + request.getQueryString();
        }

        return new ApiErrorResponse(timestamp, message, status.value(), path);
    }

}