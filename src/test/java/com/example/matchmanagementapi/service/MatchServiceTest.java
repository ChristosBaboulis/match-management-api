package com.example.matchmanagementapi.service;

import com.example.matchmanagementapi.Initializer;
import com.example.matchmanagementapi.domain.Match;
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
}
