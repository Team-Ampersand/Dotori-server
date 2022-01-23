package com.server.Dotori.model.rule.service;

import com.server.Dotori.exception.member.exception.MemberNotFoundException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.model.rule.RuleViolation;
import com.server.Dotori.model.rule.dto.FindIdAndRuleAndDateDto;
import com.server.Dotori.model.rule.dto.FindRuleAndDateDto;
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
    public HashMap<Rule, RulesCntAndDatesDto> findAllViolationOfTheRule(String stuNum) {
        HashMap<Rule, RulesCntAndDatesDto> response = new LinkedHashMap<>();
        List<FindRuleAndDateDto> findRulesAndDatesDto = ruleRepository.findViolationOfTheRule(stuNum);

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
            response.put(rule,rulesCntAndDatesDto);
            cnt = 0;
        }

        return response;
    }

    @Override
    public List<FindIdAndRuleAndDateDto> findViolationOfTheRules(String stuNum) {
        List<FindIdAndRuleAndDateDto> response = ruleRepository.findViolationOfTheRules(stuNum);

        return response;
    }

}
