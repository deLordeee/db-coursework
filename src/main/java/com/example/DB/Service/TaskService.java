package com.example.DB.Service;

import com.example.DB.dto.TaskDto;
import com.example.DB.entity.Task;

import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto);

    TaskDto getTaskById(Long id);

    List<TaskDto> getProjectTasks(Long projectId);

    List<TaskDto> getUserTasks(Long userId);

    TaskDto updateTaskStatus(Long id, Task.TaskStatus status);

    TaskDto assignTask(Long id, Long userId);
}
