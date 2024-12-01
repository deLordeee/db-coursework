package com.example.DB.dto;

import com.example.DB.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private Long userId;
    private Long teamId;
    private Member.TeamRole teamRole;
    private LocalDateTime joinedAt;
}