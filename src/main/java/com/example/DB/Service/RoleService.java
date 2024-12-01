package com.example.DB.Service;

import com.example.DB.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    RoleDTO createRole(RoleDTO roleDto);

    RoleDTO getRoleById(Long id);

    List<RoleDTO> getAllRoles();

    RoleDTO updateRole(Long id, RoleDTO roleDto);

    void deleteRole(Long id);
}
