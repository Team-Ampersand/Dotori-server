package com.server.Dotori.exception.music.exception;

public class MusicTodayNotRequestedException extends RuntimeException {
    public MusicTodayNotRequestedException(String msg, Throwable t) {
        super(msg, t);
    }
    public MusicTodayNotRequestedException(String msg) {
        super(msg);
    }
    public MusicTodayNotRequestedException() {
        super();
    }
}