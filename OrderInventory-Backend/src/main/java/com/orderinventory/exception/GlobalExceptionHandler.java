package com.orderinventory.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler()
	public ResponseEntity<ExceptionResponse> handler(ProductsNotFoundException ex) {
		ExceptionResponse exception = new ExceptionResponse(ex.getMessage(), LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
				HttpStatus.NOT_FOUND);
		return response;
	}

	@ExceptionHandler()
	public ResponseEntity<ExceptionResponse> handler(CustomerNotFoundException ex) {
		ExceptionResponse exception = new ExceptionResponse(ex.getMessage(), LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
				HttpStatus.NOT_FOUND);
		return response;
	}

	@ExceptionHandler()
	public ResponseEntity<ExceptionResponse> handler(OrdersNotFoundException ex) {
		ExceptionResponse exception = new ExceptionResponse(ex.getMessage(), LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
				HttpStatus.NOT_FOUND);
		return response;

	}
	
	
	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationExc(ConstraintViolationException ex, WebRequest request) {
        ExceptionResponse errRes = new ExceptionResponse(ex.getMessage(), LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value());
        System.out.println("it is not detected");
        return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);

    }

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> validationErrs = new ArrayList<>();
		for (FieldError err : ex.getBindingResult().getFieldErrors()) {

            System.out.println("----" + err.getDefaultMessage());

            validationErrs.add(err.getDefaultMessage());

        }

        ErrorResponse errResp = new ErrorResponse("Validation Failed", validationErrs.toString());

        return new ResponseEntity<Object>(errResp, status);
	}
	
}
