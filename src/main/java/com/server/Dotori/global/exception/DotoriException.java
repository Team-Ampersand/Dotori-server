package com.server.Dotori.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DotoriException extends RuntimeException{

    private final ErrorCode errorCode;
}
