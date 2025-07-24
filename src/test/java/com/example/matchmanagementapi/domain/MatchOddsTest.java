package com.example.matchmanagementapi.domain;

import com.example.matchmanagementapi.Initializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class MatchOddsTest extends Initializer {
    @Test
    void testCassFunctionality(){
        MatchOdds matchOdds1 = new MatchOdds();

        MatchOdds matchOdds2 = new MatchOdds(
                match,
                "2",
                2.0
        );

        Assertions.assertNotNull(matchOdds1);
        Assertions.assertNotNull(matchOdds2);

        Assertions.assertEquals("2", matchOdds2.getSpecifier());
        Assertions.assertEquals(2.0, matchOdds2.getOdd());
        Assertions.assertEquals(match, matchOdds2.getMatch());
    }
}
