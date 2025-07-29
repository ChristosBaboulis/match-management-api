package com.example.matchmanagementapi.service;

import com.example.matchmanagementapi.Initializer;
import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.MatchOdds;
import com.example.matchmanagementapi.domain.Sport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ActiveProfiles("test")
@SpringBootTest
public class MatchOddsServiceTest extends Initializer {

    @Autowired
    private MatchOddsService matchOddsService;

    @Autowired
    private MatchService matchService;

    @Test
    void testSaveAndFindAll() {
        Match savedMatch = matchService.save(match);
        MatchOdds mo = new MatchOdds(savedMatch, "1", 1.90);

        MatchOdds savedOdds = matchOddsService.save(mo);
        Assertions.assertNotNull(savedOdds);

        List<MatchOdds> oddsList = matchOddsService.findAll();
        Assertions.assertEquals(1, oddsList.size());
    }

    @Test
    void testSaveAll() {
        Match savedMatch = matchService.save(match);

        List<MatchOdds> oddsList = new ArrayList<>();
        oddsList.add(new MatchOdds(savedMatch, "1", 1.90));
        oddsList.add(new MatchOdds(savedMatch, "X", 3.25));
        oddsList.add(new MatchOdds(savedMatch, "2", 4.10));

        matchOddsService.saveAll(oddsList);

        List<MatchOdds> result = matchOddsService.findAll();
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void testSearchMatchOdds_withFilters() {
        Match savedMatch = matchService.save(match);

        matchOddsService.saveAll(List.of(
                new MatchOdds(savedMatch, "1", 1.90),
                new MatchOdds(savedMatch, "X", 3.20),
                new MatchOdds(savedMatch, "2", 4.00)
        ));

        List<MatchOdds> result = matchOddsService.searchMatchOdds("X", null, 3.0, 3.5, savedMatch.getId());

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("X", result.getFirst().getSpecifier());
        Assertions.assertEquals(3.20, result.getFirst().getOdd());
    }

    @Test
    void testGetRecordsCount() {
        Match savedMatch = matchService.save(match);

        for (int i = 0; i < 5; i++) {
            MatchOdds mo = new MatchOdds(savedMatch, "SP" + i, 1.50 + i);
            matchOddsService.save(mo);
        }

        long count = matchOddsService.getRecordsCount();
        Assertions.assertEquals(5, count);
    }

    @Test
    void testDeleteById() {
        Match savedMatch = matchService.save(match);
        MatchOdds mo = new MatchOdds(savedMatch, "1", 1.90);
        MatchOdds savedOdds = matchOddsService.save(mo);

        matchOddsService.deleteById(savedOdds.getId());

        List<MatchOdds> oddsList = matchOddsService.findAll();
        Assertions.assertEquals(0, oddsList.size());
    }

    @Test
    void testDeleteByIds() {
        Match savedMatch = matchService.save(match);

        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MatchOdds mo = new MatchOdds(savedMatch, "SP" + i, 2.0 + i);
            MatchOdds saved = matchOddsService.save(mo);
            ids.add(saved.getId());
        }

        matchOddsService.deleteByIds(ids);

        List<MatchOdds> oddsList = matchOddsService.findAll();
        Assertions.assertEquals(0, oddsList.size());
    }

    @Test
    void testUpdate() {
        Match savedMatch = matchService.save(match);
        MatchOdds savedOdds = matchOddsService.save(new MatchOdds(savedMatch, "1", 1.90));

        savedOdds.setSpecifier("2");
        savedOdds.setOdd(2.25);
        MatchOdds updated = matchOddsService.update(savedOdds.getId(), savedOdds);

        Assertions.assertEquals("2", updated.getSpecifier());
        Assertions.assertEquals(2.25, updated.getOdd());
    }

    @Test
    void testPartialUpdate_specifierAndOdd() {
        Match savedMatch = matchService.save(match);
        MatchOdds savedOdds = matchOddsService.save(new MatchOdds(savedMatch, "1", 1.90));

        Map<String, Object> updates = Map.of(
                "specifier", "X",
                "odd", 3.15
        );

        MatchOdds updated = matchOddsService.partialUpdate(savedOdds.getId(), updates);

        Assertions.assertEquals("X", updated.getSpecifier());
        Assertions.assertEquals(3.15, updated.getOdd());
    }

    @Test
    void testPartialUpdate_withMatchChange() {
        Match savedMatch1 = matchService.save(new Match("M1", testMatchDate, testMatchTime, "A", "B", Sport.Football));
        Match savedMatch2 = matchService.save(new Match("M2", testMatchDate, testMatchTime, "C", "D", Sport.Football));

        MatchOdds savedOdds = matchOddsService.save(new MatchOdds(savedMatch1, "1", 1.80));

        Map<String, Object> updates = Map.of(
                "match", Map.of("id", savedMatch2.getId())
        );

        MatchOdds updated = matchOddsService.partialUpdate(savedOdds.getId(), updates);

        Assertions.assertEquals(savedMatch2.getId(), updated.getMatch().getId());
    }

    @Test
    void testPartialUpdate_invalidField_throwsException() {
        Match savedMatch = matchService.save(match);
        MatchOdds savedOdds = matchOddsService.save(new MatchOdds(savedMatch, "1", 1.90));

        Map<String, Object> updates = Map.of("invalidField", "value");

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchOddsService.partialUpdate(savedOdds.getId(), updates));
    }

    @Test
    void testPartialUpdate_invalidMatchFormat_throwsException() {
        Match savedMatch = matchService.save(match);
        MatchOdds savedOdds = matchOddsService.save(new MatchOdds(savedMatch, "1", 1.90));

        Map<String, Object> updates = Map.of("match", "invalidFormat");

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchOddsService.partialUpdate(savedOdds.getId(), updates));
    }
}
