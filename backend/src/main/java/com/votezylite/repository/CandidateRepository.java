package com.votezylite.repository;

import com.votezylite.entity.Candidate;
import com.votezylite.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findByApprovalStatus(ApprovalStatus status);
    List<Candidate> findByElectionIdAndApprovalStatus(Long electionId, ApprovalStatus status);
    long countByApprovalStatus(ApprovalStatus status);
}
