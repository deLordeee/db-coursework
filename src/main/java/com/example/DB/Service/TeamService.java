package com.example.DB.Service;

import com.example.DB.dto.TeamDto;

import java.util.List;

public interface TeamService {
    TeamDto createTeam();
    

    TeamDto getTeamById(Long id);

    List<TeamDto> getAllTeams();

    List<TeamDto> getTeamsByMember(Long userId);

    List<TeamDto> getTeamsByProject(Long projectId);

    void deleteTeam(Long id);
}
