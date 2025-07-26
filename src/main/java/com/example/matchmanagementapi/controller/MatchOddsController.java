package com.example.matchmanagementapi.controller;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.MatchOdds;
import com.example.matchmanagementapi.dto.MatchDTO;
import com.example.matchmanagementapi.dto.MatchOddsDTO;
import com.example.matchmanagementapi.dto.MatchOddsMapper;
import com.example.matchmanagementapi.service.MatchOddsService;
import com.example.matchmanagementapi.service.MatchService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/api/matchOdds")
@RequiredArgsConstructor
public class MatchOddsController {
    private final MatchOddsService matchOddsService;
    private final MatchOddsMapper matchOddsMapper;
    private final ObjectMapper objectMapper;
    private final MatchService matchService;

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

    // <editor-fold desc="POST endpoints">
    /**
     * Saves one or more matchOdds records to the database.
     * Example:
     * - Single insert: POST /api/matchOdds
     *   Request Body:
     *   {
     *     "specifier": "1",
     *     "odd": 2.5,
     *     "match": {
     *       "id": 5
     *     }
     *   }
     * - Batch insert: POST /api/matchOdds
     *   Request Body:
     *   [
     *     {
     *       "specifier": "1",
     *       "odd": 2.5,
     *       "match": {
     *         "id": 5
     *       }
     *     },
     *     {
     *       "specifier": "X",
     *       "odd": 3.0,
     *       "match": {
     *         "id": 5
     *       }
     *     }
     *   ]
     *
     * @param body The request body containing a single MatchOddsDTO or a list of MatchOddsDTOs.
     * @return ResponseEntity containing the saved MatchOddsDTO or list of MatchOddsDTOs.
     */
    @PostMapping
    public ResponseEntity<?> saveMatchOdds(@RequestBody Object body) {
        if (body instanceof List<?> list && !list.isEmpty() && list.getFirst() instanceof LinkedHashMap) {
            // Batch insert
            List<MatchOddsDTO> matchOddsDTOs = objectMapper.convertValue(body, new TypeReference<>() {});
            List<MatchOdds> matchOddsEntities = matchOddsDTOs.stream().map(dto -> {
                Long matchId = dto.getMatch().getId();
                Match match = matchService.find(matchId);
                return new MatchOdds(match, dto.getSpecifier(), dto.getOdd());
            }).toList();
            List<MatchOdds> saved = matchOddsService.saveAll(matchOddsEntities);
            return ResponseEntity.ok(matchOddsMapper.toDTO(saved));
        } else {
            // Single insert
            MatchOddsDTO matchOddsDTO = objectMapper.convertValue(body, MatchOddsDTO.class);
            Long matchId = matchOddsDTO.getMatch().getId();
            Match match = matchService.find(matchId);
            MatchOdds matchOdds = matchOddsMapper.toEntity(matchOddsDTO);
            MatchOdds saved = matchOddsService.save(matchOdds);
            return ResponseEntity.ok(matchOddsMapper.toDTO(saved));
        }
    }

    // </editor-fold>

    // <editor-fold desc="DELETE endpoints">
    /**
     * Deletes a matchOdds by its ID.
     * Example: DELETE /api/matchOdds/1
     *
     * @param id The ID of the matchOdds to delete.
     * @return Empty ResponseEntity indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        matchOddsService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes multiple matchOdds by their IDs.
     * Example: DELETE /api/matchOdds
     * Request Body: [1, 2, 3]
     *
     * @param ids The list of matchOdds IDs to delete.
     * @return Empty ResponseEntity with HTTP 200 status.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteByIds(@RequestBody List<Long> ids) {
        matchOddsService.deleteByIds(ids);
        return ResponseEntity.ok().build();
    }
    // </editor-fold>

    // <editor-fold desc="PUT endpoints">
    /**
     * Updates an existing matchOdds.
     * Example: PUT /api/matchOdds/1
     * Request Body: { {
     *          "specifier": "1",
     *           "odd": 2.5,
     *           "match": {
     *             "id": 5
     *           }
     *         } }
     *
     * @param id       The ID of the matchOdds to update (from URL).
     * @param matchOddsDTO The MatchOddsDTO with updated data.
     * @return ResponseEntity with the updated MatchOddsDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatchOddsDTO> updateMatchOdds(@PathVariable Long id, @RequestBody MatchOddsDTO matchOddsDTO){
        matchOddsDTO.setId(id);

        Long matchId = matchOddsDTO.getMatch().getId();
        Match match = matchService.find(matchId);

        MatchOdds matchOdds = matchOddsMapper.toEntity(matchOddsDTO);
        MatchOdds updatedMatchOdds = matchOddsService.update(matchOdds);
        return ResponseEntity.ok(matchOddsMapper.toDTO(updatedMatchOdds));
    }

    /**
     * Updates a list of matchOdds in the database.
     * Example: PUT /api/matchOdds/batch
     * Request Body: [ {
     *           "specifier": "1",
     *           "odd": 2.5,
     *           "match": {
     *             "id": 5
     *           }
     *         }, ... ]
     *
     * @param matchOddsDTOs The list of MatchOddsDTOs to update.
     * @return ResponseEntity containing the list of updated MatchOddsDTOs.
     */
    @PutMapping("/batch")
    public ResponseEntity<List<MatchOddsDTO>> updateMatchOddsBatch(@RequestBody List<MatchOddsDTO> matchOddsDTOs){
        for (MatchOddsDTO dto : matchOddsDTOs) {
            Long matchId = dto.getMatch().getId();
            Match match = matchService.find(matchId);
            MatchDTO matchDTO = new MatchDTO();
            matchDTO.setId(match.getId());
            dto.setMatch(matchDTO);
        }

        List<MatchOdds> matchOdds = matchOddsMapper.toEntity(matchOddsDTOs);
        List<MatchOdds> updatedMatchOdds = matchOddsService.update(matchOdds);
        return ResponseEntity.ok(matchOddsMapper.toDTO(updatedMatchOdds));
    }
    // </editor-fold>
}
