package com.learning.cms.controller;

import com.learning.cms.dto.MaterialResponseDTO;
import com.learning.cms.service.CourseMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
@Tag(name = "Course Material Management", description = "APIs for uploading, downloading, and managing course materials")
public class CourseMaterialController {

    private final CourseMaterialService courseMaterialService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload course material", description = "Upload a file as course material (PDF, video, document, etc.)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Material uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file or input data"),
            @ApiResponse(responseCode = "404", description = "Course not found"),
            @ApiResponse(responseCode = "500", description = "File storage error")
    })
    public ResponseEntity<MaterialResponseDTO> uploadMaterial(
            @Parameter(description = "Title of the material") @RequestParam String title,
            @Parameter(description = "ID of the course") @RequestParam Long courseId,
            @Parameter(description = "File to upload (max 10MB)") @RequestParam MultipartFile file) {
        MaterialResponseDTO response = courseMaterialService.uploadMaterial(title, courseId, file);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/download")
    @Operation(summary = "Download course material", description = "Download a course material file by material ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File downloaded successfully", content = @Content(mediaType = "application/octet-stream")),
            @ApiResponse(responseCode = "404", description = "Material not found"),
            @ApiResponse(responseCode = "500", description = "File not found on server")
    })
    public ResponseEntity<Resource> downloadMaterial(@Parameter(description = "ID of the material") @PathVariable Long id) {
        Resource resource = courseMaterialService.downloadMaterial(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get materials by course", description = "Retrieve all materials for a specific course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Materials retrieved successfully")
    })
    public ResponseEntity<List<MaterialResponseDTO>> getMaterialsByCourseId(
            @Parameter(description = "ID of the course") @PathVariable Long courseId) {
        List<MaterialResponseDTO> materials = courseMaterialService.getMaterialsByCourseId(courseId);
        return ResponseEntity.ok(materials);
    }
}
