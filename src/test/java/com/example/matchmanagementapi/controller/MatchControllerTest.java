package com.example.matchmanagementapi.controller;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.Sport;
import com.example.matchmanagementapi.dto.MatchDTO;
import com.example.matchmanagementapi.service.MatchOddsService;
import com.example.matchmanagementapi.service.MatchService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatchController.class)
@Import(MatchControllerTest.MockConfig.class)
class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MatchService matchService;

    @Autowired
    private MatchOddsService matchOddsService;

    @TestConfiguration
    static class MockConfig {

        @Bean
        public MatchService matchService() {
            return Mockito.mock(MatchService.class);
        }

        @Bean
        public MatchOddsService matchOddsService() {
            return Mockito.mock(MatchOddsService.class);
        }
    }

    private final Match match = new Match(
            "OSFP-PAO",
            LocalDate.of(2025, 8, 1),
            LocalTime.of(20, 0),
            "OSFP",
            "PAO",
            Sport.Football
    );

    private final MatchDTO matchDTO = new MatchDTO();
    {
        matchDTO.setId(1L);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 1));
        matchDTO.setMatchTime(LocalTime.of(20, 0));
        matchDTO.setTeamA("OSFP");
        matchDTO.setTeamB("PAO");
        matchDTO.setSport(Sport.Football);
    }

    @Test
    void getMatchById_ReturnsMatch() throws Exception {
        Mockito.when(matchService.find(1L)).thenReturn(match);

        mockMvc.perform(get("/api/matches/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("OSFP-PAO"));
    }

    @Test
    void getCount_ReturnsNumber() throws Exception {
        Mockito.when(matchService.getRecordsCount()).thenReturn(5L);

        mockMvc.perform(get("/api/matches/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void deleteById_DeletesMatch() throws Exception {
        mockMvc.perform(delete("/api/matches/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByIds_DeletesMultiple() throws Exception {
        mockMvc.perform(delete("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1,2,3]"))
                .andExpect(status().isOk());
    }

    @Test
    void patchMatch_UpdatesField() throws Exception {
        Match patched = new Match(
                "AEK-PAO",
                LocalDate.now(),
                LocalTime.now(),
                "AEK",
                "PAO",
                Sport.Football
        );

        Mockito.when(matchService.partialUpdate(Mockito.eq(1L), Mockito.anyMap())).thenReturn(patched);

        mockMvc.perform(patch("/api/matches/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"teamA\":\"AEK\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamA").value("AEK"));
    }

    @Test
    void searchMatches_ReturnsFilteredList() throws Exception {
        Mockito.when(matchService.searchMatches(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any()
        )).thenReturn(List.of(match));

        mockMvc.perform(get("/api/matches")
                        .param("teamA", "OSFP")
                        .param("teamB", "PAO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("OSFP-PAO"));
    }

    @Test
    void saveMatch_SingleInsert_ReturnsSavedMatch() throws Exception {
        Mockito.when(matchService.save(Mockito.any(Match.class))).thenReturn(match);

        String body = """
        {
            "description": "OSFP-PAO",
            "matchDate": "2025-08-01",
            "matchTime": "20:00:00",
            "teamA": "OSFP",
            "teamB": "PAO",
            "sport": "Football"
        }
    """;

        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("OSFP-PAO"));
    }

    @Test
    void saveMatch_BatchInsert_ReturnsSavedMatches() throws Exception {
        Mockito.when(matchService.saveAll(Mockito.anyList())).thenReturn(List.of(match));

        String body = """
        [
            {
                "description": "OSFP-PAO",
                "matchDate": "2025-08-01",
                "matchTime": "20:00:00",
                "teamA": "OSFP",
                "teamB": "PAO",
                "sport": "Football"
            }
        ]
    """;

        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("OSFP-PAO"));
    }

    @Test
    void updateMatch_UpdatesAndReturnsMatch() throws Exception {
        Mockito.when(matchService.update(Mockito.eq(1L), Mockito.any(Match.class))).thenReturn(match);

        String body = """
        {
            "description": "OSFP-PAO",
            "matchDate": "2025-08-01",
            "matchTime": "20:00:00",
            "teamA": "OSFP",
            "teamB": "PAO",
            "sport": "Football"
        }
    """;

        mockMvc.perform(put("/api/matches/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("OSFP-PAO"));
    }

}
