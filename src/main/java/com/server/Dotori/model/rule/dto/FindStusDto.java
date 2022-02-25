package com.server.Dotori.model.rule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FindStusDto {

    private Long id;
    private String memberName;
    private String stuNum;

}
