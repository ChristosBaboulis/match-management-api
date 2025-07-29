package com.example.matchmanagementapi.dto;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.Sport;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchMapperTest {

    @Test
    void testToDTO() {
        Match match = new Match();
        match.setId(1L);
        match.setDescription("TeamA-TeamB");
        match.setMatchDate(LocalDate.of(2025, 7, 29));
        match.setMatchTime(LocalTime.of(20, 45));
        match.setTeamA("TeamA");
        match.setTeamB("TeamB");
        match.setSport(Sport.Football);

        MatchDTO dto = MatchMapper.toDTO(match);

        assertEquals(1L, dto.getId());
        assertEquals("TeamA-TeamB", dto.getDescription());
        assertEquals(LocalDate.of(2025, 7, 29), dto.getMatchDate());
        assertEquals(LocalTime.of(20, 45), dto.getMatchTime());
        assertEquals("TeamA", dto.getTeamA());
        assertEquals("TeamB", dto.getTeamB());
        assertEquals(Sport.Football, dto.getSport());
    }

    @Test
    void testToEntity() {
        MatchDTO dto = new MatchDTO();
        dto.setId(2L);
        dto.setDescription("TeamX-TeamY");
        dto.setMatchDate(LocalDate.of(2025, 8, 1));
        dto.setMatchTime(LocalTime.of(18, 0));
        dto.setTeamA("TeamX");
        dto.setTeamB("TeamY");
        dto.setSport(Sport.Basketball);

        Match entity = MatchMapper.toEntity(dto);

        assertEquals(2L, entity.getId());
        assertEquals("TeamX-TeamY", entity.getDescription());
        assertEquals(LocalDate.of(2025, 8, 1), entity.getMatchDate());
        assertEquals(LocalTime.of(18, 0), entity.getMatchTime());
        assertEquals("TeamX", entity.getTeamA());
        assertEquals("TeamY", entity.getTeamB());
        assertEquals(Sport.Basketball, entity.getSport());
    }

    @Test
    void testToDTOList() {
        Match m1 = new Match();
        m1.setId(1L);
        m1.setDescription("A-B");
        m1.setMatchDate(LocalDate.of(2025, 7, 29));
        m1.setMatchTime(LocalTime.of(21, 0));
        m1.setTeamA("A");
        m1.setTeamB("B");
        m1.setSport(Sport.Basketball);

        Match m2 = new Match();
        m2.setId(2L);
        m2.setDescription("C-D");
        m2.setMatchDate(LocalDate.of(2025, 7, 30));
        m2.setMatchTime(LocalTime.of(22, 0));
        m2.setTeamA("C");
        m2.setTeamB("D");
        m2.setSport(Sport.Football);

        List<MatchDTO> dtos = MatchMapper.toDTO(List.of(m1, m2));

        assertEquals(2, dtos.size());
        assertEquals("A-B", dtos.get(0).getDescription());
        assertEquals("C-D", dtos.get(1).getDescription());
    }

    @Test
    void testToEntityList() {
        MatchDTO d1 = new MatchDTO();
        d1.setId(1L);
        d1.setDescription("X-Y");
        d1.setMatchDate(LocalDate.of(2025, 7, 27));
        d1.setMatchTime(LocalTime.of(17, 0));
        d1.setTeamA("X");
        d1.setTeamB("Y");
        d1.setSport(Sport.Basketball);

        MatchDTO d2 = new MatchDTO();
        d2.setId(2L);
        d2.setDescription("Z-W");
        d2.setMatchDate(LocalDate.of(2025, 7, 28));
        d2.setMatchTime(LocalTime.of(19, 0));
        d2.setTeamA("Z");
        d2.setTeamB("W");
        d2.setSport(Sport.Basketball);

        List<Match> entities = MatchMapper.toEntity(List.of(d1, d2));

        assertEquals(2, entities.size());
        assertEquals("X-Y", entities.get(0).getDescription());
        assertEquals("Z-W", entities.get(1).getDescription());
    }
}
