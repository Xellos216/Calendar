package com.example.calendar.dto;

import jakarta.validation.constraints.NotBlank;

public record ScheduleUpdateRequestDto(
        @NotBlank String title,
        @NotBlank String contents
) {}


