package com.example.calendar.service;

import com.example.calendar.dto.DeleteScheduleRequestDto;
import com.example.calendar.dto.ScheduleRequestDto;
import com.example.calendar.dto.ScheduleResponseDto;
import com.example.calendar.entity.Schedule;
import com.example.calendar.exception.InvalidPasswordException;
import com.example.calendar.exception.ScheduleNotFoundException;
import com.example.calendar.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

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

    public List<ScheduleResponseDto> getSchedules(String writer) {
        List<Schedule> schedules;

        if (writer == null || writer.isBlank()) {
            schedules = scheduleRepository.findAllByOrderByCreatedAtDesc();
        } else {
            schedules = scheduleRepository.findByWriterOrderByCreatedAtDesc(writer);
        }

        return schedules.stream()
                .map(ScheduleResponseDto::from)
                .collect(Collectors.toList());
    }

    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));

        return ScheduleResponseDto.from(schedule);
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new InvalidPasswordException();
        }

        schedule.update(requestDto.getTitle(), requestDto.getWriter());

        return ScheduleResponseDto.from(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id, DeleteScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new InvalidPasswordException();
        }
        scheduleRepository.delete(schedule);
    }
}


