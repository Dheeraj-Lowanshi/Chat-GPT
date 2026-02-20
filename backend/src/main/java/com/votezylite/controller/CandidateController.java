package com.votezylite.controller;

import com.votezylite.dto.CandidateDto;
import com.votezylite.dto.CandidateRegistrationDto;
import com.votezylite.enums.ApprovalStatus;
import com.votezylite.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
@CrossOrigin
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<CandidateDto> register(@Valid @RequestPart("data") CandidateRegistrationDto dto,
                                                 @RequestPart(value = "partySymbol", required = false) MultipartFile partySymbol,
                                                 @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto) {
        return ResponseEntity.ok(candidateService.register(dto, partySymbol, profilePhoto));
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<CandidateDto> update(@PathVariable Long id,
                                               @Valid @RequestPart("data") CandidateRegistrationDto dto,
                                               @RequestPart(value = "partySymbol", required = false) MultipartFile partySymbol,
                                               @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto) {
        return ResponseEntity.ok(candidateService.updateProfile(id, dto, partySymbol, profilePhoto));
    }

    @PatchMapping("/{id}/approval")
    public ResponseEntity<CandidateDto> approve(@PathVariable Long id, @RequestParam ApprovalStatus status) {
        return ResponseEntity.ok(candidateService.approve(id, status));
    }

    @GetMapping("/election/{electionId}")
    public ResponseEntity<List<CandidateDto>> listByElection(@PathVariable Long electionId,
                                                             @RequestParam(required = false) String search) {
        return ResponseEntity.ok(candidateService.listByElection(electionId, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDto> one(@PathVariable Long id) {
        return ResponseEntity.ok(candidateService.getById(id));
    }
}
