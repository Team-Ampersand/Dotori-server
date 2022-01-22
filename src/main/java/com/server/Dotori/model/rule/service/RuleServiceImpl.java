package com.server.Dotori.model.rule.service;

import com.server.Dotori.exception.member.exception.MemberNotFoundException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.model.rule.RuleViolation;
import com.server.Dotori.model.rule.dto.FindRulesAndDatesDto;
import com.server.Dotori.model.rule.dto.RuleGrantDto;
import com.server.Dotori.model.rule.dto.RulesCntAndDatesDto;
import com.server.Dotori.model.rule.enumType.Rule;
import com.server.Dotori.model.rule.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class RuleServiceImpl implements RuleService{

    private final MemberRepository memberRepository;
    private final RuleRepository ruleRepository;

    @Override
    public void grant(RuleGrantDto ruleGrantDto) {
        List<RuleViolation> ruleViolations = new ArrayList<>();

        for (String stuNum : ruleGrantDto.getStuNum()){
            Member member = memberRepository.findByStuNum(stuNum).orElseThrow(() -> new MemberNotFoundException());
            ruleViolations.add(ruleGrantDto.toEntity(member,ruleGrantDto.getRule()));
        }

        for (RuleViolation ruleViolation : ruleViolations) {
            ruleRepository.save(ruleViolation);
        }
    }

    @Override
    public HashMap<Rule, RulesCntAndDatesDto> findAllViolationOfTheRules(String stuNum) {
        HashMap<Rule, RulesCntAndDatesDto> result = new LinkedHashMap<>();
        List<FindRulesAndDatesDto> findRulesAndDatesDto = ruleRepository.findViolationOfTheRules(stuNum);

        int cnt = 0;
        for (Rule rule : Rule.values()) {
            List<String> localDateTime = new LinkedList<>();
            for (int i = 0; i < findRulesAndDatesDto.size(); i++) { // 3
                if(findRulesAndDatesDto.get(i).getRules().equals(rule)){
                    localDateTime.add(findRulesAndDatesDto.get(i).getDates().toString().substring(0,10));
                    cnt++;
                }
            }
            RulesCntAndDatesDto rulesCntAndDatesDto = new RulesCntAndDatesDto(cnt, localDateTime);
            result.put(rule,rulesCntAndDatesDto);
            cnt = 0;
        }

        return result;
    }

}
