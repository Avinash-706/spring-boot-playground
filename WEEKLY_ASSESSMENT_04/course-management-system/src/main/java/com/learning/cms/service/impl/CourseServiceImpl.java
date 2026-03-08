package com.learning.cms.service.impl;

import com.learning.cms.dto.CourseRequestDTO;
import com.learning.cms.dto.CourseResponseDTO;
import com.learning.cms.entity.Course;
import com.learning.cms.entity.User;
import com.learning.cms.exception.ResourceNotFoundException;
import com.learning.cms.repository.CourseRepository;
import com.learning.cms.repository.UserRepository;
import com.learning.cms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @CacheEvict(value = "courses", allEntries = true)
    public CourseResponseDTO createCourse(CourseRequestDTO courseRequestDTO, Long instructorId) {
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

        Course course = modelMapper.map(courseRequestDTO, Course.class);
        course.setInstructor(instructor);
        Course savedCourse = courseRepository.save(course);

        CourseResponseDTO responseDTO = modelMapper.map(savedCourse, CourseResponseDTO.class);
        responseDTO.setInstructorName(instructor.getFullName());
        return responseDTO;
    }

    @Override
    @CacheEvict(value = "courses", allEntries = true)
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO courseRequestDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        course.setTitle(courseRequestDTO.getTitle());
        course.setDescription(courseRequestDTO.getDescription());
        course.setPrice(courseRequestDTO.getPrice());
        course.setDuration(courseRequestDTO.getDuration());
        course.setLevel(courseRequestDTO.getLevel());

        Course updatedCourse = courseRepository.save(course);
        CourseResponseDTO responseDTO = modelMapper.map(updatedCourse, CourseResponseDTO.class);
        responseDTO.setInstructorName(updatedCourse.getInstructor().getFullName());
        return responseDTO;
    }

    @Override
    @CacheEvict(value = "courses", allEntries = true)
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        courseRepository.delete(course);
    }

    @Override
    @Cacheable(value = "courses")
    public Page<CourseResponseDTO> getAllCourses(Pageable pageable) {
        Page<Course> courses = courseRepository.findAll(pageable);
        return courses.map(course -> {
            CourseResponseDTO dto = modelMapper.map(course, CourseResponseDTO.class);
            dto.setInstructorName(course.getInstructor().getFullName());
            return dto;
        });
    }

    @Override
    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        CourseResponseDTO responseDTO = modelMapper.map(course, CourseResponseDTO.class);
        responseDTO.setInstructorName(course.getInstructor().getFullName());
        return responseDTO;
    }
}
