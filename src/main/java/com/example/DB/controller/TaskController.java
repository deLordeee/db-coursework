package com.example.DB.controller;

import com.example.DB.Service.TaskService;
import com.example.DB.dto.TaskDto;
import com.example.DB.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/project/{projectId}")
    public List<TaskDto> getProjectTasks(@PathVariable Long projectId) {
        return taskService.getProjectTasks(projectId);
    }

    @GetMapping("/user/{userId}")
    public List<TaskDto> getUserTasks(@PathVariable Long userId) {
        return taskService.getUserTasks(userId);
    }

    @PatchMapping("/{id}/status")
    public TaskDto updateTaskStatus(@PathVariable Long id, @RequestBody Task.TaskStatus status) {
        return taskService.updateTaskStatus(id, status);
    }

    @PatchMapping("/{id}/assign/{userId}")
    public TaskDto assignTask(@PathVariable Long id, @PathVariable Long userId) {
        return taskService.assignTask(id, userId);
    }
}