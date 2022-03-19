package com.server.Dotori.domain.rule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class RulesCntAndDatesDto {

    private int cnt;
    private List<LocalDate> date;

}
