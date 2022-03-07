package com.server.Dotori.new_exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // *** UNKNOWN-ERROR 500 ***
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "UNKNOWN_ERROR", ErrorRole.SERVER+"-ERROR-500"),


    // *** CONFLICT 409 ***
    MASSAGE_ALREADY(HttpStatus.CONFLICT, "이미 안마의자 신청을 신청하신 회원입니다.", ErrorRole.STUDENT+"-ERROR-409"),
    MASSAGE_OVER(HttpStatus.CONFLICT, "안마의자 신청 인원이 5명을 초과 하였습니다.", ErrorRole.STUDENT+"-ERROR-409"),

    // *** ACCEPT 202 ***
    MASSAGE_CANT_REQUEST_THIS_TIME(HttpStatus.ACCEPTED, "안마의자 신청은 오후 8시부터 오후 10시까지만 신청이 가능합니다.", ErrorRole.STUDENT+"-ERROR-202"),
    MASSAGE_CANT_REQUEST_THIS_DATE(HttpStatus.ACCEPTED, "안마의자 신청을 하실 수 없는 요일입니다.", ErrorRole.STUDENT+"-ERROR-202"),
    MASSAGE_ANYONE_NOT_REQUEST(HttpStatus.ACCEPTED, "안마의자를 신청한 학생이 없습니다", ErrorRole.ALL+"-ERROR-202"),
    MASSAGE_CANT_CANCEL_REQUEST(HttpStatus.ACCEPTED, "안마의자 신청을 취소할 수 있는 상태가 아닙니다.", ErrorRole.STUDENT+"-ERROR-202"),
    ;


    private final HttpStatus httpStatus;
    private String message;
    private String details;

}
