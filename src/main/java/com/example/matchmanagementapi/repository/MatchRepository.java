package com.example.matchmanagementapi.repository;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long>, JpaSpecificationExecutor<Match> {
    boolean existsByDescriptionAndMatchDateAndMatchTimeAndTeamAAndTeamBAndSport(
            String description,
            LocalDate matchDate,
            LocalTime matchTime,
            String teamA,
            String teamB,
            Sport sport
    );
}

//  REMOVED - Query-like Example
//    @Modifying
//    @Transactional
//    @Query("UPDATE Match m SET m.description = :description WHERE m.id = :id")
//    void updateDescriptionById(@Param("id") long id, @Param("description") String description);