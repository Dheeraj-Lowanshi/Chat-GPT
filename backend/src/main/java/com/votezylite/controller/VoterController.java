package com.votezylite.controller;

import com.votezylite.dto.VoterDto;
import com.votezylite.dto.VoterRegistrationDto;
import com.votezylite.service.VoterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voters")
@RequiredArgsConstructor
@CrossOrigin
public class VoterController {
    private final VoterService voterService;

    @PostMapping
    public ResponseEntity<VoterDto> register(@Valid @RequestBody VoterRegistrationDto dto) {
        return ResponseEntity.ok(voterService.register(dto));
    }

    @GetMapping
    public ResponseEntity<Page<VoterDto>> list(@RequestParam(required = false) String search,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(voterService.list(search, page, size));
    }
}
