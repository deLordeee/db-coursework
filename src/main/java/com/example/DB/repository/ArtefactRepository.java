package com.example.DB.repository;

import com.example.DB.entity.Artefact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtefactRepository extends JpaRepository<Artefact, Long> {
    List<Artefact> findByProjectId(Long projectId);
    List<Artefact> findByUploadedById(Long userId);
    List<Artefact> findByFileType(String fileType);
}
