package com.server.Dotori.model.rule.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RulesCntAndDatesDto {

    private int cnt;
    private List<String> date;

    public RulesCntAndDatesDto(int cnt, List<String> date) {
        this.cnt = cnt;
        this.date = date;
    }
}
