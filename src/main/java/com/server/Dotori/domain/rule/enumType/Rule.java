package com.server.Dotori.domain.rule.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rule {

    FIREARMS("금지 물품 반입"),
    WEAPON("금지 물품 반입"),
    ALCOHOL("금지 물품 반입"),
    TOBACCO("금지 물품 반입"),
    MEANDERING_APPARATUS("금지 물품 반입"),
    FOOD("금지 물품 반입"),                                                   // 1. 금지 물품 반입

    USE_FIREARMS("금지 물품 사용"),
    USE_WEAPON("금지 물품 사용"),
    DRINKING_ALCOHOL("금지 물품 사용"),
    USE_TOBACCO("금지 물품 사용"),
    USE_MEANDERING_APPARATUS("금지 물품 사용"),
    EAT_FOOD("금지 물품 사용"),                                               // 2. 금지 물품 사용

    MANAGER_GUIDANCE("사감 지도 불이행"),                                     // 3. 사감 지도 불이행

    TIME("시간 관 소홀 및 이탈 행위"),
    OUTING("시간 관 소홀 및 이탈 행위"),
    OVERNIGHT_STAY("시간 관 소홀 및 이탈 행위"),                                // 4. 시간 관 소홀 및 이탈 행위

    DAMAGE_OF_GOODS("물품 훼손 및 절도"),
    THEFT("물품 훼손 및 절도"),
    CHANTAGE("물품 훼손 및 절도"),                                           // 5. 물품 훼손 및 절도

    DISTURBING_SLEEP("취침 방해"),
    ELECTRONIC_DEVICE("취침 방해"),                                        // 6. 취침 방해

    LOUD("공동 생활 방해 및 위생 상태 불량"),
    INCORRECT_ENTRY("공동 생활 방해 및 위생 상태 불량"),
    LAUNDRY("공동 생활 방해 및 위생 상태 불량"),
    VIOLATION_OF_THE_USE_OF_FACILITIES("공동 생활 방해 및 위생 상태 불량"),
    DAMAGE_OF_POST("공동 생활 방해 및 위생 상태 불량"),
    POSSESSION_OF_ELECTRONICS_DEVICES("공동 생활 방해 및 위생 상태 불량"),
    CLEAN_COUNDITION_BAD("공동 생활 방해 및 위생 상태 불량"),
    ENVIRONMENT_POLLUTION("공동 생활 방해 및 위생 상태 불량"),                  // 7. 공동 생활 방해 및 위생 상태 불량

    AFFECTIONATE_ACT("애정 행위"),
    SEXUAL_ACT("애정 행위"),                                               // 8. 애정 행위

    ENTRY_TO_PROHIBITED_AREAS("기숙사 출입 금지 구역 출입"),                  // 9. 기숙사 출입 금지 구역 출입

    VIOLATION_OF_STUDY_ROOM_RULES("학습실 면학분위기 저해"),                 // 10. 학습실에서 면학분위기 저해

    OUTSIDER_ENTRY("외부인 출입 관여");                                   // 11. 외부인 출입 관여

    private String big;

}
