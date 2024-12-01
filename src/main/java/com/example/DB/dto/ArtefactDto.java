package com.example.DB.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtefactDto {
    private Long id;
    private String title;
    private String description;
    private String filePath;
    private String fileType;
    private Long uploadedById;
    private Long projectId;
    private LocalDateTime createdAt;
}
