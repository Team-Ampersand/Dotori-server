package com.server.Dotori.exception.music;

import com.server.Dotori.exception.token.exception.InvalidTokenException;
import com.server.Dotori.exception.token.exception.LogoutTokenException;
import com.server.Dotori.exception.token.exception.RefreshTokenFailException;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.util.ExceptionResponseObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@Service
public class MusicExceptionHandlerImpl implements MusicExceptionHandler {

    private ExceptionResponseObjectUtil exceptionResponseObjectUtil;

    @Override
    public CommonResult musicAlreadyException(InvalidTokenException ex) {
        log.debug("=== Music Already Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MUSIC_ALREADY);
    }

    @Override
    public CommonResult musicNotAppliedException(LogoutTokenException ex) {
        log.debug("=== Music Not Applied Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MUSIC_NOT_APPLIED);
    }

    @Override
    public CommonResult musicNotFoundException(RefreshTokenFailException ex) {
        log.debug("=== Music Not Found Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MUSIC_NOT_FOUND);
    }
}
