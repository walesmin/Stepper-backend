package com.example.stepperbackend.service;

import com.example.stepperbackend.domain.Member;
import com.example.stepperbackend.repository.MemberRepository;
import com.example.stepperbackend.web.dto.FCMNotificationRequestDto;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class FCMNotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final MemberRepository memberRepository;

    public String sendNotificationByToken(FCMNotificationRequestDto requestDto) {

        Optional<Member> user = memberRepository.findById(requestDto.getTargetUserId());

        if (user.isPresent()) {
            if (user.get().getFirebaseToken() != null) {
                Notification notification = Notification.builder()
                        .setTitle(requestDto.getTitle())
                        .setBody(requestDto.getBody())
                        .build();

                Message message = Message.builder()
                        .setToken(user.get().getFirebaseToken())
                        .setNotification(notification)
                        .build();

                try {
                    firebaseMessaging.send(message);
                    return "알림을 성공적으로 전송했습니다. targetUserId=" + requestDto.getTargetUserId();
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                    return "알림 보내기를 실패하였습니다. targetUserId=" + requestDto.getTargetUserId();
                }
            } else {
                return "서버에 저장된 해당 유저의 FirebaseToken이 존재하지 않습니다. targetUserId=" + requestDto.getTargetUserId();
            }

        } else {
            return "해당 유저가 존재하지 않습니다. targetUserId=" + requestDto.getTargetUserId();
        }


    }
}