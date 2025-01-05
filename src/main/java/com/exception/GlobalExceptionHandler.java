package com.exception;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex) {
        Response response = new Response(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
    	
    	System.out.println(ex);
        Response response = new Response("SERVER_ERROR", "An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleForbiddenException(AccessDeniedException ex) {
        System.out.println(ex);
        Response response = new Response("FORBIDDEN", "You do not have permission to access this resource.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(response);
    }
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Object> handleUnauthorizedException(AuthenticationCredentialsNotFoundException ex) {
        System.out.println(ex);
        Response response = new Response("UNAUTHORIZED", "Authentication required or invalid credentials.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }
 
    // Not Found Exception Handler (404)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleNotFoundException(ResponseStatusException ex) {
        System.out.println(ex);
        Response response = new Response("NOT_FOUND", "The resource you are looking for was not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        System.out.println(ex);
        Response response = new Response("NOT_FOUND", "The resource you are looking for was not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        System.out.println(ex);
        Response response = new Response("BAD_REQUEST", "Invalid argument provided.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
  
    
}