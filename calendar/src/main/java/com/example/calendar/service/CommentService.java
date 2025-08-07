package com.example.calendar.service;

import com.example.calendar.dto.CommentRequestDto;
import com.example.calendar.dto.CommentResponseDto;
import com.example.calendar.entity.Comment;
import com.example.calendar.entity.Schedule;
import com.example.calendar.exception.ScheduleNotFoundException;
import com.example.calendar.repository.CommentRepository;
import com.example.calendar.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException(scheduleId));

        long commentCount = commentRepository.countByScheduleId(scheduleId);
        if (commentCount >= 10) {
            throw new IllegalStateException("댓글은 최대 10개");
        }

        Comment comment = new Comment(
                null,
                schedule,
                requestDto.getComment(),
                requestDto.getWriter(),
                requestDto.getPassword(),
                null, // createdAT
                null // modifiedAt
        );
        Comment saved = commentRepository.save(comment);
        return CommentResponseDto.from(saved);
    }
}

