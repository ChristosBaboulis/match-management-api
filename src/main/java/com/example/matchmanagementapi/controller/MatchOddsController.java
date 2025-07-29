package com.example.matchmanagementapi.controller;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.MatchOdds;
import com.example.matchmanagementapi.dto.MatchOddsDTO;
import com.example.matchmanagementapi.dto.MatchOddsMapper;
import com.example.matchmanagementapi.service.MatchOddsService;
import com.example.matchmanagementapi.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/matchOdds")
@RequiredArgsConstructor
public class MatchOddsController {
    private final MatchOddsService matchOddsService;
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
        List<MatchOddsDTO> result = MatchOddsMapper.toDTO(
                matchOddsService.searchMatchOdds(specifier, odd, oddOver, oddUnder, matchId)
        );
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
        MatchOddsDTO result = MatchOddsMapper.toDTO(matchOddsService.find(id));
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
     * Creates a single MatchOdds entry.
     * Example: POST /api/matchOdds
     * Request Body:
     * {
     *   "matchId": 5,
     *   "specifier": "1",
     *   "odd": 2.5
     * }
     *
     * @param dto The MatchOddsDTO containing the data for the new match odd.
     * @return ResponseEntity containing the created MatchOddsDTO.
     */
    @PostMapping
    public ResponseEntity<MatchOddsDTO> saveSingle(@RequestBody MatchOddsDTO dto) {
        Match match = matchService.find(dto.getMatchId());
        MatchOdds entity = MatchOddsMapper.toEntity(dto, match);
        MatchOdds saved = matchOddsService.save(entity);
        return ResponseEntity.ok(MatchOddsMapper.toDTO(saved));
    }

    /**
     * Creates multiple MatchOdds entries in batch.
     * Example: POST /api/matchOdds/batch
     * Request Body:
     * [
     *   {
     *     "matchId": 5,
     *     "specifier": "1",
     *     "odd": 2.5
     *   },
     *   {
     *     "matchId": 6,
     *     "specifier": "X",
     *     "odd": 3.1
     *   }
     * ]
     *
     * @param dtos List of MatchOddsDTOs to be created.
     * @return ResponseEntity containing the list of created MatchOddsDTOs.
     */
    @PostMapping("/batch")
    public ResponseEntity<List<MatchOddsDTO>> saveBatch(@RequestBody List<MatchOddsDTO> dtos) {
        List<MatchOdds> entities = new ArrayList<>();
        for (MatchOddsDTO dto : dtos) {
            Match match = matchService.find(dto.getMatchId());
            entities.add(MatchOddsMapper.toEntity(dto, match));
        }
        List<MatchOdds> saved = matchOddsService.saveAll(entities);
        return ResponseEntity.ok(MatchOddsMapper.toDTO(saved));
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
        Match match = matchService.find(matchOddsDTO.getMatchId());

        MatchOdds entity = MatchOddsMapper.toEntity(matchOddsDTO, match);
        MatchOdds updated = matchOddsService.update(id, entity);
        return ResponseEntity.ok(MatchOddsMapper.toDTO(updated));
    }
    // </editor-fold>

    // <editor-fold desc="PATCH endpoints">
    /**
     * Partially updates a MatchOdds by its ID.
     * Example: PATCH /api/matchOdds/1 with body {"odd": 2.9}
     * Request Body: Map of fields to update (e.g., specifier, odd, match.id).
     *
     * @param id The ID of the MatchOdds to update.
     * @param updates Fields and values to be updated.
     * @return The updated MatchOddsDTO.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<MatchOddsDTO> patchMatchOdds(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        if (updates.containsKey("match")) {
            Object matchObj = updates.get("match");
            if (matchObj instanceof Map<?, ?> matchMap) {
                Object matchIdObj = matchMap.get("id");
                if (matchIdObj instanceof Number matchIdNumber) {
                    Long matchId = matchIdNumber.longValue();
                    matchService.find(matchId);
                }
            }
        }

        MatchOdds updatedMatchOdds = matchOddsService.partialUpdate(id, updates);
        return ResponseEntity.ok(MatchOddsMapper.toDTO(updatedMatchOdds));
    }
    // </editor-fold>
}
