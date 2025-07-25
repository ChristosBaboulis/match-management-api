package com.example.matchmanagementapi.dto;

import com.example.matchmanagementapi.Initializer;
import com.example.matchmanagementapi.domain.MatchOdds;
import com.example.matchmanagementapi.domain.Sport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class MatchOddsMapperTest extends Initializer {
    @Autowired
    private MatchMapper matchMapper;
    @Autowired
    private MatchOddsMapper matchOddsMapper;

    @Test
    void testToDTO() {
        MatchOddsDTO dto = matchOddsMapper.toDTO(matchOdds);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("X", dto.getSpecifier());
        Assertions.assertEquals(1.5, dto.getOdd());
        Assertions.assertNotNull(dto.getMatch());
        Assertions.assertEquals("OSFP", dto.getMatch().getTeamA());
    }

    @Test
    void testToEntity() {
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setTeamA("Team A");
        matchDTO.setTeamB("Team B");
        matchDTO.setDescription("Semi-final");
        matchDTO.setMatchDate(LocalDate.now());
        matchDTO.setMatchTime(LocalTime.of(20, 30));
        matchDTO.setSport(Sport.Football);

        MatchOddsDTO dto = new MatchOddsDTO();
        dto.setMatch(matchDTO);
        dto.setSpecifier("X");
        dto.setOdd(2.15);

        MatchOdds entity = matchOddsMapper.toEntity(dto);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals("X", entity.getSpecifier());
        Assertions.assertEquals(2.15, entity.getOdd());
        Assertions.assertNotNull(entity.getMatch());
        Assertions.assertEquals("Team A", entity.getMatch().getTeamA());
    }

    @Test
    void testListMapping() {
        MatchOdds matchOdds = new MatchOdds(match, "2", 3.10);

        List<MatchOddsDTO> dtoList = matchOddsMapper.toDTO(Collections.singletonList(matchOdds));
        Assertions.assertEquals(1, dtoList.size());
        Assertions.assertEquals("2", dtoList.getFirst().getSpecifier());

        List<MatchOdds> entityList = matchOddsMapper.toEntity(dtoList);
        Assertions.assertEquals(1, entityList.size());
        Assertions.assertEquals("2", entityList.getFirst().getSpecifier());
    }
}
