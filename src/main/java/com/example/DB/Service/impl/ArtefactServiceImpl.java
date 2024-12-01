package com.example.DB.Service.impl;

import com.example.DB.Service.ArtefactService;
import com.example.DB.dto.ArtefactDto;
import com.example.DB.entity.Artefact;
import com.example.DB.entity.User;
import com.example.DB.entity.Project;
import com.example.DB.repository.ArtefactRepository;
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
public class ArtefactServiceImpl implements ArtefactService {
    private final ArtefactRepository artefactRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public ArtefactDto createArtefact(ArtefactDto artefactDto) {
        User uploader = userRepository.findById(artefactDto.getUploadedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(artefactDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Artefact artefact = new Artefact();
        artefact.setTitle(artefactDto.getTitle());
        artefact.setDescription(artefactDto.getDescription());
        artefact.setFilePath(artefactDto.getFilePath());
        artefact.setFileType(artefactDto.getFileType());
        artefact.setUploadedBy(uploader);
        artefact.setProject(project);

        Artefact savedArtefact = artefactRepository.save(artefact);
        return convertToDto(savedArtefact);
    }

    public ArtefactDto getArtefactById(Long id) {
        Artefact artefact = artefactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artefact not found"));
        return convertToDto(artefact);
    }

    public List<ArtefactDto> getProjectArtefacts(Long projectId) {
        return artefactRepository.findByProjectId(projectId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ArtefactDto> getUserArtefacts(Long userId) {
        return artefactRepository.findByUploadedById(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ArtefactDto updateArtefact(Long id, ArtefactDto artefactDto) {
        Artefact artefact = artefactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artefact not found"));

        artefact.setTitle(artefactDto.getTitle());
        artefact.setDescription(artefactDto.getDescription());

        return convertToDto(artefactRepository.save(artefact));
    }

    private ArtefactDto convertToDto(Artefact artefact) {
        return new ArtefactDto(
                artefact.getId(),
                artefact.getTitle(),
                artefact.getDescription(),
                artefact.getFilePath(),
                artefact.getFileType(),
                artefact.getUploadedBy().getId(),
                artefact.getProject().getId(),
                artefact.getCreatedAt()
        );
    }
}
