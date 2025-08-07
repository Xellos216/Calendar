package com.example.calendar.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long id) {
        super("해당 ID의 댓글을 찾을 수 없습니다: " + id);
    }
}
