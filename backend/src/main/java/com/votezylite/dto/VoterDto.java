package com.votezylite.dto;

import lombok.Data;

@Data
public class VoterDto {
    private Long id;
    private String fullName;
    private String email;
    private String voterIdNumber;
    private String constituency;
}
