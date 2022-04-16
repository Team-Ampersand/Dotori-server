package com.server.Dotori.domain.stu_info.dto;

import com.server.Dotori.domain.member.enumType.Gender;
import lombok.*;

@Getter @Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED) @NoArgsConstructor
public class GenderUpdateDto {

    private Long receiverId;

    private Gender gender;
}
