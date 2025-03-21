package com.example.stepperbackend.web.controller;

import com.example.stepperbackend.apiPayload.ApiResponse;
import com.example.stepperbackend.converter.MyExerciseConverter;
import com.example.stepperbackend.converter.RateDiaryConverter;
import com.example.stepperbackend.domain.RateDiary;
import com.example.stepperbackend.service.RateDiaryService.RateDiaryService;
import com.example.stepperbackend.web.dto.RateDiaryDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/RateDiary")
public class RateDiaryController {

    private final RateDiaryService rateDiaryService;

    @PostMapping("/write")
    @Operation(summary = "평가일지 작성 API", description = "평가일지 작성")
    public ApiResponse<RateDiaryDto.RateDiaryWriteResponseDTO> Write(@RequestPart(value = "image", required = false) MultipartFile image,
                                                                     @RequestPart(value = "request") @Valid RateDiaryDto.RateDiaryWriteRequestDTO request) {
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        RateDiaryDto.RateDiaryWriteResponseDTO rateDiary = rateDiaryService.writeDiary(image, request, memberId);
        return ApiResponse.onSuccess(rateDiary);
    }


    @GetMapping("/check")
    @Operation(summary = "평가일지 조회 API", description = "평가일지 조회")
    public ApiResponse<List<RateDiaryDto.RateDiaryCheckResponseDTO>> Check() {
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        List<RateDiary> rateDiary = rateDiaryService.checkRateDiary(memberId);

        return ApiResponse.onSuccess(RateDiaryConverter.toCheckRateDiaryListDTO(rateDiary));
    }
}