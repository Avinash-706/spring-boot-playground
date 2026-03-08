package com.learning.cms.service.impl;

import com.learning.cms.dto.EnrollmentRequestDTO;
import com.learning.cms.dto.EnrollmentResponseDTO;
import com.learning.cms.entity.Course;
import com.learning.cms.entity.Enrollment;
import com.learning.cms.entity.EnrollmentStatus;
import com.learning.cms.entity.User;
import com.learning.cms.exception.ResourceNotFoundException;
import com.learning.cms.repository.CourseRepository;
import com.learning.cms.repository.EnrollmentRepository;
import com.learning.cms.repository.UserRepository;
import com.learning.cms.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO enrollmentRequestDTO) {
        User student = userRepository.findById(enrollmentRequestDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Course course = courseRepository.findById(enrollmentRequestDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        enrollment.setProgressPercentage(0);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        EnrollmentResponseDTO responseDTO = modelMapper.map(savedEnrollment, EnrollmentResponseDTO.class);
        responseDTO.setCourseTitle(course.getTitle());
        responseDTO.setStudentName(student.getFullName());
        return responseDTO;
    }

    @Override
    public List<EnrollmentResponseDTO> getEnrollmentsByStudentId(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream().map(enrollment -> {
            EnrollmentResponseDTO dto = modelMapper.map(enrollment, EnrollmentResponseDTO.class);
            dto.setCourseTitle(enrollment.getCourse().getTitle());
            dto.setStudentName(enrollment.getStudent().getFullName());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentResponseDTO> getEnrollmentsByCourseId(Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        return enrollments.stream().map(enrollment -> {
            EnrollmentResponseDTO dto = modelMapper.map(enrollment, EnrollmentResponseDTO.class);
            dto.setCourseTitle(enrollment.getCourse().getTitle());
            dto.setStudentName(enrollment.getStudent().getFullName());
            return dto;
        }).collect(Collectors.toList());
    }
}
