package com.example.matchmanagementapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MatchOddsDTO {
    public Long id;
    public MatchDTO match;
    public String specifier;
    public double odd;
}
