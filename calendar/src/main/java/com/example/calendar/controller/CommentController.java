package com.example.calendar.controller;

import com.example.calendar.dto.CommentRequestDto;
import com.example.calendar.dto.CommentResponseDto;
import com.example.calendar.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long id,
            @RequestBody @Valid CommentRequestDto requestDto
    ) {
        CommentResponseDto responseDto = commentService.createComment(id, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}

