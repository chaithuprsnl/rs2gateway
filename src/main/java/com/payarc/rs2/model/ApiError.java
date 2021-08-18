package com.payarc.rs2.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiError {
	
	private HttpStatus status;
	private String message;
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timeStamp;
	private String debugMessage;
	private List<ApiSubError> subErrors;
	
	private ApiError() {
		timeStamp = LocalDateTime.now();
	}

	public ApiError(HttpStatus status) {
		super();
		this.status = status;
	}
	
	public ApiError(HttpStatus status, Throwable ex) {
		super();
		this.status = status;
		this.debugMessage = ex.getLocalizedMessage();
	}

	public ApiError(HttpStatus status, String message, Throwable ex) {
		super();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}
	
	public ApiError(HttpStatus status, String message, Throwable ex, List<ApiSubError> subErrors) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
		this.subErrors = subErrors;
	}
}
