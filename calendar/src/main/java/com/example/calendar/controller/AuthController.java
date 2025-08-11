package com.example.calendar.controller;

import com.example.calendar.dto.LoginRequestDto;
import com.example.calendar.dto.UserResponseDto;
import com.example.calendar.entity.User;
import com.example.calendar.repository.UserRepository;
import com.example.calendar.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public static final String SESSION_KEY = "USER_ID";

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody @Valid LoginRequestDto dto, HttpServletRequest req) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));
        if (!passwordEncoder.matches(dto.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        HttpSession session = req.getSession(true);
        session.setAttribute(SESSION_KEY, user.getId());
        return ResponseEntity.ok(new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(),
                user.getCreatedAt(), user.getModifiedAt()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public UserResponseDto me(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(SESSION_KEY) == null)
            throw new IllegalArgumentException("로그인이 필요합니다.");
        Long userId = (Long) session.getAttribute(SESSION_KEY);
        var u = userService.getEntity(userId);
        return new UserResponseDto(u.getId(), u.getUsername(), u.getEmail(), u.getCreatedAt(), u.getModifiedAt());
    }
}
