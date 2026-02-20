package com.votezylite.controller;

import com.votezylite.dto.AdminDashboardDto;
import com.votezylite.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/dashboard/{electionId}")
    public ResponseEntity<AdminDashboardDto> dashboard(@PathVariable Long electionId) {
        return ResponseEntity.ok(adminService.dashboard(electionId));
    }
}
