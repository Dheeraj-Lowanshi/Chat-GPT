package com.votezylite.service.impl;

import com.votezylite.dto.ElectionDto;
import com.votezylite.dto.ResultDto;
import com.votezylite.entity.Election;
import com.votezylite.enums.ElectionStatus;
import com.votezylite.exception.ApiException;
import com.votezylite.repository.ElectionRepository;
import com.votezylite.repository.VoteRepository;
import com.votezylite.service.ElectionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;
    private final VoteRepository voteRepository;
    private final ModelMapper modelMapper;

    @Override
    public ElectionDto create(ElectionDto dto) {
        Election election = modelMapper.map(dto, Election.class);
        if (election.getStatus() == null) {
            election.setStatus(ElectionStatus.UPCOMING);
        }
        return modelMapper.map(electionRepository.save(election), ElectionDto.class);
    }

    @Override
    public List<ElectionDto> listAll() {
        updateStatusesBasedOnTime();
        return electionRepository.findAll().stream().map(e -> modelMapper.map(e, ElectionDto.class)).toList();
    }

    @Override
    public List<ElectionDto> listActive() {
        updateStatusesBasedOnTime();
        return electionRepository.findByStatus(ElectionStatus.ONGOING).stream().map(e -> modelMapper.map(e, ElectionDto.class)).toList();
    }

    @Override
    public ElectionDto closeElection(Long electionId) {
        Election election = electionRepository.findById(electionId).orElseThrow(() -> new ApiException("Election not found"));
        election.setStatus(ElectionStatus.COMPLETED);
        return modelMapper.map(electionRepository.save(election), ElectionDto.class);
    }

    @Override
    public List<ResultDto> getResults(Long electionId) {
        Election election = electionRepository.findById(electionId).orElseThrow(() -> new ApiException("Election not found"));
        if (election.getEndDateTime().isAfter(LocalDateTime.now()) && election.getStatus() != ElectionStatus.COMPLETED) {
            throw new ApiException("Results available only after election ends");
        }
        return voteRepository.getResultByElection(electionId).stream()
                .map(r -> new ResultDto((Long) r[0], (String) r[1], (String) r[2], (Long) r[3]))
                .toList();
    }

    @Override
    public byte[] exportResultsCsv(Long electionId) {
        String header = "candidateId,candidateName,party,votes\n";
        String body = getResults(electionId).stream()
                .map(r -> r.getCandidateId() + "," + r.getCandidateName() + "," + r.getPartyName() + "," + r.getVotes())
                .reduce("", (a, b) -> a + b + "\n");
        return (header + body).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void updateStatusesBasedOnTime() {
        LocalDateTime now = LocalDateTime.now();
        List<Election> elections = electionRepository.findAll();
        elections.forEach(e -> {
            if (now.isBefore(e.getStartDateTime())) {
                e.setStatus(ElectionStatus.UPCOMING);
            } else if (now.isAfter(e.getEndDateTime())) {
                e.setStatus(ElectionStatus.COMPLETED);
            } else {
                e.setStatus(ElectionStatus.ONGOING);
            }
        });
        electionRepository.saveAll(elections);
    }
}
