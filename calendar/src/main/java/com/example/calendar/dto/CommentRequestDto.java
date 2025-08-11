package com.example.calendar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommentRequestDto {

    @NotNull
    private Long scheduleId;

    @NotBlank
    @Size(max = 1000)
    private String contents;

    public CommentRequestDto() {}
    public CommentRequestDto(Long scheduleId, String contents) {
        this.scheduleId = scheduleId; this.contents = contents;
    }

    public Long getScheduleId() { return scheduleId; }
    public String getContents() { return contents; }
}
