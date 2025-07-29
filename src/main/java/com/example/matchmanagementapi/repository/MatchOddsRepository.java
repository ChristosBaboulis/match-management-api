package com.example.matchmanagementapi.repository;

import com.example.matchmanagementapi.domain.MatchOdds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchOddsRepository extends JpaRepository<MatchOdds, Long>, JpaSpecificationExecutor<MatchOdds> {
    List<MatchOdds> findByMatchId(Long matchId);
}
