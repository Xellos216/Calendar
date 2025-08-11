package com.example.calendar.service;

import com.example.calendar.dto.CommentRequestDto;
import com.example.calendar.dto.CommentResponseDto;
import com.example.calendar.entity.Comment;
import com.example.calendar.entity.Schedule;
import com.example.calendar.entity.User;
import com.example.calendar.exception.ForbiddenOperationException;
import com.example.calendar.exception.UnauthorizedException;
import com.example.calendar.repository.CommentRepository;
import com.example.calendar.repository.ScheduleRepository;
import com.example.calendar.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          ScheduleRepository scheduleRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    private User getSessionUser(Long sessionUserId) {
        if (sessionUserId == null) throw new UnauthorizedException("로그인이 필요합니다.");
        return userRepository.findById(sessionUserId)
                .orElseThrow(() -> new UnauthorizedException("세션 유저를 찾을 수 없습니다."));
    }

    @Transactional
    public CommentResponseDto create(CommentRequestDto dto, Long sessionUserId) {
        User user = getSessionUser(sessionUserId);
        Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));
        Comment comment = Comment.of(dto.getContents(), user, schedule);
        return CommentResponseDto.from(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getOne(Long id) {
        Comment c = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        return CommentResponseDto.from(c);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getBySchedule(Long scheduleId) {
        return commentRepository.findAllByScheduleIdOrderByCreatedAtDesc(scheduleId)
                .stream().map(CommentResponseDto::from).toList();
    }

    @Transactional
    public CommentResponseDto update(Long id, CommentRequestDto dto, Long sessionUserId) {
        User user = getSessionUser(sessionUserId);
        Comment c = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if (!c.getUser().getId().equals(user.getId())) {
            throw new ForbiddenOperationException("본인만 수정할 수 있습니다.");
        }
        c.change(dto.getContents());
        return CommentResponseDto.from(c);
    }

    @Transactional
    public void delete(Long id, Long sessionUserId) {
        User user = getSessionUser(sessionUserId);
        Comment c = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if (!c.getUser().getId().equals(user.getId())) {
            throw new ForbiddenOperationException("본인만 삭제할 수 있습니다.");
        }
        commentRepository.delete(c);
    }
}
