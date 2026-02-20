package com.votezylite.dto;

import com.votezylite.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VoterRegistrationDto {
    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private Gender gender;

    @NotBlank
    private String address;

    @Size(min = 12, max = 20)
    private String aadharNumber;

    @NotBlank
    private String voterIdNumber;

    @NotBlank
    private String constituency;
}
