package com.server.Dotori.model.rule.enumType;

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

    MANAGER_GUIDANCE("사감 지도 불이행"),                                     // 2. 사감 지도 불이행

    TIME("시간 관 소홀 및 이탈 행위"),
    OUTING("시간 관 소홀 및 이탈 행위"),
    OVERNIGHT_STAY("시간 관 소홀 및 이탈 행위"),                               // 3. 시간 관 소홀 및 이탈 행위 ( 타호실 )

    DAMAGE_OF_GOODS("물품 훼손 및 절도"),
    THEFT("물품 훼손 및 절도"),
    CHANTAGE("물품 훼손 및 절도"),                                           // 4. 물품 훼손 및 절도

    DISTURBING_SLEEP("취침 방해"),
    ELECTRONIC_DEVICE("취침 방해"),                                        // 5. 취침 방해

    LOUD("공동 생활 방해 및 위생 상태 불량"),
    INCORRECT_ENTRY("공동 생활 방해 및 위생 상태 불량"),
    LAUNDRY("공동 생활 방해 및 위생 상태 불량"),
    VIOLATION_OF_THE_USE_OF_FACILITIES("공동 생활 방해 및 위생 상태 불량"),
    DAMAGE_OF_POST("공동 생활 방해 및 위생 상태 불량"),
    POSSESSION_OF_ELECTRONICS_DEVICES("공동 생활 방해 및 위생 상태 불량"),
    CLEAN_COUNDITION_BAD("공동 생활 방해 및 위생 상태 불량"),
    ENVIRONMENT_POLLUTION("공동 생활 방해 및 위생 상태 불량"),                      // 6. 공동 생활 방해 및 위생 상태 불량

    AFFECTIONATE_ACT("애정 행위 금지"),
    SEXUAL_ACT("애정 행위 금지"),                                               // 7. 애정 행위 금지

    ENTRY_TO_PROHIBITED_AREAS("기숙사 출입금지 구역을 허가받지 않고 이성 기숙사 구역에 출입한 경우"),                                                  // 8. 기숙사 출입금지 구역을 허가받지 않고 이성 기숙사 구역에 출입한 경우

    VIOLATION_OF_STUDY_ROOM_RULES("학습실에서 면학분위기를 저해할 경우"),                                              // 9. 학습실에서 면학분위기를 저해할 경우

    OUTSIDER_ENTRY("외부인을 출입 시키는 경우");                                                              // 10. 외부인을 출입 시키는 경우

    private String big;

}
