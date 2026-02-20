package com.votezylite.controller;

import com.votezylite.dto.VoteRequestDto;
import com.votezylite.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
@CrossOrigin
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Map<String, String>> castVote(@Valid @RequestBody VoteRequestDto request) {
        voteService.castVote(request);
        return ResponseEntity.ok(Map.of("message", "Vote cast successfully"));
    }
}
