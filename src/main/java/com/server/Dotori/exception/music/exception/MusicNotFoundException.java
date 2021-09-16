package com.server.Dotori.exception.music.exception;

public class MusicNotFoundException extends RuntimeException {
    public MusicNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
    public MusicNotFoundException(String msg) {
        super(msg);
    }
    public MusicNotFoundException() {
        super();
    }
}
