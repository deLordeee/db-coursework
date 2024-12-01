package com.example.DB.controller;


import com.example.DB.Service.RoleService;
import com.example.DB.dto.RoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDTO createRole(@RequestBody RoleDTO roleDto) {
        return roleService.createRole(roleDto);
    }

    @GetMapping("/{id}")
    public RoleDTO getRole(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @GetMapping
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PutMapping("/{id}")
    public RoleDTO updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDto) {
        return roleService.updateRole(id, roleDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
}