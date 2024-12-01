package com.example.DB.repository;

import com.example.DB.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwnerId(Long ownerId);
    List<Project> findByTeamId(Long teamId);
    Optional<Project> findByName(String name);
    boolean existsByName(String name);
}