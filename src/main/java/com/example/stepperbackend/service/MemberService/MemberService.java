package com.example.stepperbackend.service.MemberService;

import com.example.stepperbackend.web.dto.MemberDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface MemberService {
    MemberDto.MemberResponseDto signup(MemberDto.MemberSignupRequestDto dto);

    void deleteMember(String email);

    MemberDto.MemberResponseDto getMemberInfo(String email);

    // Firebase 토큰 저장 메서드
    void saveFirebaseToken(String email, String firebaseToken);
}
