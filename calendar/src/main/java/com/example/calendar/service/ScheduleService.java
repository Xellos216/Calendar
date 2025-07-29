package com.example.calendar.service;

import com.example.calendar.entity.Schedule;
import com.example.calendar.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 전체 일정 조회 (월 단위)
    public List<Schedule> getMonthlySchedules(LocalDate startDate, LocalDate endDate) {
        return scheduleRepository.findByDateBetween(startDate, endDate);
    }

    // 일정 저장
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    // 일정 상세 조회
    public Schedule findById(Long id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    // 일정 삭제
    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }
}
