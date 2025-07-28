package com.example.matchmanagementapi.controller;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.Sport;
import com.example.matchmanagementapi.dto.MatchDTO;
import com.example.matchmanagementapi.dto.MatchMapper;
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
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MatchControllerTest {
    @InjectMocks
    private MatchController matchController;

    @Mock
    private MatchService matchService;

    @Mock
    private MatchMapper matchMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        matchController = new MatchController(matchService, matchMapper, objectMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(matchController).build();
    }

    // <editor-fold desc="GET endpoints">
    @Test
    void testSearchMatches_withFilters() throws Exception {
        // Arrange
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(1L);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 1));
        matchDTO.setMatchTime(LocalTime.of(20, 0));
        matchDTO.setTeamA("OSFP");
        matchDTO.setTeamB("PAO");

        Match match = new Match("OSFP-PAO", LocalDate.of(2025, 8, 1), LocalTime.of(20, 0), "OSFP", "PAO", Sport.Football);

        // mock service and mapper behavior
        when(matchService.searchMatches(
                eq("OSFP-PAO"),
                eq("OSFP"),
                eq("PAO"),
                eq(Sport.Football),
                eq(LocalDate.of(2025, 8, 1)),
                eq(null),
                eq(null),
                eq(LocalTime.of(20, 0)),
                eq(null),
                eq(null)
        )).thenReturn(List.of(match));

        when(matchMapper.toDTO(List.of(match))).thenReturn(List.of(matchDTO));

        // Act & Assert
        mockMvc.perform(get("/api/matches")
                        .param("description", "OSFP-PAO")
                        .param("teamA", "OSFP")
                        .param("teamB", "PAO")
                        .param("sport", "Football")
                        .param("matchDate", "2025-08-01")
                        .param("matchTime", "20:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].description").value("OSFP-PAO"))
                .andExpect(jsonPath("$[0].teamA").value("OSFP"))
                .andExpect(jsonPath("$[0].teamB").value("PAO"))
                .andExpect(jsonPath("$[0].matchDate[0]").value(2025))
                .andExpect(jsonPath("$[0].matchDate[1]").value(8))
                .andExpect(jsonPath("$[0].matchDate[2]").value(1));
    }

    @Test
    void testGetMatchesByIds() throws Exception {
        // Arrange
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(1L);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 1));
        matchDTO.setMatchTime(LocalTime.of(20, 0));
        matchDTO.setTeamA("OSFP");
        matchDTO.setTeamB("PAO");

        List<Long> ids = List.of(1L, 2L, 3L);

        when(matchService.findAll(ids)).thenReturn(List.of());
        when(matchMapper.toDTO(List.of())).thenReturn(List.of(matchDTO));

        // Act & Assert
        mockMvc.perform(get("/api/matches/byIdsList")
                        .param("ids", "1")
                        .param("ids", "2")
                        .param("ids", "3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("OSFP-PAO"));
    }

    @Test
    void testGetMatchById() throws Exception {
        // Arrange
        Long matchId = 1L;
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(matchId);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 1));
        matchDTO.setMatchTime(LocalTime.of(20, 0));
        matchDTO.setTeamA("OSFP");
        matchDTO.setTeamB("PAO");

        Match match = new Match("OSFP-PAO", LocalDate.of(2025, 8, 1), LocalTime.of(20, 0), "OSFP", "PAO", Sport.Football);
        when(matchService.find(matchId)).thenReturn(match);
        when(matchMapper.toDTO(match)).thenReturn(matchDTO);

        // Act & Assert
        mockMvc.perform(get("/api/matches/{id}", matchId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(matchId))
                .andExpect(jsonPath("$.description").value("OSFP-PAO"));
    }

    @Test
    void testGetCount() throws Exception {
        Mockito.when(matchService.getRecordsCount()).thenReturn(5L);

        mockMvc.perform(get("/api/matches/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
    // </editor-fold>

    // <editor-fold desc="POST endpoints">
    @Test
    void testSaveMatch() throws Exception {
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

        when(matchMapper.toEntity(matchDTO)).thenReturn(matchEntity);
        when(matchService.save(matchEntity)).thenReturn(matchEntity);
        when(matchMapper.toDTO(matchEntity)).thenReturn(matchDTO);

        // Act & Assert
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "id": 1,
                          "description": "OSFP-PAO",
                          "matchDate": "2025-08-01",
                          "matchTime": "20:00:00",
                          "teamA": "OSFP",
                          "teamB": "PAO",
                          "sport": "Football"
                        }
                    """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("OSFP-PAO"));
    }

    @Test
    void testSaveMatchesBatch() throws Exception {
        // Arrange
        MatchDTO match1 = new MatchDTO();
        match1.setId(1L);
        match1.setDescription("OSFP-PAO");
        match1.setMatchDate(LocalDate.of(2025, 8, 1));
        match1.setMatchTime(LocalTime.of(20, 0));
        match1.setTeamA("OSFP");
        match1.setTeamB("PAO");
        match1.setSport(Sport.Football);

        MatchDTO match2 = new MatchDTO();
        match2.setId(2L);
        match2.setDescription("AEK-ARIS");
        match2.setMatchDate(LocalDate.of(2025, 8, 2));
        match2.setMatchTime(LocalTime.of(21, 0));
        match2.setTeamA("AEK");
        match2.setTeamB("ARIS");
        match2.setSport(Sport.Football);

        List<MatchDTO> matchDTOs = List.of(match1, match2);
        List<Match> matchEntities = matchDTOs.stream()
                .map(dto -> new Match(dto.getDescription(), dto.getMatchDate(), dto.getMatchTime(), dto.getTeamA(), dto.getTeamB(), dto.getSport()))
                .toList();

        when(matchMapper.toEntity(matchDTOs)).thenReturn(matchEntities);
        when(matchService.saveAll(matchEntities)).thenReturn(matchEntities);
        when(matchMapper.toDTO(matchEntities)).thenReturn(matchDTOs);

        // Act & Assert
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        [
                          {
                            "id": 1,
                            "description": "OSFP-PAO",
                            "matchDate": "2025-08-01",
                            "matchTime": "20:00:00",
                            "teamA": "OSFP",
                            "teamB": "PAO",
                            "sport": "Football"
                          },
                          {
                            "id": 2,
                            "description": "AEK-ARIS",
                            "matchDate": "2025-08-02",
                            "matchTime": "21:00:00",
                            "teamA": "AEK",
                            "teamB": "ARIS",
                            "sport": "Football"
                          }
                        ]
                    """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("OSFP-PAO"))
                .andExpect(jsonPath("$[1].description").value("AEK-ARIS"));
    }

    // </editor-fold>

    // <editor-fold desc="DELETE endpoints">
    @Test
    void deleteById_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/matches/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteByIds() throws Exception {
        List<Long> ids = List.of(1L, 2L, 3L);

        Mockito.doNothing().when(matchService).deleteByIds(ids);

        mockMvc.perform(delete("/api/matches")
                        .contentType("application/json")
                        .content("[1,2,3]"))
                .andExpect(status().isOk());
    }
    // </editor-fold>

    // <editor-fold desc="PUT endpoints">
//    @Test
//    void testUpdateMatch() throws Exception {
//        // Arrange
//        Long id = 1L;
//        MatchDTO matchDTO = new MatchDTO();
//        matchDTO.setId(id);
//        matchDTO.setDescription("OSFP-PAO");
//        matchDTO.setMatchDate(LocalDate.of(2025, 8, 1));
//        matchDTO.setMatchTime(LocalTime.of(20, 0));
//        matchDTO.setTeamA("OSFP");
//        matchDTO.setTeamB("PAO");
//        matchDTO.setSport(Sport.Football);
//
//        Match matchEntity = new Match("OSFP-PAO", matchDTO.getMatchDate(), matchDTO.getMatchTime(), "OSFP", "PAO", Sport.Football);
//
//        when(matchMapper.toEntity(matchDTO)).thenReturn(matchEntity);
//        when(matchService.update(1L, matchEntity)).thenReturn(matchEntity);
//        when(matchMapper.toDTO(matchEntity)).thenReturn(matchDTO);
//
//        // Act & Assert
//        mockMvc.perform(put("/api/matches/{id}", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                        {
//                          "description": "OSFP-PAO",
//                          "matchDate": "2025-08-01",
//                          "matchTime": "20:00:00",
//                          "teamA": "OSFP",
//                          "teamB": "PAO",
//                          "sport": "Football"
//                        }
//                    """)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.description").value("OSFP-PAO"));
//    }

    @Test
    void testUpdateMatches() throws Exception {
        // Arrange
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(1L);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 1));
        matchDTO.setMatchTime(LocalTime.of(20, 0));
        matchDTO.setTeamA("OSFP");
        matchDTO.setTeamB("PAO");
        matchDTO.setSport(Sport.Football);

        List<MatchDTO> matchDTOList = List.of(matchDTO);

        Match matchEntity = new Match("OSFP-PAO", matchDTO.getMatchDate(), matchDTO.getMatchTime(), "OSFP", "PAO", Sport.Football);
        List<Match> matchEntityList = List.of(matchEntity);

        when(matchMapper.toEntity(matchDTOList)).thenReturn(matchEntityList);
        when(matchService.update(matchEntityList)).thenReturn(matchEntityList);
        when(matchMapper.toDTO(matchEntityList)).thenReturn(matchDTOList);

        // Act & Assert
        mockMvc.perform(put("/api/matches/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    [
                      {
                        "id": 1,
                        "description": "OSFP-PAO",
                        "matchDate": "2025-08-01",
                        "matchTime": "20:00:00",
                        "teamA": "OSFP",
                        "teamB": "PAO",
                        "sport": "Football"
                      }
                    ]
                    """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("OSFP-PAO"));
    }
    // </editor-fold>

    // <editor-fold desc="PATCH endpoints">
    @Test
    void testPatchMatch_updatesTeamAAndDescription() throws Exception {
        // Arrange
        Long matchId = 1L;

        Match updatedMatch = new Match("AEK-PAO", LocalDate.of(2025, 8, 1), LocalTime.of(20, 0), "AEK", "PAO", Sport.Football);

        MatchDTO updatedDTO = new MatchDTO();
        updatedDTO.setId(matchId);
        updatedDTO.setDescription("AEK-PAO");
        updatedDTO.setTeamA("AEK");
        updatedDTO.setTeamB("PAO");
        updatedDTO.setMatchDate(LocalDate.of(2025, 8, 1));
        updatedDTO.setMatchTime(LocalTime.of(20, 0));

        Map<String, Object> updates = Map.of("teamA", "AEK");

        when(matchService.partialUpdate(eq(matchId), eq(updates))).thenReturn(updatedMatch);
        when(matchMapper.toDTO(updatedMatch)).thenReturn(updatedDTO);

        // Act & Assert
        mockMvc.perform(patch("/api/matches/{id}", matchId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "teamA": "AEK"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(matchId))
                .andExpect(jsonPath("$.teamA").value("AEK"))
                .andExpect(jsonPath("$.description").value("AEK-PAO"));
    }

    // </editor-fold>
}
