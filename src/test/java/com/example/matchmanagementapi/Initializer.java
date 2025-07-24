package com.example.matchmanagementapi;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.MatchOdds;
import com.example.matchmanagementapi.domain.Sport;
import com.example.matchmanagementapi.repository.MatchOddsRepository;
import com.example.matchmanagementapi.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@ActiveProfiles("test")
@SpringBootTest
public class Initializer {
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected LocalDate testMatchDate = LocalDate.parse("31/03/2021", formatter);
    protected LocalTime testMatchTime = LocalTime.of(12, 0);
    protected Sport testSport = Sport.Football;
    protected Match match;
    protected String description = "OSFP-PAO";
    protected String teamA = "OSFP";
    protected String teamB = "PAO";
    protected LocalDate testMatchDateBefore = LocalDate.parse("30/03/2021", formatter);
    protected LocalDate testMatchDateAfter = LocalDate.parse("31/04/2021", formatter);
    protected LocalTime testMatchTimeBefore = LocalTime.of(11, 0);
    protected LocalTime testMatchTimeAfter = LocalTime.of(13, 0);

    protected MatchOdds matchOdds;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchOddsRepository matchOddsRepository;

    @BeforeEach
    public void setup(){
        matchOddsRepository.deleteAll();
        matchRepository.deleteAll();

        match = new Match(
                description,
                testMatchDate,
                testMatchTime,
                teamA,
                teamB,
                testSport
        );

        matchOdds = new MatchOdds(
                match,
                "X",
                1.5
        );
    }
}
