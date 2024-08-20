package com.example.stepperbackend.web.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleNotificationRequestDto {
    private Long memberId;
    private LocalDateTime notificationTime;
}