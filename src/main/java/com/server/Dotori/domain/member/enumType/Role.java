package com.server.Dotori.domain.member.enumType;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_ADMIN, ROLE_MEMBER, ROLE_COUNCILLOR, ROLE_DEVELOPER;

    public String getAuthority() {
        return name();
    }
}
