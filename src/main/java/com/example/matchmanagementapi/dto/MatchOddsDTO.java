package com.example.matchmanagementapi.dto;

import lombok.Data;

@Data
public class MatchOddsDTO {
    public Long id;
    public MatchDTO match;
    public String specifier;
    public double odd;
}
