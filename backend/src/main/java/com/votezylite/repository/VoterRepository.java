package com.votezylite.repository;

import com.votezylite.entity.Voter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<Voter, Long> {
    boolean existsByVoterIdNumber(String voterIdNumber);
    Page<Voter> findByFullNameContainingIgnoreCase(String name, Pageable pageable);
}
