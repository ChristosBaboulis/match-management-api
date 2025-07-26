package com.example.matchmanagementapi.dto;

import com.example.matchmanagementapi.domain.Sport;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MatchDTO {
    public Long id;
    public String description;
    public LocalDate matchDate;
    public LocalTime matchTime;
    public String teamA;
    public String teamB;
    public Sport sport;
}
