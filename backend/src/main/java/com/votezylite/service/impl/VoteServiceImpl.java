package com.votezylite.service.impl;

import com.votezylite.dto.VoteRequestDto;
import com.votezylite.entity.Candidate;
import com.votezylite.entity.Election;
import com.votezylite.entity.Vote;
import com.votezylite.entity.Voter;
import com.votezylite.enums.ApprovalStatus;
import com.votezylite.enums.ElectionStatus;
import com.votezylite.exception.ApiException;
import com.votezylite.repository.CandidateRepository;
import com.votezylite.repository.ElectionRepository;
import com.votezylite.repository.VoteRepository;
import com.votezylite.repository.VoterRepository;
import com.votezylite.service.VoteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;

    @Override
    @Transactional
    public void castVote(VoteRequestDto request) {
        Election election = electionRepository.findById(request.getElectionId()).orElseThrow(() -> new ApiException("Election not found"));
        if (election.getStatus() != ElectionStatus.ONGOING) {
            throw new ApiException("Election is not ongoing");
        }
        if (voteRepository.existsByVoterIdAndElectionId(request.getVoterId(), request.getElectionId())) {
            throw new ApiException("You have already voted in this election");
        }

        Voter voter = voterRepository.findById(request.getVoterId()).orElseThrow(() -> new ApiException("Voter not found"));
        Candidate candidate = candidateRepository.findById(request.getCandidateId()).orElseThrow(() -> new ApiException("Candidate not found"));

        if (candidate.getApprovalStatus() != ApprovalStatus.APPROVED || !candidate.getElection().getId().equals(election.getId())) {
            throw new ApiException("Invalid candidate for election");
        }

        Vote vote = new Vote();
        vote.setCandidate(candidate);
        vote.setElection(election);
        vote.setVoter(voter);
        vote.setCastAt(LocalDateTime.now());
        voteRepository.save(vote);
    }
}
