package com.example.matchmanagementapi.dto;

import com.example.matchmanagementapi.domain.Match;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class MatchMapper {
    public abstract MatchDTO toDTO(Match entity);

    public abstract Match toEntity(MatchDTO dto);

    public abstract List<MatchDTO> toDTO(List<Match> entity);

    public abstract List<Match> toEntity(List<MatchDTO> dto);
}
