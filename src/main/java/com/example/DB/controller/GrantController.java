package com.example.DB.controller;

import com.example.DB.Service.GrantService;
import com.example.DB.dto.GrantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grants")
@RequiredArgsConstructor
public class GrantController {
    private final GrantService grantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrantDto createGrant(@RequestBody GrantDto grantDto) {
        return grantService.createGrant(grantDto);
    }

    @GetMapping("/{id}")
    public GrantDto getGrant(@PathVariable Long id) {
        return grantService.getGrantById(id);
    }

    @GetMapping("/project/{projectId}")
    public List<GrantDto> getGrantsByProject(@PathVariable Long projectId) {
        return grantService.getGrantsByProject(projectId);
    }

    @GetMapping("/user/{userId}")
    public List<GrantDto> getGrantsByUser(@PathVariable Long userId) {
        return grantService.getGrantsByUser(userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revokeGrant(@PathVariable Long id) {
        grantService.revokeGrant(id);
    }
}