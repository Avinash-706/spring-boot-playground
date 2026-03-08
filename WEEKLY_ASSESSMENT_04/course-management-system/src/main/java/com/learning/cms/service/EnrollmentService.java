package com.learning.cms.service;

import com.learning.cms.dto.EnrollmentRequestDTO;
import com.learning.cms.dto.EnrollmentResponseDTO;

import java.util.List;

public interface EnrollmentService {
    EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO enrollmentRequestDTO);
    List<EnrollmentResponseDTO> getEnrollmentsByStudentId(Long studentId);
    List<EnrollmentResponseDTO> getEnrollmentsByCourseId(Long courseId);
}
