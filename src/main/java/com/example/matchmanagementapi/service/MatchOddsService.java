package com.example.matchmanagementapi.service;

import com.example.matchmanagementapi.domain.MatchOdds;
import com.example.matchmanagementapi.repository.MatchOddsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchOddsService {
    private final MatchOddsRepository matchOddsRepository;

    // <editor-fold desc="FIND Methods">
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
    public MatchOdds update(MatchOdds matchOdds){
        return matchOddsRepository.save(matchOdds);
    }

    public List<MatchOdds> update(List<MatchOdds> matchOddsList){
        return matchOddsRepository.saveAll(matchOddsList);
    }
    // </editor-fold>
}
