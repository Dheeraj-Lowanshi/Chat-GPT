package com.votezylite.service.impl;

import com.votezylite.dto.CandidateDto;
import com.votezylite.dto.CandidateRegistrationDto;
import com.votezylite.entity.Candidate;
import com.votezylite.entity.Election;
import com.votezylite.entity.Party;
import com.votezylite.enums.ApprovalStatus;
import com.votezylite.enums.Role;
import com.votezylite.exception.ApiException;
import com.votezylite.repository.CandidateRepository;
import com.votezylite.repository.ElectionRepository;
import com.votezylite.repository.PartyRepository;
import com.votezylite.repository.UserRepository;
import com.votezylite.repository.VoteRepository;
import com.votezylite.service.CandidateService;
import com.votezylite.util.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;
    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private final FileStorageService fileStorageService;

    @Override
    public CandidateDto register(CandidateRegistrationDto dto, MultipartFile symbolFile, MultipartFile profileFile) {
        ensureUnique(dto.getEmail(), dto.getAadharNumber());
        Election election = electionRepository.findById(dto.getElectionId())
                .orElseThrow(() -> new ApiException("Election not found"));

        Party party = partyRepository.findByNameIgnoreCase(dto.getPartyName()).orElseGet(Party::new);
        party.setName(dto.getPartyName());
        String symbolPath = fileStorageService.save(symbolFile, "party-symbols");
        if (symbolPath != null) {
            party.setSymbolPath(symbolPath);
        }
        party = partyRepository.save(party);

        Candidate c = new Candidate();
        c.setFullName(dto.getFullName());
        c.setEmail(dto.getEmail());
        c.setPassword(dto.getPassword());
        c.setPhoneNumber(dto.getPhoneNumber());
        c.setDateOfBirth(dto.getDateOfBirth());
        c.setGender(dto.getGender());
        c.setAddress(dto.getAddress());
        c.setAadharNumber(dto.getAadharNumber());
        c.setManifesto(dto.getManifesto());
        c.setProfilePhotoPath(fileStorageService.save(profileFile, "candidate-profiles"));
        c.setParty(party);
        c.setElection(election);
        c.setApprovalStatus(ApprovalStatus.PENDING);
        c.setRole(Role.CANDIDATE);
        return toDto(candidateRepository.save(c), false);
    }

    @Override
    public CandidateDto updateProfile(Long candidateId, CandidateRegistrationDto dto, MultipartFile symbolFile, MultipartFile profileFile) {
        Candidate c = candidateRepository.findById(candidateId).orElseThrow(() -> new ApiException("Candidate not found"));
        if (c.getApprovalStatus() != ApprovalStatus.PENDING) {
            throw new ApiException("Approved/Rejected candidate cannot update profile");
        }
        c.setFullName(dto.getFullName());
        c.setPhoneNumber(dto.getPhoneNumber());
        c.setAddress(dto.getAddress());
        c.setManifesto(dto.getManifesto());
        String profilePath = fileStorageService.save(profileFile, "candidate-profiles");
        if (profilePath != null) c.setProfilePhotoPath(profilePath);
        String symbolPath = fileStorageService.save(symbolFile, "party-symbols");
        if (symbolPath != null && c.getParty() != null) c.getParty().setSymbolPath(symbolPath);
        return toDto(candidateRepository.save(c), false);
    }

    @Override
    public CandidateDto approve(Long candidateId, ApprovalStatus status) {
        Candidate c = candidateRepository.findById(candidateId).orElseThrow(() -> new ApiException("Candidate not found"));
        c.setApprovalStatus(status);
        return toDto(candidateRepository.save(c), false);
    }

    @Override
    public List<CandidateDto> listByElection(Long electionId, String search) {
        List<Candidate> list = candidateRepository.findByElectionIdAndApprovalStatus(electionId, ApprovalStatus.APPROVED);
        if (search != null && !search.isBlank()) {
            String q = search.toLowerCase();
            list = list.stream().filter(c -> c.getFullName().toLowerCase().contains(q)
                    || c.getParty().getName().toLowerCase().contains(q)).toList();
        }
        return list.stream().map(c -> toDto(c, false)).toList();
    }

    @Override
    public CandidateDto getById(Long candidateId) {
        Candidate c = candidateRepository.findById(candidateId).orElseThrow(() -> new ApiException("Candidate not found"));
        boolean canSeeVotes = c.getElection().getStatus().name().equals("COMPLETED");
        return toDto(c, canSeeVotes);
    }

    private void ensureUnique(String email, String aadhar) {
        if (userRepository.existsByEmail(email)) throw new ApiException("Email already exists");
        if (userRepository.existsByAadharNumber(aadhar)) throw new ApiException("Aadhar already exists");
    }

    private CandidateDto toDto(Candidate c, boolean includeVotes) {
        CandidateDto dto = new CandidateDto();
        dto.setId(c.getId());
        dto.setFullName(c.getFullName());
        dto.setEmail(c.getEmail());
        dto.setManifesto(c.getManifesto());
        dto.setPartyName(c.getParty() != null ? c.getParty().getName() : null);
        dto.setProfilePhotoPath(c.getProfilePhotoPath());
        dto.setApprovalStatus(c.getApprovalStatus());
        dto.setElectionId(c.getElection() != null ? c.getElection().getId() : null);
        if (includeVotes && c.getElection() != null) {
            dto.setTotalVotes(voteRepository.countByCandidateIdAndElectionId(c.getId(), c.getElection().getId()));
        }
        return dto;
    }
}
