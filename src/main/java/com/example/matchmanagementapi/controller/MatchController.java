package com.example.matchmanagementapi.controller;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.Sport;
import com.example.matchmanagementapi.dto.MatchDTO;
import com.example.matchmanagementapi.dto.MatchMapper;
import com.example.matchmanagementapi.service.MatchService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {
    private final MatchService matchService;
    private final MatchMapper matchMapper;

    public MatchController(MatchService matchService, MatchMapper matchMapper) {
        this.matchService = matchService;
        this.matchMapper = matchMapper;
    }

    // <editor-fold desc="GET endpoints">
    /**
     * Returns a list of all matches.
     * Endpoint:
     * - GET /api/matches
     *
     * @return ResponseEntity containing the list of all MatchDTOs.
     */
    @GetMapping
    public ResponseEntity<List<MatchDTO>> getAllMatches() {
        List<MatchDTO> result = matchMapper.toDTO(matchService.findAll());
        return ResponseEntity.ok(result);
    }

    /**
     * Returns a list of matches by their IDs.
     * Supports both:
     * - GET /api/matches/byIdsList?ids=1&ids=2&ids=3
     * - GET /api/matches/byIdsList?ids=1,2,3
     *
     * @param ids List of match IDs to retrieve.
     * @return ResponseEntity containing the list of MatchDTOs.
     */
    @GetMapping("/byIdsList")
    public ResponseEntity<List<MatchDTO>> getMatchesByMatchIds(@RequestParam List<Long> ids){
        List<MatchDTO> result = matchMapper.toDTO(matchService.findAll(ids));
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
        MatchDTO result = matchMapper.toDTO(matchService.find(id));
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a list of matches based on their description.
     * Example: GET /api/matches/searchByDescription?description=Test Match
     *
     * @param description The description of the match(es) to retrieve.
     * @return ResponseEntity containing the list of MatchDTOs.
     */
    @GetMapping("/searchByDescription")
    public ResponseEntity<List<MatchDTO>> getMatchesByDescription(@RequestParam String description){
        List<MatchDTO> result = matchMapper.toDTO(matchService.findByDescription(description));
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a list of matches taking place on the given date.
     * Example: GET /api/matches/searchByMatchDate?matchDate=2025-08-01
     *
     * @param matchDate The date of the match(es) to retrieve.
     * @return ResponseEntity containing the list of MatchDTOs.
     */
    @GetMapping("/searchByMatchDate")
    public ResponseEntity<List<MatchDTO>> getMatchesByMatchDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate matchDate
    ){
        List<MatchDTO> result = matchMapper.toDTO(matchService.findByMatchDate(matchDate));
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a list of matches that take place between the given start and end dates (inclusive).
     * Example: GET /api/matches/searchByDateRange?startDate=2025-08-01&endDate=2025-08-10
     *
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @return ResponseEntity containing the list of MatchDTOs.
     */

    @GetMapping("/searchByDateRange")
    public ResponseEntity<List<MatchDTO>> getMatchesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ){
        List<MatchDTO> result = matchMapper.toDTO(matchService.findByMatchDateBetween(startDate, endDate));
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a list of matches that take place before the given date.
     * Example: GET /api/matches/searchByDateBefore?matchDate=2025-08-01
     *
     * @param matchDate The exclusive upper-bound date (matches strictly before this date).
     * @return ResponseEntity containing the list of MatchDTOs.
     */
    @GetMapping("/searchByDateBefore")
    public ResponseEntity<List<MatchDTO>> getMatchesBeforeDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate matchDate
    ){
        List<MatchDTO> result = matchMapper.toDTO(matchService.findByMatchDateBefore(matchDate));
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a list of matches that take place after the given date.
     * Example: GET /api/matches/searchByDateAfter?matchDate=2025-08-01
     *
     * @param matchDate The exclusive lower-bound date (matches strictly after this date).
     * @return ResponseEntity containing the list of MatchDTOs.
     */
    @GetMapping("/searchByDateAfter")
    public ResponseEntity<List<MatchDTO>> getMatchesAfterDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate matchDate
    ){
        List<MatchDTO> result = matchMapper.toDTO(matchService.findByMatchDateAfter(matchDate));
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a list of matches that start at the given time.
     * Example: GET /api/matches/searchByMatchTime?matchTime=20:00:00
     *
     * @param matchTime The exact time the match starts.
     * @return ResponseEntity containing the list of MatchDTOs.
     */
    @GetMapping("/searchByMatchTime")
    public ResponseEntity<List<MatchDTO>> getMatchesByTime(
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime matchTime
    ){
        List<MatchDTO> result = matchMapper.toDTO(matchService.findByMatchTime(matchTime));
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a list of matches that start before the given time.
     * Example: GET /api/matches/searchByTimeBefore?matchTime=20:00:00
     *
     * @param matchTime The exclusive upper-bound time (matches strictly before this time).
     * @return ResponseEntity containing the list of MatchDTOs.
     */
    @GetMapping("/searchByTimeBefore")
    public ResponseEntity<List<MatchDTO>> getMatchesByTimeBefore(
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime matchTime
    ){
        List<MatchDTO> result = matchMapper.toDTO(matchService.findByMatchTimeBefore(matchTime));
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a list of matches that start after the given time.
     * Example: GET /api/matches/searchByTimeAfter?matchTime=20:00:00
     *
     * @param matchTime The exclusive lower-bound time (matches strictly after this time).
     * @return ResponseEntity containing the list of MatchDTOs.
     */
    @GetMapping("/searchByTimeAfter")
    public ResponseEntity<List<MatchDTO>> getMatchesByTimeAfter(
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime matchTime
    ){
        List<MatchDTO> result = matchMapper.toDTO(matchService.findByMatchTimeAfter(matchTime));
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a list of matches where the specified team played as the first team (Team A).
     * Example: GET /api/matches/searchByFirstTeam?teamA=TeamA
     *
     * @param teamA The name of the first team.
     * @return ResponseEntity containing the list of MatchDTOs.
     */
    @GetMapping("/searchByFirstTeam")
    public ResponseEntity<List<MatchDTO>> getMatchesByFirstTeam(@RequestParam String teamA){
        List<MatchDTO> result = matchMapper.toDTO(matchService.findByTeamA(teamA));
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a list of matches where the specified team played as the second team (Team B).
     * Example: GET /api/matches/searchByFirstTeam?teamB=TeamB
     *
     * @param teamB The name of the second team.
     * @return ResponseEntity containing the list of MatchDTOs.
     */
    @GetMapping("/searchBySecondTeam")
    public ResponseEntity<List<MatchDTO>> getMatchesBySecondTeam(@RequestParam String teamB){
        List<MatchDTO> result = matchMapper.toDTO(matchService.findByTeamB(teamB));
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a list of matches where the specified teams played against each other.
     * Example: GET /api/matches/searchByTeams?teamA=OSFP&teamB=PAO
     *
     * @param teamA The name of the first team.
     * @param teamB The name of the second team.
     * @return ResponseEntity containing the list of MatchDTOs.
     */
    @GetMapping("/searchByTeams")
    public ResponseEntity<List<MatchDTO>> getMatchesByTeams(@RequestParam String teamA, @RequestParam String teamB){
        List<MatchDTO> result = matchMapper.toDTO(matchService.findByTeamAAndTeamB(teamA, teamB));
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a list of matches based on the specified sport.
     * Example: GET /api/matches/searchBySport?sport=Football
     *
     * @param sport The sport type of the matches.
     * @return ResponseEntity containing the list of MatchDTOs.
     */
    @GetMapping("/searchBySport")
    public ResponseEntity<List<MatchDTO>> getMatchesBySport(@RequestParam Sport sport){
        List<MatchDTO> result = matchMapper.toDTO(matchService.findBySport(sport));
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
    // </editor-fold>

    // <editor-fold desc="POST endpoints">
    /**
     * Saves a list of matches to the database.
     * Example: POST /api/matches/saveAll
     * Request Body: [ { "description": "OSFP-PAO", "matchDate": "...", ... }, ... ]
     *
     * @param matchDTOs The list of MatchDTOs to save.
     * @return ResponseEntity containing the list of saved MatchDTOs.
     */
    @PostMapping("/saveAll")
    public ResponseEntity<List<MatchDTO>> saveAllMatches(@RequestBody List<MatchDTO> matchDTOs){
        List<Match> matches = matchMapper.toEntity(matchDTOs);
        List<Match> savedMatches = matchService.saveAll(matches);
        return ResponseEntity.ok(matchMapper.toDTO(savedMatches));
    }

    /**
     * Saves a single match to the database.
     * Example: POST /api/matches
     * Request Body: { "description": "OSFP-PAO", "matchDate": "...", ... }
     *
     * @param matchDTO The MatchDTO to save.
     * @return ResponseEntity containing the saved MatchDTO.
     */
    @PostMapping
    public ResponseEntity<MatchDTO> saveMatch(@RequestBody MatchDTO matchDTO){
        Match match = matchMapper.toEntity(matchDTO);
        Match savedMatch = matchService.save(match);
        return ResponseEntity.ok(matchMapper.toDTO(savedMatch));
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
     * Example: DELETE /api/matches/deleteByIds?ids=1&ids=2&ids=3
     *
     * @param ids The list of match IDs to delete.
     * @return Empty ResponseEntity with HTTP 200 status.
     */
    @DeleteMapping("/deleteByIds")
    public ResponseEntity<Void> deleteByIds(@RequestParam List<Long> ids){
        matchService.deleteByIds(ids);
        return ResponseEntity.ok().build();
    }
    // </editor-fold>
}
