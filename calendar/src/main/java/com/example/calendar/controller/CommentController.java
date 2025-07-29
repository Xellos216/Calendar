package com.example.calendar.controller;

import com.example.calendar.entity.Comment;
import com.example.calendar.entity.Schedule;
import com.example.calendar.service.CommentService;
import com.example.calendar.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final ScheduleService scheduleService;

    public CommentController(CommentService commentService, ScheduleService scheduleService) {
        this.commentService = commentService;
        this.scheduleService = scheduleService;
    }

    // 댓글 작성
    @PostMapping("/write")
    public String write(@RequestParam Long scheduleId, @RequestParam String content) {
        Schedule schedule = scheduleService.findById(scheduleId);

        if (schedule != null && content != null && !content.trim().isEmpty()) {
            Comment comment = new Comment();
            comment.setSchedule(schedule);
            comment.setContent(content);
            commentService.save(comment);
        }

        return "redirect:/schedule/detail?id=" + scheduleId;
    }

    // 댓글 삭제
    @GetMapping("/delete")
    public String delete(@RequestParam Long id, @RequestParam Long scheduleId) {
        commentService.delete(id);
        return "redirect:/schedule/detail?id=" + scheduleId;
    }
}
