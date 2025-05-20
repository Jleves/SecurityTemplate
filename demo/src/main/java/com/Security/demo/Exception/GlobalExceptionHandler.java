package com.Security.demo.Exception;

import com.Security.demo.Exception.DTO.ErrorResponse;
import com.Security.demo.Exception.JWT.CustomExpiredJwtException;
import com.Security.demo.Exception.JWT.CustomInvalidJwtException;
import com.Security.demo.Exception.JWT.InvalidCredentialsException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.time.LocalDate;
@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDate.now(),
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(CustomExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDate.now(),
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED) ;
    }

    @ExceptionHandler({CustomInvalidJwtException.class, SignatureException.class})
    public ResponseEntity<ErrorResponse> handleInvalidJwtException(Exception ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDate.now(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST) ;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex , HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDate.now(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR) ;


    }
}
