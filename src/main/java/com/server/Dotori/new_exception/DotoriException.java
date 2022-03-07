package com.server.Dotori.new_exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DotoriException extends RuntimeException{

    private final ErrorCode errorCode;
}
