package com.example.matchmanagementapi.controller;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.MatchOdds;
import com.example.matchmanagementapi.domain.Sport;
import com.example.matchmanagementapi.dto.MatchDTO;
import com.example.matchmanagementapi.dto.MatchMapper;
import com.example.matchmanagementapi.dto.MatchOddsDTO;
import com.example.matchmanagementapi.dto.MatchOddsMapper;
import com.example.matchmanagementapi.service.MatchOddsService;
import com.example.matchmanagementapi.service.MatchService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;
    private final MatchOddsService matchOddsService;
    private final ObjectMapper objectMapper;

    // <editor-fold desc="GET endpoints">
    /**
     * Retrieves a filtered list of matches.
     * Example: GET /api/matches?teamA=OSFP&teamB=PAO&sport=Football&startDate=2025-08-01&endDate=2025-08-10
     *
     * @param description       Optional match description.
     * @param teamA             Optional first team name.
     * @param teamB             Optional second team name.
     * @param sport             Optional sport type.
     * @param matchDate         Optional exact match date.
     * @param matchDateBefore   Optional start date for date range.
     * @param matchDateAfter    Optional end date for date range.
     * @param matchTime         Optional exact match time.
     * @param matchTimeBefore   Optional upper-bound for time.
     * @param matchTimeAfter    Optional lower-bound for time.
     * @return ResponseEntity   containing the filtered list of MatchDTOs.
     */
    @GetMapping
    public ResponseEntity<List<MatchDTO>> searchMatches(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String teamA,
            @RequestParam(required = false) String teamB,
            @RequestParam(required = false) Sport sport,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate matchDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate matchDateBefore ,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate matchDateAfter  ,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime matchTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime matchTimeBefore,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime matchTimeAfter
    ) {
        List<MatchDTO> result = MatchMapper.toDTO(matchService.searchMatches(
                description, teamA, teamB, sport,
                matchDate, matchDateBefore , matchDateAfter,
                matchTime, matchTimeBefore, matchTimeAfter
        ));
        return ResponseEntity.ok(result);
    }

    /**
     * Returns a match by its ID.
     * Endpoint:
     * - GET /api/matches/{id}
     *
     * @param id The ID of the match to retrieve.
     * @return ResponseEntity containing the MatchDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatchById(@PathVariable Long id){
        MatchDTO result = MatchMapper.toDTO(matchService.find(id));
        return ResponseEntity.ok(result);
    }

    /**
     * Returns the total number of match records.
     * Example: GET /api/matches/count
     *
     * @return ResponseEntity containing the total count of matches.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getCount(){
        long result = matchService.getRecordsCount();
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves all match odds for a given match ID.
     * Example: GET /api/matches/{id}/odds
     *
     * @param id The ID of the match whose odds are to be retrieved.
     * @return ResponseEntity containing the list of MatchOddsDTOs.
     */
    @GetMapping("/{id}/odds")
    public ResponseEntity<List<MatchOddsDTO>> getOddsForMatch(@PathVariable Long id) {
        List<MatchOdds> odds = matchOddsService.getOddsForMatch(id);
        return ResponseEntity.ok(MatchOddsMapper.toDTO(odds));
    }
    // </editor-fold>

    // <editor-fold desc="POST endpoints">
    /**
     * Saves one or more matches to the database.
     * Example:
     * - Single insert: POST /api/matches
     *   Request Body:
     *   {
     *     "description": "OSFP-PAO",
     *     "matchDate": "2025-08-01",
     *     "matchTime": "20:00:00",
     *     "teamA": "OSFP",
     *     "teamB": "PAO",
     *     "sport": "Football"
     *   }
     * - Batch insert: POST /api/matches
     *   Request Body:
     *   [
     *     {
     *       "description": "OSFP-PAO",
     *       "matchDate": "2025-08-01",
     *       "matchTime": "20:00:00",
     *       "teamA": "OSFP",
     *       "teamB": "PAO",
     *       "sport": "Football"
     *     },
     *     {
     *       "description": "AEK-ARIS",
     *       "matchDate": "2025-08-02",
     *       "matchTime": "21:00:00",
     *       "teamA": "AEK",
     *       "teamB": "ARIS",
     *       "sport": "Football"
     *     }
     *   ]
     *
     * @param body The request body containing a single MatchDTO or a list of MatchDTOs.
     * @return ResponseEntity containing the saved MatchDTO or list of MatchDTOs.
     */
    @PostMapping
    public ResponseEntity<?> saveMatch(@RequestBody Object body) {
        if (body instanceof List<?> list && !list.isEmpty() && list.getFirst() instanceof LinkedHashMap) {
            // Batch insert
            List<MatchDTO> matchDTOs = objectMapper.convertValue(body, new TypeReference<>() {
            });
            List<Match> matches = MatchMapper.toEntity(matchDTOs);
            List<Match> saved = matchService.saveAll(matches);
            return ResponseEntity.ok(MatchMapper.toDTO(saved));
        } else {
            // Single insert
            MatchDTO matchDTO = objectMapper.convertValue(body, MatchDTO.class);
            Match match = MatchMapper.toEntity(matchDTO);
            Match saved = matchService.save(match);
            return ResponseEntity.ok(MatchMapper.toDTO(saved));
        }
    }
    // </editor-fold>

    // <editor-fold desc="DELETE endpoints">
    /**
     * Deletes a match by its ID.
     * Example: DELETE /api/matches/1
     *
     * @param id The ID of the match to delete.
     * @return Empty ResponseEntity indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        matchService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes multiple matches by their IDs.
     * Example: DELETE /api/matches
     * Request Body: [1, 2, 3]
     *
     * @param ids The list of match IDs to delete.
     * @return Empty ResponseEntity with HTTP 200 status.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteByIds(@RequestBody List<Long> ids) {
        matchService.deleteByIds(ids);
        return ResponseEntity.ok().build();
    }
    // </editor-fold>

    // <editor-fold desc="PUT endpoints">
    /**
     * Updates an existing match.
     * Example: PUT /api/matches/1
     * Request Body: { "description": "OSFP-PAO", "matchDate": "...", ... }
     *
     * @param id       The ID of the match to update (from URL).
     * @param matchDTO The MatchDTO with updated data.
     * @return ResponseEntity with the updated MatchDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatchDTO> updateMatch(@PathVariable Long id, @RequestBody MatchDTO matchDTO){
        Match match = MatchMapper.toEntity(matchDTO);
        Match updatedMatch = matchService.update(id, match);
        return ResponseEntity.ok(MatchMapper.toDTO(updatedMatch));
    }
    // </editor-fold>

    // <editor-fold desc="PATCH endpoints">
    /**
     * Partially updates a match by its ID.
     * Example: PATCH /api/matches/1 with body {"teamA": "AEK"}
     * Request Body: Map of fields to update.
     *
     * @param id The ID of the match to update.
     * @param updates Fields and values to be updated (e.g., teamA, teamB, description).
     * @return The updated MatchDTO.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<MatchDTO> patchMatch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Match updatedMatch = matchService.partialUpdate(id, updates);
        return ResponseEntity.ok(MatchMapper.toDTO(updatedMatch));
    }
    // </editor-fold>
}
