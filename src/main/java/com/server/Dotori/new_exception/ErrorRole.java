package com.server.Dotori.new_exception;

/**
 * STUDENT : 사감쌤을 제외한 Dotori 서비스를 이용하는 모든 학생
 * ALL : 사감쌤과 학생 모두를 포함
 * SERVER : 서버 에러
 */
public enum ErrorRole {
    SERVER, ALL, STUDENT
}
