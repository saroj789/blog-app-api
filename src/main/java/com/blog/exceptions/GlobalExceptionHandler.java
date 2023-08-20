package com.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.payloads.ApiResponse;

//@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
		Map<String,String> resp = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach( (error) ->{
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST) ;
		
	}
	
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String,String>> HttpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex){
		Map<String,String> resp = new HashMap<>();
		
		resp.put("message", ex.getLocalizedMessage());
		resp.put("success", "false");
		/*ex.getBindingResult().getAllErrors().forEach( (error) ->{
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		*/
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST) ;
		
	}

}
