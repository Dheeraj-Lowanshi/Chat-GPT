package com.votezylite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultDto {
    private Long candidateId;
    private String candidateName;
    private String partyName;
    private Long votes;
}
