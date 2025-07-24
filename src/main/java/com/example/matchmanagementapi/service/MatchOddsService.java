package com.example.matchmanagementapi.service;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.MatchOdds;
import com.example.matchmanagementapi.repository.MatchOddsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchOddsService {
    private final MatchOddsRepository matchOddsRepository;
    private final MatchService matchService;

    public List<MatchOdds> findAll() {
        return matchOddsRepository.findAll();
    }

    public List<MatchOdds> findAll(List<Long> ids) {
        return matchOddsRepository.findAllById(ids);
    }

    public MatchOdds find(Long id){
        return matchOddsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Match odds not found with id: " + id));
    }

    public List<MatchOdds> findByMatch(Match match){
        return matchOddsRepository.findByMatch(match);
    }

    public List<MatchOdds> findByMatchId(Long matchId){
        Match match = matchService.find(matchId);
        return  matchOddsRepository.findByMatch(match);
    }

    public List<MatchOdds> findByMatchAndOdd(Match match, double odd){
        return matchOddsRepository.findByMatchAndOdd(match, odd);
    }

    public List<MatchOdds> findByOdd(Double odd){
        return matchOddsRepository.findByOdd(odd);
    }

    public List<MatchOdds> findByOddGreaterThan(double odd){
        return matchOddsRepository.findByOddGreaterThan(odd);
    }

    public List<MatchOdds> findByOddGreaterThanEqual(double odd){
        return matchOddsRepository.findByOddGreaterThanEqual(odd);
    }

    public List<MatchOdds> findByOddLessThan(double odd){
        return matchOddsRepository.findByOddLessThan(odd);
    }

    public List<MatchOdds> findByOddLessThanEqual(double odd){
        return matchOddsRepository.findByOddLessThanEqual(odd);
    }

    public List<MatchOdds> findByOddBetween(double odd1, double odd2){
        if (odd1 > odd2) {
            throw new IllegalArgumentException("The lower bound (odd1) must be less than or equal to the upper bound (odd2).");
        }

        return matchOddsRepository.findByOddBetween(odd1, odd2);
    }

    public List<MatchOdds> findBySpecifier(String specifier){
        return matchOddsRepository.findBySpecifier(specifier);
    }

    public List<MatchOdds> findBySpecifierAndOdd(String specifier, double odd){
        return matchOddsRepository.findBySpecifierAndOdd(specifier, odd);
    }

    public List<MatchOdds> findBySpecifierAndOddGreaterThan(String specifier, double odd){
        return matchOddsRepository.findBySpecifierAndOddGreaterThan(specifier, odd);
    }

    public List<MatchOdds> findBySpecifierAndOddGreaterThanEqual(String specifier, double odd){
        return matchOddsRepository.findBySpecifierAndOddGreaterThanEqual(specifier, odd);
    }

    public List<MatchOdds> findBySpecifierAndOddLessThan(String specifier, double odd){
        return matchOddsRepository.findBySpecifierAndOddLessThan(specifier, odd);
    }

    public List<MatchOdds> findBySpecifierAndOddLessThanEqual(String specifier, double odd){
        return matchOddsRepository.findBySpecifierAndOddLessThanEqual(specifier, odd);
    }

    public List<MatchOdds> saveAll(List<MatchOdds> matchList){
        return matchOddsRepository.saveAll(matchList);
    }

    public MatchOdds save(MatchOdds matchOdds){
        return matchOddsRepository.save(matchOdds);
    }

    public void delete(MatchOdds match){
        matchOddsRepository.delete(match);
    }

    public void deleteById(Long id){
        find(id);
        matchOddsRepository.deleteById(id);
    }

    public void deleteByIds(List<Long> ids){
        matchOddsRepository.deleteAllById(ids);
    }

    public long getRecordsCount(){
        return matchOddsRepository.count();
    }

    public MatchOdds update(MatchOdds matchOdds){
        return matchOddsRepository.save(matchOdds);
    }

    public List<MatchOdds> update(List<MatchOdds> matchOddsList){
        return matchOddsRepository.saveAll(matchOddsList);
    }

    public void updateSpecifier(Long id, String specifier){
        find(id);
        matchOddsRepository.updateSpecifierById(id, specifier);
    }

    public void updateOdd(Long id, double odd){
        find(id);
        matchOddsRepository.updateOddById(id, odd);
    }
}
