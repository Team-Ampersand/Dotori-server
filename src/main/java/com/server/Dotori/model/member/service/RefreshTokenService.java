package com.server.Dotori.model.member.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RefreshTokenService {
    Map<String,String> getRefreshToken(HttpServletRequest request);
}
