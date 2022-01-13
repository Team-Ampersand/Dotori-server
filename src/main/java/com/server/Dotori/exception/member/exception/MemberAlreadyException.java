package com.server.Dotori.exception.member.exception;

public class UserAlreadyException extends RuntimeException {

    public UserAlreadyException(String msg, Throwable t){
        super(msg, t);
    }
    public UserAlreadyException(String msg){
        super(msg);
    }
    public UserAlreadyException(){
        super();
    }
}
