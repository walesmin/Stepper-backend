package com.example.stepperbackend.web.controller;

import com.example.stepperbackend.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class RootController {

    final S3Service s3Service;

    @GetMapping("/health")
    public String healthCheck() {
        return "i'm healthy";
    }

    @PostMapping("/s3/upload")
    public ResponseEntity<?> s3Upload(@RequestPart(value = "image", required = false) MultipartFile image){
        String profileImage = s3Service.saveFile(image);
        return ResponseEntity.ok(profileImage);
    }

}
