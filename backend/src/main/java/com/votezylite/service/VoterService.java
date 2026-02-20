package com.votezylite.service;

import com.votezylite.dto.VoterDto;
import com.votezylite.dto.VoterRegistrationDto;
import org.springframework.data.domain.Page;

public interface VoterService {
    VoterDto register(VoterRegistrationDto dto);
    Page<VoterDto> list(String search, int page, int size);
}
