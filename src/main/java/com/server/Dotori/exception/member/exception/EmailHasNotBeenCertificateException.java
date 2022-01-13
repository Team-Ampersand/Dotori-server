package com.server.Dotori.exception.member.exception;

public class EmailHasNotBeenCertificateException extends RuntimeException {
    public EmailHasNotBeenCertificateException(String msg, Throwable t){
        super(msg, t);
    }
    public EmailHasNotBeenCertificateException(String msg){
        super(msg);
    }
    public EmailHasNotBeenCertificateException(){
        super();
    }
}
