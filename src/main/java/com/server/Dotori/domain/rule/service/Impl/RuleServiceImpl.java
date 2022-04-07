package com.server.Dotori.domain.rule.service.Impl;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.rule.RuleViolation;
import com.server.Dotori.domain.rule.dto.*;
import com.server.Dotori.domain.rule.enumType.Rule;
import com.server.Dotori.domain.rule.repository.RuleRepository;
import com.server.Dotori.domain.rule.service.RuleService;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static com.server.Dotori.global.exception.ErrorCode.MEMBER_NOT_FOUND;
import static com.server.Dotori.global.exception.ErrorCode.RULE_NO_HISTORY;

@RequiredArgsConstructor
@Service
public class RuleServiceImpl implements RuleService {

    private final MemberRepository memberRepository;
    private final RuleRepository ruleRepository;
    private final CurrentMemberUtil currentMemberUtil;

    @Override
    public void grant(RuleGrantDto ruleGrantDto) {
        for (String stuNum : ruleGrantDto.getStuNum()){
            Member member = memberRepository.findByStuNum(stuNum).orElseThrow(() -> new DotoriException(MEMBER_NOT_FOUND));
            for (Rule rule : ruleGrantDto.getRule()) {
                ruleRepository.save(
                        RuleViolation.builder()
                                .member(member)
                                .rule(rule)
                                .date(ruleGrantDto.getDate())
                                .build()
                );
            }
        }
    }

    @Override
    public HashMap<Rule, RulesCntAndDatesDto> findAllViolationOfTheRule(String stuNum) {
        if(!memberRepository.existsByStuNum(stuNum)) throw new DotoriException(MEMBER_NOT_FOUND);

        HashMap<Rule, RulesCntAndDatesDto> response = new LinkedHashMap<>();
        List<FindRuleAndDateDto> findRulesAndDatesDtoList = ruleRepository.findViolationOfTheRule(stuNum);

        if (findRulesAndDatesDtoList.isEmpty()) throw new DotoriException(RULE_NO_HISTORY);

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
        if(!memberRepository.existsByStuNum(stuNum)) throw new DotoriException(MEMBER_NOT_FOUND);

        List<FindIdAndRuleAndDateDto> result = ruleRepository.findViolationOfTheRules(stuNum);

        if(result.isEmpty()) throw new DotoriException(RULE_NO_HISTORY);

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

    @Override
    public List<FindViolationOfTheRuleResponseDto> findRuleAtMainPage() {
        String currentMemberStuNum = currentMemberUtil.getCurrentMember().getStuNum();

        List<FindIdAndRuleAndDateDto> result = ruleRepository.findViolationOfTheRules(currentMemberStuNum);

        if(result.isEmpty()) throw new DotoriException(RULE_NO_HISTORY);

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
    public List<FindStusDto> findAllStudents() {
        List<FindStusDto> response = memberRepository.findAllStuOfRulePage();

        if (response.isEmpty()) throw new DotoriException(MEMBER_NOT_FOUND);

        return response;
    }

    @Override
    public List<FindStusDto> findStusByClassId(Long classId) {
        List<FindStusDto> response = memberRepository.findStusByClassId(classId);

        if (response.isEmpty()) throw new DotoriException(MEMBER_NOT_FOUND);

        return response;
    }

    @Override
    public List<FindStusDto> findStusByMemberName(String memberName) {
        List<FindStusDto> response = memberRepository.findStusByMemberName(memberName);

        if (response.isEmpty()) throw new DotoriException(MEMBER_NOT_FOUND);

        return response;
    }

}
