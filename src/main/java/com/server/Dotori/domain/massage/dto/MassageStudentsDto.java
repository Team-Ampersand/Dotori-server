package com.server.Dotori.domain.massage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MassageStudentsDto {

    private Long id;
    private String stuNum;
    private String memberName;
}
