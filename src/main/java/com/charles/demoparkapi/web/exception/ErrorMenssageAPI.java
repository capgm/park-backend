package com.charles.demoparkapi.web.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.servlet.http.HttpServletRequest;

public class ErrorMenssageAPI {
	
	private String path;
	
	private String method;
	
	private int status;
	
	private String statusText;
	
	private String message;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Map<String, String> errors;	
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	public ErrorMenssageAPI() {
		super();
	}

	public ErrorMenssageAPI(HttpServletRequest request, HttpStatus status, String message) {
		
		this.path = request.getRequestURI();
		this.method = request.getMethod();
		this.status = status.value();
		this.statusText = status.getReasonPhrase();
		this.message = message;
	}
	
	public ErrorMenssageAPI(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
		
		this.path = request.getRequestURI();
		this.method = request.getMethod();
		this.status = status.value();
		this.statusText = status.getReasonPhrase();
		this.message = message;
		addErrors(result);
	}

	private void addErrors(BindingResult result) {
		this.errors = new HashMap<>();
		
		for(FieldError fieldError : result.getFieldErrors()) {
			this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
	}

	@Override
	public String toString() {
		return "ErrorMenssage [path=" + path + ", method=" + method + ", status=" + status + ", statusText="
				+ statusText + ", message=" + message + ", errors=" + errors + "]";
	}
}
