package com.votezylite.entity;

import com.votezylite.enums.ElectionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "elections")
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1500)
    private String description;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ElectionStatus status;

    @OneToMany(mappedBy = "election")
    private List<Candidate> candidates = new ArrayList<>();

    @OneToMany(mappedBy = "election")
    private List<Vote> votes = new ArrayList<>();
}
