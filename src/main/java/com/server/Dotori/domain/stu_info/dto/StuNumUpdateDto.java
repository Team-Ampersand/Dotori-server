package com.server.Dotori.domain.stu_info.dto;

import lombok.*;

@Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StuNumUpdateDto {

    private Long receiverId;

    private String stuNum;
}
