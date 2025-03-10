package com.example.stepperbackend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class MemberDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class MemberResponseDto {
        private Long id;
        private String name;
        private String nickName;
        private String email;
        private String password;
        private String profileImage;
        private Long height;
        private Long weight;
        private boolean communityAlarm;
        private boolean exerciseAlarm;
        private boolean emailAgree;
        private boolean useAgree;
        private boolean perAgree;
        private LocalDate createdAt;
        private LocalDate updatedAt;
        //private String firebaseToken;
    }

    @Data
    public static class MemberSignupRequestDto {
        private String name;
        private String nickName;
        private String email;
        private String password;
        private String profileImage;
        private Long height;
        private Long weight;
        private boolean communityAlarm;
        private boolean exerciseAlarm;
        private boolean emailAgree;
        private boolean useAgree;
        private boolean perAgree;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberLoginRequestDto {
        private String email;
        private String password;
        private String firebaseToken;
    }
}
