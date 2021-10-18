package com.server.Dotori.exception.music.exception;

public class MusicCantRequestDateException extends RuntimeException {
    public MusicCantRequestDateException(String msg, Throwable t) {
        super(msg, t);
    }
    public MusicCantRequestDateException(String msg) {
        super(msg);
    }
    public MusicCantRequestDateException() {
        super();
    }
}
