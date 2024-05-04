package com.charles.demoparkapi.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.charles.demoparkapi.exception.UsernameUniqueViolationException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMenssageAPI> entityNotFoundException(RuntimeException ex, HttpServletRequest request){

		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMenssageAPI(request, HttpStatus.NOT_FOUND, ex.getMessage()));
	}
	
	
	@ExceptionHandler(UsernameUniqueViolationException.class)
	public ResponseEntity<ErrorMenssageAPI> uniqueViolationException(RuntimeException ex, HttpServletRequest request){

		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMenssageAPI(request, HttpStatus.CONFLICT, ex.getMessage()));
	}
	
	
	@ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMenssageAPI> methoArgumentsValidException(MethodArgumentNotValidException ex, HttpServletRequest request, BindingResult result){

		return ResponseEntity
				.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMenssageAPI(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) invalido(s)", result));
	}
}
