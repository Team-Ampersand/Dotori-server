package com.server.Dotori.exception.member.exception;

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