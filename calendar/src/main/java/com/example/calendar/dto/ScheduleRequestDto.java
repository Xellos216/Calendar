package com.example.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {
    @NotBlank(message = "제목을 입력해")
    @Size(max = 30, message = "30자 이하로 입력해")
    private String title;

    @NotBlank(message = "내용을 입력해")
    @Size(max = 200, message = "200자 이하로 입력해")
    private String content;

    @NotBlank(message = "이름을 입력해")
    @Size(max = 10)
    private String writer;

    @NotBlank(message = "비밀번호를 입력해")
    @Size(max = 15)
    private String password;
}