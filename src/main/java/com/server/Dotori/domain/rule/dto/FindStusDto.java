package com.server.Dotori.domain.rule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class FindStusDto {

    private Long id;
    private String memberName;
    private String stuNum;
    private List<String> ruleBigViolationList;

}
