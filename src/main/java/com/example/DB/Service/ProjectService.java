package com.example.DB.Service;

import com.example.DB.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    ProjectDto createProject(ProjectDto projectDto);

    ProjectDto getProjectById(Long id);

    List<ProjectDto> getAllProjects();

    List<ProjectDto> getProjectsByOwner(Long ownerId);

    ProjectDto updateProject(Long id, ProjectDto projectDto);

    void deleteProject(Long id);
}
