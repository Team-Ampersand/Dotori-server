package com.server.Dotori.model.rule.service;

import com.server.Dotori.exception.member.exception.MemberNotFoundException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.model.rule.RuleViolation;
import com.server.Dotori.model.rule.dto.RuleGrantDto;
import com.server.Dotori.model.rule.repository.RuleRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RuleServiceImpl implements RuleService{

    private final MemberRepository memberRepository;
    private final RuleRepository ruleRepository;

    @Override
    public void grant(RuleGrantDto ruleGrantDto) {
        List<Member> members = new ArrayList<>();
        List<RuleViolation> ruleViolations = new ArrayList<>();

        for (Long memberId : ruleGrantDto.getMemberId()){
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId + " 번 학생을 찾을 수 없습니다."));
            members.add(member);
            ruleViolations.add(ruleGrantDto.toEntity(member,ruleGrantDto.getRule()));
        }

        for (RuleViolation ruleViolation : ruleViolations) {
            ruleRepository.save(ruleViolation);
        }
    }
}
