package com.example.calendar.service;

import com.example.calendar.entity.Comment;
import com.example.calendar.entity.Schedule;
import com.example.calendar.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // 댓글 저장
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    // 댓글 삭제
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    // 특정 일정의 댓글 목록 조회
    public List<Comment> findBySchedule(Schedule schedule) {
        return commentRepository.findByScheduleOrderByCreatedAtDesc(schedule);
    }
}
