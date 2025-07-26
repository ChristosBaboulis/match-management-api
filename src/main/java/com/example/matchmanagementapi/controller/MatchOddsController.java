package com.example.matchmanagementapi.controller;

import com.example.matchmanagementapi.dto.MatchOddsDTO;
import com.example.matchmanagementapi.dto.MatchOddsMapper;
import com.example.matchmanagementapi.service.MatchOddsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matchOdds")
@RequiredArgsConstructor
public class MatchOddsController {
    private final MatchOddsService matchOddsService;
    private final MatchOddsMapper matchOddsMapper;
    private final ObjectMapper objectMapper;

    // <editor-fold desc="GET endpoints">
    /**
     * Retrieves a filtered list of match odds.
     * Example: GET /api/matchOdds?specifier=1&oddOver=2.0&matchId=5
     *
     * @param specifier   Optional specifier value (e.g., "1", "X", "2").
     * @param odd         Optional exact odd value.
     * @param oddOver     Optional lower bound (exclusive) for odd (e.g., odd > 2.0).
     * @param oddUnder    Optional upper bound (exclusive) for odd (e.g., odd < 3.5).
     * @param matchId     Optional match ID to filter odds by specific match.
     * @return ResponseEntity containing the filtered list of MatchOddsDTOs.
     */
    @GetMapping
    public ResponseEntity<List<MatchOddsDTO>> searchMatchOdds(
            @RequestParam(required = false) String specifier,
            @RequestParam(required = false) Double odd,
            @RequestParam(required = false) Double oddOver,
            @RequestParam(required = false) Double oddUnder,
            @RequestParam(required = false) Long matchId
    ) {
        List<MatchOddsDTO> result = matchOddsMapper.toDTO(
                matchOddsService.searchMatchOdds(specifier, odd, oddOver, oddUnder, matchId)
        );
        return ResponseEntity.ok(result);
    }

    /**
     * Returns a list of matchOdds by their IDs.
     * Supports both:
     * - GET /api/matchOdds/byIdsList?ids=1&ids=2&ids=3
     * - GET /api/matchOdds/byIdsList?ids=1,2,3
     *
     * @param ids List of matchOdds IDs to retrieve.
     * @return ResponseEntity containing the list of MatchOddsDTOs.
     */
    @GetMapping("/byIdsList")
    public ResponseEntity<List<MatchOddsDTO>> getMatchesByMatchOddsIds(@RequestParam List<Long> ids){
        List<MatchOddsDTO> result = matchOddsMapper.toDTO(matchOddsService.findAll(ids));
        return ResponseEntity.ok(result);
    }

    /**
     * Returns a matchOdds by its ID.
     * Endpoint:
     * - GET /api/matchOdds/{id}
     *
     * @param id The ID of the matchOdds to retrieve.
     * @return ResponseEntity containing the MatchOddsDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatchOddsDTO> getMatchOddsById(@PathVariable Long id){
        MatchOddsDTO result = matchOddsMapper.toDTO(matchOddsService.find(id));
        return ResponseEntity.ok(result);
    }

    /**
     * Returns the total number of matchOdds records.
     * Example: GET /api/matchOdds/count
     *
     * @return ResponseEntity containing the total count of matchOdds.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getCount(){
        long result = matchOddsService.getRecordsCount();
        return ResponseEntity.ok(result);
    }
    // </editor-fold>
}
