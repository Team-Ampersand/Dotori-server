package com.server.Dotori.model.rule.service;

import com.server.Dotori.exception.member.exception.MemberNotFoundException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.model.rule.RuleViolation;
import com.server.Dotori.model.rule.dto.*;
import com.server.Dotori.model.rule.enumType.Rule;
import com.server.Dotori.model.rule.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            ruleViolations.add(ruleGrantDto.toEntity(member));
        }

        for (RuleViolation ruleViolation : ruleViolations) {
            ruleRepository.save(ruleViolation);
        }
    }

    @Override
    public HashMap<Rule, RulesCntAndDatesDto> findAllViolationOfTheRule(String stuNum) {
        if(!memberRepository.existsByStuNum(stuNum)) throw new MemberNotFoundException();

        HashMap<Rule, RulesCntAndDatesDto> response = new LinkedHashMap<>();
        List<FindRuleAndDateDto> findRulesAndDatesDtoList = ruleRepository.findViolationOfTheRule(stuNum);

        if (findRulesAndDatesDtoList.isEmpty()) throw new IllegalArgumentException("규정위반 내역이 없습니다.");

        int cnt = 0;
        for (Rule rule : Rule.values()) {
            List<LocalDate> date = new LinkedList<>();
            for (int i = 0; i < findRulesAndDatesDtoList.size(); i++) {
                if(findRulesAndDatesDtoList.get(i).getRules().equals(rule)){
                    date.add(findRulesAndDatesDtoList.get(i).getDates());
                    cnt++;
                }
            }
            RulesCntAndDatesDto rulesCntAndDatesDto = new RulesCntAndDatesDto(cnt, date);
            response.put(rule,rulesCntAndDatesDto);
            cnt = 0;
        }

        return response;
    }

    @Override
    public List<FindViolationOfTheRuleResponseDto> findViolationOfTheRules(String stuNum) {
        if(!memberRepository.existsByStuNum(stuNum)) throw new MemberNotFoundException();

        List<FindIdAndRuleAndDateDto> result = ruleRepository.findViolationOfTheRules(stuNum);

        if(result.isEmpty()) throw new IllegalArgumentException("규정위반 내역이 없습니다.");

        List<FindViolationOfTheRuleResponseDto> response = new LinkedList<>();

        for (FindIdAndRuleAndDateDto dto : result) {
            FindViolationOfTheRuleResponseDto responseDto = FindViolationOfTheRuleResponseDto.builder()
                    .id(dto.getId())
                    .rule(dto.getRule())
                    .date(dto.getDate())
                    .build();

            response.add(responseDto);
        }

        return response;
    }

    @Override
    public void deleteViolationOfTheRules(Long id) {
        ruleRepository.deleteById(id);
    }

}
