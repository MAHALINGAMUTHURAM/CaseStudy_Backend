package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice(basePackages = "com.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(ex.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( Exception.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(ex.getMessage());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

class ErrorResponse {
    private String error;
    private int status;
    private String path;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

    
}