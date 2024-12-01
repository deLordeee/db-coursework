package com.example.DB.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Artefact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artefact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileType;

    @ManyToOne
    @JoinColumn(name = "uploadedBy", nullable = false)
    private User uploadedBy;

    @ManyToOne
    @JoinColumn(name = "projectId", nullable = false)
    private Project project;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}