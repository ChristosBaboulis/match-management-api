package com.example.matchmanagementapi.dto;

import com.example.matchmanagementapi.Initializer;
import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.Sport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;

@ActiveProfiles("test")
@SpringBootTest
public class MatchMapperTest extends Initializer {
    @Autowired
    private MatchMapper matchMapper;

    @Test
    void toDTO_shouldMapMatchToMatchDTO() {

        MatchDTO dto = matchMapper.toDTO(match);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(match.getId(), dto.getId());
        Assertions.assertEquals(match.getDescription(), dto.getDescription());
        Assertions.assertEquals(match.getMatchDate(), dto.getMatchDate());
        Assertions.assertEquals(match.getMatchTime(), dto.getMatchTime());
        Assertions.assertEquals(match.getTeamA(), dto.getTeamA());
        Assertions.assertEquals(match.getTeamB(), dto.getTeamB());
    }

    @Test
    void toEntity_shouldMapMatchDTOToMatch() {
        MatchDTO dto = new MatchDTO();
        dto.setDescription("Match 2");
        dto.setMatchDate(LocalDate.of(2025, 8, 5));
        dto.setMatchTime(LocalTime.of(18, 30));
        dto.setTeamA("Team X");
        dto.setTeamB("Team Y");
        dto.setSport(Sport.Football);

        Match match = matchMapper.toEntity(dto);

        Assertions.assertNotNull(match);
        Assertions.assertEquals(dto.getDescription(), match.getDescription());
        Assertions.assertEquals(dto.getMatchDate(), match.getMatchDate());
        Assertions.assertEquals(dto.getMatchTime(), match.getMatchTime());
        Assertions.assertEquals(dto.getTeamA(), match.getTeamA());
        Assertions.assertEquals(dto.getTeamB(), match.getTeamB());
        Assertions.assertEquals(dto.getSport(), match.getSport());
    }

    @Test
    void shouldMapMatchListToDTOListAndBack() {
        Match match1 = new Match(
                "Desc A",
                LocalDate.of(2025, 8, 1),
                LocalTime.of(20, 0),
                "Team A",
                "Team B",
                Sport.Basketball
        );

        Match match2 = new Match(
                "Desc B",
                LocalDate.of(2025, 8, 2),
                LocalTime.of(21, 30),
                "Team C",
                "Team D",
                Sport.Football
        );

        var dtoList = matchMapper.toDTO(java.util.List.of(match1, match2));
        Assertions.assertEquals(2, dtoList.size());
        Assertions.assertEquals("Team A", dtoList.get(0).getTeamA());
        Assertions.assertEquals("Team D", dtoList.get(1).getTeamB());

        var entityList = matchMapper.toEntity(dtoList);
        Assertions.assertEquals(2, entityList.size());
        Assertions.assertEquals("Desc A", entityList.get(0).getDescription());
        Assertions.assertEquals(Sport.Football, entityList.get(1).getSport());
    }

}
