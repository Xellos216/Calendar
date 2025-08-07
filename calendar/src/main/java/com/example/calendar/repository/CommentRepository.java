package com.example.calendar.repository;

import com.example.calendar.entity.Comment;
import com.example.calendar.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 일정에 달린 댓글 리스트 조회 (최신순)
    List<Comment> findByScheduleOrderByCreatedAtDesc(Schedule schedule);
}
