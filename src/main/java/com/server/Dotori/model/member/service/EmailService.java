package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.dto.MemberEmailDto;
import com.server.Dotori.model.member.dto.MemberEmailKeyDto;

public interface EmailService {
    String auth(MemberEmailDto memberEmailDto);
    String authCheck(MemberEmailKeyDto memberEmailKeyDto);
}
