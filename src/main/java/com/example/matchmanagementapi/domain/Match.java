package com.example.matchmanagementapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @NonNull
    @Column(name = "description", nullable=false)
    private String description;

    @NonNull
    @Column(name = "match_date", nullable = false)
    private LocalDate matchDate;

    @NonNull
    @Column(name = "match_time", nullable = false)
    private LocalTime matchTime;

    @NonNull
    @Column(name = "team_a", nullable = false)
    private String teamA;

    @NonNull
    @Column(name = "team_b", nullable = false)
    private String teamB;

    @NonNull
    @Column(name = "sport", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Sport sport;
}
