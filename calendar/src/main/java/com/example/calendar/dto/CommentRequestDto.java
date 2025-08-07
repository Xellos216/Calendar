package com.example.calendar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    private Long scheduleId;

    @NotBlank
    @Size(max = 200)
    private String comment;

    @NotBlank
    @Size(max = 10)
    private String writer;

    @NotBlank
    @Size(max = 10)
    private String password;
}
