package com.example.calendar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DeleteScheduleRequestDto {
    @NotBlank
    @Size(max = 10)
    private String password;

    public String getPassword() {
        return password;
    }
}
