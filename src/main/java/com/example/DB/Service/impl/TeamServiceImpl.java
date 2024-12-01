package com.example.DB.Service.impl;

import com.example.DB.Service.TeamService;
import com.example.DB.dto.TeamDto;
import com.example.DB.entity.Team;
import com.example.DB.entity.Member;
import com.example.DB.entity.Project;
import com.example.DB.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    public TeamDto createTeam() {
        Team team = new Team();
        Team savedTeam = teamRepository.save(team);
        return convertToDto(savedTeam);
    }

    public TeamDto getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        return convertToDto(team);
    }

    public List<TeamDto> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TeamDto> getTeamsByMember(Long userId) {
        return teamRepository.findByMembersUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TeamDto> getTeamsByProject(Long projectId) {
        return teamRepository.findByProjectsId(projectId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new RuntimeException("Team not found");
        }
        teamRepository.deleteById(id);
    }

    private TeamDto convertToDto(Team team) {
        return new TeamDto(
                team.getId(),
                team.getCreatedAt(),
                team.getMembers() != null ?
                        team.getMembers().stream().map(Member::getId).collect(Collectors.toList()) :
                        new ArrayList<>(),
                team.getProjects() != null ?
                        team.getProjects().stream().map(Project::getId).collect(Collectors.toList()) :
                        new ArrayList<>()
        );
    }
}