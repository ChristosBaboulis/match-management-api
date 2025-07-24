package com.example.matchmanagementapi.domain;

import com.example.matchmanagementapi.Initializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

@ActiveProfiles("test")
@SpringBootTest
public class MatchTest extends Initializer {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate testMatchDate = LocalDate.parse("31/03/2021", formatter);
    LocalTime testMatchTime = LocalTime.of(12, 0);
    Sport testSport = Sport.Football;

    @Test
    public void testClassFunctionality() {
        Match match1 = new Match();

        Match match2 = new Match(
                "OSFP-PAO",
                testMatchDate,
                testMatchTime,
                "OSFP",
                "PAO",
                testSport
        );

        Assertions.assertNotNull(match1);
        Assertions.assertNotNull(match2);

        Assertions.assertEquals("OSFP-PAO", match2.getDescription());
        Assertions.assertEquals(testMatchDate, match2.getMatchDate());
        Assertions.assertEquals(testMatchTime, match2.getMatchTime());
        Assertions.assertEquals(testSport, match2.getSport());
        Assertions.assertEquals("OSFP", match2.getTeamA());
        Assertions.assertEquals("PAO", match2.getTeamB());

        Assertions.assertNotEquals(match1, match2);
    }
}
