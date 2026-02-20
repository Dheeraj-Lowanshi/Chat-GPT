package com.votezylite.dto;

import com.votezylite.enums.ElectionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ElectionDto {
    private Long id;

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    private ElectionStatus status;
}
