package com.example.matchmanagementapi.repository;

import com.example.matchmanagementapi.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long>, JpaSpecificationExecutor<Match> {
}

//  REMOVED - Query-like Example
//    @Modifying
//    @Transactional
//    @Query("UPDATE Match m SET m.description = :description WHERE m.id = :id")
//    void updateDescriptionById(@Param("id") long id, @Param("description") String description);