// ScheduleResponseDto.java
package com.example.calendar.dto;

import java.time.LocalDateTime;

public record ScheduleResponseDto(
        Long id,
        String title,
        String contents,
        Long userId,
        String username,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {}
