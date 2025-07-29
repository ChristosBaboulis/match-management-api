package com.example.matchmanagementapi.service;

import com.example.matchmanagementapi.domain.Match;
import com.example.matchmanagementapi.domain.MatchOdds;
import com.example.matchmanagementapi.exception.ResourceNotFoundException;
import com.example.matchmanagementapi.repository.MatchOddsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MatchOddsService {
    private final MatchOddsRepository matchOddsRepository;
    private final MatchService matchService;

    // <editor-fold desc="FIND Methods">
    public List<MatchOdds> findAll() {
        return matchOddsRepository.findAll();
    }

    public MatchOdds find(Long id){
        return matchOddsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match odds not found with id: " + id));
    }

    public List<MatchOdds> searchMatchOdds(
            String specifier,
            Double odd,
            Double oddOver,
            Double oddUnder,
            Long matchId
    ) {
        Specification<MatchOdds> spec = (root, query, cb) -> cb.conjunction();

        if (specifier != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("specifier"), specifier));
        }

        if (odd != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("odd"), odd));
        }

        if (oddOver != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThan(root.get("odd"), oddOver));
        }

        if (oddUnder != null) {
            spec = spec.and((root, query, cb) -> cb.lessThan(root.get("odd"), oddUnder));
        }

        if (matchId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("match").get("id"), matchId));
        }

        return matchOddsRepository.findAll(spec);
    }

    public long getRecordsCount(){
        return matchOddsRepository.count();
    }
    // </editor-fold>

    // <editor-fold desc="SAVE Methods">
    public List<MatchOdds> saveAll(List<MatchOdds> matchList){
        return matchOddsRepository.saveAll(matchList);
    }

    public MatchOdds save(MatchOdds matchOdds){
        matchService.find(matchOdds.getMatch().getId());
        return matchOddsRepository.save(matchOdds);
    }
    // </editor-fold>

    // <editor-fold desc="DELETE Methods">
    public void deleteById(Long id){
        MatchOdds matchOdds = find(id);
        if(matchOdds!=null)
            matchOddsRepository.deleteById(id);
    }

    public void deleteByIds(List<Long> ids){
        matchOddsRepository.deleteAllById(ids);
    }
    // </editor-fold>

    // <editor-fold desc="UPDATE Methods">
    public MatchOdds update(Long id, MatchOdds updated){
        MatchOdds odds = find(id);

        odds.setSpecifier(updated.getSpecifier());
        odds.setOdd(updated.getOdd());
        Match match = matchService.find(updated.getMatch().getId());
        odds.setMatch(match);

        return matchOddsRepository.save(odds);
    }

    public MatchOdds partialUpdate(Long id, Map<String, Object> updates) {
        MatchOdds matchOdds = find(id);

        Set<String> allowedFields = Set.of(
                "specifier",
                "odd",
                "match"
        );

        for (String key : updates.keySet()) {
            if (!allowedFields.contains(key)) {
                throw new IllegalArgumentException("Field '" + key + "' is not allowed to be updated.");
            }
        }

        updates.forEach((key, value) -> {
            switch (key) {
                case "specifier" -> matchOdds.setSpecifier((String) value);
                case "odd" -> {
                    if (value instanceof Number number) {
                        matchOdds.setOdd(number.doubleValue());
                    } else {
                        matchOdds.setOdd(Double.parseDouble(value.toString()));
                    }
                }
                case "match" -> {
                    if (value instanceof Map<?, ?> matchMap) {
                        Object matchIdObj = matchMap.get("id");
                        if (matchIdObj instanceof Number matchIdNumber) {
                            Long matchId = matchIdNumber.longValue();
                            Match match = matchService.find(matchId);
                            matchOdds.setMatch(match);
                        } else {
                            throw new IllegalArgumentException("Invalid or missing match.id");
                        }
                    } else {
                        throw new IllegalArgumentException("Invalid format for match");
                    }
                }
            }
        });

        return matchOddsRepository.save(matchOdds);
    }
    // </editor-fold>
}
