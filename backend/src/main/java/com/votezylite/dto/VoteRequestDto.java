package com.votezylite.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteRequestDto {
    @NotNull
    private Long voterId;

    @NotNull
    private Long candidateId;

    @NotNull
    private Long electionId;
}
