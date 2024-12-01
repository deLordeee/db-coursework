package com.example.DB.Service.impl;

import com.example.DB.Service.TaskService;
import com.example.DB.dto.TaskDto;
import com.example.DB.entity.Project;
import com.example.DB.entity.Task;
import com.example.DB.entity.User;
import com.example.DB.repository.ProjectRepository;
import com.example.DB.repository.TaskRepository;
import com.example.DB.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskDto createTask(TaskDto taskDto) {
        Project project = projectRepository.findById(taskDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setProject(project);
        task.setStatus(taskDto.getStatus());
        task.setPriority(taskDto.getPriority());
        task.setDueDate(taskDto.getDueDate());

        if (taskDto.getAssignedToId() != null) {
            User assignee = userRepository.findById(taskDto.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            task.setAssignedTo(assignee);
        }

        Task savedTask = taskRepository.save(task);
        return convertToDto(savedTask);
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return convertToDto(task);
    }

    public List<TaskDto> getProjectTasks(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getUserTasks(Long userId) {
        return taskRepository.findByAssignedToId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TaskDto updateTaskStatus(Long id, Task.TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(status);
        return convertToDto(taskRepository.save(task));
    }

    public TaskDto assignTask(Long id, Long userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        User assignee = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setAssignedTo(assignee);
        return convertToDto(taskRepository.save(task));
    }

    private TaskDto convertToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getAssignedTo() != null ? task.getAssignedTo().getId() : null,
                task.getProject().getId(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt()
        );
    }
}