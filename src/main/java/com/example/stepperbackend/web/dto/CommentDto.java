package com.example.stepperbackend.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public class CommentDto {

    @Getter
    public static class CommentRequestDto {
        private Long postId;
        private String content;
        private boolean anonymous;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResponseDto {
        private Long postId;
        private Long commentId;
        private String profileImage;
        private String memberName;
        private String content;
        private LocalDateTime dateTime;
        private List<ReplyResponseDto> replyList;
    }

    @Getter
    public static class ReplyRequestDto {
        private Long postId;
        private Long parentCommentId;
        private String content;
        private boolean anonymous;
    }

    @Builder
    @Getter
    public static class ReplyResponseDto {
        private Long postId;
        private Long parentCommentId;
        private String content;
        private boolean anonymous;
        private String profileImage;
        private LocalDateTime localDateTime;
        private String memberName;
    }
}
