package com.example.matchmanagementapi.service;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.Sport;
import com.example.matchmanagementapi.repository.MatchRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;

    // <editor-fold desc="FIND Methods">
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

    public List<Match> searchMatches(
            String description,
            String teamA,
            String teamB,
            Sport sport,
            LocalDate matchDate,
            LocalDate matchDateBefore,
            LocalDate matchDateAfter,
            LocalTime matchTime,
            LocalTime matchTimeBefore,
            LocalTime matchTimeAfter
    ) {
        Specification<Match> spec = (root, query, cb) -> cb.conjunction();

        if (description != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("description"), description));
        }

        if (teamA != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("teamA"), teamA));
        }

        if (teamB != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("teamB"), teamB));
        }

        if (sport != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("sport"), sport));
        }

        if (matchDate != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("matchDate"), matchDate));
        }

        if (matchDateBefore != null) {
            spec = spec.and((root, query, cb) -> cb.lessThan(root.get("matchDate"), matchDateBefore));
        }

        if (matchDateAfter != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThan(root.get("matchDate"), matchDateAfter));
        }

        if (matchTime != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("matchTime"), matchTime));
        }

        if (matchTimeBefore != null) {
            spec = spec.and((root, query, cb) -> cb.lessThan(root.get("matchTime"), matchTimeBefore));
        }

        if (matchTimeAfter != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThan(root.get("matchTime"), matchTimeAfter));
        }

        return matchRepository.findAll(spec);
    }

    public long getRecordsCount(){
        return matchRepository.count();
    }
    // </editor-fold>

    // <editor-fold desc="SAVE Methods">
    public List<Match> saveAll(List<Match> matchList){
        return matchRepository.saveAll(matchList);
    }

    public Match save(Match match){
        return matchRepository.save(match);
    }
    // </editor-fold>

    // <editor-fold desc="DELETE Methods">
    public void deleteById(Long id){
        Match match = find(id);
        if(match!=null)
            matchRepository.deleteById(id);
    }

    public void deleteByIds(List<Long> ids){
        matchRepository.deleteAllById(ids);
    }
    // </editor-fold>

    // <editor-fold desc="UPDATE Methods">
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

    public Match partialUpdate(Long id, Map<String, Object> updates) {
        Match match = find(id);

        Set<String> allowedFields = Set.of(
                "description",
                "matchDate",
                "matchTime",
                "teamA",
                "teamB",
                "sport"
        );

        for (String key : updates.keySet()) {
            if (!allowedFields.contains(key)) {
                throw new IllegalArgumentException("Field '" + key + "' is not allowed to be updated.");
            }
        }

        String originalTeamA = match.getTeamA();
        String originalTeamB = match.getTeamB();
        String originalDescription = match.getDescription();

        updates.forEach((key, value) -> {
            switch (key) {
                case "description" -> match.setDescription((String) value);
                case "matchDate" -> match.setMatchDate(LocalDate.parse((String) value));
                case "matchTime" -> match.setMatchTime(LocalTime.parse((String) value));
                case "teamA" -> match.setTeamA((String) value);
                case "teamB" -> match.setTeamB((String) value);
                case "sport" -> match.setSport(Sport.valueOf((String) value));
            }
        });

        synchronizeTeamsAndDescription(match, originalTeamA, originalTeamB, originalDescription);

        return matchRepository.save(match);
    }
    // </editor-fold>

    // <editor-fold desc="Private HELPER Methods">
    private void synchronizeTeamsAndDescription(Match match, String originalTeamA, String originalTeamB, String originalDescription) {
        boolean teamAChanged = !Objects.equals(originalTeamA, match.getTeamA());
        boolean teamBChanged = !Objects.equals(originalTeamB, match.getTeamB());
        boolean descriptionChanged = !Objects.equals(originalDescription, match.getDescription());

        if (teamAChanged || teamBChanged) {
            match.setDescription(match.getTeamA() + "-" + match.getTeamB());
        } else if (descriptionChanged) {
            String[] parts = match.getDescription().split("-");
            if (parts.length == 2) {
                match.setTeamA(parts[0]);
                match.setTeamB(parts[1]);
            } else {
                throw new IllegalArgumentException("Invalid description format. Expected 'teamA-teamB'.");
            }
        }
    }

    private String generateDescription(String teamA, String teamB) {
        return teamA + "-" + teamB;
    }
    // </editor-fold>
}
