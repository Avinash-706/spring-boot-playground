package com.learning.cms.service;

import com.learning.cms.dto.CourseRequestDTO;
import com.learning.cms.dto.CourseResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    CourseResponseDTO createCourse(CourseRequestDTO courseRequestDTO, Long instructorId);
    CourseResponseDTO updateCourse(Long id, CourseRequestDTO courseRequestDTO);
    void deleteCourse(Long id);
    Page<CourseResponseDTO> getAllCourses(Pageable pageable);
    CourseResponseDTO getCourseById(Long id);
}
