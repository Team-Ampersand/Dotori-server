package com.server.Dotori.model.member.dto;

import com.server.Dotori.model.member.enumType.SelfStudy;
import lombok.*;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class SelfStudyDto {

    private SelfStudy selfStudy;
    private long count;
}
