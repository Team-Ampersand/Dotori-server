package com.server.Dotori.exception.user.exception;

public class EmailHasNotBeenCertificate extends RuntimeException {
    public EmailHasNotBeenCertificate(String msg, Throwable t){
        super(msg, t);
    }
    public EmailHasNotBeenCertificate(String msg){
        super(msg);
    }
    public EmailHasNotBeenCertificate(){
        super();
    }
}
