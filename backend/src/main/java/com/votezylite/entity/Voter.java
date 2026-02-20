package com.votezylite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "voters", uniqueConstraints = {
        @UniqueConstraint(name = "uk_voter_voter_id", columnNames = "voter_id_number")
})
public class Voter extends User {
    @Column(name = "voter_id_number", nullable = false)
    private String voterIdNumber;

    @Column(nullable = false)
    private String constituency;

    @OneToMany(mappedBy = "voter")
    private List<Vote> votes = new ArrayList<>();
}
