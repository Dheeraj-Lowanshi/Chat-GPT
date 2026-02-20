package com.votezylite.service.impl;

import com.votezylite.dto.AdminDashboardDto;
import com.votezylite.repository.CandidateRepository;
import com.votezylite.repository.VoteRepository;
import com.votezylite.repository.VoterRepository;
import com.votezylite.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;
    private final VoteRepository voteRepository;

    @Override
    public AdminDashboardDto dashboard(Long electionId) {
        long voters = voterRepository.count();
        long candidates = candidateRepository.count();
        long votes = voteRepository.countByElectionId(electionId);
        double turnout = voters == 0 ? 0 : ((double) votes / voters) * 100;
        return new AdminDashboardDto(voters, candidates, votes, Math.round(turnout * 100.0) / 100.0);
    }
}
