package com.example.matchmanagementapi.service;

import com.example.matchmanagementapi.Initializer;
import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.Sport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

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
    void testFindByDescription(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        List<Match> matchList = matchService.findByDescription(savedMatch.getDescription());
        Assertions.assertEquals(1, matchList.size());
    }

    @Test
    void testFindByMatchDate(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        List<Match> matchList = matchService.findByMatchDate(savedMatch.getMatchDate());
        Assertions.assertEquals(1, matchList.size());
    }

    @Test
    void testFindByMatchDateBetween(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        List<Match> matchList = matchService.findByMatchDateBetween(testMatchDateBefore, testMatchDateAfter);
        Assertions.assertEquals(1, matchList.size());
    }

    @Test
    void testFindByMatchDateBefore(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        List<Match> matchList = matchService.findByMatchDateBefore(testMatchDateAfter);
        Assertions.assertEquals(1, matchList.size());
    }

    @Test
    void testFindByMatchDateAfter(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        List<Match> matchList = matchService.findByMatchDateAfter(testMatchDateBefore);
        Assertions.assertEquals(1, matchList.size());
    }

    @Test
    void testFindByMatchTime(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        List<Match> matchList = matchService.findByMatchTime(testMatchTime);
        Assertions.assertEquals(1, matchList.size());
    }

    @Test
    void testFindByMatchTimeBefore(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        List<Match> matchList = matchService.findByMatchTimeBefore(testMatchTimeAfter);
        Assertions.assertEquals(1, matchList.size());
    }

    @Test
    void testFindByMatchTimeAfter(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        List<Match> matchList = matchService.findByMatchTimeAfter(testMatchTimeBefore);
        Assertions.assertEquals(1, matchList.size());
    }

    @Test
    void testFindByTeamA(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        List<Match> matchList = matchService.findByTeamA(savedMatch.getTeamA());
        Assertions.assertEquals(1, matchList.size());
    }

    @Test
    void testFindByTeamB(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        List<Match> matchList = matchService.findByTeamB(savedMatch.getTeamB());
        Assertions.assertEquals(1, matchList.size());
    }

    @Test
    void testFindByTeamAAndTeamB(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        List<Match> matchList = matchService.findByTeamAAndTeamB(savedMatch.getTeamA(), savedMatch.getTeamB());
        Assertions.assertEquals(1, matchList.size());
    }

    @Test
    void testFindBySport(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        List<Match> matchList = matchService.findBySport(savedMatch.getSport());
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
    void TestUpdateMatchDate(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        matchService.updateMatchDate(savedMatch.getId(), testMatchDateBefore);
        Assertions.assertEquals(testMatchDateBefore, matchService.find(savedMatch.getId()).getMatchDate());
    }

    @Test
    void testUpdateMatchTime(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        matchService.updateMatchTime(savedMatch.getId(), testMatchTimeBefore);
        Assertions.assertEquals(testMatchTimeBefore, matchService.find(savedMatch.getId()).getMatchTime());
    }

    @Test
    void testupdateDescription(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        matchService.updateDescription(savedMatch.getId(), "AEK-PAOK");
        Assertions.assertEquals("AEK-PAOK", matchService.find(savedMatch.getId()).getDescription());
        Assertions.assertEquals("AEK", matchService.find(savedMatch.getId()).getTeamA());
        Assertions.assertEquals("PAOK", matchService.find(savedMatch.getId()).getTeamB());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> matchService.updateDescription(savedMatch.getId(), "PAOK-PAO-AEK"));
    }

    @Test
    void testUpdateTeamA(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        matchService.updateTeamA(savedMatch.getId(), "AEK");
        Assertions.assertEquals("AEK", matchService.find(savedMatch.getId()).getTeamA());
        Assertions.assertEquals("AEK-PAO", matchService.find(savedMatch.getId()).getDescription());
    }

    @Test
    void testUpdateTeamB(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        matchService.updateTeamB(savedMatch.getId(), "AEK");
        Assertions.assertEquals("AEK", matchService.find(savedMatch.getId()).getTeamB());
        Assertions.assertEquals("OSFP-AEK", matchService.find(savedMatch.getId()).getDescription());
    }

    @Test
    void testUpdateBothTeams(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        matchService.updateBothTeams(savedMatch.getId(), "AEK", "PAOK");
        Assertions.assertEquals("AEK", matchService.find(savedMatch.getId()).getTeamA());
        Assertions.assertEquals("PAOK", matchService.find(savedMatch.getId()).getTeamB());
        Assertions.assertEquals("AEK-PAOK", matchService.find(savedMatch.getId()).getDescription());
    }

    @Test
    void testUpdateSport(){
        Match savedMatch = matchService.save(match);
        Assertions.assertNotNull(savedMatch);

        matchService.updateSport(savedMatch.getId(), testSport);
        Assertions.assertEquals(testSport, matchService.find(savedMatch.getId()).getSport());
    }
}
