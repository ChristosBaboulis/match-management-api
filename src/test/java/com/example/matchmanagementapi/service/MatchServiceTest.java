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

        List<Long> ids = List.of(matchList.getFirst().getId());
        List<Match> matchList2 = matchService.findAll(ids);
        Assertions.assertEquals(1, matchList2.size());
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
    void testUpdate(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        savedMatch.setSport(Sport.Basketball);
        Match updatedMatch = matchService.update(savedMatch);
        Assertions.assertEquals(Sport.Basketball, updatedMatch.getSport());

        savedMatch.setTeamA("TeamA");
        updatedMatch = matchService.update(savedMatch);
        Assertions.assertEquals("TeamA", updatedMatch.getTeamA());
    }

    @Test
    void testUpdateMass(){
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

        List<Match> savedMatches = matchService.saveAll(matches);
        for(Match m : savedMatches){
            m.setSport(Sport.Basketball);
        }

        List<Match> updatedMatches = matchService.update(savedMatches);
        for(Match m : updatedMatches){
            Assertions.assertEquals(Sport.Basketball, m.getSport());
        }

        List<Match> savedMatches2 = matchService.saveAll(matches);
        for(Match m : savedMatches2){
            m.setTeamA("TeamA");
        }

        List<Match> updatedMatches2 = matchService.update(savedMatches2);
        for(Match m : updatedMatches2){
            Assertions.assertEquals("TeamA", m.getTeamA());
        }
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
