package com.example.stepperbackend.web.controller;

import com.example.stepperbackend.service.FCMNotificationService;
import com.example.stepperbackend.service.SchedulerService;
import com.example.stepperbackend.web.dto.FCMNotificationRequestDto;
import com.example.stepperbackend.web.dto.ScheduleNotificationRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Notification", description = "FCM Notification api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notification")
public class FCMNotificationApiController {

    private final FCMNotificationService fcmNotificationService;
    private final SchedulerService SchedulerService;

    @Operation(summary = "알림 보내기")
    @PostMapping("/alarm")
    public String sendNotificationByToken(@RequestBody FCMNotificationRequestDto requestDto) {
        return fcmNotificationService.sendNotificationByToken(requestDto);
    }

    @Operation(summary = "알림 시간 설정")
    @PostMapping("/schedule")
    public ResponseEntity<String> scheduleNotification(@RequestBody ScheduleNotificationRequestDto requestDto) {
        LocalDateTime notificationTime = requestDto.getNotificationTime();
        Long memberId = requestDto.getMemberId();

        SchedulerService.scheduleNotification(memberId, notificationTime);
        return ResponseEntity.ok("Notification scheduled for " + notificationTime);
    }

    @Operation(summary = "알림 취소")
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelNotification(@RequestParam Long memberId) {
        SchedulerService.cancelScheduledTask(memberId);
        return ResponseEntity.ok("Notification cancelled for memberId: " + memberId);
    }

}
