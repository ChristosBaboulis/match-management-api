package com.example.matchmanagementapi.repository;

import com.example.matchmanagementapi.Initializer;
import com.example.matchmanagementapi.domain.Match;
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

    @Test
    void testSaveFindByMatch(){
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByMatch(match);
        Assertions.assertNotNull(matchOdds);
    }

    @Test
    void testFindByMatchAndOdd(){
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByMatchAndOdd(match, 1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindByOdd(){
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByOdd(1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindByOddGreaterThan(){
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByOddGreaterThan(1.0);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindByOddGreaterThanEqual(){
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByOddGreaterThanEqual(1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindByOddLessThan(){
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByOddLessThan(2.0);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindByOddLessThanEqual(){
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByOddLessThanEqual(1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindByOddBetween(){
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findByOddBetween(1.0, 1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindBySpecifier(){
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findBySpecifier("X");
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals("X",matchOdds.getFirst().getSpecifier());
    }

    @Test
    void testFindBySpecifierAndOdd(){
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findBySpecifierAndOdd("X", 1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals("X",matchOdds.getFirst().getSpecifier());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindBySpecifierAndOddGreaterThan(){
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findBySpecifierAndOddGreaterThan("X", 1.4);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals("X",matchOdds.getFirst().getSpecifier());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindBySpecifierAndOddGreaterThanEqual(){
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findBySpecifierAndOddGreaterThanEqual("X", 1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals("X",matchOdds.getFirst().getSpecifier());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindBySpecifierAndOddLessThan(){
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findBySpecifierAndOddLessThan("X", 1.6);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals("X",matchOdds.getFirst().getSpecifier());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testFindBySpecifierAndOddLessThanEqual(){
        matchOddsRepository.save(matchOdds);
        Assertions.assertNotNull(matchOdds);

        List<MatchOdds> matchOdds = matchOddsRepository.findBySpecifierAndOddLessThanEqual("X", 1.5);
        Assertions.assertEquals(1, matchOdds.size());
        Assertions.assertEquals("X",matchOdds.getFirst().getSpecifier());
        Assertions.assertEquals(1.5,matchOdds.getFirst().getOdd());
    }

    @Test
    void testUpdateSpecifierById(){
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
