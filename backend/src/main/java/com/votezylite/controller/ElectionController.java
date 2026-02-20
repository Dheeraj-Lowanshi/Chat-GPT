package com.votezylite.controller;

import com.votezylite.dto.ElectionDto;
import com.votezylite.dto.ResultDto;
import com.votezylite.service.ElectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elections")
@RequiredArgsConstructor
@CrossOrigin
public class ElectionController {
    private final ElectionService electionService;

    @PostMapping
    public ResponseEntity<ElectionDto> create(@Valid @RequestBody ElectionDto dto) {
        return ResponseEntity.ok(electionService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ElectionDto>> listAll() {
        return ResponseEntity.ok(electionService.listAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<ElectionDto>> listActive() {
        return ResponseEntity.ok(electionService.listActive());
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<ElectionDto> close(@PathVariable Long id) {
        return ResponseEntity.ok(electionService.closeElection(id));
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<List<ResultDto>> results(@PathVariable Long id) {
        return ResponseEntity.ok(electionService.getResults(id));
    }

    @GetMapping("/{id}/results/export")
    public ResponseEntity<byte[]> export(@PathVariable Long id) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=results.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(electionService.exportResultsCsv(id));
    }
}
