package com.example.matchmanagementapi.service;

import com.example.matchmanagementapi.Initializer;
import com.example.matchmanagementapi.domain.MatchOdds;
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
    void testSaveAll(){
        matchService.save(match);
        List<MatchOdds> matchOddsList = new ArrayList<>();
        for (int i=0; i<3; i++){
            MatchOdds mo = new MatchOdds(
                    match,
                    "x",
                    i
            );
            matchOddsList.add(mo);
        }

        List<MatchOdds> matchList = matchOddsService.saveAll(matchOddsList);
        Assertions.assertEquals(3, matchList.size());
    }

    @Test
    void testSearchMatchOdds_withFilters() {
        MatchOdds odds1 = new MatchOdds(match, "1", 1.80);
        MatchOdds odds2 = new MatchOdds(match, "2", 2.10);
        MatchOdds odds3 = new MatchOdds(match, "2", 2.50);
        matchService.save(match);
        matchOddsService.saveAll(List.of(odds1, odds2, odds3));

        // Act
        List<MatchOdds> result = matchOddsService.searchMatchOdds(
                "2",
                null,
                2.0,
                2.4,
                match.getId()
        );

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("2", result.getFirst().getSpecifier());
        Assertions.assertEquals(2.10, result.getFirst().getOdd());
        Assertions.assertEquals(match.getId(), result.getFirst().getMatch().getId());
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
                    i
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
                    i
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
                    i
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
}
