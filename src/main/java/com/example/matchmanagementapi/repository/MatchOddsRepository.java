package com.example.matchmanagementapi.repository;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.MatchOdds;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchOddsRepository extends JpaRepository<MatchOdds, Long> {
    public List<MatchOdds> findByMatch(Match match);

    public List<MatchOdds> findByMatchAndOdd(Match match, double odd);


    public List<MatchOdds> findByOdd(Double odd);

    public List<MatchOdds> findByOddGreaterThan(Double odd);

    public List<MatchOdds> findByOddGreaterThanEqual(Double odd);

    public List<MatchOdds> findByOddLessThan(Double odd);

    public List<MatchOdds> findByOddLessThanEqual(Double odd);

    public List<MatchOdds> findByOddBetween(Double odd1, Double odd2);


    public List<MatchOdds> findBySpecifier(String specifier);

    public List<MatchOdds> findBySpecifierAndOdd(String specifier, double odd);

    public List<MatchOdds> findBySpecifierAndOddGreaterThan(String specifier, double odd);

    public List<MatchOdds> findBySpecifierAndOddGreaterThanEqual(String specifier, double odd);

    public List<MatchOdds> findBySpecifierAndOddLessThan(String specifier, double odd);

    public List<MatchOdds> findBySpecifierAndOddLessThanEqual(String specifier, double odd);


    @Modifying
    @Transactional
    @Query("UPDATE MatchOdds mo SET mo.specifier = :specifier WHERE mo.id = :id")
    void updateSpecifierById(@Param("id") Long id, @Param("specifier") String specifier);

    @Modifying
    @Transactional
    @Query("UPDATE MatchOdds mo SET mo.odd = :odd WHERE mo.id = :id")
    void updateOddById(@Param("id") Long id, @Param("odd") double odd);
}
