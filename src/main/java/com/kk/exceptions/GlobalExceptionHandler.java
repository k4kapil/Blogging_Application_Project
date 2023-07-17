package com.kk.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kk.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException exp){
	String message=	exp.getMessage();
	ApiResponse api= new ApiResponse(message,false);
	return new ResponseEntity<ApiResponse>(api,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException exp){
		Map<String, String> map= new HashMap<>();
		exp.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName= ((FieldError) error).getField();
			String message= error.getDefaultMessage();
			map.put(fieldName, message);
		}
	);
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> apiException(ApiException exp){
	String message=	exp.getMessage();
	ApiResponse api= new ApiResponse(message,true);
	return new ResponseEntity<ApiResponse>(api,HttpStatus.BAD_REQUEST);
	}
}
