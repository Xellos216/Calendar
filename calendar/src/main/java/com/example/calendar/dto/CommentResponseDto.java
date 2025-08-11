package com.example.calendar.dto;

import com.example.calendar.entity.Comment;

import java.time.LocalDateTime;

public class CommentResponseDto {
    private Long id;
    private String contents;
    private Long userId;
    private String username;
    private Long scheduleId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Long id, String contents, Long userId, String username,
                              Long scheduleId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.contents = contents;
        this.userId = userId;
        this.username = username;
        this.scheduleId = scheduleId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentResponseDto from(Comment c) {
        return new CommentResponseDto(
                c.getId(),
                c.getContents(),
                c.getUser().getId(),
                c.getUser().getUsername(),
                c.getSchedule().getId(),
                c.getCreatedAt(),
                c.getModifiedAt()
        );
    }

    public Long getId() { return id; }
    public String getContents() { return contents; }
    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public Long getScheduleId() { return scheduleId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getModifiedAt() { return modifiedAt; }
}
