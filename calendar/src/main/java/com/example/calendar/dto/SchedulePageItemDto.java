package com.example.calendar.dto;

import java.time.LocalDateTime;

// 구조 파악을 위해 Lombok 미사용
public class SchedulePageItemDto {
    private Long id;
    private String title;
    private String contents;
    private Long userId;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private long commentCount;

    public SchedulePageItemDto(Long id, String title, String contents,
                               Long userId, String username,
                               LocalDateTime createdAt, LocalDateTime modifiedAt,
                               long commentCount) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.userId = userId;
        this.username = username;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.commentCount = commentCount;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContents() { return contents; }
    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getModifiedAt() { return modifiedAt; }
    public long getCommentCount() { return commentCount; }
}

