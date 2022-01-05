package com.server.Dotori.exception.music.exception;

public class MusicNotRequestOnThatDateException extends RuntimeException{
    public MusicNotRequestOnThatDateException(String msg, Throwable t) {
        super(msg, t);
    }

    public MusicNotRequestOnThatDateException(String msg) {
        super(msg);
    }

    public MusicNotRequestOnThatDateException() {
        super();
    }
}
