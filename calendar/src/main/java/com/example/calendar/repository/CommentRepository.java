package com.example.calendar.repository;

import com.example.calendar.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByScheduleIdOrderByCreatedAtDesc(Long scheduleId);
    long countByScheduleId(Long scheduleId);
    Optional<Comment> findByIdAndUserId(Long id, Long userId);
}
