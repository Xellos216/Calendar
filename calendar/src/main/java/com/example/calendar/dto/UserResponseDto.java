package com.example.calendar.dto;

import java.time.LocalDateTime;

public record UserResponseDto(
        Long id,
        String username,
        String email,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {}
