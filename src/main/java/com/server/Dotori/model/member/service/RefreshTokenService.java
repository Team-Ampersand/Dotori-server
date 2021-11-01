package com.server.Dotori.model.member.service;

import java.util.Map;

public interface RefreshTokenService {
    Map<String,String> getRefreshToken(String username, String token);
}
