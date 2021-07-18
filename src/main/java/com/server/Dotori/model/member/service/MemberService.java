package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.dto.MemberLoginDto;

import java.util.Map;

public interface MemberService {
    Long signup(MemberDto memberDto);
    Map<String,String> signin(MemberLoginDto memberLoginDto);
}
