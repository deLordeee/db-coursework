package com.example.DB.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrantDto {
    private Long id;
    private Long projectId;
    private Long userId;
    private LocalDateTime createdAt;
}
