package com.server.Dotori.new_exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    private final LocalDateTime timeStamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .message(errorCode.getMessage())
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(HttpStatus httpStatus, String message) {
        return ResponseEntity
                .status(httpStatus)
                .body(ErrorResponse.builder()
                        .status(httpStatus.value())
                        .error(httpStatus.name())
                        .message(message)
                        .build()
                );
    }


}
