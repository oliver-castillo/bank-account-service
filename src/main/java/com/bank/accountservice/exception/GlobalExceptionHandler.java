package com.bank.accountservice.exception;

import com.bank.accountservice.model.dto.response.OperationResponse;
import com.bank.accountservice.util.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyExistsException.class)
    public Mono<ResponseEntity<OperationResponse>> handleAlreadyExistsException(AlreadyExistsException e) {
        log.error("Exception occurred: {}", e.getMessage());
        return Mono.just(new ResponseEntity<>(new OperationResponse(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<OperationResponse>> handleNotFoundException(NotFoundException e) {
        return Mono.just(new ResponseEntity<>(new OperationResponse(e.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(BadRequestException.class)
    public Mono<ResponseEntity<OperationResponse>> handleBadRequestException(BadRequestException e) {
        return Mono.just(new ResponseEntity<>(new OperationResponse(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(value = {WebExchangeBindException.class})
    public Mono<ResponseEntity<Map<String, Object>>> handleMethodArgumentNotValidException(WebExchangeBindException e) {
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            log.error("Field {}: {}", fieldName, errorMessage);
        });
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.put("code", HttpStatus.BAD_REQUEST.value());
        response.put("message", ResponseMessage.ARGUMENT_NOT_VALID.getMessage());
        response.put("timestamp", LocalDateTime.now());
        response.put("errors", errors);
        return Mono.just(new ResponseEntity<>(response, HttpStatus.BAD_REQUEST));
    }
}
