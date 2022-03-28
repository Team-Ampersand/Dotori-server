package com.server.Dotori.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // *** SERVER ERROR ***
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러입니다."),


    // *** MASSAGE ***
    MASSAGE_ALREADY(HttpStatus.CONFLICT, "이미 안마의자 신청을 신청하신 회원입니다."),
    MASSAGE_OVER(HttpStatus.CONFLICT, "안마의자 신청 인원이 5명을 초과 하였습니다."),
    MASSAGE_CANT_REQUEST_THIS_TIME(HttpStatus.ACCEPTED, "안마의자 신청은 오후 8시부터 오후 10시까지만 신청이 가능합니다."),
    MASSAGE_CANT_REQUEST_THIS_DATE(HttpStatus.ACCEPTED, "안마의자 신청을 하실 수 없는 요일입니다."),
    MASSAGE_ANYONE_NOT_REQUEST(HttpStatus.ACCEPTED, "안마의자를 신청한 학생이 없습니다"),
    MASSAGE_CANT_CANCEL_REQUEST(HttpStatus.ACCEPTED, "안마의자 신청을 취소할 수 있는 상태가 아닙니다."),


    // *** BOARD ***
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다."),
    BOARD_NOT_HAVE_PERMISSION_TO_CREATE(HttpStatus.FORBIDDEN, "해당 게시물 생성 권한이 없습니다."),
    BOARD_NOT_HAVE_PERMISSION_TO_MODIFY(HttpStatus.FORBIDDEN, "해당 게시물 수정 권한이 없습니다."),
    BOARD_NOT_HAVE_PERMISSION_TO_DELETE(HttpStatus.FORBIDDEN, "해당 게시물 생성 권한이 없습니다."),
    BOARD_EMPTY(HttpStatus.ACCEPTED, "공지사항이 비어있습니다."),


    // *** MEMBER ***
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    MEMBER_ALREADY(HttpStatus.CONFLICT, "이미 가입된 유저입니다."),
    MEMBER_PASSWORD_NOT_MATCHING(HttpStatus.CONFLICT, "비밀번호가 올바르지 않습니다."),
    MEMBER_AUTHENTICATION_KEY_NOT_MATCHING(HttpStatus.CONFLICT, "인증 키가 일치하지 않습니다."),
    MEMBER_ALREADY_JOIN_THIS_STUNUM(HttpStatus.CONFLICT, "이미 존재하는 학번입니다."),
    MEMBER_NOT_FOUND_BY_CLASS(HttpStatus.ACCEPTED, "해당반에 해당하는 학생들이 없습니다."),
    MEMBER_NO_INFORMATION(HttpStatus.ACCEPTED, "회원 정보가 없습니다."),
    MEMBER_OVER_CERTIFICATE_TIME(HttpStatus.ACCEPTED, "인증 시간이 초과되었습니다."),
    MEMBER_EMAIL_HAS_NOT_BEEN_CERTIFICATE(HttpStatus.ACCEPTED, "이메일 인증이 되지않았습니다."),


    // *** SELF STUDY ***
    SELF_STUDY_ALREADY(HttpStatus.CONFLICT, "이미 자습신청을 하신 상태입니다."),
    SELF_STUDY_OVER(HttpStatus.CONFLICT, "자습신청 인원이 50명을 초과 하였습니다."),
    SELF_STUDY_NOT_FOUND(HttpStatus.ACCEPTED, "자습신청한 학생이 없습니다."),
    SELF_STUDY_CANT_CANCEL(HttpStatus.ACCEPTED, "자습신청을 취소할 수 있는 상태가 아닙니다."),
    SELF_STUDY_CANT_REQUEST_DATE(HttpStatus.ACCEPTED, "자습신청을 하실 수 없는 요일입니다."),
    SELF_STUDY_CANT_REQUEST_TIME(HttpStatus.ACCEPTED, "자습신청은 오후 8시부터 오후 10시까지만 가능합니다."),
    SELF_STUDY_CANT_CANCEL_DATE(HttpStatus.ACCEPTED, "자습신청을 취소 하실 수 없는 요일입니다."),
    SELF_STUDY_CANT_CANCEL_TIME(HttpStatus.ACCEPTED, "자습신청은 오후 8시부터 오후 10시까지만 취소가 가능합니다."),


    // *** MUSIC ***
    MUSIC_ALREADY(HttpStatus.CONFLICT, "이미 음악을 신청하신 회원입니다."),
    MUSIC_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 신청된 음악을 찾을 수 없습니다."),
    MUSIC_NOT_REQUESTED(HttpStatus.ACCEPTED, "신청된 음악이 없습니다."),
    MUSIC_CANT_REQUEST_DATE(HttpStatus.ACCEPTED, "음악신청을 하실 수 없는 요일입니다."),
    MUSIC_TODAY_NOT_REQUESTED(HttpStatus.ACCEPTED, "오늘 신청된 음악이 없습니다."),
    MUSIC_NOT_REQUEST_ON_THAT_DATE(HttpStatus.ACCEPTED, "해당 날짜에 신청된 노래가 없습니다."),


    // *** TOKEN ***
    TOKEN_INVALID(HttpStatus.FORBIDDEN, "변질된 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_REFRESH_FAIL(HttpStatus.BAD_REQUEST, "토큰 재발급에 실패했습니다."),


    // *** RULE ***
    RULE_NO_HISTORY(HttpStatus.ACCEPTED, "규정위반 내역이 없습니다."),
    ;


    private final HttpStatus httpStatus;
    private String message;

}
