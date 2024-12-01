package com.example.DB.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Member")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "teamId", nullable = false)
    private Team team;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeamRole teamRole = TeamRole.Developer;

    @Column(nullable = false)
    private LocalDateTime joinedAt;

    public enum TeamRole {
        Developer, Project_Leader
    }

    @PrePersist
    protected void onCreate() {
        joinedAt = LocalDateTime.now();
    }
}