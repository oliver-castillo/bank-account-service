package com.bank.accountservice.model.dto.response;

import com.bank.accountservice.util.ResponseMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class OperationResponse {
    private String message;
    private String status;
    private Integer code;
    private LocalDateTime timestamp;

    public OperationResponse(ResponseMessage responseMessage, HttpStatus httpStatus) {
        this.message = responseMessage.getMessage();
        this.status = httpStatus.getReasonPhrase();
        this.code = httpStatus.value();
        this.timestamp = LocalDateTime.now();
    }

    public OperationResponse(String message, HttpStatus httpStatus) {
        this.message = message;
        this.status = httpStatus.getReasonPhrase();
        this.code = httpStatus.value();
        this.timestamp = LocalDateTime.now();
    }
}
