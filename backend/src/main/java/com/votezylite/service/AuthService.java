package com.votezylite.service;

import com.votezylite.dto.AuthRequestDto;
import com.votezylite.dto.AuthResponseDto;

public interface AuthService {
    AuthResponseDto login(AuthRequestDto request);
}
