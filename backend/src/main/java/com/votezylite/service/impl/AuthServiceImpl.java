package com.votezylite.service.impl;

import com.votezylite.dto.AuthRequestDto;
import com.votezylite.dto.AuthResponseDto;
import com.votezylite.entity.User;
import com.votezylite.exception.ApiException;
import com.votezylite.repository.UserRepository;
import com.votezylite.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public AuthResponseDto login(AuthRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException("Invalid credentials"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new ApiException("Invalid credentials");
        }
        if (request.getRole() != null && request.getRole() != user.getRole()) {
            throw new ApiException("Role mismatch");
        }

        return new AuthResponseDto(user.getId(), user.getRole(), user.getFullName(), user.getEmail());
    }
}
