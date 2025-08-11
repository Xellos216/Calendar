package com.example.calendar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users",
        indexes = {
                @Index(name = "idx_user_email", columnList = "email", unique = true)
        })
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "username", length = 30, nullable = false)
    private String username;

    @Email
    @NotBlank
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    protected User() {} // JPA

    public User(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }


    @Column(name = "password_hash", length = 100, nullable = false)
    private String passwordHash;

    public String getPasswordHash() { return passwordHash; }

    public void change(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void changePassword(String encodedPassword) {
        this.passwordHash = encodedPassword;
    }
}
