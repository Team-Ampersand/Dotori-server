package com.server.Dotori.model.member.service.email;

public interface EmailSendService {
    void sendEmail(String email,String key);
    void sendPasswordEmail(String email, String password);
}
