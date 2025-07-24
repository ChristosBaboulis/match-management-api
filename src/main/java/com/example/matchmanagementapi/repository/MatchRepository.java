package com.example.matchmanagementapi.repository;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.Sport;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByDescription(String description);


    List<Match> findByMatchDate(LocalDate startDate);

    List<Match> findByMatchDateBetween(LocalDate startDate, LocalDate endDate);

    List<Match> findByMatchDateAfter(LocalDate startDate);

    List<Match> findByMatchDateBefore(LocalDate endDate);


    List<Match> findByMatchTime(LocalTime time);

    List<Match> findByMatchTimeBetween(LocalTime startTime, LocalTime endTime);

    List<Match> findByMatchTimeAfter(LocalTime time);

    List<Match> findByMatchTimeBefore(LocalTime time);


    List<Match> findByTeamA(String teamA);

    List<Match> findByTeamB(String teamB);

    List<Match> findByTeamAAndTeamB(String teamA, String teamB);


    List<Match> findBySport(Sport sport);

    @Modifying
    @Transactional
    @Query("UPDATE Match m SET m.description = :description WHERE m.id = :id")
    void updateDescriptionById(@Param("id") long id, @Param("description") String description);

    @Modifying
    @Transactional
    @Query("UPDATE Match m SET m.matchDate = :matchDate WHERE m.id = :id")
    void updateMatchDateById(@Param("id") Long id, @Param("matchDate") LocalDate matchDate);

    @Modifying
    @Transactional
    @Query("UPDATE Match m SET m.matchTime = :matchTime WHERE m.id = :id")
    void updateMatchTimeById(@Param("id") Long id, @Param("matchTime") LocalTime matchTime);

    @Modifying
    @Transactional
    @Query("UPDATE Match m SET m.teamA = :teamA WHERE m.id = :id")
    void updateTeamAById(@Param("id") Long id, @Param("teamA") String teamA);

    @Modifying
    @Transactional
    @Query("UPDATE Match m SET m.teamB = :teamB WHERE m.id = :id")
    void updateTeamBById(@Param("id") Long id, @Param("teamB") String teamB);

    @Modifying
    @Transactional
    @Query("UPDATE Match m SET m.teamA = :teamA, m.teamB = :teamB WHERE m.id = :id")
    void updateBothTeamsById(@Param("id") Long id, @Param("teamA") String teamA, @Param("teamB") String teamB);

    @Modifying
    @Transactional
    @Query("UPDATE Match m SET m.sport = :sport WHERE m.id = :id")
    void updateSportById(@Param("id") Long id, @Param("sport") Sport sport);
}
