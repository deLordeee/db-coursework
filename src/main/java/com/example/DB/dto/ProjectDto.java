package com.example.DB.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private Long ownerId;
    private Long teamId;
    private LocalDateTime createdAt;
    private List<Long> taskIds;
    private List<Long> artefactIds;
}