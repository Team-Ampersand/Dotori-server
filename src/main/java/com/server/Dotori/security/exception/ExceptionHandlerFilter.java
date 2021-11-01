package com.server.Dotori.security.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.Dotori.exception.token.TokenExceptionHandler;
import com.server.Dotori.exception.token.exception.InvalidTokenException;
import com.server.Dotori.exception.unknown_exception.UnknownExceptionHandler;
import com.server.Dotori.response.result.CommonResult;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final TokenExceptionHandler tokenExceptionHandler;
    private final UnknownExceptionHandler unknownExceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        }
        catch (ExpiredJwtException ex){
            log.debug("================= [ ExceptionHandlerFilter ] 에서 ExpiredJwtException 발생 ===================");
            setErrorResponse(HttpStatus.UNAUTHORIZED,response,tokenExceptionHandler.expiredJwtException(ex));
        }
        catch (JwtException | IllegalArgumentException ex) {
            log.debug("================= [ ExceptionHandlerFilter ] 에서 JwtException 발생 ===================");
            setErrorResponse(HttpStatus.UNAUTHORIZED,response,tokenExceptionHandler.invalidTokenException(new InvalidTokenException()));
        }
        catch (Exception ex){
            log.debug("================= [ ExceptionHandlerFilter ] 에서 Exception 발생 ===================");
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,response,unknownExceptionHandler.defaultException(ex));
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, CommonResult commonResult) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(throwableResultToJson(commonResult));
    }

    private String throwableResultToJson(CommonResult ex) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(ex);
    }
}
