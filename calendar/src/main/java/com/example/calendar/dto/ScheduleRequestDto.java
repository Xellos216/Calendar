package com.example.calendar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ScheduleRequestDto(
        @NotBlank String title,
        @NotBlank String contents,
        @NotNull  Long userId   // 🔸 추가
) {}