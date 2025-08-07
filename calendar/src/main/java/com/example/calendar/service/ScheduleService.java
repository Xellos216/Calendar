package com.example.calendar.service;

import com.example.calendar.dto.*;
import com.example.calendar.entity.Comment;
import com.example.calendar.entity.Schedule;
import com.example.calendar.exception.InvalidPasswordException;
import com.example.calendar.exception.ScheduleNotFoundException;
import com.example.calendar.repository.CommentRepository;
import com.example.calendar.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

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
                saved.getModifiedAt(),
                new ArrayList<>()
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
                .map(schedule -> {
                    List<Comment> commentList = commentRepository.findAllByScheduleIdOrderByCreatedAtAsc(schedule.getId());
                    List<CommentResponseDto> commentDtos = commentList.stream()
                            .map(CommentResponseDto::from)
                            .collect(Collectors.toList());

                    // schedule과 함께 commentDtos를 from() 메서드에 넘김
                    return ScheduleResponseDto.from(schedule, commentDtos);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));

        List<Comment> commentList = commentRepository.findAllByScheduleIdOrderByCreatedAtAsc(id);

        List<CommentResponseDto> commentDtos = commentList.stream()
                .map(CommentResponseDto::from)
                .collect(Collectors.toList());

        return ScheduleResponseDto.from(schedule, commentDtos);
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new InvalidPasswordException();
        }

        schedule.update(requestDto.getTitle(), requestDto.getWriter());

        List<Comment> commentList = commentRepository.findAllByScheduleIdOrderByCreatedAtAsc(schedule.getId());
        List<CommentResponseDto> commentDtos = commentList.stream()
                .map(CommentResponseDto::from)
                .collect(Collectors.toList());

        return ScheduleResponseDto.from(schedule, commentDtos);
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


