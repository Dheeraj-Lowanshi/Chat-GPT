package com.votezylite.dto;

import com.votezylite.enums.ApprovalStatus;
import lombok.Data;

@Data
public class CandidateDto {
    private Long id;
    private String fullName;
    private String email;
    private String partyName;
    private String manifesto;
    private String profilePhotoPath;
    private ApprovalStatus approvalStatus;
    private Long electionId;
    private Long totalVotes;
}
