package com.example.DB.dto;

import com.example.DB.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Long roleId;
    private User.UserStatus status;
    private LocalDateTime createdAt;
}
