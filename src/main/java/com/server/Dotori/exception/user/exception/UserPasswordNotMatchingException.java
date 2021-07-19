package com.server.Dotori.exception.user.exception;

public class UserPasswordNotMatchingException extends RuntimeException{

    public UserPasswordNotMatchingException(String msg, Throwable t){
        super(msg, t);
    }
    public UserPasswordNotMatchingException(String msg){
        super(msg);
    }
    public UserPasswordNotMatchingException(){
        super();
    }
}