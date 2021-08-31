package com.server.Dotori.model.member.service.email;

import com.server.Dotori.model.member.dto.EmailDto;

public interface EmailSendService {
    void sendEmail(String userEmail,String key);
    void sendPasswordEmail(String userEmail, String password);
}
