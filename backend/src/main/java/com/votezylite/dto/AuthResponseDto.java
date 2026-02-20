package com.votezylite.dto;

import com.votezylite.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private Long userId;
    private Role role;
    private String fullName;
    private String email;
}
