package com.example.calendar.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "schedules")
public class Schedule extends BaseTimeEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "contents", columnDefinition = "TEXT", nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Schedule() {}

    public Schedule(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContents() { return contents; }
    public User getUser() { return user; }

    public void change(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}



