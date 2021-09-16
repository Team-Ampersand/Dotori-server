package com.server.Dotori.exception.music.exception;

public class MusicNotAppliedException extends RuntimeException {
    public MusicNotAppliedException(String msg, Throwable t) {
        super(msg, t);
    }
    public MusicNotAppliedException(String msg) {
        super(msg);
    }
    public MusicNotAppliedException() {
        super();
    }
}
