package com.example.stepperbackend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.annotation.Value;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Configuration
public class FCMConfig {

    // 환경 변수에서 JSON 문자열을 가져옴
    //@Value("${FIREBASE}")
    @Value("${FIREBASE_ENCODED}")
    private String firebaseConfig;

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        //ClassPathResource resource = new ClassPathResource("firebase/fcmstepper-firebase-adminsdk-isxl7-3326eb84b9.json");

        byte[] decodedBytes = Base64.getDecoder().decode(firebaseConfig);
        // 환경 변수에서 가져온 JSON 문자열을 InputStream으로 변환
        //InputStream refreshToken = new ByteArrayInputStream(firebaseConfig.getBytes());
        InputStream refreshToken = new ByteArrayInputStream(decodedBytes);

        //InputStream refreshToken = resource.getInputStream();

        FirebaseApp firebaseApp = null;
        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();

        if (firebaseAppList != null && !firebaseAppList.isEmpty()) {
            for (FirebaseApp app : firebaseAppList) {
                if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                    firebaseApp = app;
                }
            }
        } else {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .build();

            firebaseApp = FirebaseApp.initializeApp(options);
        }

        return FirebaseMessaging.getInstance(firebaseApp);
    }
}