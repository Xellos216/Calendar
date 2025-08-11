package com.example.calendar.service;

import com.example.calendar.dto.UserRequestDto;
import com.example.calendar.dto.UserResponseDto;
import com.example.calendar.dto.UserUpdateRequestDto;
import com.example.calendar.entity.User;
import com.example.calendar.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseDto create(UserRequestDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }
        User saved = userRepository.save(new User(dto.username(), dto.email()));
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
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private UserResponseDto toDto(User u) {
        return new UserResponseDto(u.getId(), u.getUsername(), u.getEmail(),
                u.getCreatedAt(), u.getModifiedAt());
    }
}
