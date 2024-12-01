package com.example.DB.Service.impl;

import com.example.DB.Service.UserService;
import com.example.DB.dto.UserDto;
import com.example.DB.entity.Role;
import com.example.DB.entity.User;
import com.example.DB.repository.RoleRepository;
import com.example.DB.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword((userDto.getPassword()));
        user.setStatus(User.UserStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        Role role = roleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDto.getUsername());
        user.setStatus(userDto.getStatus());

        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                (long) user.getRole().getId(),
                user.getStatus(),
                user.getCreatedAt()
        );
    }
}
