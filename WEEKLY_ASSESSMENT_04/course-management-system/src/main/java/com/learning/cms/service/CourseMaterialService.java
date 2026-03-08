package com.learning.cms.service;

import com.learning.cms.dto.MaterialResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseMaterialService {
    MaterialResponseDTO uploadMaterial(String title, Long courseId, MultipartFile file);
    Resource downloadMaterial(Long id);
    List<MaterialResponseDTO> getMaterialsByCourseId(Long courseId);
}
