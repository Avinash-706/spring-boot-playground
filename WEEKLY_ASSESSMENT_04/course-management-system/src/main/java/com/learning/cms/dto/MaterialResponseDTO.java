package com.learning.cms.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MaterialResponseDTO {
    private Long id;
    private String title;
    private String fileName;
    private String fileType;
    private String fileUrl;
    private LocalDate uploadDate;
}
