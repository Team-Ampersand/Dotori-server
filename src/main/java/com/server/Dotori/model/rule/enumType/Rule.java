package com.server.Dotori.model.rule.enumType;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Rule {
    FIREARMS1, WEAPON1, ALCOHOL1, TOBACCO1, MEANDERING_APPARATUS1,
    FIREARMS2, WEAPON2, ALCOHOL2, TOBACCO2, MEANDERING_APPARATUS2, FOOD,       // 1. 금지 물품 반입

    MANAGER_GUIDANCE1, MANAGER_GUIDANCE2,                                       // 2. 사감 지도 불이행

    TIME, OUTING, OVERNIGHT_STAY,                                               // 3. 시간 관 소홀 및 이탈 행위 ( 타호실 )

    DAMAGE_OF_GOODS, THEFT, CHANTAGE,                                           // 4. 물품 훼손 및 절도

    DISTURBING_SLEEP, ELECTRONIC_DEVICE,                                        // 5. 취침 방해

    LOUD, INCORRECT_ENTRY, LAUNDRY, VIOLATION_OF_THE_USE_OF_FACILITIES,
    DAMAGE_OF_POST, POSSESSION_OF_ELECTRONICS_DEVICES, CLEAN_COUNDITION_BAD,
    ENVIRONMENT_POLLUTION,                                                      // 6. 공동 생활 방해 및 위생 상태 불량

    AFFECTIONATE_ACT, SEXUAL_ACT,                                               // 7. 애정 행위 금지

    ENTRY_TO_PROHIBITED_AREAS,                                                  // 8. 기숙사 출입금지 구역을 허가받지 않고 이성 기숙사 구역에 출입한 경우

    VIOLATION_OF_STUDY_ROOM_RULES,                                              // 9. 학습실에서 면학분위기를 저해할 경우

    OUTSIDER_ENTRY;                                                              // 10. 외부인을 출입 시키는 경우

}
