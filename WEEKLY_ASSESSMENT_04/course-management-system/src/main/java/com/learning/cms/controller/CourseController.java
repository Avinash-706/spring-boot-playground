package com.learning.cms.controller;

import com.learning.cms.dto.CourseRequestDTO;
import com.learning.cms.dto.CourseResponseDTO;
import com.learning.cms.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "Course Management", description = "APIs for creating, updating, deleting, and viewing courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @Operation(summary = "Create a new course", description = "Create a new course by an instructor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Instructor not found")
    })
    public ResponseEntity<CourseResponseDTO> createCourse(
            @Valid @RequestBody CourseRequestDTO courseRequestDTO,
            @Parameter(description = "ID of the instructor creating the course") @RequestParam Long instructorId) {
        CourseResponseDTO response = courseService.createCourse(courseRequestDTO, instructorId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a course", description = "Update course details by course ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<CourseResponseDTO> updateCourse(
            @Parameter(description = "ID of the course to update") @PathVariable Long id,
            @Valid @RequestBody CourseRequestDTO courseRequestDTO) {
        CourseResponseDTO response = courseService.updateCourse(id, courseRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course", description = "Delete a course by course ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Course deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<Void> deleteCourse(@Parameter(description = "ID of the course to delete") @PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(
            summary = "Get all courses with pagination and sorting",
            description = "Retrieve all courses with pagination support. " +
                    "Returns a paginated response with course details. " +
                    "\n\nPagination Parameters:" +
                    "\n- page: Page number starting from 0 (default: 0)" +
                    "\n- size: Number of courses per page (default: 10)" +
                    "\n- sort: Field name to sort by (options: title, price, createdAt, level, duration)" +
                    "\n\nExample: /api/courses?page=0&size=5&sort=price" +
                    "\n\nResponse includes:" +
                    "\n- content: List of courses" +
                    "\n- totalPages: Total number of pages" +
                    "\n- totalElements: Total number of courses" +
                    "\n- size: Page size" +
                    "\n- number: Current page number"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courses retrieved successfully with pagination metadata")
    })
    public ResponseEntity<Page<CourseResponseDTO>> getAllCourses(
            @Parameter(
                    description = "Page number (0-indexed). First page is 0",
                    example = "0"
            ) @RequestParam(defaultValue = "0") int page,
            @Parameter(
                    description = "Number of courses per page",
                    example = "10"
            ) @RequestParam(defaultValue = "10") int size,
            @Parameter(
                    description = "Field to sort by. Available fields: title, price, createdAt, level, duration",
                    example = "title"
            ) @RequestParam(defaultValue = "title") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<CourseResponseDTO> courses = courseService.getAllCourses(pageable);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Retrieve course details by course ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course found"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<CourseResponseDTO> getCourseById(@Parameter(description = "ID of the course") @PathVariable Long id) {
        CourseResponseDTO response = courseService.getCourseById(id);
        return ResponseEntity.ok(response);
    }
}
