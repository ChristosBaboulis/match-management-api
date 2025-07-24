package com.example.matchmanagementapi.service;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.Sport;
import com.example.matchmanagementapi.repository.MatchRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;

    public List<Match> findAll(){
        return matchRepository.findAll();
    }

    public List<Match> findAll(List<Long> ids){
        return matchRepository.findAllById(ids);
    }

    public Match find(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Match not found with id: " + id));
    }

    public List<Match> findByDescription(String description){
        return matchRepository.findByDescription(description);
    }

    public List<Match> findByMatchDate(LocalDate matchDate){
        return matchRepository.findByMatchDate(matchDate);
    }

    public List<Match> findByMatchDateBetween(LocalDate startDate, LocalDate endDate){
        return matchRepository.findByMatchDateBetween(startDate, endDate);
    }

    public List<Match> findByMatchDateBefore(LocalDate matchDate){
        return matchRepository.findByMatchDateBefore(matchDate);
    }

    public List<Match> findByMatchDateAfter(LocalDate matchDate){
        return matchRepository.findByMatchDateAfter(matchDate);
    }

    public List<Match> findByMatchTime(LocalTime matchTime){
        return matchRepository.findByMatchTime(matchTime);
    }

    public List<Match> findByMatchTimeBefore(LocalTime matchTime){
        return matchRepository.findByMatchTimeBefore(matchTime);
    }

    public List<Match> findByMatchTimeAfter(LocalTime matchTime){
        return matchRepository.findByMatchTimeAfter(matchTime);
    }

    public List<Match> findByTeamA(String teamA){
        return matchRepository.findByTeamA(teamA);
    }

    public List<Match> findByTeamB(String teamB){
        return matchRepository.findByTeamB(teamB);
    }

    public List<Match> findByTeamAAndTeamB(String teamA, String teamB){
        return matchRepository.findByTeamAAndTeamB(teamA, teamB);
    }

    public List<Match> findBySport(Sport sport){
        return matchRepository.findBySport(sport);
    }

    public List<Match> saveAll(List<Match> matchList){
        return matchRepository.saveAll(matchList);
    }

    public Match save(Match match){
        return matchRepository.save(match);
    }

    public void delete(Match match){
        matchRepository.delete(match);
    }

    public void deleteById(Long id){
        find(id);
        matchRepository.deleteById(id);
    }

    public void deleteById(List<Long> ids){
        matchRepository.deleteAllById(ids);
    }

    public long getRecordsCount(){
        return matchRepository.count();
    }

    public Match update(Match match){
        if(!((match.getTeamA() + "-" + match.getTeamB()).equals(match.getDescription()))){
            match.setDescription(generateDescription(match.getTeamA(), match.getTeamB()));
        }

        return matchRepository.save(match);
    }

    public List<Match> update(List<Match> matchList){
        for (Match match : matchList){
            if(!((match.getTeamA() + "-" + match.getTeamB()).equals(match.getDescription()))){
                match.setDescription(generateDescription(match.getTeamA(), match.getTeamB()));
            }
        }

        return matchRepository.saveAll(matchList);
    }

    public void updateMatchDate(Long id, LocalDate matchDate){
        find(id);
        matchRepository.updateMatchDateById(id, matchDate);
    }

    public void updateMatchTime(Long id, LocalTime matchTime){
        find(id);
        matchRepository.updateMatchTimeById(id, matchTime);
    }

    public void updateDescription(Long id, String description){
        find(id);

        String[] teams = description.split("-");
        if (teams.length != 2) {
            throw new IllegalArgumentException("Description must be in the format 'teamA-teamB'");
        }

        String teamA = teams[0].trim();
        String teamB = teams[1].trim();

        updateTeamFields(id, teamA, teamB);
    }

    public void updateTeamA(Long id, String teamA){
        Match matchBeforeUpdate = find(id);
        updateTeamFields(id, teamA, matchBeforeUpdate.getTeamB());
    }

    public void updateTeamB(Long id, String teamB){
        Match matchBeforeUpdate = find(id);
        updateTeamFields(id, matchBeforeUpdate.getTeamA(), teamB);
    }

    public void updateBothTeams(Long id, String teamA, String teamB){
        find(id);
        updateTeamFields(id, teamA, teamB);
    }

    public void updateSport(Long id, Sport sport){
        find(id);
        matchRepository.updateSportById(id, sport);
    }

    private void updateTeamFields(Long id, String teamA, String teamB) {
        String description = generateDescription(teamA, teamB);
        matchRepository.updateDescriptionById(id, description);
        matchRepository.updateTeamAById(id, teamA);
        matchRepository.updateTeamBById(id, teamB);
    }

    private String generateDescription(String teamA, String teamB) {
        return teamA + "-" + teamB;
    }
}
