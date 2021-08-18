package com.payarc.rs2.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RS2ExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	@ResponseBody
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		
		/*
		 * List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		 * List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream().map(error
		 * -> new FieldErrorMessage(error.getField(),
		 * error.getDefaultMessage())).collect(Collectors.toList()); return
		 * fieldErrorMessages;
		 */
		//return ResponseEntity.unprocessableEntity().body(fieldErrorMessages);
		
		//List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream().map(error
			//	  -> new FieldErrorMessage(error.getField(),
				//  error.getDefaultMessage())).collect(Collectors.toList());
		//return new ResponseEntity<>(fieldErrorMessages, HttpStatus.UNPROCESSABLE_ENTITY);
		
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		List<ApiSubError> apiSubErrors = fieldErrors.stream().map(fieldError -> new ApiValidationError(fieldError.getObjectName(), fieldError.getField(),fieldError.getRejectedValue(),fieldError.getDefaultMessage())).collect(Collectors.toList());
		ApiError error = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid API Input", ex, apiSubErrors);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	
}
