package com.server.Dotori.exception.member.exception;

public class MemberPasswordNotMatchingException extends RuntimeException{

    public MemberPasswordNotMatchingException(String msg, Throwable t){
        super(msg, t);
    }
    public MemberPasswordNotMatchingException(String msg){
        super(msg);
    }
    public MemberPasswordNotMatchingException(){
        super();
    }
}