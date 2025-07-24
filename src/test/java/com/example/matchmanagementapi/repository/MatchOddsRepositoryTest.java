package com.example.matchmanagementapi.repository;

import com.example.matchmanagementapi.Initializer;
import com.example.matchmanagementapi.domain.MatchOdds;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class MatchOddsRepositoryTest extends Initializer {
    @Autowired
    private MatchOddsRepository matchOddsRepository;
    @Autowired
    private MatchRepository matchRepository;

    @Test
    void testSaveFindByMatch(){
        matchRepository.save(match);
        matchRepository.save(match);
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByMatch(match);
        Assertions.assertNotNull(matchOdds);
    }

    @Test
    void testFindByMatchAndOdd(){
        matchRepository.save(match);
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByMatchAndOdd(match, 1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindByOdd(){
        matchRepository.save(match);
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByOdd(1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindByOddGreaterThan(){
        matchRepository.save(match);
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByOddGreaterThan(1.0);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindByOddGreaterThanEqual(){
        matchRepository.save(match);
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByOddGreaterThanEqual(1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindByOddLessThan(){
        matchRepository.save(match);
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByOddLessThan(2.0);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindByOddLessThanEqual(){
        matchRepository.save(match);
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByOddLessThanEqual(1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindByOddBetween(){
        matchRepository.save(match);
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByOddBetween(1.0, 1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindBySpecifier(){
        matchRepository.save(match);
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findBySpecifier("X");
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals("X",matchOdds.getFirst().getSpecifier());
    }

    @Test
    void testFindBySpecifierAndOdd(){
        matchRepository.save(match);
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findBySpecifierAndOdd("X", 1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals("X",matchOdds.getFirst().getSpecifier());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindBySpecifierAndOddGreaterThan(){
        matchRepository.save(match);
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findBySpecifierAndOddGreaterThan("X", 1.4);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals("X",matchOdds.getFirst().getSpecifier());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindBySpecifierAndOddGreaterThanEqual(){
        matchRepository.save(match);
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findBySpecifierAndOddGreaterThanEqual("X", 1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals("X",matchOdds.getFirst().getSpecifier());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindBySpecifierAndOddLessThan(){
        matchRepository.save(match);
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findBySpecifierAndOddLessThan("X", 1.6);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals("X",matchOdds.getFirst().getSpecifier());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindBySpecifierAndOddLessThanEqual(){
        matchRepository.save(match);
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findBySpecifierAndOddLessThanEqual("X", 1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals("X",matchOdds.getFirst().getSpecifier());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testUpdateSpecifierById(){
        matchRepository.save(match);
        MatchOdds savedMatchOdds = matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        matchOddsRepository.updateSpecifierById(savedMatchOdds.getId(), "1");

        Optional<MatchOdds> updatedMatchOddsOptional = matchOddsRepository.findById(savedMatchOdds.getId());
        Assertions.assertTrue(updatedMatchOddsOptional.isPresent());
        MatchOdds updatedMatchOdds = updatedMatchOddsOptional.get();

        Assertions.assertNotNull(updatedMatchOdds);
        Assertions.assertEquals("1",updatedMatchOdds.getSpecifier());
    }

    @Test
    void testUpdateOddById(){
        matchRepository.save(match);
        MatchOdds savedMatchOdds = matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        matchOddsRepository.updateOddById(savedMatchOdds.getId(), 2.0);

        Optional<MatchOdds> updatedMatchOddsOptional = matchOddsRepository.findById(savedMatchOdds.getId());
        Assertions.assertTrue(updatedMatchOddsOptional.isPresent());
        MatchOdds updatedMatchOdds = updatedMatchOddsOptional.get();

        Assertions.assertNotNull(updatedMatchOdds);
        Assertions.assertEquals(2.0,updatedMatchOdds.getOdd());
    }
}
