package com.example.DB.Service;

import com.example.DB.dto.ArtefactDto;
import java.util.*;
public interface ArtefactService {
    ArtefactDto createArtefact(ArtefactDto artefactDto);

    ArtefactDto getArtefactById(Long id);

    List<ArtefactDto> getProjectArtefacts(Long projectId);

    List<ArtefactDto> getUserArtefacts(Long userId);

    ArtefactDto updateArtefact(Long id, ArtefactDto artefactDto);
}
