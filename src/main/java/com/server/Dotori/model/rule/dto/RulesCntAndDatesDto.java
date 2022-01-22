package com.server.Dotori.model.rule.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
public class RulesCntAndDatesDto {

    private int cnt;
    private List<String> date;

}
