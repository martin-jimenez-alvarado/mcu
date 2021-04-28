package com.collaborators.error;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public  class ExceptionHandlers{
	private static final String BAD_REQUEST = "BAD_REQUEST";
	private LocalDateTime timestamp = LocalDateTime.now();
	
	@ExceptionHandler(WebExchangeBindException.class)
	public final ResponseEntity<ErrorResponse> handeWebExchangeBindException(WebExchangeBindException ex, ServerHttpRequest serverHttpRequest){	
		List<Map<String, String>> errors = errores(ex.getBindingResult());
		ErrorResponse error = new ErrorResponse(this.timestamp.toString(), serverHttpRequest.getPath().toString(), BAD_REQUEST, errors,  HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	public static List<Map<String, String>> errores(BindingResult result){
		return result.getFieldErrors().stream()
				.map(err -> {
					Map<String, String> error = new HashMap<>();
					error.put(err.getField(), err.getDefaultMessage());
					return error;
				}).collect(Collectors.toList());
	}
	
	
}
