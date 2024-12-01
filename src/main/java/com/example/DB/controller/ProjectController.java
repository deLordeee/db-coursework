package com.example.DB.controller;

import com.example.DB.Service.ProjectService;
import com.example.DB.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(@RequestBody ProjectDto projectDto) {
        return projectService.createProject(projectDto);
    }

    @GetMapping("/{id}")
    public ProjectDto getProject(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @GetMapping
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/owner/{ownerId}")
    public List<ProjectDto> getProjectsByOwner(@PathVariable Long ownerId) {
        return projectService.getProjectsByOwner(ownerId);
    }

    @PutMapping("/{id}")
    public ProjectDto updateProject(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
        return projectService.updateProject(id, projectDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}