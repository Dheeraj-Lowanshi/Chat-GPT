package com.votezylite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "votes", uniqueConstraints = {
        @UniqueConstraint(name = "uk_voter_election", columnNames = {"voter_id", "election_id"})
})
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "voter_id")
    private Voter voter;

    @ManyToOne(optional = false)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "election_id")
    private Election election;

    @Column(nullable = false)
    private LocalDateTime castAt;
}
