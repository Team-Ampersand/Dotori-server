package com.server.Dotori.domain.rule.service.Impl;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.rule.RuleViolation;
import com.server.Dotori.domain.rule.dto.*;
import com.server.Dotori.domain.rule.enumType.Rule;
import com.server.Dotori.domain.rule.repository.RuleRepository;
import com.server.Dotori.domain.rule.service.RuleService;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.exception.ErrorCode;
import com.server.Dotori.global.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RuleServiceImpl implements RuleService {

    private final MemberRepository memberRepository;
    private final RuleRepository ruleRepository;
    private final CurrentMemberUtil currentMemberUtil;

    /**
     * 규정위반을 부여하는 서비스 로직
     * @param ruleGrantDto stuNum, rule, date
     * @return memberId
     * @author 노경준
     */
    @Override
    public void grant(RuleGrantDto ruleGrantDto) {
        for (String stuNum : ruleGrantDto.getStuNum()){
            Member member = memberRepository.findByStuNum(stuNum).orElseThrow(() -> new DotoriException(ErrorCode.MEMBER_NOT_FOUND));
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

    /**
     * 단일 학생의 규정위반 정보를 전체조회하는 서비스 로직
     * @param stuNum
     * @return HashMap<Rule, RulesCntAndDatesDto>
     * @author 노경준
     */
    @Override
    public HashMap<Rule, RulesCntAndDatesDto> findAllViolationOfTheRule(String stuNum) {
        if(!memberRepository.existsByStuNum(stuNum)) throw new DotoriException(ErrorCode.MEMBER_NOT_FOUND);

        HashMap<Rule, RulesCntAndDatesDto> response = new LinkedHashMap<>();
        List<FindRuleAndDateDto> findRulesAndDatesDtoList = ruleRepository.findViolationOfTheRule(stuNum);

        if (findRulesAndDatesDtoList.isEmpty()) throw new DotoriException(ErrorCode.RULE_NO_HISTORY);

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

    /**
     * 학번으로 단일 학생의 규정위반 내역을 전체조회하는 서비스 로직
     * @param stuNum
     * @return List<FindViolationOfTheRuleResponseDto>
     * @author 노경준
     */
    @Override
    public List<ViolationOfTheRuleResponseDto> findViolationOfTheRules(String stuNum) {
        if(!memberRepository.existsByStuNum(stuNum)) throw new DotoriException(ErrorCode.MEMBER_NOT_FOUND);

        return getViolationOfTheRuleResponseDtos(stuNum);
    }

    /**
     * 규정위반을 삭제하는 서비스 로직
     * @param id
     * @author 노경준
     */
    @Override
    public void deleteViolationOfTheRules(Long id) {
        ruleRepository.deleteById(id);
    }

    /**
     * 현재 로그인 된 학생의 규정위반 정보를 메인페이지에서 전체조회하는 서비스 로직
     * @return List<FindViolationOfTheRuleResponseDto>
     * @author 노경준
     */
    @Override
    public List<ViolationOfTheRuleResponseDto> findRuleAtMainPage() {
        String currentMemberStuNum = currentMemberUtil.getCurrentMember().getStuNum();

        return getViolationOfTheRuleResponseDtos(currentMemberStuNum);
    }

    /**
     * 규정위반 페이지에서 학생들을 전체조회하는 서비스 로직
     * @return List<FindStusDto>
     * @author 노경준
     */
    @Override
    public List<FindStusDto> findAllStudents() {
        List<FindStusDto> response = memberRepository.findAllStuOfRulePage();

        isEmptyValidResponse(response);

        return response;
    }

    /**
     * 학년 반으로 학생들을 조회하는 서비스 로직
     * @param classId
     * @return List<FindStusDto>
     * @author 노경준
     */
    @Override
    public List<FindStusDto> findStusByClassId(Long classId) {
        List<FindStusDto> response = memberRepository.findStusByClassId(classId);

        isEmptyValidResponse(response);

        return response;
    }

    /**
     * 학생 이름으로 학생들을 조회하는 서비스 로직
     * @param memberName
     * @return List<FindStusDto>
     * @author 노경준
     */
    @Override
    public List<FindStusDto> findStusByMemberName(String memberName) {
        List<FindStusDto> response = memberRepository.findStusByMemberName(memberName);

        isEmptyValidResponse(response);

        return response;
    }

    /**
     * 학번으로 규정위반 목록 ResponseDto 들을 조회하는 private method
     * @param stuNum
     * @return List<FindViolationOfTheRuleResponseDto>
     * @author 노경준
     */
    private List<ViolationOfTheRuleResponseDto> getViolationOfTheRuleResponseDtos(String stuNum) {
        List<FindIdAndRuleAndDateDto> result = ruleRepository.findViolationOfTheRules(stuNum);

        if(result.isEmpty()) throw new DotoriException(ErrorCode.RULE_NO_HISTORY);

        List<ViolationOfTheRuleResponseDto> response = new LinkedList<>();

        for (FindIdAndRuleAndDateDto dto : result) {
            ViolationOfTheRuleResponseDto responseDto = ViolationOfTheRuleResponseDto.builder()
                    .id(dto.getId())
                    .rule(dto.getRule())
                    .date(dto.getDate())
                    .build();

            response.add(responseDto);
        }

        return response;
    }

    /**
     * response 가 비었는지 검증하는 privateMethod
     * @param response
     * @throws DotoriException MEMBER_NOT_FOUND
     * @author 노경준
     */
    private void isEmptyValidResponse(List<FindStusDto> response) throws DotoriException{
        if (response.isEmpty()) throw new DotoriException(ErrorCode.MEMBER_NOT_FOUND);
    }
}
