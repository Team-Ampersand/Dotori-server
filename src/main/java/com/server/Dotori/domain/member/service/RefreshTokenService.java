package com.server.Dotori.domain.member.service;

import com.server.Dotori.domain.member.dto.RefreshTokenDto;

import java.util.Map;

public interface RefreshTokenService {
    Map<String,String> refreshToken(String refreshToken, RefreshTokenDto refreshTokenDto);
}
