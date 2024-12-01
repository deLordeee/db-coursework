package com.example.DB.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private Long id;
    private LocalDateTime createdAt;
    private List<Long> memberIds;
    private List<Long> projectIds;
}