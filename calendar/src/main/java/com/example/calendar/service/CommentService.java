package com.example.calendar.service;

import com.example.calendar.dto.CommentRequestDto;
import com.example.calendar.dto.CommentResponseDto;
import com.example.calendar.dto.DeleteCommentRequestDto;
import com.example.calendar.entity.Comment;
import com.example.calendar.entity.Schedule;
import com.example.calendar.exception.CommentNotFoundException;
import com.example.calendar.exception.InvalidPasswordException;
import com.example.calendar.exception.ScheduleNotFoundException;
import com.example.calendar.repository.CommentRepository;
import com.example.calendar.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void deleteComment(Long scheduleId, Long commentId, DeleteCommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (!comment.getSchedule().getId().equals(scheduleId)) {
            throw new IllegalArgumentException("댓글이 해당 일정에 속하지 않습니다.");
        }

        if (!comment.getPassword().equals(requestDto.getPassword())) {
            throw new InvalidPasswordException();
        }

        commentRepository.delete(comment);
    }

}

