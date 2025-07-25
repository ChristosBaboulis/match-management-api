package com.example.matchmanagementapi.dto;

import com.example.matchmanagementapi.domain.Sport;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

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
