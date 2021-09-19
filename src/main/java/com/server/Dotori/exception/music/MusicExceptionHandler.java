package com.server.Dotori.exception.music;

import com.server.Dotori.exception.music.exception.MusicAlreadyException;
import com.server.Dotori.exception.music.exception.MusicNotAppliedException;
import com.server.Dotori.exception.music.exception.MusicNotFoundException;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface MusicExceptionHandler {

    String MUSIC_ALREADY = "music-already";
    String MUSIC_NOT_APPLIED = "music-not-applied";
    String MUSIC_NOT_FOUND = "music-not-found";

    @ExceptionHandler(MusicAlreadyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult musicAlreadyException(MusicAlreadyException ex);

    @ExceptionHandler(MusicNotAppliedException.class)
    @ResponseStatus(HttpStatus.OK)
    CommonResult musicNotAppliedException(MusicNotAppliedException ex);

    @ExceptionHandler(MusicNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    CommonResult musicNotFoundException(MusicNotFoundException ex);
}
