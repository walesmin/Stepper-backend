package com.example.stepperbackend.web.dto;

import com.example.stepperbackend.domain.enums.SubCategory;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    @Getter
    public static class PostRequestDto {
        private String imageUrl;
        private String title;
        private String body;
        @Nullable
        private String bodyPart;
        @Nullable
        private SubCategory subCategory;
        @Nullable
        private Long weeklyMissionId;
    }

    @Data
    @Builder
    public static class PostResponseDto {
        private Long id;
        private String imageUrl;
        private String title;
        private String body;
        private String authorEmail;
        private String bodyPart;
        private SubCategory subCategory;
        private String weeklyMissionTitle;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<ImageDto.ImageResponseDto> imageList;

    }

    @Data
    @Builder
    public static class PostViewDto {
        private Long id;
        private String profileImageUrl;
        private String title;
        private String body;
        private String authorEmail;
        private String bodyPart;
        private int likes;
        private int scraps;
        private int commentsCount;
        private SubCategory subCategory;
        private String weeklyMissionTitle;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<ImageDto.ImageResponseDto> imageList;
    }
}