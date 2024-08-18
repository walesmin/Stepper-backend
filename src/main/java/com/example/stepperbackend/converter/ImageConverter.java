package com.example.stepperbackend.converter;

import com.example.stepperbackend.domain.*;
import com.example.stepperbackend.web.dto.ImageDto;

public class ImageConverter {

    public static Image toEntity(String url, Post post) {
        return Image.builder()
                .url(url)
                .post(post)
                .build();
    }

    public static ImageDto.ImageResponseDto toDto(Image image) {
        return ImageDto.ImageResponseDto.builder()
                .id(image.getId())
                .imageUrl(image.getUrl())
                .build();
    }
}
