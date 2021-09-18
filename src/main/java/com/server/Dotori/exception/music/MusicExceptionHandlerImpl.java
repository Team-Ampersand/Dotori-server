package com.server.Dotori.exception.music;

import com.server.Dotori.exception.music.exception.MusicAlreadyException;
import com.server.Dotori.exception.music.exception.MusicNotAppliedException;
import com.server.Dotori.exception.music.exception.MusicNotFoundException;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.util.ExceptionResponseObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MusicExceptionHandlerImpl implements MusicExceptionHandler {

    private final ExceptionResponseObjectUtil exceptionResponseObjectUtil;

    @Override
    public CommonResult musicAlreadyException(MusicAlreadyException ex) {
        log.debug("=== Music Already Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MUSIC_ALREADY);
    }

    @Override
    public CommonResult musicNotAppliedException(MusicNotAppliedException ex) {
        log.debug("=== Music Not Applied Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MUSIC_NOT_APPLIED);
    }

    @Override
    public CommonResult musicNotFoundException(MusicNotFoundException ex) {
        log.debug("=== Music Not Found Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MUSIC_NOT_FOUND);
    }
}
