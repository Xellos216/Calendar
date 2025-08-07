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

    @NotBlank(message = "댓글을 입력해")
    @Size(max = 100, message = "100자 이하로 입력해")
    private String comment;

    @NotBlank(message = "이름을 입력해")
    @Size(max = 10)
    private String writer;

    @NotBlank(message = "비밀번호를 입력해")
    @Size(max = 15)
    private String password;
}
