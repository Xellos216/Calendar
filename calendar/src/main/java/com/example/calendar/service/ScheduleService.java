package com.example.calendar.service;

import com.example.calendar.dto.ScheduleRequestDto;
import com.example.calendar.dto.ScheduleResponseDto;
import com.example.calendar.entity.Schedule;
import com.example.calendar.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // ScheduleService.java
    public Schedule findById(Long id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getWriter(),
                requestDto.getPassword()
        );

        Schedule saved = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getWriter(),
                saved.getCreatedAt(),
                saved.getModifiedAt()
        );
    }
}
