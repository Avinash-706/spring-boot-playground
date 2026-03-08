package com.learning.cms.dto;

import com.learning.cms.entity.EnrollmentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EnrollmentResponseDTO {
    private Long id;
    private String courseTitle;
    private String studentName;
    private EnrollmentStatus status;
    private Integer progressPercentage;
    private LocalDate enrollmentDate;
}
