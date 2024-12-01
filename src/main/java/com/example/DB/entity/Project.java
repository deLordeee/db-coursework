package com.example.DB.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ownerId", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "teamId", nullable = false)
    private Team team;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Artefact> artefacts = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Grant> grants;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}