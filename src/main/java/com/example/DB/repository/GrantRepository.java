package com.example.DB.repository;

import com.example.DB.entity.Grant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrantRepository extends JpaRepository<Grant, Long> {
    List<Grant> findByProjectId(Long projectId);
    List<Grant> findByUserId(Long userId);
    Optional<Grant> findByProjectIdAndUserId(Long projectId, Long userId);
    boolean existsByProjectIdAndUserId(Long projectId, Long userId);
}