package com.example.stepperbackend.converter;

import com.example.stepperbackend.domain.Image;
import com.example.stepperbackend.domain.Member;
import com.example.stepperbackend.domain.Post;
import com.example.stepperbackend.domain.WeeklyMission;
import com.example.stepperbackend.domain.enums.BodyPart;
import com.example.stepperbackend.web.dto.BadgeDto;
import com.example.stepperbackend.web.dto.ImageDto;
import com.example.stepperbackend.web.dto.PostDto;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static Post toEntity(PostDto.PostRequestDto dto, Member member, WeeklyMission weeklyMission) {
        return Post.builder()
                .imageUrl(dto.getImageUrl())
                .title(dto.getTitle())
                .body(dto.getBody())
                .bodyPart(dto.getBodyPart() != null ? BodyPart.valueOf(dto.getBodyPart()) : null)
                .subCategory(dto.getSubCategory() != null ? dto.getSubCategory() : null)
                .member(member)
                .weeklyMission(weeklyMission != null ? weeklyMission : null)
                .build();
    }

    public static PostDto.PostResponseDto toDto(Post post, List<Image> imageList) {
        List<ImageDto.ImageResponseDto> imageDtoList = imageList.stream()
                .map(ImageConverter::toDto).collect(Collectors.toList());

        return PostDto.PostResponseDto.builder()
                .id(post.getId())
                //.imageUrl(post.getImageUrl())
                .title(post.getTitle())
                .body(post.getBody())
                .bodyPart(post.getBodyPart() != null ? post.getBodyPart().toString() : null)
                .authorEmail(post.getMember().getEmail() != null ? post.getMember().getEmail() : null)
                .subCategory(post.getSubCategory())
                //.weeklyMissionTitle(post.getWeeklyMission() != null ? post.getWeeklyMission().getMissionTitle() : null)
                .weeklyMissionTitle(post.getWeeklyMission() != null ? post.getWeeklyMission().getMissionTitle() : null)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .imageList(imageDtoList)
                .build();
    }

    public static PostDto.PostViewDto toViewDto(Post post, int likes, int scraps, int commentsCount, List<Image> imageList) {
        List<ImageDto.ImageResponseDto> imageDtoList = imageList.stream()
                .map(ImageConverter::toDto).collect(Collectors.toList());

        return PostDto.PostViewDto.builder()
                .id(post.getId())
                .profileImageUrl(post.getMember().getProfileImage())
                .title(post.getTitle())
                .body(post.getBody())
                .bodyPart(post.getBodyPart().toString())
                .authorEmail(post.getMember().getEmail())
                .subCategory(post.getSubCategory())
                //
                .likes(likes)
                .scraps(scraps)
                .commentsCount(commentsCount)
                //.weeklyMissionTitle(post.getWeeklyMission() != null ? post.getWeeklyMission().getMissionTitle() : null)
                .weeklyMissionTitle(post.getWeeklyMission() != null ? post.getWeeklyMission().getMissionTitle() : null)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .imageList(imageDtoList)
                .build();
    }
}