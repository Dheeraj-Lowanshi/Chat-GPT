package com.votezylite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminDashboardDto {
    private long totalVoters;
    private long totalCandidates;
    private long totalVotesCast;
    private double turnoutPercentage;
}
