package com.example.calendar.controller;

import com.example.calendar.dto.DeleteScheduleRequestDto;
import com.example.calendar.dto.ScheduleRequestDto;
import com.example.calendar.dto.ScheduleResponseDto;
import com.example.calendar.exception.ScheduleNotFoundException;
import com.example.calendar.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody @Valid ScheduleRequestDto requestDto
    ) {
        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(
            @RequestParam(required = false) String writer
    ) {
        List<ScheduleResponseDto> schedules = scheduleService.getSchedules(writer);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(
            @PathVariable Long id
    ) {
        ScheduleResponseDto responseDto = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(responseDto);
    }

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(ScheduleNotFoundException.class)
        public ResponseEntity<String> handleScheduleNotFound(ScheduleNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody @Valid DeleteScheduleRequestDto requestDto
    ) {
        scheduleService.deleteSchedule(id, requestDto);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
