package com.apress.hellorest.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.apress.hellorest.dto.error.ErrorDetail;
import com.apress.hellorest.dto.error.ValidationError;
import com.apress.hellorest.exception.ResourceNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Inject
	private MessageSource messageSource;
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException
			rnfe, HttpServletRequest request ){
		
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(new Date().getTime());
		errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
		errorDetail.setTitle("RESOURCE NOT FOUND");
		errorDetail.setDetail(rnfe.getMessage());
		errorDetail.setDeveloperMessage(rnfe.getClass().getName());
		return new ResponseEntity<>(errorDetail,null,HttpStatus.NOT_FOUND);
	}
	/* au cas ou l'on etend pas ResponseEntityExceptionHandler
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException 
			          manve, HttpServletRequest request ){
		
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(new Date().getTime());
		errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
		errorDetail.setTitle("VALIDATION FAILED");
		errorDetail.setDetail("input validation failed");
		errorDetail.setDeveloperMessage(manve.getClass().getName());
		
		List<FieldError> fieldErrors = manve.getBindingResult().getFieldErrors();
		
		for(FieldError fe: fieldErrors ){
			List<ValidationError> validationErrorsList = errorDetail.getErrors().get(fe.getField());
			if(validationErrorsList==null){
				validationErrorsList = new ArrayList<ValidationError>();
				errorDetail.getErrors().put(fe.getField(), validationErrorsList);
			}
			ValidationError validationError = new ValidationError();
			validationError.setCode(fe.getCode());
			//validationError.setMessage(fe.getDefaultMessage());
			validationError.setMessage(messageSource.getMessage(fe, null));
			validationErrorsList.add(validationError);
		}
		return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
	}
	*/
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(new Date().getTime());
		errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
		errorDetail.setTitle("VALIDATION FAILED");
		errorDetail.setDetail("input validation failed");
		errorDetail.setDeveloperMessage(ex.getClass().getName());
		
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		
		for(FieldError fe: fieldErrors ){
			List<ValidationError> validationErrorsList = errorDetail.getErrors().get(fe.getField());
			if(validationErrorsList==null){
				validationErrorsList = new ArrayList<ValidationError>();
				errorDetail.getErrors().put(fe.getField(), validationErrorsList);
			}
			ValidationError validationError = new ValidationError();
			validationError.setCode(fe.getCode());
			//validationError.setMessage(fe.getDefaultMessage());
			validationError.setMessage(messageSource.getMessage(fe, null));
			validationErrorsList.add(validationError);
		}
		
		
		return handleExceptionInternal(ex, errorDetail, headers, status, request);
	}
	
	

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(new Date().getTime());
		errorDetail.setStatus(status.value());
		errorDetail.setTitle("Message illisible");
		errorDetail.setDetail(ex.getMessage());
		errorDetail.setDeveloperMessage(ex.getClass().getName());
		
		return handleExceptionInternal(ex, errorDetail, headers, status, request);
	}

}
