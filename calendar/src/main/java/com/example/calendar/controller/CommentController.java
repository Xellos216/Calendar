package com.example.calendar.controller;

import com.example.calendar.dto.CommentRequestDto;
import com.example.calendar.dto.CommentResponseDto;
import com.example.calendar.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) { this.commentService = commentService; }

    private Long requireSessionUserId(HttpServletRequest req) {
        var session = req.getSession(false);
        if (session == null || session.getAttribute(AuthController.SESSION_KEY) == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        return (Long) session.getAttribute(AuthController.SESSION_KEY);
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> create(@RequestBody @Valid CommentRequestDto dto,
                                                     HttpServletRequest req) {
        Long userId = requireSessionUserId(req);
        var created = commentService.create(dto, userId);
        return ResponseEntity.created(URI.create("/api/comments/" + created.getId())).body(created);
    }

    @GetMapping("/comments/{id}")
    public CommentResponseDto getOne(@PathVariable Long id) {
        return commentService.getOne(id);
    }

    @GetMapping("/schedules/{scheduleId}/comments")
    public List<CommentResponseDto> getBySchedule(@PathVariable Long scheduleId) {
        return commentService.getBySchedule(scheduleId);
    }

    @PutMapping("/comments/{id}")
    public CommentResponseDto update(@PathVariable Long id,
                                     @RequestBody @Valid CommentRequestDto dto,
                                     HttpServletRequest req) {
        Long userId = requireSessionUserId(req);
        return commentService.update(id, dto, userId);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest req) {
        Long userId = requireSessionUserId(req);
        commentService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
