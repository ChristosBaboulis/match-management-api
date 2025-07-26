package com.example.matchmanagementapi.controller;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.MatchOdds;
import com.example.matchmanagementapi.domain.Sport;
import com.example.matchmanagementapi.dto.MatchDTO;
import com.example.matchmanagementapi.dto.MatchOddsDTO;
import com.example.matchmanagementapi.dto.MatchOddsMapper;
import com.example.matchmanagementapi.service.MatchOddsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MatchOddsControllerTest {
    @InjectMocks
    private MatchOddsController matchOddsController;

    @Mock
    private MatchOddsService matchOddsService;

    @Mock
    private MatchOddsMapper matchOddsMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        matchOddsController = new MatchOddsController(matchOddsService, matchOddsMapper, objectMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(matchOddsController).build();
    }

    // <editor-fold desc="GET endpoints">
    @Test
    void testSearchMatchOdds_withFilters() throws Exception {
        // Arrange
        MatchOddsDTO dto = new MatchOddsDTO();
        dto.setSpecifier("1");
        dto.setOdd(2.5);

        Match match = new Match();
        match.setDescription("OSFP-PAO");
        match.setMatchDate(LocalDate.of(2025, 8, 1));
        match.setMatchTime(LocalTime.of(20, 0));
        match.setTeamA("OSFP");
        match.setTeamB("PAO");
        match.setSport(Sport.Football);

        MatchOdds entity = new MatchOdds(match, "1", 2.5);

        // Mock service & mapper
        when(matchOddsService.searchMatchOdds(
                eq("1"),
                eq(2.5),
                eq(null),
                eq(null),
                eq(5L)
        )).thenReturn(List.of(entity));

        when(matchOddsMapper.toDTO(List.of(entity))).thenReturn(List.of(dto));

        // Act & Assert
        mockMvc.perform(get("/api/matchOdds")
                        .param("specifier", "1")
                        .param("odd", "2.5")
                        .param("matchId", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].specifier").value("1"))
                .andExpect(jsonPath("$[0].odd").value(2.5));
    }

    @Test
    void testGetMatchOddsByIds() throws Exception {
        // Arrange
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(5L);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 1));
        matchDTO.setMatchTime(LocalTime.of(20, 0));
        matchDTO.setTeamA("OSFP");
        matchDTO.setTeamB("PAO");

        MatchOddsDTO oddsDTO = new MatchOddsDTO();
        oddsDTO.setId(1L);
        oddsDTO.setSpecifier("1");
        oddsDTO.setOdd(2.5);
        oddsDTO.setMatch(matchDTO);

        List<Long> ids = List.of(1L, 2L, 3L);

        when(matchOddsService.findAll(ids)).thenReturn(List.of());
        when(matchOddsMapper.toDTO(List.of())).thenReturn(List.of(oddsDTO));

        // Act & Assert
        mockMvc.perform(get("/api/matchOdds/byIdsList")
                        .param("ids", "1")
                        .param("ids", "2")
                        .param("ids", "3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].specifier").value("1"))
                .andExpect(jsonPath("$[0].odd").value(2.5));
    }

    @Test
    void testGetMatchOddById() throws Exception {
        // Arrange
        Long oddsId = 1L;

        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(5L);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 1));
        matchDTO.setMatchTime(LocalTime.of(20, 0));
        matchDTO.setTeamA("OSFP");
        matchDTO.setTeamB("PAO");

        MatchOddsDTO oddsDTO = new MatchOddsDTO();
        oddsDTO.setId(oddsId);
        oddsDTO.setSpecifier("1");
        oddsDTO.setOdd(2.5);
        oddsDTO.setMatch(matchDTO);

        Match match = new Match("OSFP-PAO", LocalDate.of(2025, 8, 1), LocalTime.of(20, 0), "OSFP", "PAO", Sport.Football);
        MatchOdds entity = new MatchOdds(match, "1", 2.5);

        when(matchOddsService.find(oddsId)).thenReturn(entity);
        when(matchOddsMapper.toDTO(entity)).thenReturn(oddsDTO);

        // Act & Assert
        mockMvc.perform(get("/api/matchOdds/{id}", oddsId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.specifier").value("1"))
                .andExpect(jsonPath("$.odd").value(2.5));
    }

    @Test
    void testGetMatchOddsCount() throws Exception {
        when(matchOddsService.getRecordsCount()).thenReturn(7L);

        mockMvc.perform(get("/api/matchOdds/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("7"));
    }

    // </editor-fold>
}
