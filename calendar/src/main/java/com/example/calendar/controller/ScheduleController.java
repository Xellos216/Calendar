package com.example.calendar.controller;

import com.example.calendar.entity.Schedule;
import com.example.calendar.service.ScheduleService;
import com.example.calendar.service.CommentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final CommentService commentService;

    public ScheduleController(ScheduleService scheduleService, CommentService commentService) {
        this.scheduleService = scheduleService;
        this.commentService = commentService;
    }
    // 캘린더 월별 보기
    @GetMapping("/calendar")
    public String calendar(@RequestParam(required = false)
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                           LocalDate date,
                           Model model) {
        if (date == null) {
            date = LocalDate.now();
        }

        YearMonth yearMonth = YearMonth.from(date);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        List<Schedule> schedules = scheduleService.getMonthlySchedules(start, end);

        model.addAttribute("yearMonth", yearMonth);
        model.addAttribute("schedules", schedules);
        return "calendar";
    }

    // 일정 작성 폼
    @GetMapping("/schedule/write")
    public String writeForm(Model model) {
        model.addAttribute("schedule", new Schedule());
        return "write";
    }

    // 일정 저장 처리
    @PostMapping("/schedule/write")
    public String save(@ModelAttribute Schedule schedule) {
        scheduleService.save(schedule);
        return "redirect:/calendar";
    }

    // 일정 상세 보기
    @GetMapping("/schedule/detail")
    public String detail(@RequestParam Long id, Model model) {
        Schedule schedule = scheduleService.findById(id);
        model.addAttribute("schedule", schedule);

        // 댓글 리스트 추가
        model.addAttribute("scheduleComments", commentService.findBySchedule(schedule));

        return "detail";
    }

    // 일정 삭제
    @GetMapping("/schedule/delete")
    public String delete(@RequestParam Long id) {
        scheduleService.delete(id);
        return "redirect:/calendar";
    }
}
