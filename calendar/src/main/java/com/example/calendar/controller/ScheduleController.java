package com.example.calendar.controller;

import com.example.calendar.dto.*;
import com.example.calendar.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) { this.scheduleService = scheduleService; }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> create(@RequestBody @Valid ScheduleRequestDto dto) {
        var saved = scheduleService.create(dto);
        return ResponseEntity.created(URI.create("/api/schedules/" + saved.id())).body(saved);
    }

    @GetMapping("/{id}")
    public ScheduleResponseDto get(@PathVariable Long id) { return scheduleService.get(id); }

    @GetMapping
    public List<ScheduleResponseDto> getAll() { return scheduleService.getAll(); }

    @PutMapping("/{id}")
    public ScheduleResponseDto update(@PathVariable Long id,
                                      @RequestBody @Valid ScheduleUpdateRequestDto dto) {
        return scheduleService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public Page<ScheduleResponseDto> pageByUser(@PathVariable Long userId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        return scheduleService.getPageByUser(userId, PageRequest.of(page, size));
    }
}
