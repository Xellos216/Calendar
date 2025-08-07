package com.example.calendar.repository;

import com.example.calendar.entity.Comment;
import com.example.calendar.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByScheduleOrderByCreatedAtDesc(Schedule schedule);
    long countByScheduleId(Long scheduleId);
}
