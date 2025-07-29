package com.example.matchmanagementapi.dto;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.MatchOdds;
import com.example.matchmanagementapi.domain.Sport;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchOddsMapperTest {

    private Match createSampleMatch() {
        Match match = new Match();
        match.setId(1L);
        match.setDescription("TeamA-TeamB");
        match.setMatchDate(LocalDate.of(2025, 7, 29));
        match.setMatchTime(LocalTime.of(21, 0));
        match.setTeamA("TeamA");
        match.setTeamB("TeamB");
        match.setSport(Sport.Football);
        return match;
    }

    @Test
    void testToDTO() {
        Match match = createSampleMatch();

        MatchOdds odds = new MatchOdds();
        odds.setId(100L);
        odds.setSpecifier("1");
        odds.setOdd(2.5);
        odds.setMatch(match);

        MatchOddsDTO dto = MatchOddsMapper.toDTO(odds);

        assertEquals(100L, dto.getId());
        assertEquals("1", dto.getSpecifier());
        assertEquals(2.5, dto.getOdd());
        assertEquals(1L, dto.getMatchId());
    }

    @Test
    void testToEntity() {
        Match match = createSampleMatch();

        MatchOddsDTO dto = new MatchOddsDTO();
        dto.setId(101L);
        dto.setSpecifier("X");
        dto.setOdd(3.1);
        dto.setMatchId(1L);

        MatchOdds entity = MatchOddsMapper.toEntity(dto, match);

        assertEquals(101L, entity.getId());
        assertEquals("X", entity.getSpecifier());
        assertEquals(3.1, entity.getOdd());
        assertEquals(1L, entity.getMatch().getId());
    }

    @Test
    void testToDTOList() {
        Match match = createSampleMatch();

        MatchOdds o1 = new MatchOdds();
        o1.setId(1L);
        o1.setSpecifier("1");
        o1.setOdd(2.0);
        o1.setMatch(match);

        MatchOdds o2 = new MatchOdds();
        o2.setId(2L);
        o2.setSpecifier("2");
        o2.setOdd(3.5);
        o2.setMatch(match);

        List<MatchOddsDTO> dtos = MatchOddsMapper.toDTO(List.of(o1, o2));

        assertEquals(2, dtos.size());
        assertEquals("1", dtos.get(0).getSpecifier());
        assertEquals("2", dtos.get(1).getSpecifier());
    }

    @Test
    void testToEntityList() {
        Match match = createSampleMatch();

        MatchOddsDTO d1 = new MatchOddsDTO();
        d1.setId(1L);
        d1.setSpecifier("1");
        d1.setOdd(2.0);
        d1.setMatchId(1L);

        MatchOddsDTO d2 = new MatchOddsDTO();
        d2.setId(2L);
        d2.setSpecifier("2");
        d2.setOdd(3.5);
        d2.setMatchId(1L);

        List<MatchOdds> entities = MatchOddsMapper.toEntity(List.of(d1, d2), match);

        assertEquals(2, entities.size());
        assertEquals("1", entities.get(0).getSpecifier());
        assertEquals("2", entities.get(1).getSpecifier());
        assertEquals(1L, entities.get(0).getMatch().getId());
    }
}
