package com.server.Dotori.exception.member.exception;

public class MemberAlreadyException extends RuntimeException {

    public MemberAlreadyException(String msg, Throwable t){
        super(msg, t);
    }
    public MemberAlreadyException(String msg){
        super(msg);
    }
    public MemberAlreadyException(){
        super();
    }
}