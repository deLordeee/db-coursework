package com.example.DB.Service.impl;

import com.example.DB.Service.GrantService;
import com.example.DB.dto.GrantDto;
import com.example.DB.entity.Grant;
import com.example.DB.entity.Project;
import com.example.DB.entity.User;
import com.example.DB.repository.GrantRepository;
import com.example.DB.repository.ProjectRepository;
import com.example.DB.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GrantServiceImpl implements GrantService {
    private final GrantRepository grantRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public GrantDto createGrant(GrantDto grantDto) {
        if (grantRepository.existsByProjectIdAndUserId(grantDto.getProjectId(), grantDto.getUserId())) {
            throw new RuntimeException("Grant already exists for this project and user");
        }

        Project project = projectRepository.findById(grantDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = userRepository.findById(grantDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Grant grant = new Grant();
        grant.setProject(project);
        grant.setUser(user);

        Grant savedGrant = grantRepository.save(grant);
        return convertToDto(savedGrant);
    }

    public GrantDto getGrantById(Long id) {
        Grant grant = grantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grant not found"));
        return convertToDto(grant);
    }

    public List<GrantDto> getGrantsByProject(Long projectId) {
        return grantRepository.findByProjectId(projectId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<GrantDto> getGrantsByUser(Long userId) {
        return grantRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void revokeGrant(Long id) {
        if (!grantRepository.existsById(id)) {
            throw new RuntimeException("Grant not found");
        }
        grantRepository.deleteById(id);
    }

    private GrantDto convertToDto(Grant grant) {
        return new GrantDto(
                grant.getId(),
                grant.getProject().getId(),
                grant.getUser().getId(),
                grant.getCreatedAt()
        );
    }
}
