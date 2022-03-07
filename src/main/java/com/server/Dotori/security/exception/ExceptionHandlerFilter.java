package com.server.Dotori.security.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.Dotori.exception.token.TokenExceptionHandler;
import com.server.Dotori.exception.token.exception.InvalidTokenException;
import com.server.Dotori.new_exception.DotoriException;
import com.server.Dotori.new_exception.ErrorCode;
import com.server.Dotori.new_exception.ErrorResponse;
import com.server.Dotori.new_exception.handler.UnknownExceptionHandler;
import com.server.Dotori.response.result.CommonResult;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.server.Dotori.new_exception.ErrorCode.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final UnknownExceptionHandler unknownExceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            log.debug("================= [ ExceptionHandlerFilter ] 에서 ExpiredJwtException 발생 ===================");
            setErrorResponse(TOKEN_EXPIRED, response);
        } catch (JwtException | IllegalArgumentException ex) {
            log.debug("================= [ ExceptionHandlerFilter ] 에서 JwtException 발생 ===================");
            setErrorResponse(TOKEN_INVALID, response);
        } catch (Exception ex) {
            log.debug("================= [ ExceptionHandlerFilter ] 에서 Exception 발생 ===================");
            setErrorResponse(UNKNOWN_ERROR, response);
        }
    }

    public void setErrorResponse(ErrorCode errorCode, HttpServletResponse response) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json; charset=utf-8");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .error(errorCode.getHttpStatus().name())
                .message(errorCode.getMessage())
                .build();
        String errorResponseEntityToJson = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(errorResponseEntityToJson.toString());
    }

}