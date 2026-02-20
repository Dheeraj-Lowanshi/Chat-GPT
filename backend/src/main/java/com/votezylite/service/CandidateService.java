package com.votezylite.service;

import com.votezylite.dto.CandidateDto;
import com.votezylite.dto.CandidateRegistrationDto;
import com.votezylite.enums.ApprovalStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateService {
    CandidateDto register(CandidateRegistrationDto dto, MultipartFile symbolFile, MultipartFile profileFile);
    CandidateDto updateProfile(Long candidateId, CandidateRegistrationDto dto, MultipartFile symbolFile, MultipartFile profileFile);
    CandidateDto approve(Long candidateId, ApprovalStatus status);
    List<CandidateDto> listByElection(Long electionId, String search);
    CandidateDto getById(Long candidateId);
}
