package com.server.Dotori.model.member.service.email;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.AuthPasswordDto;
import com.server.Dotori.model.member.dto.EmailDto;
import com.server.Dotori.model.member.dto.MemberEmailKeyDto;

public interface EmailService {
    String authKey(EmailDto emailDto);
    String authCheck(MemberEmailKeyDto memberEmailKeyDto);
    Member authPassword(AuthPasswordDto authPasswordDto);
}
