package com.example.matchmanagementapi.dto;

import com.example.matchmanagementapi.domain.MatchOdds;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = MatchMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class MatchOddsMapper {
    public abstract MatchOddsDTO toDTO(MatchOdds entity);

    public abstract MatchOdds toEntity(MatchOddsDTO dto);

    public abstract List<MatchOddsDTO> toDTO(List<MatchOdds> entity);

    public abstract List<MatchOdds> toEntity(List<MatchOddsDTO> dto);
}
