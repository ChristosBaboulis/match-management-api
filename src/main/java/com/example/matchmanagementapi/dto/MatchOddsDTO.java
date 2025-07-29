package com.example.matchmanagementapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MatchOddsDTO {
    private Long id;
    private Long matchId;
    private String specifier;
    private double odd;
}
