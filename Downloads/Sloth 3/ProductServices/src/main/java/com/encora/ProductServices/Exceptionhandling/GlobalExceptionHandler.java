package com.encora.ProductServices.Exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebInputException;



public class GlobalExceptionHandler {
	 @ExceptionHandler(ProductNotFoundException.class)
	    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(InvalidProductRequestException.class)
	    public ResponseEntity<String> handleInvalidProductRequestException(InvalidProductRequestException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(ServerWebInputException.class)
	    public ResponseEntity<String> handleWebInputException(ServerWebInputException ex) {
	        return new ResponseEntity<>("Invalid request input", HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleGlobalException(Exception ex) {
	        return new ResponseEntity<>("Internal Server Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
