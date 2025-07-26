package com.example.matchmanagementapi.controller;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.MatchOdds;
import com.example.matchmanagementapi.domain.Sport;
import com.example.matchmanagementapi.dto.MatchDTO;
import com.example.matchmanagementapi.dto.MatchOddsDTO;
import com.example.matchmanagementapi.dto.MatchOddsMapper;
import com.example.matchmanagementapi.service.MatchOddsService;
import com.example.matchmanagementapi.service.MatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
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
    private MatchService matchService;

    @Mock
    private MatchOddsMapper matchOddsMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        matchOddsController = new MatchOddsController(matchOddsService, matchOddsMapper, objectMapper, matchService);
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

    // <editor-fold desc="POST endpoints">
    @Test
    void testSaveMatchOdds() throws Exception {
        // Arrange
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 1));
        matchDTO.setMatchTime(LocalTime.of(20, 0));
        matchDTO.setTeamA("OSFP");
        matchDTO.setTeamB("PAO");
        matchDTO.setSport(Sport.Football);

        Match matchEntity = new Match("OSFP-PAO", matchDTO.getMatchDate(), matchDTO.getMatchTime(), "OSFP", "PAO", Sport.Football);

        MatchOddsDTO oddsDTO = new MatchOddsDTO();
        oddsDTO.setMatch(matchDTO);
        oddsDTO.setSpecifier("1");
        oddsDTO.setOdd(2.5);

        MatchOdds oddsEntity = new MatchOdds(matchEntity, "1", 2.5);

        when(matchOddsMapper.toEntity(oddsDTO)).thenReturn(oddsEntity);
        when(matchOddsService.save(oddsEntity)).thenReturn(oddsEntity);
        when(matchOddsMapper.toDTO(oddsEntity)).thenReturn(oddsDTO);

        // Act & Assert
        mockMvc.perform(post("/api/matchOdds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "match": {
                            "description": "OSFP-PAO",
                            "matchDate": "2025-08-01",
                            "matchTime": "20:00:00",
                            "teamA": "OSFP",
                            "teamB": "PAO",
                            "sport": "Football"
                          },
                          "specifier": "1",
                          "odd": 2.5
                        }
                    """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specifier").value("1"))
                .andExpect(jsonPath("$.odd").value(2.5));
    }

    @Test
    void testSaveMatchOddsBatch() throws Exception {
        // Arrange
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(1L);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 1));
        matchDTO.setMatchTime(LocalTime.of(20, 0));
        matchDTO.setTeamA("OSFP");
        matchDTO.setTeamB("PAO");
        matchDTO.setSport(Sport.Football);

        Match matchEntity = new Match("OSFP-PAO", matchDTO.getMatchDate(), matchDTO.getMatchTime(), "OSFP", "PAO", Sport.Football);
        when(matchService.find(1L)).thenReturn(matchEntity);

        MatchOddsDTO dto1 = new MatchOddsDTO();
        dto1.setMatch(matchDTO);
        dto1.setSpecifier("1");
        dto1.setOdd(2.5);

        MatchOddsDTO dto2 = new MatchOddsDTO();
        dto2.setMatch(matchDTO);
        dto2.setSpecifier("X");
        dto2.setOdd(3.1);

        MatchOdds entity1 = new MatchOdds(matchEntity, "1", 2.5);
        MatchOdds entity2 = new MatchOdds(matchEntity, "X", 3.1);

        List<MatchOddsDTO> dtoList = List.of(dto1, dto2);
        List<MatchOdds> entityList = List.of(entity1, entity2);

        when(matchOddsService.saveAll(entityList)).thenReturn(entityList);
        when(matchOddsMapper.toDTO(entityList)).thenReturn(dtoList);

        // Act & Assert
        mockMvc.perform(post("/api/matchOdds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        [
                          {
                            "specifier": "1",
                            "odd": 2.5,
                            "match": {
                              "id": 1
                            }
                          },
                          {
                            "specifier": "X",
                            "odd": 3.1,
                            "match": {
                              "id": 1
                            }
                          }
                        ]
                    """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].specifier").value("1"))
                .andExpect(jsonPath("$[1].specifier").value("X"))
                .andExpect(jsonPath("$[0].match.id").value(1))
                .andExpect(jsonPath("$[1].match.id").value(1));
    }

    // </editor-fold>

    // <editor-fold desc="DELETE endpoints">
    @Test
    void deleteById_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/matchOdds/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteByIds() throws Exception {
        List<Long> ids = List.of(1L, 2L, 3L);

        Mockito.doNothing().when(matchOddsService).deleteByIds(ids);

        mockMvc.perform(delete("/api/matchOdds")
                        .contentType("application/json")
                        .content("[1,2,3]"))
                .andExpect(status().isOk());
    }
    // </editor-fold>

    // <editor-fold desc="PUT endpoints">
    @Test
    void testUpdateMatchOdds() throws Exception {
        // Arrange
        Long matchOddsId = 1L;
        Long matchId = 5L;

        MatchOddsDTO matchOddsDTO = new MatchOddsDTO();
        matchOddsDTO.setId(matchOddsId);
        matchOddsDTO.setSpecifier("1");
        matchOddsDTO.setOdd(2.5);

        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(matchId);
        matchOddsDTO.setMatch(matchDTO);

        MatchOddsDTO savedMatchOddsDTO = new MatchOddsDTO();
        savedMatchOddsDTO.setId(matchOddsId);
        savedMatchOddsDTO.setSpecifier("1");
        savedMatchOddsDTO.setOdd(2.5);
        savedMatchOddsDTO.setMatch(matchDTO);

        MatchOdds matchOddsEntity = new MatchOdds();
        matchOddsEntity.setSpecifier("1");
        matchOddsEntity.setOdd(2.5);
        matchOddsEntity.setMatch(new Match());

        when(matchOddsMapper.toEntity(matchOddsDTO)).thenReturn(matchOddsEntity);
        when(matchOddsService.update(matchOddsEntity)).thenReturn(matchOddsEntity);
        when(matchOddsMapper.toDTO(matchOddsEntity)).thenReturn(savedMatchOddsDTO);

        // Act & Assert
        mockMvc.perform(put("/api/matchOdds/{id}", matchOddsId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "specifier": "1",
                      "odd": 2.5,
                      "match": {
                        "id": 5
                      }
                    }
                    """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specifier").value("1"))
                .andExpect(jsonPath("$.odd").value(2.5))
                .andExpect(jsonPath("$.match.id").value(5));
    }

    @Test
    void testUpdateMatchOddsBatch() throws Exception {
        // Arrange
        Long matchId = 5L;

        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(matchId);

        MatchOddsDTO matchOddsDTO = new MatchOddsDTO();
        matchOddsDTO.setId(1L);
        matchOddsDTO.setSpecifier("1");
        matchOddsDTO.setOdd(2.5);
        matchOddsDTO.setMatch(matchDTO);

        List<MatchOddsDTO> matchOddsDTOList = List.of(matchOddsDTO);

        Match matchEntity = new Match();
        when(matchService.find(matchId)).thenReturn(matchEntity);

        MatchOdds matchOddsEntity = new MatchOdds();
        matchOddsEntity.setSpecifier("1");
        matchOddsEntity.setOdd(2.5);
        matchOddsEntity.setMatch(matchEntity);

        List<MatchOdds> matchOddsEntityList = List.of(matchOddsEntity);

        when(matchService.find(matchId)).thenReturn(matchEntity);
        doReturn(matchOddsEntityList).when(matchOddsMapper).toEntity(anyList());
        when(matchOddsService.update(matchOddsEntityList)).thenReturn(matchOddsEntityList);
        when(matchOddsMapper.toDTO(matchOddsEntityList)).thenReturn(matchOddsDTOList);

        // Act & Assert
        mockMvc.perform(put("/api/matchOdds/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    [
                      {
                        "id": 1,
                        "specifier": "1",
                        "odd": 2.5,
                        "match": {
                          "id": 5
                        }
                      }
                    ]
                    """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].specifier").value("1"))
                .andExpect(jsonPath("$[0].odd").value(2.5))
                .andExpect(jsonPath("$[0].match.id").value(5));
    }
    // </editor-fold>
}
