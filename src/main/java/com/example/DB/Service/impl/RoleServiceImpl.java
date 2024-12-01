package com.example.DB.Service.impl;


import com.example.DB.Service.RoleService;
import com.example.DB.dto.RoleDTO;
import com.example.DB.entity.Role;
import com.example.DB.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleDTO createRole(RoleDTO roleDto) {
        if (roleRepository.existsByName(roleDto.getName())) {
            throw new RuntimeException("Role name already exists");
        }

        Role role = new Role();
        role.setName(roleDto.getName());

        Role savedRole = roleRepository.save(role);
        return convertToDto(savedRole);
    }

    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return convertToDto(role);
    }

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public RoleDTO updateRole(Long id, RoleDTO roleDto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setName(roleDto.getName());

        Role updatedRole = roleRepository.save(role);
        return convertToDto(updatedRole);
    }

    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found");
        }
        roleRepository.deleteById(id);
    }

    private RoleDTO convertToDto(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getName()
        );
    }
}