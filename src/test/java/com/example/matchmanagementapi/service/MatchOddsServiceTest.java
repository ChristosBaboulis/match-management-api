package com.example.matchmanagementapi.service;

import com.example.matchmanagementapi.Initializer;
import com.example.matchmanagementapi.domain.MatchOdds;
import com.example.matchmanagementapi.repository.MatchRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class MatchOddsServiceTest extends Initializer {
    @Autowired
    MatchOddsService matchOddsService;
    @Autowired
    private MatchService matchService;

    @Test
    void testSaveAndFindAll(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findAll();
        Assertions.assertEquals(1, matchOddsList.size());

        List<Long> ids = List.of(matchOddsList.getFirst().getId());
        List<MatchOdds> matchOddsList2 = matchOddsService.findAll(ids);
        Assertions.assertEquals(1, matchOddsList2.size());
    }

    @Test
    void testFindByMatch(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findByMatch(savedMatchOdds.getMatch());
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals(savedMatchOdds.getMatch().getId(), matchOddsList.getFirst().getMatch().getId());
    }

    @Test
    void testFindByMatchId(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findByMatchId(savedMatchOdds.getMatch().getId());
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals(savedMatchOdds.getMatch().getId(), matchOddsList.getFirst().getMatch().getId());
    }

    @Test
    void testFindByMatchAndOdd(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findByMatchAndOdd(savedMatchOdds.getMatch(),  1.5);
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals(savedMatchOdds.getMatch().getId(), matchOddsList.getFirst().getMatch().getId());
        Assertions.assertEquals(1.5, matchOddsList.getFirst().getOdd());
    }

    @Test
    void testFindByOdd(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findByOdd(1.5);
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals(1.5, matchOddsList.getFirst().getOdd());
    }

    @Test
    void testFindByOddGreaterThan(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findByOddGreaterThan(1.4);
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals(1.5, matchOddsList.getFirst().getOdd());
    }

    @Test
    void testFindByOddGreaterThanEqual(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findByOddGreaterThanEqual(1.5);
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals(1.5, matchOddsList.getFirst().getOdd());
    }

    @Test
    void testFindByOddLessThan(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findByOddLessThan(1.6);
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals(1.5, matchOddsList.getFirst().getOdd());
    }

    @Test
    void testFindByOddLessThanEqual(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findByOddLessThanEqual(1.5);
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals(1.5, matchOddsList.getFirst().getOdd());
    }

    @Test
    void testFindByOddBetween(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findByOddBetween(1.4, 1.6);
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals(1.5, matchOddsList.getFirst().getOdd());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> matchOddsService.findByOddBetween(1.6, 1.4));
    }

    @Test
    void testFindBySpecifier(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findBySpecifier("X");
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals("X", matchOddsList.getFirst().getSpecifier());
    }

    @Test
    void testFindBySpecifierAndOdd(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findBySpecifierAndOdd("X", 1.5);
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals("X", matchOddsList.getFirst().getSpecifier());
        Assertions.assertEquals(1.5, matchOddsList.getFirst().getOdd());
    }

    @Test
    void testFindBySpecifierAndOddGreaterThan(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findBySpecifierAndOddGreaterThan("X", 1.4);
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals("X", matchOddsList.getFirst().getSpecifier());
        Assertions.assertEquals(1.5, matchOddsList.getFirst().getOdd());
    }

    @Test
    void testFindBySpecifierAndOddGreaterThanEqual(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findBySpecifierAndOddGreaterThanEqual("X",  1.5);
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals("X", matchOddsList.getFirst().getSpecifier());
        Assertions.assertEquals(1.5, matchOddsList.getFirst().getOdd());
    }

    @Test
    void testFindBySpecifierAndOddLessThan(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findBySpecifierAndOddLessThan("X", 1.6);
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals("X", matchOddsList.getFirst().getSpecifier());
        Assertions.assertEquals(1.5, matchOddsList.getFirst().getOdd());
    }

    @Test
    void testFindBySpecifierAndOddLessThanEqual(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        List<MatchOdds> matchOddsList = matchOddsService.findBySpecifierAndOddLessThanEqual("X", 1.5);
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals("X", matchOddsList.getFirst().getSpecifier());
        Assertions.assertEquals(1.5, matchOddsList.getFirst().getOdd());
    }

    @Test
    void testSaveAll(){
        matchService.save(match);
        List<MatchOdds> matchOddsList = new ArrayList<>();
        for (int i=0; i<3; i++){
            MatchOdds mo = new MatchOdds(
                    match,
                    "x",
                    (double)i
            );
            matchOddsList.add(mo);
        }

        List<MatchOdds> matchList = matchOddsService.saveAll(matchOddsList);
        Assertions.assertEquals(3, matchList.size());
    }

    @Test
    void testDelete(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        matchOddsService.delete(savedMatchOdds);
        List<MatchOdds> matchOddsList = matchOddsService.findAll();
        Assertions.assertEquals(0, matchOddsList.size());
    }

    @Test
    void testDeleteById(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        matchOddsService.deleteById(savedMatchOdds.getId());
        List<MatchOdds> matchOddsList = matchOddsService.findAll();
        Assertions.assertEquals(0, matchOddsList.size());
    }

    @Test
    void testDeleteByIds(){
        matchService.save(match);
        List<Long> ids = new ArrayList<>();
        for (int i=0; i<3; i++){
            MatchOdds mo = new MatchOdds(
                    match,
                    "x",
                    (double)i
            );
            MatchOdds moAdded = matchOddsService.save(mo);
            ids.add(moAdded.getId());
        }

        matchOddsService.deleteByIds(ids);
        List<MatchOdds> matchOddsList = matchOddsService.findAll();
        Assertions.assertEquals(0, matchOddsList.size());
    }

    @Test
    void testGetRecordsCount(){
        matchService.save(match);
        for (int i=0; i<3; i++){
            MatchOdds mo = new MatchOdds(
                    match,
                    "x",
                    (double)i
            );
            matchOddsService.save(mo);
        }

        long count = matchOddsService.getRecordsCount();
        Assertions.assertEquals(3, count);
    }

    @Test
    void testUpdate(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);
        Assertions.assertNotNull(savedMatchOdds);

        savedMatchOdds.setSpecifier("1");
        MatchOdds updatedMatchOdds = matchOddsService.update(savedMatchOdds);
        Assertions.assertEquals("1", updatedMatchOdds.getSpecifier());
    }

    @Test
    void testUpdateMass(){
        matchService.save(match);
        List<MatchOdds> matchOddsList = new ArrayList<>();
        for (int i=0; i<3; i++){
            MatchOdds mo = new MatchOdds(
                    match,
                    "x",
                    (double)i
            );
            matchOddsList.add(mo);
        }

        List<MatchOdds> savedMatchOdds = matchOddsService.saveAll(matchOddsList);
        for(MatchOdds mo : savedMatchOdds){
            mo.setSpecifier("1");
        }

        List<MatchOdds> updatedMatchOdds = matchOddsService.update(savedMatchOdds);
        for(MatchOdds mo : updatedMatchOdds){
            Assertions.assertEquals("1", mo.getSpecifier());
        }
    }

    @Test
    void testUpdateSpecifier(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);

        matchOddsService.updateSpecifier(savedMatchOdds.getId(), "2");
        List<MatchOdds> matchOddsList = matchOddsService.findAll();
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals("2", matchOddsList.getFirst().getSpecifier());
    }

    @Test
    void testUpdateOdd(){
        matchService.save(match);
        MatchOdds savedMatchOdds = matchOddsService.save(matchOdds);

        matchOddsService.updateOdd(savedMatchOdds.getId(), 2.0);
        List<MatchOdds> matchOddsList = matchOddsService.findAll();
        Assertions.assertEquals(1, matchOddsList.size());
        Assertions.assertEquals(2.0, matchOddsList.getFirst().getOdd());
    }
}
