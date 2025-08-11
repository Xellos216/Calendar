package com.example.calendar.service;

import com.example.calendar.dto.*;
import com.example.calendar.entity.Schedule;
import com.example.calendar.entity.User;
import com.example.calendar.exception.ScheduleNotFoundException;
import com.example.calendar.repository.ScheduleRepository;
import com.example.calendar.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ScheduleResponseDto create(ScheduleRequestDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        Schedule saved = scheduleRepository.save(new Schedule(dto.title(), dto.contents(), user));
        return toDto(saved);
    }

    public ScheduleResponseDto get(Long id) {
        Schedule s = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));
        return toDto(s);
    }

    public List<ScheduleResponseDto> getAll() {
        return scheduleRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional
    public ScheduleResponseDto update(Long id, ScheduleUpdateRequestDto dto) {
        Schedule s = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));
        s.change(dto.title(), dto.contents());
        return toDto(s);
    }

    @Transactional
    public void delete(Long id) {
        if (!scheduleRepository.existsById(id)) throw new ScheduleNotFoundException(id);
        scheduleRepository.deleteById(id);
    }

    public Page<ScheduleResponseDto> getPageByUser(Long userId, Pageable pageable) {
        return scheduleRepository.findAllByUser_Id(userId, pageable).map(this::toDto);
    }

    private ScheduleResponseDto toDto(Schedule s) {
        return new ScheduleResponseDto(
                s.getId(), s.getTitle(), s.getContents(),
                s.getUser().getId(), s.getUser().getUsername(),
                s.getCreatedAt(), s.getModifiedAt()
        );
    }
}
