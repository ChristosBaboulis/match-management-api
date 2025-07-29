package com.example.matchmanagementapi.controller;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.MatchOdds;
import com.example.matchmanagementapi.dto.MatchOddsDTO;
import com.example.matchmanagementapi.service.MatchOddsService;
import com.example.matchmanagementapi.service.MatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatchOddsController.class)
@Import(MatchOddsControllerTest.MockConfig.class)
class MatchOddsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MatchOddsService matchOddsService;

    @Autowired
    private MatchService matchService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {

        @Bean
        public MatchOddsService matchOddsService() {
            return Mockito.mock(MatchOddsService.class);
        }

        @Bean
        public MatchService matchService() {
            return Mockito.mock(MatchService.class);
        }

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @Test
    void testGetMatchOddsById() throws Exception {
        Match match = new Match();
        match.setId(1L);
        MatchOdds odds = new MatchOdds(match, "1", 2.5);
        odds.setId(10L);

        when(matchOddsService.find(10L)).thenReturn(odds);

        mockMvc.perform(get("/api/matchOdds/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.matchId").value(1))
                .andExpect(jsonPath("$.specifier").value("1"))
                .andExpect(jsonPath("$.odd").value(2.5));
    }

    @Test
    void testSaveSingleMatchOdds() throws Exception {
        Match match = new Match();
        match.setId(5L);

        MatchOddsDTO dto = new MatchOddsDTO();
        dto.setMatchId(5L);
        dto.setSpecifier("X");
        dto.setOdd(3.1);

        MatchOdds saved = new MatchOdds(match, "X", 3.1);
        saved.setId(99L);

        when(matchService.find(5L)).thenReturn(match);
        when(matchOddsService.save(any())).thenReturn(saved);

        mockMvc.perform(post("/api/matchOdds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(99))
                .andExpect(jsonPath("$.matchId").value(5))
                .andExpect(jsonPath("$.specifier").value("X"))
                .andExpect(jsonPath("$.odd").value(3.1));
    }

    @Test
    void testPatchMatchOdds() throws Exception {
        Match match = new Match();
        match.setId(5L);

        MatchOdds updated = new MatchOdds(match, "1", 2.9);
        updated.setId(7L);

        Map<String, Object> patch = Map.of("odd", 2.9);

        when(matchOddsService.partialUpdate(eq(7L), any())).thenReturn(updated);

        mockMvc.perform(patch("/api/matchOdds/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.specifier").value("1"))
                .andExpect(jsonPath("$.odd").value(2.9));
    }

    @Test
    void testDeleteMatchOddsById() throws Exception {
        doNothing().when(matchOddsService).deleteById(12L);

        mockMvc.perform(delete("/api/matchOdds/12"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetMatchOddsCount() throws Exception {
        when(matchOddsService.getRecordsCount()).thenReturn(42L);

        mockMvc.perform(get("/api/matchOdds/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));
    }

    @Test
    void testSearchMatchOdds() throws Exception {
        Match match = new Match();
        match.setId(5L);

        MatchOdds odds = new MatchOdds(match, "X", 3.3);
        odds.setId(20L);

        when(matchOddsService.searchMatchOdds("X", null, null, null, 5L)).thenReturn(List.of(odds));

        mockMvc.perform(get("/api/matchOdds?specifier=X&matchId=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(20))
                .andExpect(jsonPath("$[0].matchId").value(5))
                .andExpect(jsonPath("$[0].specifier").value("X"))
                .andExpect(jsonPath("$[0].odd").value(3.3));
    }

    @Test
    void testBatchSaveMatchOdds() throws Exception {
        Match match = new Match();
        match.setId(5L);

        MatchOddsDTO dto1 = new MatchOddsDTO();
        dto1.setMatchId(5L);
        dto1.setSpecifier("1");
        dto1.setOdd(2.0);

        MatchOddsDTO dto2 = new MatchOddsDTO();
        dto2.setMatchId(5L);
        dto2.setSpecifier("X");
        dto2.setOdd(3.5);

        MatchOdds saved1 = new MatchOdds(match, "1", 2.0);
        saved1.setId(101L);

        MatchOdds saved2 = new MatchOdds(match, "X", 3.5);
        saved2.setId(102L);

        when(matchService.find(5L)).thenReturn(match);
        when(matchOddsService.saveAll(anyList())).thenReturn(List.of(saved1, saved2));

        mockMvc.perform(post("/api/matchOdds/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(dto1, dto2))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(101))
                .andExpect(jsonPath("$[0].specifier").value("1"))
                .andExpect(jsonPath("$[1].id").value(102))
                .andExpect(jsonPath("$[1].specifier").value("X"));
    }

    @Test
    void testUpdateMatchOdds() throws Exception {
        Match match = new Match();
        match.setId(5L);

        MatchOddsDTO dto = new MatchOddsDTO();
        dto.setMatchId(5L);
        dto.setSpecifier("2");
        dto.setOdd(4.4);

        MatchOdds updated = new MatchOdds(match, "2", 4.4);
        updated.setId(8L);

        when(matchService.find(5L)).thenReturn(match);
        when(matchOddsService.update(eq(8L), any(MatchOdds.class))).thenReturn(updated);

        mockMvc.perform(put("/api/matchOdds/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(8))
                .andExpect(jsonPath("$.matchId").value(5))
                .andExpect(jsonPath("$.specifier").value("2"))
                .andExpect(jsonPath("$.odd").value(4.4));
    }

    @Test
    void testDeleteMatchOddsByIds() throws Exception {
        doNothing().when(matchOddsService).deleteByIds(List.of(1L, 2L, 3L));

        mockMvc.perform(delete("/api/matchOdds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(1, 2, 3))))
                .andExpect(status().isOk());
    }
}
