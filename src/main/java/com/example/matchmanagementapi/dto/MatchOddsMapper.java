package com.example.matchmanagementapi.dto;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.MatchOdds;

import java.util.ArrayList;
import java.util.List;

public class MatchOddsMapper {

    public static MatchOddsDTO toDTO(MatchOdds entity) {
        MatchOddsDTO dto = new MatchOddsDTO();
        dto.setId(entity.getId());
        dto.setSpecifier(entity.getSpecifier());
        dto.setOdd(entity.getOdd());
        dto.setMatchId(entity.getMatch().getId());
        return dto;
    }

    public static MatchOdds toEntity(MatchOddsDTO dto, Match match) {
        MatchOdds entity = new MatchOdds();
        entity.setId(dto.getId());
        entity.setSpecifier(dto.getSpecifier());
        entity.setOdd(dto.getOdd());
        entity.setMatch(match);
        return entity;
    }

    public static List<MatchOddsDTO> toDTO(List<MatchOdds> entities) {
        List<MatchOddsDTO> list = new ArrayList<>();
        for (MatchOdds odds : entities) {
            list.add(toDTO(odds));
        }
        return list;
    }

    public static List<MatchOdds> toEntity(List<MatchOddsDTO> dtos, Match match) {
        List<MatchOdds> list = new ArrayList<>();
        for (MatchOddsDTO dto : dtos) {
            list.add(toEntity(dto, match));
        }
        return list;
    }
}
