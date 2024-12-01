package com.example.DB.Service.impl;

import com.example.DB.Service.ProjectService;
import com.example.DB.dto.ProjectDto;
import com.example.DB.entity.Project;
import com.example.DB.entity.Team;
import com.example.DB.entity.User;
import com.example.DB.entity.Task;
import com.example.DB.entity.Artefact;
import com.example.DB.repository.ProjectRepository;
import com.example.DB.repository.TeamRepository;
import com.example.DB.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setOwner(userRepository.findById(projectDto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found")));
        project.setTeam(teamRepository.findById(projectDto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found")));
        project.setTasks(new ArrayList<>());
        project.setArtefacts(new ArrayList<>());
        project.setCreatedAt(LocalDateTime.now());

        return convertToDto(projectRepository.save(project));
    }

    public ProjectDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return convertToDto(project);
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProjectDto> getProjectsByOwner(Long ownerId) {
        return projectRepository.findByOwnerId(ownerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProjectDto updateProject(Long id, ProjectDto projectDto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());

        Project updatedProject = projectRepository.save(project);
        return convertToDto(updatedProject);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(id);
    }

    private ProjectDto convertToDto(Project project) {
        return new ProjectDto(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getOwner().getId(),
                project.getTeam().getId(),
                project.getCreatedAt(),
                project.getTasks().stream().map(Task::getId).collect(Collectors.toList()),
                project.getArtefacts().stream().map(Artefact::getId).collect(Collectors.toList())
        );
    }
}