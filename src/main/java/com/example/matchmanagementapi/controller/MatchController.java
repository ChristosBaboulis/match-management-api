package com.example.matchmanagementapi.controller;

import com.example.matchmanagementapi.dto.MatchDTO;
import com.example.matchmanagementapi.dto.MatchMapper;
import com.example.matchmanagementapi.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {
    @Autowired
    private MatchService matchService;
    @Autowired
    private MatchMapper matchMapper;

    /**
     * Returns a list of all matches.
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
     * Example: GET /api/matches/searchByDateBefore?matchDate=2025-08-01
     *
     * @param matchDate The exclusive upper bound date (matches strictly before this date).
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
     *
     * Example: GET /api/matches/searchByDateAfter?matchDate=2025-08-01
     *
     * @param matchDate The exclusive lower bound date (matches strictly after this date).
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
     *
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
}
