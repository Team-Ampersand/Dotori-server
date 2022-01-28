package com.server.Dotori.model.massage.dto;

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
