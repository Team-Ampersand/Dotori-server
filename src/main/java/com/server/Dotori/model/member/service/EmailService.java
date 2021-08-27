package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.dto.EmailDto;
import com.server.Dotori.model.member.dto.MemberEmailKeyDto;

public interface EmailService {
    String authKey(EmailDto emailDto);
    String authCheck(MemberEmailKeyDto memberEmailKeyDto);
    String authPassword(EmailDto emailDto);
}
