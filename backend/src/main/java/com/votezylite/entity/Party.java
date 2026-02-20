package com.votezylite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "parties", uniqueConstraints = {
        @UniqueConstraint(name = "uk_party_name", columnNames = "name")
})
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String symbolPath;

    @OneToMany(mappedBy = "party")
    private List<Candidate> candidates = new ArrayList<>();
}
