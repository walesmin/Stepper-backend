package com.example.stepperbackend.service;

import com.example.stepperbackend.domain.Member;
import com.example.stepperbackend.repository.MemberRepository;
import com.example.stepperbackend.web.dto.FCMNotificationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final TaskScheduler taskScheduler;
    private final FCMNotificationService fcmNotificationService;
    private final MemberRepository memberRepository;

    private Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public void scheduleNotification(Long memberId, LocalDateTime time) {
        cancelScheduledTask(memberId); // 기존에 등록된 작업을 취소

        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(() -> {
            sendNotification(memberId);
        }, Date.from(time.atZone(ZoneId.systemDefault()).toInstant()));

        scheduledTasks.put(memberId, scheduledTask);
        log.info("Scheduled notification for user {} at {}", memberId, time);
    }

    public void cancelScheduledTask(Long memberId) {
        ScheduledFuture<?> scheduledTask = scheduledTasks.get(memberId);
        if (scheduledTask != null && !scheduledTask.isCancelled()) {
            scheduledTask.cancel(true);
            scheduledTasks.remove(memberId);
            log.info("Cancelled scheduled notification for user {}", memberId);
        }
    }

    private void sendNotification(Long memberId) {
        // FCMNotificationService를 사용하여 알림 전송
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member != null && member.getFirebaseToken() != null) {
            FCMNotificationRequestDto requestDto = FCMNotificationRequestDto.builder()
                    .targetUserId(memberId)
                    .title("STEPPER")
                    .body("재활 루틴을 진행할 시간이에요! STEPPER에 얼른 들어오세요!")
                    .build();
            fcmNotificationService.sendNotificationByToken(requestDto);
            log.info("Sent scheduled notification to user {}", memberId);
        }
    }
}