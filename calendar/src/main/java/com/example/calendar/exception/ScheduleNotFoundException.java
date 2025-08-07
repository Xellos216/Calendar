package com.example.calendar.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(Long id) {
        super("일정 ID " + id + "을(를) 찾을 수 없습니다.");
    }
}
