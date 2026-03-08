package com.learning.cms.service.impl;

import com.learning.cms.dto.MaterialResponseDTO;
import com.learning.cms.entity.Course;
import com.learning.cms.entity.CourseMaterial;
import com.learning.cms.exception.FileStorageException;
import com.learning.cms.exception.ResourceNotFoundException;
import com.learning.cms.repository.CourseMaterialRepository;
import com.learning.cms.repository.CourseRepository;
import com.learning.cms.service.CourseMaterialService;
import com.learning.cms.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseMaterialServiceImpl implements CourseMaterialService {

    private final CourseMaterialRepository courseMaterialRepository;
    private final CourseRepository courseRepository;
    private final FileStorageUtil fileStorageUtil;
    private final ModelMapper modelMapper;

    @Override
    public MaterialResponseDTO uploadMaterial(String title, Long courseId, MultipartFile file) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        String fileName = fileStorageUtil.storeFile(file);

        CourseMaterial material = new CourseMaterial();
        material.setTitle(title);
        material.setCourse(course);
        material.setFileName(fileName);
        material.setFileType(file.getContentType());
        material.setFileUrl("/api/materials/" + fileName);
        material.setUploadDate(LocalDate.now());

        CourseMaterial savedMaterial = courseMaterialRepository.save(material);
        return modelMapper.map(savedMaterial, MaterialResponseDTO.class);
    }

    @Override
    public Resource downloadMaterial(Long id) {
        CourseMaterial material = courseMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material not found"));

        try {
            Path filePath = fileStorageUtil.loadFile(material.getFileName());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileStorageException("File not found");
            }
        } catch (Exception ex) {
            throw new FileStorageException("File not found");
        }
    }

    @Override
    public List<MaterialResponseDTO> getMaterialsByCourseId(Long courseId) {
        List<CourseMaterial> materials = courseMaterialRepository.findByCourseId(courseId);
        return materials.stream()
                .map(material -> modelMapper.map(material, MaterialResponseDTO.class))
                .collect(Collectors.toList());
    }
}
