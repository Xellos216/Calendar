package com.example.calendar.controller;

import com.example.calendar.dto.SchedulePageItemDto;
import com.example.calendar.dto.ScheduleRequestDto;
import com.example.calendar.dto.ScheduleResponseDto;
import com.example.calendar.dto.ScheduleUpdateRequestDto;
import com.example.calendar.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> create(@RequestBody @Valid ScheduleRequestDto dto,
                                                      HttpServletRequest req) {
        var session = req.getSession(false);
        if (session == null || session.getAttribute(AuthController.SESSION_KEY) == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        Long sessionUserId = (Long) session.getAttribute(AuthController.SESSION_KEY);

        var saved = scheduleService.create(dto, sessionUserId);
        return ResponseEntity
                .created(URI.create("/api/schedules/" + saved.id()))
                .body(saved);
    }

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

    @GetMapping("/{id}")
    public ScheduleResponseDto get(@PathVariable Long id) {
        return scheduleService.get(id);
    }

    @GetMapping
    public List<ScheduleResponseDto> getAll() {
        return scheduleService.getAll();
    }

    @GetMapping("/user/{userId}")
    public Page<ScheduleResponseDto> pageByUser(@PathVariable Long userId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        return scheduleService.getPageByUser(userId, PageRequest.of(page, size));
    }

    @GetMapping("/page")
    public Page<SchedulePageItemDto> getSchedulesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "modifiedAt"));
        return scheduleService.getPageWithCommentCount(pageable);
    }
}
