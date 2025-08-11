package com.example.calendar.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comments", indexes = {
        @Index(name = "idx_comments_schedule_id", columnList = "schedule_id"),
        @Index(name = "idx_comments_user_id", columnList = "user_id")
})
public class Comment extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents", nullable = false, columnDefinition = "TEXT")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_comment_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_comment_schedule"))
    private Schedule schedule;

    protected Comment() {}

    private Comment(String contents, User user, Schedule schedule) {
        this.contents = contents;
        this.user = user;
        this.schedule = schedule;
    }

    public static Comment of(String contents, User user, Schedule schedule) {
        return new Comment(contents, user, schedule);
    }

    public Long getId() { return id; }
    public String getContents() { return contents; }
    public User getUser() { return user; }
    public Schedule getSchedule() { return schedule; }

    public void change(String contents) { this.contents = contents; }
}


