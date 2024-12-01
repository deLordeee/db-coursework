package com.example.DB.controller;

import com.example.DB.Service.TeamService;
import com.example.DB.dto.TeamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDto createTeam() {
        return teamService.createTeam();
    }

    @GetMapping("/{id}")
    public TeamDto getTeam(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    @GetMapping
    public List<TeamDto> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/member/{userId}")
    public List<TeamDto> getTeamsByMember(@PathVariable Long userId) {
        return teamService.getTeamsByMember(userId);
    }

    @GetMapping("/project/{projectId}")
    public List<TeamDto> getTeamsByProject(@PathVariable Long projectId) {
        return teamService.getTeamsByProject(projectId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }
}

