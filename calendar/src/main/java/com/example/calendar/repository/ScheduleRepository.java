package com.example.calendar.repository;

import com.example.calendar.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByOrderByCreatedAtDesc();
    List<Schedule> findByWriterOrderByCreatedAtDesc(String writer);
}
