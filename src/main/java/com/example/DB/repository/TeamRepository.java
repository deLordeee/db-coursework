package com.example.DB.repository;

import com.example.DB.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByMembersUserId(Long userId);
    List<Team> findByProjectsId(Long projectId);
}

