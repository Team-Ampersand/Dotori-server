package com.server.Dotori.security.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.Dotori.exception.token.TokenExceptionHandler;
import com.server.Dotori.response.result.CommonResult;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.persister.collection.OneToManyPersister;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        }
        catch (ExpiredJwtException ex){
            setErrorResponse(HttpStatus.UNAUTHORIZED,response,tokenExceptionHandler.expiredJwtException(ex));
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
