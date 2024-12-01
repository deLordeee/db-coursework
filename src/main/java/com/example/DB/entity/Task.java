package com.example.DB.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "assignedTo")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "projectId", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority = TaskPriority.MEDIUM;

    private LocalDateTime dueDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public enum TaskStatus {
        PENDING, IN_PROGRESS, COMPLETED, ON_HOLD, CANCELLED
    }

    public enum TaskPriority {
        LOW, MEDIUM, HIGH
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}


