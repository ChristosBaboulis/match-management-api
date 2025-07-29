package com.example.matchmanagementapi.dto;

import com.example.matchmanagementapi.domain.Match;

import java.util.ArrayList;
import java.util.List;

public class MatchMapper {

    public static MatchDTO toDTO(Match entity) {
        MatchDTO dto = new MatchDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setMatchDate(entity.getMatchDate());
        dto.setMatchTime(entity.getMatchTime());
        dto.setTeamA(entity.getTeamA());
        dto.setTeamB(entity.getTeamB());
        dto.setSport(entity.getSport());
        return dto;
    }

    public static Match toEntity(MatchDTO dto) {
        Match entity = new Match();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setMatchDate(dto.getMatchDate());
        entity.setMatchTime(dto.getMatchTime());
        entity.setTeamA(dto.getTeamA());
        entity.setTeamB(dto.getTeamB());
        entity.setSport(dto.getSport());
        return entity;
    }

    public static List<MatchDTO> toDTO(List<Match> entities) {
        List<MatchDTO> list = new ArrayList<>();
        for (Match match : entities) {
            list.add(toDTO(match));
        }
        return list;
    }

    public static List<Match> toEntity(List<MatchDTO> dtos) {
        List<Match> list = new ArrayList<>();
        for (MatchDTO matchDTO : dtos) {
            list.add(toEntity(matchDTO));
        }
        return list;
    }
}
