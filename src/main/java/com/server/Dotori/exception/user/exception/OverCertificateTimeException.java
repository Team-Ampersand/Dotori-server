package com.server.Dotori.exception.user.exception;

public class OverCertificateTimeException extends RuntimeException {
    public OverCertificateTimeException(String msg, Throwable t){
        super(msg, t);
    }
    public OverCertificateTimeException(String msg){
        super(msg);
    }
    public OverCertificateTimeException(){
        super();
    }
}