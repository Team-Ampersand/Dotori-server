package com.server.Dotori.global.exception;

import com.server.Dotori.global.util.CurrentMemberUtil;
import lombok.Getter;

@Getter
public class DotoriException extends RuntimeException{

    private final ErrorCode errorCode;
    private final String errorBy;

    public DotoriException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorBy = CurrentMemberUtil.getMemberName();
    }
}
