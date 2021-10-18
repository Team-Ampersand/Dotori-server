package com.server.Dotori.exception.music.exception;

public class MusicCantRequestDate extends RuntimeException {
    public MusicCantRequestDate(String msg, Throwable t) {
        super(msg, t);
    }
    public MusicCantRequestDate(String msg) {
        super(msg);
    }
    public MusicCantRequestDate() {
        super();
    }
}
