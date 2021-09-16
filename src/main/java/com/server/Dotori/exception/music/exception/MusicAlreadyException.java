package com.server.Dotori.exception.music.exception;

public class MusicAlreadyException extends RuntimeException {
    public MusicAlreadyException(String msg, Throwable t) {
        super(msg, t);
    }
    public MusicAlreadyException(String msg) {
        super(msg);
    }
    public MusicAlreadyException() {
        super();
    }
}
