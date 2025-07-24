package com.example.matchmanagementapi.repository;

import com.example.matchmanagementapi.Initializer;
import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.Sport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class MatchRepositoryTest extends Initializer {
    @Autowired
    private MatchRepository matchRepository;

    @Test
    void testSaveFindByDescription(){
        matchRepository.save(match);

        Match foundMatch = matchRepository.findByDescription(match.getDescription()).getFirst();
        Assertions.assertNotNull(foundMatch);
        Assertions.assertEquals(match.getDescription(), foundMatch.getDescription());
    }

    @Test
    void testFindByMatchDate(){
        matchRepository.save(match);

        Match foundMatch = matchRepository.findByMatchDate(match.getMatchDate()).getFirst();
        Assertions.assertNotNull(foundMatch);
        Assertions.assertEquals(match.getMatchDate(), foundMatch.getMatchDate());
    }

    @Test
    void testFindByMatchDateBetween(){
        matchRepository.save(match);

        Match foundMatch = matchRepository.findByMatchDateBetween(testMatchDateBefore, testMatchDateAfter).getFirst();
        Assertions.assertNotNull(foundMatch);
        Assertions.assertEquals(match.getMatchDate(), foundMatch.getMatchDate());
    }

    @Test
    void testFindByMatchDateAfter(){
        matchRepository.save(match);

        Match foundMatch = matchRepository.findByMatchDateAfter(testMatchDateBefore).getFirst();
        Assertions.assertNotNull(foundMatch);
        Assertions.assertEquals(match.getMatchDate(), foundMatch.getMatchDate());
    }

    @Test
    void testFindByMatchDateBefore(){
        matchRepository.save(match);

        Match foundMatch = matchRepository.findByMatchDateBefore(testMatchDateAfter).getFirst();
        Assertions.assertNotNull(foundMatch);
        Assertions.assertEquals(match.getMatchDate(), foundMatch.getMatchDate());
    }

    @Test
    void testFindByMatchTime(){
        matchRepository.save(match);

        Match foundMatch = matchRepository.findByMatchTime(match.getMatchTime()).getFirst();
        Assertions.assertNotNull(foundMatch);
        Assertions.assertEquals(match.getMatchTime(), foundMatch.getMatchTime());
    }

    @Test
    void testFindByMatchTimeBetween(){
        matchRepository.save(match);

        Match foundMatch = matchRepository.findByMatchTimeBetween(testMatchTimeBefore, testMatchTimeAfter).getFirst();
        Assertions.assertNotNull(foundMatch);
        Assertions.assertEquals(match.getMatchTime(), foundMatch.getMatchTime());
    }

    @Test
    void testFindByMatchTimeAfter(){
        matchRepository.save(match);

        Match foundMatch = matchRepository.findByMatchTimeAfter(testMatchTimeBefore).getFirst();
        Assertions.assertNotNull(foundMatch);
        Assertions.assertEquals(match.getMatchTime(), foundMatch.getMatchTime());
    }

    @Test
    void testFindByMatchTimeBefore(){
        matchRepository.save(match);

        Match foundMatch = matchRepository.findByMatchTimeBefore(testMatchTimeAfter).getFirst();
        Assertions.assertNotNull(foundMatch);
        Assertions.assertEquals(match.getMatchTime(), foundMatch.getMatchTime());
    }

    @Test
    void testFindByTeamA(){
        matchRepository.save(match);

        Match foundMatch = matchRepository.findByTeamA(match.getTeamA()).getFirst();
        Assertions.assertNotNull(foundMatch);
        Assertions.assertEquals(match.getTeamA(), foundMatch.getTeamA());
    }

    @Test
    void testFindByTeamB(){
        matchRepository.save(match);

        Match foundMatch = matchRepository.findByTeamB(match.getTeamB()).getFirst();
        Assertions.assertNotNull(foundMatch);
        Assertions.assertEquals(match.getTeamB(), foundMatch.getTeamB());
    }

    @Test
    void testFindByTeamAAndTeamB(){
        matchRepository.save(match);

        Match foundMatch = matchRepository.findByTeamAAndTeamB(match.getTeamA(), match.getTeamB()).getFirst();
        Assertions.assertNotNull(foundMatch);
        Assertions.assertEquals(match.getTeamA(), foundMatch.getTeamA());
    }

    @Test
    void testFindBySport(){
        matchRepository.save(match);

        Match foundMatch = matchRepository.findBySport(match.getSport()).getFirst();
        Assertions.assertNotNull(foundMatch);
        Assertions.assertEquals(match.getSport(), foundMatch.getSport());
    }

    @Test
    void testUpdateDescriptionById(){
        matchRepository.save(match);

        matchRepository.updateDescriptionById(match.getId(), "PAO-OSFP");
        Optional<Match> updatedMatchOptional = matchRepository.findById(match.getId());
        Assertions.assertTrue(updatedMatchOptional.isPresent());
        Match updatedMatch = updatedMatchOptional.get();
        Assertions.assertEquals("PAO-OSFP", updatedMatch.getDescription());
    }

    @Test
    void testUpdateMatchDateById(){
        matchRepository.save(match);

        matchRepository.updateMatchDateById(match.getId(),testMatchDateAfter);
        Optional<Match> updatedMatchOptional = matchRepository.findById(match.getId());
        Assertions.assertTrue(updatedMatchOptional.isPresent());
        Match updatedMatch = updatedMatchOptional.get();
        Assertions.assertEquals(testMatchDateAfter, updatedMatch.getMatchDate());
    }

    @Test
    void testUpdateMatchTimeById(){
        matchRepository.save(match);

        matchRepository.updateMatchTimeById(match.getId(),testMatchTimeAfter);
        Optional<Match> updatedMatchOptional = matchRepository.findById(match.getId());
        Assertions.assertTrue(updatedMatchOptional.isPresent());
        Match updatedMatch = updatedMatchOptional.get();
        Assertions.assertEquals(testMatchTimeAfter, updatedMatch.getMatchTime());
    }

    @Test
    void testUpdateTeamAById(){
        matchRepository.save(match);

        matchRepository.updateTeamAById(match.getId(),"AEK");
        Optional<Match> updatedMatchOptional = matchRepository.findById(match.getId());
        Assertions.assertTrue(updatedMatchOptional.isPresent());
        Match updatedMatch = updatedMatchOptional.get();
        Assertions.assertEquals("AEK", updatedMatch.getTeamA());
    }

    @Test
    void testUpdateTeamBById(){
        matchRepository.save(match);

        matchRepository.updateTeamBById(match.getId(),"PAOK");
        Optional<Match> updatedMatchOptional = matchRepository.findById(match.getId());
        Assertions.assertTrue(updatedMatchOptional.isPresent());
        Match updatedMatch = updatedMatchOptional.get();
        Assertions.assertEquals("PAOK", updatedMatch.getTeamB());
    }

    @Test
    void testUpdateBothTeamsById(){
        matchRepository.save(match);

        matchRepository.updateBothTeamsById(match.getId(), "AEK", "PAOK");
        Optional<Match> updatedMatchOptional = matchRepository.findById(match.getId());
        Assertions.assertTrue(updatedMatchOptional.isPresent());
        Match updatedMatch = updatedMatchOptional.get();
        Assertions.assertEquals("AEK", updatedMatch.getTeamA());
        Assertions.assertEquals("PAOK", updatedMatch.getTeamB());
    }

    @Test
    void testUpdateSportById(){
        matchRepository.save(match);

        matchRepository.updateSportById(match.getId(), Sport.Basketball);
        Optional<Match> updatedMatchOptional = matchRepository.findById(match.getId());
        Assertions.assertTrue(updatedMatchOptional.isPresent());
        Match updatedMatch = updatedMatchOptional.get();
        Assertions.assertEquals(Sport.Basketball, updatedMatch.getSport());
    }
}
