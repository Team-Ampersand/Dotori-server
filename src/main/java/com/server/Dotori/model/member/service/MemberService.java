package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;

public interface MemberService {
    Long signup(MemberDto memberDto);
}
