package com.server.Dotori.domain.member.service;

import java.util.Map;

public interface RefreshTokenService {
    Map<String,String> getRefreshToken(String refreshToken);
}
