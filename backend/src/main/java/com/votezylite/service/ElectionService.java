package com.votezylite.service;

import com.votezylite.dto.ElectionDto;
import com.votezylite.dto.ResultDto;

import java.util.List;

public interface ElectionService {
    ElectionDto create(ElectionDto dto);
    List<ElectionDto> listAll();
    List<ElectionDto> listActive();
    ElectionDto closeElection(Long electionId);
    List<ResultDto> getResults(Long electionId);
    byte[] exportResultsCsv(Long electionId);
    void updateStatusesBasedOnTime();
}
