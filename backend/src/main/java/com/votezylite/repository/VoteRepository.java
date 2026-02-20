package com.votezylite.repository;

import com.votezylite.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByVoterIdAndElectionId(Long voterId, Long electionId);
    long countByElectionId(Long electionId);
    long countByCandidateIdAndElectionId(Long candidateId, Long electionId);

    @Query("""
            select v.candidate.id, v.candidate.fullName, v.candidate.party.name, count(v.id)
            from Vote v
            where v.election.id = :electionId
            group by v.candidate.id, v.candidate.fullName, v.candidate.party.name
            order by count(v.id) desc
            """)
    List<Object[]> getResultByElection(Long electionId);

    Optional<Vote> findByVoterIdAndElectionId(Long voterId, Long electionId);
}
