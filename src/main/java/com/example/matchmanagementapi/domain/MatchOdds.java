package com.example.matchmanagementapi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "match_odds")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class MatchOdds {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @NonNull
    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @NonNull
    @Column(name = "specifier", nullable = false)
    private String specifier;

    @Column(name = "odd")
    private double odd;

    public MatchOdds(Match match, String specifier, double odd) {
        this.match = match;
        this.specifier = specifier;
        this.odd = odd;
    }
}
