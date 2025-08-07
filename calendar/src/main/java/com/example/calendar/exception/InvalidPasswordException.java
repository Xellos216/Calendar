package com.example.calendar.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("비밀번호가 틀렸단다, 머저리야.");
    }
}
