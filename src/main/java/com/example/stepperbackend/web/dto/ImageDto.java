package com.example.stepperbackend.web.dto;

import lombok.Builder;
import lombok.Data;


public class ImageDto {

    @Data
    @Builder
    public static class ImageResponseDto {
        private Long id;
        private String imageUrl;
    }
}
