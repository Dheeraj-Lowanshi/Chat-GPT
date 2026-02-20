package com.votezylite.entity;

import com.votezylite.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "candidates")
public class Candidate extends User {

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    @Column(length = 3000)
    private String manifesto;

    private String profilePhotoPath;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    @OneToMany(mappedBy = "candidate")
    private List<Vote> votes = new ArrayList<>();
}
