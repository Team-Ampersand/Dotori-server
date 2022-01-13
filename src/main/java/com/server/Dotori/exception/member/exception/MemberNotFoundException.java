package com.server.Dotori.exception.member.exception;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(String msg, Throwable t){
        super(msg, t);
    }
    public MemberNotFoundException(String msg){
        super(msg);
    }
    public MemberNotFoundException(){
        super();
    }
}
