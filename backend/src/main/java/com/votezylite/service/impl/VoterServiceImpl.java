package com.votezylite.service.impl;

import com.votezylite.dto.VoterDto;
import com.votezylite.dto.VoterRegistrationDto;
import com.votezylite.entity.Voter;
import com.votezylite.enums.Role;
import com.votezylite.exception.ApiException;
import com.votezylite.repository.UserRepository;
import com.votezylite.repository.VoterRepository;
import com.votezylite.service.VoterService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoterServiceImpl implements VoterService {
    private final VoterRepository voterRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public VoterDto register(VoterRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) throw new ApiException("Email already exists");
        if (userRepository.existsByAadharNumber(dto.getAadharNumber())) throw new ApiException("Aadhar already exists");
        if (voterRepository.existsByVoterIdNumber(dto.getVoterIdNumber())) throw new ApiException("Voter ID already exists");

        Voter voter = modelMapper.map(dto, Voter.class);
        voter.setRole(Role.VOTER);
        return modelMapper.map(voterRepository.save(voter), VoterDto.class);
    }

    @Override
    public Page<VoterDto> list(String search, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        if (search == null || search.isBlank()) {
            return voterRepository.findAll(pageable).map(v -> modelMapper.map(v, VoterDto.class));
        }
        return voterRepository.findByFullNameContainingIgnoreCase(search, pageable).map(v -> modelMapper.map(v, VoterDto.class));
    }
}
