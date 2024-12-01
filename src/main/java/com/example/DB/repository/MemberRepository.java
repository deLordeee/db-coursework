package com.example.DB.repository;

import com.example.DB.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByTeamId(Long teamId);
    List<Member> findByUserId(Long userId);
    Optional<Member> findByUserIdAndTeamId(Long userId, Long teamId);
    boolean existsByUserIdAndTeamId(Long userId, Long teamId);
    List<Member> findByTeamRoleAndTeamId(Member.TeamRole role, Long teamId);
}
