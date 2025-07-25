package com.example.matchmanagementapi.controller;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.Sport;
import com.example.matchmanagementapi.dto.MatchDTO;
import com.example.matchmanagementapi.dto.MatchMapper;
import com.example.matchmanagementapi.service.MatchService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        mockMvc = MockMvcBuilders.standaloneSetup(matchController).build();
    }

    @Test
    void testGetMatchesByIds() throws Exception {
        // Arrange
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(1L);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 1));
        matchDTO.setMatchTime(LocalTime.of(20, 0));
        matchDTO.setTeamA("TeamA");
        matchDTO.setTeamB("TeamB");

        List<Long> ids = List.of(1L, 2L, 3L);

        Mockito.when(matchService.findAll(ids)).thenReturn(List.of());
        Mockito.when(matchMapper.toDTO(List.of())).thenReturn(List.of(matchDTO));

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
    void testGetAllMatches() throws Exception {
        // Arrange
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(1L);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 1));
        matchDTO.setMatchTime(LocalTime.of(20, 0));
        matchDTO.setTeamA("TeamA");
        matchDTO.setTeamB("TeamB");

        Mockito.when(matchService.findAll()).thenReturn(List.of());
        Mockito.when(matchMapper.toDTO(List.of())).thenReturn(List.of(matchDTO));

        // Act & Assert
        mockMvc.perform(get("/api/matches")
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
        matchDTO.setTeamA("TeamA");
        matchDTO.setTeamB("TeamB");

        Match match = new Match("OSFP-PAO", LocalDate.of(2025, 8, 1), LocalTime.of(20, 0), "Team A", "Team B", Sport.Football);
        Mockito.when(matchService.find(matchId)).thenReturn(match);
        Mockito.when(matchMapper.toDTO(match)).thenReturn(matchDTO);

        // Act & Assert
        mockMvc.perform(get("/api/matches/{id}", matchId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(matchId))
                .andExpect(jsonPath("$.description").value("OSFP-PAO"));
    }

    @Test
    void testGetMatchesByDescription() throws Exception {
        String description = "OSFP-PAO";

        MatchDTO dto = new MatchDTO();
        dto.setId(1L);
        dto.setDescription(description);
        dto.setMatchDate(LocalDate.of(2025, 8, 1));
        dto.setMatchTime(LocalTime.of(20, 0));
        dto.setTeamA("TeamA");
        dto.setTeamB("TeamB");

        Mockito.when(matchService.findByDescription(description)).thenReturn(List.of());
        Mockito.when(matchMapper.toDTO(List.of())).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/matches/searchByDescription")
                        .param("description", description)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value(description));
    }

    @Test
    void testGetMatchesByMatchDate() throws Exception {
        // Arrange
        var matchDTO = new MatchDTO();
        matchDTO.setId(1L);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 1));
        matchDTO.setMatchTime(LocalTime.of(18, 0));
        matchDTO.setTeamA("TeamA");
        matchDTO.setTeamB("TeamB");

        Mockito.when(matchService.findByMatchDate(LocalDate.of(2025, 8, 1))).thenReturn(List.of());
        Mockito.when(matchMapper.toDTO(List.of())).thenReturn(List.of(matchDTO));

        // Act & Assert
        mockMvc.perform(get("/api/matches/searchByMatchDate")
                        .param("matchDate", "2025-08-01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("OSFP-PAO"));
    }

    @Test
    void testGetMatchesByDateRange() throws Exception {
        // Arrange
        LocalDate startDate = LocalDate.of(2025, 8, 1);
        LocalDate endDate = LocalDate.of(2025, 8, 10);

        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(1L);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 5));
        matchDTO.setMatchTime(LocalTime.of(20, 0));
        matchDTO.setTeamA("TeamA");
        matchDTO.setTeamB("TeamB");

        Mockito.when(matchService.findByMatchDateBetween(startDate, endDate)).thenReturn(List.of());
        Mockito.when(matchMapper.toDTO(List.of())).thenReturn(List.of(matchDTO));

        // Act & Assert
        mockMvc.perform(get("/api/matches/searchByDateRange")
                        .param("startDate", "2025-08-01")
                        .param("endDate", "2025-08-10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("OSFP-PAO"));
    }

    @Test
    void testGetMatchesBeforeDate() throws Exception {
        // Arrange
        LocalDate matchDate = LocalDate.of(2025, 8, 1);

        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(1L);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 7, 25));
        matchDTO.setMatchTime(LocalTime.of(18, 30));
        matchDTO.setTeamA("TeamA");
        matchDTO.setTeamB("TeamB");

        Mockito.when(matchService.findByMatchDateBefore(matchDate)).thenReturn(List.of());
        Mockito.when(matchMapper.toDTO(List.of())).thenReturn(List.of(matchDTO));

        // Act & Assert
        mockMvc.perform(get("/api/matches/searchByDateBefore")
                        .param("matchDate", "2025-08-01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("OSFP-PAO"));
    }

    @Test
    void testGetMatchesAfterDate() throws Exception {
        // Arrange
        LocalDate matchDate = LocalDate.of(2025, 8, 1);

        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(1L);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 10));
        matchDTO.setMatchTime(LocalTime.of(20, 0));
        matchDTO.setTeamA("TeamA");
        matchDTO.setTeamB("TeamB");

        Mockito.when(matchService.findByMatchDateAfter(matchDate)).thenReturn(List.of());
        Mockito.when(matchMapper.toDTO(List.of())).thenReturn(List.of(matchDTO));

        // Act & Assert
        mockMvc.perform(get("/api/matches/searchByDateAfter")
                        .param("matchDate", "2025-08-01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("OSFP-PAO"));
    }

    @Test
    void testGetMatchesByTime() throws Exception {
        // Arrange
        LocalTime matchTime = LocalTime.of(20, 0);

        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(1L);
        matchDTO.setDescription("OSFP-PAO");
        matchDTO.setMatchDate(LocalDate.of(2025, 8, 1));
        matchDTO.setMatchTime(matchTime);
        matchDTO.setTeamA("TeamA");
        matchDTO.setTeamB("TeamB");

        Mockito.when(matchService.findByMatchTime(matchTime)).thenReturn(List.of());
        Mockito.when(matchMapper.toDTO(List.of())).thenReturn(List.of(matchDTO));

        // Act & Assert
        mockMvc.perform(get("/api/matches/searchByMatchTime")
                        .param("matchTime", "20:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("OSFP-PAO"));
    }

}
