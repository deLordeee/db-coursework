package com.example.DB.controller;

import com.example.DB.Service.ArtefactService;
import com.example.DB.dto.ArtefactDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/artefacts")
@RequiredArgsConstructor
public class ArtefactController {
    private final ArtefactService artefactService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtefactDto createArtefact(@RequestBody ArtefactDto artefactDto) {
        return artefactService.createArtefact(artefactDto);
    }

    @GetMapping("/{id}")
    public ArtefactDto getArtefact(@PathVariable Long id) {
        return artefactService.getArtefactById(id);
    }

    @GetMapping("/project/{projectId}")
    public List<ArtefactDto> getProjectArtefacts(@PathVariable Long projectId) {
        return artefactService.getProjectArtefacts(projectId);
    }

    @GetMapping("/user/{userId}")
    public List<ArtefactDto> getUserArtefacts(@PathVariable Long userId) {
        return artefactService.getUserArtefacts(userId);
    }

    @PutMapping("/{id}")
    public ArtefactDto updateArtefact(@PathVariable Long id, @RequestBody ArtefactDto artefactDto) {
        return artefactService.updateArtefact(id, artefactDto);
    }
}