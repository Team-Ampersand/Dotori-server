package com.server.Dotori.exception.music;

import com.server.Dotori.exception.music.exception.MusicAlreadyException;
import com.server.Dotori.exception.music.exception.MusicCantRequestDate;
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
    String MUSIC_CANT_REQUEST_DATE = "music-cant-request-date";

    @ExceptionHandler(MusicAlreadyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult musicAlreadyException(MusicAlreadyException ex);

    @ExceptionHandler(MusicNotAppliedException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult musicNotAppliedException(MusicNotAppliedException ex);

    @ExceptionHandler(MusicNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult musicNotFoundException(MusicNotFoundException ex);

    @ExceptionHandler(MusicCantRequestDate.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult musicCantRequestDate(MusicCantRequestDate ex);
}
