package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.dto.EmailDto;

public interface EmailSandService {
    void sandEmail(String userEmail,String key);
    void sandPasswordEmail(String userEmail, String password);
}
