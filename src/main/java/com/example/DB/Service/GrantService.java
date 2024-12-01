package com.example.DB.Service;

import com.example.DB.dto.GrantDto;

import java.util.List;

public interface GrantService {
    GrantDto createGrant(GrantDto grantDto);

    GrantDto getGrantById(Long id);

    List<GrantDto> getGrantsByProject(Long projectId);

    List<GrantDto> getGrantsByUser(Long userId);

    void revokeGrant(Long id);
}
