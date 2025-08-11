package com.example.calendar.controller;

import com.example.calendar.dto.*;
import com.example.calendar.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto dto) {
        UserResponseDto saved = userService.create(dto);
        return ResponseEntity.created(URI.create("/api/users/" + saved.id())).body(saved);
    }

    @GetMapping("/{id}")
    public UserResponseDto get(@PathVariable Long id) { return userService.get(id); }

    @GetMapping
    public List<UserResponseDto> getAll() { return userService.getAll(); }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id,
                                  @RequestBody @Valid UserUpdateRequestDto dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
