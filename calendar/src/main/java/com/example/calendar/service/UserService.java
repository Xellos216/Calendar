package com.example.calendar.service;

import com.example.calendar.dto.UserRequestDto;
import com.example.calendar.dto.UserResponseDto;
import com.example.calendar.dto.UserUpdateRequestDto;
import com.example.calendar.entity.User;
import com.example.calendar.repository.ScheduleRepository;
import com.example.calendar.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ScheduleRepository scheduleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ScheduleRepository scheduleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public UserResponseDto create(UserRequestDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }
        String hash = passwordEncoder.encode(dto.password());
        User saved = userRepository.save(new User(dto.username(), dto.email(), hash));
        return toDto(saved);
    }

    public UserResponseDto get(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        return toDto(user);
    }

    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional
    public UserResponseDto update(Long id, UserUpdateRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        if (!user.getEmail().equals(dto.email()) && userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }
        user.change(dto.username(), dto.email());
        return toDto(user);
    }

    @Transactional
    public void changePassword(Long id, String rawPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        user.changePassword(passwordEncoder.encode(rawPassword));
    }

    public User getEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }

    @Transactional
    public void delete(Long id) {
        if (scheduleRepository.countByUser_Id(id) > 0) {
            throw new IllegalStateException("해당 유저의 일정이 남아 있어 삭제할 수 없습니다.");
        }
        userRepository.deleteById(id);
    }

    private UserResponseDto toDto(User u) {
        return new UserResponseDto(u.getId(), u.getUsername(), u.getEmail(),
                u.getCreatedAt(), u.getModifiedAt());
    }
}
