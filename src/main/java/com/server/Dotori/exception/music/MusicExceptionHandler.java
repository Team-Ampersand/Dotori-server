package com.server.Dotori.exception.music;

import com.server.Dotori.exception.music.exception.MusicAlreadyException;
import com.server.Dotori.exception.music.exception.MusicNotAppliedException;
import com.server.Dotori.exception.music.exception.MusicNotFoundException;
import com.server.Dotori.exception.token.exception.InvalidTokenException;
import com.server.Dotori.exception.token.exception.LogoutTokenException;
import com.server.Dotori.exception.token.exception.RefreshTokenFailException;
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
    CommonResult musicAlreadyException(InvalidTokenException ex);

    @ExceptionHandler(MusicNotAppliedException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    CommonResult musicNotAppliedException(LogoutTokenException ex);

    @ExceptionHandler(MusicNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult musicNotFoundException(RefreshTokenFailException ex);
}
