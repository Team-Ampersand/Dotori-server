package com.server.Dotori.domain.stu_info.dto;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class MemberNameUpdateDto {

    private Long receiverId;

    private String memberName;
}
