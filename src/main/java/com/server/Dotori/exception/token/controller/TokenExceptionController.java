package com.server.Dotori.exception.token;

import com.server.Dotori.exception.token.exception.AccessTokenExpiredException;
import com.server.Dotori.exception.token.exception.InvalidTokenException;
import com.server.Dotori.exception.token.exception.LogoutTokenException;
import com.server.Dotori.exception.token.exception.RefreshTokenFailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class TokenExceptionController {

    @GetMapping("/access-token-expired")
    public void accessTokenExpiredException(){
        throw new AccessTokenExpiredException();
    }

    @GetMapping("/invalid-token")
    public void invalidTokenException(){
        throw new InvalidTokenException();
    }

    @GetMapping("/logout-token")
    public void logoutTokenException() {
        throw new LogoutTokenException();
    }

    @GetMapping("/refresh-token-fail")
    public void refreshTokenFailException() {
        throw new RefreshTokenFailException();
    }
}