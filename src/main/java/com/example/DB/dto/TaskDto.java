package com.example.DB.dto;

import com.example.DB.entity.Task;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Long assignedToId;
    private Long projectId;
    private Task.TaskStatus status;
    private Task.TaskPriority priority;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
}