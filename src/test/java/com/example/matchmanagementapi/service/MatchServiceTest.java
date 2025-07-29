package com.example.matchmanagementapi.service;

import com.example.matchmanagementapi.Initializer;
import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.Sport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ActiveProfiles("test")
@SpringBootTest
public class MatchServiceTest extends Initializer {
    @Autowired
    private MatchService matchService;

    @Test
    void testSaveAndFindAll(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        List<Match> matchList = matchService.findAll();
        Assertions.assertEquals(1, matchList.size());
    }

    @Test
    void testSaveAll(){
        List<Match> matches = new ArrayList<>();
        for (int i=0; i<5; i++){
            Match m = new Match(
                    "Team" + i + "-Team" + (i+1),
                    testMatchDate,
                    testMatchTime,
                    "Team" + i,
                    "Team" + (i+1),
                    testSport
            );
            matches.add(m);
        }

        matchService.saveAll(matches);
        List<Match> matchList = matchService.findAll();
        Assertions.assertEquals(5, matchList.size());
    }

    @Test
    void testSearchMatches_withFilters() {
        Match match1 = new Match("OSFP-PAO", testMatchDate, LocalTime.of(20, 0), "OSFP", "PAO", Sport.Football);
        Match match2 = new Match("AEK-PAO", testMatchDate.plusDays(1), testMatchTime.plusHours(1), "AEK", "PAO", Sport.Football);
        Match match3 = new Match("OSFP-ARIS", testMatchDate.minusDays(1), testMatchTime.minusHours(1), "OSFP", "ARIS", Sport.Basketball);

        matchService.saveAll(List.of(match1, match2, match3));

        List<Match> result = matchService.searchMatches(
                null,
                "OSFP",
                null,
                Sport.Football,
                null,
                testMatchDate.plusDays(2),
                null,
                null,
                null,
                LocalTime.of(18, 0)
        );

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("OSFP-PAO", result.getFirst().getDescription());
    }

    @Test
    void testGetRecordsCount(){
        for (int i=0; i<5; i++){
            Match m = new Match(
                    "Team" + i + "-Team" + (i+1),
                    testMatchDate,
                    testMatchTime,
                    "Team" + i,
                    "Team" + (i+1),
                    testSport
            );
            matchService.save(m);
        }

        long count = matchService.getRecordsCount();
        Assertions.assertEquals(5, count);
    }

    @Test
    void testDeleteById(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        matchService.deleteById(savedMatch.getId());
        List<Match> matchList = matchService.findAll();
        Assertions.assertEquals(0, matchList.size());
    }

    @Test
    void testDeleteByIds(){
        List<Long> ids = new ArrayList<>();
        for (int i=0; i<5; i++){
            Match m = new Match(
                    "Team" + i + "-Team" + (i+1),
                    testMatchDate,
                    testMatchTime,
                    "Team" + i,
                    "Team" + (i+1),
                    testSport
            );
            Match mAdded = matchService.save(m);
            ids.add(mAdded.getId());
        }

        matchService.deleteByIds(ids);
        List<Match> matchList = matchService.findAll();
        Assertions.assertEquals(0, matchList.size());
    }

    @Test
    void testUpdate_shouldRegenerateDescriptionAndUpdateAllFields() {
        Match original = new Match(
                "OSFP-PAO",
                testMatchDate,
                testMatchTime,
                "OSFP",
                "PAO",
                testSport
        );
        Match saved = matchService.save(original);

        Match incoming = new Match();
        incoming.setTeamA("AEK");
        incoming.setTeamB("ARIS");
        incoming.setDescription("Wrong-Description"); // θα αγνοηθεί
        incoming.setMatchDate(testMatchDate.plusDays(1));
        incoming.setMatchTime(testMatchTime.plusHours(2));
        incoming.setSport(Sport.Basketball);

        Match updated = matchService.update(saved.getId(), incoming);

        Assertions.assertEquals("AEK", updated.getTeamA());
        Assertions.assertEquals("ARIS", updated.getTeamB());
        Assertions.assertEquals("AEK-ARIS", updated.getDescription()); // regenerated
        Assertions.assertEquals(testMatchDate.plusDays(1), updated.getMatchDate());
        Assertions.assertEquals(testMatchTime.plusHours(2), updated.getMatchTime());
        Assertions.assertEquals(Sport.Basketball, updated.getSport());
    }


    @Test
    void testPartialUpdate_teamAChange_updatesDescriptionAutomatically() {
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        Map<String, Object> updates = Map.of("teamA", "AEK");
        Match updatedMatch = matchService.partialUpdate(savedMatch.getId(), updates);

        Assertions.assertEquals("AEK", updatedMatch.getTeamA());
        Assertions.assertEquals("PAO", updatedMatch.getTeamB());
        Assertions.assertEquals("AEK-PAO", updatedMatch.getDescription());
    }

    @Test
    void testPartialUpdate_descriptionChange_updatesTeamsAutomatically() {
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        Map<String, Object> updates = Map.of("description", "AEK-ARIS");
        Match updatedMatch = matchService.partialUpdate(savedMatch.getId(), updates);

        Assertions.assertEquals("AEK", updatedMatch.getTeamA());
        Assertions.assertEquals("ARIS", updatedMatch.getTeamB());
        Assertions.assertEquals("AEK-ARIS", updatedMatch.getDescription());
    }

    @Test
    void testPartialUpdate_invalidDescriptionFormat_throwsException() {
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        Map<String, Object> updates = Map.of("description", "INVALID_DESCRIPTION");

        Assertions.assertThrows(IllegalArgumentException.class, () -> matchService.partialUpdate(savedMatch.getId(), updates));
    }
}
