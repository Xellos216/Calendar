package com.example.calendar.dto;

import com.example.calendar.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String comment;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getComment(),
                comment.getWriter(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
