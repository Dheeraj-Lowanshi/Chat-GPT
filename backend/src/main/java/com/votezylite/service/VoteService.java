package com.votezylite.service;

import com.votezylite.dto.VoteRequestDto;

public interface VoteService {
    void castVote(VoteRequestDto request);
}
