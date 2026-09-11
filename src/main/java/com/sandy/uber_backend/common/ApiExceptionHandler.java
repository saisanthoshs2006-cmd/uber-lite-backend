package com.sandy.uber_backend.common;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sandy.uber_backend.ride.RideNotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(RideNotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(RideNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiError(Instant.now(), 404, "Not Found", exception.getMessage(), Map.of()));
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ApiError> handleInvalidState(IllegalStateException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ApiError(Instant.now(), 409, "Conflict", exception.getMessage(), Map.of()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException exception) {
		Map<String, String> fields = exception.getBindingResult().getFieldErrors().stream()
				.collect(java.util.stream.Collectors.toMap(
						error -> error.getField(),
					error -> error.getDefaultMessage(),
					(first, second) -> first));
		return ResponseEntity.badRequest()
				.body(new ApiError(Instant.now(), 400, "Bad Request", "Validation failed", fields));
	}

	public record ApiError(Instant timestamp, int status, String error, String message, Map<String, String> fields) {
	}
}
