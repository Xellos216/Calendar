package com.example.calendar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleUpdateRequestDto {
    @NotBlank
    @Size(max = 30)
    private String title;

    @NotBlank
    private String writer;

    @NotBlank
    private String password;
}

